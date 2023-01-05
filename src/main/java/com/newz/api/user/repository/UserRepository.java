package com.newz.api.user.repository;

import com.newz.api.user.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {

  UserVo getUserInformationByUserId(int userId);

}
