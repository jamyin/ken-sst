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
import com.tianfang.user.dto.RemindDto;
import com.tianfang.user.mapper.RemindMapper;
import com.tianfang.user.mapper.RemindUsersExMapper;
import com.tianfang.user.mapper.RemindUsersMapper;
import com.tianfang.user.pojo.Remind;
import com.tianfang.user.pojo.RemindExample;

@Repository
public class RemindDao extends MyBatisBaseDao<Remind> {
	
	@Getter
	@Autowired
	private RemindMapper mapper;

	@Getter
	@Autowired
	private RemindUsersExMapper mappers;
	
	public List<RemindDto> findRemindByParam(RemindDto dto){
		return findRemindByParam(dto, null);
	}
	
	public List<RemindDto> findRemindByParam(RemindDto dto, PageQuery query) {
		RemindExample example = new RemindExample();
		RemindExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
        if(null != query){
        	example.setOrderByClause("create_time desc limit "+query.getStartNum() +"," + query.getPageSize());
		}
        List<Remind> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, RemindDto.class);
	}
	
	public int countRemindByParam(RemindDto dto){
		RemindExample example = new RemindExample();
		RemindExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
		return mapper.countByExample(example);
	}
	
	public List<RemindDto> findRemindByParam(String userId, PageQuery query) {
		List<Remind> remindList = mappers.queryRemindList(userId,query);
		return BeanUtils.createBeanListByTarget(remindList, RemindDto.class);
	}	
	
	public int countRemindByParam(String userId){
		return mappers.countRemindList(userId);
	}
	
	public List<RemindDto> selectByExample(RemindDto dto, PageQuery query) {
		return mappers.selectByExample(dto, query);
	}
	
	public int countByExample (RemindDto dto) {
		return mappers.countByExample(dto);
	}
	
	/**
	 * 组装查询参数
	 * @param params
	 * @param criteria
	 * @author xiang_wang
	 * 2016年1月12日下午4:51:12
	 */
	private void assemblyParams(RemindDto params, RemindExample.Criteria criteria) {
		if (null != params) {
        	if (StringUtils.isNotBlank(params.getId())){
        		criteria.andIdEqualTo(params.getId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getUserId())){
        		criteria.andUserIdEqualTo(params.getUserId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getToUserId())){
        		criteria.andToUserIdEqualTo(params.getToUserId().trim());
        	}
        	if (null != params.getType()){
        		criteria.andTypeEqualTo(params.getType().intValue());
        	}
        	if (StringUtils.isNotBlank(params.getUserName())){
        		criteria.andUserNameLike("%"+params.getUserName()+"%");
        	}
        	if (StringUtils.isNotBlank(params.getToUserName())){
        		criteria.andToUserNameLike("%"+params.getToUserName()+"%");
        	}
        }
		criteria.andStatEqualTo(DataStatus.ENABLED);
	}
}