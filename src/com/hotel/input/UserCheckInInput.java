package com.hotel.input;

import java.sql.Timestamp;

public class UserCheckInInput {
    private int userId;
    private int hotelId;
    private Timestamp checkIn;
    private Timestamp checkOut;
    private String createdBy;

    public UserCheckInInput() {

    }

    public UserCheckInInput(int userId, int hotelId, Timestamp checkIn, Timestamp checkOut, String createdBy) {
        this.userId = userId;
        this.hotelId = hotelId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.createdBy = createdBy;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Timestamp checkIn) {
        this.checkIn = checkIn;
    }

    public Timestamp getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Timestamp checkOut) {
        this.checkOut = checkOut;
    }

    @Override
    public String toString() {
        return "UserCheckInInput{" +
                "userId=" + userId +
                ", hotelId=" + hotelId +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}
