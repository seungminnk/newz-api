package com.newz.api.common.auth;

import com.google.gson.Gson;
import com.newz.api.common.exception.ErrorCode;
import com.newz.api.common.exception.NewzCommonException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class JwtUtil {

  private static final long ACCESS_TOKEN_VALID_TIME = 1209600000;  // 14일
  private static final long REFRESH_TOKEN_VALID_TIME = 31536000000L;  // 1년

  private static final Gson gson = new Gson();

  private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

  private static Key signingKey;
  private static SecretKey encryptKey;
  private static IvParameterSpec iv;

  @PostConstruct
  void initialize() {
    String secretKey = System.getenv("SECRET_KEY");
    byte[] secretKeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
    signingKey = new SecretKeySpec(secretKeyBytes, SIGNATURE_ALGORITHM.getJcaName());

    String saltKey = System.getenv("SALT_KEY");
    byte[] saltKeyBytes = saltKey.getBytes(StandardCharsets.UTF_8);
    encryptKey = new SecretKeySpec(saltKeyBytes, "AES");

    String ivKey = System.getenv("IV_KEY");
    byte[] ivKeyBytes = ivKey.getBytes(StandardCharsets.UTF_8);
    iv = new IvParameterSpec(ivKeyBytes);
  }

  private JwtUtil() {}

  public static String generateAccessToken(NewzUser user) throws Exception {
    String userDataStr = gson.toJson(user);

    Claims claims = Jwts.claims().setSubject(encrypt(userDataStr));
    Date expiredDate = new Date(new Date().getTime() + ACCESS_TOKEN_VALID_TIME);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuer("newmoa")
        .setIssuedAt(Calendar.getInstance().getTime())
        .setAudience("www.newz.com")
        .setExpiration(expiredDate)
        .signWith(signingKey, SIGNATURE_ALGORITHM)
        .compact();
  }

  public static String generateRefreshToken() {
    Date expiredDate = new Date(new Date().getTime() + REFRESH_TOKEN_VALID_TIME);

    return Jwts.builder()
        .setIssuedAt(Calendar.getInstance().getTime())
        .setExpiration(expiredDate)
        .signWith(signingKey, SIGNATURE_ALGORITHM)
        .compact();
  }

  private static String encrypt(String str) throws NoSuchPaddingException,
                                                  NoSuchAlgorithmException,
                                                  InvalidKeyException,
                                                  IllegalBlockSizeException,
                                                  BadPaddingException,
                                                  InvalidAlgorithmParameterException {
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    cipher.init(Cipher.ENCRYPT_MODE, encryptKey, iv);

    byte[] encryptedStr = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
    byte[] encodedEncryptedStr = Base64.getEncoder().encode(encryptedStr);

    return new String(encodedEncryptedStr);
  }

  private static String decrypt(String str) throws NoSuchPaddingException,
                                                  NoSuchAlgorithmException,
                                                  InvalidKeyException,
                                                  IllegalBlockSizeException,
                                                  BadPaddingException,
                                                  InvalidAlgorithmParameterException {
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    cipher.init(Cipher.DECRYPT_MODE, encryptKey, iv);

    byte[] decodedStr = Base64.getDecoder().decode(str);
    byte[] decryptedStr = cipher.doFinal(decodedStr);

    return new String(decryptedStr, StandardCharsets.UTF_8);
  }

  public static Claims validateToken(String token) throws Exception {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(signingKey)
          .build()
          .parseClaimsJws(token)
          .getBody();
    } catch (ExpiredJwtException e) {
      throw new NewzCommonException(HttpStatus.UNAUTHORIZED, ErrorCode.EXPIRED_TOKEN);
    } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
      throw new NewzCommonException(HttpStatus.UNAUTHORIZED,ErrorCode.INVALID_TOKEN);
    } catch (Exception e) {
      throw new Exception(e);
    }
  }

  public static NewzUser getUserInformationByAccessToken(String accessToken) throws Exception {
    Claims claims = validateToken(accessToken);

    String subjectStr = claims.getSubject();
    if(StringUtils.isBlank(subjectStr)) {
      throw new NewzCommonException(HttpStatus.UNAUTHORIZED, ErrorCode.INVALID_TOKEN);
    }

    String decryptedStr = decrypt(subjectStr);

    return gson.fromJson(decryptedStr, NewzUser.class);
  }

}
