package com.tigersndragons.docbookmarks.model;

public interface HasParent<T> {

	public T getParent();
	
	public void setParent(T obj);
}
