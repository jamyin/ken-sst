package com.tianfang.train.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.common.model.PageQuery;
import com.tianfang.train.dto.CompetitionNoticeDto;


public interface CompetitionNoticeExMapper {
	List<CompetitionNoticeDto> selectByExample(@Param("competitionNoticeDto") CompetitionNoticeDto competitionNoticeDto,@Param("page") PageQuery page);
	
	int countByExample(@Param("competitionNoticeDto") CompetitionNoticeDto competitionNoticeDto);
} 