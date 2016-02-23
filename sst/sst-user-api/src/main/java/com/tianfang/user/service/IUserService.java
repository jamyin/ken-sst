package com.tianfang.user.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.user.app.FriendApp;
import com.tianfang.user.dto.UserDto;

/**		
 * <p>Title: IUserService </p>
 * <p>Description: 类描述:用户基本操作</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月12日下午4:10:40
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public interface IUserService {
	
	String save(UserDto dto) throws Exception;
	
	void del(String ids) throws Exception;
	
	int update(UserDto dto) throws Exception;
	
	UserDto getUserById(String id);
	
	List<UserDto> findUserByParam(UserDto dto) throws Exception;
	
	PageResult<UserDto> findUserByParam(UserDto dto, PageQuery query) throws Exception;

	void joinTeam(String userId, String teamId) throws Exception;
	
	/**
	 * 查询返回给移动端用户分组和好友信息
	 * @param userId
	 * @return FriendAppList
	 * @throws Exception
	 * @author xiang_wang
	 * 2016年1月13日下午3:16:42
	 */
	List<FriendApp> findFriendsByUserId(String userId) throws Exception;

	/**
	 * 校验手机号码是否注册过
	 * @param mobile
	 * @return
	 * @throws Exception
	 * @author xiang_wang
	 * 2016年1月18日下午3:58:04
	 */
	UserDto checkMobile(String mobile) throws Exception;

	/**
	 * 校验用户是否存在
	 * @param dto
	 * @return
	 * @throws Exception
	 * @author xiang_wang
	 * 2016年1月18日下午4:34:03
	 */
	UserDto checkUser(UserDto dto) throws Exception;
	
	/**
	 * 特别关注
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author xiang_wang
	 * 2016年1月20日下午1:26:30
	 */
	List<FriendApp> findCareFriends(String userId) throws Exception;
}