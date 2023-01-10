package com.newz.api.user.repository;

import com.newz.api.user.vo.UserKeywordVo;
import com.newz.api.user.vo.UserVo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {

  UserVo getUserInformationByUserId(int userId);

  int getSavedKeywordTotalCountByUserId(int userId);

  int insertUserKeywords(List<UserKeywordVo> keywords);

}
