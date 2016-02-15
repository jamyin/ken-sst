package com.tianfang.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.admin.pojo.AdminMenu;

public interface AuthorizationExMapper {
    
	List<AdminMenu> findMenuByAdminId(@Param("id") String id);
	
}
