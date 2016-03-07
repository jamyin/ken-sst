package com.tianfang.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.common.model.PageQuery;
import com.tianfang.user.dto.UserApplyTeamDto;

public interface UserApplyTeamExMapper {
    
	int countByExample(@Param("example")UserApplyTeamDto example);

    List<UserApplyTeamDto> selectByExample(@Param("example")UserApplyTeamDto example, @Param("page") PageQuery page);
}