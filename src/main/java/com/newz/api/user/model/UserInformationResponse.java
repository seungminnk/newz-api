package com.newz.api.user.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInformationResponse {

  private int id;
  private String name;
  private String email;
  private boolean haveKeywords;

  @JsonInclude(Include.NON_EMPTY)
  private TokenResponse tokens;

}
