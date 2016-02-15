package com.tianfang.user.mapper;

import org.apache.ibatis.annotations.Param;

import com.tianfang.user.dto.PlanDto;


public interface PlanExMapper {

	void batchUpdate(@Param("params")PlanDto dto);
}