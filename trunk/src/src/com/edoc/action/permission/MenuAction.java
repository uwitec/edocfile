package com.edoc.action.permission;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.edoc.action.AbstractAction;
import com.edoc.entity.baseinfo.User;
import com.edoc.entity.permission.Menu;
import com.edoc.service.permission.MenuService;
import com.edoc.utils.StringUtils;

@Component("menuAction")
@Scope("prototype")
public class MenuAction extends AbstractAction{
	private static final long serialVersionUID = 1L;

	@Resource(name="menuService")
	private MenuService menuService=null;
	
	private Menu menu;
	
	/**
	 * �������˵����νṹ
	 * @author 		�³� 2011-02-03
	 */
	public String createMenuTree(){
		User user = (User)this.getSession().getAttribute("DOCUSER");
		String userId = user.getId();
		List<Menu> menuList = menuService.getMenuByParentId("0",userId);	//���ݸ��ڵ�˵�Id��ȡ��Ӧ���Ӳ˵���Ϣ
		String treeScript = this.createTreeScript(menuList);
		
//		try {
//			HttpServletResponse res = this.getResponse();
//			this.getResponse().setContentType("text/xml;charset=GBK");
//			res.setCharacterEncoding("utf-8");
//
//			PrintWriter out = res.getWriter();
//			out.print(treeScript);
//			out.flush();
//			out.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		this.setAttribute("treeScript", treeScript);
		return "toShowTreeMenu";
	}
	
	public void getSubTreeMenu(){
		User user = (User)this.getSession().getAttribute("DOCUSER");
		String userId = user.getId();
		String parentId = this.getParameter("parentId");
		List<Menu> menuList = menuService.getMenuByParentId(parentId,userId);
		if(menuList!=null && !menuList.isEmpty()){
			String str = "";
			str += "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
			str += "<tree>";
			for(Menu m:menuList){
				str += "<tree text=\""+m.getName()+"\" action=\"javascript:link('"+m.getUrl()+"','perspective_content')\"/>";
			}
			str += "</tree>";
			try {
				HttpServletResponse res = this.getResponse();
				this.getResponse().setContentType("text/xml;charset=GBK");
				res.setCharacterEncoding("utf-8");
				PrintWriter out = res.getWriter();
				out.print(str);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return;
	}
	
	/**
	 * �������β˵��ű�
	 * @param menuList
	 * @return
	 */
	private String createTreeScript(List<Menu> menuList) {
		String script = "";
		if(menuList!=null && !menuList.isEmpty()){
			script += "var tree = new WebFXTree(\"�ҵ�Ӧ��\",\"\",\"\",\"jsplugin/xtree2b/images/base.gif\",\"jsplugin/xtree2b/images/base.gif\");";
			script += "tree.setBehavior(\"classic\");";
			for(Menu m:menuList){
				if(m.getEnName()!=null && m.getEnName().equals("GXWJJ")){	//�����ļ��к��ҵ��ļ���
					if(StringUtils.isValid(m.getIcon())){
						script += "tree.add(new WebFXLoadTreeItem(\""+m.getName()+"\",\"fileAction!createShoredTreeMenu.action?parentId=-1&Rnd=\"+Math.random(),\"javascript:link('"+m.getUrl()+"','perspective_content')\",\"\",\""+m.getIcon()+"\",\""+m.getIcon()+"\"));";
					}else{
						script += "tree.add(new WebFXLoadTreeItem(\""+m.getName()+"\",\"fileAction!createShoredTreeMenu.action?parentId=-1&Rnd=\"+Math.random(),\"javascript:link('"+m.getUrl()+"','perspective_content')\"));";
					}

				}else if(m.getEnName()!=null && m.getEnName().equals("WDWJJ") ){
					if(StringUtils.isValid(m.getIcon())){
						script += "tree.add(new WebFXLoadTreeItem(\""+m.getName()+"\",\"fileAction!createTreeMenu.action?parentId=0&Rnd=\"+Math.random(),\"javascript:link('"+m.getUrl()+"','perspective_content')\",\"\",\""+m.getIcon()+"\",\""+m.getIcon()+"\"));";
					}else{
						script += "tree.add(new WebFXLoadTreeItem(\""+m.getName()+"\",\"fileAction!createTreeMenu.action?parentId=0&Rnd=\"+Math.random(),\"javascript:link('"+m.getUrl()+"','perspective_content')\"));";
					}
				}else{
					if(StringUtils.isValid(m.getIcon())){
						script += "tree.add(new WebFXLoadTreeItem(\""+m.getName()+"\",\"menuAction!getSubTreeMenu.action?parentId="+m.getId()+"&Rnd=\"+Math.random(),\"javascript:link('"+m.getUrl()+"','perspective_content')\",\"\",\""+m.getIcon()+"\",\""+m.getIcon()+"\"));";
					}else{
						script += "tree.add(new WebFXLoadTreeItem(\""+m.getName()+"\",\"menuAction!getSubTreeMenu.action?parentId="+m.getId()+"&Rnd=\"+Math.random(),\"javascript:link('"+m.getUrl()+"','perspective_content')\"));";
					}
				}
			}
			script += "tree.write();";
		}
//		System.out.println("script:"+script);
		return script;
	}

	/**
	 * �������еĲ˵���Ϣ,�����ݷ��ط�ʽ�����Ѳ�ѯ���ò˵������ҳ��
	 * 
	 * @return 		"showMenuListPage = ./jsp/security/menu_list.jsp"
	 * @author 		�³� 2010-6-21
	 */
	public String findAllMenus(){
		List<Menu> menuList = menuService.findAllMenus();
		
		//�ж��Ƿ�����JSON��ʽ��������
		if(isPrintJSON()){
			printJSON(null, null, menuList);
			return null;
		}else{
			this.getRequest().setAttribute("menuList", menuList);
			return "showMenuListPage";
		}
	}
	
	/**
	 * �����µĲ˵���Ϣ
	 * 
	 * @author �³� 2010-6-23
	 */
	public void saveMenu(){
		System.out.println("createTime:"+menu.getCreateDate());
		menuService.saveMenu(menu);
		return;
	}
	
	public void test(){
		menuService.getAllMenus();
		System.out.println("chenchao");
	}
	
	public void createMenu(){
		menu=new Menu();
		menu.setName("chenc");
		menu.setTitle("�³�");
		menu.setCreateDate(new Date());
		menuService.createMenu(menu);
	}
	public void editMenu(){
		menuService.editMenu(menu);
	}

	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
}
