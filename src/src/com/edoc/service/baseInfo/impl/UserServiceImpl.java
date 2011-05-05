package com.edoc.service.baseInfo.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.dbsupport.PropertyFilter;
import com.edoc.entity.baseinfo.User;
import com.edoc.orm.hibernate.dao.GenericDAO;
import com.edoc.service.baseInfo.UserService;
import com.edoc.utils.RandomGUID;
import com.edoc.utils.StringUtils;

@Component("userService")
@Transactional(readOnly=true)
public class UserServiceImpl implements UserService{
	@Resource(name="userDao")
	private GenericDAO<User,String> userDao=null;
	
	public List<User> getUserList() {
		return userDao.getAll();
	}
	
	
	/**
	 * 删除角色用户
	 * @param roleId
	 * @param userIds
	 */
	@Transactional(readOnly=false)
	public void deleteRoleUser(String roleId, String[] userIds){
		if(userIds!=null && userIds.length>0){
			String sql = "delete from sys_user_role where c_role_id='"+roleId+"' and c_user_id in (";
			for(String s:userIds){
				sql += "'"+s+"',";
			}
			
			sql = sql.substring(0, sql.length()-1);
			sql += ")";
			System.out.println("sql:"+sql);
			userDao.delete(sql);
		}
		
	}
	/**
	 * 添加角色用户
	 * @param roleId	角色Id
	 * @param userIds	用户Id
	 */
	@Transactional(readOnly=false)
	public void addRoleUser(String roleId, String[] userIds){
//		//删除已设置的角色信息
//		String sql = "delete from sys_user_role where c_role_id = '"+roleId+"'";
//		userDao.update(sql);
		
		if(userIds!=null){
			for(String userId:userIds){
				String insertSQL = "insert into sys_user_role(id,c_user_id,c_role_id) values ('"+new RandomGUID().toString()+"','"
						+ userId +"','" + roleId + "')";
				
				userDao.update(insertSQL);
			}
		}
	}
	
	/**
	 * 修改用户密码
	 * @param id			用户信息Id
	 * @param newPassword	新密码
	 * @return
	 */
	@Transactional(readOnly=false)
	public boolean updatePassword(String id, String newPassword){
		String sql = "update sys_user set C_PASSWORD='"+newPassword+"' where ID='"+id+"'";
		userDao.update(sql);
		return true;
	}

	/**
	 * 用户登录业务
	 * @param username	用户名
	 * @param password	密码
	 * @return
	 */
	public User doLogin(String username, String password){
		if(!StringUtils.isValid(username)){
			return null;
		}else{
			List<PropertyFilter> filterList = new ArrayList<PropertyFilter>(2);
			PropertyFilter filter0 = new PropertyFilter("loginName",username,PropertyFilter.MatchType.EQ);
			filterList.add(filter0);
				
			PropertyFilter filter1 = new PropertyFilter("password",password,PropertyFilter.MatchType.EQ);
			filterList.add(filter1);
			
			List<User> users = userDao.find(filterList);
			if(users!=null && !users.isEmpty()){
				return users.get(0);
			}else{
				return null;
			}
		}
	}
	/**
	 * 更新用户信息
	 * @param user
	 */
	@Transactional(readOnly=false)
	public void updateUser(User user){
		if(user!=null){
			userDao.update(user);
		}
	}
	/**
	 * 根据用户Id获取该用户的信息
	 * @param userId	用户Id
	 * @return		返回获取到的用户信息
	 * @author 		陈超
	 */
	public User getUserById(String userId){
		return userDao.get(userId);
	}
	@Transactional(readOnly=false)
	public boolean deleteUser(String[] deleteParams) {
		if(deleteParams!=null){
			User user = null;
			for(String id:deleteParams){
				user = userDao.get(id);
				deleteProcess(user);
				userDao.update(user);
			}
			return true;
		}
		return true;
	}
	
	private void deleteProcess(User user){
		user.setIsDelete(1);
	}

	/**
	 * 分页查询某个部门下的所有员工信息
	 * @param currentPage	分页起始页
	 * @param pageSize		每页显示的记录数
	 * @param orgId			科室Id,当deptId=-1时表示获取说有的员工信息
	 * @return 				返回经过分页对象(PageValueObject)包装后的用户对象信息
	 * @author 				陈超 2010-7-26
	 */
	public PageValueObject<User> getUsersByOrgId(int currentPage, int pageSize, String orgId, String userName) {
		PageValueObject<User> page =  new PageValueObject<User>(currentPage,pageSize);
		List<PropertyFilter> filterList = new ArrayList<PropertyFilter>(2);
		if(StringUtils.isValid(orgId) && !orgId.equals("-1")){
			PropertyFilter filter = new PropertyFilter("orgId",orgId,PropertyFilter.MatchType.EQ);
			filterList.add(filter);
		}
			
		if(StringUtils.isValid(userName)){
			PropertyFilter filter = new PropertyFilter("trueName",userName,PropertyFilter.MatchType.LIKE);
			filterList.add(filter);
		}
			
		page.setResult(userDao.find(filterList, page.getFirstResult(), page.getPageSize()));
		page.setTotalRows(userDao.getCount(filterList));
		return page;
	}

	
	/**
	 * 查询角色用户
	 * @param currentPage
	 * @param pageSize
	 * @param roleId
	 * @param userName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageValueObject<User> getUsersByRoleId(int currentPage,
			int pageSize, String roleId, String userName){
		PageValueObject<User> page =  new PageValueObject<User>(currentPage,pageSize);
		String sql = "select a.id,a.C_TRUENAME,a.C_LOGINNAME,a.C_PASSWORD,a.D_CREATEDATE from sys_user as a,sys_user_role as b where a.id=b.c_user_id and b.c_role_id='"+roleId+"'";
		String sql2 = "select count(*) from sys_user as a,sys_user_role as b where a.id=b.c_user_id and b.c_role_id='"+roleId+"'";
		if(StringUtils.isValid(userName)){
			sql += " and a.C_TRUENAME='"+userName+"'";
			sql2 += " and a.C_TRUENAME='"+userName+"'";
			
		}
		List<User> tempResult = new LinkedList<User>();
		List r = userDao.excuteQuerySQL(sql, page.getFirstResult(), page.getPageSize());
		if(r!=null && !r.isEmpty()){
			for(Object o:r){
				Object[] vs = (Object[])o;
				User u = new User();
				u.setId((String)vs[0]);
				u.setTrueName((String)vs[1]);
				u.setLoginName((String)vs[2]);
				u.setPassword((String)vs[3]);
//				u.setCreateDate((String)vs[4]);
				
				tempResult.add(u);
			}
		}
		page.setResult(tempResult);
		page.setTotalRows(userDao.excuteGetCountSQL(sql2));
		return page;
		
//		List<Menu> menus = new LinkedList();
//		String sql = "select a.id,a.C_ICON,a.C_NAME,a.C_PARENT_ID from sys_menu as a,sys_role_menu as b where b.c_menu_id = a.id and b.c_role_id='"+roleId+"'";
//		List r = menuDao.excuteQuery(sql);
//		for(Object o :r){
//			Object[] vs = (Object[])o;
//			Menu m = new Menu();
//			m.setId((String)vs[0]);
//			m.setIcon((String)vs[1]);
//			m.setName((String)vs[2]);
//			m.setParentMenuId((String)vs[3]);
//			
//			menus.add(m);
//		}
//		return menus;
	}
	@Transactional(readOnly=false)
	public boolean saveUser(User user) {
		userDao.save(user);
		return false;
	}

	public List<User> getAllUsers() {
		
		return userDao.getAll();
	}

}
