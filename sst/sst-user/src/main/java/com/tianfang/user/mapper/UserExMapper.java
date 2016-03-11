package com.tianfang.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.user.app.FriendApp;
import com.tianfang.user.dto.UserDto;

public interface UserExMapper {
	
	/**
	 * 查询返回给移动端用户分组和好友信息
	 * @param userId
	 * @return
	 * @author xiang_wang
	 * 2016年1月13日下午3:23:39
	 */
	List<FriendApp> findFriendsByUserId(@Param("userId")String userId);

	/**
	 * 查询该用户的特别关注好友
	 * @param userId
	 * @return
	 * @author xiang_wang
	 * 2016年1月20日下午1:39:30
	 */
	List<FriendApp> findCareFriends(@Param("userId")String userId);
	
	/**
	 * 根据群组Id查询用户信息
	 * @author YIn
	 * @time:2016年3月10日 下午5:11:17
	 * @param groupId
	 * @return
	 */
	List<UserDto> findUserByGroupId(String groupId);
}