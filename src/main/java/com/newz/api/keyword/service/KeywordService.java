package com.newz.api.keyword.service;

import com.newz.api.keyword.repository.KeywordRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KeywordService {

  private KeywordRepository keywordRepository;

  public List<String> getFixedKeywords() {
    return keywordRepository.getFixedKeywords();
  }

}
