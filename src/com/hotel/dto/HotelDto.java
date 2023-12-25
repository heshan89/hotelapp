package com.hotel.dto;

public class HotelDto {
    private int id;
    private String hotelCode;
    private String name;
    private boolean isActive;

    public HotelDto() {
    }

    public HotelDto(int id, String hotelCode, String name, boolean isActive) {
        this.id = id;
        this.hotelCode = hotelCode;
        this.name = name;
        this.isActive = isActive;
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

    @Override
    public String toString() {
        return "HotelDto{" +
                "id=" + id +
                ", hotelCode='" + hotelCode + '\'' +
                ", name='" + name + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
