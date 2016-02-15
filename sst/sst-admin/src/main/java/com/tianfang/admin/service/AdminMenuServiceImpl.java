package com.tianfang.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.admin.dao.AdminMenuDao;
import com.tianfang.admin.dto.AdminMenuAuthRespDto;
import com.tianfang.admin.dto.AdminMenuDto;
import com.tianfang.admin.dto.AdminMenuRespDto;
import com.tianfang.admin.pojo.AdminMenu;
import com.tianfang.admin.pojo.Authorization;
import com.tianfang.common.util.BeanUtils;

@Service
public class AdminMenuServiceImpl implements IAdminMenuService {
	
	@Autowired
	private AdminMenuDao adminMenuDao;

	@Override
	public List<AdminMenuDto> findMenuByAdminId(String id) {
		List<AdminMenu> permLists = adminMenuDao.findMenuByAdminId(id);//用户拥有的权限
    	List<AdminMenu> list1 = findByLeaf("否"); //左侧一级菜单判断
    	List<AdminMenu> list2 = new ArrayList<AdminMenu>();
    	for (AdminMenu sportMenu:permLists) {
    		for (AdminMenu sportmenu:list1) {
    			if (sportMenu.getId().equals(sportmenu.getId())) {
    				list2.add(sportMenu);
    				list1.remove(sportmenu);
    				break;
    			}
    		}
    	}    	
    	List<AdminMenu> list3 = findByLeaf("是"); //左侧所有二级菜单判断
    	List<AdminMenu> list4 = new ArrayList<AdminMenu>();
    	for (AdminMenu sportMenu:permLists) {
    		for (AdminMenu sportmenu:list3) {
    			if (sportMenu.getId().equals(sportmenu.getId())) {
    				list4.add(sportMenu);
    			}
    		}
    	}    	
    	List<AdminMenuDto> permissionDTOListOut = new ArrayList<AdminMenuDto>();    	
    	for (AdminMenu sportMenu:list2) {
    		AdminMenuDto menuRespDto = new AdminMenuDto();
    		AdminMenuRespDto sportMenuRespDto = BeanUtils.createBeanByTarget(sportMenu, AdminMenuRespDto.class);
    		List<AdminMenuRespDto> sportMenuRespDtos = new ArrayList<AdminMenuRespDto>();
    		for (AdminMenu sportmenu:list4){    			
    			if (sportMenu.getId().equals(sportmenu.getParentId())) {    				
    				AdminMenuRespDto sportmenuRespDto = BeanUtils.createBeanByTarget(sportmenu, AdminMenuRespDto.class);
    				sportMenuRespDtos.add(sportmenuRespDto);
    			}
    		}
    		menuRespDto.setParant(sportMenuRespDto);
    		menuRespDto.setLeaf(sportMenuRespDtos); 
    		permissionDTOListOut.add(menuRespDto);
    	}    	
    	return permissionDTOListOut;
	}

	@Override
	public List<AdminMenuAuthRespDto> getAdminAuthById(String adminId) {
		   List<AdminMenuAuthRespDto> trainingMenuAuthRespDtos = new ArrayList<AdminMenuAuthRespDto>();
	        List<AdminMenu> trainingMenuLeaf = findByLeaf("否");
	        for (AdminMenu trainingMenu:trainingMenuLeaf) {
	        	AdminMenuAuthRespDto trainingMenuAuthRespDto = BeanUtils.createBeanByTarget(trainingMenu, AdminMenuAuthRespDto.class);
	            Authorization trainingAuthorization = adminMenuDao.findByAdminIdAndMenuId(adminId,trainingMenu.getId());
	            List<AdminMenu> trainingMenuChild = adminMenuDao.findByParentId(trainingMenu.getId());
	            if (null == trainingAuthorization) {
	                List<AdminMenuAuthRespDto> trainingmenuAuthRespDtos = BeanUtils.createBeanListByTarget(trainingMenuChild, AdminMenuAuthRespDto.class);
	                for (AdminMenuAuthRespDto trainingmenuAuthRespDto : trainingmenuAuthRespDtos) {
	                    trainingmenuAuthRespDto.setChecked(false);
	                }
	                trainingMenuAuthRespDto.setNodes(trainingmenuAuthRespDtos);
	            }
	            if(null != trainingAuthorization) {
	                List<AdminMenuAuthRespDto> trainingmenuAuthRespDtos = BeanUtils.createBeanListByTarget(trainingMenuChild, AdminMenuAuthRespDto.class);
	                for (AdminMenuAuthRespDto trainingmenuAuthRespDto : trainingmenuAuthRespDtos) {
	                    Authorization trainingauthorization = adminMenuDao.findByMenuId(trainingmenuAuthRespDto.getId());
	                    if (null != trainingauthorization) {
	                        trainingmenuAuthRespDto.setChecked(true);
	                    }else {
	                        trainingmenuAuthRespDto.setChecked(false);
	                    }
	                } 
	                trainingMenuAuthRespDto.setChecked(true);
	                trainingMenuAuthRespDto.setNodes(trainingmenuAuthRespDtos);                
	            }
	            trainingMenuAuthRespDtos.add(trainingMenuAuthRespDto);
	        }
	        return trainingMenuAuthRespDtos;
	}
	
	private List<AdminMenu> findByLeaf(String leaf){
        return adminMenuDao.findByleaf(leaf);
    }
}