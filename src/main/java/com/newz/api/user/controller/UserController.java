package com.newz.api.user.controller;

import com.newz.api.user.model.BookmarkAddRequest;
import com.newz.api.user.service.UserService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

  private UserService userService;

  @GetMapping("")
  public ResponseEntity<Map<String, Object>> getUserInformation(@RequestParam("id") int id) {
    Map<String, Object> result = new HashMap<>();
    result.put("id", id);
    result.put("name", "뉴모아");

    return new ResponseEntity<>(userService.getUserInformationByUserId(id), HttpStatus.OK);
  }

  @GetMapping("/keyword/list")
  public ResponseEntity<List<String>> getUserKeywordList(@RequestParam("id") int id) {
    List<String> result = new ArrayList<>();
    result.add("플러터");
    result.add("구글");
    result.add("경제");
    result.add("테슬라");

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @PostMapping("/keyword")
  public ResponseEntity<List<Map<String, Object>>> setUserKeyword(@RequestBody List<String> keywords) {
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/bookmark/list")
  public ResponseEntity<List<Map<String, Object>>> getUserBookmarkList(@RequestParam("id") int id) {
    List<Map<String, Object>> result = new ArrayList<>();

    Map<String, Object> item1 = new HashMap<>();
    item1.put("bookmarkId", 1);
    item1.put("title", "커피빈, 제품 가격 인상");
    item1.put("content", "커피 전문점 '커피빈'은 3일부터 우유가 포함된 음료의 가격을 200원씩 인상하며 카페라떼(s)를 기존 5600원에서 5800원에, 바닐라라떼(s)는 6100원에서 6300원에 구매할 수 있다.");
    item1.put("link", "https://n.news.naver.com/mnews/article/055/0001025795?sid=102");
    result.add(item1);

    Map<String, Object> item2 = new HashMap<>();
    item2.put("bookmarkId", 2);
    item2.put("title", "\"편의점도 모닝 세트 판다\"…CU, GET 커피 세트 할인 행사");
    item2.put("content", "CU가 새해 리오프닝을 맞아 아침밥 시장을 공략하기 위해 GET 커피를 중심으로 모닝 세트 할인 등 다양한 행사를 펼친다고 2일 밝혔다.");
    item2.put("link", "https://n.news.naver.com/mnews/article/032/0003197157?sid=102");
    result.add(item2);

    Map<String, Object> item3 = new HashMap<>();
    item3.put("bookmarkId", 3);
    item3.put("title", "대전신세계, 엑스포타워 스페셜티 커피 전문점 '폴 바셋' 오픈");
    item3.put("content", "3일 대전신세계는 대전 최고층 건물에서 즐기는 시원한 전망과 정통 이탈리안 피자 등을 함께 즐길 수 있는 스페셜티 커피 전문점 '폴 바셋' 대전엑스포 스카이점이 오픈한다고 밝혔다.");
    item3.put("link", "https://n.news.naver.com/mnews/article/082/0001192142?sid=102");
    result.add(item3);

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @PostMapping("/bookmark")
  public ResponseEntity<Void> addBookmark(@RequestBody BookmarkAddRequest request) {
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

}