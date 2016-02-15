package com.tianfang.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.user.dao.UserFriendDao;
import com.tianfang.user.dto.UserFriendDto;
import com.tianfang.user.pojo.UserFriend;

@Service
public class UserFriendServiceImpl implements IUserFriendService {

	@Autowired
	private UserFriendDao userFriendDao;

	@Override
	public String save(UserFriendDto dto) throws Exception {
		checkObjIsNull(dto);
		UserFriend m = BeanUtils.createBeanByTarget(dto, UserFriend.class);
		String id = UUIDGenerator.getUUID();
		m.setId(id);
		userFriendDao.insertSelective(m);
		return id;
	}

	@Override
	public void del(String id) throws Exception {
		checkIdIsNull(id);
		UserFriend m = userFriendDao.selectByPrimaryKey(id);
		checkObjIsNotExist(m);
		m.setStat(DataStatus.DISABLED);
		userFriendDao.updateByPrimaryKeySelective(m);
	}

	@Override
	public void update(UserFriendDto dto) throws Exception {
		checkObjIsNull(dto);
		checkIdIsNull(dto.getId());
		checkObjIsNotExist(userFriendDao.selectByPrimaryKey(dto.getId()));
		UserFriend m = BeanUtils.createBeanByTarget(dto, UserFriend.class);
		userFriendDao.updateByPrimaryKeySelective(m);
	}

	@Override
	public UserFriendDto getUserFriendById(String id) throws Exception {
		checkIdIsNull(id);
		UserFriend m = userFriendDao.selectByPrimaryKey(id);
		UserFriendDto dto = BeanUtils.createBeanByTarget(m, UserFriendDto.class);
		return dto;
	}

	@Override
	public List<UserFriendDto> findUserFriendByParam(UserFriendDto dto) throws Exception {
		return userFriendDao.findUserFriendByParam(dto);
	}

	@Override
	public PageResult<UserFriendDto> findUserFriendByParam(UserFriendDto dto, PageQuery query)
			throws Exception {
		int total = userFriendDao.countUserFriendByParam(dto);
		if (total > 0){
			query.setTotal(total);
			List<UserFriendDto> results = userFriendDao.findUserFriendByParam(dto, query);
			return new PageResult<UserFriendDto>(query, results);
		}
		
		return null;
	}

	private void checkObjIsNull(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,用户朋友对象为空!");
		}
	}
	
	private void checkIdIsNull(String id) {
		if (StringUtils.isBlank(id)){
			throw new RuntimeException("对不起,用户朋友对象主键ID为空!");
		}
	}
	
	private void checkObjIsNotExist(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,用户朋友对象不存在!");
		}
	}
}