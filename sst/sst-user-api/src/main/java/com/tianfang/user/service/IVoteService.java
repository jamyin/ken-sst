package com.tianfang.user.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.user.app.VoteApp;
import com.tianfang.user.dto.VoteDto;
import com.tianfang.user.dto.VoteOptionDto;
import com.tianfang.user.dto.VoteParams;
import com.tianfang.user.dto.VoteUserTempDto;

/**		
 * <p>Title: IVoteService </p>
 * <p>Description: 类描述:投票接口</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月15日上午9:37:32
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public interface IVoteService {

	String save(VoteDto dto);
	
	/**
	 * 发布投票
	 * @param dto
	 * @param temps
	 * @param options
	 * @return
	 * @author xiang_wang
	 * 2016年3月9日下午2:55:36
	 */
	String save(VoteDto dto, List<VoteUserTempDto> temps, List<VoteOptionDto> options);
	
	void del(String id);
	
	void update(VoteDto dto);
	
	void update(VoteUserTempDto temp);
	
	VoteDto getVoteById(String id);
	
	List<VoteDto> findVoteByParam(VoteDto dto);
	
	PageResult<VoteDto> findVoteByParam(VoteDto dto, PageQuery query);
	
	VoteApp getVoteAppById(String id);
	
	/**
	 * 关联sst_vote_user_temp表查询,根据指定参数查询
	 * @param dto
	 * @return
	 * @author xiang_wang
	 * 2016年3月8日下午5:11:09
	 */
	List<VoteDto> findVoteTempByParam(VoteParams params);
	
	/**
	 * 关联sst_vote_user_temp表查询,根据指定参数分页查询
	 * @param dto
	 * @param query
	 * @return
	 * @author xiang_wang
	 * 2016年3月8日下午5:11:11
	 */
	PageResult<VoteDto> findVoteTempByParam(VoteParams params, PageQuery query);
}