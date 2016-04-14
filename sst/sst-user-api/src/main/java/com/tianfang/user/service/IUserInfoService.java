package com.tianfang.user.service;

import java.util.List;

import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.user.dto.UserInfoDto;


/**
 * @author Yin
 *
 */
public interface IUserInfoService {

	//增加用户参赛信息
	int addUserInfo(UserInfoDto userInfoDto);

	//查询用户参赛信息
	List<UserInfoDto> findUserInfo(UserInfoDto userInfoDto);

	//修改用户参赛信息
	int updateUserInfo(UserInfoDto userInfoDto);

	//批量删除用户参赛信息
	Integer delByIds(String ids);

	//分页查询用户参赛信息
	PageResult<UserInfoDto> findUserInfoViewByPage(UserInfoDto userInfoDto,
			PageQuery changeToPageQuery);

	/**
	 * <p>Description: 根据用户id查询用户参赛信息 </p>
	 * <p>Company: 上海天坊信息科技有限公司</p>
	 * @param userId
	 * @return UserInfoDto
	 * @author wangxiang
	 * @date 16/4/14 上午11:05
	 * @version 1.0
	 */
	UserInfoDto getUserInfo(String userId);
}