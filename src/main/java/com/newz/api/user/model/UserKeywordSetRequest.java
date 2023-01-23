package com.newz.api.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserKeywordSetRequest {

  @Schema(accessMode = AccessMode.READ_ONLY)
  private int userId;
  @Schema(description = "삭제할 키워드들", example = "['플러터', '구글']")
  private List<String> keywords;

}
