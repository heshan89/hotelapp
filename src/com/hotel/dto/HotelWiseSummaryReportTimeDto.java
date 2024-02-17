package com.hotel.dto;

public class HotelWiseSummaryReportTimeDto {

    private String userId;
    private String userName;
    private String workDuration;
    private String hourWage;
    private String total;

    public HotelWiseSummaryReportTimeDto() {
    }

    public HotelWiseSummaryReportTimeDto(String userId, String userName, String workDuration, String hourWage, String total) {
        this.userId = userId;
        this.userName = userName;
        this.workDuration = workDuration;
        this.hourWage = hourWage;
        this.total = total;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWorkDuration() {
        return workDuration;
    }

    public void setWorkDuration(String workDuration) {
        this.workDuration = workDuration;
    }

    public String getHourWage() {
        return hourWage;
    }

    public void setHourWage(String hourWage) {
        this.hourWage = hourWage;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "HotelWiseSummaryReportTimeDto{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", workDuration='" + workDuration + '\'' +
                ", hourWage='" + hourWage + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}
