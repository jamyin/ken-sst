package com.tianfang.business.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tianfang.business.dto.AlbumPictureDto;
import com.tianfang.common.model.PageQuery;
import com.tianfang.common.model.PageResult;
/**
 * @author YIn
 * @time:2016年2月29日 下午3:37:13
 * @ClassName: IAlbumPicService
 * @Description: katherine
 * @
 */
@Service("iAlbumPicService")
public interface IAlbumPicService {

	/**
	 * 增加相片
	 * @author YIn
	 * @time:2016年2月29日 下午4:06:55
	 * @param albumPictureDto
	 * @return
	 */
	Integer addAlbumPic(AlbumPictureDto albumPictureDto);

	/**
	 * 根据条件查询相片
	 * @author YIn
	 * @time:2016年2月29日 下午4:06:37
	 * @param dto
	 * @return
	 */
	AlbumPictureDto selectAlbumPic(AlbumPictureDto dto);

	/**
	 * 编辑相片
	 * @author YIn
	 * @time:2016年2月29日 下午4:07:06
	 * @param albumPictureDto
	 * @return
	 */
	int updateAlbumPic(AlbumPictureDto albumPictureDto);

	/**
	 * 根据Id删除
	 * @author YIn
	 * @time:2016年2月29日 下午4:07:24
	 * @param id
	 * @return
	 */
	int delAlbumPic(String id);

	/**
	 * 批量删除
	 * @author YIn
	 * @time:2016年2月29日 下午4:07:42
	 * @param ids
	 * @return
	 */
	Integer delByIds(String ids);

	/**
	 * 分页查询- 连接相册表查询
	 * @author YIn
	 * @time:2016年2月29日 下午4:07:49
	 * @param query
	 * @param changeToPageQuery
	 * @return
	 */
	PageResult<AlbumPictureDto> findAlbumPicByPage(AlbumPictureDto query,
			PageQuery changeToPageQuery);

	List<AlbumPictureDto> findTeamAlbumPic(AlbumPictureDto albumPictureDto);
	
	
	
}
