package com.edoc.service;
/**
 * ϵͳ����ʵ����
 * @author �³�
 *
 */
public class SystemParam {
	private String id = "";				
	private String name = "";			//��������
	private String displayName = "";	//������ʾ����
	private String type = "";			//��������
	private String value = "";			//����ֵ
	private int isShow = 0;				//�Ƿ���ҳ����ʾ�ṩ�û�����
	
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
