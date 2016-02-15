package com.tianfang.train.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.train.dao.TeamImageDao;
import com.tianfang.train.dto.TeamImageDto;
import com.tianfang.train.pojo.TeamImage;


@Service
public class TeamImageServiceImpl implements ITeamImageService {

	@Autowired
	private TeamImageDao teamImageDao;

	/**
	 * @author YIn
	 * @time:2016年1月27日 上午11:20:18
	 */
	@Override
	public int addTeamImage(TeamImageDto teamImageDto) {
		TeamImage TeamImage = BeanUtils.createBeanByTarget(teamImageDto, TeamImage.class);
		return teamImageDao.insertSelective(TeamImage);
	}

	/**
	 * 根据主键Id更新
	 * @author YIn
	 * @time:2016年1月27日 上午11:28:38
	 */
	@Override
	public int updateTeamImage(TeamImageDto teamImageDto) {
		TeamImage teamImage = BeanUtils.createBeanByTarget(teamImageDto, TeamImage.class);
		return teamImageDao.updateByPrimaryKeySelective(teamImage);
	}

	/**
	 * 根据主键Id删除 -物理删除
	 * @author YIn
	 * @time:2016年1月27日 上午11:51:34
	 */
	@Override
	public int delTeamImage(TeamImageDto teamImageDto) {
		TeamImage TeamImage = BeanUtils.createBeanByTarget(teamImageDto, TeamImage.class);
		return teamImageDao.deleteByPrimaryKey(TeamImage.getId());
	}
	
	/**
	 * 批量逻辑删除
	 * @author YIn
	 * @time:2016年1月27日 下午6:12:19
	 */
	@Override
	public Integer delByIds(String ids) {
		  String[] idArr = ids.split(",");
	        for (String id : idArr) {
	        	TeamImage TeamImage = teamImageDao.selectByPrimaryKey(id);
	            if (null == TeamImage) {
	                return 0;//无此条记录
	            }
	            TeamImage.setStat(DataStatus.DISABLED);
	            teamImageDao.updateByPrimaryKeySelective(TeamImage);
	        }
	        return 1;
	}
	
	/**
	 * @author YIn
	 * @time:2016年1月27日 下午1:17:12
	 */
	@Override
	public List<TeamImageDto> findTeamImage(TeamImageDto teamImageDto) {
		TeamImage teamImage = BeanUtils.createBeanByTarget(teamImageDto, TeamImage.class);
		List<TeamImage> list = teamImageDao.selectByParameter(teamImage);
		List<TeamImageDto> dtoList = BeanUtils.createBeanListByTarget(list, TeamImageDto.class);
		return dtoList;
	}

	/**
	 * @author YIn
	 * @time:2016年1月27日 下午2:18:10
	 */
	@Override
	public PageResult<TeamImageDto> findTeamImageViewByPage(TeamImageDto teamImageDto , PageQuery page) {
		TeamImage TeamImage = BeanUtils.createBeanByTarget(teamImageDto, TeamImage.class);
		List<TeamImage> list = teamImageDao.findTeamImageViewByPage(TeamImage,page);
		int total = teamImageDao.selectAccount(TeamImage);
		page.setTotal(total);
		List<TeamImageDto> dtoList = BeanUtils.createBeanListByTarget(list, TeamImageDto.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		for(TeamImageDto dto : dtoList){
			if(dto.getCreateTime() != null){
			dto.setCreateTimeStr(sdf.format(dto.getCreateTime()));}
			if(dto.getLastUpdateTime() != null){
			dto.setLastUpdateTimeStr(sdf.format(dto.getLastUpdateTime()));
			}
		}
		return new PageResult<TeamImageDto>(page, dtoList);
	}

	/**
	 * @author YIn
	 * @time:2016年1月22日 下午5:35:13
	 */
	@Override
	public PageResult<TeamImageDto> findTeamImageConnPage(TeamImageDto teamImageDto, PageQuery page) {
		List<TeamImageDto> list = teamImageDao.findTeamImageConn(teamImageDto,page);
		int total = teamImageDao.selectTeamImageConnAccount(teamImageDto);
		page.setTotal(total);
		List<TeamImageDto> dtoList = BeanUtils.createBeanListByTarget(list, TeamImageDto.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		for(TeamImageDto dto : dtoList){
			if(dto.getCreateTime() != null){
				dto.setCreateTimeStr(sdf.format(dto.getCreateTime()));}
			if(dto.getLastUpdateTime() != null){
				dto.setLastUpdateTimeStr(sdf.format(dto.getLastUpdateTime()));
			}
		}
		return new PageResult<TeamImageDto>(page, dtoList);
	}

	/**
	 * @author YIn
	 * @time:2016年1月29日 下午1:53:03
	 */
	@Override
	public Integer addTeamImageBatch(List<TeamImageDto> list) {
		return teamImageDao.addTeamImageBatch(list);
	}

}