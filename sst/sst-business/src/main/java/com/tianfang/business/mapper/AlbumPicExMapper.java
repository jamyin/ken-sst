package com.tianfang.business.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tianfang.business.dto.AlbumPictureDto;
import com.tianfang.business.pojo.AlbumPicture;
import com.tianfang.common.model.PageQuery;

public interface AlbumPicExMapper {
	List<AlbumPictureDto> selectAlbumPicByPage(@Param("albumPicture") AlbumPicture albumPicture,@Param("page") PageQuery page);
	
	Long selectAlbumPicByPageCount(@Param("albumPicture")  AlbumPicture albumPicture);
	
	//void insertPictures(@Param("albumPicDto") AlbumPictureDto albumPicDto,@Param("list") List<String> pictures);

	//void updateAlbumPicList(@Param("albumId")String albumId);

	//List<AlbumPicture> findTeamAlbumPicByRand(@Param("albumPictureDto") AlbumPictureDto albumPictureDto);
	
	
	
}
