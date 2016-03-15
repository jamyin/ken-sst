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
import com.tianfang.user.dto.GroupDto;
import com.tianfang.user.mapper.GroupExMapper;
import com.tianfang.user.mapper.GroupMapper;
import com.tianfang.user.pojo.Group;
import com.tianfang.user.pojo.GroupExample;

@Repository
public class GroupDao extends MyBatisBaseDao<Group> {
	
	@Autowired
	@Getter
	private GroupMapper mapper;

	@Autowired
	@Getter
	private GroupExMapper mapperex;
	
	public List<GroupDto> findGroupByParam(GroupDto dto){
		return findGroupByParam(dto, null);
	}
	
	public List<GroupDto> findGroupByParam(GroupDto dto, PageQuery query) {
		GroupExample example = new GroupExample();
		GroupExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
        if(null != query){
        	example.setOrderByClause("create_time desc limit "+query.getStartNum() +"," + query.getPageSize());
		}
        List<Group> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, GroupDto.class);
	}
	
	public int countGroupByParam(GroupDto dto){
		GroupExample example = new GroupExample();
		GroupExample.Criteria criteria = example.createCriteria();
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
	private void assemblyParams(GroupDto params, GroupExample.Criteria criteria) {
		if (null != params) {
        	if (StringUtils.isNotBlank(params.getId())){
        		criteria.andIdEqualTo(params.getId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getName())){
        		criteria.andNameLike("%"+params.getName().trim()+"%");
        	}
        	if (StringUtils.isNotBlank(params.getCreateUserId())){
        		criteria.andCreateUserIdEqualTo(params.getCreateUserId());
        	}
        }
		criteria.andStatEqualTo(DataStatus.ENABLED);
	}

	public List<GroupDto> findGroupsByUserId(String userId) {
		// TODO Auto-generated method stub
		return mapperex.findGroupsByUserId(userId);
	}
}
