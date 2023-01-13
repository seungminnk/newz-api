package com.newz.api.user.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInformationResponse {

  private int id;
  private String name;
  private String email;
  private boolean haveKeywords;

}
