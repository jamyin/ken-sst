package com.tianfang.user.service;

import com.alibaba.fastjson.JSON;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.user.dao.UserInfoDao;
import com.tianfang.user.dto.UserInfoDto;
import com.tianfang.user.pojo.UserInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl implements IUserInfoService {
	protected static final Log logger = LogFactory.getLog(UserInfoServiceImpl.class);
	
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

	@Override
	public UserInfoDto getUserInfo(String userId) {
		if (StringUtils.isBlank(userId)){
			throw new RuntimeException("对不起,用户id为空!");
		}
		UserInfoDto param = new UserInfoDto();
		param.setUserId(userId);
		List<UserInfoDto> userInfo = userInfoDao.findUserInfo(param);
		if (null != userInfo && userInfo.size() > 0){
			if (userInfo.size() != 1){
				logger.error("userInfo数据存在异常!数据内容:"+ JSON.toJSONString(userInfo));
			}
			return userInfo.get(0);
		}
		return null;
	}
}
