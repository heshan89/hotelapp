package com.hotel.dto;

public class OrderItemDTO {
	private Integer orderId;
	private String itemName;
	private int quantity;
	private String userName;
	
	public OrderItemDTO() {
	}

	public OrderItemDTO(String itemName, int quantity, Integer orderId, String userName) {
		this.itemName = itemName;
		this.quantity = quantity;
		this.orderId = orderId;
		this.userName = userName;
	}
	
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "OrderItemDTO [orderId=" + orderId + ", itemName=" + itemName + ", quantity=" + quantity + ", userName="
				+ userName + "]";
	}
	
	
}
