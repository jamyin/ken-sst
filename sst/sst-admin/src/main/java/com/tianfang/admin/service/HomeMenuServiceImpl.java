package com.tianfang.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.admin.dao.HomeMenuDao;
import com.tianfang.admin.dto.HomeMenuDto;
import com.tianfang.admin.dto.MenuDto;
import com.tianfang.admin.enums.MenuTypeEnums;
import com.tianfang.admin.pojo.HomeMenu;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;

@Service
public class HomeMenuServiceImpl implements IHomeMenuService {

	@Autowired
	private HomeMenuDao homeMenuDao;
	
	@Override
	public List<MenuDto> findHomeMenuList(HomeMenuDto dto) {
		List<HomeMenu> dataList = homeMenuDao.findHomeMenuList(dto);
		List<HomeMenuDto> resultList = BeanUtils.createBeanListByTarget(dataList, HomeMenuDto.class);

		HashMap<String,List<HomeMenuDto>> listMap = new HashMap<String,List<HomeMenuDto>>();
		
		List<HomeMenuDto> mapList = null;
		for(HomeMenuDto mDto : resultList){
			if(listMap.containsKey(mDto.getParentId())){
				mapList = listMap.get(mDto.getParentId());
				mapList.add(mDto);
			}else{
				mapList = new ArrayList<HomeMenuDto>();		
				mapList.add(mDto);
			}
			listMap.put(mDto.getParentId(),mapList);
		}
		List<MenuDto> dataResultList = new ArrayList<MenuDto>();
		MenuDto menuDto = null;
		for(HomeMenuDto sportMenuDto : resultList){
			if(Integer.valueOf(sportMenuDto.getMenuType()).intValue() == MenuTypeEnums.ONE.getIndex()){
				menuDto = new MenuDto();
				menuDto.setHomeMenuDto(sportMenuDto);
				menuDto.setSecondList(listMap.get(sportMenuDto.getId()));
				dataResultList.add(menuDto);
			}
		}

		return dataResultList;
	}
	
	
	public PageResult<HomeMenuDto> findPage(HomeMenuDto dto,PageQuery page) {
		List<HomeMenuDto> results = homeMenuDao.findPage(dto, page);
        page.setTotal(homeMenuDao.count(dto));
        return new PageResult<HomeMenuDto>(page,results);
	}
	
	public List<HomeMenuDto> findByMenuType(Integer menuType) {
		List<HomeMenuDto> results = homeMenuDao.findByMenuType(menuType);
		return results;
	}
	
	public Integer save(HomeMenuDto HomeMenuDto) {
		HomeMenu HomeMenu = BeanUtils.createBeanByTarget(HomeMenuDto, HomeMenu.class);
		return homeMenuDao.insert(HomeMenu);
	}
	
	public HomeMenuDto findById(String id) {
		HomeMenu homeMenu = homeMenuDao.selectByPrimaryKey(id);
		if(homeMenu!=null){
			return BeanUtils.createBeanByTarget(homeMenu, HomeMenuDto.class);
		}else{
			return null;
		}
	}
	
	public Integer edit(HomeMenuDto dto) {
		HomeMenu homeMenu = BeanUtils.createBeanByTarget(dto, HomeMenu.class);
		return homeMenuDao.updateByPrimaryKeySelective(homeMenu);
	}
	
	public Integer delete(String ids) {
		String[] idStr = ids.split(",");
        if (idStr.length>0) {
            for (String id:idStr) {
            	HomeMenu HomeMenu = homeMenuDao.selectByPrimaryKey(id);
            	HomeMenu.setStat(DataStatus.DISABLED);
            	homeMenuDao.updateByPrimaryKeySelective(HomeMenu);
            }
        }else{
        	HomeMenu HomeMenu = homeMenuDao.selectByPrimaryKey(ids);
        	HomeMenu.setStat(DataStatus.DISABLED);
        	homeMenuDao.updateByPrimaryKeySelective(HomeMenu);
        }
        return null;        
	}


	@Override
	public List<HomeMenuDto> findByParentId(String parentId) {
		HomeMenuDto dto = new HomeMenuDto();
		dto.setParentId(parentId);
		List<HomeMenu> dataList = homeMenuDao.findHomeMenuList(dto);
		return BeanUtils.createBeanListByTarget(dataList, HomeMenuDto.class);
	}

	@Override
	public List<HomeMenuDto> findAll() {
		List<HomeMenu> dataList = homeMenuDao.findHomeMenuList(null);
		return BeanUtils.createBeanListByTarget(dataList, HomeMenuDto.class);
	}
}