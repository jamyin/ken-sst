package com.tianfang.user.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.user.dto.UserApplyTeamDto;

public interface IUserApplyTeamService {

	String save(UserApplyTeamDto dto) throws Exception;
	
	void del(String id) throws Exception;
	
	void update(UserApplyTeamDto dto) throws Exception;
	
	UserApplyTeamDto getUserApplyTeamById(String id) throws Exception;
	
	List<UserApplyTeamDto> findUserApplyTeamByParam(UserApplyTeamDto dto) throws Exception;
	
	PageResult<UserApplyTeamDto> findUserApplyTeamByParam(UserApplyTeamDto dto, PageQuery query) throws Exception;
}
