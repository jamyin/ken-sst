package com.tianfang.user.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.user.dto.VoteOptionDto;

/**		
 * <p>Title: IVoteOptionService </p>
 * <p>Description: 类描述:投票选项接口</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月15日上午9:37:06
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public interface IVoteOptionService {

	String save(VoteOptionDto dto) throws Exception;
	
	void del(String id) throws Exception;
	
	void update(VoteOptionDto dto) throws Exception;
	
	VoteOptionDto getVoteOptionById(String id) throws Exception;
	
	List<VoteOptionDto> findVoteOptionByParam(VoteOptionDto dto) throws Exception;
	
	PageResult<VoteOptionDto> findVoteOptionByParam(VoteOptionDto dto, PageQuery query) throws Exception;
}