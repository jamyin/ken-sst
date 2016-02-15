package com.tianfang.admin.service;

import com.tianfang.admin.dto.AdminDto;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;

public interface IAdminService {
    
    public AdminDto adminLogin(String account, String pwd);
    
    public void updateAdmin(AdminDto dto);
    
    public PageResult<AdminDto> findPage(AdminDto dto, PageQuery page);
    
    /**
     * 后台管理员新增操作
     * @param admin
     * @return 新增管理员的主键id
     * @author xiang_wang
     * 2016年1月12日上午9:56:19
     */
    public String save(AdminDto dto);
    
    /**
     * 后台管理员+关联权限 组合 新增操作
     * @param admin
     * @param jsonClasss
     * @return 新增管理员的主键id
     * @author xiang_wang
     * 2016年1月12日上午10:02:11
     */
    public String save(AdminDto admin, String jsonClasss);
    
    public Integer delAdIds(String delAdIds);
    
    public AdminDto getAdminById(String id);
    
    /**
     * 后台管理员更新操作
     * @param dto
     * @return 管理员的主键id
     * @author xiang_wang
     * 2016年1月12日上午10:06:40
     */
    public String edit(AdminDto dto); 
    
    /**
     * 后台管理员更新 + 后台管理菜单关联 操作
     * @param dto
     * @param jsonClasss
     * @return 管理员的主键id
     * @author xiang_wang
     * 2016年1月12日上午11:17:32
     */
    public String edit(AdminDto dto, String jsonClasss);   
    
    public Integer distributionAuth(String adminId,String menuIds);
}