package com.tianfang.admin.service;

import com.tianfang.admin.dto.AdminLogDto;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;

import java.util.List;


/**		
 * <p>Title: IAdminLogService </p>
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
public interface IAdminLogService {
	
	String save(AdminLogDto dto) throws Exception;
	
	void del(String id) throws Exception;
	
	void update(AdminLogDto dto) throws Exception;
	
	AdminLogDto getAdminLogById(String id) throws Exception;
	
	List<AdminLogDto> findAdminLogByParam(AdminLogDto dto) throws Exception;
	
	PageResult<AdminLogDto> findAdminLogByParam(AdminLogDto dto, PageQuery query) throws Exception;
	
	List<AdminLogDto> findAdminLogByAdminId(String adminId) throws Exception;
}