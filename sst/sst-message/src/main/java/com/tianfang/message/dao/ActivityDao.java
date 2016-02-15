package com.tianfang.message.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.message.dto.ActivityDto;
import com.tianfang.message.mapper.ActivityMapper;
import com.tianfang.message.pojo.Activity;
import com.tianfang.message.pojo.ActivityExample;

@Repository
public class ActivityDao extends MyBatisBaseDao<Activity>{

	@Autowired
	@Getter
	private ActivityMapper mapper;
	
	public List<ActivityDto> findActivityByParam(ActivityDto dto){
		return findActivityByParam(dto, null);
	}
	
	public List<ActivityDto> findActivityByParam(ActivityDto dto, PageQuery query) {
		ActivityExample example = new ActivityExample();
		ActivityExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
        if(null != query){
        	example.setOrderByClause("create_time desc limit "+query.getStartNum() +"," + query.getPageSize());
		}
        List<Activity> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, ActivityDto.class);
	}
	
	public int countActivityByParam(ActivityDto dto){
		ActivityExample example = new ActivityExample();
		ActivityExample.Criteria criteria = example.createCriteria();
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
	private void assemblyParams(ActivityDto params, ActivityExample.Criteria criteria) {
		if (null != params) {
        	if (StringUtils.isNotBlank(params.getId())){
        		criteria.andIdEqualTo(params.getId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getTitle())){
        		criteria.andTitleLike("%"+params.getTitle().trim()+"%");
        	}
        	if (null != params.getStatus()){
        		criteria.andStatusEqualTo(params.getStatus().intValue());
        	}
        	if (StringUtils.isNotBlank(params.getAddress())){
        		criteria.andAddressLike("%"+params.getAddress().trim()+"%");
        	}
        	if (StringUtils.isNotBlank(params.getActivityDate())){
        		criteria.andActivityDateEqualTo(params.getActivityDate().trim());
        	}
        }
		criteria.andStatEqualTo(DataStatus.ENABLED);
	}
}
