package com.tianfang.train.dao;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.dto.TeamPlayerDto;
import com.tianfang.train.mapper.TeamPlayerExMapper;
import com.tianfang.train.mapper.TeamPlayerMapper;
import com.tianfang.train.pojo.TeamPlayer;
import com.tianfang.train.pojo.TeamPlayerExample;
import com.tianfang.train.pojo.TeamPlayerExample.Criteria;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeamPlayerDao extends MyBatisBaseDao<TeamPlayer>{

	@Autowired
	@Getter
	private TeamPlayerMapper mapper;
	@Autowired
	private TeamPlayerExMapper exMapper;

	public List<TeamPlayerDto> findTeamPlayerByParam(TeamPlayerDto dto){
		return findTeamPlayerByParam(dto, null);
	}
	
	public List<TeamPlayerDto> findTeamPlayerByParam(TeamPlayerDto dto, PageQuery query) {
		return exMapper.findTeamPlayerByParam(dto, query);
	}
	
	public int countTeamPlayerByParam(TeamPlayerDto dto){
		return exMapper.countTeamPlayerByParam(dto);
	}
	
	public List<TeamPlayerDto> findTeamPlayerByTeamPlayer(TeamPlayerDto dto, PageQuery query) {
		return exMapper.findTeamPlayerByTeamPlayer(dto, query);
	}
	
	public int countTeamPlayerByTeamPlayer(TeamPlayerDto dto) {
		return exMapper.countTeamPlayerByTeamPlayer(dto);
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
