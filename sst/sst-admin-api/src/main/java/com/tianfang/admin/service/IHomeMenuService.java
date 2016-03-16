package com.tianfang.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tianfang.admin.dto.HomeMenuDto;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;

@Service
public interface IHomeMenuService {
	/*public List<MenuDto> findSportHomeMenuList(HomeMenuDto dto);*/
	
	public PageResult<HomeMenuDto> findPage(HomeMenuDto dto,PageQuery page);
	
	public List<HomeMenuDto> findByMenuType(Integer menuType);
	
	public Integer save(HomeMenuDto HomeMenuDto) ;
	
	public HomeMenuDto findById(String id);
	
	public Integer edit(HomeMenuDto dto);
	
	public Integer delete(String ids);
	
	public List<HomeMenuDto> findByParentId(String parentId);
	
	public List<HomeMenuDto> findAll();
}
