package com.tianfang.business.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianfang.business.dao.AlbumPicDao;
import com.tianfang.business.dto.AlbumPictureDto;
import com.tianfang.business.pojo.AlbumPicture;
import com.tianfang.business.service.IAlbumPicService;
import com.tianfang.common.constants.DataStatus;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
import com.tianfang.common.util.BeanUtils;
import com.tianfang.common.util.JsonUtil;


@Service
public class AlbumPicServiceImpl implements IAlbumPicService {

	@Autowired
	private AlbumPicDao albumPicDao;
	
	/** 球队相片显示
	 * @author YIn
	 * @time:2015年11月14日 下午1:16:27
	 */
	@Override
	public PageResult<AlbumPictureDto> findAlbumPicByPage(AlbumPictureDto albumPictureDto, PageQuery page) {
		AlbumPicture albumPic = new AlbumPicture();
    	BeanUtils.copyProperties( albumPictureDto,albumPic);
    	List<AlbumPictureDto> albumPicDtoList = albumPicDao.findAlbumPicByPage(albumPic, page);
    	if(albumPicDtoList.size()>0){
        	for(AlbumPictureDto s : albumPicDtoList){
        		if(s.getCreateTime() != null){
        			s.setCreateTimeStr(JsonUtil.parseDateStr(s.getCreateTime(), "yyyy-MM-dd"));
     	    	}else{
     	    		s.setCreateTimeStr("");
     	    	}
     	    	if(s.getLastUpdateTime()!=null){s.setLastUpdateTimeStr(JsonUtil.parseDateStr(s.getLastUpdateTime(),"yyyy-MM-dd"));
     	    	}else{
     	    		s.setLastUpdateTimeStr("");
     	    	}
     	    }
        	}
		long total = albumPicDao.selectAllAlbumPic(albumPic);
		page.setTotal(total);
    	return new PageResult<AlbumPictureDto>(page, albumPicDtoList);
	}

	/**
	 * @author YIn
	 * @time:2015年11月14日 下午5:24:19
	 */
	@Override
	public Integer addAlbumPic(AlbumPictureDto albumPictureDto) {
		AlbumPicture albumPic = BeanUtils.createBeanByTarget(albumPictureDto,AlbumPicture.class);
		int status = albumPicDao.insertSelective(albumPic);
		return status;
	}

	/**
	 * @author YIn
	 * @time:2015年11月14日 下午5:24:19
	 */
	@Override
	public int updateAlbumPic(AlbumPictureDto albumPictureDto) {
		AlbumPicture albumPic  = BeanUtils.createBeanByTarget(albumPictureDto,AlbumPicture.class);
		int status = albumPicDao.updateByPrimaryKeySelective(albumPic);
		return status;
	}

	/**
	 * @author YIn
	 * @time:2015年11月14日 下午5:24:19
	 */
	@Override
	public int delAlbumPic(String id) {
		//int status = albumPicDao.deleteByPrimaryKey(id);
		//逻辑删除
		AlbumPicture pic =new AlbumPicture();
		pic.setId(id);
		pic.setStat(DataStatus.DISABLED);
		int status = albumPicDao.updateByPrimaryKeySelective(pic);
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
	        	AlbumPicture pic = albumPicDao.selectByPrimaryKey(id);
	            if (null == pic) {
	                return 0;//无此条记录
	            }
	            pic.setStat(DataStatus.DISABLED);
	            albumPicDao.updateByPrimaryKeySelective(pic);
	        }
	        return 1;
	}

	@Override
	public AlbumPictureDto selectAlbumPic(AlbumPictureDto albumPicDto) {
		AlbumPicture albumPic  = BeanUtils.createBeanByTarget(albumPicDto,AlbumPicture.class);
		AlbumPicture AlbumPicture =  albumPicDao.selectAlbumPic(albumPic);
		AlbumPictureDto data = BeanUtils.createBeanByTarget(AlbumPicture, AlbumPictureDto.class);
		return data;
	}


}
