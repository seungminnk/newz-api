package com.newz.api.common.auth;

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
import java.security.Key;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class JwtUtil {

  private static final long ACCESS_TOKEN_VALID_TIME = 1209600000;  // 14일
  private static final long REFRESH_TOKEN_VALID_TIME = 31536000000L;  // 1년

  private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

  private static Key signingKey;

  @PostConstruct
  void initialize() {
    String secretKey = System.getenv("SECRET_KEY");

    byte[] secretKeyBytes = Base64.getDecoder().decode(secretKey);
    signingKey = new SecretKeySpec(secretKeyBytes, SIGNATURE_ALGORITHM.getJcaName());
  }

  public static String generateAccessToken(int userId) {
    Claims claims = Jwts.claims().setSubject(String.valueOf(userId));
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

  public static int parseUserIdByAccessToken(String accessToken) throws Exception {
    Claims claims = validateToken(accessToken);
    return claims.getSubject() != null ? Integer.parseInt(claims.getSubject()) : 0;
  }

}
