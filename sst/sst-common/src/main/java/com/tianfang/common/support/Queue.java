package com.tianfang.common.support;

public interface Queue<T> {

	void push(T t);
	
	T pop();
	
	T bPop();
	
	void empty();
}
