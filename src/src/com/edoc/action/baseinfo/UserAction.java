package com.edoc.action.baseinfo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.edoc.action.AbstractAction;
import com.edoc.dbsupport.PageValueObject;
import com.edoc.entity.baseinfo.Department;
import com.edoc.entity.baseinfo.User;
import com.edoc.service.baseInfo.OrganizationService;
import com.edoc.service.baseInfo.UserService;
import com.edoc.utils.RandomGUID;
import com.edoc.utils.StringUtils;
/**
 * 用户信息控制类
 * @author 陈超 2010-7-24
 *
 */
@Component("userAction")
@Scope("prototype")
public class UserAction extends AbstractAction{
	
	private static final long serialVersionUID = 1L;
	private User user = null;
	private String orgId = "-1";
	private String userId = null;
	private String userName = null;
	private String[] deleteParams = null;
	@Resource(name="userService")
	private UserService userService = null;
	
	@Resource(name="orgService")
	private OrganizationService orgService = null;
	
	/**
	 * 删除角色用户
	 * @return
	 */
	public String deleteRoleUser(){
		String roleId = this.getParameter("roleId");
		String[] userIds = this.getParameterValues("userIds");
		userService.deleteRoleUser(roleId,userIds);
		
		return getRoleUser();
	}
	
	/**
	 * 添加角色用户
	 * @return
	 */
	public String addRoleUser(){
		String[] userIds = this.getParameterValues("userId");
		String roleId = this.getParameter("roleId");
		userService.addRoleUser(roleId,userIds);
		return getRoleUser();
	}
	
	/**
	 * 获取角色用户
	 * @return
	 */
	public String getRoleUser(){
		String roleId = this.getParameter("roleId");
		PageValueObject<User> userList = userService.getUsersByRoleId(getCurrentPage(),getPageSize(), roleId, userName);
		
		this.setAttribute("userName", userName);
		this.setAttribute("userList", userList);
		this.setAttribute("roleId", roleId);
		return "showRoleUserPage";
	}
	
	/**
	 * 修改密码
	 */
	public void updatePassword(){
		User user = (User)this.getSession().getAttribute("DOCUSER");
		String newPassword = this.getParameter("newPassword");
		
		userService.updatePassword(user.getId(),newPassword);
		this.showMessage2(this.getResponse(), "密码修改成功!", true);
	}
	/**
	 * 登录功能
	 * @return
	 */
	public String doLogin(){
		String username = this.getParameter("j_username");
		String password = this.getParameter("j_password");
		User user = userService.doLogin(username,password);
		if(user!=null){
			this.getSession().setAttribute("DOCUSER", user);
			return "showIndex";
		}else{
			this.showMessage2(this.getResponse(), "用户名或密码不正确！请确认后重试！", true);
		}
		return null;
	}
	
	/**
	 * 注销功能
	 * @return
	 */
	public void doLogout(){
		
		User user = (User)this.getSession().getAttribute("DOCUSER");
		if(user!=null){
			this.getSession().removeAttribute("DOCUSER");
		}
		String loginUrl = "login.jsp";
		try {
			PrintWriter out = this.getResponse().getWriter();
			out.println("<html>");
			out.println("<script>");
			out.println("window.top.location = '" + this.getRequest().getContextPath() + "/" + loginUrl + "';");
			out.println("</script>");
			out.println("</html>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	
	public String getUserById(){
		String userId = this.getParameter("userId");
		User user = userService.getUserById(userId);
		Department dept = orgService.getOrgById(user.getOrgId());
		this.setAttribute("user", user);
		this.setAttribute("dept", dept);
		if(StringUtils.isValid(getForward())){
			getRequest().setAttribute("forward", getForward());
			return getForward();
		}
		return null;
	}
	
	public String updateUser(){
		userService.updateUser(user);
		return getUsersByOrgId();
	}
	
	/**
	 * 分页获取某个部门下的员工信息
	 * @return 'showUserListPage = jsp/baseinfo/orgusers.jsp'
	 * @author 陈超 2010-7-26
	 */
	public String getUsersByOrgId(){
		PageValueObject<User> userList = userService.getUsersByOrgId(getCurrentPage(),getPageSize(), orgId, userName);
		
		getRequest().setAttribute("userPageVO", userList);
		getRequest().setAttribute("orgId", orgId);
		getRequest().setAttribute("userName", userName);
		if(StringUtils.isValid(getForward())){
			getRequest().setAttribute("forward", getForward());
			return getForward();
		}
		return "showUserListPage";
	}
	
	/**
	 * 获取所有的用户信息
	 * @return 'showUserListPage = jsp/baseinfo/orgusers.jsp'
	 * @author 陈超 2010-7-29
	 */
	public String getAllUsers(){
		List<User> userList = userService.getAllUsers();
		this.getRequest().setAttribute("userList", userList);
		return "showUserListPage";
	}
	
	/**
	 * 保存用户信息
	 * @return
	 * @author 陈超 2010-7-26
	 */
	public String saveUser(){
		if(user!=null){
			user.setId(new RandomGUID().toString());
		}
		userService.saveUser(user);
		return getUsersByOrgId();
	}
	
	/**
	 * 删除指定的用户信息
	 * @return
	 * @author 陈超 2010-7-26
	 */
	public String deleteUser(){
		userService.deleteUser(deleteParams);
		return getUsersByOrgId();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String[] getDeleteParams() {
		return deleteParams;
	}

	public void setDeleteParams(String[] deleteParams) {
		this.deleteParams = deleteParams;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
