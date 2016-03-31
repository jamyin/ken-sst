package com.tianfang.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.user.dao.RemindUsersDao;
import com.tianfang.user.dto.RemindUsersDto;
import com.tianfang.user.pojo.RemindUsers;

@Service
public class RemindUsersServiceImpl implements IRemindUsersService {
	
	@Autowired
	private RemindUsersDao remindUsersDao;

	@Override
	public String save(RemindUsersDto dto) throws Exception {
		RemindUsers m = BeanUtils.createBeanByTarget(dto, RemindUsers.class);
		String id = UUIDGenerator.getUUID();
		m.setId(id);
		remindUsersDao.insertSelective(m);
		return id;
	}

	@Override
	public List<RemindUsersDto> findRemindUsersByParam(RemindUsersDto dto) throws Exception {
		return remindUsersDao.findRemindUsersByParam(dto);
	}

	@Override
	public void insertList(List<RemindUsersDto> list) {
		// TODO Auto-generated method stub
		remindUsersDao.insertList(list);
	}

	
}