package com.tianfang.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.user.dto.GroupUserDto;
import com.tianfang.user.dto.UserDto;

public interface GroupUserExMapper {
	
    List<UserDto> findUsersByGroupId(@Param("groupId")String groupId);

    /**
     * 批量保存用户群组对应关系
     * @param gus
     */
    void insertBatchGroupUser(List<GroupUserDto> gus);
}