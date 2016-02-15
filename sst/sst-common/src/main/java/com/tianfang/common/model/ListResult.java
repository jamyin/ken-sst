package com.tianfang.common.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rkzhang on 14-9-25.
 */
@ToString
public class ListResult<T> implements Serializable{

	private static final long serialVersionUID = 614126614178797779L;

	public ListResult(){

    }

    public ListResult(List<T> results){
        this.success = true;
        this.results = results;
    }

    public ListResult(boolean success, String message){
        this.success = success;
        this.message = message;
    }

    public ListResult(boolean success, List<T> results){
        this.success = success;
        this.results = results;
    }

    public ListResult(boolean success, String message, List<T> results){
        this(success, message);
        this.results = results;
    }

    @Getter
    @Setter
    public boolean success;

    @Getter
    @Setter
    public String message;

    @Getter
    @Setter
    private List<T> results;
    
    @Setter
	@Getter
	private long total;
}
