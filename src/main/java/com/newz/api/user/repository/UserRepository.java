package com.newz.api.user.repository;

import com.newz.api.user.vo.UserBookmarkVo;
import com.newz.api.user.vo.UserKeywordVo;
import com.newz.api.user.vo.UserVo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {

  UserVo getUserInformationByServiceUniqueId(String serviceType, String serviceUniqueId);

  int getUserKeywordTotalCountByUserId(int userId);

  int saveRefreshToken(int userId, String refreshToken);

  int insertUser(UserVo user);

  UserVo getUserInformationByRefreshToken(String refreshToken);

  UserVo getUserInformationByUserId(int userId);

  List<String> getUserKeywordsByUserId(int userId);

  int getSavedKeywordTotalCountByUserId(int userId);

  int insertUserKeywords(List<UserKeywordVo> keywords);

  int deleteUserKeywords(int userId, List<String> keywords);

  int getUSerBookmarkTotalCountByUserId(int userId);

  List<UserBookmarkVo> getUserBookmarkNewsByUserId(int userId, int offset, int limit);

  int insertUserBookmark(UserBookmarkVo bookmark);

  int deleteUserBookmarkByBookmarkId(int bookmarkId);

}
