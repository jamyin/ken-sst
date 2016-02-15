package com.tianfang.train.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.train.dto.CompetitionApplyDto;
public interface ICompetitionApplyService {

	/**
	 * 增加比赛报名
	 * @author YIn
	 * @time:2016年1月20日 上午10:50:44
	 * @param competitionApplyDto
	 * @return
	 */
	int addCompetitionApply(CompetitionApplyDto competitionApplyDto);

	/**
	 * 编辑比赛报名(根据主键Id进行更新)
	 * @author YIn
	 * @time:2016年1月20日 上午11:28:26
	 * @param competitionapplyDto
	 * @return
	 */
	int updateCompetitionApply(CompetitionApplyDto competitionApplyDto);
	
	/**
	 * 审核赛事报名
	 * @param id
	 * @param status
	 * @return
	 * @author xiang_wang
	 * 2016年1月23日上午11:20:50
	 */
	int auditCompetitionApply(String id, Integer status);
	
	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月20日 上午11:51:24
	 * @param competitionapplyDto
	 * @return
	 */
	int delCompetitionApply(CompetitionApplyDto competitionApplyDto);
	
	/**
	 * 批量逻辑删除
	 * @author YIn
	 * @time:2016年1月20日 下午6:11:52
	 * @param ids
	 * @return
	 */
	Integer delByIds(String ids);
	
	/**
	 * 根据主键id查询报名对象
	 * @param id
	 * @return
	 * @author xiang_wang
	 * 2016年1月23日上午10:40:37
	 */
	CompetitionApplyDto getCompetitionApplyById(String id);

	/**
	 * 查询比赛报名-不分页
	 * @author YIn
	 * @time:2016年1月20日 下午1:25:20
	 * @param competitionapplyDto
	 * @return
	 */
	List<CompetitionApplyDto> findCompetitionApply(CompetitionApplyDto competitionApplyDto);

	/**
	 * 后台比赛报名显示页面-分页
	 * @author YIn
	 * @time:2016年1月20日 下午2:17:54
	 * @param competitionapplyDto
	 * @param changeToPageQuery
	 * @return
	 */
	PageResult<CompetitionApplyDto> findCompetitionApplyViewByPage(CompetitionApplyDto competitionApplyDto, PageQuery page);
	/**
	 * 后台比赛报名显示页面-分页(连接赛事表查询)
	 * @author YIn
	 * @time:2016年1月23日 上午10:13:11
	 * @param competitionApplyDto
	 * @param page
	 * @return
	 */
	PageResult<CompetitionApplyDto> findCompApplyViewByPage(CompetitionApplyDto competitionApplyDto, PageQuery page);

}