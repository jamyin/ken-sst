package com.tianfang.user.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.user.dto.VoteUserOptionDto;

/**		
 * <p>Title: IVoteUserOptionService </p>
 * <p>Description: 类描述:用户投票结果接口</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月15日上午9:37:44
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public interface IVoteUserOptionService {

	String save(VoteUserOptionDto dto) throws Exception;
	
	void del(String id) throws Exception;
	
	void update(VoteUserOptionDto dto) throws Exception;
	
	VoteUserOptionDto getVoteUserOptionById(String id) throws Exception;
	
	List<VoteUserOptionDto> findVoteUserOptionByParam(VoteUserOptionDto dto) throws Exception;
	
	PageResult<VoteUserOptionDto> findVoteUserOptionByParam(VoteUserOptionDto dto, PageQuery query) throws Exception;
}