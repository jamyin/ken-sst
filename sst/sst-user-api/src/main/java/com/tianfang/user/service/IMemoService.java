package com.tianfang.user.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.user.dto.MemoDto;

/**		
 * <p>Title: IMemoService </p>
 * <p>Description: 类描述:用户备忘录</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月14日下午2:37:42
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public interface IMemoService {
	
	String save(MemoDto dto) throws Exception;
	
	void del(String id) throws Exception;
	
	void update(MemoDto dto) throws Exception;
	
	MemoDto getMemoById(String id) throws Exception;
	
	List<MemoDto> findMemoByParam(MemoDto dto) throws Exception;
	
	PageResult<MemoDto> findMemoByParam(MemoDto dto, PageQuery query) throws Exception;
	
	List<MemoDto> findMemoByUserId(String userId) throws Exception;
}