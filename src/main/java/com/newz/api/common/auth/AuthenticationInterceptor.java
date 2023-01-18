package com.newz.api.common.auth;

import com.newz.api.common.exception.ErrorCode;
import com.newz.api.common.exception.NewzCommonException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

@Component
public class AuthenticationInterceptor implements AsyncHandlerInterceptor {

  private static final String ACCESS_TOKEN_HEADER = "x-newz-access-token";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    String accessToken = request.getHeader(ACCESS_TOKEN_HEADER);
    if(StringUtils.isBlank(accessToken)) {
      throw new NewzCommonException(HttpStatus.BAD_REQUEST, ErrorCode.TOKEN_NOT_FOUND);
    }

    int userId = JwtUtil.parseUserIdByAccessToken(accessToken);
    if(userId == 0) {
      throw new NewzCommonException(HttpStatus.NOT_FOUND, ErrorCode.USER_NOT_FOUND);
    }

    return true;
  }

}
