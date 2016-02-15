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
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.train.dao.CompetitionMatchDao;
import com.tianfang.train.dto.CompetitionMatchDto;
import com.tianfang.train.dto.TeamDto;
import com.tianfang.train.pojo.CompetitionMatch;


@Service
public class CompetitionMatchServiceImpl implements ICompetitionMatchService {

	@Autowired
	private CompetitionMatchDao competitionMatchDao;

	/**
	 * @author YIn
	 * @time:2016年1月15日 上午11:20:18
	 */
	@Override
	public int addCompetitionMatch(CompetitionMatchDto competitionMatchDto) {
		CompetitionMatch competitionMatch = BeanUtils.createBeanByTarget(competitionMatchDto, CompetitionMatch.class);
		return competitionMatchDao.insertSelective(competitionMatch);
	}

	@Override
	public String saveMatch(CompetitionMatchDto dto, TeamDto homeTeam,
			TeamDto visitingTeam, String adminId, String adminName) {
		CompetitionMatch obj = assemblyMatch(dto, homeTeam, visitingTeam, adminId, adminName);
		String id = UUIDGenerator.getUUID();
		obj.setId(id);
		competitionMatchDao.insertSelective(obj);
		return id;
	}

	@Override
	public void updateMatch(CompetitionMatchDto dto, TeamDto homeTeam, TeamDto visitingTeam, String adminId, String adminName) {
		CompetitionMatch obj = assemblyMatch(dto, homeTeam, visitingTeam, adminId, adminName);
		checkIdIsNull(obj);
		competitionMatchDao.updateByPrimaryKeySelective(obj);
	}

	/**
	 * 根据主键Id更新
	 * @author YIn
	 * @time:2016年1月15日 上午11:28:38
	 */
	@Override
	public int updateCompetitionMatch(CompetitionMatchDto competitionMatchDto) {
		CompetitionMatch competitionMatch = BeanUtils.createBeanByTarget(competitionMatchDto, CompetitionMatch.class);
		return competitionMatchDao.updateByPrimaryKeySelective(competitionMatch);
	}

	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月15日 上午11:51:34
	 */
	@Override
	public int delCompetitionMatch(CompetitionMatchDto competitionMatchDto) {
		CompetitionMatch competitionMatch = BeanUtils.createBeanByTarget(competitionMatchDto, CompetitionMatch.class);
		return competitionMatchDao.deleteByPrimaryKey(competitionMatch.getId());
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
			CompetitionMatch competitionMatch = competitionMatchDao
					.selectByPrimaryKey(id);
			if (null == competitionMatch) {
				return 0;// 无此条记录
			}
			competitionMatch.setStat(DataStatus.DISABLED);
			competitionMatchDao.updateByPrimaryKeySelective(competitionMatch);
		}
		return 1;
	}
	
	@Override
	public CompetitionMatchDto getMatchById(String id) {
		checkIdIsNull(id);
		CompetitionMatch m = competitionMatchDao.selectByPrimaryKey(id);
		if (null != m && m.getStat() == DataStatus.ENABLED){
			return BeanUtils.createBeanByTarget(m, CompetitionMatchDto.class);
		}
		return null;
	}
	
	private void checkIdIsNull(String id) {
		if (StringUtils.isBlank(id)){
			throw new RuntimeException("对不起,比赛主键id为空!");
		}
	}

	/**
	 * @author YIn
	 * @time:2016年1月15日 下午1:17:12
	 */
	@Override
	public List<CompetitionMatchDto> findCompetitionMatch(CompetitionMatchDto competitionMatchDto) {
		CompetitionMatch competitionMatch = BeanUtils.createBeanByTarget(competitionMatchDto, CompetitionMatch.class);
		List<CompetitionMatch> list = competitionMatchDao.selectByParameter(competitionMatch);
		List<CompetitionMatchDto> dtoList = BeanUtils.createBeanListByTarget(list, CompetitionMatchDto.class);
		return dtoList;
	}

	/**
	 * @author YIn
	 * @time:2016年1月15日 下午2:18:10
	 */
	@Override
	public PageResult<CompetitionMatchDto> findCompetitionMatchViewByPage(CompetitionMatchDto competitionMatchDto , PageQuery page) {
		CompetitionMatch competitionMatch = BeanUtils.createBeanByTarget(competitionMatchDto, CompetitionMatch.class);
		List<CompetitionMatch> list = competitionMatchDao.findCompetitionMatchViewByPage(competitionMatch,page);
		int total = competitionMatchDao.selectAccount(competitionMatch);
		page.setTotal(total);
		List<CompetitionMatchDto> dtoList = BeanUtils.createBeanListByTarget(list, CompetitionMatchDto.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		for(CompetitionMatchDto dto : dtoList){
			if(dto.getCreateTime() != null){
			dto.setCreateTimeStr(sdf.format(dto.getCreateTime()));}
			if(dto.getLastUpdateTime() != null){
			dto.setLastUpdateTimeStr(sdf.format(dto.getLastUpdateTime()));
			}
		}
		return new PageResult<CompetitionMatchDto>(page, dtoList);
	}
	
	@Override
	public List<CompetitionMatchDto> findMatchByRoundId(String roundId) {
		CompetitionMatch m = new CompetitionMatch();
		m.setCroundId(roundId);
		List<CompetitionMatch> ms = competitionMatchDao.selectByParameter(m);
		return PojoToDto(ms);
	}
	
	@Override
	public List<CompetitionMatchDto> findMatchByTeamIdAndCompId(String teamId, String compId){
		if (StringUtils.isBlank(teamId) || StringUtils.isBlank(compId)){
			throw new RuntimeException("对不起,请求参数异常!");
		}
		List<CompetitionMatch> ms = competitionMatchDao.findMatchByTeamIdAndCompId(teamId, compId);
		return PojoToDto(ms);
	}
	
	@Override
	public List<CompetitionMatchDto> findMatchByCompId(String compId) {
		if (StringUtils.isBlank(compId)){
			throw new RuntimeException("对不起,请求参数异常!");
		}
		List<CompetitionMatch> ms = competitionMatchDao.findMatchByCompId(compId);
		return PojoToDto(ms);
	}
	
	private CompetitionMatch assemblyMatch(CompetitionMatchDto dto,
			TeamDto homeTeam, TeamDto visitingTeam, String adminId,
			String adminName) {
		checkMatchIsNull(dto);
		checkTeamIsNull(homeTeam);
		checkTeamIsNull(visitingTeam);
		dto.setHomeTeamIcon(homeTeam.getIcon());
		dto.setHomeTeamName(homeTeam.getName());
		dto.setAddress(homeTeam.getAddress());
		dto.setVisitingTeamIcon(visitingTeam.getIcon());
		dto.setVisitingTeamName(visitingTeam.getName());
		dto.setCreateAdminId(adminId);
		dto.setCreateAdminName(adminName);
		dto.setMatchTime(DateUtils.parse(dto.getMatchDateStr() + " " + dto.getMatchTimeStr(), "yyyy-MM-dd HH:ss"));
		CompetitionMatch obj = BeanUtils.createBeanByTarget(dto, CompetitionMatch.class);
		return obj;
	}
	
	private void checkTeamIsNull(TeamDto home) {
		if (null == home){
			throw new RuntimeException("对不起,球队对象为空!");
		}
	}

	private void checkMatchIsNull(CompetitionMatchDto dto) {
		if (null == dto){
			throw new RuntimeException("对不起,比赛对象为空!");
		}
	}
	
	private void checkIdIsNull(CompetitionMatch obj) {
		if (StringUtils.isBlank(obj.getId())){
			throw new RuntimeException("对不起,比赛主键ID为空!");
		}
	}
	
	private List<CompetitionMatchDto> PojoToDto(List<CompetitionMatch> ms) {
		if (null != ms && ms.size() > 0){
			return BeanUtils.createBeanListByTarget(ms, CompetitionMatchDto.class);
		}
		return null;
	}
}