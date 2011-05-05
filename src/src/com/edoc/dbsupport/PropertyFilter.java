package com.edoc.dbsupport;

import java.io.Serializable;

/**
 * ��ѯ��������
 * 
 * @author �³�
 * 
 */
public class PropertyFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum MatchType {
		EQ, LIKE, LT, GT, LE, GE, NE, IN;
	}

	private MatchType matchType = MatchType.EQ;// Ĭ��Ϊ���
	private String propertyName = null;// ������
	private Object propertyValue = null;// ����ֵ

	public PropertyFilter(String propertyName, Object propertyValue,
			MatchType matchTyep) {
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
		this.matchType = matchTyep;
	}

	public MatchType getMatchType() {
		return matchType;
	}

	public void setMatchType(MatchType matchType) {
		this.matchType = matchType;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Object getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(Object propertyValue) {
		this.propertyValue = propertyValue;
	}
}
