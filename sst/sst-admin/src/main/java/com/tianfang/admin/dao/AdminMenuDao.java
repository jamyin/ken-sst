package com.tianfang.admin.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.tianfang.admin.dto.AdminMenuDto;
import com.tianfang.admin.mapper.AdminMenuMapper;
import com.tianfang.admin.mapper.AuthorizationExMapper;
import com.tianfang.admin.mapper.AuthorizationMapper;
import com.tianfang.admin.pojo.AdminMenu;
import com.tianfang.admin.pojo.AdminMenuExample;
import com.tianfang.admin.pojo.Authorization;
import com.tianfang.admin.pojo.AuthorizationExample;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;
import com.tianfang.common.util.UUIDGenerator;

@Repository
public class AdminMenuDao extends MyBatisBaseDao<AdminMenu>{

	@Autowired
	@Getter
	private AdminMenuMapper mapper;
	
	@Autowired
	@Getter
	private AuthorizationMapper authMapper;
	
	@Autowired
    @Getter
    private AuthorizationExMapper authExmapper;
	

	public List<AdminMenuDto> findAll() {
		AdminMenuExample example = new AdminMenuExample();
		AdminMenuExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		example.setOrderByClause(" orderby asc ");
		List<AdminMenu>  trainingMenuList = mapper.selectByExample(example);
		List<AdminMenuDto> trainingMenuRespDTOs = new ArrayList<AdminMenuDto>();
        if (trainingMenuList.size()>0) {
            trainingMenuRespDTOs= BeanUtils.createBeanListByTarget(trainingMenuList, AdminMenuDto.class);
            return trainingMenuRespDTOs;
        }
		return trainingMenuRespDTOs;
	} 
	
	public List<AdminMenu> findByleftMenu(String leftMenu) {
		AdminMenuExample example = new AdminMenuExample();
		AdminMenuExample.Criteria criteria = example.createCriteria();
        criteria.andLeftMenuEqualTo(leftMenu);
        criteria.andStatEqualTo(DataStatus.ENABLED);
        example.setOrderByClause(" orderby asc ");
        List<AdminMenu>  trainingMenuList = mapper.selectByExample(example);
        return trainingMenuList;
	}
	
	public List<AdminMenu> findByleaf(String leaf) {
		AdminMenuExample example = new AdminMenuExample();
		AdminMenuExample.Criteria criteria = example.createCriteria();
        criteria.andLeafEqualTo(leaf);
        criteria.andStatEqualTo(DataStatus.ENABLED);
        example.setOrderByClause(" orderby asc ");
        List<AdminMenu>  trainingMenuList = mapper.selectByExample(example);
        return trainingMenuList;
    }
    
	public List<AdminMenu> findByParentId(String ParentId) {
		AdminMenuExample example = new AdminMenuExample();
		AdminMenuExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(ParentId);
        criteria.andStatEqualTo(DataStatus.ENABLED);
        example.setOrderByClause(" orderby asc ");
        List<AdminMenu>  trainingMenuList = mapper.selectByExample(example);
        return trainingMenuList;
    }
	
	public List<AdminMenu> findMenuByAdminId(String id) {
	    return authExmapper.findMenuByAdminId(id);
	}	
	
	public Integer save(Authorization authorization) {
		if (null == authorization.getId() || StringUtils.isEmpty((authorization.getId()).trim())){
			authorization.setId(UUIDGenerator.getUUID());
		}
		authorization.setCreateTime(new Date());
		authorization.setStat(DataStatus.ENABLED);
	    return authMapper.insert(authorization);
	}
	
	public Integer update(Authorization authorization) {
		authorization.setLastUpdateTime(new Date());
        return authMapper.updateByPrimaryKeySelective(authorization);
    }
	
	public List<Authorization> findByAdminId(String adminId) {
		return findByAdminId(adminId, DataStatus.ENABLED);
	}
	
	public List<Authorization> findByAdminId(String adminId, Integer stat) {
		AuthorizationExample example = new AuthorizationExample();
		AuthorizationExample.Criteria criteria = example.createCriteria();
	    criteria.andAdminIdEqualTo(adminId);
	    if (null != stat){
	    	criteria.andStatEqualTo(stat);
	    }
	    
	    return authMapper.selectByExample(example);
	}
	
	public Authorization findByMenuId(String menuId) {
		return findByMenuId(menuId, DataStatus.ENABLED);
	}
	
	public Authorization findByMenuId(String menuId, Integer stat) {
		AuthorizationExample example = new AuthorizationExample();
		AuthorizationExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(menuId)) {
            criteria.andMenuIdEqualTo(menuId);
        }
        if (null != stat){
        	criteria.andStatEqualTo(stat);
        }
        List<Authorization> results = authMapper.selectByExample(example);
        return CollectionUtils.isEmpty(results) ? null : results.get(0);
	}
	
	public Authorization findByAdminIdAndMenuId(String adminId, String menuId) {
		return findByAdminMenuId(adminId, menuId, DataStatus.ENABLED);
    }
	
	public Authorization findByAdminMenuId(String adminId, String menuId, Integer stat) {
		AuthorizationExample example = new AuthorizationExample();
		AuthorizationExample.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(adminId)) {
            criteria.andAdminIdEqualTo(adminId);
        }
        if (StringUtils.isNotBlank(menuId)) {
            criteria.andMenuIdEqualTo(menuId);
        }
        if (null != stat){
        	 criteria.andStatEqualTo(stat);
        }
        List<Authorization> results = authMapper.selectByExample(example);
        return CollectionUtils.isEmpty(results) ? null : results.get(0);
	}
}