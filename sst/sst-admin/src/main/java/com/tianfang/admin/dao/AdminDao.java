package com.tianfang.admin.dao;

import java.util.List;

import lombok.Getter;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.tianfang.admin.dto.AdminDto;
import com.tianfang.admin.mapper.AdminMapper;
import com.tianfang.admin.pojo.Admin;
import com.tianfang.admin.pojo.AdminExample;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
/**
 * @time:2016年2月18日 上午10:40:13
 * @ClassName: AdminDao
 * @Description: TODO
 * @
 */
@Repository
public class AdminDao extends MyBatisBaseDao<Admin>{

    @Autowired
    @Getter
    private AdminMapper mapper;
    
    public Admin findByAccountPwd(String account, String pwd) {
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(account);
        criteria.andPwdEqualTo(pwd);
        criteria.andStatEqualTo(DataStatus.ENABLED);
        List<Admin> results = mapper.selectByExample(example);
        return CollectionUtils.isEmpty(results) ? null : results.get(0);
    }
    
    public Admin getAdminById(String id){
    	return mapper.selectByPrimaryKey(id);
    }
    
    public Admin getAdminByAccount(String account){
    	AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andAccountEqualTo(account);
        criteria.andStatEqualTo(DataStatus.ENABLED);
        List<Admin> results = mapper.selectByExample(example);
        return CollectionUtils.isEmpty(results) ? null : results.get(0);
    }
    
    public List<Admin> findPage(AdminDto dto, PageQuery page) {
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
        if (page != null) {
            example.setOrderByClause("create_time desc limit "
                    + page.getStartNum() + ", " + page.getPageSize() + "");
        }
        List<Admin> results = mapper.selectByExample(example);
        return CollectionUtils.isEmpty(results) ? null : results;
    }

    public int count(AdminDto dto) {
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        assemblyParams(dto, criteria);
        return mapper.countByExample(example);
    }
    
	private void assemblyParams(AdminDto dto, AdminExample.Criteria criteria) {
		if (StringUtils.isNotBlank(dto.getAccount())) {
            criteria.andAccountLike("%"+dto.getAccount()+"%");
        }
        criteria.andStatEqualTo(DataStatus.ENABLED);
	}
}