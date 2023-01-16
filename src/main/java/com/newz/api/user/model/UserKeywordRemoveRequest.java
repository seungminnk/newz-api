package com.newz.api.user.model;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserKeywordRemoveRequest {

  @Min(value = 1, message = "'userId' 필드는 필수입니다.")
  private int userId;

  @NotEmpty(message = "'keywords' 필드는 필수입니다.")
  private List<String> keywords;

}
