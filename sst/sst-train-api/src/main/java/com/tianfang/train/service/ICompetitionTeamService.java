package com.tianfang.train.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.train.dto.CompetitionMatchDto;
import com.tianfang.train.dto.CompetitionTeamDto;

public interface ICompetitionTeamService {
	
	String save(CompetitionTeamDto dto);
	
	void del(String id);
	
	void update(CompetitionTeamDto dto);
	
	CompetitionTeamDto getCompetitionTeamById(String id);
	
	CompetitionTeamDto checkTeam(String compId, String teamId);
	
	List<CompetitionTeamDto> findCompetitionTeamByCompId(String compId);
	
	List<CompetitionTeamDto> findCompetitionTeamByParam(CompetitionTeamDto dto);
	
	PageResult<CompetitionTeamDto> findCompetitionTeamByParam(CompetitionTeamDto dto, PageQuery query);

	/**
	 * 更新赛事下球队数据
	 * @param cTeam
	 * @param matchs
	 * @author xiang_wang
	 * 2016年2月1日下午1:42:15
	 */
	void updateTeamDatas(CompetitionTeamDto cTeam, List<CompetitionMatchDto> matchs);
}