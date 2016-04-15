package com.tianfang.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.common.model.PageQuery;
import com.tianfang.user.dto.RemindDto;
import com.tianfang.user.dto.RemindUsersDto;
import com.tianfang.user.pojo.Remind;

public interface RemindUsersExMapper {

	void insertList(List<RemindUsersDto> list);

	List<Remind> queryRemindList(@Param(value="userId") String userId, @Param(value="page") PageQuery query);

	int countRemindList(@Param(value="userId") String userId);
	
	List<RemindDto> selectByExample(@Param(value="dto") RemindDto dto, @Param(value="page") PageQuery query);
	
	int countByExample(@Param(value="dto") RemindDto dto);
}
