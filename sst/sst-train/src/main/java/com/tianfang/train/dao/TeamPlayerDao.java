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
import com.tianfang.train.dto.TeamPlayerDto;
import com.tianfang.train.mapper.TeamPlayerMapper;
import com.tianfang.train.pojo.TeamPlayer;
import com.tianfang.train.pojo.TeamPlayerExample;
import com.tianfang.train.pojo.TeamPlayerExample.Criteria;

@Repository
public class TeamPlayerDao extends MyBatisBaseDao<TeamPlayer>{

	@Autowired
	@Getter
	private TeamPlayerMapper mapper;
	public List<TeamPlayerDto> findTeamPlayerByParam(TeamPlayerDto dto){
		return findTeamPlayerByParam(dto, null);
	}
	
	public List<TeamPlayerDto> findTeamPlayerByParam(TeamPlayerDto dto, PageQuery query) {
		TeamPlayerExample example = new TeamPlayerExample();
		TeamPlayerExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
        if(null != query){
        	example.setOrderByClause("create_time desc limit "+query.getStartNum() +"," + query.getPageSize());
		}
        List<TeamPlayer> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, TeamPlayerDto.class);
	}
	
	public int countTeamPlayerByParam(TeamPlayerDto dto){
		TeamPlayerExample example = new TeamPlayerExample();
		TeamPlayerExample.Criteria criteria = example.createCriteria();
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
	private void assemblyParams(TeamPlayerDto params, TeamPlayerExample.Criteria criteria) {
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
        	if (null != params.getNum()){
        		criteria.andNumEqualTo(params.getNum().intValue());
        	}
        	if (null != params.getPosition()){
        		criteria.andPositionEqualTo(params.getPosition().intValue());
        	}
        	if (StringUtils.isNotBlank(params.getName())){
        		criteria.andNameLike("%"+params.getName().trim()+"%");
        	}
        }
		criteria.andStatEqualTo(DataStatus.ENABLED);
	}

	/**
	 * 不带分页查询
	 * @author YIn
	 * @time:2016年1月27日 下午1:24:21
	 */
	public List<TeamPlayer> selectByParameter(TeamPlayer teamPlayer) {
		TeamPlayerExample example = new TeamPlayerExample();
		TeamPlayerExample.Criteria criteria = example.createCriteria();
        assemblyParams(teamPlayer, criteria);   //组装参数
        List<TeamPlayer> result = mapper.selectByExample(example);  
        return result;
	}

	/**
	 * 组装参数
	 * @author YIn
	 * @time:2016年1月27日 下午1:27:43
	 */
	private void assemblyParams(TeamPlayer teamPlayer, Criteria criteria) {
		if (StringUtils.isNotBlank(teamPlayer.getId())){
    		criteria.andIdEqualTo(teamPlayer.getId());
    	}
		if (StringUtils.isNotBlank(teamPlayer.getTeamId())){
			criteria.andTeamIdEqualTo(teamPlayer.getTeamId());
		}
		if (StringUtils.isNotBlank(teamPlayer.getName())){
			criteria.andNameLike("%"+teamPlayer.getName()+"%");
		}
    	criteria.andStatEqualTo(DataStatus.ENABLED);
	}

	/**
	 * 分页查询赛事公告
	 * @author YIn
	 * @time:2016年1月27日 下午2:43:29
	 */
	public List<TeamPlayer> findTeamPlayerViewByPage(TeamPlayer teamPlayer, PageQuery page) {
		TeamPlayerExample example = new TeamPlayerExample();
		TeamPlayerExample.Criteria criteria = example.createCriteria();
		example.setOrderByClause(" create_time DESC limit " + page.getStartNum() +"," + page.getPageSize());
        assemblyParams(teamPlayer, criteria);   //组装参数
        List<TeamPlayer> result = mapper.selectByExample(example);  
        return result;
	}

	/**
	 * 查询总条数
	 * @author YIn
	 * @time:2016年1月27日 下午2:43:59
	 */
	public int selectAccount(TeamPlayer teamPlayer) {
		TeamPlayerExample example = new TeamPlayerExample();
		TeamPlayerExample.Criteria criteria = example.createCriteria();
        assemblyParams(teamPlayer, criteria);   //组装参数
        return mapper.countByExample(example);
	}
	
}
