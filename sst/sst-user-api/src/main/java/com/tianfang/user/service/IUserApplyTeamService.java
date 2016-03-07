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
	
	/**
	 * 自定义sql查询(联表查询)
	 * @param dto
	 * @param query
	 * @return
	 * @author xiang_wang
	 * 2016年3月7日上午10:19:33
	 */
	PageResult<UserApplyTeamDto> findUserApplyTeamExByParam(UserApplyTeamDto dto, PageQuery query);
}