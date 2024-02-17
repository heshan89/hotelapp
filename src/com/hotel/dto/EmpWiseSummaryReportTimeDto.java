package com.hotel.dto;

public class EmpWiseSummaryReportTimeDto {

    private String hotelName;
    private String timeDuration;
    private String wagePerHour;
    private String total;

    public EmpWiseSummaryReportTimeDto() {
    }

    public EmpWiseSummaryReportTimeDto(String hotelName, String timeDuration, String wagePerHour, String total) {
        this.hotelName = hotelName;
        this.timeDuration = timeDuration;
        this.wagePerHour = wagePerHour;
        this.total = total;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(String timeDuration) {
        this.timeDuration = timeDuration;
    }

    public String getWagePerHour() {
        return wagePerHour;
    }

    public void setWagePerHour(String wagePerHour) {
        this.wagePerHour = wagePerHour;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "EmpWiseSummaryReportTimeDto{" +
                "hotelName='" + hotelName + '\'' +
                ", timeDuration='" + timeDuration + '\'' +
                ", wagePerHour='" + wagePerHour + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}
