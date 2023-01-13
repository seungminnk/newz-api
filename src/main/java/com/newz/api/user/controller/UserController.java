package com.newz.api.user.controller;

import com.newz.api.user.model.BookmarkAddRequest;
import com.newz.api.user.model.BookmarkNewsListResponse;
import com.newz.api.user.model.UserInformationResponse;
import com.newz.api.user.model.UserKeywordSetRequest;
import com.newz.api.user.service.UserService;
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

  @GetMapping("")
  public ResponseEntity<UserInformationResponse> getUserInformation(@RequestParam("userId") int userId) {
    return new ResponseEntity<>(userService.getUserInformationByUserId(userId), HttpStatus.OK);
  }

  @GetMapping("/keyword/list")
  public ResponseEntity<List<String>> getUserKeywordList(@RequestParam("userId") int userId) {
    return new ResponseEntity<>(userService.getUserKeywordsByUserId(userId), HttpStatus.OK);
  }

  @PostMapping("/keyword")
  public ResponseEntity<List<Map<String, Object>>> setUserKeyword(@RequestBody @Valid UserKeywordSetRequest request)
      throws Exception {
    userService.setUserKeyword(request);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/bookmark/news")
  public ResponseEntity<BookmarkNewsListResponse> getUserBookmarkNews(
      @RequestParam("userId") int userId,
      @RequestParam(value = "page", required = false, defaultValue = "1") int page,
      @RequestParam(value = "limit", required = false, defaultValue = "3") int limit) {
    return new ResponseEntity<>(userService.getUserBookmarkNewsByUserId(userId, page, limit), HttpStatus.OK);
  }

  @PostMapping("/bookmark/add")
  public ResponseEntity<Void> addBookmark(@RequestBody @Valid BookmarkAddRequest request) {
    userService.addUserBookmark(request);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PatchMapping("/bookmark/remove")
  public ResponseEntity<Void> removeBookmark(@RequestParam("bookmarkId") int bookmarkId) {
    userService.removeUserBookmark(bookmarkId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
