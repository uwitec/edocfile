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
	 * 获取系统参数
	 * @param type
	 * @return
	 * @author 陈超
	 */
	public List<String[]> getSysParams(String type){
		
		return null;
	}
	
	/**
	 * 设置系统参数
	 * @param type		参数类型
	 * @param params	参数信息：key-记录ID,value-参数值
	 * @author 			陈超
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
