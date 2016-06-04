package com.tigersndragons.docbookmarks.dao.impl;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.tigersndragons.docbookmarks.model.Entity;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.NaturalIdLoadAccess;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.NaturalIdentifier;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import com.tigersndragons.docbookmarks.dao.DocBookmarksDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocBookmarksDAOImpl implements DocBookmarksDAO {
	
	public DocBookmarksDAOImpl ( SessionFactory factory){
		super();
		this.sessionFactory = factory;
	}
	
	private static Logger logger = LoggerFactory.getLogger(DocBookmarksDAOImpl.class);

	private SessionFactory sessionFactory;

	
	private Session getSession() {
		Session session = sessionFactory.getCurrentSession();
		if(session.getFlushMode() != FlushMode.COMMIT) {
			session.setFlushMode(FlushMode.COMMIT);
		}
		return session;
	}

	public void disableAutoFlush() {
		sessionFactory.getCurrentSession().setFlushMode(FlushMode.COMMIT);
	}
	
	public void flush() {
		sessionFactory.getCurrentSession().flush();
	}
	
	public void save(Object obj) {
		getSession().saveOrUpdate(obj);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T load(Class<T> clazz, Serializable id) {
		//need to use get here so we don't return a proxy
		return (T)getSession().get(clazz, id);
	}
	
	public <T> T findEntity(Class<T> clazz, Map<String, Object> naturalKey) {
		Set<String> keyValueSet = mapAsStringSet(naturalKey);
		
		if(isNaturalIdFields(clazz, keyValueSet)){
			return queryByNatId(clazz, naturalKey);
		}
		else if(isPrimaryKeyFields(clazz, keyValueSet)){
			return queryByPrimaryKey(clazz, naturalKey);
		} 
		else {
			return queryWithCriteria(clazz, naturalKey);
		}
	}
	
	private boolean isNaturalIdFields(Class<?> clazz, Set<String> fields){
		ClassMetadata metadata = sessionFactory.getClassMetadata(clazz);
		Set<String> naturalIdProps = new HashSet<String>();
		String[] propertyNames = metadata.getPropertyNames();
		int[] naturalIdIndexs = metadata.getNaturalIdentifierProperties();
		if(naturalIdIndexs == null){
			return false;
		}
		for(int index:naturalIdIndexs){
			naturalIdProps.add(propertyNames[index]);
		}
		return fields.equals(naturalIdProps);
	}
	
	private boolean isPrimaryKeyFields(Class<?> clazz, Set<String> fields){
		ClassMetadata metadata = sessionFactory.getClassMetadata(clazz);
		if(fields.size() != 1){
			return false;
		}
		String primaryKey = metadata.getIdentifierPropertyName();
		return primaryKey.equals(fields.iterator().next());
	}
	
	@SuppressWarnings("unchecked")
	private <T> T queryByNatId(Class<T> clazz, Map<String, Object> naturalKey){
		NaturalIdLoadAccess naturalId = getSession().byNaturalId(clazz);
		for(Entry<String, Object> entry:naturalKey.entrySet()){
			naturalId.using(entry.getKey(), entry.getValue());
		}
		naturalId.setSynchronizationEnabled(false);
		return (T) naturalId.load();
	}
	@SuppressWarnings("unchecked")
	private <T> T queryByPrimaryKey(Class<T> clazz, Map<String, Object> naturalKey){
		Entry<String, Object> entry = naturalKey.entrySet().iterator().next();
		return (T) getSession().get(clazz, (Serializable) entry.getValue());
	}
	@SuppressWarnings("unchecked")
	private <T> T queryWithCriteria(Class<T> clazz, Map<String, Object> naturalKey){
		
		Criteria crit = getSession().createCriteria(clazz);
		NaturalIdentifier natId = new NaturalIdentifier();
		for(Entry<String, Object> entry:naturalKey.entrySet()){
			natId = natId.set(entry.getKey(), entry.getValue());
		}
		crit.add(natId);
		crit.setCacheable(true);
		List<T> results = crit.list();
		if(results.size() > 1){
			logger.warn("Returned {} items for natural key {} on class {} but expected 1. Using the first item", new Object[]{
				results.size(),
				naturalKey,
				clazz
			});
		}
		if(results.isEmpty()){
			return null;
		}else{
			return results.get(0);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<Object> queryWithGroupBy(Class<T> clazz, String property){
		Criteria crit = getSession().createCriteria(clazz);
		crit.setProjection(Projections.projectionList()
				.add(Projections.groupProperty(property), property));

		crit.setCacheable(true);
		return crit.list();	
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Entity> List<T> findByKeyValue(
			Class<T> clazz, String propertyName, Object value) {
		Criteria crit = getSession().createCriteria(clazz);
		if(value ==  null){
			crit.add(Restrictions.isNull(propertyName));
		}else{
			crit.add(Restrictions.eq(propertyName, value));
		}
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	public <T > List<T> retrieveEntities(	Class<T> clazz) {
		Criteria crit = getSession().createCriteria(clazz);
		crit.addOrder(Order.desc("id"));
		return crit.list();
	}

	public Set<String> mapAsStringSet(Map<String, Object> naturalKey){
		Set<String> mapSet = new HashSet<String>();
		for(Entry<String, Object> entry: naturalKey.entrySet()){
			mapSet.add(entry.getKey());
		}
		return mapSet;
	}

	public <T> T remove(T value) {
		getSession().delete(value);
		return (T) value;
	}

}
