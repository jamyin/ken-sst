package com.tianfang.train.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.train.dto.TeamResultDto;

public interface ITeamResultService {
	
	String save(TeamResultDto dto);
	
	void del(String id);
	
	void update(TeamResultDto dto);
	
	TeamResultDto getTeamResultById(String id);
	
	List<TeamResultDto> findTeamResultByParam(TeamResultDto dto);
	
	PageResult<TeamResultDto> findTeamResultByParam(TeamResultDto dto, PageQuery query);
}