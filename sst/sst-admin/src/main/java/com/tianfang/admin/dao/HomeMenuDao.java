package com.tianfang.admin.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.admin.dto.HomeMenuDto;
import com.tianfang.admin.mapper.HomeMenuMapper;
import com.tianfang.admin.pojo.HomeMenu;
import com.tianfang.admin.pojo.HomeMenuExample;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.StringUtils;

@Repository
public class HomeMenuDao extends MyBatisBaseDao<HomeMenu>{
	
	@Autowired
	@Getter
	private HomeMenuMapper mapper;

	public List<HomeMenu> findHomeMenuList(HomeMenuDto dto) {
		HomeMenuExample example = new HomeMenuExample();
		HomeMenuExample.Criteria criteria = example.createCriteria();
		assemblyParams(dto, criteria);
		criteria.andStatEqualTo(DataStatus.ENABLED);
	    
        example.setOrderByClause(" menu_type,menu_order");
        
	    List<HomeMenu> resultList = mapper.selectByExample(example);
	    return resultList;
	}
	
	public List<HomeMenuDto> findPage(HomeMenuDto dto,PageQuery page) {
		HomeMenuExample example = new HomeMenuExample();
		HomeMenuExample.Criteria criteria = example.createCriteria();	
		assemblyParams(dto, criteria);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		if (null != page) {
            example.setOrderByClause(" menu_type,menu_order,create_time asc limit "+page.getStartNum() +"," + page.getPageSize());
        }
		List<HomeMenu> HomeMenus = mapper.selectByExample(example);
		return BeanUtils.createBeanListByTarget(HomeMenus, HomeMenuDto.class);
	}
	
	public List<HomeMenuDto> findByMenuType(Integer menuType) {
		HomeMenuExample example = new HomeMenuExample();
		HomeMenuExample.Criteria criteria = example.createCriteria();
		criteria.andStatEqualTo(DataStatus.ENABLED);
		List<HomeMenu> HomeMenus = mapper.selectByExample(example);
		return BeanUtils.createBeanListByTarget(HomeMenus, HomeMenuDto.class);
	}
	
	public long count(HomeMenuDto dto) {
		HomeMenuExample example = new HomeMenuExample();
		HomeMenuExample.Criteria criteria = example.createCriteria();	  
		assemblyParams(dto, criteria);
		criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.countByExample(example);
	}
	
	private void assemblyParams(HomeMenuDto dto,
			HomeMenuExample.Criteria criteria) {
		if (null != dto){
			if (StringUtils.isNotBlank(dto.getId())) {
				criteria.andIdEqualTo(dto.getId());
			}
			if (StringUtils.isNotBlank(dto.getParentId())) {
				criteria.andParentIdEqualTo(dto.getParentId());
			}
			if (null != dto.getMenuType()){
				criteria.andMenuTypeEqualTo(dto.getMenuType());
			}
			if (StringUtils.isNotBlank(dto.getCname())){
				criteria.andCnameLike("%"+dto.getCname().trim()+"%");
			}
			if (null != dto.getVideoType()){
				criteria.andVideoTypeEqualTo(dto.getVideoType());
			}
		}
	}
}
