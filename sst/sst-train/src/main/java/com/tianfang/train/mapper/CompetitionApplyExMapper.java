package com.tianfang.train.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.common.model.PageQuery;
import com.tianfang.train.dto.CompetitionApplyDto;


public interface CompetitionApplyExMapper {
	List<CompetitionApplyDto> selectByExample(@Param("competitionApplyDto") CompetitionApplyDto competitionApplyDto,@Param("page") PageQuery page);
	
	int countByExample(@Param("competitionNoticeDto") CompetitionApplyDto competitionApplyDto);
} 