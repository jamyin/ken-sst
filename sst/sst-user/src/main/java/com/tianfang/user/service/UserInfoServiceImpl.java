package com.tianfang.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.user.dao.UserInfoDao;
import com.tianfang.user.dto.UserInfoDto;
import com.tianfang.user.pojo.UserInfo;

@Service
public class UserInfoServiceImpl implements IUserInfoService {
	
	@Autowired
	private UserInfoDao userInfoDao;

	@Override
	public int addUserInfo(UserInfoDto userInfoDto) {
		UserInfo userInfo = BeanUtils.createBeanByTarget(userInfoDto, UserInfo.class);
		return userInfoDao.insertSelective(userInfo);
	}

	@Override
	public List<UserInfoDto> findUserInfo(UserInfoDto userInfoDto) {
		return userInfoDao.findUserInfo(userInfoDto);
	}

	@Override
	public int updateUserInfo(UserInfoDto userInfoDto) {
		UserInfo userInfo = BeanUtils.createBeanByTarget(userInfoDto, UserInfo.class);
		return userInfoDao.updateByPrimaryKeySelective(userInfo);
	}

	@Override
	public Integer delByIds(String ids) {
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			UserInfo userInfo = userInfoDao.selectByPrimaryKey(id);
			if (null == userInfo) {
				return 0;//无此条记录
			}
			userInfo.setStat(DataStatus.DISABLED);
			userInfoDao.updateByPrimaryKeySelective(userInfo);
		}
		return 1;
	}

	@Override
	public PageResult<UserInfoDto> findUserInfoViewByPage(
			UserInfoDto userInfoDto, PageQuery page) {
		List<UserInfoDto> list = userInfoDao.findUserInfoViewByPage(userInfoDto ,  page);
		int total = userInfoDao.selectAccount(userInfoDto);
		page.setTotal(total);
		return new PageResult<UserInfoDto>(page, list);
	}

}
