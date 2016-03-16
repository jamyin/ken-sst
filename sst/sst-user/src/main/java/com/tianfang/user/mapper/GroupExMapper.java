package com.tianfang.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.user.dto.GroupDto;

public interface GroupExMapper {

	List<GroupDto> findGroupsByUserId(@Param("userId")String userId);

}
