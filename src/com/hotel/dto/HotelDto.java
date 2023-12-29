package com.hotel.dto;

import java.sql.Time;
import java.sql.Timestamp;

public class HotelDto {
    private int id;
    private String hotelCode;
    private String name;
    private Time hotelCheckIn;
    private Time hotelCheckInThreshold;
    private Time hotelCheckOut;
    private Time hotelCheckOutThreshold;
    private boolean isActive;
    private Timestamp systemCheckIn;

    public HotelDto() {
    }

    public HotelDto(int id, String hotelCode, String name, Time hotelCheckIn, Time hotelCheckInThreshold, Time hotelCheckOut, Time hotelCheckOutThreshold, boolean isActive, Timestamp systemCheckIn) {
        this.id = id;
        this.hotelCode = hotelCode;
        this.name = name;
        this.hotelCheckIn = hotelCheckIn;
        this.hotelCheckInThreshold = hotelCheckInThreshold;
        this.hotelCheckOut = hotelCheckOut;
        this.hotelCheckOutThreshold = hotelCheckOutThreshold;
        this.isActive = isActive;
        this.systemCheckIn = systemCheckIn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHotelCode() {
        return hotelCode;
    }

    public void setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Time getHotelCheckIn() {
        return hotelCheckIn;
    }

    public void setHotelCheckIn(Time hotelCheckIn) {
        this.hotelCheckIn = hotelCheckIn;
    }

    public Time getHotelCheckOut() {
        return hotelCheckOut;
    }

    public void setHotelCheckOut(Time hotelCheckOut) {
        this.hotelCheckOut = hotelCheckOut;
    }

    public Time getHotelCheckInThreshold() {
        return hotelCheckInThreshold;
    }

    public void setHotelCheckInThreshold(Time hotelCheckInThreshold) {
        this.hotelCheckInThreshold = hotelCheckInThreshold;
    }

    public Time getHotelCheckOutThreshold() {
        return hotelCheckOutThreshold;
    }

    public void setHotelCheckOutThreshold(Time hotelCheckOutThreshold) {
        this.hotelCheckOutThreshold = hotelCheckOutThreshold;
    }

    public Timestamp getSystemCheckIn() {
        return systemCheckIn;
    }

    public void setSystemCheckIn(Timestamp systemCheckIn) {
        this.systemCheckIn = systemCheckIn;
    }

    @Override
    public String toString() {
        return "HotelDto{" +
                "id=" + id +
                ", hotelCode='" + hotelCode + '\'' +
                ", name='" + name + '\'' +
                ", hotelCheckIn=" + hotelCheckIn +
                ", hotelCheckInThreshold=" + hotelCheckInThreshold +
                ", hotelCheckOut=" + hotelCheckOut +
                ", hotelCheckOutThreshold=" + hotelCheckOutThreshold +
                ", isActive=" + isActive +
                ", systemCheckIn=" + systemCheckIn +
                '}';
    }
}
