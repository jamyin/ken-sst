package com.tianfang.business.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
/**
 * @author YIn
 * @time:2015年11月13日 下午5:59:20
 * @Fields serialVersionUID : TODO
 */
public class AlbumDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
    private String id;

	@Getter
	@Setter
    private String title;

	@Getter
	@Setter
	private String thumbnail;
	
	@Getter
	@Setter
	private String publisher;
	
	@Getter
	@Setter
	private Integer pageRanking;
	
	@Getter
	@Setter
	private Date createTime;
	
	@Getter
	@Setter
	private Date lastUpdateTime;
	
	@Getter
	@Setter
	private String createTimeStr;    //页面显示时间
	
	@Getter
	@Setter
	private String lastUpdateTimeStr;    //页面显示修改时间 
	
	@Getter
	@Setter
	private Integer stat;
	
	@Getter
	@Setter
	private String teamId;
	
	@Getter
	@Setter
	private String teamName; //所属球队名
	
	@Getter
    @Setter
	private String gameId; //赛事
	
	@Getter
    @Setter
    private String teamPic; //赛事图片
	
	@Getter
    @Setter
	private Integer totalPic;//总共张数 
	
	@Getter
	@Setter
	private String described;
	
	@Getter
	@Setter
	private Integer randNumber; //随机数
	
	@Getter
	@Setter
	private Integer picType; //相片类型（4：教练员培训）
	
}