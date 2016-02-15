package com.tianfang.train.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.train.dto.TeamPlayerDto;

public interface ITeamPlayerService {
	
	String save(TeamPlayerDto dto);
	
	void del(String id);
	
	void update(TeamPlayerDto dto);
	
	List<TeamPlayerDto> findTeamPlayerByParam(TeamPlayerDto dto);
	
	PageResult<TeamPlayerDto> findTeamPlayerByParam(TeamPlayerDto dto, PageQuery query);
	
	List<TeamPlayerDto> findTeamPlayerByTeamId(String teamId);
	
	/**
	 * 增加赛事球队队员
	 * @author YIn
	 * @time:2016年1月27日 上午10:50:44
	 * @param teamPlayerDto
	 * @return
	 */
	int addTeamPlayer(TeamPlayerDto TeamPlayerDto);

	/**
	 * 编辑赛事球队队员(根据主键Id进行更新)
	 * @author YIn
	 * @time:2016年1月27日 上午11:28:26
	 * @param teamPlayerDto
	 * @return
	 */
	int updateTeamPlayer(TeamPlayerDto TeamPlayerDto);
	
	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月27日 上午11:51:24
	 * @param teamPlayerDto
	 * @return
	 */
	int delTeamPlayer(TeamPlayerDto TeamPlayerDto);
	
	/**
	 * 批量逻辑删除
	 * @author YIn
	 * @time:2016年1月27日 下午6:11:52
	 * @param ids
	 * @return
	 */
	Integer delByIds(String ids);
	
	/**
	 * 根据主键id查询赛事球队队员
	 * @param id
	 * @return
	 * @author xiang_wang
	 * 2016年1月26日下午5:18:12
	 */
	TeamPlayerDto getTeamPlayerById(String id);

	/**
	 * 查询赛事球队队员-不分页
	 * @author YIn
	 * @time:2016年1月27日 下午1:25:20
	 * @param teamPlayerDto
	 * @return
	 */
	List<TeamPlayerDto> findTeamPlayer(TeamPlayerDto TeamPlayerDto);

	/**
	 * 后台赛事球队队员显示页面-分页
	 * @author YIn
	 * @time:2016年1月27日 下午2:17:54
	 * @param teamPlayerDto
	 * @param page
	 * @return
	 */
	PageResult<TeamPlayerDto> findTeamPlayerViewByPage(TeamPlayerDto TeamPlayerDto, PageQuery page);
}