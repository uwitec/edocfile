package com.edoc.orm.hibernate.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.edoc.dbsupport.PropertyFilter;

/**
 * ����DAO,ͳ�Ƶ����ݿ���ʽӿ�,ʵ����{@link GenericDAOImpl}
 * 
 * @author �³�
 * 
 * @param <T>
 * @param <PK>
 */
public interface GenericDAO<T, PK extends Serializable> {
	
	public void merge(T instance);
	/**
	 * �������
	 * 
	 * @param instance
	 *            ʵ��
	 */
	public void save(T instance);
	
	/**
	 * ����������
	 * @param instances
	 */
	public void save(List<T> instances);

	/**
	 * �޸Ĳ���,�޸ĵĶ������ݿ��б������,������������׳��쳣
	 * 
	 * @param instance
	 *            �޸ĵ�ʵ��
	 */
	public void update(T instance);

	/**
	 * ������޸Ĳ���,������ݿ��в����ڸ������������ݿ��в���һ��������,�෴��ִ���޸Ĳ���
	 * 
	 * @param instance
	 *            ʵ��
	 */
	public void saveOrUpdate(T instance);

	/**
	 * ɾ������
	 * 
	 * @param instance
	 *            ɾ����ʵ��
	 */
	public void delete(T instance);
	
	/**
	 * ɾ������
	 * @param ids
	 */
	public void delete(String[] ids);
	
	

	/**
	 * ����ʵ�������ݿ��е�������ȡ��Ӧ�ļ�¼
	 * 
	 * @param pk
	 *            ����
	 * @return ����pk����Ӧ�ļ�¼,����ü�¼�����ڵĻ��򷵻�null
	 */
	public T get(PK pk);

	/**
	 * ��ȡ�������еļ�¼
	 * 
	 * @return ���ؼ�¼��,����������򷵻�null
	 */
	public List<T> getAll();

	/**
	 * ��ҳ��ȡ���еļ�¼
	 * 
	 * @param start
	 *            ��ҳ��ѯ����ʼ��ַ
	 * @param limit
	 *            ÿҳ��ʾ�ļ�¼��
	 * @return ���ز�ѯ���Ľ��
	 */
	public List<T> getAll(int start, int limit);

	/**
	 * ��ȡ���м�¼�ĸ���
	 * 
	 * @return ���ر��еļ�¼����,���û�м�¼�򷵻�0
	 */
	public int getCount();

	/**
	 * ���ݶ����ѯ����,��ѯ���м�¼����
	 * 
	 * @param filters
	 *            ��ѯ����
	 * @return ���ز�ѯ���ļ�¼����,����������򷵻�0
	 */
	public int getCount(List<PropertyFilter> filters);

	/**
	 * ��������ѯ
	 * 
	 * @param filters
	 *            ��ѯ����
	 * @return ���ز�ѯ���ļ�¼,����������򷵻�null
	 */
	public List<T> find(List<PropertyFilter> filters);

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
	public List<T> find(List<PropertyFilter> filters, int start, int limit);

	/**
	 * ������ѯ
	 * 
	 * @param queryName
	 *            ��ѯ����
	 * @param params
	 *            ������ѯ����
	 * @return ���ز�ѯ���
	 */
	public List<T> namedQuery(String queryName, Map<String, Object> params);

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
	public List<T> namedQuery(String queryName, Map<String, Object> params,
			int start, int limit);

	/**
	 * ������ѯ,��ȡ��¼��
	 * 
	 * @param queryName
	 *            ��ѯ����
	 * @param params
	 *            ������ѯ����
	 * @return ���ز�ѯ���ļ�¼��
	 */
	public int getCountByNamedQuery(String queryName, Map<String, Object> params);

	/**
	 * ����HQL����ҳ��ѯ(��ѯ�����﷨ΪHQL���﷨),����:select * from Department m ,���е�hql��ѯ���
	 * ����'m'��Ϊ��ı���
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @param start
	 *            ��ҳ��ѯ����ʼ��ַ
	 * @param limit
	 *            ÿҳ��ʾ�ļ�¼��
	 * @return
	 */
	public List<T> excuteQuerySQL(String sql, int start, int limit);

	/**
	 * ִ��HQL���,��������ִ�в�ѯ��¼������
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @return
	 */
	public int excuteGetCountSQL(String sql);
	
	public int executeUpdate(String hql);
	
	
	@SuppressWarnings("unchecked")
	public List excuteQuery(String sql);
	
	public void update(String sql);
	
	public void delete(String sql);
}
