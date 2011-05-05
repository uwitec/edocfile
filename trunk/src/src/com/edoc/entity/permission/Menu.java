package com.edoc.entity.permission;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.edoc.entity.AbstractBaseEntity;
import com.edoc.utils.RandomGUID;

/**
 * �˵�ʵ����
 * 
 * @author �³�
 * 
 */
@Entity
@Table(name = "sys_menu")
public class Menu extends AbstractBaseEntity{
	@Id
	@Column(name = "ID")
	private String id = null; // �˵��ڵ�Id
	
	@Column(name = "I_DEL", nullable = true)
	private int isDelete=0; //��¼ɾ����־
	
	@Column(name = "D_CREATEDATE", nullable = true)
	private Date createDate=null; //��¼��������
	
	@Column(name = "C_PARENT_ID", nullable = true)
	private String parentMenuId = null; // �˵����ڵ�Id
	
	@Column(name = "C_NAME", nullable = true)
	private String name = null; // �˵�����
	
	@Column(name = "C_ENNAME", nullable = true)
	private String enName = null;	//�˵�Ӣ������
	
	@Column(name = "C_TITLE", nullable = true)
	private String title = null; // ˵��
	
	@Column(name = "C_URL", nullable = true)
	private String url = null; // �˵����ӵ�ַ
	
	@Column(name = "C_TARGET", nullable = true)
	private String target = null; // ����Ŀ��
	
	@Column(name = "C_ICON", nullable = true)
	private String icon = null; // �˵�ͼ��
	
	
	@Column(name = "C_NO", nullable = true)
	private String no = null; 				//�˵����
	
	@Transient
	private List<Menu> subMenus = new LinkedList<Menu>();

	public Menu() {
		super();
		id = new RandomGUID().toString();
		createDate = new Date();
	}

	
	public String getNo() {
		return no;
	}


	public void setNo(String no) {
		this.no = no;
	}


	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public List<Menu> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<Menu> subMenus) {
		this.subMenus = subMenus;
	}

	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getParentMenuId() {
		return parentMenuId;
	}



	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}



	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
