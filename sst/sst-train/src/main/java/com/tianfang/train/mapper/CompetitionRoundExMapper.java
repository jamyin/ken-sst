package com.tianfang.train.mapper;

import org.apache.ibatis.annotations.Param;


public interface CompetitionRoundExMapper {
	
    /**
     * 当前赛事下页面排序的最大值
     * @param compId
     * @return
     * @author xiang_wang
     * 2016年2月2日下午1:49:03
     */
    Integer maxPageRanking(@Param("compId")String compId);
}