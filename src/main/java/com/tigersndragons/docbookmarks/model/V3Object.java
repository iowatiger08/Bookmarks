package com.tigersndragons.docbookmarks.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.commons.lang.ObjectUtils;

@MappedSuperclass
public abstract class V3Object extends Entity implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof V3Object 
				&& obj !=null){
			return ObjectUtils.equals(this.id, ((V3Object) obj).getId())	;
		}
		return false;
	}
	public boolean matches(Entity entity) {
		if(entity == null){
			//A null here would be odd but this is a safety measure
			return false;
		}	
		return false;
	}
	@Override
	@Transient
	public String getComparatorString() {
		return toString();
	}
	@Override
	public String toString() {
		return super.toString() + "#" + getId();
	}
}
