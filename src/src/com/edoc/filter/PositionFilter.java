package com.edoc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.edoc.utils.StringUtils;

/**
 * ��ǰλ�ù�����
 * @author �³� 2011-02-10
 *
 */
public class PositionFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String isDir = req.getParameter("isDir");
		if(StringUtils.isValid(isDir) && isDir.equals("true")){		//�ж��Ƿ���Ŀ¼����
			String isRootPosition = req.getParameter("isRootPosition");
			String posStr = "";
			String posName = req.getParameter("posName");
			String posURL = req.getParameter("posURL");
			if(StringUtils.isValid(isRootPosition)){	//�ж��Ƿ��Ǹ�Ŀ¼
				if(isRootPosition.equals("true")){
					posStr += "�ҵ�Ӧ�� / " + "<a href=\""+posURL+"\">"+posName+"</a>";
				}
			}else{
				String prePosStr = req.getParameter("posStr");
				posStr += prePosStr;
				posStr += "<a href=\""+posURL+"\">"+posName+"</a>";
			}
			System.out.println("posStr:"+posStr);
			req.setAttribute("posStr", posStr);
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

}
