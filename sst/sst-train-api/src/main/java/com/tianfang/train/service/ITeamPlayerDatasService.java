package com.tianfang.train.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.train.dto.TeamPlayerDatasDto;

public interface ITeamPlayerDatasService {
	
	String save(TeamPlayerDatasDto dto);
	
	void del(String id);
	
	void update(TeamPlayerDatasDto dto);
	
	TeamPlayerDatasDto getTeamPlayerDatasById(String id);
	
	List<TeamPlayerDatasDto> findTeamPlayerDatasByParam(TeamPlayerDatasDto dto);
	
	PageResult<TeamPlayerDatasDto> findTeamPlayerDatasByParam(TeamPlayerDatasDto dto, PageQuery query);
	
	/**
	 * 分页查询赛事下球队数据
	 * @param compId
	 * @param query
	 * @param order 排序规则
	 * @return
	 * @author xiang_wang
	 * 2016年2月2日下午4:42:34
	 */
	PageResult<TeamPlayerDatasDto> findTeamPlayerDatasByCompId(String compId, PageQuery query, String order);
}