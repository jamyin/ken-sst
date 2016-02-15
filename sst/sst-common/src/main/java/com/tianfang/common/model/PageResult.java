package com.tianfang.common.model;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class PageResult<T> implements Serializable{
	
	private static final long serialVersionUID = -3472252186240745272L;

	public PageResult(){
		
	}
	
	public PageResult(long total, int currPage, int pageSize, List<T> results){
		this.total = total;
		this.pageSize = pageSize;
		this.currPage = currPage;
		setTotalPage(total, pageSize);
	}
	
	public PageResult(long total, int pageSize, List<T> results){
		this.pageSize = pageSize;
		this.total = total;
		currPage = 1;
		setTotalPage(total, pageSize);
	}
	
	public PageResult(PageQuery query,  List<T> results){
		this.total = query.getTotal();
		this.pageSize = query.getPageSize();
		this.currPage = query.getCurrPage();
		this.results = results;
		setTotalPage(total, pageSize);
	}

	@Setter
	@Getter
	private long total;
	
	@Getter
	private long totalPage;  // 总页数
	
	@Setter
	@Getter
	private int pageSize;
	
	@Setter
	@Getter
	private int currPage = 1;
	
	@Setter
	@Getter
	private List<T> results;
	
	public long getStartNum(){
		return  pageSize * (currPage - 1);
	}
	
	/**
	 * 计算总页数
	 * @param total
	 * @param pageSize
	 * @author xiang_wang
	 * 2015年11月23日上午11:10:37
	 */
	private void setTotalPage(long total, int pageSize){
		if (total%pageSize == 0){
			this.totalPage = total/pageSize;
		}else{
			this.totalPage = total/pageSize + 1;
		}
	}
}
