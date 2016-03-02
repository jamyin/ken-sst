package com.tianfang.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.business.dao.AlbumDao;
import com.tianfang.business.dao.AlbumPicDao;
import com.tianfang.business.dto.AlbumDto;
import com.tianfang.business.pojo.Album;
import com.tianfang.business.service.IAlbumService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.JsonUtil;
import com.tianfang.common.util.StringUtils;


@Service
public class AlbumServiceImpl implements IAlbumService {

	@Autowired
	private AlbumDao albumDao;

	@Autowired
	private AlbumPicDao albumPicDao;
	
	
	/** 球队相册显示
	 * @author YIn
	 * @time:2015年11月14日 下午1:16:27
	 */
	@Override
	public PageResult<AlbumDto> findAlbumByPage(AlbumDto albumDto, PageQuery page) {
		Album album = BeanUtils.createBeanByTarget(albumDto,Album.class);
    	List<Album> albumList = albumDao.selectAlbumByPage(album, page);
    	List<AlbumDto> dtoList = new ArrayList<AlbumDto>();
    	if(albumList.size()>0){
    		dtoList = BeanUtils.createBeanListByTarget(albumList, AlbumDto.class);
    	for(AlbumDto s : dtoList){
    		if(s.getCreateTime() != null){
    			s.setCreateTimeStr(JsonUtil.parseDateStr(s.getCreateTime(), "yyyy-MM-dd"));
 	    	}else{
 	    		s.setCreateTimeStr("");
 	    	}
 	    	if(s.getLastUpdateTime()!=null){s.setLastUpdateTimeStr(JsonUtil.parseDateStr(s.getLastUpdateTime(),"yyyy-MM-dd"));
 	    	}
 	    	else{
 	    		s.setLastUpdateTimeStr("");
 	    	}
 	    }
    	}
		long total = albumDao.selectAccount(album);
		page.setTotal(total);
    	return new PageResult<AlbumDto>(page, dtoList);
	}
	
	/**
	 * @author YIn
	 * @time:2015年11月14日 下午5:24:19
	 */
	@Override
	public int addAlbum(AlbumDto albumDto) {
		Album album = BeanUtils.createBeanByTarget(albumDto,Album.class);
		if(!StringUtils.isEmpty(album.getId())){
			return albumDao.updateByPrimaryKeySelective(album);	
		}else{
			return albumDao.insertSelective(album);
		}
	}

	/**
	 * @author YIn
	 * @time:2015年11月14日 下午5:24:19
	 */
	@Override
	public int updateAlbum(AlbumDto albumDto) {
		Album album = BeanUtils.createBeanByTarget(albumDto,Album.class);
		int status = albumDao.updateByPrimaryKeySelective(album);
		return status;
	}

	/**
	 * @author YIn
	 * @time:2015年11月14日 下午5:24:19
	 */
	@Override
	public int delAlbum(String id) {
		int status = albumDao.deleteByPrimaryKey(id);
		return status;
	}

	/**
	 * @author YIn
	 * @time:2015年11月16日 下午3:16:19
	 */
	@Override
	public Integer delByIds(String ids) {
		  String[] idArr = ids.split(",");
	        for (String id : idArr) {
	        	Album album = albumDao.selectByPrimaryKey(id);
	            if (null == album) {
	                return 0;//无此条记录
	            }
	            album.setStat(DataStatus.DISABLED);
	            albumDao.updateByPrimaryKeySelective(album);
	        }
	        return 1;
	}

	/**
	 * @author YIn
	 * @time:2016年3月2日 上午9:08:44
	 */
	@Override
	public List<AlbumDto> findAlbum(AlbumDto albumDto) {
		Album album = BeanUtils.createBeanByTarget(albumDto,Album.class);
		List<Album> list  = albumDao.selectByParameter(album);
		return BeanUtils.createBeanListByTarget(list, AlbumDto.class);
	}

}
