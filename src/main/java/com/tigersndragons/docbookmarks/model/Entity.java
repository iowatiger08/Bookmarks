package com.tigersndragons.docbookmarks.model;

import javax.persistence.Transient;


public abstract class Entity {//implements Matchable<Entity> {
	public abstract Long getId();
//	public abstract boolean matches(Entity entity);
	@Transient
	public abstract String getComparatorString();
}
