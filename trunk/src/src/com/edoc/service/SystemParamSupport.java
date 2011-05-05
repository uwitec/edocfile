package com.edoc.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.edoc.entity.baseinfo.User;
import com.edoc.orm.hibernate.dao.GenericDAO;

public class SystemParamSupport {
	@Resource(name="userDao")
	private GenericDAO<User,String> userDao=null;
	
	/**
	 * ��ȡϵͳ����
	 * @param type
	 * @return
	 * @author �³�
	 */
	public List<String[]> getSysParams(String type){
		
		return null;
	}
	
	/**
	 * ����ϵͳ����
	 * @param type		��������
	 * @param params	������Ϣ��key-��¼ID,value-����ֵ
	 * @author 			�³�
	 */
	public void setSysParam(String type,Map<String,String> params){
		if(params!=null && !params.isEmpty()){
			String sql = "update s_";
		}
	}
	
	public List<SystemParam> findSysParams(String type){
		List<SystemParam> params = new LinkedList<SystemParam>();
		params.add(new SystemParam("host","smtp.126.com"));
		params.add(new SystemParam("from","chenchao2008208@126.com"));
		params.add(new SystemParam("user","chenchao2008208@126.com"));
		params.add(new SystemParam("password","201205"));
		params.add(new SystemParam("port","smtp.126.com"));
		
		return params;
	}
}
