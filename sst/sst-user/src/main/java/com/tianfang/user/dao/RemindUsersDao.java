package com.tianfang.user.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.user.dto.RemindUsersDto;
import com.tianfang.user.mapper.RemindUsersMapper;
import com.tianfang.user.pojo.RemindUsers;

@Repository
public class RemindUsersDao extends MyBatisBaseDao<RemindUsers>{

	@Getter
	@Autowired
	private RemindUsersMapper mapper;

	public List<RemindUsersDto> findRemindUsersByParam(RemindUsersDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

}
