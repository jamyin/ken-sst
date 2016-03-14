package com.tianfang.message.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.UUIDGenerator;
import com.tianfang.message.dao.InformationDao;
import com.tianfang.message.dto.InformationDto;
import com.tianfang.message.pojo.Information;

@Service
public class InformationServiceImpl implements IInformationService {

	@Autowired
	private InformationDao informationDao;
	
	@Override
	public String save(InformationDto dto) {
		checkObjIsNull(dto);
		Information obj = BeanUtils.createBeanByTarget(dto, Information.class);
		String id = UUIDGenerator.getUUID();
		obj.setId(id);
		informationDao.insertSelective(obj);
		return id;
	}

	@Override
	public void del(String id) {
		checkIdIsNull(id);
		Information obj = informationDao.selectByPrimaryKey(id);
		checkObjNotExist(obj);
		if (null != obj.getStat() && obj.getStat() == DataStatus.DISABLED){
			return;
		}
		obj.setStat(DataStatus.DISABLED);
		informationDao.updateByPrimaryKeySelective(obj);
	}

	@Override
	public void update(InformationDto dto) {
		checkObjIsNull(dto);
		checkIdIsNull(dto.getId());
		Information obj = BeanUtils.createBeanByTarget(dto, Information.class);
		informationDao.updateByPrimaryKeySelective(obj);
	}

	@Override
	public InformationDto getInformationById(String id) {
		checkIdIsNull(id);
		Information obj = informationDao.selectByPrimaryKey(id);
		if (null != obj && obj.getStat() == DataStatus.ENABLED){
			InformationDto dto = BeanUtils.createBeanByTarget(obj, InformationDto.class);
			return dto;
		}
		return null;
	}

	@Override
	public List<InformationDto> findInformationByParam(
			InformationDto dto) {
		List<InformationDto> list = informationDao.findInformationByParam(dto);
		if (null != list && list.size() > 0){
			return BeanUtils.createBeanListByTarget(list, InformationDto.class);
		}
		return null;
	}

	@Override
	public PageResult<InformationDto> findInformationByParam(
			InformationDto dto, PageQuery query) {
		int total = informationDao.countInformationByParam(dto);
		if (total > 0){
			query.setTotal(total);
			List<InformationDto> results = informationDao.findInformationByParam(dto, query);
			return new PageResult<InformationDto>(query, results);
		}
		return null;
	}

	private void checkObjIsNull(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,资讯对象为空!");
		}
	}
	
	private void checkIdIsNull(String id) {
		if (StringUtils.isBlank(id)){
			throw new RuntimeException("对不起,资讯对象主键ID为空!");
		}
	}
	
	private void checkObjNotExist(Object obj) {
		if (null == obj){
			throw new RuntimeException("对不起,资讯对象不存在!");
		}
	}

	@Override
	public List<InformationDto> findInformationByTop(Integer topNum,Integer enabled,Integer parentType) {
		List<Information> dataList = informationDao.findInformationByTop(topNum,enabled,parentType);
		List<InformationDto> objList = BeanUtils.createBeanListByTarget(dataList, InformationDto.class);
		return objList;
	}
}