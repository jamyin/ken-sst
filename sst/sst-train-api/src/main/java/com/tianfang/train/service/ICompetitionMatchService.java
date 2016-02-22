package com.tianfang.train.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.train.dto.CompetitionMatchDto;
import com.tianfang.train.dto.TeamDto;
public interface ICompetitionMatchService {

	/**
	 * 增加比赛
	 * @author YIn
	 * @time:2016年1月15日 上午10:50:44
	 * @param competitionMatchDto
	 * @return
	 */
	int addCompetitionMatch(CompetitionMatchDto competitionMatchDto);
	
	/**
	 * 保存比赛
	 * @param dto
	 * @param homeTeam 	主场球队
	 * @param visitingTeam	客场球队
	 * @param adminId
	 * @param adminName
	 * @return
	 * @author xiang_wang
	 * 2016年1月26日下午3:03:49
	 */
	String saveMatch(CompetitionMatchDto dto, TeamDto homeTeam, TeamDto visitingTeam, String adminId, String adminName);
	
	/**
	 * 更新比赛
	 * @param dto
	 * @param homeTeam 主场球队
	 * @param visitingTeam 客场球队
	 * @param id
	 * @param account
	 * @author xiang_wang
	 * 2016年1月26日下午3:47:08
	 */
	void updateMatch(CompetitionMatchDto dto, TeamDto homeTeam, TeamDto visitingTeam, String id, String account);

	/**
	 * 编辑比赛(根据主键Id进行更新)
	 * @author YIn
	 * @time:2016年1月15日 上午11:28:26
	 * @param competitionMatchDto
	 * @return
	 */
	int updateCompetitionMatch(CompetitionMatchDto competitionMatchDto);
	
	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月15日 上午11:51:24
	 * @param competitionMatchDto
	 * @return
	 */
	int delCompetitionMatch(CompetitionMatchDto competitionMatchDto);
	
	/**
	 * 批量逻辑删除
	 * @author YIn
	 * @time:2016年1月15日 下午6:11:52
	 * @param ids
	 * @return
	 */
	Integer delByIds(String ids);
	
	/**
	 * 根据主键id查询比赛
	 * @param id
	 * @return
	 * @author xiang_wang
	 * 2016年1月26日下午3:36:46
	 */
	CompetitionMatchDto getMatchById(String id);

	/**
	 * 查询比赛-不分页
	 * @author YIn
	 * @time:2016年1月15日 下午1:25:20
	 * @param competitionMatchDto
	 * @return
	 */
	List<CompetitionMatchDto> findCompetitionMatch(CompetitionMatchDto competitionMatchDto);
	
	/**
	 * 后台比赛显示页面-分页
	 * @author YIn
	 * @time:2016年1月15日 下午2:17:54
	 * @param competitionMatchDto
	 * @param changeToPageQuery
	 * @return
	 */
	PageResult<CompetitionMatchDto> findCompetitionMatchViewByPage(CompetitionMatchDto competitionMatchDto, PageQuery page);

	/**
	 * 根据场次id查询比赛
	 * @param roundId
	 * @return
	 * @author xiang_wang
	 * 2016年1月25日下午3:38:32
	 */
	List<CompetitionMatchDto> findMatchByRoundId(String roundId);
	
	/**
	 * 根据球队id和赛事id查询比赛
	 * @param teamId 球队id
	 * @param compId 赛事id
	 * @return
	 * @author xiang_wang
	 * 2016年2月1日下午1:22:43
	 */
	List<CompetitionMatchDto> findMatchByTeamIdAndCompId(String teamId, String compId);
	
	/**
	 * 根据赛事id查询比赛
	 * @param compId
	 * @return
	 * @author xiang_wang
	 * 2016年2月2日下午3:09:25
	 */
	List<CompetitionMatchDto> findMatchByCompId(String compId);
}