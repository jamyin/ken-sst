package com.tianfang.business.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class VideoDto implements Serializable {

		@Getter
		@Setter
	    private String id;

		@Getter
		@Setter
	    private String video;

		@Getter
		@Setter
	    private String publishPeople;

		@Getter
		@Setter
	    private Integer pageRanking;

		@Getter
		@Setter
	    private Integer videoStatus;

		@Getter
		@Setter
	    private Integer marked;

		@Getter
		@Setter
	    private Date lastUpdateTime;

		@Getter
		@Setter
	    private Date createTime;
		
		@Getter
		@Setter
	    private String lastUpdateTimeStr;

		@Getter
		@Setter
	    private String createTimeStr;

		@Getter
		@Setter
	    private Integer stat;

		@Getter
		@Setter
	    private String videoName;
		
		@Getter
		@Setter
		private String teamId;
		
		@Getter
		@Setter
		private String gameId;
		
		@Getter
		@Setter
		private String videoThumb;
				
		@Getter
		@Setter
		private String userId;
		
		@Getter
		@Setter
		private String described;
		
		@Getter
		@Setter
		private Integer videoType;	
		
		@Getter
        @Setter
        private Integer clickRate;
        
        @Getter
        @Setter
        private Integer browseQuantity;
}
