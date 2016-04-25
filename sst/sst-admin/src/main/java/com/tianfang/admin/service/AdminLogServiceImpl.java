package com.tianfang.admin.service;

import com.tianfang.admin.dao.AdminLogDao;
import com.tianfang.admin.dto.AdminLogDto;
import com.tianfang.admin.pojo.AdminLog;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.common.util.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminLogServiceImpl implements IAdminLogService {
	
	@Autowired
	private AdminLogDao adminLogDao;

	@Override
	public String save(AdminLogDto dto) throws Exception {
		checkObjIsNull(dto);
		AdminLog adminLog = BeanUtils.createBeanByTarget(dto, AdminLog.class);
		String id = UUIDGenerator.getUUID();
		adminLog.setId(id);
		int flag = adminLogDao.insertSelective(adminLog);
		if(flag > 0){
			return id;
		}
		return "";
	}

	@Override
	public void del(String id) throws Exception {
		checkIdIsNull(id);
		AdminLog m = adminLogDao.selectByPrimaryKey(id);
		checkObjIsNotExist(m);
		m.setStat(DataStatus.DISABLED);
		adminLogDao.updateByPrimaryKeySelective(m);
	}

	@Override
	public void update(AdminLogDto dto) throws Exception {
		checkObjIsNull(dto);
		checkIdIsNull(dto.getId());
		checkObjIsNotExist(adminLogDao.selectByPrimaryKey(dto.getId()));
		AdminLog m = BeanUtils.createBeanByTarget(dto, AdminLog.class);
		adminLogDao.updateByPrimaryKeySelective(m);
	}

	@Override
	public AdminLogDto getAdminLogById(String id) throws Exception {
		checkIdIsNull(id);
		AdminLog m = adminLogDao.selectByPrimaryKey(id);
		AdminLogDto dto = BeanUtils.createBeanByTarget(m, AdminLogDto.class);
		return dto;
	}

	@Override
	public List<AdminLogDto> findAdminLogByParam(AdminLogDto dto) throws Exception {
		return adminLogDao.findAdminLogByParam(dto);
	}
	
	@Override
	public List<AdminLogDto> findAdminLogByAdminId(String adminId) throws Exception {
		checkAdminIdIsNull(adminId);
		AdminLogDto dto = new AdminLogDto();
		dto.setAdminId(adminId);
		return adminLogDao.findAdminLogByParam(dto);
	}

	@Override
	public PageResult<AdminLogDto> findAdminLogByParam(AdminLogDto dto, PageQuery query)
			throws Exception {
		int total = adminLogDao.countAdminLogByParam(dto);
		if (total > 0){
			query.setTotal(total);
			List<AdminLogDto> results = adminLogDao.findAdminLogByParam(dto, query);
			return new PageResult<AdminLogDto>(query, results);
		}
		
		return null;
	}

	private void checkObjIsNull(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,后台用户操作日志对象为空!");
		}
	}
	
	private void checkIdIsNull(String id) {
		if (StringUtils.isBlank(id)){
			throw new RuntimeException("对不起,后台用户操作日志对象主键ID为空!");
		}
	}
	
	private void checkObjIsNotExist(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,后台用户操作日志对象不存在!");
		}
	}
	
	private void checkAdminIdIsNull(String adminId) {
		if (StringUtils.isBlank(adminId)){
			throw new RuntimeException("对不起,后台用户操作日志对象管理员ID为空!");
		}
	}
}