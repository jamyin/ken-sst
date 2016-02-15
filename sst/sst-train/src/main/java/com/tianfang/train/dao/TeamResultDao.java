package com.tianfang.train.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.dto.TeamResultDto;
import com.tianfang.train.mapper.TeamResultMapper;
import com.tianfang.train.pojo.TeamResult;
import com.tianfang.train.pojo.TeamResultExample;

@Repository
public class TeamResultDao extends MyBatisBaseDao<TeamResult>{

	@Autowired
	@Getter
	private TeamResultMapper mapper;
	
	public List<TeamResultDto> findTeamResultByParam(TeamResultDto dto){
		return findTeamResultByParam(dto, null);
	}
	
	public List<TeamResultDto> findTeamResultByParam(TeamResultDto dto, PageQuery query) {
		TeamResultExample example = new TeamResultExample();
		TeamResultExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
        if(null != query){
        	example.setOrderByClause("create_time desc limit "+query.getStartNum() +"," + query.getPageSize());
		}
        List<TeamResult> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, TeamResultDto.class);
	}
	
	public int countTeamResultByParam(TeamResultDto dto){
		TeamResultExample example = new TeamResultExample();
		TeamResultExample.Criteria criteria = example.createCriteria();
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
	private void assemblyParams(TeamResultDto params, TeamResultExample.Criteria criteria) {
		if (null != params) {
        	if (StringUtils.isNotBlank(params.getId())){
        		criteria.andIdEqualTo(params.getId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getTeamId())){
        		criteria.andTeamIdEqualTo(params.getTeamId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getTeamName())){
        		criteria.andTeamNameLike("%"+params.getTeamName().trim()+"%");
        	}
        	if (StringUtils.isNotBlank(params.getGameName())){
        		criteria.andGameNameLike("%"+params.getGameName().trim()+"%");
        	}
        	if (StringUtils.isNotBlank(params.getYear())){
        		criteria.andYearEqualTo(params.getYear().trim());
        	}
        }
		criteria.andStatEqualTo(DataStatus.ENABLED);
	}
}
