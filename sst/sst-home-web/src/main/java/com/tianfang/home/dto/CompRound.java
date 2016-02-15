package com.tianfang.home.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.tianfang.train.dto.CompetitionMatchDto;

/**		
 * <p>Title: CompRound </p>
 * <p>Description: 类描述:移动端获取比赛场次下比赛数据</p>
 * <p>Copyright (c) 2015 </p>
 * <p>Company: 上海天坊信息科技有限公司</p>
 * @author xiang_wang	
 * @date 2016年2月2日下午2:57:57
 * @version 1.0
 * <p>修改人：</p>
 * <p>修改时间：</p>
 * <p>修改备注：</p>
 */
public class CompRound implements Serializable {

	private static final long serialVersionUID = 1741556603448826097L;

	@Getter
	@Setter
	private String id;
	
	@Getter
	@Setter
	private String name;
	
	@Getter
	@Setter
	private List<CompetitionMatchDto> matchs;
}