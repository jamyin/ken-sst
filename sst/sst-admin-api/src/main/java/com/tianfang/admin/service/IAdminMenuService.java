package com.tianfang.admin.service;

import java.util.List;

import com.tianfang.admin.dto.AdminMenuAuthRespDto;
import com.tianfang.admin.dto.AdminMenuDto;

public interface IAdminMenuService {
	
    /**
     * 根据管理员id查询后台管理菜单
     * @param id
     * @return
     * @author xiang_wang
     * 2016年1月11日下午4:54:35
     */
    List<AdminMenuDto> findMenuByAdminId(String id);
    
    /**
     * 根据管理员id查询后台管理菜单授权信息
     * @param adminId
     * @return
     * @author xiang_wang
     * 2016年1月11日下午4:55:26
     */
    public List<AdminMenuAuthRespDto> getAdminAuthById(String adminId);
}