package com.tianfang.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tianfang.admin.dao.AdminDao;
import com.tianfang.admin.dao.AdminMenuDao;
import com.tianfang.admin.dto.AdminDto;
import com.tianfang.admin.dto.AdminMenuRespDto;
import com.tianfang.admin.pojo.Admin;
import com.tianfang.admin.pojo.Authorization;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.UUIDGenerator;

@Service
public class AdminServiceImpl implements IAdminService {
	
	@Autowired
    private AdminDao adminDao;
    
    @Autowired
    private AdminMenuDao menuDao;

	@Override
	public AdminDto adminLogin(String account, String pwd) {
		Admin admin = adminDao.findByAccountPwd(account, pwd);
		if (null != admin){
			return BeanUtils.createBeanByTarget(admin, AdminDto.class);
		}
		return null;
	}

	@Override
	public void updateAdmin(AdminDto dto) {
		checkObjectIsNullException(dto);
		checkIdIsNullException(dto.getId());
		Admin admin = adminDao.getAdminById(dto.getId().trim());
		checkObjIsNotExist(admin);
        admin.setAccount(dto.getAccount());
        admin.setPwd(dto.getPwd());
        adminDao.updateByPrimaryKey(admin);
	}

	@Override
	public PageResult<AdminDto> findPage(AdminDto dto, PageQuery page) {
		int total = adminDao.count(dto);
		if (total > 0){
			page.setTotal(total);
			List<AdminDto> dtos = BeanUtils.createBeanListByTarget(adminDao.findPage(dto, page), AdminDto.class);
			return new PageResult<AdminDto>(page, dtos);
		}
		return null;
	}

	@Override
	public String save(AdminDto dto) {
		checkObjectIsNullException(dto);
        checkAccountIsExist(dto.getAccount());
        Admin admin = BeanUtils.createBeanByTarget(dto, Admin.class);
        String id = UUIDGenerator.getUUID();
        admin.setId(id);
        adminDao.insert(admin);
        return id;
	}

	@Override
	public String save(AdminDto dto, String jsonClasss) {
		List<AdminMenuRespDto> menuRespDtos = new Gson().fromJson(jsonClasss, new TypeToken<List<AdminMenuRespDto>>(){}.getType()); 
        String adminId = save(dto);
        for (AdminMenuRespDto trainingMenuRespDto : menuRespDtos) {
            Authorization trainingAuthorization = new Authorization();
            trainingAuthorization.setAdminId(adminId);
            trainingAuthorization.setId(UUIDGenerator.getUUID());
            trainingAuthorization.setMenuId(trainingMenuRespDto.getId());
            menuDao.save(trainingAuthorization);
        }
        return adminId;
	}

	@Override
	public Integer delAdIds(String delAdIds) {
		String[] idArr = delAdIds.split(",");
        for (String id : idArr) {
            Admin admin = adminDao.selectByPrimaryKey(id);
            if (null == admin) {
                return 0;//无此条记录
            }
            admin.setStat(DataStatus.DISABLED);
            adminDao.updateByPrimaryKey(admin);
        }
        return 1;
	}

	@Override
	public AdminDto getAdminById(String id) {
		checkIdIsNullException(id);
		return BeanUtils.createBeanByTarget(adminDao.getAdminById(id), AdminDto.class);
	}

	@Override
	public String edit(AdminDto dto) {
		checkObjectIsNullException(dto);
		checkIdIsNullException(dto.getId());
		checkObjIsNotExist(adminDao.selectByPrimaryKey(dto.getId()));
        Admin admin = BeanUtils.createBeanByTarget(dto, Admin.class);
        adminDao.updateByPrimaryKeySelective(admin);
        return dto.getId();
	}

	@Override
	public String edit(AdminDto dto, String jsonClasss) {
		List<AdminMenuRespDto> menuRespDtos = new Gson().fromJson(jsonClasss, new TypeToken<List<AdminMenuRespDto>>(){}.getType()); 
		Map<String, AdminMenuRespDto> map = new HashMap<String, AdminMenuRespDto>(menuRespDtos.size()); 
		for (AdminMenuRespDto menu : menuRespDtos){
			map.put(menu.getId(), menu);
		}
		edit(dto);
        List<Authorization> authList = menuDao.findByAdminId(dto.getId(), null);
        
        for (Authorization auth : authList) {
        	if (map.containsKey(auth.getMenuId())){
        		map.remove(auth.getMenuId());
        		if (auth.getStat().intValue() == DataStatus.ENABLED){
        			continue;
        		}
        		auth.setStat(DataStatus.ENABLED);
        	}else{
        		if (auth.getStat().intValue() == DataStatus.DISABLED){
        			continue;
        		}
        		auth.setStat(DataStatus.DISABLED);
        	}
            menuDao.update(auth);
        }
       
        if (map.size() > 0){
        	Iterator<String> it = map.keySet().iterator();
            Authorization newAuth;
            while (it.hasNext()){
            	String menuId = it.next();
            	newAuth = new Authorization();
            	newAuth.setAdminId(dto.getId());
            	newAuth.setMenuId(menuId);
            	menuDao.save(newAuth);
            }
        }
        
        return dto.getId();
	}

	@Override
	public Integer distributionAuth(String adminId, String menuIds) {
		String[] idArr = menuIds.split(",");
		List<String> list = new ArrayList<String>(idArr.length);
		for (String id : idArr){
			list.add(id);
		}
		List<Authorization> authList = menuDao.findByAdminId(adminId, null);
		for (Authorization auth : authList) {
        	if (list.contains(auth.getMenuId())){
        		list.remove(auth.getMenuId());
        		if (auth.getStat().intValue() == DataStatus.ENABLED){
        			continue;
        		}
        		auth.setStat(DataStatus.ENABLED);
        	}else{
        		if (auth.getStat().intValue() == DataStatus.DISABLED){
        			continue;
        		}
        		auth.setStat(DataStatus.DISABLED);
        	}
            menuDao.update(auth);
	    }
		if (list.size() > 0){
        	Iterator<String> it = list.iterator();
            Authorization newAuth;
            while (it.hasNext()){
            	String menuId = it.next();
            	newAuth = new Authorization();
            	newAuth.setAdminId(adminId);
            	newAuth.setMenuId(menuId);
            	menuDao.save(newAuth);
            }
        }
		
        return 1;
	}
	
	private void checkObjectIsNullException(Object dto) {
		if (null == dto){
			throw new RuntimeException("对不起,管理员对象为空!");
		}
	}
	
	private void checkIdIsNullException(String id) {
		if (null == id || "".equals(id.trim())){
			throw new RuntimeException("对不起,管理员对象主键ID为空!");   
		}
	}
	
	private void checkObjIsNotExist(Object dto) {
		if (null == dto) {
           throw new RuntimeException("对不起,管理员对象不存在!");
        }
	}
	
	private void checkAccountIsExist(String account){
		Admin obj = adminDao.getAdminByAccount(account.trim());
		if (null != obj) {
	           throw new RuntimeException("对不起,管理员账号已经存在!");
	    }
	}
}