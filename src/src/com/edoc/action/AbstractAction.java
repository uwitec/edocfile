package com.edoc.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.edoc.utils.StringUtils;
import com.edoc.utils.Tools;
import com.opensymphony.xwork2.ActionSupport;

import flexjson.JSONSerializer;


/**
 * ����Action,�ṩ�����ķ��������
 * 
 * @author �³�
 * 
 */
public abstract class AbstractAction extends ActionSupport implements
		ServletRequestAware, ServletResponseAware {

	protected static Log logger = LogFactory.getLog(AbstractAction.class);

	private static final long serialVersionUID = 1L;
	public static final String PAGE_PARAMS_CURRENTPAGE = "currentPage";// ��ҳ����,��ǰҳ
	public static final String PAGE_PARAMS_PAGESIZE = "pageSize";// ��ҳ����,ÿҳ��ʾ�ļ�¼��
	public static final String ATTR_PAGEBEAN = "pageBean";
	public static final String ATTR_HQL = "hql";
	public static final String SESSION_ATTR_USERINFO = "DOCUSER";

	private String forward = null;
	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	private String hql = null;
	private boolean printJSON = false;

	public boolean isPrintJSON() {
		return printJSON;
	}

	public void setPrintJSON(boolean printJSON) {
		this.printJSON = printJSON;
	}

	public String getHql() {
		return hql;
	}

	public void setHql(String hql) {
		this.hql = hql;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public int getPageSize() {
		String pageSizeStr = this.getParameter("pageSize");
		if(StringUtils.isValid(pageSizeStr)){
			return Integer.parseInt(pageSizeStr);
		}
		return 15;
	}

	public int getCurrentPage() {
		String currentPageStr = this.getParameter("currentPage");
		if(StringUtils.isValid(currentPageStr)){
			return Integer.parseInt(currentPageStr);
		}
		return 0;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getRequest() {
		return this.request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletResponse getResponse() {
		return this.response;
	}

	public HttpSession getSession() {
		return request.getSession();
	}

	public String getParameter(String name) {
		return request.getParameter(name);
	}

	public String[] getParameterValues(String name) {
		return request.getParameterValues(name);
	}
	
	public void setAttribute(String name,Object o){
		request.setAttribute(name, o);
	}
	
	/**
	 * ���JSON��ʽ�������磺{a:1,b:2,c:3},JSON���Լ�ֵ�Ե���ʽ��ʾ��
	 * 
	 * @param include
	 *            ָ����Ҫ�����л��Ĳ���,�����ָ���Ļ�Ĭ�ϲ����Bean�еļ������Ͳ������л�
	 * @param exclude
	 *            �ƶ�����Ҫ���л��Ĳ���
	 * @param obj
	 *            ���л�����
	 */
	public void printJSON(List<String> include, List<String> exclude, Object obj) {
		// ���objΪ����ʲô�������,�������л��ö������
		if (obj != null) {
			String json = this.serializerObject(include, exclude, obj);
			System.out.println("JSON:"+json);
			print(json);
		}
	}

	/**
	 * ���л�����
	 * 
	 * @param include
	 *            ָ����Ҫ�����л��Ĳ���,�����ָ���Ļ�Ĭ�ϲ����Bean�еļ������Ͳ������л�
	 * @param exclude
	 *            �ƶ�����Ҫ���л��Ĳ���
	 * @param obj
	 *            �����л��Ķ���
	 * @return �������л�����ַ���
	 */
	public String serializerObject(List<String> include, List<String> exclude,
			Object obj) {
		JSONSerializer json = new JSONSerializer();
		if (include != null && !include.isEmpty()) {
			json.setIncludes(include);
		}
		if (exclude != null && !exclude.isEmpty()) {
			json.setExcludes(exclude);
		}
		return json.serialize(obj).toString();
	}

	public void print(String json) {
		try {
			response.setCharacterEncoding("utf-8");

			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showMessage(HttpServletResponse res, String message,boolean colseWin){
		try {
			Tools.showMessage(this.getResponse(), message, colseWin);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void showMessage2(HttpServletResponse res, String message,boolean goBack){
		try {
			Tools.showMessage2(this.getResponse(), message, goBack);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
