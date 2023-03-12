package com.hotel.input;

public class FaultInput {
    private int floor;
    private String room;
    private int faultTypeId;
    private int faultStatusId;
    private String description;
    private String attachment;
    private String createdBy;

    public FaultInput() {
    }

    public FaultInput(int floor, String room, int faultTypeId, int faultStatusId, String description, String attachment, String createdBy) {
        this.floor = floor;
        this.room = room;
        this.faultTypeId = faultTypeId;
        this.faultStatusId = faultStatusId;
        this.description = description;
        this.attachment = attachment;
        this.createdBy = createdBy;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getFaultTypeId() {
        return faultTypeId;
    }

    public void setFaultTypeId(int faultTypeId) {
        this.faultTypeId = faultTypeId;
    }

    public int getFaultStatusId() {
        return faultStatusId;
    }

    public void setFaultStatusId(int faultStatusId) {
        this.faultStatusId = faultStatusId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "FaultInput{" +
                "floor='" + floor + '\'' +
                ", room='" + room + '\'' +
                ", faultTypeId=" + faultTypeId +
                ", faultStatusId=" + faultStatusId +
                ", description='" + description + '\'' +
                ", attachment='" + attachment + '\'' +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}
