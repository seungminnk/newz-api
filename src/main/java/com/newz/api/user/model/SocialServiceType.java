package com.newz.api.user.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SocialServiceType {
  GOOGLE("google");

  private String serviceType;

  @JsonValue
  public String getServiceType() {
    return this.serviceType;
  }

  @JsonCreator
  public static SocialServiceType fromValue(String serviceType) {
    if(serviceType == null) {
      return null;
    }

    switch (serviceType) {
      case "google":
        return GOOGLE;
      default:
        return null;
    }
  }
}
