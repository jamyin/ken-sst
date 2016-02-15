package com.tianfang.user.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.user.dto.UserFriendDto;
import com.tianfang.user.mapper.UserFriendMapper;
import com.tianfang.user.pojo.UserFriend;
import com.tianfang.user.pojo.UserFriendExample;

@Repository
public class UserFriendDao extends MyBatisBaseDao<UserFriend> {
	
	@Getter
	@Autowired
	private UserFriendMapper mapper;
	
	public List<UserFriendDto> findUserFriendByParam(UserFriendDto dto){
		return findUserFriendByParam(dto, null);
	}
	
	public List<UserFriendDto> findUserFriendByParam(UserFriendDto dto, PageQuery query) {
		UserFriendExample example = new UserFriendExample();
		UserFriendExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
        if(null != query){
        	example.setOrderByClause("create_time desc limit "+query.getStartNum() +"," + query.getPageSize());
		}
        List<UserFriend> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, UserFriendDto.class);
	}
	
	public int countUserFriendByParam(UserFriendDto dto){
		UserFriendExample example = new UserFriendExample();
		UserFriendExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
		return mapper.countByExample(example);
	}
	
	/**
	 * 组装查询参数
	 * @param params
	 * @param criteria
	 * @author xiang_wang
	 * 2016年1月12日下午4:51:12
	 */
	private void assemblyParams(UserFriendDto params, UserFriendExample.Criteria criteria) {
		if (null != params) {
        	if (StringUtils.isNotBlank(params.getId())){
        		criteria.andIdEqualTo(params.getId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getUserId())){
        		criteria.andUserIdEqualTo(params.getUserId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getFriendId())){
        		criteria.andFriendIdEqualTo(params.getFriendId().trim());
        	}
        }
		criteria.andStatEqualTo(DataStatus.ENABLED);
	}

}