package com.tianfang.user.service;

import java.util.List;

import com.tianfang.user.dto.GroupUserDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.user.dao.GroupDao;
import com.tianfang.user.dao.GroupUserDao;
import com.tianfang.user.dto.GroupDto;
import com.tianfang.user.dto.UserDto;
import com.tianfang.user.pojo.Group;

import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupServiceImpl implements IGroupService {

	@Autowired
	private GroupDao groupDao;
	@Autowired
	private GroupUserDao groupUserDao;

	@Override
	public String save(GroupDto dto) throws Exception {
		checkObjIsNull(dto);
		Group m = BeanUtils.createBeanByTarget(dto, Group.class);
		String id = UUIDGenerator.getUUID();
		m.setId(id);
		groupDao.insertSelective(m);
		return id;
	}

	@Override
	@Transactional
	public String save(GroupDto dto, List<GroupUserDto> gus){
		checkObjIsNull(dto);
		checkObjIsNull(gus);
		Group m = BeanUtils.createBeanByTarget(dto, Group.class);
		groupDao.insertSelective(m);
		groupUserDao.insertBatchGroupUser(gus);

		return dto.getId();
	}

	@Override
	public void del(String id) throws Exception {
		checkIdIsNull(id);
		Group m = groupDao.selectByPrimaryKey(id);
		checkObjIsNotExist(m);
		m.setStat(DataStatus.DISABLED);
		groupDao.updateByPrimaryKeySelective(m);
	}

	@Override
	public void update(GroupDto dto) throws Exception {
		checkObjIsNull(dto);
		checkIdIsNull(dto.getId());
		checkObjIsNotExist(groupDao.selectByPrimaryKey(dto.getId()));
		Group m = BeanUtils.createBeanByTarget(dto, Group.class);
		groupDao.updateByPrimaryKeySelective(m);
	}

	@Override
	public GroupDto getGroupById(String id, Boolean flag) throws Exception {
		checkIdIsNull(id);
		Group m = groupDao.selectByPrimaryKey(id);
		GroupDto dto = BeanUtils.createBeanByTarget(m, GroupDto.class);
		if (null != flag && flag.booleanValue()){
			List<UserDto> users = groupUserDao.findUsersByGroupId(id);
			dto.setUsers(users);
		}
		return dto;
	}

	@Override
	public List<GroupDto> findGroupByParam(GroupDto dto) throws Exception {
		return groupDao.findGroupByParam(dto);
	}

	@Override
	public PageResult<GroupDto> findGroupByParam(GroupDto dto, PageQuery query)
			throws Exception {
		int total = groupDao.countGroupByParam(dto);
		if (total > 0){
			query.setTotal(total);
			List<GroupDto> results = groupDao.findGroupByParam(dto, query);
			return new PageResult<GroupDto>(query, results);
		}
		
		return null;
	}

	@Override
	public List<GroupDto> findGroupByUserId(String userId) throws Exception {
		checkUserIdIsNull(userId);
		GroupDto dto = new GroupDto();
		dto.setCreateUserId(userId);
		return groupDao.findGroupByParam(dto);
	}
	
	private void checkObjIsNull(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,群组对象为空!");
		}
	}
	
	private void checkIdIsNull(String id) {
		if (StringUtils.isBlank(id)){
			throw new RuntimeException("对不起,群组对象主键ID为空!");
		}
	}
	
	private void checkObjIsNotExist(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,群组对象不存在!");
		}
	}
	
	private void checkUserIdIsNull(String userId) {
		if (StringUtils.isBlank(userId)){
			throw new RuntimeException("对不起,群组对象用户ID为空!");
		}
	}

	@Override
	public List<GroupDto> findGroupsByUserId(String userId) throws Exception {
		checkUserIdIsNull(userId);
		return groupDao.findGroupsByUserId(userId);
	}
}