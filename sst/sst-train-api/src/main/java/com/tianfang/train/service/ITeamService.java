package com.tianfang.train.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.train.dto.TeamDto;

public interface ITeamService {
	
	/**
	 * 查询所有球队
	 * @return
	 * @throws Exception
	 * @author xiang_wang
	 * 2016年1月13日上午9:53:06
	 */
	List<TeamDto> findAll();
	
	/**
	 * 查需赛事下所有球队
	 * @param compId
	 * @return
	 * @author xiang_wang
	 * 2016年1月25日下午2:25:23
	 */
	List<TeamDto> findAllByCompId(String compId);
	
	/**
	 * 增加赛事公告
	 * @author YIn
	 * @time:2016年1月15日 上午10:50:44
	 * @param teamDto
	 * @return
	 */
	int addTeam(TeamDto teamDto);

	/**
	 * 编辑赛事公告(根据主键Id进行更新)
	 * @author YIn
	 * @time:2016年1月15日 上午11:28:26
	 * @param teamDto
	 * @return
	 */
	int updateTeam(TeamDto teamDto);
	
	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月15日 上午11:51:24
	 * @param teamDto
	 * @return
	 */
	int delTeam(TeamDto teamDto);
	
	/**
	 * 批量逻辑删除
	 * @author YIn
	 * @time:2016年1月15日 下午6:11:52
	 * @param ids
	 * @return
	 */
	Integer delByIds(String ids);

	/**
	 * 查询赛事公告-不分页
	 * @author YIn
	 * @time:2016年1月15日 下午1:25:20
	 * @param teamDto
	 * @return
	 */
	List<TeamDto> findTeam(TeamDto teamDto);

	/**
	 * 后台赛事公告显示页面-分页
	 * @author YIn
	 * @time:2016年1月15日 下午2:17:54
	 * @param teamDto
	 * @param page
	 * @return
	 */
	PageResult<TeamDto> findTeamViewByPage(TeamDto teamDto, PageQuery page);
	
	/**
	 * 根据主键id查询球队
	 * @param id
	 * @return
	 * @author xiang_wang
	 * 2016年1月26日下午3:06:20
	 */
	TeamDto getTeamById(String id);
}