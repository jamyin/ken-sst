package com.tianfang.train.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.train.dao.CompetitionTeamDao;
import com.tianfang.train.dto.CompetitionMatchDto;
import com.tianfang.train.dto.CompetitionTeamDto;
import com.tianfang.train.enums.TeamType;
import com.tianfang.train.pojo.CompetitionTeam;

@Service
public class CompetitionTeamServiceImpl implements ICompetitionTeamService {

	@Autowired
	private CompetitionTeamDao cteamDao;
	
	@Override
	public String save(CompetitionTeamDto dto) {
		checkObjIsNull(dto);
		CompetitionTeam obj = BeanUtils.createBeanByTarget(dto, CompetitionTeam.class);
		String id = UUIDGenerator.getUUID();
		obj.setId(id);
		cteamDao.insertSelective(obj);
		return id;
	}

	@Override
	public void del(String id) {
		checkIdIsNull(id);
		CompetitionTeam obj = cteamDao.selectByPrimaryKey(id);
		checkObjNotExist(obj);
		if (null != obj.getStat() && obj.getStat() == DataStatus.DISABLED){
			return;
		}
		obj.setStat(DataStatus.DISABLED);
		cteamDao.updateByPrimaryKeySelective(obj);
	}

	@Override
	public void update(CompetitionTeamDto dto) {
		checkObjIsNull(dto);
		checkIdIsNull(dto.getId());
		CompetitionTeam obj = BeanUtils.createBeanByTarget(dto, CompetitionTeam.class);
		cteamDao.updateByPrimaryKeySelective(obj);
	}

	@Override
	public CompetitionTeamDto checkTeam(String compId, String teamId) {
		checkCompIdIsNull(compId);
		checkTeamIdIsNull(teamId);
		CompetitionTeamDto dto = new CompetitionTeamDto();
		dto.setCompId(compId);
		dto.setTeamId(teamId);
		List<CompetitionTeamDto> cteam = findCompetitionTeamByParam(dto);
		if (null != cteam && cteam.size() > 0){
			return cteam.get(0);
		}
		return null;
	}
	
	@Override
	public CompetitionTeamDto getCompetitionTeamById(String id) {
		checkIdIsNull(id);
		CompetitionTeamDto dto = new CompetitionTeamDto();
		dto.setId(id);
		List<CompetitionTeamDto> objs = cteamDao.findCompetitionTeamByParam(dto);
		if (null != objs){
			return objs.get(0);
		}
		return null;
	}
	
	@Override
	public List<CompetitionTeamDto> findCompetitionTeamByCompId(String compId) {
		checkCompIdIsNull(compId);
		CompetitionTeamDto dto = new CompetitionTeamDto();
		dto.setCompId(compId);
		return findCompetitionTeamByParam(dto);
	}

	@Override
	public List<CompetitionTeamDto> findCompetitionTeamByParam(CompetitionTeamDto dto) {
		List<CompetitionTeamDto> list = cteamDao.findCompetitionTeamByParam(dto);
		if (null != list && list.size() > 0){
			return BeanUtils.createBeanListByTarget(list, CompetitionTeamDto.class);
		}
		return null;
	}

	@Override
	public PageResult<CompetitionTeamDto> findCompetitionTeamByParam(CompetitionTeamDto dto,
			PageQuery query) {
		int total = cteamDao.countCompetitionTeamByParam(dto);
		if (total > 0){
			query.setTotal(total);
			dto.setOrderByClause("(a.win*3+a.draw) DESC,(a.win-a.lose) DESC limit "+query.getStartNum() +"," + query.getPageSize());
			List<CompetitionTeamDto> results = cteamDao.findCompetitionTeamByParam(dto);
			return new PageResult<CompetitionTeamDto>(query, results);
		}
		return null;
	}
	
	@Override
	@Transactional
	public void updateTeamDatas(CompetitionTeamDto cTeam, List<CompetitionMatchDto> matchs) {
		checkObjIsNull(cTeam);
		checkIdIsNull(cTeam.getId());
		if (null != matchs && matchs.size() > 0){
			int win = 0;
			int draw = 0;
			int lose = 0;
			int goalIn = 0;
			int goalOut = 0;
			for (CompetitionMatchDto match : matchs){
				if (match.getStat().intValue() == DataStatus.DISABLED || match.getMatchType().intValue() != TeamType.OVER.getIndex()){
					continue;
				}
				if (cTeam.getTeamId().equals(match.getHomeTeamId())){
					if (match.getHomeScore() > match.getVisitingScore()){
						win++;
					}else if (match.getHomeScore() < match.getVisitingScore()){
						lose++;
					}else{
						draw++;
					}
					goalIn += match.getHomeScore();
					goalOut += match.getVisitingScore();
				}else if (cTeam.getTeamId().equals(match.getVisitingTeamId())){
					if (match.getHomeScore() > match.getVisitingScore()){
						lose++;
					}else if (match.getHomeScore() < match.getVisitingScore()){
						win++;
					}else{
						draw++;
					}
					goalOut += match.getHomeScore();
					goalIn += match.getVisitingScore();
				}
			}
			cTeam.setWin(win);
			cTeam.setLose(lose);
			cTeam.setDraw(draw);
			cTeam.setGoalIn(goalIn);
			cTeam.setGoalOut(goalOut);
			
			CompetitionTeam obj = BeanUtils.createBeanByTarget(cTeam, CompetitionTeam.class);
			cteamDao.updateByPrimaryKeySelective(obj);
		}
	}

	private void checkObjIsNull(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,赛事球队对象为空!");
		}
	}
	
	private void checkIdIsNull(String id) {
		if (StringUtils.isBlank(id)){
			throw new RuntimeException("对不起,赛事球队对象主键ID为空!");
		}
	}
	
	private void checkObjNotExist(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,赛事球队对象不存在!");
		}
	}
	
	private void checkCompIdIsNull(String compId) {
		if (StringUtils.isBlank(compId)){
			throw new RuntimeException("对不起,赛事ID为空!");
		}
	}
	
	private void checkTeamIdIsNull(String teamId) {
		if (StringUtils.isBlank(teamId)){
			throw new RuntimeException("对不起,球队ID为空!");
		}
	}

	@Override
	public List<CompetitionTeamDto> selectCompeTeamList(String userId) {
		// TODO Auto-generated method stub
		return cteamDao.selectCompeTeamList(userId);
	}
}