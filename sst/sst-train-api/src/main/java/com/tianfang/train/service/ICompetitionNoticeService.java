package com.tianfang.train.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.train.dto.CompetitionNoticeDto;
public interface ICompetitionNoticeService {

	/**
	 * 增加赛事公告
	 * @author YIn
	 * @time:2016年1月15日 上午10:50:44
	 * @param competitionNoticeDto
	 * @return
	 */
	int addCompetitionNotice(CompetitionNoticeDto competitionNoticeDto);

	/**
	 * 编辑赛事公告(根据主键Id进行更新)
	 * @author YIn
	 * @time:2016年1月15日 上午11:28:26
	 * @param competitionNoticeDto
	 * @return
	 */
	int updateCompetitionNotice(CompetitionNoticeDto competitionNoticeDto);
	
	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月15日 上午11:51:24
	 * @param competitionNoticeDto
	 * @return
	 */
	int delCompetitionNotice(CompetitionNoticeDto competitionNoticeDto);
	
	/**
	 * 批量逻辑删除
	 * @author YIn
	 * @time:2016年1月15日 下午6:11:52
	 * @param ids
	 * @return
	 */
	Integer delByIds(String ids);
	
	/**
	 * 根据主键id查询赛事公告
	 * @param id
	 * @return
	 * @author xiang_wang
	 * 2016年1月26日下午5:18:12
	 */
	CompetitionNoticeDto getNoticeById(String id);

	/**
	 * 查询赛事公告-不分页
	 * @author YIn
	 * @time:2016年1月15日 下午1:25:20
	 * @param competitionNoticeDto
	 * @return
	 */
	List<CompetitionNoticeDto> findCompetitionNotice(CompetitionNoticeDto competitionNoticeDto);

	/**
	 * 后台赛事公告显示页面-分页
	 * @author YIn
	 * @time:2016年1月15日 下午2:17:54
	 * @param competitionNoticeDto
	 * @param changeToPageQuery
	 * @return
	 */
	PageResult<CompetitionNoticeDto> findCompetitionNoticeViewByPage(CompetitionNoticeDto competitionNoticeDto, PageQuery page);

	/**
	 * 后台赛事公告显示页面-分页(连接赛事表)
	 * @author YIn
	 * @time:2016年1月22日 下午5:34:30
	 * @param competitionNoticeDto
	 * @param page
	 * @return
	 */
	PageResult<CompetitionNoticeDto> findCompNoticeViewByPage(CompetitionNoticeDto competitionNoticeDto, PageQuery page);

}