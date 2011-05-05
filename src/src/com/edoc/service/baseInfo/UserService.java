package com.edoc.service.baseInfo;

import java.util.List;

import com.edoc.dbsupport.PageValueObject;
import com.edoc.entity.baseinfo.User;

/**
 * 用户服务类,用于用户信息查询、删除、修改、添加等操作,实现类型{@link UserServiceImpl}
 * @author dell
 *
 */
public interface UserService {
	
	/**
	 * 获取用户信息
	 * @return
	 */
	public List<User> getUserList();
	
	
	/**
	 * 分页查询某个部门下的所有员工信息
	 * @param currentPage	分页起始页
	 * @param pageSize		每页显示的记录数
	 * @param orgId			科室Id,当deptId=-1时表示获取说有的员工信息
	 * @param userName 		用户名称
	 * @return 				返回经过分页对象(PageValueObject)包装后的用户对象信息
	 * @author 				陈超 2010-7-26
	 */
	public PageValueObject<User> getUsersByOrgId(int currentPage, int pageSize, String orgId, String userName);
	

	/**
	 * 保存用户信息
	 * @param user
	 * @return 保存成功返回true否则返回false
	 * @author 陈超 2010-7-26
	 */
	public boolean saveUser(User user);

	/**
	 * 删除用户信息
	 * @param deleteParams
	 * @author 陈超 2010-7-26
	 */
	public boolean deleteUser(String[] deleteParams);

	/**
	 * 获取或有的用户信息
	 * @return 返回经过分页对象(PageValueObject)包装后的用户对象信息
	 * @author 陈超 2010-7-29
	 */
	public List<User> getAllUsers();
	
	/**
	 * 根据用户Id获取该用户的信息
	 * @param userId	用户Id
	 * @return		返回获取到的用户信息
	 * @author 		陈超
	 */
	public User getUserById(String userId);
	
	/**
	 * 更新用户信息
	 * @param user
	 */
	public void updateUser(User user);
	

	/**
	 * 用户登录业务
	 * @param username	用户名
	 * @param password	密码
	 * @return
	 */
	public User doLogin(String username, String password);

	/**
	 * 修改用户密码
	 * @param id			用户信息Id
	 * @param newPassword	新密码
	 * @return
	 */
	public boolean updatePassword(String id, String newPassword);

	
	/**
	 * 查询角色用户
	 * @param currentPage
	 * @param pageSize
	 * @param roleId
	 * @param userName
	 * @return
	 */
	public PageValueObject<User> getUsersByRoleId(int currentPage,
			int pageSize, String roleId, String userName);

	
	/**
	 * 添加角色用户
	 * @param roleId	角色Id
	 * @param userIds	用户Id
	 */
	public void addRoleUser(String roleId, String[] userIds);

	/**
	 * 删除角色用户
	 * @param roleId
	 * @param userIds
	 */
	public void deleteRoleUser(String roleId, String[] userIds);
}
