package com.newz.api.common.auth;

import com.newz.api.common.exception.ErrorCode;
import com.newz.api.common.exception.NewzCommonException;
import com.newz.api.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

@Component
@AllArgsConstructor
public class AuthenticationInterceptor implements AsyncHandlerInterceptor {

  private UserService userService;

  private static final String ACCESS_TOKEN_HEADER = "x-newz-access-token";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    if(isPreflightRequest(request)) {
      return true;
    }

    String accessToken = request.getHeader(ACCESS_TOKEN_HEADER);
    if(StringUtils.isBlank(accessToken)) {
      throw new NewzCommonException(HttpStatus.BAD_REQUEST, ErrorCode.TOKEN_NOT_FOUND);
    }

    NewzUser user = JwtUtil.getUserInformationByAccessToken(accessToken);
    if(user == null) {
      throw new NewzCommonException(HttpStatus.NOT_FOUND, ErrorCode.USER_NOT_FOUND);
    }

    boolean isValidUser = userService.isValidUser(user.getId());
    if(!isValidUser) {
      throw new NewzCommonException(HttpStatus.NOT_FOUND, ErrorCode.USER_NOT_FOUND);
    }

    request.setAttribute("user", user);

    return true;
  }

  private boolean isPreflightRequest(HttpServletRequest request) {
    return request.getMethod().equals(HttpMethod.OPTIONS.toString());
  }

}
