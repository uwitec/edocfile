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
 * GenericDao的实现类,该类采用了Hibernate框架来实现GenericDao中的方法
 * 
 * @author 陈超
 * 
 */
public class GenericDAOImpl<T, PK extends Serializable> extends
		HibernateDaoSupport implements GenericDAO<T, PK> {

	protected Class<T> clazz;
	
	public void merge(T instance){
		getSession(false).merge(instance);
	}
	/**
	 * 删除操作
	 * 
	 * @param instance
	 *            删除的实例
	 */
	public void delete(T instance) {
		getSession(false).delete(instance);
	}
	
	/**
	 * 删除操作
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
	 * 多条件查询
	 * 
	 * @param filters
	 *            查询条件
	 * @return 返回查询到的记录,如果不存在则返回null
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(List<PropertyFilter> filters) {
		// 创建Criteria
		Criteria criteria = getSession(false).createCriteria(clazz);
		// 创建查询条件
		createCriterionsFromPropertyFilter(filters, criteria);
		// 测试排序代码
		addPropertyOrders(criteria);

		return criteria.list();
	}

	/**
	 * 多条件分页查询
	 * 
	 * @param filters
	 *            查询条件
	 * @param start
	 *            分页查询的起始地址
	 * @param limit
	 *            每页显示的记录数
	 * @return 返回查询到的记录,如果不存在则返回null
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(List<PropertyFilter> filters, int start, int limit) {
		// 创建Criteria
		Criteria criteria = getSession(false).createCriteria(clazz);
		// 创建查询条件
		createCriterionsFromPropertyFilter(filters, criteria);
		// 测试排序代码
		addPropertyOrders(criteria);

		criteria.setFirstResult(start);
		criteria.setMaxResults(limit);
		return criteria.list();
	}

	/**
	 * 根据实例在数据库中的主键获取相应的记录
	 * 
	 * @param pk
	 *            主键
	 * @return 返回pk所对应的记录,如果该记录不存在的话则返回null
	 */
	@SuppressWarnings("unchecked")
	public T get(PK pk) {
		return (T) getSession(false).get(clazz, pk);
	}

	/**
	 * 为criteria添加类的默认排序
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
	 * 获取表中所有的记录
	 * 
	 * @return 返回记录集,如果不存在则返回null
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		Session session = getSession(false);
		Criteria criteria = session.createCriteria(clazz);

		criteria.add(Restrictions.eq("isDelete", 0));

		// 测试排序代码
		addPropertyOrders(criteria);

		List<T> result = criteria.list();
		if (isValid(result)) {
			return result;
		} else {
			return null;
		}
	}

	/**
	 * 分页获取所有的记录
	 * 
	 * @param start
	 *            分页查询的起始地址
	 * @param limit
	 *            每页显示的记录数
	 * @return 返回查询到的结果
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll(int start, int limit) {
		Session session = getSession(false);
		Criteria criteria = session.createCriteria(clazz);

		criteria.add(Restrictions.eq("isDelete", 0));

		// 测试排序代码
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
	 * 获取表中记录的个数
	 * 
	 * @return 返回表中的记录个数,如果没有记录则返回0
	 */
	public int getCount() {
		Session session = getSession(false);
		Criteria criteria = session.createCriteria(clazz).setProjection(
				Projections.rowCount());

		criteria.add(Restrictions.eq("isDelete", 0));
		return getCount(criteria);
	}

	/**
	 * 查询表中记录个数
	 * 
	 * @param filters
	 *            查询条件
	 * @return 返回查询到的记录个数,如果不存在则返回0
	 */
	public int getCount(List<PropertyFilter> filters) {
		// 创建Criteria
		Session session = getSession(false);
		Criteria criteria = session.createCriteria(clazz).setProjection(
				Projections.rowCount());
		// 创建查询条件
		createCriterionsFromPropertyFilter(filters, criteria);

		return getCount(criteria);
	}

	/**
	 * 插入操作
	 * 
	 * @param instance
	 *            实例
	 */
	public void save(T instance) {
		getSession(false).save(instance);
	}
	
	/**
	 * 插入多个对象
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
					//20，与JDBC批量设置相同    
					//将本批插入的对象立即写入数据库并释放内存    
					session.flush();    
					session.clear();    
				}    
			}
		}
	}

	/**
	 * 插入或修改操作,如果数据库中不存在该数据则向数据库中插入一条新数据,相反则执行修改操作
	 * 
	 * @param instance
	 *            实例
	 */
	public void saveOrUpdate(T instance) {
		getSession(false).saveOrUpdate(instance);
	}

	/**
	 * 修改操作,修改的对象数据库中必须存在,如果不存在则抛出异常
	 * 
	 * @param instance
	 *            修改的实例
	 */
	public void update(T instance) {
		getSession(false).clear();
		getSession(false).update(instance);
	}

	/**
	 * 命名查询
	 * 
	 * @param queryName
	 *            查询名称
	 * @param params
	 *            命名查询参数
	 * @return 返回查询结果
	 */
	@SuppressWarnings("unchecked")
	public List<T> namedQuery(String queryName, Map<String, Object> params)
			throws NullPointerException {
		if (!StringUtils.isValid(queryName)) {
			throw new java.lang.NullPointerException("命名查询时,参数'queryName'为null");
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
	 * 命名查询
	 * 
	 * @param queryName
	 *            查询名称
	 * @param params
	 *            命名查询参数
	 * @param start
	 *            分页查询起始地址
	 * @param limit
	 *            每页显示的记录数
	 * @return 返回查询结果
	 */
	@SuppressWarnings("unchecked")
	public List<T> namedQuery(String queryName, Map<String, Object> params,
			int start, int limit) {
		if (!StringUtils.isValid(queryName)) {
			throw new java.lang.NullPointerException("命名查询时,参数'queryName'为null");
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
	 * 命名查询,获取记录数
	 * 
	 * @param queryName
	 *            查询名称
	 * @param params
	 *            命名查询参数
	 * @return 返回查询到的记录数
	 */
	public int getCountByNamedQuery(String queryName, Map<String, Object> params) {
		if (!StringUtils.isValid(queryName)) {
			throw new java.lang.NullPointerException("命名查询时,参数'queryName'为null");
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
	 * 创建命名查询Query
	 * 
	 * @param queryName
	 *            命名查询名称
	 * @return 返回Query
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
		// 获取查询到的记录数
		Integer count = (Integer) criteria.uniqueResult();

		// 返回查询到的记录数,如果没有则返回0
		if (count != null) {
			return count.intValue();
		} else {
			return 0;
		}
	}

	/**
	 * 创建查询条件
	 * 
	 * @param filters
	 *            查询参数和条件的
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
	 * 根据SQL语句分页查询
	 * 
	 * @param sql
	 *            查询语句
	 * @param start
	 *            分页查询的起始地址
	 * @param limit
	 *            每页显示的记录数
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
	 * 执行HQL语句,可以用来执行查询记录数操作
	 * 
	 * @param sql
	 *            查询语句
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
	 * 判断集合是否为空
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
