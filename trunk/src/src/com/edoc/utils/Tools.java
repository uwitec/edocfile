package com.edoc.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 业务管理系统中的一些辅助性工具
 * 
 * @author zsx
 * @version
 * @todo
 * @History
 */
public class Tools {
	
	/**
	 * 产生警告等提示消息
	 * 
	 * @param res
	 * @param message
	 * @param type
	 *            "1"为弹出警告，“2”为弹出警告，再返回，“3”为弹出警告，再关闭该窗口,“4”为用户自定义javascript。
	 * @throws IOException
	 */
	public static void showMessage(HttpServletResponse res, String message,boolean colseWin) throws IOException {
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		String str = "<html><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>";
		str += "<script>alert(\""+message+"\"); ";
		if(colseWin){
			str += "window.close();";
		}
		str += "</script></head><body></body></html>";
		out.println(str);
		out.flush();
		out.close();
	}
	
	/**
	 * 产生警告等提示消息
	 * 
	 * @param res
	 * @param message
	 * @param type
	 *            "1"为弹出警告，“2”为弹出警告，再返回，“3”为弹出警告，再关闭该窗口,“4”为用户自定义javascript。
	 * @throws IOException
	 */
	public static void showMessage2(HttpServletResponse res, String message,boolean goBack) throws IOException {
		res.setContentType("text/html; charset=UTF-8");
		PrintWriter out = res.getWriter();
		String str = "<html><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>";
		str += "<script>alert(\""+message+"\"); ";
		if(goBack){
			str += "history.go(-1);";
		}
		str += "</script></head><body></body></html>";
		out.println(str);
		out.flush();
		out.close();
	}



	/**
	 * 根据路径 产生警告等提示消息 提交到页面
	 * 
	 * @param res
	 * @param req
	 * @param url
	 * @param message
	 * @param type
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void showMessage(HttpServletResponse res,
			HttpServletRequest req, String url, String message, int type)
			throws ServletException, IOException {
		String returnMessage = "";
		switch (type) {
		case 1:
			returnMessage = "<script>alert(\"" + message + "\");</script>";
			break;
		case 2:
			returnMessage = "<script>alert(\"" + message
					+ "\");window.history.back();</script>";
			break;
		case 3:
			returnMessage = "<script>alert(\"" + message
					+ "\");window.close();</script>";
			break;
		case 4:
			returnMessage = "<script>" + message + "</script>";
			break;
		default:
			returnMessage = "<script>alert(\"" + message + "\");</script>";
			break;
		}
		req.setAttribute("message", returnMessage);
		String url1 = "/error/showMessage.jsp";
		RequestDispatcher dispatcherList = req.getRequestDispatcher(url1);
		dispatcherList.forward(req, res);
	}

	/**
	 * 产生错误信息提交到错误页面
	 * 
	 * @param type
	 *            错误类型：1为错误警告---跳转页面到error.jsp；2为信息提示----跳转页面到information.jsp；
	 * @param msgTitle
	 *            错误标题：更新失败!
	 * @param msgContent
	 *            错误内容：某某字段过长
	 * @param scriptStr
	 *            需要做的的操作：window.history.back() ;
	 * @param onLoadScript
	 *            在页面初始化时加载的操作，可以为空""
	 * @param rootPath
	 *            根目录："../"
	 * @param buttonValue
	 *            按钮值：“确定”
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void showMessage(int type, String msgTitle,
			String msgContent, String scriptStr, String onLoadScript,
			String rootPath, String buttonValue, HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException {
		String errorUrl = rootPath + "error/error.jsp";
		if (type == 2)
			errorUrl = rootPath + "error/info.jsp";
		req.setAttribute("rootPath", rootPath);
		req.setAttribute("msgTitle", msgTitle);
		req.setAttribute("msgContent", msgContent);
		req.setAttribute("msgButtonCaption", buttonValue);
		req.setAttribute("msgButtonScript", scriptStr);
		req.setAttribute("onLoadScript", onLoadScript);
		RequestDispatcher dispatcherList = req.getRequestDispatcher(errorUrl);
		dispatcherList.forward(req, res);
	}

	
}
