package com.hotel.dto;

public class FaultStatusDto {

    private int id;
    private String name;

    public FaultStatusDto() {
    }

    public FaultStatusDto(int id, String name) {
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
        return "FaultStatusDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
