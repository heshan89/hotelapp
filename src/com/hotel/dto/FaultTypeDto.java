package com.hotel.dto;

public class FaultTypeDto {
    private int id;
    private String name;

    public FaultTypeDto() {
    }

    public FaultTypeDto(int id, String name) {
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
        return "FaultType [id=" + id + ", name=" + name + "]";
    }
}
