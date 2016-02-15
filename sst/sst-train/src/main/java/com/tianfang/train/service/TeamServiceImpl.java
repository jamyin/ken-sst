package com.tianfang.train.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.train.dao.TeamDao;
import com.tianfang.train.dto.TeamDto;
import com.tianfang.train.pojo.Team;

@Service
public class TeamServiceImpl implements ITeamService{

	@Autowired
	private TeamDao teamDao;
	
	@Override
	public List<TeamDto> findAll(){
		return teamDao.findAll();
	}
	
	@Override
	public List<TeamDto> findAllByCompId(String compId){
		checkCompIdIsNull(compId);
		List<Team> teams = teamDao.findAllByCompId(compId);
		if (null != teams && teams.size() > 0){
			return BeanUtils.createBeanListByTarget(teams, TeamDto.class);
		}
		return null;
	}
	
	private void checkCompIdIsNull(String compId) {
		if (StringUtils.isBlank(compId)){
			throw new RuntimeException("对不起,赛事id为空!");
		}
	}

	/**
	 * @author YIn
	 * @time:2016年1月15日 上午11:20:18
	 */
	@Override
	public int addTeam(TeamDto teamDto) {
		Team team = BeanUtils.createBeanByTarget(teamDto, Team.class);
		return teamDao.insertSelective(team);
	}

	/**
	 * 根据主键Id更新
	 * @author YIn
	 * @time:2016年1月15日 上午11:28:38
	 */
	@Override
	public int updateTeam(TeamDto teamDto) {
		Team team = BeanUtils.createBeanByTarget(teamDto, Team.class);
		return teamDao.updateByPrimaryKeySelective(team);
	}

	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月15日 上午11:51:34
	 */
	@Override
	public int delTeam(TeamDto teamDto) {
		Team team = BeanUtils.createBeanByTarget(teamDto, Team.class);
		return teamDao.deleteByPrimaryKey(team.getId());
	}
	
	/**
	 * 批量逻辑删除
	 * @author YIn
	 * @time:2016年1月15日 下午6:12:19
	 */
	@Override
	public Integer delByIds(String ids) {
		  String[] idArr = ids.split(",");
	        for (String id : idArr) {
	        	Team team = teamDao.selectByPrimaryKey(id);
	            if (null == team) {
	                return 0;//无此条记录
	            }
	            team.setStat(DataStatus.DISABLED);
	            teamDao.updateByPrimaryKeySelective(team);
	        }
	        return 1;
	}
	
	/**
	 * @author YIn
	 * @time:2016年1月15日 下午1:17:12
	 */
	@Override
	public List<TeamDto> findTeam(TeamDto teamDto) {
		Team team = BeanUtils.createBeanByTarget(teamDto, Team.class);
		List<Team> list = teamDao.selectByParameter(team);
		List<TeamDto> dtoList = BeanUtils.createBeanListByTarget(list, TeamDto.class);
		return dtoList;
	}

	/**
	 * @author YIn
	 * @time:2016年1月15日 下午2:18:10
	 */
	@Override
	public PageResult<TeamDto> findTeamViewByPage(TeamDto teamDto , PageQuery page) {
		Team team = BeanUtils.createBeanByTarget(teamDto, Team.class);
		List<Team> list = teamDao.findTeamViewByPage(team,page);
		int total = teamDao.selectAccount(team);
		page.setTotal(total);
		List<TeamDto> dtoList = BeanUtils.createBeanListByTarget(list, TeamDto.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		for(TeamDto dto : dtoList){
			if(dto.getCreateTime() != null){
			dto.setCreateTimeStr(sdf.format(dto.getCreateTime()));}
			if(dto.getLastUpdateTime() != null){
			dto.setLastUpdateTimeStr(sdf.format(dto.getLastUpdateTime()));
			}
		}
		return new PageResult<TeamDto>(page, dtoList);
	}

	@Override
	public TeamDto getTeamById(String id) {
		checkIdIsNull(id);
		Team t = teamDao.selectByPrimaryKey(id);
		if (null != t && t.getStat() == DataStatus.ENABLED){
			return BeanUtils.createBeanByTarget(t, TeamDto.class);
		}
		return null;
	}

	private void checkIdIsNull(String id) {
		if (StringUtils.isBlank(id)){
			throw new RuntimeException("对不起,球队的主键ID为空!");
		}
	}
}