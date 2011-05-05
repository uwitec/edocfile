package com.edoc.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ҵ�����ϵͳ�е�һЩ�����Թ���
 * 
 * @author zsx
 * @version
 * @todo
 * @History
 */
public class Tools {
	
	/**
	 * �����������ʾ��Ϣ
	 * 
	 * @param res
	 * @param message
	 * @param type
	 *            "1"Ϊ�������棬��2��Ϊ�������棬�ٷ��أ���3��Ϊ�������棬�ٹرոô���,��4��Ϊ�û��Զ���javascript��
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
	 * �����������ʾ��Ϣ
	 * 
	 * @param res
	 * @param message
	 * @param type
	 *            "1"Ϊ�������棬��2��Ϊ�������棬�ٷ��أ���3��Ϊ�������棬�ٹرոô���,��4��Ϊ�û��Զ���javascript��
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
	 * ����·�� �����������ʾ��Ϣ �ύ��ҳ��
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
	 * ����������Ϣ�ύ������ҳ��
	 * 
	 * @param type
	 *            �������ͣ�1Ϊ���󾯸�---��תҳ�浽error.jsp��2Ϊ��Ϣ��ʾ----��תҳ�浽information.jsp��
	 * @param msgTitle
	 *            ������⣺����ʧ��!
	 * @param msgContent
	 *            �������ݣ�ĳĳ�ֶι���
	 * @param scriptStr
	 *            ��Ҫ���ĵĲ�����window.history.back() ;
	 * @param onLoadScript
	 *            ��ҳ���ʼ��ʱ���صĲ���������Ϊ��""
	 * @param rootPath
	 *            ��Ŀ¼��"../"
	 * @param buttonValue
	 *            ��ťֵ����ȷ����
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
