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
import com.tianfang.user.dto.GroupUserDto;
import com.tianfang.user.dto.UserDto;
import com.tianfang.user.mapper.GroupUserExMapper;
import com.tianfang.user.mapper.GroupUserMapper;
import com.tianfang.user.pojo.GroupUser;
import com.tianfang.user.pojo.GroupUserExample;

@Repository
public class GroupUserDao extends MyBatisBaseDao<GroupUser> {

	@Getter
	@Autowired
	private GroupUserMapper mapper;
	@Autowired
	private GroupUserExMapper exMapper;
	
	public List<GroupUserDto> findGroupUserByParam(GroupUserDto dto){
		return findGroupUserByParam(dto, null);
	}
	
	public List<GroupUserDto> findGroupUserByParam(GroupUserDto dto, PageQuery query) {
		GroupUserExample example = new GroupUserExample();
		GroupUserExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
        if(null != query){
        	example.setOrderByClause("create_time desc limit "+query.getStartNum() +"," + query.getPageSize());
		}
        List<GroupUser> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, GroupUserDto.class);
	}
	
	public int countGroupUserByParam(GroupUserDto dto){
		GroupUserExample example = new GroupUserExample();
		GroupUserExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
		return mapper.countByExample(example);
	}

	public void insertBatchGroupUser(List<GroupUserDto> gus){
		exMapper.insertBatchGroupUser(gus);
	}
	
	public List<UserDto> findUsersByGroupId(String groupId){
		return exMapper.findUsersByGroupId(groupId);
	}
	
	/**
	 * 组装查询参数
	 * @param params
	 * @param criteria
	 * @author xiang_wang
	 * 2016年1月12日下午4:51:12
	 */
	private void assemblyParams(GroupUserDto params, GroupUserExample.Criteria criteria) {
		if (null != params) {
        	if (StringUtils.isNotBlank(params.getId())){
        		criteria.andIdEqualTo(params.getId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getUserId())){
        		criteria.andUserIdEqualTo(params.getUserId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getGroupId())){
        		criteria.andGroupIdEqualTo(params.getGroupId().trim());
        	}
        }
		criteria.andStatEqualTo(DataStatus.ENABLED);
	}
}