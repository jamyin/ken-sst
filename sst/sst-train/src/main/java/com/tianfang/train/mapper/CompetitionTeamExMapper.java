package com.tianfang.train.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.train.dto.CompetitionTeamDto;

public interface CompetitionTeamExMapper {
	
	List<CompetitionTeamDto> findCompetitionTeamByParam(@Param("dto")CompetitionTeamDto dto);
	
	int countCompetitionTeamByParam(@Param("dto")CompetitionTeamDto dto);
}