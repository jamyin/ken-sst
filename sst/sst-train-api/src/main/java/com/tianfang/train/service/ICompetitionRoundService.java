package com.tianfang.train.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.train.dto.CompetitionRoundDto;

public interface ICompetitionRoundService {
	
	String save(CompetitionRoundDto dto);
	
	/**
	 * 保存球队场次和球队比赛
	 * @param dto
	 * @param adminId
	 * @param adminName
	 * @return
	 * @author xiang_wang
	 * 2016年1月25日上午10:11:38
	 */
	String saveRoundAndMatch(CompetitionRoundDto dto, String adminId, String adminName);
	
	void del(String id);
	
	void update(CompetitionRoundDto dto);
	
	CompetitionRoundDto getRoundById(String id);
	
	List<CompetitionRoundDto> findRoundByParam(CompetitionRoundDto dto);
	
	PageResult<CompetitionRoundDto> findRoundByParam(CompetitionRoundDto dto, PageQuery query);
	
	List<CompetitionRoundDto> findRoundByCompId(String compId);

	/**
	 * 更新球队场次和球队比赛
	 * @param dto
	 * @param id
	 * @param account
	 * @author xiang_wang
	 * 2016年1月25日下午4:07:25
	 */
	void updateRoundAndMatch(CompetitionRoundDto dto, String id, String account);
	
	/**
	 * 当前赛事下页面排序的最大值
	 * @param compId
	 * @return
	 * @author xiang_wang
	 * 2016年2月2日下午1:55:25
	 */
	int maxPageRanking(String compId);
	
	/**
	 * 更新场次的页面排序
	 * @param id
	 * @param pageRanking
	 * @author xiang_wang
	 * 2016年2月2日下午2:09:42
	 */
	void updatePageRanking(String id, int pageRanking);
}