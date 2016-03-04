package com.tianfang.user.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.user.dto.UserApplyTeamDto;

public interface IUserApplyTeamService {

	String save(UserApplyTeamDto dto);
	
	void del(String id);
	
	void update(UserApplyTeamDto dto);
	
	UserApplyTeamDto getUserApplyTeamById(String id);
	
	List<UserApplyTeamDto> findUserApplyTeamByParam(UserApplyTeamDto dto);
	
	PageResult<UserApplyTeamDto> findUserApplyTeamByParam(UserApplyTeamDto dto, PageQuery query);
}
