package com.tianfang.user.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.user.app.VoteApp;
import com.tianfang.user.dto.VoteDto;

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

	String save(VoteDto dto) throws Exception;
	
	void del(String id) throws Exception;
	
	void update(VoteDto dto) throws Exception;
	
	VoteDto getVoteById(String id) throws Exception;
	
	List<VoteDto> findVoteByParam(VoteDto dto) throws Exception;
	
	PageResult<VoteDto> findVoteByParam(VoteDto dto, PageQuery query) throws Exception;
	
	VoteApp getVoteAppById(String id) throws Exception;
}