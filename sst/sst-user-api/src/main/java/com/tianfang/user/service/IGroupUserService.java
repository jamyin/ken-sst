package com.tianfang.user.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.user.dto.GroupUserDto;

public interface IGroupUserService {
	
	String save(GroupUserDto dto) throws Exception;
	
	void del(String id) throws Exception;
	
	void update(GroupUserDto dto) throws Exception;
	
	GroupUserDto getGroupUserById(String id) throws Exception;
	
	List<GroupUserDto> findGroupUserByParam(GroupUserDto dto) throws Exception;
	
	PageResult<GroupUserDto> findGroupUserByParam(GroupUserDto dto, PageQuery query) throws Exception;
}