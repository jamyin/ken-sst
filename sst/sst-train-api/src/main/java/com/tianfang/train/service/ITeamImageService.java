package com.tianfang.train.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.train.dto.TeamImageDto;

public interface ITeamImageService {
	
	/**
	 * 增加球队图片
	 * @author YIn
	 * @time:2016年1月27日 上午10:50:44
	 * @param teamImageDto
	 * @return
	 */
	int addTeamImage(TeamImageDto teamImageDto);

	/**
	 * 编辑球队图片(根据主键Id进行更新)
	 * @author YIn
	 * @time:2016年1月27日 上午11:28:26
	 * @param TeamImageDto
	 * @return
	 */
	int updateTeamImage(TeamImageDto teamImageDto);
	
	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月27日 上午11:51:24
	 * @param TeamImageDto
	 * @return
	 */
	int delTeamImage(TeamImageDto teamImageDto);
	
	/**
	 * 批量逻辑删除
	 * @author YIn
	 * @time:2016年1月27日 下午6:11:52
	 * @param ids
	 * @return
	 */
	Integer delByIds(String ids);

	/**
	 * 查询球队图片-不分页
	 * @author YIn
	 * @time:2016年1月27日 下午1:25:20
	 * @param teamImageDto
	 * @return
	 */
	List<TeamImageDto> findTeamImage(TeamImageDto teamImageDto);

	/**
	 * 后台球队图片显示页面-分页
	 * @author YIn
	 * @time:2016年1月27日 下午1:28:54
	 * @param teamImageDto
	 * @param page
	 * @return
	 */
	
	PageResult<TeamImageDto> findTeamImageViewByPage(TeamImageDto teamImageDto, PageQuery page);
	
	/**
	 * @author YIn
	 * @time:2016年1月27日 下午2:27:31
	 * @param teamImageDto
	 * @param page
	 * @return
	 */
	PageResult<TeamImageDto> findTeamImageConnPage(TeamImageDto teamImageDto,PageQuery page);

	/**
	 * 批量添加图片
	 * @author YIn
	 * @time:2016年1月29日 下午1:52:36
	 * @param list
	 * @return
	 */
	Integer addTeamImageBatch(List<TeamImageDto> list);
}