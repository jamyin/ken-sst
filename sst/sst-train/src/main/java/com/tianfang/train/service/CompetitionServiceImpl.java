package com.tianfang.train.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.train.dao.CompetitionDao;
import com.tianfang.train.dto.CompetitionDto;
import com.tianfang.train.pojo.Competition;
import com.tianfang.train.service.ICompetitionService;


@Service
public class CompetitionServiceImpl implements ICompetitionService {

	@Autowired
	private CompetitionDao competitionDao;

	/**
	 * @author YIn
	 * @time:2016年1月14日 上午11:20:18
	 */
	@Override
	public int addCompetition(CompetitionDto competitionDto) {
		Competition competition = BeanUtils.createBeanByTarget(competitionDto, Competition.class);
		return competitionDao.insertSelective(competition);
	}

	/**
	 * 根据主键Id更新
	 * @author YIn
	 * @time:2016年1月14日 上午11:28:38
	 */
	@Override
	public int updateCompetition(CompetitionDto competitionDto) {
		Competition competition = BeanUtils.createBeanByTarget(competitionDto, Competition.class);
		return competitionDao.updateByPrimaryKeySelective(competition);
	}

	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月14日 上午11:51:34
	 */
	@Override
	public int delCompetition(CompetitionDto competitionDto) {
		Competition competition = BeanUtils.createBeanByTarget(competitionDto, Competition.class);
		return competitionDao.deleteByPrimaryKey(competition.getId());
	}
	
	/**
	 * 批量逻辑删除
	 * @author YIn
	 * @time:2016年1月14日 下午6:12:19
	 */
	@Override
	public Integer delByIds(String ids) {
		  String[] idArr = ids.split(",");
	        for (String id : idArr) {
	        	Competition competition = competitionDao.selectByPrimaryKey(id);
	            if (null == competition) {
	                return 0;//无此条记录
	            }
	            competition.setStat(DataStatus.DISABLED);
	            competitionDao.updateByPrimaryKeySelective(competition);
	        }
	        return 1;
	}
	
	@Override
	public CompetitionDto getCompById(String id) {
		checkIdIsNull(id);
		CompetitionDto dto = BeanUtils.createBeanByTarget(competitionDao.selectByPrimaryKey(id), CompetitionDto.class);
		return dto;
	}
	
	/**
	 * @author YIn
	 * @time:2016年1月14日 下午1:17:12
	 */
	@Override
	public List<CompetitionDto> findCompetition(CompetitionDto competitionDto) {
		Competition competition = BeanUtils.createBeanByTarget(competitionDto, Competition.class);
		List<Competition> list = competitionDao.selectByParameter(competition);
		List<CompetitionDto> dtoList = BeanUtils.createBeanListByTarget(list, CompetitionDto.class);
		return dtoList;
	}

	@Override
	public List<CompetitionDto> findAllCompetition() {
		List<Competition> list = competitionDao.selectByParameter(null);
		if (!CollectionUtils.isEmpty(list)){
			return BeanUtils.createBeanListByTarget(list, CompetitionDto.class);
		}
		return null;
	}
	
	/**
	 * @author YIn
	 * @time:2016年1月14日 下午2:18:10
	 */
	@Override
	public PageResult<CompetitionDto> findCompetitionViewByPage(CompetitionDto competitionDto , PageQuery page) {
		Competition competition = BeanUtils.createBeanByTarget(competitionDto, Competition.class);
		List<Competition> list = competitionDao.findcompetitionViewByPage(competition,page);
		int total = competitionDao.selectAccount(competition);
		page.setTotal(total);
		List<CompetitionDto> dtoList = BeanUtils.createBeanListByTarget(list, CompetitionDto.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		for(CompetitionDto dto : dtoList){
			if(dto.getCreateTime() != null){
			dto.setCreateTimeStr(sdf.format(dto.getCreateTime()));}
			if(dto.getLastUpdateTime() != null){
			dto.setLastUpdateTimeStr(sdf.format(dto.getLastUpdateTime()));
			}
			if(dto.getStartDate() != null){
				dto.setStartDateStr(sdf.format(dto.getStartDate()));
			}
			if(dto.getEndDate() != null){
				dto.setEndDateStr(sdf.format(dto.getEndDate()));
			}
		}
		return new PageResult<CompetitionDto>(page, dtoList);
	}
	
	private void checkIdIsNull(String id) {
		if (StringUtils.isBlank(id)){
			throw new RuntimeException("对不起,赛事主键ID为空!");
		}
	}
}