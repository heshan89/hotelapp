package com.hotel.input;

import java.sql.Timestamp;

public class UserCheckInInput {
    private int userId;
    private int hotelId;
    private Timestamp userCheckIn;
    private Timestamp userCheckOut;
    private Timestamp systemCheckIn;
    private Timestamp systemCheckOut;
    private String createdBy;

    public UserCheckInInput() {

    }

    public UserCheckInInput(int userId, int hotelId, Timestamp userCheckIn, Timestamp userCheckOut, Timestamp systemCheckIn, Timestamp systemCheckOut, String createdBy) {
        this.userId = userId;
        this.hotelId = hotelId;
        this.userCheckIn = userCheckIn;
        this.userCheckOut = userCheckOut;
        this.systemCheckIn = systemCheckIn;
        this.systemCheckOut = systemCheckOut;
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

    public Timestamp getUserCheckIn() {
        return userCheckIn;
    }

    public void setUserCheckIn(Timestamp userCheckIn) {
        this.userCheckIn = userCheckIn;
    }

    public Timestamp getUserCheckOut() {
        return userCheckOut;
    }

    public void setUserCheckOut(Timestamp userCheckOut) {
        this.userCheckOut = userCheckOut;
    }

    public Timestamp getSystemCheckIn() {
        return systemCheckIn;
    }

    public void setSystemCheckIn(Timestamp systemCheckIn) {
        this.systemCheckIn = systemCheckIn;
    }

    public Timestamp getSystemCheckOut() {
        return systemCheckOut;
    }

    public void setSystemCheckOut(Timestamp systemCheckOut) {
        this.systemCheckOut = systemCheckOut;
    }

    @Override
    public String toString() {
        return "UserCheckInInput{" +
                "userId=" + userId +
                ", hotelId=" + hotelId +
                ", userCheckIn=" + userCheckIn +
                ", userCheckOut=" + userCheckOut +
                ", systemCheckIn=" + systemCheckIn +
                ", systemCheckOut=" + systemCheckOut +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}
