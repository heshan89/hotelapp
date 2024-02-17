package com.hotel.dto;

import java.util.List;

public class HotelWiseSummaryReportDto {

    private String hotelName;
    private List<HotelWiseSummaryReportTimeDto> hotelWiseSummaryReportTimes;

    public HotelWiseSummaryReportDto() {
    }

    public HotelWiseSummaryReportDto(String hotelName, List<HotelWiseSummaryReportTimeDto> hotelWiseSummaryReportTimes) {
        this.hotelName = hotelName;
        this.hotelWiseSummaryReportTimes = hotelWiseSummaryReportTimes;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public List<HotelWiseSummaryReportTimeDto> getHotelWiseSummaryReportTimes() {
        return hotelWiseSummaryReportTimes;
    }

    public void setHotelWiseSummaryReportTimes(List<HotelWiseSummaryReportTimeDto> hotelWiseSummaryReportTimes) {
        this.hotelWiseSummaryReportTimes = hotelWiseSummaryReportTimes;
    }

    @Override
    public String toString() {
        return "HotelWiseSummaryReportDto{" +
                "hotelName='" + hotelName + '\'' +
                ", hotelWiseSummaryReportTimes=" + hotelWiseSummaryReportTimes +
                '}';
    }
}
