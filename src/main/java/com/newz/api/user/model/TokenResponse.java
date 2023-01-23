package com.newz.api.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponse {

  @Schema(description = "액세스 토큰")
  private String accessToken;
  @Schema(description = "리프레시 토큰")
  private String refreshToken;

}
