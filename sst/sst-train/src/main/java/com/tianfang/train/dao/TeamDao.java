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
import com.tianfang.train.dto.TeamDto;
import com.tianfang.train.mapper.TeamExMapper;
import com.tianfang.train.mapper.TeamMapper;
import com.tianfang.train.pojo.Team;
import com.tianfang.train.pojo.TeamExample;
import com.tianfang.train.pojo.TeamExample.Criteria;

@Repository
public class TeamDao extends MyBatisBaseDao<Team>{
	
	@Autowired
	@Getter
	private TeamMapper mapper;
	@Autowired
	private TeamExMapper exMapper;
	
	public List<TeamDto> findAll(){
		TeamExample example = new TeamExample();
		TeamExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
        List<Team> results = mapper.selectByExample(example);        
		return BeanUtils.createBeanListByTarget(results, TeamDto.class);
	}
	
	/**
	 * 不带分页查询
	 * @author YIn
	 * @time:2016年1月23日 下午1:24:21
	 */
	public List<Team> selectByParameter(Team team) {
		TeamExample example = new TeamExample();
		TeamExample.Criteria criteria = example.createCriteria();
        assemblyParams(team, criteria);   //组装参数
        List<Team> result = mapper.selectByExample(example);  
        return result;
	}

	/**
	 * 组装参数
	 * @author YIn
	 * @time:2016年1月23日 下午1:27:43
	 */
	private void assemblyParams(Team team, Criteria criteria) {
		if (StringUtils.isNotBlank(team.getId())){
    		criteria.andIdEqualTo(team.getId());
    	}
		if (StringUtils.isNotBlank(team.getName())){
    		criteria.andNameLike("%"+team.getName()+"%");
    	}
		if (StringUtils.isNotBlank(team.getProvince())){
			criteria.andProvinceEqualTo(team.getProvince());
		}
		if (StringUtils.isNotBlank(team.getCity())){
			criteria.andCityEqualTo(team.getCity());
		}
		if (StringUtils.isNotBlank(team.getArea())){
			criteria.andAreaEqualTo(team.getArea());
		}
		if (StringUtils.isNotBlank(team.getContacts())){
			criteria.andContactsLike("%"+team.getContacts().trim()+"%");
		}
		if (StringUtils.isNotBlank(team.getMobile())){
			criteria.andMobileLike("%"+team.getMobile().trim()+"%");
		}
    	criteria.andStatEqualTo(DataStatus.ENABLED);
	}

	/**
	 * 分页查询球队
	 * @author YIn
	 * @time:2016年1月23日 下午2:43:29
	 */
	public List<Team> findTeamViewByPage(Team team, PageQuery page) {
		TeamExample example = new TeamExample();
		TeamExample.Criteria criteria = example.createCriteria();
		example.setOrderByClause(" create_time DESC limit " + page.getStartNum() +"," + page.getPageSize());
        assemblyParams(team, criteria);   //组装参数
        List<Team> result = mapper.selectByExample(example);  
        return result;
	}

	/**
	 * 查询总条数
	 * @author YIn
	 * @time:2016年1月23日 下午2:43:59
	 */
	public int selectAccount(Team team) {
		TeamExample example = new TeamExample();
		TeamExample.Criteria criteria = example.createCriteria();
        assemblyParams(team, criteria);   //组装参数
        return mapper.countByExample(example);
	}
	
	/**
	 * 根据ids查询球队
	 * @param ids
	 * @return
	 * @author xiang_wang
	 * 2016年1月25日上午10:59:05
	 */
	public List<Team> findTeamByIds(List<String> ids){
		TeamExample example = new TeamExample();
		TeamExample.Criteria criteria = example.createCriteria();
		criteria.andIdIn(ids);
		example.setOrderByClause(" create_time DESC");
		criteria.andStatEqualTo(DataStatus.ENABLED);
        List<Team> result = mapper.selectByExample(example);  
        return result;
	}

	public List<Team> findAllByCompId(String compId) {
		return exMapper.findAllByCompId(compId);
	}
}