package com.gs.crick.base;



import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public interface IBaseDao {

	public Object persist(Object entity);

	EntityManager getEm();

	<T> T merge(T entity);

	public <T> List<T> findAll(Class<T> entityClass);

	public <T> List<T> find(Class<T> entityClass, String whereString,
			Object primaryKey);

	public <T> List<T> findlike(Class<T> entityClass, String whereString,
			Object primaryKey);

	public Object update(Object entity);

	public Object remove(Object entity);

	public <T> Double sum(Class<T> entityClass, Object columnToSum,
			String whereString, Object searchKey);

	public <T> Integer sumInt(Class<T> entityClass, Object columnToSum,
			String whereString, Object searchKey);

	public <T> List<T> search(Class<T> entityClass,
			Map<String, Object> searchKey);

	EntityTransaction getTransaction();


	public <T> List<T> findAll(Class<T> entityClass, String id);
	
	
	public <T> List<T> searchOrderBy(Class<T> entityClass,
			Map<String, Object> searchKey);
	
	public <T> List<T> searchOrderByAsc(Class<T> entityClass,
			Map<String, Object> searchKey);
	
}
