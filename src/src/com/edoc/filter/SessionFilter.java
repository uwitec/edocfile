package com.edoc.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 过滤器
 * 
 * @author zsx
 * @version
 * @todo
 * @History
 */
public class SessionFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest httpreq = (HttpServletRequest) arg0;
		String forwardURL = httpreq.getRequestURI().substring(httpreq.getContextPath().length());
//		System.out.println("forwardURL:"+forwardURL);
		HttpSession session = httpreq.getSession(false);
		if (!IsAccess(forwardURL)) {

			if (session == null || session.getAttribute("DOCUSER") == null) {
				HttpServletResponse httprep = (HttpServletResponse) arg1;
				System.out.println("当前用户没有登陆!");

				String loginUrl = "login.jsp";
				PrintWriter out = httprep.getWriter();
				out.println("<html>");
				out.println("<script>");
//				System.out.println("loginUrl:"+"window.top.location = '" + httpreq.getContextPath() + "/" + loginUrl + "';");
				out.println("window.top.location = '" + httpreq.getContextPath() + "/" + loginUrl + "';");
				out.println("</script>");
				out.println("</html>");
				return;
			} else {
				arg0.setCharacterEncoding("UTF-8");
				arg1.setCharacterEncoding("UTF-8");
				arg2.doFilter(arg0, arg1);
			}
		} else {
			arg0.setCharacterEncoding("UTF-8");
			arg1.setCharacterEncoding("UTF-8");
			arg2.doFilter(arg0, arg1);
		}
	}

	/**
	 * 判断是否是要用户验证的地址前缀
	 * 
	 * @param forwardURL
	 * @return
	 */
	public boolean IsAccess(String forwardURL) {
		if (forwardURL.startsWith("/login.jsp")
				|| forwardURL.equals("/redirect.jsp")
				|| forwardURL.startsWith("/userAction!doLogin.action")
				|| forwardURL.startsWith("/css")
				|| forwardURL.startsWith("/dropdown")
				|| forwardURL.startsWith("/icon")
				|| forwardURL.equals("/")
				|| forwardURL.startsWith("/images")
				|| forwardURL.startsWith("/js")
				|| forwardURL.startsWith("/jsplugin")
				|| forwardURL.startsWith("/tree-menu")
				|| forwardURL.startsWith("/tree-table")
				|| forwardURL.startsWith("/tree-menu")
				|| forwardURL.startsWith("/temp")) {
			return true;
		}
		return false;
	}

	public void init(FilterConfig config) throws ServletException {
	}
}
