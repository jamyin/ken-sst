package com.tianfang.train.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.common.model.PageQuery;
import com.tianfang.train.dto.CompetitionMatchDto;
import com.tianfang.train.pojo.CompetitionMatch;

public interface CompetitionMatchExMapper {

	void insertBatch(List<CompetitionMatch> matchs);
	
	List<CompetitionMatch> selectCompetitionMatchByTeamId(@Param("teamId") String teamId,@Param("page") PageQuery page);
	
	int countCompetitionMatchByTeamId(@Param("teamId") String teamId);
}