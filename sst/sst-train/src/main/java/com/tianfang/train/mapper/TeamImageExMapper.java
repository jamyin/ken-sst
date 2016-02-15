package com.tianfang.train.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.common.model.PageQuery;
import com.tianfang.train.dto.TeamImageDto;


public interface TeamImageExMapper {
	List<TeamImageDto> selectByExample(@Param("TeamImageDto") TeamImageDto teamImageDto,@Param("page") PageQuery page);
	
	int countByExample(@Param("TeamImageDto") TeamImageDto teamImageDto);

	Integer addTeamImageBatch(List<TeamImageDto> list);
} 