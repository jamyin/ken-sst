package com.tianfang.train.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.common.model.PageQuery;
import com.tianfang.train.dto.CompetitionNewsDto;


public interface CompetitionNewsExMapper {
	List<CompetitionNewsDto> selectByExample(@Param("competitionNewsDto") CompetitionNewsDto competitionNewsDto,@Param("page") PageQuery page);
	
	int countByExample(@Param("competitionNewsDto") CompetitionNewsDto competitionNewsDto);
} 