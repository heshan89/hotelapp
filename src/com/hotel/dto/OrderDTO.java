/**
 * 
 */
package com.hotel.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Rangana_107371
 *
 */
public class OrderDTO {
	private LocalDate date;
	private int floor;
	private int room;
	private String userName;
	private List<OrderItemDTO> orderItemList;
	
	public OrderDTO() {
	}

	public OrderDTO(LocalDate date, int floor, int room, String userName, List<OrderItemDTO> orderItemList) {
		this.date = date;
		this.floor = floor;
		this.room = room;
		this.userName = userName;
		this.orderItemList = orderItemList;
	}
	
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public int getRoom() {
		return room;
	}
	public void setRoom(int room) {
		this.room = room;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	

	public List<OrderItemDTO> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItemDTO> orderItemList) {
		this.orderItemList = orderItemList;
	}

	@Override
	public String toString() {
		return "OrderDTO [date=" + date + ", floor=" + floor + ", room=" + room + ", userName=" + userName + ", orderItemList=" + orderItemList + "]";
	}
	
}
