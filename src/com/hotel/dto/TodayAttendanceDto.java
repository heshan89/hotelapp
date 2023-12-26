package com.hotel.dto;

import java.time.LocalDateTime;

public class TodayAttendanceDto {
    private String hotelName;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private String duration;
    private String breakTime;
    private String formattedCheckIn;
    private String formattedCheckOut;

    public TodayAttendanceDto() {

    }

    public TodayAttendanceDto(String hotelName, LocalDateTime checkIn, LocalDateTime checkOut, String duration, String breakTime, String formattedCheckIn, String formattedCheckOut) {
        this.hotelName = hotelName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.duration = duration;
        this.breakTime = breakTime;
        this.formattedCheckIn = formattedCheckIn;
        this.formattedCheckOut = formattedCheckOut;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
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

    public String getFormattedCheckIn() {
        return formattedCheckIn;
    }

    public void setFormattedCheckIn(String formattedCheckIn) {
        this.formattedCheckIn = formattedCheckIn;
    }

    public String getFormattedCheckOut() {
        return formattedCheckOut;
    }

    public void setFormattedCheckOut(String formattedCheckOut) {
        this.formattedCheckOut = formattedCheckOut;
    }

    @Override
    public String toString() {
        return "TodayAttendanceDto{" +
                "hotelName='" + hotelName + '\'' +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", duration='" + duration + '\'' +
                ", breakTime='" + breakTime + '\'' +
                ", formattedCheckIn='" + formattedCheckIn + '\'' +
                ", formattedCheckOut='" + formattedCheckOut + '\'' +
                '}';
    }
}
