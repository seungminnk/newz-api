package com.newz.api.user.controller;

import com.newz.api.common.exception.ErrorResponse;
import com.newz.api.news.keywordnews.model.NewsListResponse;
import com.newz.api.user.model.BookmarkAddRequest;
import com.newz.api.user.model.BookmarkNewsListResponse;
import com.newz.api.user.model.UserInformationResponse;
import com.newz.api.user.model.UserKeywordSetRequest;
import com.newz.api.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

  private UserService userService;

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
  public ResponseEntity<UserInformationResponse> getUserInformation(@RequestParam("userId") int userId) {
    return new ResponseEntity<>(userService.getUserInformationByUserId(userId), HttpStatus.OK);
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
  public ResponseEntity<List<String>> getUserKeywordList(@RequestParam("userId") int userId) {
    return new ResponseEntity<>(userService.getUserKeywordsByUserId(userId), HttpStatus.OK);
  }

  @Operation(summary = "키워드 등록하기")
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
  @PostMapping("/keyword")
  public ResponseEntity<List<Map<String, Object>>> setUserKeyword(@RequestBody @Valid UserKeywordSetRequest request)
      throws Exception {
    userService.setUserKeyword(request);
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
      @RequestParam("userId") int userId,
      @RequestParam(value = "page", required = false, defaultValue = "1") int page,
      @RequestParam(value = "limit", required = false, defaultValue = "3") int limit) {
    return new ResponseEntity<>(userService.getUserBookmarkNewsByUserId(userId, page, limit), HttpStatus.OK);
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
  public ResponseEntity<Void> addBookmark(@RequestBody @Valid BookmarkAddRequest request) {
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
  @PatchMapping("/bookmark/remove")
  public ResponseEntity<Void> removeBookmark(@RequestParam("bookmarkId") int bookmarkId) {
    userService.removeUserBookmark(bookmarkId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
