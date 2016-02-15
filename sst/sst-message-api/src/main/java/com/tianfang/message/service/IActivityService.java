package com.tianfang.message.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.message.dto.ActivityDto;

public interface IActivityService {

	String save(ActivityDto dto);
	
	void del(String id);
	
	void update(ActivityDto dto);
	
	ActivityDto getActivityById(String id);
	
	List<ActivityDto> findActivityByParam(ActivityDto dto);
	
	PageResult<ActivityDto> findActivityByParam(ActivityDto dto, PageQuery query);
}