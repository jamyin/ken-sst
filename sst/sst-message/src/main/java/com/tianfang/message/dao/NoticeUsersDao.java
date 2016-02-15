package com.tianfang.message.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.message.dto.NoticeUsersDto;
import com.tianfang.message.mapper.NoticeUsersExMapper;
import com.tianfang.message.mapper.NoticeUsersMapper;
import com.tianfang.message.pojo.NoticeUsers;

@Repository
public class NoticeUsersDao extends MyBatisBaseDao<NoticeUsers> {

	@Getter
	@Autowired
	private NoticeUsersMapper mapper;
	
	@Getter
	@Autowired
	private NoticeUsersExMapper mappers;

	/**
	 * @author YIn
	 * @time:2016年2月3日 下午5:27:04
	 */
	public int releaseNotice(List<NoticeUsersDto> list) {

		return mappers.releaseNotice(list);
	}

	
}