package com.hotel.dto;

import java.sql.Date;

public class FaultDto {

    private int id;
    private String floor;
    private String room;
    private String faultTypeName;
    private String faultStatusName;
    private String description;
    private String attachment;
    private Date createdDate;
    private String createdBy;
    private Date updatedDate;
    private String updatedBy;

    public FaultDto() {
    }

    public FaultDto(int id, String floor, String room, String faultTypeName, String faultStatusName, String description, String attachment, Date createdDate, String createdBy, Date updatedDate, String updatedBy) {
        this.id = id;
        this.floor = floor;
        this.room = room;
        this.faultTypeName = faultTypeName;
        this.faultStatusName = faultStatusName;
        this.description = description;
        this.attachment = attachment;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.updatedDate = updatedDate;
        this.updatedBy = updatedBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getFaultTypeName() {
        return faultTypeName;
    }

    public void setFaultTypeName(String faultTypeName) {
        this.faultTypeName = faultTypeName;
    }

    public String getFaultStatusName() {
        return faultStatusName;
    }

    public void setFaultStatusName(String faultStatusName) {
        this.faultStatusName = faultStatusName;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String toString() {
        return "FaultDto{" +
                "id=" + id +
                ", floor='" + floor + '\'' +
                ", room='" + room + '\'' +
                ", faultTypeName=" + faultTypeName +
                ", faultStatusName=" + faultStatusName +
                ", description='" + description + '\'' +
                ", attachment='" + attachment + '\'' +
                ", createdDate=" + createdDate +
                ", createdBy='" + createdBy + '\'' +
                ", updatedDate=" + updatedDate +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}
