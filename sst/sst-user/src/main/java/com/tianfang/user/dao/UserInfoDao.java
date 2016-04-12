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
import com.tianfang.user.dto.UserInfoDto;
import com.tianfang.user.mapper.UserInfoExMapper;
import com.tianfang.user.mapper.UserInfoMapper;
import com.tianfang.user.pojo.UserInfo;
import com.tianfang.user.pojo.UserInfoExample;
import com.tianfang.user.pojo.UserInfoExample.Criteria;

@Repository
public class UserInfoDao extends MyBatisBaseDao<UserInfo> {

	@Autowired
	@Getter
	private UserInfoMapper mapper;
	
	@Autowired
	@Getter
	private UserInfoExMapper mappers;

	public List<UserInfoDto> findUserInfoViewByPage(UserInfoDto userInfoDto,
			PageQuery page) {
		return mappers.findUserInfoViewByPage(userInfoDto,page);
	}

	public int selectAccount(UserInfoDto userInfoDto) {
		return mappers.selectAccount(userInfoDto);
	}

	public List<UserInfoDto> findUserInfo(UserInfoDto userInfoDto) {
		UserInfoExample example = new UserInfoExample();
		UserInfoExample.Criteria criteria = example.createCriteria();
        assemblyParams(userInfoDto, criteria);
        example.setOrderByClause("create_time desc");
        List<UserInfo> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, UserInfoDto.class);
	}

	private void assemblyParams(UserInfoDto userInfoDto, Criteria criteria) {
		if (null != userInfoDto) {
        	if (StringUtils.isNotEmpty(userInfoDto.getId())){
        		criteria.andIdEqualTo(userInfoDto.getId().trim());
        	}		
        	if (StringUtils.isNotEmpty(userInfoDto.getUserId())){
        		criteria.andUserIdEqualTo(userInfoDto.getUserId().trim());
        	}		
		}
		criteria.andStatEqualTo(DataStatus.ENABLED);
	}

}