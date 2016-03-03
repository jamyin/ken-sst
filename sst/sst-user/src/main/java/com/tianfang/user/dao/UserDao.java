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
import com.tianfang.user.app.FriendApp;
import com.tianfang.user.dto.UserDto;
import com.tianfang.user.mapper.UserExMapper;
import com.tianfang.user.mapper.UserMapper;
import com.tianfang.user.pojo.User;
import com.tianfang.user.pojo.UserExample;

@Repository
public class UserDao extends MyBatisBaseDao<User> {

	@Autowired
	@Getter
	private UserMapper mapper;
	
	@Autowired
	private UserExMapper uExMapper;

	public List<UserDto> findUserByParam(UserDto dto){
		return findUserByParam(dto, null);
	}

	public UserDto getUserByMobile(String mobile){
		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
		criteria.andMobileEqualTo(mobile);
        List<User> results = mapper.selectByExample(example); 
        if (null != results && results.size() > 0){
        	return BeanUtils.createBeanByTarget(results.get(0), UserDto.class);
        }
		return null;
	}
	
	public List<UserDto> findUserByParam(UserDto dto, PageQuery query) {
		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
        if(null != query){
        	example.setOrderByClause("create_time desc limit "+query.getStartNum() +"," + query.getPageSize());
		}
        List<User> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, UserDto.class);
	}
	
	public int countUserByParam(UserDto dto){
		UserExample example = new UserExample();
		UserExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
		return mapper.countByExample(example);
	}
	
	public List<FriendApp> findFriendsByUserId(String userId){
		return uExMapper.findFriendsByUserId(userId);
	}
	
	public List<FriendApp> findCareFriends(String userId){
		return uExMapper.findCareFriends(userId);
	}
	
	/**
	 * 组装查询参数
	 * @param params
	 * @param criteria
	 * @author xiang_wang
	 * 2016年1月12日下午4:51:12
	 */
	private void assemblyParams(UserDto params, UserExample.Criteria criteria) {
		if (null != params) {
        	if (StringUtils.isNotBlank(params.getId())){
        		criteria.andIdEqualTo(params.getId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getNickName())){
        		criteria.andNickNameLike("%"+params.getNickName().trim()+"%");
        	}
        	if (StringUtils.isNotBlank(params.getTeamId())){
        		criteria.andTeamIdEqualTo(params.getTeamId().trim());
        	}
        	if (null != params.getGender()){
        		criteria.andGenderEqualTo(params.getGender().intValue());
        	}
        	if (StringUtils.isNotBlank(params.getBirthday())){
        		criteria.andBirthdayEqualTo(params.getBirthday().trim());
        	}
        	if (StringUtils.isNotBlank(params.getMobile())){
        		criteria.andMobileEqualTo(params.getMobile().trim());
        	}
        	if (null != params.getUtype()){
        		criteria.andUtypeEqualTo(params.getUtype().intValue());
        	}
        	if (StringUtils.isNotBlank(params.getPassword())){
        		criteria.andPasswordEqualTo(params.getPassword());
        	}
        }
		criteria.andStatEqualTo(DataStatus.ENABLED);
	}
}