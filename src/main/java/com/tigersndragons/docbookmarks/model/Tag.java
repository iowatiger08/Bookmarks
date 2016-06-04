package com.tigersndragons.docbookmarks.model;

import java.io.Serializable;

public class Tag implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public Tag(Long id, String tageName) {
		this.id = id;
		this.tagName = tageName;
	}
	
	public Long id;
	public String tagName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tageName) {
		this.tagName = tageName;
	}
	
	

}
