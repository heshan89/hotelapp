package com.hotel.dto;

public class RatingCategoryDto {

    private int id;
    private String name;

    public RatingCategoryDto() {
    }

    public RatingCategoryDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RatingHotelDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
