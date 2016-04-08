package com.tianfang.train.mapper;

import com.tianfang.common.model.PageQuery;
import com.tianfang.train.dto.CompetitionApplyDto;
import com.tianfang.train.dto.CompetitionDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CompetitionApplyExMapper {
	List<CompetitionApplyDto> selectByExample(@Param("competitionApplyDto") CompetitionApplyDto competitionApplyDto,@Param("page") PageQuery page);
	
	int countByExample(@Param("competitionNoticeDto") CompetitionApplyDto competitionApplyDto);

	List<CompetitionDto> findCompApplyByParams(@Param("params") CompetitionApplyDto params);
}