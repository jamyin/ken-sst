package com.tianfang.message.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**		
 * <p>Title: NewsDto </p>
 * <p>Description: 类描述:新闻</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年1月29日上午10:52:20
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
@JsonIgnoreProperties({"parentType", "createAdminId", "createAdminName", "createTime", "lastUpdateTime", "stat"})
public class InformationDto implements Serializable {

	private static final long serialVersionUID = -4608720377215170465L;

	@Getter
	@Setter
	private String id;

    /**
     * 标题
     */
    @Getter
    @Setter
    private String title;

    /**
     * 缩略图
     */
    @Getter
    @Setter
    private String pic;

    /**
     * 简介
     */
    @Getter
    @Setter
    private String summary;

    /**
     * 内容
     */
    @Getter
    @Setter
    private String content;
    
    /**
     * 一级类型(0-新闻,1-健康食谱)
     */
    @Getter
    @Setter
    private Integer parentType;
    
    /**
     * 二级类型(健康食谱:(0-常识,1-瘦身,2-食疗,3-实物归档,4-营养手册))
     */
    @Getter
    @Setter
    private Integer subType;

    /**
     * 创建者id
     */
    @Getter
    @Setter
    private String createAdminId;

    /**
     * 创建者
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
     * 分享全路径(包括参数)
     */
    @Getter
    @Setter
    private String shareUrl;
}