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
import com.tianfang.train.dto.TeamPlayerDatasDto;
import com.tianfang.train.mapper.TeamPlayerDatasMapper;
import com.tianfang.train.pojo.TeamPlayerDatas;
import com.tianfang.train.pojo.TeamPlayerDatasExample;

@Repository
public class TeamPlayerDatasDao extends MyBatisBaseDao<TeamPlayerDatas>{

	@Autowired
	@Getter
	private TeamPlayerDatasMapper mapper;
	
	public List<TeamPlayerDatasDto> findTeamPlayerDatasByParam(TeamPlayerDatasDto dto){
		return findTeamPlayerDatasByParam(dto, null);
	}
	
	public List<TeamPlayerDatasDto> findTeamPlayerDatasByParam(TeamPlayerDatasDto dto, PageQuery query) {
		TeamPlayerDatasExample example = new TeamPlayerDatasExample();
		TeamPlayerDatasExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
        if(null != query){
        	example.setOrderByClause("create_time desc limit "+query.getStartNum() +"," + query.getPageSize());
		}else{
			example.setOrderByClause("create_time desc");
		}
        List<TeamPlayerDatas> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, TeamPlayerDatasDto.class);
	}
	
	public List<TeamPlayerDatasDto> findTeamPlayerDatasByParam(TeamPlayerDatasDto dto, PageQuery query, String order) {
		TeamPlayerDatasExample example = new TeamPlayerDatasExample();
		TeamPlayerDatasExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
        if(null != query){
        	if (StringUtils.isNotBlank(order)){
        		example.setOrderByClause(order+" DESC limit "+query.getStartNum() +"," + query.getPageSize());
			}else{
				example.setOrderByClause("create_time DESC limit "+query.getStartNum() +"," + query.getPageSize());
			}
		}else{
			if (StringUtils.isNotBlank(order)){
				example.setOrderByClause(order + " DESC");
			}else{
				example.setOrderByClause("create_time DESC");
			}
		}
        List<TeamPlayerDatas> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, TeamPlayerDatasDto.class);
	}
	
	public int countTeamPlayerDatasByParam(TeamPlayerDatasDto dto){
		TeamPlayerDatasExample example = new TeamPlayerDatasExample();
		TeamPlayerDatasExample.Criteria criteria = example.createCriteria();
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
	private void assemblyParams(TeamPlayerDatasDto params, TeamPlayerDatasExample.Criteria criteria) {
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
        	if (StringUtils.isNotBlank(params.getCompId())){
        		criteria.andCompIdEqualTo(params.getCompId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getCompName())){
        		criteria.andCompNameLike("%"+params.getCompName().trim()+"%");
        	}
        	if (StringUtils.isNotBlank(params.getPlayerId())){
        		criteria.andPlayerIdEqualTo(params.getPlayerId().trim());
        	}
        	if (StringUtils.isNotBlank(params.getPlayerName())){
        		criteria.andPlayerNameLike("%"+params.getPlayerName().trim()+"%");
        	}
        }
		criteria.andStatEqualTo(DataStatus.ENABLED);
	}
}
