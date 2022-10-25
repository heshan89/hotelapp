package com.hotel.dto;

import java.time.LocalDate;

public class PlacedOrderItemDTO {
	private Integer id;
	private Integer orderId;
	private LocalDate orderDate;
	private Integer floor;
	private String itemName;
	private Integer amount;
	private String status;
	private String createdBy;

	public PlacedOrderItemDTO(Integer id, Integer orderId, LocalDate orderDate, Integer floor, String itemName, Integer amount, String createdBy) {
		this.id = id;
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.floor = floor;
		this.itemName = itemName;
		this.amount = amount;
		this.createdBy = createdBy;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public PlacedOrderItemDTO() {
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String toString() {
		return "PlacedOrderItemDTO{" +
				"id=" + id +
				", orderId=" + orderId +
				", orderDate=" + orderDate +
				", floor=" + floor +
				", itemName='" + itemName + '\'' +
				", amount=" + amount +
				", status='" + status + '\'' +
				", createdBy='" + createdBy + '\'' +
				'}';
	}
}
