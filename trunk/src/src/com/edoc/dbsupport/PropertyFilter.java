package com.edoc.dbsupport;

import java.io.Serializable;

/**
 * 查询参数设置
 * 
 * @author 陈超
 * 
 */
public class PropertyFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum MatchType {
		EQ, LIKE, LT, GT, LE, GE, NE, IN;
	}

	private MatchType matchType = MatchType.EQ;// 默认为相等
	private String propertyName = null;// 属性名
	private Object propertyValue = null;// 属性值

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
