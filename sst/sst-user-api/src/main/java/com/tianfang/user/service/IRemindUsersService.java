package com.tianfang.user.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.user.dto.RemindDto;
import com.tianfang.user.dto.RemindUsersDto;

/**		
 * <p>Title: IRemindService </p>
 * <p>Description: 类描述:用户提醒</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月14日下午3:56:02
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public interface IRemindUsersService {
	
	String save(RemindUsersDto dto) throws Exception;

	List<RemindUsersDto> findRemindUsersByParam(RemindUsersDto dto) throws Exception;

	void insertList(List<RemindUsersDto> list);

}