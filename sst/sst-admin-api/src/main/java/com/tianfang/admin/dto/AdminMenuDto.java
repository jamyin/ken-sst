package com.tianfang.admin.dto;

import java.io.Serializable;
import java.util.List;

/**		
 * <p>Title: MenuRespDto </p>
 * <p>Description: 类描述:后台父子菜单封装</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月11日下午4:44:55
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public class AdminMenuDto implements Serializable
{
	private static final long serialVersionUID = 8554370785001498027L;
	private AdminMenuRespDto parant;
    private List<AdminMenuRespDto> leaf;

    public AdminMenuRespDto getParant() {
        return parant;
    }

    public void setParant(AdminMenuRespDto parant) {
        this.parant = parant;
    }

    public List<AdminMenuRespDto> getLeaf() {
        return leaf;
    }

    public void setLeaf(List<AdminMenuRespDto> leaf) {
        this.leaf = leaf;
    }
}