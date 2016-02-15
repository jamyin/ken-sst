package com.tianfang.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.user.dto.VoteExDto;

public interface VoteExMapper {
	
	/**
	 * 多表联查查询投票,选项,用户投票结果
	 * @param id
	 * @return
	 * @author xiang_wang
	 * 2016年1月14日上午11:36:32
	 */
	List<VoteExDto> findVoteExById(@Param("id")String id);
}