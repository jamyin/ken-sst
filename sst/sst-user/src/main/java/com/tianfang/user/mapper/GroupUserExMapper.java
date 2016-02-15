package com.tianfang.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.user.dto.UserDto;

public interface GroupUserExMapper {
	
    List<UserDto> findUsersByGroupId(@Param("groupId")String groupId);
}