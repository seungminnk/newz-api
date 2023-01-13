package com.newz.api.keyword.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KeywordRepository {

  List<String> getFixedKeywords();

}
