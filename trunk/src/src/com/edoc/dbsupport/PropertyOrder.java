package com.edoc.dbsupport;

import java.io.Serializable;

/**
 * 排序类
 * 
 * @author LH
 * 
 */
public class PropertyOrder implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum OrderType {
		ASC, DESC;
	}

	private OrderType orderType = OrderType.ASC;// 默认为升序
	private String propertyName = null;// 属性名

	public PropertyOrder(String propertyName, OrderType orderType) {
		this.propertyName = propertyName;
		this.orderType = orderType;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

}
