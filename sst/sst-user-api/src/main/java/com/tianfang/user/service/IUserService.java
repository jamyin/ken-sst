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
	
	String save(UserDto dto);
	
	void del(String ids);
	
	int update(UserDto dto);
	
	UserDto getUserById(String id);
	
	List<UserDto> findUserByParam(UserDto dto);
	
	PageResult<UserDto> findUserByParam(UserDto dto, PageQuery query);

	void joinTeam(String userId, String teamId);
	
	/**
	 * 用户注册
	 * @param dto
	 * @return
	 */
	public UserDto regiest(UserDto dto);
	
	/**
	 * 查询返回给移动端用户分组和好友信息
	 * @param userId
	 * @return FriendAppList
	 * @throws Exception
	 * @author xiang_wang
	 * 2016年1月13日下午3:16:42
	 */
	List<FriendApp> findFriendsByUserId(String userId);

	/**
	 * 校验手机号码是否注册过
	 * @param mobile
	 * @return
	 * @throws Exception
	 * @author xiang_wang
	 * 2016年1月18日下午3:58:04
	 */
	UserDto checkMobile(String mobile);

	/**
	 * 校验用户是否存在
	 * @param dto
	 * @return
	 * @throws Exception
	 * @author xiang_wang
	 * 2016年1月18日下午4:34:03
	 */
	UserDto checkUser(UserDto dto);
	
	/**
	 * 特别关注
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author xiang_wang
	 * 2016年1月20日下午1:26:30
	 */
	List<FriendApp> findCareFriends(String userId);
	
	/**
     * 根据群组Id查询用户信息 
     * @author YIn
     * @time:2016年3月10日 下午4:45:57
     * @param groupId
     * @return
     */
	List<UserDto> findUserByGroupId(String groupId);
}