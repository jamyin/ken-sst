package com.tianfang.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.common.model.PageQuery;
import com.tianfang.user.dto.UserInfoDto;


public interface UserInfoExMapper {

	List<UserInfoDto> findUserInfoViewByPage(@Param("userInfoDto")UserInfoDto userInfoDto,
			 @Param("page")PageQuery query);

	int selectAccount(@Param("userInfoDto")UserInfoDto userInfoDto); 
	
}