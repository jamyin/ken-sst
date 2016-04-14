package com.tianfang.train.service;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.train.dto.TeamPlayerDto;

import java.util.List;

public interface ITeamPlayerService {
	
	String save(TeamPlayerDto dto);
	
	void del(String id);
	
	void update(TeamPlayerDto dto);

	/**		
	 * <p>Description: 联表查询球员信息 </p>
	 * <p>Company: 上海天坊信息科技有限公司</p>
	 * @param dto
	 * @return List<TeamPlayerDto>
	 * @author wangxiang	
	 * @date 16/4/14 下午1:43
	 * @version 1.0
	 */
	List<TeamPlayerDto> findTeamPlayerByParam(TeamPlayerDto dto);

	/**
	 * <p>Description: 联表分页查询球员信息 </p>
	 * <p>Company: 上海天坊信息科技有限公司</p>
	 * @param dto
	 * @return List<TeamPlayerDto>
	 * @author wangxiang
	 * @date 16/4/14 下午1:43
	 * @version 1.0
	 */
	PageResult<TeamPlayerDto> findTeamPlayerByParam(TeamPlayerDto dto, PageQuery query);

	/**
	 * <p>Description: 根据球队id联表查询球员信息 </p>
	 * <p>Company: 上海天坊信息科技有限公司</p>
	 * @param teamId
	 * @return List<TeamPlayerDto>
	 * @author wangxiang
	 * @date 16/4/14 下午1:43
	 * @version 1.0
	 */
	List<TeamPlayerDto> findTeamPlayerByTeamId(String teamId);
	
	/**		
	 * <p>Description: 根据球队id和用户id查询球员信息 </p>
	 * <p>Company: 上海天坊信息科技有限公司</p>
	 * @param userId
	 * @return TeamPlayerDto
	 * @author wangxiang	
	 * @date 16/4/14 下午1:51
	 * @version 1.0
	 */
	TeamPlayerDto getTeamPlayeByUserId(String userId);
	
	/**
	 * 增加赛事球队队员
	 * @author YIn
	 * @time:2016年1月27日 上午10:50:44
	 * @param teamPlayerDto
	 * @return
	 */
	int addTeamPlayer(TeamPlayerDto teamPlayerDto);

	/**
	 * 编辑赛事球队队员(根据主键Id进行更新)
	 * @author YIn
	 * @time:2016年1月27日 上午11:28:26
	 * @param teamPlayerDto
	 * @return
	 */
	int updateTeamPlayer(TeamPlayerDto teamPlayerDto);
	
	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月27日 上午11:51:24
	 * @param teamPlayerDto
	 * @return
	 */
	int delTeamPlayer(TeamPlayerDto teamPlayerDto);
	
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
	List<TeamPlayerDto> findTeamPlayer(TeamPlayerDto teamPlayerDto);

	/**
	 * 后台赛事球队队员显示页面-分页
	 * @author YIn
	 * @time:2016年1月27日 下午2:17:54
	 * @param teamPlayerDto
	 * @param page
	 * @return
	 */
	PageResult<TeamPlayerDto> findTeamPlayerViewByPage(TeamPlayerDto teamPlayerDto, PageQuery page);
}