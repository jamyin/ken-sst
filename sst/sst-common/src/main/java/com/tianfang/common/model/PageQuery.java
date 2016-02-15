package com.tianfang.common.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class PageQuery implements Serializable{
	
	private static final long serialVersionUID = -3749696235922714829L;

	public PageQuery(){
	}
	
	public PageQuery(long total, int currPage, int pageSize){
		this.total = total;
		this.pageSize = pageSize;
		this.currPage = currPage;
	}
	
	public PageQuery(long total, int pageSize){
		this.pageSize = pageSize;
		this.total = total;
		currPage = 1;
	}

	public PageQuery(int pageSize){
		this.pageSize = pageSize;
		currPage = 1;
	}
	
	@Setter
	@Getter
	private long total;
	
	@Setter
	@Getter
	private int pageSize;
	
	@Setter
	@Getter
	private int currPage = 1;


	
	public long getStartNum(){
		return  pageSize * (currPage - 1);
	}
	
	public void nextPage(){
		currPage = currPage + 1;
	}

}
