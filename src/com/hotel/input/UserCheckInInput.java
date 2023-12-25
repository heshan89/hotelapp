package com.hotel.input;

public class UserCheckInInput {
    private int userId;
    private int hotelId;
    private String createdBy;

    public UserCheckInInput() {

    }

    public UserCheckInInput(int userId, int hotelId, String createdBy) {
        this.userId = userId;
        this.hotelId = hotelId;
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

    @Override
    public String toString() {
        return "UserCheckInInput{" +
                "userId=" + userId +
                ", hotelId=" + hotelId +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}
