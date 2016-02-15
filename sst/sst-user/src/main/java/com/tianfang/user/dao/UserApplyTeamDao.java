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
import com.tianfang.user.dto.UserApplyTeamDto;
import com.tianfang.user.mapper.UserApplyTeamMapper;
import com.tianfang.user.pojo.UserApplyTeam;
import com.tianfang.user.pojo.UserApplyTeamExample;

@Repository
public class UserApplyTeamDao extends MyBatisBaseDao<UserApplyTeam>{
	
	@Getter
	@Autowired
	private UserApplyTeamMapper mapper;

	public List<UserApplyTeamDto> findUserApplyTeamByParam(UserApplyTeamDto dto){
		return findUserApplyTeamByParam(dto, null);
	}
	
	public List<UserApplyTeamDto> findUserApplyTeamByParam(UserApplyTeamDto dto, PageQuery query) {
		UserApplyTeamExample example = new UserApplyTeamExample();
		UserApplyTeamExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
        if(null != query){
        	example.setOrderByClause("create_time desc limit "+query.getStartNum() +"," + query.getPageSize());
		}
        List<UserApplyTeam> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, UserApplyTeamDto.class);
	}
	
	public int countUserApplyTeamByParam(UserApplyTeamDto dto){
		UserApplyTeamExample example = new UserApplyTeamExample();
		UserApplyTeamExample.Criteria criteria = example.createCriteria();
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
	private void assemblyParams(UserApplyTeamDto params, UserApplyTeamExample.Criteria criteria) {
		if (null != params) {
        	if (StringUtils.isNotBlank(params.getId())){
        		criteria.andIdEqualTo(params.getId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getUserId())){
        		criteria.andUserIdEqualTo(params.getUserId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getTeamId())){
        		criteria.andTeamIdEqualTo(params.getTeamId().trim());
        	}
        	if (null != params.getStatus()){
        		criteria.andStatusEqualTo(params.getStatus().intValue());
        	}
        }
		criteria.andStatEqualTo(DataStatus.ENABLED);
	}
}