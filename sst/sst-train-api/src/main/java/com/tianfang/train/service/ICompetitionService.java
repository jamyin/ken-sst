package com.tianfang.train.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.train.dto.CompetitionDto;
public interface ICompetitionService {

	/**
	 * 增加赛事
	 * @author YIn
	 * @time:2016年1月14日 上午10:50:44
	 * @param competitionDto
	 * @return
	 */
	int addCompetition(CompetitionDto competitionDto);

	/**
	 * 编辑赛事(根据主键Id进行更新)
	 * @author YIn
	 * @time:2016年1月14日 上午11:28:26
	 * @param competitionDto
	 * @return
	 */
	int updateCompetition(CompetitionDto competitionDto);
	
	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月14日 上午11:51:24
	 * @param competitionDto
	 * @return
	 */
	int delCompetition(CompetitionDto competitionDto);
	
	/**
	 * 批量逻辑删除
	 * @author YIn
	 * @time:2016年1月14日 下午6:11:52
	 * @param ids
	 * @return
	 */
	Integer delByIds(String ids);
	
	/**
	 * 根据主键id查询赛事
	 * @param id
	 * @return
	 * @author xiang_wang
	 * 2016年1月25日下午3:05:24
	 */
	CompetitionDto getCompById(String id);

	/**
	 * 查询赛事-不分页
	 * @author YIn
	 * @time:2016年1月14日 下午1:25:20
	 * @param competitionDto
	 * @return
	 */
	List<CompetitionDto> findCompetition(CompetitionDto competitionDto);
	
	/**
	 * 查询所有赛事
	 * @return
	 * @author xiang_wang
	 * 2016年1月23日下午3:19:52
	 */
	List<CompetitionDto> findAllCompetition();

	/**
	 * 后台赛事显示页面-分页
	 * @author YIn
	 * @time:2016年1月14日 下午2:17:54
	 * @param competitionDto
	 * @param changeToPageQuery
	 * @return
	 */
	PageResult<CompetitionDto> findCompetitionViewByPage(CompetitionDto competitionDto, PageQuery page);

}