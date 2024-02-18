package com.hotel.dto;

import java.util.List;

public class EmpWiseSummaryReportDto {

    private String userId;
    private String userName;
    private String userWiseTotal;
    private List<EmpWiseSummaryReportTimeDto> empWiseSummaryReportTimes;

    public EmpWiseSummaryReportDto() {
    }

    public EmpWiseSummaryReportDto(String userId, String userName, String userWiseTotal, List<EmpWiseSummaryReportTimeDto> empWiseSummaryReportTimes) {
        this.userId = userId;
        this.userName = userName;
        this.userWiseTotal = userWiseTotal;
        this.empWiseSummaryReportTimes = empWiseSummaryReportTimes;
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

    public List<EmpWiseSummaryReportTimeDto> getEmpWiseSummaryReportTimes() {
        return empWiseSummaryReportTimes;
    }

    public void setEmpWiseSummaryReportTimes(List<EmpWiseSummaryReportTimeDto> empWiseSummaryReportTimes) {
        this.empWiseSummaryReportTimes = empWiseSummaryReportTimes;
    }

    public String getUserWiseTotal() {
        return userWiseTotal;
    }

    public void setUserWiseTotal(String userWiseTotal) {
        this.userWiseTotal = userWiseTotal;
    }

    @Override
    public String toString() {
        return "EmpWiseSummaryReportDto{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userWiseTotal='" + userWiseTotal + '\'' +
                ", empWiseSummaryReportTimes=" + empWiseSummaryReportTimes +
                '}';
    }
}
