package com.edoc.orm.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.edoc.dbsupport.PropertyFilter;

/**
 * 泛型DAO,统计的数据库访问接口,实现类{@link GenericDAOImpl}
 * 
 * @author 陈超
 * 
 * @param <T>
 * @param <PK>
 */
public interface GenericDAO<T, PK extends Serializable> {
	
	public void merge(T instance);
	/**
	 * 插入操作
	 * 
	 * @param instance
	 *            实例
	 */
	public void save(T instance);
	
	/**
	 * 插入多个对象
	 * @param instances
	 */
	public void save(List<T> instances);

	/**
	 * 修改操作,修改的对象数据库中必须存在,如果不存在则抛出异常
	 * 
	 * @param instance
	 *            修改的实例
	 */
	public void update(T instance);

	/**
	 * 插入或修改操作,如果数据库中不存在该数据则向数据库中插入一条新数据,相反则执行修改操作
	 * 
	 * @param instance
	 *            实例
	 */
	public void saveOrUpdate(T instance);

	/**
	 * 删除操作
	 * 
	 * @param instance
	 *            删除的实例
	 */
	public void delete(T instance);
	
	/**
	 * 删除操作
	 * @param ids
	 */
	public void delete(String[] ids);
	
	

	/**
	 * 根据实例在数据库中的主键获取相应的记录
	 * 
	 * @param pk
	 *            主键
	 * @return 返回pk所对应的记录,如果该记录不存在的话则返回null
	 */
	public T get(PK pk);

	/**
	 * 获取表中所有的记录
	 * 
	 * @return 返回记录集,如果不存在则返回null
	 */
	public List<T> getAll();

	/**
	 * 分页获取所有的记录
	 * 
	 * @param start
	 *            分页查询的起始地址
	 * @param limit
	 *            每页显示的记录数
	 * @return 返回查询到的结果
	 */
	public List<T> getAll(int start, int limit);

	/**
	 * 获取表中记录的个数
	 * 
	 * @return 返回表中的记录个数,如果没有记录则返回0
	 */
	public int getCount();

	/**
	 * 根据多个查询条件,查询表中记录个数
	 * 
	 * @param filters
	 *            查询条件
	 * @return 返回查询到的记录个数,如果不存在则返回0
	 */
	public int getCount(List<PropertyFilter> filters);

	/**
	 * 多条件查询
	 * 
	 * @param filters
	 *            查询条件
	 * @return 返回查询到的记录,如果不存在则返回null
	 */
	public List<T> find(List<PropertyFilter> filters);

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
	public List<T> find(List<PropertyFilter> filters, int start, int limit);

	/**
	 * 命名查询
	 * 
	 * @param queryName
	 *            查询名称
	 * @param params
	 *            命名查询参数
	 * @return 返回查询结果
	 */
	public List<T> namedQuery(String queryName, Map<String, Object> params);

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
	public List<T> namedQuery(String queryName, Map<String, Object> params,
			int start, int limit);

	/**
	 * 命名查询,获取记录数
	 * 
	 * @param queryName
	 *            查询名称
	 * @param params
	 *            命名查询参数
	 * @return 返回查询到的记录数
	 */
	public int getCountByNamedQuery(String queryName, Map<String, Object> params);

	/**
	 * 根据HQL语句分页查询(查询语句的语法为HQL的语法),例如:select * from Department m ,所有的hql查询语句
	 * 均已'm'作为类的别名
	 * 
	 * @param sql
	 *            查询语句
	 * @param start
	 *            分页查询的起始地址
	 * @param limit
	 *            每页显示的记录数
	 * @return
	 */
	public List<T> excuteQuerySQL(String sql, int start, int limit);

	/**
	 * 执行HQL语句,可以用来执行查询记录数操作
	 * 
	 * @param sql
	 *            查询语句
	 * @return
	 */
	public int excuteGetCountSQL(String sql);
	
	public int executeUpdate(String hql);
	
	
	@SuppressWarnings("unchecked")
	public List excuteQuery(String sql);
	
	public void update(String sql);
	
	public void delete(String sql);
}
