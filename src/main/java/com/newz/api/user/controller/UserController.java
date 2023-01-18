package com.newz.api.user.controller;

import com.newz.api.common.auth.NewzUser;
import com.newz.api.common.exception.ErrorCode;
import com.newz.api.common.exception.ErrorResponse;
import com.newz.api.common.exception.NewzCommonException;
import com.newz.api.user.model.BookmarkAddRequest;
import com.newz.api.user.model.BookmarkNewsListResponse;
import com.newz.api.user.model.LoginRequest;
import com.newz.api.user.model.TokenResponse;
import com.newz.api.user.model.UserInformationResponse;
import com.newz.api.user.model.UserKeywordRemoveRequest;
import com.newz.api.user.model.UserKeywordSetRequest;
import com.newz.api.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

  private UserService userService;

  @PostMapping("/login")
  public ResponseEntity<UserInformationResponse> login(@RequestBody LoginRequest request)
      throws Exception {
    if(request.getServiceType() == null) {
      throw new NewzCommonException(
          HttpStatus.BAD_REQUEST,
          ErrorCode.INVALID_REQUEST_DATA.getCode(),
          "소셜 서비스 타입을 다시 확인하세요.");
    }
    if(StringUtils.isBlank(request.getServiceUniqueId())) {
      throw new NewzCommonException(
          HttpStatus.BAD_REQUEST,
          ErrorCode.INVALID_REQUEST_DATA.getCode(),
          "소셜 서비스 고유 id 값을 다시 확인하세요.");
    }

    return new ResponseEntity<>(userService.login(request), HttpStatus.OK);
  }

  @PostMapping("/token/reissue")
  public ResponseEntity<TokenResponse> reissueTokensByRefreshToken(
      @RequestHeader("x-newz-refresh-token") String refreshToken) throws Exception {
    if(StringUtils.isBlank(refreshToken)) {
      throw new NewzCommonException(
          HttpStatus.BAD_REQUEST,
          ErrorCode.INVALID_REQUEST_DATA.getCode(),
          "리프레시 토큰을 확인하세요.");
    }

    return new ResponseEntity<>(userService.reissueTokensByRefreshToken(refreshToken), HttpStatus.OK);
  }

  @Operation(summary = "사용자 정보 가져오기")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "넘겨준 userId에 해당하는 사용자 정보 반환",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UserInformationResponse.class)
              )
          }
      ),
      @ApiResponse(
          responseCode = "400",
          description = "넘겨준 userId에 해당하는 사용자가 존재하지 않을 경우 400(Not Found) 반환",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class)
              )
          }
      )
  })
  @GetMapping("")
  public ResponseEntity<UserInformationResponse> getUserInformation(NewzUser user) {
    return new ResponseEntity<>(userService.getUserInformationByUserId(user.getId()), HttpStatus.OK);
  }

  @Operation(summary = "사용자가 등록한 키워드 목록 가져오기")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "넘겨준 userId에 해당하는 사용자가 등록해 둔 키워드 목록 반환 (List<String>)",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = List.class)
              )
          }
      )
  })
  @GetMapping("/keyword/list")
  public ResponseEntity<List<String>> getUserKeywordList(NewzUser user) {
    return new ResponseEntity<>(userService.getUserKeywordsByUserId(user.getId()), HttpStatus.OK);
  }

  @Operation(summary = "사용자 키워드 등록하기")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "원하는 키워드를 등록(키워드 여러개 한꺼번에 등록 가능하지만, 총 9개까지 등록 가능.)",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema()
              )
          }
      ),
      @ApiResponse(
          responseCode = "409",
          description = "등록하려는 키워드 개수와 등록된 키워드 개수를 합해 9개를 초과한 경우 409(Conflict) 반환",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema()
              )
          }
      )
  })
  @PostMapping("/keyword/add")
  public ResponseEntity addUserKeyword(@RequestBody UserKeywordSetRequest request, NewzUser user) {
    if(request.getKeywords().isEmpty()) {
      throw new NewzCommonException(
          HttpStatus.BAD_REQUEST,
          ErrorCode.INVALID_REQUEST_DATA.getCode(),
          "등록할 키워드는 하나 이상이어야 합니다.");
    }

    request.setUserId(user.getId());

    userService.addUserKeyword(request);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Operation(summary = "사용자 키워드 제거하기")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "등록했던 키워드를 삭제",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema()
              )
          }
      )
  })
  @PostMapping("/keyword/remove")
  public ResponseEntity removeUserKeyword(@RequestBody UserKeywordRemoveRequest request, NewzUser user) {
    if(request.getKeywords().isEmpty()) {
      throw new NewzCommonException(
          HttpStatus.BAD_REQUEST,
          ErrorCode.INVALID_REQUEST_DATA.getCode(),
          "삭제할 키워드는 하나 이상이어야 합니다.");
    }

    request.setUserId(user.getId());

    userService.removeUserKeyword(request);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Operation(summary = "사용자가 북마크해둔 뉴스 기사 데이터 반환")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "넘겨준 userId에 해당하는 사용자가 북마크해둔 뉴스 기사 목록 반환",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = BookmarkNewsListResponse.class)
              )
          }
      )
  })
  @GetMapping("/bookmark/news")
  public ResponseEntity<BookmarkNewsListResponse> getUserBookmarkNews(
      @RequestParam(value = "page", required = false, defaultValue = "1") int page,
      @RequestParam(value = "limit", required = false, defaultValue = "3") int limit,
      NewzUser user) {
    return new ResponseEntity<>(
        userService.getUserBookmarkNewsByUserId(user.getId(), page, limit), HttpStatus.OK);
  }

  @Operation(summary = "북마크 추가")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "201",
          description = "북마크 추가에 성공하면 201(Created) 반환",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema()
              )
          }
      )
  })
  @PostMapping("/bookmark/add")
  public ResponseEntity<Void> addBookmark(@RequestBody BookmarkAddRequest request, NewzUser user) {
    if(StringUtils.isBlank(request.getNewsUrl())) {
      throw new NewzCommonException(
          HttpStatus.BAD_REQUEST,
          ErrorCode.INVALID_REQUEST_DATA.getCode(),
          "북마크할 뉴스 링크는 필수 값입니다.");
    }

    request.setUserId(user.getId());

    userService.addUserBookmark(request);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @Operation(summary = "북마크 제거")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "북마크 제거에 성공하면 200(OK) 반환",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema()
              )
          }
      )
  })
  @PostMapping("/bookmark/remove")
  public ResponseEntity<Void> removeBookmark(@RequestParam("bookmarkId") int bookmarkId) {
    userService.removeUserBookmark(bookmarkId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
