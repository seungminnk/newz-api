package com.newz.api.user.service;

import com.newz.api.user.repository.UserRepository;
import com.newz.api.user.vo.UserVo;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

  private UserRepository userRepository;

  public Map<String, Object> getUserInformationByUserId(int userId) {
    UserVo user = userRepository.getUserInformationByUserId(userId);

    Map<String, Object> result = new HashMap<>();
    result.put("id", user.getId());
    result.put("name", user.getName());
    result.put("email", user.getEmail());

    return result;
  }

}
