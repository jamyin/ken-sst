package com.tianfang.common.ext;

import com.tianfang.common.model.PageQuery;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class ExtPageQuery {
	
	@Setter
	@Getter
	private int limit=10;
	
	@Setter
	@Getter
	private int page=1;
	
	@Setter
	@Getter
	private int start=0;
	
	public PageQuery changeToPageQuery(){
		PageQuery query =  new PageQuery();
		query.setCurrPage(page);
		query.setPageSize(limit);
		return query;
	}
	
}
