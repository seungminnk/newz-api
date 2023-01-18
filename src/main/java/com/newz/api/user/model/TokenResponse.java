package com.newz.api.user.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponse {

  private String accessToken;
  private String refreshToken;

}
