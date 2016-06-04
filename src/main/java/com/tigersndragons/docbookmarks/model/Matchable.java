package com.tigersndragons.docbookmarks.model;

public interface Matchable<T> {
	public boolean matches(T matchee);
}
