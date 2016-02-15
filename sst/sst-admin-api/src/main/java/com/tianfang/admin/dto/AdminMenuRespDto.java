package com.tianfang.admin.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: AdminMenuDto </p>
 * <p>Description: 类描述:后台管理菜单</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月11日下午4:45:01
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public class AdminMenuRespDto implements Serializable {
    
	private static final long serialVersionUID = 1125702170086409419L;
	private String id;
    private Long createTime;
    private String descript;
    private String leaf;
    private String leftMenu;
    private String menuId;
    private String menuTitle;
    
    @Getter
    @Setter
    private String menuUrl;
    
    @Getter
    @Setter
    private String menuIcon;
    
    private String parentId;

    private Long updateTime;

    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript == null ? null : descript.trim();
    }

    public String getLeaf() {
        return leaf;
    }

    public void setLeaf(String leaf) {
        this.leaf = leaf == null ? null : leaf.trim();
    }

    public String getLeftMenu() {
        return leftMenu;
    }

    public void setLeftMenu(String leftMenu) {
        this.leftMenu = leftMenu == null ? null : leftMenu.trim();
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId == null ? null : menuId.trim();
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle == null ? null : menuTitle.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}