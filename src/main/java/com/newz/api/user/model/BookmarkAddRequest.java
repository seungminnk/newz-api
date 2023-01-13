package com.newz.api.user.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookmarkAddRequest {

  @Min(value = 1, message = "'userId' 필드는 필수입니다.")
  private int userId;

  @NotBlank(message = "'newsUrl' 필드는 필수입니다.")
  private String newsUrl;

}
