package com.tianfang.business.dao;

import java.util.List;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianfang.business.dto.AlbumPictureDto;
import com.tianfang.business.mapper.AlbumPicExMapper;
import com.tianfang.business.mapper.AlbumPictureMapper;
import com.tianfang.business.pojo.AlbumPicture;
import com.tianfang.business.pojo.AlbumPictureExample;
import com.tianfang.business.pojo.AlbumPictureExample.Criteria;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.mybatis.MyBatisBaseDao;
import com.tianfang.common.util.StringUtils;

@Repository
public class AlbumPicDao extends MyBatisBaseDao<AlbumPicture>{
	
	@Autowired
	@Getter
	private AlbumPictureMapper mapper;
	
	@Autowired
	@Getter
	private AlbumPicExMapper mapperEx;
	
	/**
	 * @author YIn
	 * @time:2016年2月29日 下午4:52:17
	 */
	public AlbumPicture selectAlbumPic(AlbumPicture albumPic) {
		AlbumPictureExample example = new AlbumPictureExample();
		AlbumPictureExample.Criteria criteria = example.createCriteria();
		assemblyParams(albumPic, criteria);   //组装参数
        List<AlbumPicture> result = mapper.selectByExample(example); 
        if(result.size() > 0){
        	return result.get(0);
        }
        return null;
	}

	/**
	 * @author YIn
	 * @time:2016年2月29日 下午4:52:22
	 */
	public List<AlbumPictureDto> findAlbumPicByPage(AlbumPicture albumPic,
			PageQuery page) {

		return mapperEx.selectAlbumPicByPage(albumPic, page);
	}

	/**
	 * @author YIn
	 * @time:2016年2月29日 下午4:52:39
	 */
	public long selectAllAlbumPic(AlbumPicture albumPic) {

		return mapperEx.selectAlbumPicByPageCount(albumPic);
	}
	
	/**
	 * 组装参数
	 * @author YIn
	 * @time:2016年2月29日 下午2:29:58
	 * @param Album
	 * @param criteria
	 */
	private void assemblyParams(AlbumPicture albumPic,Criteria criteria) {
		if (StringUtils.isNotBlank(albumPic.getId())){
    		criteria.andIdEqualTo(albumPic.getId());
    	}
		if (StringUtils.isNotBlank(albumPic.getTitle())){
    		criteria.andTitleLike("%" +albumPic.getTitle()+"%");
    	}
    	criteria.andStatEqualTo(DataStatus.ENABLED);
	}
	
/*	
	public List<AlbumPictureDto> selectAlbumPicbyTeamId(AlbumPicture AlbumPicture, PageQuery page) {
		return mapperEx.selectAlbumPicByPage(AlbumPicture, page);
	}
	
	public long selectAllAlbumPic(AlbumPicture AlbumPicture) {
		return mapperEx.selectAlbumPicByPageCount(AlbumPicture);
	}
	
	public AlbumPicture selectAlbumPicById(String teamId) {
		AlbumPictureExample example = new AlbumPictureExample();
		AlbumPictureExample.Criteria criteria = example.createCriteria();
        criteria.andAlbumIdEqualTo(teamId);
        criteria.andStatEqualTo(DataStatus.ENABLED);
		return mapper.selectByPrimaryKey(teamId);
	}
	
	
	public List<AlbumPicture> findAlbumPicById(String id) {
	    AlbumPictureExample example = new AlbumPictureExample();
        AlbumPictureExample.Criteria criteria = example.createCriteria();
        criteria.andAlbumIdEqualTo(id);
        criteria.andStatEqualTo(DataStatus.ENABLED);
        return mapper.selectByExample(example);
	}
	
	public List<AlbumPicture> findTeamAlbumPic(AlbumPictureDto albumPictureDto) {
		AlbumPictureExample example = new AlbumPictureExample();
		Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(albumPictureDto.getTeamId())){
			criteria.andTeamIdEqualTo(albumPictureDto.getTeamId());	
		}

		if(!StringUtils.isEmpty(albumPictureDto.getAlbumId())){
			criteria.andAlbumIdEqualTo(albumPictureDto.getAlbumId());	
		}
		
		if(albumPictureDto.getPicType()!=null){
			criteria.andPicTypeEqualTo(albumPictureDto.getPicType());
		}
		if(albumPictureDto.getPicStatus()!=null){
			criteria.andPicStatusEqualTo(albumPictureDto.getPicStatus());
		}

		criteria.andStatEqualTo(DataStatus.ENABLED);
		if(!Objects.equal(albumPictureDto.getLimit(),null)){
			example.setOrderByClause("create_time desc limit " + albumPictureDto.getLimit());
		}
		return mapper.selectByExample(example);
	}

	public List<AlbumPicture> findByPage(AlbumPictureDto albumPictureDto,PageQuery page) {
		AlbumPictureExample example = new AlbumPictureExample();
		
		example = queryBySql(albumPictureDto);
		
		example.setOrderByClause("  create_time desc limit " + page.getStartNum() + ", " + page.getPageSize());
		List<AlbumPicture> list = mapper.selectByExample(example);
		return list;
	}

	public long count(AlbumPictureDto albumPictureDto) {
		AlbumPictureExample example = new AlbumPictureExample();
		example = queryBySql(albumPictureDto);
		return mapper.countByExample(example);
	}
	
	public AlbumPictureExample queryBySql(AlbumPictureDto albumPictureDto){
		AlbumPictureExample example = new AlbumPictureExample();
		Criteria criteria = example.createCriteria();
		
		if(!StringUtils.isEmpty(albumPictureDto.getAlbumId())){
			criteria.andAlbumIdEqualTo(albumPictureDto.getAlbumId());
		}
		if(!StringUtils.isEmpty(albumPictureDto.getTeamId())){
			criteria.andTeamIdEqualTo(albumPictureDto.getTeamId());
		}
		if(!StringUtils.isEmpty(albumPictureDto.getUserId())){
			criteria.andUserIdEqualTo(albumPictureDto.getUserId());
		}
		
		if(!StringUtils.isEmpty(albumPictureDto.getId())){
			criteria.andIdEqualTo(albumPictureDto.getId());
		}
		
		if(albumPictureDto.getPicStatus()!=null){
			criteria.andPicStatusEqualTo(albumPictureDto.getPicStatus());
		}
		if(albumPictureDto.getPicType()!=null){
			criteria.andPicTypeEqualTo(albumPictureDto.getPicType());
		}
		
		criteria.andStatEqualTo(DataStatus.ENABLED);
		
		return example;
	}

	public void insertPictures(AlbumPictureDto albumPicDto) {
		List<String> pictures = new ArrayList<String>();
		if(albumPicDto.getPictureList()!=null){
			pictures = Arrays.asList(albumPicDto.getPictureList());
		}
		albumPicDto.setStat(DataStatus.ENABLED);
		mapperEx.insertPictures(albumPicDto,pictures);
	}

	public void updateAlbumPicList(String albumId) {
		mapperEx.updateAlbumPicList(albumId);
	}

	public List<AlbumPicture> findTeamAlbumPicByRand(
			AlbumPictureDto albumPictureDto) {
		return mapperEx.findTeamAlbumPicByRand(albumPictureDto);
	}

	public AlbumPicture getAlbumPicByDto(AlbumPictureDto albumPicDto) {
		AlbumPictureExample example = new AlbumPictureExample();
		example = queryBySql(albumPicDto);
		List<AlbumPicture> list = mapper.selectByExample(example);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}*/
	
}
