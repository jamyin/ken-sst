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
}
