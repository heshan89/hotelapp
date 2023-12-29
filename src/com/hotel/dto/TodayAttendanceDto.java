package com.hotel.dto;

import java.time.LocalDateTime;

public class TodayAttendanceDto {
    private String hotelName;
    private LocalDateTime userCheckIn;
    private LocalDateTime userCheckOut;
    private LocalDateTime systemCheckIn;
    private LocalDateTime systemCheckOut;
    private String duration;
    private String breakTime;
    private String formattedUserCheckIn;
    private String formattedUserCheckOut;
    private String formattedSystemCheckIn;
    private String formattedSystemCheckOut;

    public TodayAttendanceDto() {

    }

    public TodayAttendanceDto(String hotelName, LocalDateTime userCheckIn, LocalDateTime userCheckOut, LocalDateTime systemCheckIn, LocalDateTime systemCheckOut, String duration, String breakTime, String formattedUserCheckIn, String formattedUserCheckOut, String formattedSystemCheckIn, String formattedSystemCheckOut) {
        this.hotelName = hotelName;
        this.userCheckIn = userCheckIn;
        this.userCheckOut = userCheckOut;
        this.systemCheckIn = systemCheckIn;
        this.systemCheckOut = systemCheckOut;
        this.duration = duration;
        this.breakTime = breakTime;
        this.formattedUserCheckIn = formattedUserCheckIn;
        this.formattedUserCheckOut = formattedUserCheckOut;
        this.formattedSystemCheckIn = formattedSystemCheckIn;
        this.formattedSystemCheckOut = formattedSystemCheckOut;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(String breakTime) {
        this.breakTime = breakTime;
    }

    public String getFormattedUserCheckIn() {
        return formattedUserCheckIn;
    }

    public void setFormattedUserCheckIn(String formattedUserCheckIn) {
        this.formattedUserCheckIn = formattedUserCheckIn;
    }

    public String getFormattedUserCheckOut() {
        return formattedUserCheckOut;
    }

    public void setFormattedUserCheckOut(String formattedUserCheckOut) {
        this.formattedUserCheckOut = formattedUserCheckOut;
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

    public String getFormattedSystemCheckIn() {
        return formattedSystemCheckIn;
    }

    public void setFormattedSystemCheckIn(String formattedSystemCheckIn) {
        this.formattedSystemCheckIn = formattedSystemCheckIn;
    }

    public String getFormattedSystemCheckOut() {
        return formattedSystemCheckOut;
    }

    public void setFormattedSystemCheckOut(String formattedSystemCheckOut) {
        this.formattedSystemCheckOut = formattedSystemCheckOut;
    }

    @Override
    public String toString() {
        return "TodayAttendanceDto{" +
                "hotelName='" + hotelName + '\'' +
                ", userCheckIn=" + userCheckIn +
                ", userCheckOut=" + userCheckOut +
                ", systemCheckIn=" + systemCheckIn +
                ", systemCheckOut=" + systemCheckOut +
                ", duration='" + duration + '\'' +
                ", breakTime='" + breakTime + '\'' +
                ", formattedUserCheckIn='" + formattedUserCheckIn + '\'' +
                ", formattedUserCheckOut='" + formattedUserCheckOut + '\'' +
                ", formattedSystemCheckIn='" + formattedSystemCheckIn + '\'' +
                ", formattedSystemCheckOut='" + formattedSystemCheckOut + '\'' +
                '}';
    }
}
