package com.newz.api.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

  @Schema(description = "소셜로그인 종류", example = "google")
  private SocialServiceType serviceType;
  @Schema(description = "소셜로그인 고유 id")
  private String serviceUniqueId;
  @Schema(description = "이름", example = "홍길동")
  private String name;
  @Schema(description = "이메일", example = "gil-dong@test.com")
  private String email;

}
