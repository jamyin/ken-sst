package com.tianfang.train.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

/**		
 * <p>Title: CompetitionRoundDto </p>
 * <p>Description: 类描述:赛事场次</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月23日下午2:14:32
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
@JsonIgnoreProperties({"pageRanking","createAdminId","createAdminName","createTime","lastUpdateTime","stat","homeTeamId","visitingTeamId","homeScore","visitingScore","matchType","matchDateStr","matchTimeStr", "peopleType"})
public class CompetitionRoundDto implements Serializable{

	private static final long serialVersionUID = 2243909039276061203L;
	
	@Getter
	@Setter
	private String id;

	/**
	 * 赛事id
	 */
	@Getter
	@Setter
    private String compId;

    /**
     * 赛事名称
     */
    @Getter
    @Setter
    private String compName;

    /**
     * 比赛第几轮
     */
    @Getter
    @Setter
    private String name;
    
    /**
     * 页面排序
     */
    @Getter
    @Setter
    private Integer pageRanking;

    /**
     * 创建管理员Id
     */
    @Getter
    @Setter
    private String createAdminId;
    
    /**
     * 创建管理员
     */
    @Getter
    @Setter
    private String createAdminName;
    
    @Getter
    @Setter
    private Date createTime;
    
    @Getter
    @Setter
    private Date lastUpdateTime;

    @Getter
    @Setter
    private Integer stat;
    
    /**
     *  比赛几人制(1-11人制,2-7人制,3-5人制)
     */
    @Getter
    @Setter
    private Integer peopleType;
    
    @Getter
    @Setter
    private String[] homeTeamId;			// 主场球队id
    
    @Getter
    @Setter
    private String[] visitingTeamId;		// 客场球队id
    
    @Getter
    @Setter
    private int[] homeScore;				// 主场进球
    
    @Getter
    @Setter
    private int[] visitingScore;			// 客场进球
    
    @Getter
    @Setter
    private int[] matchType;				// 比赛状态
    
    @Getter
    @Setter
    private String[] matchDateStr;			// 比赛日期
    
    @Getter
    @Setter
    private String[] matchTimeStr;			// 比赛时间
}