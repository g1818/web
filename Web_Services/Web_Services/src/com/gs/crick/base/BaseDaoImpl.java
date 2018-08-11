package com.gs.crick.base;



import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
@Repository
public class BaseDaoImpl implements IBaseDao {

	@PersistenceContext
	protected EntityManager em;

	public void setEm(EntityManager eMgr) {
		this.em = eMgr;
	}

	public EntityManager getEm() {
		return em;
	}

	@Override
	public Object persist(Object entity) {
		em.persist(entity);
		return entity;
	}

	@Override
	public Object update(Object entity) {
		return em.merge(entity);

	}

	@Override
	public Object remove(Object entity) {

		em.remove(em.contains(entity) ? entity : em.merge(entity));
		return entity;
	}

	@Override
	public <T> List<T> findAll(Class<T> entityClass) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cQuery = cb.createQuery(entityClass);
		Root<T> from = cQuery.from(entityClass); // from clause
		cQuery.select(from);// select clause
		cQuery.orderBy(cb.asc(from.get("id")));
		TypedQuery<T> typedQuery = em.createQuery(cQuery);
		return typedQuery.getResultList();
	}

	@Override
	public <T> List<T> find(Class<T> entityClass, String whereString,
			Object primaryKey) {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<T> cQuery = cb.createQuery(entityClass);
			Root<T> from = cQuery.from(entityClass); // from clause
			cQuery.select(from);// select clause
			cQuery.where(cb.equal(from.get(whereString), primaryKey));
			TypedQuery<T> typedQuery = em.createQuery(cQuery);
			return typedQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public <T> List<T> search(Class<T> entityClass,
			Map<String, Object> searchKey) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cQuery = cb.createQuery(entityClass);
		Root<T> from = cQuery.from(entityClass);
		cQuery.select(from);
		Predicate condition = cb.conjunction();
		Set<String> it = searchKey.keySet();
		for (String key : it) {
			condition = cb.and(condition,
					cb.equal(from.get(key), searchKey.get(key)));
		}

		cQuery.where(condition);
		TypedQuery<T> typedQuery = em.createQuery(cQuery);
		return typedQuery.getResultList();
	}

	@Override
	public <T> List<T> findlike(Class<T> entityClass, String whereString,
			Object searchkey) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cQuery = cb.createQuery(entityClass);
		Root<T> from = cQuery.from(entityClass); // from clause
		Expression<String> exp = from.get(whereString);
		cQuery.select(from);// select clause
		cQuery.where(cb.like(exp, (String) searchkey));
		cQuery.orderBy(cb.asc(from.get("id")));
		TypedQuery<T> typedQuery = em.createQuery(cQuery);
		return typedQuery.getResultList();
	}

	@Override
	public <T> Double sum(Class<T> entityClass, Object columnToSum,
			String whereString, Object searchKey) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Double> cQuery = cb.createQuery(Double.class);
		Root<T> from = cQuery.from(entityClass); // from clause
		Expression<Double> exp = from.get((String) columnToSum);
		cQuery.select(cb.sum(exp));// select clause
		cQuery.where(cb.equal(from.get(whereString), searchKey));
		TypedQuery<Double> typedQuery = em.createQuery(cQuery);
		return (Double) typedQuery.getSingleResult();
	}

	@Override
	public <T> Integer sumInt(Class<T> entityClass, Object columnToSum,
			String whereString, Object searchKey) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Integer> cQuery = cb.createQuery(Integer.class);
		Root<T> from = cQuery.from(entityClass); // from clause
		Expression<Integer> exp = from.get((String) columnToSum);
		cQuery.select(cb.sum(exp));// select clause
		cQuery.where(cb.equal(from.get(whereString), searchKey));
		TypedQuery<Integer> typedQuery = em.createQuery(cQuery);
		return (Integer) typedQuery.getSingleResult();
	}


	@Override
	public EntityTransaction getTransaction() {

		return em.getTransaction();
	}

	@Override
	public <T> T merge(T entity) {
		return em.merge(entity);
	}


	public <T> List<T> findInParameter(Class<T> entityClass,
			String whereString, List<Object> list) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cQuery = cb.createQuery(entityClass);
		Root<T> from = cQuery.from(entityClass); // from clause
		cQuery.select(from).where(cb.in(from.get(whereString)).value(list));// select
																			// clause
		TypedQuery<T> typedQuery = em.createQuery(cQuery);
		return typedQuery.getResultList();
	}

	@Override
	public <T> List<T> findAll(Class<T> entityClass, String id) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cQuery = cb.createQuery(entityClass);
		Root<T> from = cQuery.from(entityClass); // from clause
		cQuery.select(from);// select clause
		cQuery.orderBy(cb.asc(from.get(id)));
		TypedQuery<T> typedQuery = em.createQuery(cQuery);
		return typedQuery.getResultList();
	}

	

	@Override
	public <T> List<T> searchOrderBy(Class<T> entityClass,
			Map<String, Object> searchKey) {

		return searchOrder(entityClass, searchKey, "desc");
	}

	private <T> List<T> searchOrder(Class<T> entityClass,
			Map<String, Object> searchKey, String order) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cQuery = cb.createQuery(entityClass);
		Root<T> from = cQuery.from(entityClass);
		cQuery.select(from);
		Predicate condition = cb.conjunction();
		Set<String> it = searchKey.keySet();
		for (String key : it) {
			condition = cb.and(condition,
					cb.equal(from.get(key), searchKey.get(key)));
		}

		cQuery.where(condition);
		if (order.equalsIgnoreCase("desc")) {
			cQuery.orderBy(cb.desc(from.get("id")));
		} else if (order.equalsIgnoreCase("asc")) {
			cQuery.orderBy(cb.asc(from.get("id")));
		}
		TypedQuery<T> typedQuery = em.createQuery(cQuery);
		return typedQuery.getResultList();
	}

	@Override
	public <T> List<T> searchOrderByAsc(Class<T> entityClass,
			Map<String, Object> searchKey) {
		return searchOrder(entityClass, searchKey, "asc");
	}

}
