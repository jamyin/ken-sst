package com.tianfang.user.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.user.dto.UserFriendDto;

public interface IUserFriendService {
	String save(UserFriendDto dto) throws Exception;
	
	void del(String id) throws Exception;
	
	void update(UserFriendDto dto) throws Exception;
	
	UserFriendDto getUserFriendById(String id) throws Exception;
	
	List<UserFriendDto> findUserFriendByParam(UserFriendDto dto) throws Exception;
	
	PageResult<UserFriendDto> findUserFriendByParam(UserFriendDto dto, PageQuery query) throws Exception;
}