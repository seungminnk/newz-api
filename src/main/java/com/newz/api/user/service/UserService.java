package com.newz.api.user.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newz.api.common.auth.JwtUtil;
import com.newz.api.common.auth.NewzUser;
import com.newz.api.common.exception.ErrorCode;
import com.newz.api.common.exception.NewzCommonException;
import com.newz.api.user.model.BookmarkAddRequest;
import com.newz.api.user.model.BookmarkNewsListModel;
import com.newz.api.user.model.BookmarkNewsListResponse;
import com.newz.api.user.model.BookmarkRemoveRequest;
import com.newz.api.user.model.LoginRequest;
import com.newz.api.user.model.TokenResponse;
import com.newz.api.user.model.UserInformationResponse;
import com.newz.api.user.model.UserKeywordRemoveRequest;
import com.newz.api.user.model.UserKeywordSetRequest;
import com.newz.api.user.repository.UserRepository;
import com.newz.api.user.vo.UserBookmarkVo;
import com.newz.api.user.vo.UserKeywordVo;
import com.newz.api.user.vo.UserVo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class UserService {

  private UserRepository userRepository;

  private final Gson gson = new Gson();
  private final RestTemplate restTemplate = new RestTemplate();

  private static final String NEWS_API_URL = "http://localhost:5000/news/data";

  public boolean isValidUser(int userId) {
    return userRepository.getUserInformationByUserId(userId) != null;
  }

  @Transactional
  public UserInformationResponse login(LoginRequest request) throws Exception {
    // TODO: 소셜로그인 유효성 체크

    UserVo user = userRepository.getUserInformationByServiceUniqueId(
        request.getServiceType().getServiceType(), request.getServiceUniqueId());

    if(user != null) {
      int keywordTotalCount = userRepository.getUserKeywordTotalCountByUserId(user.getId());

      TokenResponse authTokens = this.generateAuthTokens(
          NewzUser.builder()
              .id(user.getId())
              .build()
      );

      userRepository.saveRefreshToken(user.getId(), authTokens.getRefreshToken());

      return UserInformationResponse.builder()
          .id(user.getId())
          .name(user.getName())
          .email(user.getEmail())
          .haveKeywords(keywordTotalCount > 0)
          .tokens(authTokens)
          .build();
    }

    UserVo newUser = UserVo.builder()
        .socialServiceType(request.getServiceType().getServiceType())
        .socialServiceUniqueId(request.getServiceUniqueId())
        .name(request.getName())
        .email(request.getEmail())
        .build();

    userRepository.insertUser(newUser);

    TokenResponse authTokens = this.generateAuthTokens(
        NewzUser.builder()
            .id(newUser.getId())
            .build()
    );

    userRepository.saveRefreshToken(newUser.getId(), authTokens.getRefreshToken());

    return UserInformationResponse.builder()
        .id(newUser.getId())
        .name(newUser.getName())
        .email(newUser.getEmail())
        .haveKeywords(false)
        .tokens(authTokens)
        .build();
  }

  private TokenResponse generateAuthTokens(NewzUser user) throws Exception {
    return TokenResponse.builder()
        .accessToken(JwtUtil.generateAccessToken(user))
        .refreshToken(JwtUtil.generateRefreshToken())
        .build();
  }

  @Transactional
  public TokenResponse reissueTokensByRefreshToken(String refreshToken) throws Exception {
    JwtUtil.validateToken(refreshToken);

    UserVo user = userRepository.getUserInformationByRefreshToken(refreshToken);
    if(user == null) {
      throw new NewzCommonException(HttpStatus.NOT_FOUND, ErrorCode.USER_NOT_FOUND);
    }

    TokenResponse authTokens = this.generateAuthTokens(NewzUser.builder()
        .id(user.getId())
        .build());

    userRepository.saveRefreshToken(user.getId(), authTokens.getRefreshToken());

    return authTokens;
  }

  public UserInformationResponse getUserInformationByUserId(int userId) {
    UserVo user = userRepository.getUserInformationByUserId(userId);

    if(user == null || StringUtils.isBlank(user.getName())) {
      throw new NewzCommonException(HttpStatus.NOT_FOUND, ErrorCode.USER_NOT_FOUND);
    }

    return UserInformationResponse.builder()
        .id(user.getId())
        .name(user.getName())
        .email(user.getEmail())
        .haveKeywords(user.isHaveKeywords())
        .build();
  }

  public List<String> getUserKeywordsByUserId(int userId) {
    return userRepository.getUserKeywordsByUserId(userId);
  }

  public void addUserKeyword(UserKeywordSetRequest request) {
    int savedKeywordTotalCount = userRepository.getSavedKeywordTotalCountByUserId(request.getUserId());
    if(savedKeywordTotalCount == 9 || savedKeywordTotalCount + request.getKeywords().size() > 9) {
      throw new NewzCommonException(
          HttpStatus.CONFLICT,
          ErrorCode.ONLY_CAN_SAVE_UP_TO_NINE_KEYWORD.getCode(),
          "키워드는 총 9개까지 저장 가능합니다. "
              + "현재 저장된 키워드는 " + savedKeywordTotalCount+ "개, "
              + "저장하려는 키워드는 " + request.getKeywords().size() + "개 입니다.");
    }

    List<UserKeywordVo> keywords = request.getKeywords().stream()
        .map(keyword -> UserKeywordVo.builder()
            .userId(request.getUserId())
            .keyword(keyword)
            .build())
        .collect(Collectors.toList());

    userRepository.insertUserKeywords(keywords);
  }

  public void removeUserKeyword(UserKeywordRemoveRequest request) {
    if(request.getKeywords().isEmpty()) {
      return;
    }

    userRepository.deleteUserKeywords(request.getUserId(), request.getKeywords());
  }

  public BookmarkNewsListResponse getUserBookmarkNewsByUserId(int userId, int page, int limit) {
    if(page < 1) {
      page = 1;
    }

    int totalBookmarkCount = userRepository.getUSerBookmarkTotalCountByUserId(userId);
    if(totalBookmarkCount == 0) {
      return BookmarkNewsListResponse.builder()
          .total(totalBookmarkCount)
          .page(page)
          .news(Collections.emptyList())
          .build();
    }

    int offset = page != 0 ? (page-1) * limit : 0;
    List<UserBookmarkVo> savedBookmarkNews = userRepository.getUserBookmarkNewsByUserId(userId, offset, limit);

    if(savedBookmarkNews.isEmpty()) {
      return BookmarkNewsListResponse.builder()
          .total(totalBookmarkCount)
          .page(page)
          .news(Collections.emptyList())
          .build();
    }

    List<BookmarkNewsListModel> bookmarkNewsList = new ArrayList<>();
    List<String> bookmarkNewsUrls = new ArrayList<>();
    for(UserBookmarkVo bookmark : savedBookmarkNews) {
      BookmarkNewsListModel bookmarkRes = BookmarkNewsListModel.builder()
          .bookmarkId(bookmark.getId())
          .link(bookmark.getNewsUrl())
          .build();

      bookmarkNewsUrls.add(bookmark.getNewsUrl());
      bookmarkNewsList.add(bookmarkRes);
    }

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    Map<String, Object> fields = new HashMap<>();
    fields.put("newsUrls", bookmarkNewsUrls);

    String response = restTemplate.postForObject(NEWS_API_URL, fields, String.class);
    Map<String, Object> parsedData = gson.fromJson(response, new TypeToken<Map<String, Object>>(){}.getType());

    List<Map<String, Object>> newsData = (List<Map<String, Object>>) parsedData.get("news");

    for(int idx = 0; idx < bookmarkNewsList.size(); idx++) {
      bookmarkNewsList.get(idx).setTitle(newsData.get(idx).get("title").toString());
      bookmarkNewsList.get(idx).setContent(newsData.get(idx).get("content").toString());
    }

    return BookmarkNewsListResponse.builder()
        .total(totalBookmarkCount)
        .page(page)
        .news(bookmarkNewsList)
        .build();
  }

  public void addUserBookmark(BookmarkAddRequest request) {
    UserBookmarkVo bookmark = UserBookmarkVo.builder()
        .userId(request.getUserId())
        .newsUrl(request.getNewsUrl())
        .build();

    userRepository.insertUserBookmark(bookmark);
  }

  public void removeUserBookmark(BookmarkRemoveRequest request) {
    UserBookmarkVo bookmark = UserBookmarkVo.builder()
        .userId(request.getUserId())
        .newsUrl(request.getNewsUrl())
        .build();

    userRepository.deleteUserBookmarkByBookmarkId(bookmark);
  }

}
