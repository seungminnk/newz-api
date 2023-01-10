package com.newz.api.news.keywordnews.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.newz.api.news.keywordnews.model.NewsListModel;
import com.newz.api.news.keywordnews.model.NewsListResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class KeywordNewsService {

  private final Gson gson = new Gson();
  private final RestTemplate restTemplate = new RestTemplate();

  private static final String NEWS_CRAWLER_API_URL = "http://localhost:5000/news/list";

  public NewsListResponse getNewsListByQuery(String query, int page, int limit) {
    StringBuilder stringBuilder = new StringBuilder(NEWS_CRAWLER_API_URL);
    stringBuilder.append("?query=");
    stringBuilder.append(query);
    stringBuilder.append("&page=");
    stringBuilder.append(page);
    stringBuilder.append("&limit=");
    stringBuilder.append(limit);

    String requestUrl = stringBuilder.toString();

    String response = restTemplate.getForObject(requestUrl, String.class);
    Map<String, Object> parsedData = gson.fromJson(response, new TypeToken<Map<String, Object>>(){}.getType());

    long total = (long) Double.parseDouble(parsedData.get("total").toString());
    List<Map<String, Object>> newsData = (List<Map<String, Object>>) parsedData.get("news");

    List<NewsListModel> newsDataResponse = newsData.stream()
        .map(news -> NewsListModel.builder()
            .title(news.get("title").toString())
            .content(news.get("content").toString())
            .link(news.get("link").toString())
            .build())
        .collect(Collectors.toList());

    return NewsListResponse.builder()
        .news(newsDataResponse)
        .total(total)
        .page(page)
        .build();
  }

}
