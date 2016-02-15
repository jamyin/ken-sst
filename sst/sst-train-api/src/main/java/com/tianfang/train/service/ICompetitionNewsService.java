package com.tianfang.train.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.train.dto.CompetitionNewsDto;
public interface ICompetitionNewsService {

	/**
	 * 增加赛事动态
	 * @author YIn
	 * @time:2016年1月15日 上午10:50:44
	 * @param competitionNewsDto
	 * @return
	 */
	int addCompetitionNews(CompetitionNewsDto competitionNewsDto);

	/**
	 * 编辑赛事动态(根据主键Id进行更新)
	 * @author YIn
	 * @time:2016年1月15日 上午11:28:26
	 * @param competitionNewsDto
	 * @return
	 */
	int updateCompetitionNews(CompetitionNewsDto competitionNewsDto);
	
	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月15日 上午11:51:24
	 * @param competitionNewsDto
	 * @return
	 */
	int delCompetitionNews(CompetitionNewsDto competitionNewsDto);
	
	/**
	 * 批量逻辑删除
	 * @author YIn
	 * @time:2016年1月15日 下午6:11:52
	 * @param ids
	 * @return
	 */
	Integer delByIds(String ids);
	
	/**
	 * 根据赛事动态id查询
	 * @param id
	 * @return
	 * @author xiang_wang
	 * 2016年2月3日上午10:39:00
	 */
	CompetitionNewsDto getCompetitionNews(String id);

	/**
	 * 查询赛事动态-不分页
	 * @author YIn
	 * @time:2016年1月15日 下午1:25:20
	 * @param competitionNewsDto
	 * @return
	 */
	List<CompetitionNewsDto> findCompetitionNews(CompetitionNewsDto competitionNewsDto);

	/**
	 * 后台赛事动态显示页面-分页
	 * @author YIn
	 * @time:2016年1月15日 下午2:17:54
	 * @param competitionNewsDto
	 * @param changeToPageQuery
	 * @return
	 */
	PageResult<CompetitionNewsDto> findCompetitionNewsByPage(CompetitionNewsDto dto, PageQuery page);
}