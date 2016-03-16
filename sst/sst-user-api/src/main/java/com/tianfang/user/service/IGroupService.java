package com.tianfang.user.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.user.dto.GroupDto;
import com.tianfang.user.dto.GroupUserDto;

/**		
 * <p>Title: IGroupService </p>
 * <p>Description: 类描述:群组接口</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月15日上午9:53:59
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public interface IGroupService {
	
	String save(GroupDto dto) throws Exception;

	/**
	 * 保存用户聊天组
	 * @param dto
	 * @param gus
     * @return
     */
	String save(GroupDto dto, List<GroupUserDto> gus);
	
	void del(String id) throws Exception;
	
	void update(GroupDto dto) throws Exception;
	
	/**
	 * 获取用户群组信息
	 * @param id
	 * @param flag	是否查询所有群组内所有用户信息
	 * @return
	 * @throws Exception
	 * @author xiang_wang
	 * 2016年1月15日下午1:29:36
	 */
	GroupDto getGroupById(String id, Boolean flag) throws Exception;
	
	List<GroupDto> findGroupByParam(GroupDto dto) throws Exception;
	
	PageResult<GroupDto> findGroupByParam(GroupDto dto, PageQuery query) throws Exception;
	
	List<GroupDto> findGroupByUserId(String userId) throws Exception;
	/**
	 * 
		 * 此方法描述的是：根据group_user 表中的user_id 查找group_id
		 * @author: cwftalus@163.com
		 * @version: 2016年3月15日 上午9:52:57
	 */
	List<GroupDto> findGroupsByUserId(String userId) throws Exception;
}