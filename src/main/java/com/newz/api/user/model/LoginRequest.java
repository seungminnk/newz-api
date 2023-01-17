package com.newz.api.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

  private SocialServiceType serviceType;
  private String serviceUniqueId;

}
