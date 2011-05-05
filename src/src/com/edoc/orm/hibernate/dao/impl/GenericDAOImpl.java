package com.edoc.orm.hibernate.dao.impl;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.edoc.dbsupport.PropertyFilter;
import com.edoc.dbsupport.PropertyOrder;
import com.edoc.dbsupport.PropertyFilter.MatchType;
import com.edoc.dbsupport.PropertyOrder.OrderType;
import com.edoc.entity.AbstractBaseEntity;
import com.edoc.orm.hibernate.dao.GenericDAO;
import com.edoc.utils.StringUtils;

/**
 * GenericDao��ʵ����,���������Hibernate�����ʵ��GenericDao�еķ���
 * 
 * @author �³�
 * 
 */
public class GenericDAOImpl<T, PK extends Serializable> extends
		HibernateDaoSupport implements GenericDAO<T, PK> {

	protected Class<T> clazz;
	
	public void merge(T instance){
		getSession(false).merge(instance);
	}
	/**
	 * ɾ������
	 * 
	 * @param instance
	 *            ɾ����ʵ��
	 */
	public void delete(T instance) {
		getSession(false).delete(instance);
	}
	
	/**
	 * ɾ������
	 * @param ids
	 */
	public void delete(String[] ids){
		if(ids!=null && ids.length>0){
			String className = clazz.getSimpleName();
			String hql = "delete "+className+" where id in (";  
			for(String id:ids){
				hql += "'"+id+"',";
			}
			hql = hql.substring(0, hql.length()-1);
			hql += ")";
			
			Query q = getSession(false).createQuery(hql);  
			q.executeUpdate();  
		}
	}

	/**
	 * ��������ѯ
	 * 
	 * @param filters
	 *            ��ѯ����
	 * @return ���ز�ѯ���ļ�¼,����������򷵻�null
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(List<PropertyFilter> filters) {
		// ����Criteria
		Criteria criteria = getSession(false).createCriteria(clazz);
		// ������ѯ����
		createCriterionsFromPropertyFilter(filters, criteria);
		// �����������
		addPropertyOrders(criteria);

		return criteria.list();
	}

	/**
	 * ��������ҳ��ѯ
	 * 
	 * @param filters
	 *            ��ѯ����
	 * @param start
	 *            ��ҳ��ѯ����ʼ��ַ
	 * @param limit
	 *            ÿҳ��ʾ�ļ�¼��
	 * @return ���ز�ѯ���ļ�¼,����������򷵻�null
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(List<PropertyFilter> filters, int start, int limit) {
		// ����Criteria
		Criteria criteria = getSession(false).createCriteria(clazz);
		// ������ѯ����
		createCriterionsFromPropertyFilter(filters, criteria);
		// �����������
		addPropertyOrders(criteria);

		criteria.setFirstResult(start);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	/**
	 * ����ʵ�������ݿ��е�������ȡ��Ӧ�ļ�¼
	 * 
	 * @param pk
	 *            ����
	 * @return ����pk����Ӧ�ļ�¼,����ü�¼�����ڵĻ��򷵻�null
	 */
	@SuppressWarnings("unchecked")
	public T get(PK pk) {
		return (T) getSession(false).get(clazz, pk);
	}

	/**
	 * Ϊcriteria������Ĭ������
	 * 
	 * @param criteria
	 */
	private void addPropertyOrders(Criteria criteria) {

		try {
			T a = clazz.newInstance();
			AbstractBaseEntity b = (AbstractBaseEntity) a;
			List<PropertyOrder> propertyOrders = b.getPropertyOrders();

			if (propertyOrders != null) {
				Iterator<PropertyOrder> it = propertyOrders.iterator();
				while (it.hasNext()) {
					PropertyOrder propertyOrder = it.next();
					if (propertyOrder.getOrderType().equals(OrderType.ASC))
						criteria.addOrder(Order.asc(propertyOrder
								.getPropertyName()));
					else
						criteria.addOrder(Order.desc(propertyOrder
								.getPropertyName()));
				}
			} else {
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ�������еļ�¼
	 * 
	 * @return ���ؼ�¼��,����������򷵻�null
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		Session session = getSession(false);
		Criteria criteria = session.createCriteria(clazz);

		criteria.add(Restrictions.eq("isDelete", 0));

		// �����������
		addPropertyOrders(criteria);

		List<T> result = criteria.list();
		if (isValid(result)) {
			return result;
		} else {
			return null;
		}
	}

	/**
	 * ��ҳ��ȡ���еļ�¼
	 * 
	 * @param start
	 *            ��ҳ��ѯ����ʼ��ַ
	 * @param limit
	 *            ÿҳ��ʾ�ļ�¼��
	 * @return ���ز�ѯ���Ľ��
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll(int start, int limit) {
		Session session = getSession(false);
		Criteria criteria = session.createCriteria(clazz);

		criteria.add(Restrictions.eq("isDelete", 0));

		// �����������
		addPropertyOrders(criteria);

		criteria.setFirstResult(start);
		criteria.setMaxResults(limit);

		List<T> result = criteria.list();
		if (isValid(result)) {
			return result;
		} else {
			return null;
		}
	}

	/**
	 * ��ȡ���м�¼�ĸ���
	 * 
	 * @return ���ر��еļ�¼����,���û�м�¼�򷵻�0
	 */
	public int getCount() {
		Session session = getSession(false);
		Criteria criteria = session.createCriteria(clazz).setProjection(
				Projections.rowCount());

		criteria.add(Restrictions.eq("isDelete", 0));
		return getCount(criteria);
	}

	/**
	 * ��ѯ���м�¼����
	 * 
	 * @param filters
	 *            ��ѯ����
	 * @return ���ز�ѯ���ļ�¼����,����������򷵻�0
	 */
	public int getCount(List<PropertyFilter> filters) {
		// ����Criteria
		Session session = getSession(false);
		Criteria criteria = session.createCriteria(clazz).setProjection(
				Projections.rowCount());
		// ������ѯ����
		createCriterionsFromPropertyFilter(filters, criteria);

		return getCount(criteria);
	}

	/**
	 * �������
	 * 
	 * @param instance
	 *            ʵ��
	 */
	public void save(T instance) {
		getSession(false).save(instance);
	}
	
	/**
	 * ����������
	 * @param instances
	 */
	public void save(List<T> instances){
		Session session = getSession(false);
		if(instances!=null && instances.size()>0){
			int i = 0;
			for(T instance:instances){
				session.save(instance);
				i++;
				if ( i % 20 == 0 ) {    
					//20����JDBC����������ͬ    
					//����������Ķ�������д�����ݿⲢ�ͷ��ڴ�    
					session.flush();    
					session.clear();    
				}    
			}
		}
	}

	/**
	 * ������޸Ĳ���,������ݿ��в����ڸ������������ݿ��в���һ��������,�෴��ִ���޸Ĳ���
	 * 
	 * @param instance
	 *            ʵ��
	 */
	public void saveOrUpdate(T instance) {
		getSession(false).saveOrUpdate(instance);
	}

	/**
	 * �޸Ĳ���,�޸ĵĶ������ݿ��б������,������������׳��쳣
	 * 
	 * @param instance
	 *            �޸ĵ�ʵ��
	 */
	public void update(T instance) {
		getSession(false).clear();
		getSession(false).update(instance);
	}

	/**
	 * ������ѯ
	 * 
	 * @param queryName
	 *            ��ѯ����
	 * @param params
	 *            ������ѯ����
	 * @return ���ز�ѯ���
	 */
	@SuppressWarnings("unchecked")
	public List<T> namedQuery(String queryName, Map<String, Object> params)
			throws NullPointerException {
		if (!StringUtils.isValid(queryName)) {
			throw new java.lang.NullPointerException("������ѯʱ,����'queryName'Ϊnull");
		}
		Query query = this.createNameQuery(queryName, params);
		List<T> result = query.list();
		if (this.isValid(result)) {
			return result;
		} else {
			return null;
		}
	}

	/**
	 * ������ѯ
	 * 
	 * @param queryName
	 *            ��ѯ����
	 * @param params
	 *            ������ѯ����
	 * @param start
	 *            ��ҳ��ѯ��ʼ��ַ
	 * @param limit
	 *            ÿҳ��ʾ�ļ�¼��
	 * @return ���ز�ѯ���
	 */
	@SuppressWarnings("unchecked")
	public List<T> namedQuery(String queryName, Map<String, Object> params,
			int start, int limit) {
		if (!StringUtils.isValid(queryName)) {
			throw new java.lang.NullPointerException("������ѯʱ,����'queryName'Ϊnull");
		}
		Query query = this.createNameQuery(queryName, params);
		query.setFirstResult(start);
		query.setMaxResults(limit);

		List<T> result = query.list();
		if (this.isValid(result)) {
			return result;
		} else {
			return null;
		}
	}

	/**
	 * ������ѯ,��ȡ��¼��
	 * 
	 * @param queryName
	 *            ��ѯ����
	 * @param params
	 *            ������ѯ����
	 * @return ���ز�ѯ���ļ�¼��
	 */
	public int getCountByNamedQuery(String queryName, Map<String, Object> params) {
		if (!StringUtils.isValid(queryName)) {
			throw new java.lang.NullPointerException("������ѯʱ,����'queryName'Ϊnull");
		}
		Query query = this.createNameQuery(queryName, params);
		Long count = (Long) query.uniqueResult();
		if (count != null) {
			return count.intValue();
		} else {
			return 0;
		}
	}

	/**
	 * ����������ѯQuery
	 * 
	 * @param queryName
	 *            ������ѯ����
	 * @return ����Query
	 */
	private Query createNameQuery(String queryName, Map<String, Object> params) {
		Session session = getSession(false);

		Query query = session.getNamedQuery(queryName);
		if (params != null && !params.isEmpty()) {
			Iterator<String> paramNames = params.keySet().iterator();
			String paramName = null;
			while (paramNames.hasNext()) {
				paramName = paramNames.next();
				query.setParameter(paramName, params.get(paramName));
			}
		}
		return query;
	}

	private int getCount(Criteria criteria) {
		// ��ȡ��ѯ���ļ�¼��
		Integer count = (Integer) criteria.uniqueResult();

		// ���ز�ѯ���ļ�¼��,���û���򷵻�0
		if (count != null) {
			return count.intValue();
		} else {
			return 0;
		}
	}

	/**
	 * ������ѯ����
	 * 
	 * @param filters
	 *            ��ѯ������������
	 * @param criteria
	 */
	private void createCriterionsFromPropertyFilter(
			List<PropertyFilter> filters, Criteria criteria) {
		if (filters != null && !filters.isEmpty()) {
			MatchType matchType = null;
			Criterion criterion = null;
			for (PropertyFilter filter : filters) {
				matchType = filter.getMatchType();
				if (matchType.equals(MatchType.EQ)) {
					criterion = Restrictions.eq(filter.getPropertyName(),
							filter.getPropertyValue());
				} else if (matchType.equals(MatchType.GE)) {
					criterion = Restrictions.ge(filter.getPropertyName(),
							filter.getPropertyValue());
				} else if (matchType.equals(MatchType.GT)) {
					criterion = Restrictions.gt(filter.getPropertyName(),
							filter.getPropertyValue());
				} else if (matchType.equals(MatchType.LE)) {
					criterion = Restrictions.le(filter.getPropertyName(),
							filter.getPropertyValue());
				} else if (matchType.equals(MatchType.LT)) {
					criterion = Restrictions.lt(filter.getPropertyName(),
							filter.getPropertyValue());
				} else if (matchType.equals(MatchType.LIKE)) {
					criterion = Restrictions.like(filter.getPropertyName(), "%"
							+ filter.getPropertyValue() + "%");
				} else if (matchType.equals(MatchType.NE)) {
					criterion = Restrictions.ne(filter.getPropertyName(),
							filter.getPropertyValue());
				} else if (matchType.equals(MatchType.IN)) {
					criterion = Restrictions.in(filter.getPropertyName(),
							(Object[]) filter.getPropertyValue());
				}

				criteria.add(criterion);
			}
		}
		criteria.add(Restrictions.eq("isDelete", 0));
	}

	/**
	 * ����SQL����ҳ��ѯ
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @param start
	 *            ��ҳ��ѯ����ʼ��ַ
	 * @param limit
	 *            ÿҳ��ʾ�ļ�¼��
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> excuteQuerySQL(String sql, int start, int limit) {
		Session session = getSession(false);
		Query query = session.createSQLQuery(sql);
		if (limit > 0) {
			query.setFirstResult(start);
			query.setMaxResults(limit);
		}
		List<T> result = query.list();
		if (isValid(result)) {
			return result;
		} else {
			return null;
		}
	}

	/**
	 * ִ��HQL���,��������ִ�в�ѯ��¼������
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @return
	 */
	public int excuteGetCountSQL(String sql) {
		Session session = getSession(false);

		Query query = session.createSQLQuery(sql);

		java.math.BigInteger count = (java.math.BigInteger) query.uniqueResult();
		if (count != null) {
			return count.intValue();
		} else {
			return 0;
		}
	}

	public Class<T> getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	/**
	 * �жϼ����Ƿ�Ϊ��
	 * 
	 * @param result
	 * @return
	 */
	private boolean isValid(List<T> result) {
		if (result == null || result.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public int executeUpdate(String hql){
		Session session = getSession(false);
		Query query = session.createQuery(hql);
		return query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	public List excuteQuery(String sql){
		Session session = getSession(false);
		Query query = session.createSQLQuery(sql);
		return query.list();
	}
	
	public void update(String sql){
		Session session = getSession(false);
		Query query = session.createSQLQuery(sql);
		query.executeUpdate();
	}
	
	public void delete(String sql){
		Session session = getSession(false);
//		session.delete(sql, null);
		Query query = session.createSQLQuery(sql);
		query.executeUpdate();
	}
}
