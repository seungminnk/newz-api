package com.newz.api.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {

  private int id;
  private String socialServiceType;
  private String socialServiceUniqueId;
  private String name;
  private String email;
  private String refreshToken;

  private boolean haveKeywords;

}
