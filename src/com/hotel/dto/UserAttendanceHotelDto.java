package com.hotel.dto;

import java.time.LocalDateTime;

public class UserAttendanceHotelDto {

    private String userId;
    private String userName;
    private String hotelName;
    private LocalDateTime userCheckIn;
    private LocalDateTime userCheckOut;
    private LocalDateTime systemCheckIn;
    private LocalDateTime systemCheckOut;

    public UserAttendanceHotelDto() {
    }

    public UserAttendanceHotelDto(String userId, String userName, String hotelName, LocalDateTime userCheckIn, LocalDateTime userCheckOut, LocalDateTime systemCheckIn, LocalDateTime systemCheckOut) {
        this.userId = userId;
        this.userName = userName;
        this.hotelName = hotelName;
        this.userCheckIn = userCheckIn;
        this.userCheckOut = userCheckOut;
        this.systemCheckIn = systemCheckIn;
        this.systemCheckOut = systemCheckOut;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public LocalDateTime getUserCheckIn() {
        return userCheckIn;
    }

    public void setUserCheckIn(LocalDateTime userCheckIn) {
        this.userCheckIn = userCheckIn;
    }

    public LocalDateTime getUserCheckOut() {
        return userCheckOut;
    }

    public void setUserCheckOut(LocalDateTime userCheckOut) {
        this.userCheckOut = userCheckOut;
    }

    public LocalDateTime getSystemCheckIn() {
        return systemCheckIn;
    }

    public void setSystemCheckIn(LocalDateTime systemCheckIn) {
        this.systemCheckIn = systemCheckIn;
    }

    public LocalDateTime getSystemCheckOut() {
        return systemCheckOut;
    }

    public void setSystemCheckOut(LocalDateTime systemCheckOut) {
        this.systemCheckOut = systemCheckOut;
    }

    @Override
    public String toString() {
        return "EmployeeWiseReportDto{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", userCheckIn=" + userCheckIn +
                ", userCheckOut=" + userCheckOut +
                ", systemCheckIn=" + systemCheckIn +
                ", systemCheckOut=" + systemCheckOut +
                '}';
    }
}
