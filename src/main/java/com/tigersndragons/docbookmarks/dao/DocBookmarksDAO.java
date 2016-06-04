package com.tigersndragons.docbookmarks.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.tigersndragons.docbookmarks.model.Entity;

public interface DocBookmarksDAO {
	//extends V3EntityDAO<T> {

	void save(Object target);

	public <T> T findEntity(Class<T> clazz, Map<String, Object> naturalKey);
	
//	public void deleteAll(List<?> objects);
	
	public <T extends Entity> List<T> findByKeyValue(Class<T> clazz, String propertyName, Object value);
	
	public <T> T load(Class<T> clazz, Serializable id);
		
	public <T > List<T> retrieveEntities(Class<T> clazz);

	/**
	 * turn off the auto flushing of entities to the backend for this session
	 */
	public void disableAutoFlush();

	/**
	 * Flush entities that have been passed to {@link #save(Object)} so that they are assigned IDs
	 */
	public void flush();
	
	public <T> T remove(T value);
	
	//public <T> List<T> queryWithGroupBy(Class<T> clazz, String property);
	

	public <T>   List<Object> queryWithGroupBy(Class<T> clazz, String property);

}
