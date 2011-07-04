package com.edoc.entity.files;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.edoc.dbsupport.PropertyOrder;
import com.edoc.dbsupport.PropertyOrder.OrderType;
import com.edoc.entity.AbstractBaseEntity;
import com.edoc.utils.RandomGUID;

/**
 * 文件使用记录
 * @author 陈超
 *
 */
@Entity
@Table(name = "sys_fileuserecord")
public class FileUseRecord extends AbstractBaseEntity{
	
	@Id
	@Column(name = "ID")
	private String id  = "";
	
	@Column(name = "C_SFILEID", nullable = true)
	private String sFileId = "";
	
	@Column(name = "C_SFILENAME", nullable = true)
	private String sFileName= "";
	
	@Column(name = "C_USERID", nullable = true)
	private String userId = "";
	
	@Column(name = "C_USERNAME", nullable = true)
	private String userName = "";
	
	@Column(name = "I_USERTYPE", nullable = true)
	private int userType = 0;
	
	@Column(name = "D_CREATETIME", nullable = true)
 	private Date createTime = null;						//创建时间
	
	@Column(name = "I_ISDELETE", nullable = true)
	private int isDelete =0;	
	
	public FileUseRecord(){
		id = new RandomGUID().toString();
		createTime = new Date();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSFileId() {
		return sFileId;
	}

	public void setSFileId(String fileId) {
		sFileId = fileId;
	}

	public String getSFileName() {
		return sFileName;
	}

	public void setSFileName(String fileName) {
		sFileName = fileName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
	
	/**
	 * 获取排序信息
	 */
	public List<PropertyOrder> getPropertyOrders() {
		
		List<PropertyOrder> orders  = new ArrayList<PropertyOrder>(2);
		PropertyOrder p = new PropertyOrder("createTime",OrderType.DESC);
		orders.add(p);
		
		return orders;
	}

}
