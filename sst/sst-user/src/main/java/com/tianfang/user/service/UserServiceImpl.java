package com.tianfang.user.service;

import java.util.List;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.user.app.FriendApp;
import com.tianfang.user.dao.UserDao;
import com.tianfang.user.dto.UserDto;
import com.tianfang.user.pojo.User;

@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public String save(UserDto dto) throws Exception {
		checkObjIsNullException(dto);
		checkMobileIsException(dto.getMobile());
		User user = BeanUtils.createBeanByTarget(dto, User.class);
		String id = UUIDGenerator.getUUID();
		user.setId(id);
		userDao.insertSelective(user);
		return id;
	}

	@Override
	public void del(String ids) throws Exception {
		String[] idArr = ids.split(",");
		User user;
		for (String id : idArr){
			user = userDao.selectByPrimaryKey(id);
			checkObjIsNotExistException(user);
			user.setStat(DataStatus.DISABLED);
			userDao.updateByPrimaryKeySelective(user);
		}
	}

	@Override
	public int update(UserDto dto) throws Exception {
		checkObjIsNullException(dto);
		checkIdIsNullException(dto.getId());
		checkObjIsNotExistException(userDao.selectByPrimaryKey(dto.getId()));
		User user = BeanUtils.createBeanByTarget(dto, User.class);
		return userDao.updateByPrimaryKeySelective(user);
	}

	@Override
	public UserDto getUserById(String id) {
		checkIdIsNullException(id);
		User user = userDao.selectByPrimaryKey(id);
		if (null != user){
			return BeanUtils.createBeanByTarget(user,UserDto.class);
		}
		return null;
	}

	@Override
	public List<UserDto> findUserByParam(UserDto dto) throws Exception {
		return userDao.findUserByParam(dto);
	}

	@Override
	public PageResult<UserDto> findUserByParam(UserDto dto, PageQuery query) throws Exception {
		int total = userDao.countUserByParam(dto);
		if (total > 0){
			query.setTotal(total);
			List<UserDto> list = userDao.findUserByParam(dto, query);
			return new PageResult<UserDto>(query, list);
		}
		return null;
	}
	
	@Override
	public void joinTeam(String userId, String teamId) throws Exception {
		checkIdIsNullException(userId);
		User user = userDao.selectByPrimaryKey(userId);
		checkObjIsNotExistException(user);
		user.setTeamId(teamId);
		userDao.updateByPrimaryKeySelective(user);
	}
	
	@Override
	public List<FriendApp> findFriendsByUserId(String userId) throws Exception {
		checkIdIsNullException(userId);
		return userDao.findFriendsByUserId(userId);
	}
	
	@Override
	public UserDto checkMobile(String mobile) throws Exception {
		if (StringUtils.isBlank(mobile)){
			throw new RuntimeException("对不起,手机号码为空!");
		}
		UserDto dto = new UserDto();
		dto.setMobile(mobile);
		List<UserDto> list = userDao.findUserByParam(dto);
		if (null != list && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public UserDto checkUser(UserDto dto) throws Exception {
		checkObjIsNullException(dto);
		List<UserDto> list = userDao.findUserByParam(dto);
		if (null != list && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<FriendApp> findCareFriends(String userId) throws Exception {
		checkIdIsNullException(userId);
		return userDao.findCareFriends(userId);
	}
	
	private void checkObjIsNullException(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,用户对象为空!");
		}
	}

	private void checkObjIsNotExistException(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,用户对象不存在!");
		}
	}
	
	private void checkIdIsNullException(String id) {
		if (StringUtils.isBlank(id)){
			throw new RuntimeException("对不起,用户ID为空!");
		}
	}
	
	private void checkMobileIsException(String mobile) {
		if (null == mobile || "".equals(mobile.trim())){
			throw new RuntimeException("手机号码不能为空!");
		}
		Pattern mobile_reg = Pattern.compile("^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$");
		if (!mobile_reg.matcher(mobile.trim()).matches()){
			throw new RuntimeException("手机号码格式错误!");
		}
		if (null != userDao.getUserByMobile(mobile.trim())){
			throw new RuntimeException("对不起,该手机号码已经注册过了!");
		}
	}
}