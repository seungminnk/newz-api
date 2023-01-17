package com.newz.api.user.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserKeywordRemoveRequest {

  private int userId;
  private List<String> keywords;

}
