package com.hotel.dto;

import java.sql.Time;
import java.sql.Timestamp;

public class TodayAttendanceDto {
    private String hotelName;
    private Timestamp checkIn;
    private Timestamp checkOut;
    private String duration;
    private Time breakTime;

    public TodayAttendanceDto() {

    }

    public TodayAttendanceDto(String hotelName, Timestamp checkIn, Timestamp checkOut, String duration, Time breakTime) {
        this.hotelName = hotelName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.duration = duration;
        this.breakTime = breakTime;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Time getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(Time breakTime) {
        this.breakTime = breakTime;
    }

    @Override
    public String toString() {
        return "TodayAttendanceDto{" +
                "hotelName='" + hotelName + '\'' +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", duration=" + duration +
                ", breakTime=" + breakTime +
                '}';
    }
}
