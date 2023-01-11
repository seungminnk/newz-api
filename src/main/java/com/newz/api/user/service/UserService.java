package com.newz.api.user.service;

import com.newz.api.common.exception.NewzCommonException;
import com.newz.api.user.model.UserKeywordSetRequest;
import com.newz.api.user.repository.UserRepository;
import com.newz.api.user.vo.UserKeywordVo;
import com.newz.api.user.vo.UserVo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

  private UserRepository userRepository;

  public Map<String, Object> getUserInformationByUserId(int userId) {
    UserVo user = userRepository.getUserInformationByUserId(userId);

    if(user == null) {
      return new HashMap<>();
    }

    Map<String, Object> result = new HashMap<>();
    result.put("id", user.getId());
    result.put("name", user.getName());
    result.put("email", user.getEmail());

    return result;
  }

  public void setUserKeyword(UserKeywordSetRequest request) throws Exception {
    int savedKeywordTotalCount = userRepository.getSavedKeywordTotalCountByUserId(request.getUserId());
    if(savedKeywordTotalCount == 9 || savedKeywordTotalCount + request.getKeywords().size() > 9) {
      throw new NewzCommonException(
          HttpStatus.CONFLICT,
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

}
