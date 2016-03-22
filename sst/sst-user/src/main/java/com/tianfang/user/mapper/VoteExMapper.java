package com.tianfang.user.mapper;

import com.tianfang.common.model.PageQuery;
import com.tianfang.user.dto.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VoteExMapper {
	
	/**
	 * 多表联查查询投票,选项,用户投票结果
	 * @param id
	 * @return
	 * @author xiang_wang
	 * 2016年1月14日上午11:36:32
	 */
	List<VoteExDto> findVoteExById(@Param("id")String id);
	
	/**
	 * sst_vote 和 sst_vote_user_temp 联表查询
	 * @param params
	 * @return
	 * @author xiang_wang
	 * 2016年3月9日上午9:12:05
	 */
	int countVoteTempByParams(@Param("example")VoteParams params); 
	
	/**
	 * sst_vote 和 sst_vote_user_temp 联表查询
	 * @param params
	 * @param query
	 * @return
	 * @author xiang_wang
	 * 2016年3月8日下午5:50:19
	 */
	List<VoteDto> findVoteTempByParams(@Param("example")VoteParams params, @Param("page")PageQuery query);
	
	/**
	 * 批量保存投票选项
	 * @param options
	 * @author xiang_wang
	 * 2016年3月9日下午3:05:14
	 */
	void insertBatchVoteOption(List<VoteOptionDto> options);
	
	/**
	 * 批量保存投票用户关联表数据
	 * @param temps
	 * @author xiang_wang
	 * 2016年3月9日下午3:05:16
	 */
	void insertBatchVoteUserTemp(List<VoteUserTempDto> temps);

	/**
	 * 根据用户id 查询最新一条投票信息
	 * @param userId
	 * @return
     */
	VoteDto getLast(String userId);
}