package com.tianfang.user.mapper;

import java.util.List;

import com.tianfang.user.app.AppUserInfo;
import org.apache.ibatis.annotations.Param;

import com.tianfang.common.model.PageQuery;
import com.tianfang.user.dto.UserApplyTeamDto;

public interface UserApplyTeamExMapper {
    
	int countByExample(@Param("example")UserApplyTeamDto example);

    List<UserApplyTeamDto> selectByExample(@Param("example")UserApplyTeamDto example, @Param("page") PageQuery page);

    List<AppUserInfo> queryUserTeamApplyInfoByParam(@Param("example")UserApplyTeamDto example, @Param("page") PageQuery page);
    
    int countUserTeamApplyInfoByParam(@Param("example")UserApplyTeamDto example);
    
    List<AppUserInfo> queryUserApplyInfoByParam(@Param("example")AppUserInfo example, @Param("page") PageQuery page);

    int countUserApplyInfoByParam(@Param("example")AppUserInfo example);
}