package com.tianfang.train.dao;

import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.DateUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.train.dto.CompetitionMatchDto;
import com.tianfang.train.mapper.CompetitionMatchExMapper;
import com.tianfang.train.mapper.CompetitionMatchMapper;
import com.tianfang.train.pojo.CompetitionMatch;
import com.tianfang.train.pojo.CompetitionMatchExample;
import com.tianfang.train.pojo.CompetitionMatchExample.Criteria;

@Repository
public class CompetitionMatchDao extends MyBatisBaseDao<CompetitionMatch> {

	@Getter
	@Autowired
	private CompetitionMatchMapper mapper;
	
	@Autowired
	private CompetitionMatchExMapper exMapper;

	/**
	 * 不带分页查询
	 * @author YIn
	 * @time:2016年1月15日 下午1:24:21
	 */
	public List<CompetitionMatch> selectByParameter(CompetitionMatchDto competitionMatch) {
		CompetitionMatchExample example = new CompetitionMatchExample();
		CompetitionMatchExample.Criteria criteria = example.createCriteria();
        assemblyParams(competitionMatch, criteria);   //组装参数
        List<CompetitionMatch> result = mapper.selectByExample(example);  
        return result;
	}

	/**
	 * 组装参数
	 * @author YIn
	 * @time:2016年1月15日 下午1:27:43
	 */
	private void assemblyParams(CompetitionMatchDto competitionMatch, Criteria criteria) {
		if (null != competitionMatch){
			if (StringUtils.isNotBlank(competitionMatch.getId())){
	    		criteria.andIdEqualTo(competitionMatch.getId());
	    	}
			if (StringUtils.isNotBlank(competitionMatch.getCroundId())){
				criteria.andCroundIdEqualTo(competitionMatch.getCroundId());
			}
			if (StringUtils.isNotBlank(competitionMatch.getCompId())){
				criteria.andCompIdEqualTo(competitionMatch.getCompId());
			}
			if (StringUtils.isNotBlank(competitionMatch.getHomeTeamId())){
				criteria.andHomeTeamIdEqualTo(competitionMatch.getHomeTeamId());
			}
			if (StringUtils.isNotBlank(competitionMatch.getVisitingTeamId())){
				criteria.andVisitingTeamIdEqualTo(competitionMatch.getVisitingTeamId());
			}
			if (StringUtils.isNotBlank(competitionMatch.getCompName())){
				criteria.andCompNameLike("%"+competitionMatch.getCompName()+"%");
			}
			if (StringUtils.isNotBlank(competitionMatch.getCroundName())){
				criteria.andCroundNameLike("%"+competitionMatch.getCroundName()+"%");
			}
			if (StringUtils.isNotBlank(competitionMatch.getHomeTeamName())){
				criteria.andHomeTeamNameLike("%"+competitionMatch.getHomeTeamName()+"%");
			}
			if (StringUtils.isNotBlank(competitionMatch.getVisitingTeamName())){
				criteria.andVisitingTeamNameLike("%"+competitionMatch.getVisitingTeamName()+"%");
			}
			if (null != competitionMatch.getMatchType()){
				criteria.andMatchTypeEqualTo(competitionMatch.getMatchType().intValue());
			}
			if (StringUtils.isNotBlank(competitionMatch.getMatchDateStr())){
				criteria.andMatchTimeBetween(DateUtils.parse(competitionMatch.getMatchDateStr(), "yyyy-MM-dd"), new Date(((DateUtils.parse(competitionMatch.getMatchDateStr(), "yyyy-MM-dd")).getTime()+86400000L)));
			}
		}
		
    	criteria.andStatEqualTo(DataStatus.ENABLED);
	}

	/**
	 * 分页查询比赛
	 * @author YIn
	 * @time:2016年1月15日 下午2:43:29
	 */
	public List<CompetitionMatch> findCompetitionMatchViewByPage(CompetitionMatchDto competitionMatch, PageQuery page) {
		CompetitionMatchExample example = new CompetitionMatchExample();
		CompetitionMatchExample.Criteria criteria = example.createCriteria();
		example.setOrderByClause(" match_time asc limit " + page.getStartNum() +"," + page.getPageSize());
        assemblyParams(competitionMatch, criteria);   //组装参数
        List<CompetitionMatch> result = mapper.selectByExample(example);  
        return result;
	}

	/**
	 * 查询总条数
	 * @author YIn
	 * @time:2016年1月15日 下午2:43:59
	 */
	public int selectAccount(CompetitionMatchDto competitionMatch) {
		CompetitionMatchExample example = new CompetitionMatchExample();
		CompetitionMatchExample.Criteria criteria = example.createCriteria();
        assemblyParams(competitionMatch, criteria);   //组装参数
        return mapper.countByExample(example);
	}
	
	/**
	 * 批量新增比赛
	 * @param matchs
	 * @author xiang_wang
	 * 2016年1月25日上午11:55:02
	 */
	public void insertBatch(List<CompetitionMatch> matchs){
		exMapper.insertBatch(matchs);
	}
	
	/**
	 * 删除场次下比赛
	 * @param croundId
	 * @author xiang_wang
	 * 2016年1月25日下午4:23:51
	 */
	public void delMatchByCroundId(String croundId){
		CompetitionMatchExample example = new CompetitionMatchExample();
		CompetitionMatchExample.Criteria criteria = example.createCriteria();
		criteria.andCroundIdEqualTo(croundId);
		mapper.deleteByExample(example);
	}

	/**
	 * 根据球队id和赛事id查询比赛
	 * @param teamId 球队id
	 * @param compId 赛事id
	 * @return
	 * @author xiang_wang
	 * 2016年2月1日下午1:33:57
	 */
	public List<CompetitionMatch> findMatchByTeamIdAndCompId(String teamId, String compId) {
		CompetitionMatchExample example = new CompetitionMatchExample();
		Criteria criteria = example.createCriteria();
		criteria.andCompIdEqualTo(compId);
		criteria.andHomeTeamIdEqualTo(teamId);
		
		Criteria criteria2 = example.or();
		criteria2.andCompIdEqualTo(compId);
		criteria2.andVisitingTeamIdEqualTo(teamId);
        example.setOrderByClause(" match_time asc");
        List<CompetitionMatch> result = mapper.selectByExample(example);  
		return result;
	}
	
	/**
	 * 根据赛事id查询比赛
	 * @param compId
	 * @return
	 * @author xiang_wang
	 * 2016年2月2日下午3:13:40
	 */
	public List<CompetitionMatch> findMatchByCompId(String compId) {
		CompetitionMatchExample example = new CompetitionMatchExample();
		Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(compId)){
			criteria.andCompIdEqualTo(compId);	
		}
		
        example.setOrderByClause(" match_time asc");
        List<CompetitionMatch> result = mapper.selectByExample(example);  
		return result;
	}

	public List<CompetitionMatch> findMatch(int limint,Integer matchType) {
		CompetitionMatchExample example = new CompetitionMatchExample();
		Criteria criteria = example.createCriteria();
        if(matchType!=null){
        	criteria.andMatchTypeEqualTo(matchType);
        }		
        example.setOrderByClause(" match_time desc limit " + limint);

        List<CompetitionMatch> result = mapper.selectByExample(example);  
		return result;
	}
	
	public List<CompetitionMatch> selectCompetitionMatchByTeamId(String teamId,PageQuery page) {
		return exMapper.selectCompetitionMatchByTeamId(teamId, page);
	}
	
	public Integer countCompetitionMatchByTeamId(String teamId) {
		return exMapper.countCompetitionMatchByTeamId(teamId);
	}
}