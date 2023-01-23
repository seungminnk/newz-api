package com.newz.api.user.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInformationResponse {

  @Schema(description = "사용자 id", example = "1")
  private int id;
  @Schema(description = "이름", example = "홍길동")
  private String name;
  @Schema(description = "이메일", example = "gil-dong@test.com")
  private String email;
  @Schema(description = "등록해둔 키워드가 있는지 여부", example = "true")
  private boolean haveKeywords;

  @JsonInclude(Include.NON_EMPTY)
  private TokenResponse tokens;

}
