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
import com.tianfang.user.dao.GroupUserDao;
import com.tianfang.user.dto.GroupUserDto;
import com.tianfang.user.pojo.GroupUser;

@Service
public class GroupUserServiceImpl implements IGroupUserService {

	@Autowired
	private GroupUserDao groupUserDao;

	@Override
	public String save(GroupUserDto dto) throws Exception {
		checkObjIsNull(dto);
		GroupUser m = BeanUtils.createBeanByTarget(dto, GroupUser.class);
		String id = UUIDGenerator.getUUID();
		m.setId(id);
		groupUserDao.insertSelective(m);
		return id;
	}

	@Override
	public void del(String id) throws Exception {
		checkIdIsNull(id);
		GroupUser m = groupUserDao.selectByPrimaryKey(id);
		checkObjIsNotExist(m);
		m.setStat(DataStatus.DISABLED);
		groupUserDao.updateByPrimaryKeySelective(m);
	}

	@Override
	public void update(GroupUserDto dto) throws Exception {
		checkObjIsNull(dto);
		checkIdIsNull(dto.getId());
		checkObjIsNotExist(groupUserDao.selectByPrimaryKey(dto.getId()));
		GroupUser m = BeanUtils.createBeanByTarget(dto, GroupUser.class);
		groupUserDao.updateByPrimaryKeySelective(m);
	}

	@Override
	public GroupUserDto getGroupUserById(String id) throws Exception {
		checkIdIsNull(id);
		GroupUser m = groupUserDao.selectByPrimaryKey(id);
		GroupUserDto dto = BeanUtils.createBeanByTarget(m, GroupUserDto.class);
		return dto;
	}

	@Override
	public List<GroupUserDto> findGroupUserByParam(GroupUserDto dto) throws Exception {
		return groupUserDao.findGroupUserByParam(dto);
	}

	@Override
	public PageResult<GroupUserDto> findGroupUserByParam(GroupUserDto dto, PageQuery query)
			throws Exception {
		int total = groupUserDao.countGroupUserByParam(dto);
		if (total > 0){
			query.setTotal(total);
			List<GroupUserDto> results = groupUserDao.findGroupUserByParam(dto, query);
			return new PageResult<GroupUserDto>(query, results);
		}
		
		return null;
	}

	private void checkObjIsNull(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,群组用户对象为空!");
		}
	}
	
	private void checkIdIsNull(String id) {
		if (StringUtils.isBlank(id)){
			throw new RuntimeException("对不起,群组用户对象主键ID为空!");
		}
	}
	
	private void checkObjIsNotExist(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,群组用户对象不存在!");
		}
	}
}
