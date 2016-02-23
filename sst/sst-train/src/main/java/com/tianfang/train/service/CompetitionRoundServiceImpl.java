package com.tianfang.train.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.train.dao.CompetitionDao;
import com.tianfang.train.dao.CompetitionMatchDao;
import com.tianfang.train.dao.CompetitionRoundDao;
import com.tianfang.train.dao.TeamDao;
import com.tianfang.train.dto.CompetitionRoundDto;
import com.tianfang.train.pojo.Competition;
import com.tianfang.train.pojo.CompetitionMatch;
import com.tianfang.train.pojo.CompetitionRound;
import com.tianfang.train.pojo.Team;

@Service
public class CompetitionRoundServiceImpl implements ICompetitionRoundService {
	private static final String MSG_OBJ_NULL = "对不起,赛事场次对象为空!";
	private static final String MSG_OBJ_NOTEXIST = "对不起,赛事场次对象不存在!";
	private static final String MSG_ID_NULL = "对不起,赛事场次对象主键ID为空!";
	private static final String MSG_COMPID_NULL = "对不起,赛事场次对象赛事ID为空!";
	private static final String MSG_COMP_NOTEXIST = "对不起,赛事对象不存在!";
	private static final String MSG_MATCH_ERROR = "对不起,球队比赛保存参数异常!";
	private static final String MSG_TEAM_NOTEXIST = "对不起,球队对象不存在!";
	
	@Autowired
	private CompetitionRoundDao roundDao;
	@Autowired
	private CompetitionMatchDao matchDao;
	@Autowired
	private TeamDao teamDao;
	@Autowired
	private CompetitionDao compDao;

	@Override
	public String save(CompetitionRoundDto dto){
		String id = saveRound(dto);
		return id;
	}

	@Override
	public String saveRoundAndMatch(CompetitionRoundDto dto, String adminId, String adminName){
		List<CompetitionMatch> matchs = assemblyCompAndTeamMap(dto, adminId, adminName);
		
		return saveMatchs(dto, matchs);
	}
	
	@Override
	public void updateRoundAndMatch(CompetitionRoundDto dto, String adminId, String adminName) {
		List<CompetitionMatch> matchs = assemblyCompAndTeamMap(dto, adminId, adminName);
		updateMatchs(dto, matchs);
	}
	
	@Transactional
	public void updateMatchs(CompetitionRoundDto dto, List<CompetitionMatch> matchs) {
		updateRound(dto);
		matchDao.delMatchByCroundId(dto.getId());
		for (CompetitionMatch match : matchs) {
			match.setCroundId(dto.getId());
			match.setId(UUIDGenerator.getUUID());
			match.setCreateTime(new Date());
			match.setStat(DataStatus.ENABLED);
		}	
		matchDao.insertBatch(matchs);
	}

	@Transactional
	public String saveMatchs(CompetitionRoundDto dto, List<CompetitionMatch> matchs){
		String croundId = saveRound(dto);
		for (CompetitionMatch match : matchs) {
			match.setCroundId(croundId);
			match.setId(UUIDGenerator.getUUID());
			match.setCreateTime(new Date());
			match.setStat(DataStatus.ENABLED);
		}	
		matchDao.insertBatch(matchs);
		return croundId;
	}

	@Override
	public void del(String ids){
		checkObjIsNull(ids, MSG_ID_NULL);
		String[] arr = ids.split(",");
		for (String id : arr){
			CompetitionRound obj = roundDao.selectByPrimaryKey(id);
			checkObjIsNull(obj, MSG_OBJ_NOTEXIST);
			if (obj.getStat() == DataStatus.DISABLED){
				return;
			}
			obj.setStat(DataStatus.DISABLED);
			roundDao.updateByPrimaryKeySelective(obj);
			matchDao.delMatchByCroundId(id);
		}
	}

	@Override
	public void update(CompetitionRoundDto dto){
		checkObjIsNull(dto, MSG_OBJ_NULL);
		checkObjIsNull(dto.getId(), MSG_ID_NULL);
		CompetitionRound obj = roundDao.selectByPrimaryKey(dto.getId());
		checkObjIsNull(obj, MSG_OBJ_NOTEXIST);
		if (obj.getStat() == DataStatus.DISABLED){
			return;
		}
	}

	@Override
	public CompetitionRoundDto getRoundById(String id){
		checkObjIsNull(id, MSG_ID_NULL);
		CompetitionRound obj = roundDao.selectByPrimaryKey(id);
		if (null != obj && obj.getStat() == DataStatus.ENABLED){
			CompetitionRoundDto dto = BeanUtils.createBeanByTarget(obj, CompetitionRoundDto.class);
			return dto;
		}
		return null;
	}

	@Override
	public List<CompetitionRoundDto> findRoundByParam(CompetitionRoundDto dto){
		return roundDao.findCompetitionRoundByParam(dto);
	}

	@Override
	public PageResult<CompetitionRoundDto> findRoundByParam(
			CompetitionRoundDto dto, PageQuery query){
		int total = roundDao.countCompetitionRoundByParam(dto);
		if (total > 0){
			query.setTotal(total);
			List<CompetitionRoundDto> results = roundDao.findCompetitionRoundByParam(dto, query);
			return new PageResult<CompetitionRoundDto>(query, results);
		}
		return null;
	}

	@Override
	public List<CompetitionRoundDto> findRoundByCompId(String compId){
		checkObjIsNull(compId, MSG_COMPID_NULL);
		CompetitionRoundDto dto = new CompetitionRoundDto();
		dto.setCompId(compId);
		return roundDao.findCompetitionRoundByParam(dto);
	}
	
	@Override
	public int maxPageRanking(String compId) {
		if (StringUtils.isBlank(compId)){
			throw new RuntimeException("对不起,赛事ID为空!");
		}
		return roundDao.maxPageRanking(compId);
	}
	
	@Override
	public void updatePageRanking(String id, int pageRanking) {
		CompetitionRound obj = new CompetitionRound();
		obj.setId(id);
		obj.setPageRanking(pageRanking);
		roundDao.updateByPrimaryKeySelective(obj);
	}

	private void checkObjIsNull(Object obj, String msg) {
		if (null == obj){
			throw new RuntimeException(msg);
		}
		if (obj instanceof String){
			if (StringUtils.isBlank((String)obj)){
				throw new RuntimeException(msg);
			}
		}
	}
	
	private List<CompetitionMatch> assemblyCompAndTeamMap(CompetitionRoundDto dto, String adminId, String adminName) {
		checkObjIsNull(dto, MSG_OBJ_NULL);
		checkObjIsNull(dto.getCompId(), MSG_COMPID_NULL);
		Competition comp = compDao.selectByPrimaryKey(dto.getCompId());
		assemblyRound(dto, comp, adminId, adminName);
		
		int[] matchTypes = dto.getMatchType();
		String[] homeTeamIds = dto.getHomeTeamId();
		String[] visitingTeamIds = dto.getVisitingTeamId();
		int[] homeScores = dto.getHomeScore();
		int[] visitingScores = dto.getVisitingScore();
		String[] matchDateStrs = dto.getMatchDateStr();
		String[] matchTimeStrs = dto.getMatchTimeStr();
		Integer peopleType = dto.getPeopleType();
		if (null == matchTypes || null == homeTeamIds || null == visitingTeamIds || null == homeScores 
				|| null == visitingScores || null == matchDateStrs || null == matchTimeStrs){
			throw new RuntimeException(MSG_MATCH_ERROR);
		}
		if (matchTypes.length != homeTeamIds.length || homeTeamIds.length != visitingTeamIds.length 
				|| visitingTeamIds.length != homeScores.length || homeScores.length != visitingScores.length
				||  visitingScores.length != matchDateStrs.length || matchDateStrs.length != matchTimeStrs.length){
			throw new RuntimeException(MSG_MATCH_ERROR);
		}
		
		List<String> ids = Arrays.asList(homeTeamIds);
		List<Team> hs = teamDao.findTeamByIds(ids);
		Map<String, Team> hMap = new HashMap<String, Team>(hs.size());
		for (Team match : hs){
			hMap.put(match.getId(), match);
		}
		ids = Arrays.asList(visitingTeamIds);
		List<Team> vs = teamDao.findTeamByIds(ids);
		Map<String, Team> vMap = new HashMap<String, Team>(hs.size());
		for (Team match : vs){
			vMap.put(match.getId(), match);
		}
		
		List<CompetitionMatch> matchs = new ArrayList<CompetitionMatch>();
		CompetitionMatch match;
		Team ht;
		Team vt;
		for (int i = 0; i < matchTypes.length; i++) {
			ht = hMap.get(homeTeamIds[i]);
			checkObjIsNull(ht, MSG_TEAM_NOTEXIST);
			vt = vMap.get(visitingTeamIds[i]);
			checkObjIsNull(vt, MSG_TEAM_NOTEXIST);
			
			match = new CompetitionMatch();
			match.setCompId(dto.getCompId());
			match.setCompName(comp.getTitle());
			match.setCroundName(dto.getName());
			match.setHomeScore(homeScores[i]);
			match.setHomeTeamIcon(ht.getIcon());
			match.setHomeTeamName(ht.getName());
			match.setHomeTeamId(homeTeamIds[i]);
			match.setAddress(ht.getAddress());
			match.setVisitingTeamName(vt.getName());
			match.setVisitingTeamIcon(vt.getIcon());
			match.setVisitingTeamId(visitingTeamIds[i]);
			match.setVisitingScore(visitingScores[i]);
			String matchTime = matchDateStrs[i] + " " + matchTimeStrs[i];
			match.setMatchTime(DateUtils.parse(matchTime, "yyyy-MM-dd HH:mm"));
			match.setMatchType(matchTypes[i]);
			match.setCreateAdminId(adminId);
			match.setCreateAdminName(adminName);
			match.setPeopleType(peopleType);
			
			matchs.add(match);
		}
		
		return matchs;
	}

	private void assemblyRound(CompetitionRoundDto dto, Competition comp, String adminId, String adminName) {
		checkObjIsNull(comp, MSG_COMP_NOTEXIST);
		dto.setCompName(comp.getTitle());
		dto.setCreateAdminId(adminId);
		dto.setCreateAdminName(adminName);
	}
	
	private String saveRound(CompetitionRoundDto dto) {
		checkObjIsNull(dto, MSG_OBJ_NULL);
		CompetitionRound obj = BeanUtils.createBeanByTarget(dto, CompetitionRound.class);
		int max = roundDao.maxPageRanking(obj.getCompId());
		obj.setPageRanking(max+1);
		String id = UUIDGenerator.getUUID();
		obj.setId(id);
		roundDao.insertSelective(obj);
		return id;
	}
	
	private void updateRound(CompetitionRoundDto dto) {
		checkObjIsNull(dto, MSG_OBJ_NULL);
		checkObjIsNull(dto.getId(), MSG_ID_NULL);
		CompetitionRound obj = BeanUtils.createBeanByTarget(dto, CompetitionRound.class);
		roundDao.updateByPrimaryKeySelective(obj);
	}
}