package com.tianfang.message.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.StringUtils;
import com.tianfang.message.dto.NoticeDto;
import com.tianfang.message.dto.NoticeUsersDto;
import com.tianfang.message.mapper.NoticeUsersExMapper;
import com.tianfang.message.mapper.NoticeUsersMapper;
import com.tianfang.message.pojo.NoticeUsers;
import com.tianfang.message.pojo.NoticeUsersExample;

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

	public NoticeDto getLast(String userId){
		return mappers.getLast(userId);
	}

	/**
	 * @author YIn
	 * @time:2016年3月28日 上午11:09:01
	 */
	public int findRead(String id) {
		NoticeUsersExample example = new NoticeUsersExample();
		NoticeUsersExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotEmpty(id)){
    		criteria.andIdEqualTo(id);
    	}
		criteria.andReadstatEqualTo(DataStatus.ENABLED); //已读
		criteria.andStatEqualTo(DataStatus.ENABLED); 
        int reads = mapper.countByExample(example);
        return reads;
	}
}