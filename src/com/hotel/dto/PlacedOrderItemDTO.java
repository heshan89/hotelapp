package com.hotel.dto;

import java.time.LocalDate;

public class PlacedOrderItemDTO {
	
	private Integer orderId;
	private LocalDate orderDate;
	private Integer floor;
	private String itemName;
	private Integer amount;
	
	public PlacedOrderItemDTO(Integer orderId, LocalDate orderDate, Integer floor, String itemName, Integer amount) {
		super();
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.floor = floor;
		this.itemName = itemName;
		this.amount = amount;
	}
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public LocalDate getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
	public Integer getFloor() {
		return floor;
	}
	public void setFloor(Integer floor) {
		this.floor = floor;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "PlacedOrderItemDTO [orderId=" + orderId + ", orderDate=" + orderDate + ", floor=" + floor
				+ ", itemName=" + itemName + ", amount=" + amount + "]";
	}
}