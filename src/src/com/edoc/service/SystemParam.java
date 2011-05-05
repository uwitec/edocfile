package com.edoc.service;
/**
 * 系统参数实体类
 * @author 陈超
 *
 */
public class SystemParam {
	private String id = "";				
	private String name = "";			//参数名称
	private String displayName = "";	//参数显示名称
	private String type = "";			//参数类型
	private String value = "";			//参数值
	private int isShow = 0;				//是否在页面显示提供用户配置
	
	public SystemParam(){
		super();
	}
	
	public SystemParam(String name,String value){
		super();
		this.name = name;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getIsShow() {
		return isShow;
	}

	public void setIsShow(int isShow) {
		this.isShow = isShow;
	}
	
}
