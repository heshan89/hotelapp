package com.hotel.servelet;

import com.hotel.dao.HotelDAO;
import com.hotel.dao.UserAttendanceHotelDAO;
import com.hotel.dto.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/ReportServlet")
public class ReportServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public ReportServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // load hotels
        HotelDAO hotelDAO = new HotelDAO();
        List<HotelDto> allActiveHotels = hotelDAO.getAllActiveHotels();

        request.setAttribute("allActiveHotels", allActiveHotels);

        session.setAttribute("allActiveHotels", allActiveHotels);

        RequestDispatcher rd = request.getRequestDispatcher("/reports.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("noRecordFound_employeeWiseReport", "false");
        request.setAttribute("fieldEnable_employeeNameOrId", "false");
        request.setAttribute("fieldEnable_hotel", "false");
        request.setAttribute("fieldEnable_from", "false");
        request.setAttribute("fieldEnable_to", "false");
        request.setAttribute("buttonEnable_generateReport", "false");
        request.setAttribute("reportEnable_employeeWiseReport", "false");
        request.setAttribute("reportEnable_hotelWiseReport", "false");
        request.setAttribute("noRecordFound_hotelWiseReport", "false");

        // report selection
        String rptSelection = request.getParameter("rptSelection");
        if (rptSelection != null) {
            switch (String.valueOf(rptSelection)) {
                case "1":
                    request.setAttribute("selected1", true);
                    break;
                case "2":
                    request.setAttribute("selected2", true);
                    break;
                case "3":
                    request.setAttribute("selected3", true);
                    break;
                case "4":
                    request.setAttribute("selected4", true);
                    break;
            }

            if (rptSelection.equalsIgnoreCase("1")) {
                request.setAttribute("fieldEnable_from", "true");
                request.setAttribute("fieldEnable_to", "true");
                request.setAttribute("buttonEnable_generateReport", "true");
                request.setAttribute("reportEnable_employeeWiseReport", "true");
            } else if (rptSelection.equalsIgnoreCase("2")) {

            } else if (rptSelection.equalsIgnoreCase("3")) {
                request.setAttribute("fieldEnable_hotel", "true");
                request.setAttribute("fieldEnable_from", "true");
                request.setAttribute("fieldEnable_to", "true");
                request.setAttribute("buttonEnable_generateReport", "true");
                request.setAttribute("reportEnable_hotelWiseReport", "true");
            }
        }

        // hotel selection
        String hotelSelection = request.getParameter("selectedHotel");
        request.setAttribute("selectedHotel", hotelSelection);

        //from date selection
        String fromSelection = request.getParameter("from");
        request.setAttribute("from", fromSelection);

        //to date selection
        String toSelection = request.getParameter("to");
        request.setAttribute("to", toSelection);

        // Generate report
        if (request.getParameter("genReport") != null && rptSelection.equalsIgnoreCase("1")) {

            // Summary Report Employee Wise

            String from = request.getParameter("from");
            String to = request.getParameter("to");
            if ((from == null || from.equals("")) || (to == null || to.equals(""))) {
                request.setAttribute("noRecordFound_employeeWiseReport", "true");
            } else {

                UserAttendanceHotelDAO userAttendanceHotelDAO = new UserAttendanceHotelDAO();
                List<UserAttendanceHotelDto> employeeWiseReportList = userAttendanceHotelDAO.employeeWiseReport(from, to);

                List<EmpWiseSummaryReportDto> empWiseSummaryReportList = new ArrayList<>();

                if (employeeWiseReportList.isEmpty()) {
                    request.setAttribute("noRecordFound_employeeWiseReport", "true");
                } else {

                    Map<String, List<UserAttendanceHotelDto>> employeeWiseReportMap = employeeWiseReportList.stream()
                            .collect(Collectors.groupingBy(UserAttendanceHotelDto::getUserId));

                    for (Map.Entry<String, List<UserAttendanceHotelDto>> employeeWiseReportEntry : employeeWiseReportMap.entrySet()) {
                        EmpWiseSummaryReportDto empWiseSummaryReportDto = new EmpWiseSummaryReportDto();
                        empWiseSummaryReportDto.setUserId(employeeWiseReportEntry.getKey());
                        empWiseSummaryReportDto.setUserName(employeeWiseReportEntry.getValue().get(0).getUserName());

                        Map<String, List<UserAttendanceHotelDto>> hotelMap = employeeWiseReportEntry.getValue()
                                .stream()
                                .collect(Collectors.groupingBy(val -> val.getHotelName()));

                        List<EmpWiseSummaryReportTimeDto> empWiseSummaryReportTimeList = new ArrayList<>();
                        for (Map.Entry<String, List<UserAttendanceHotelDto>> hotelEntry : hotelMap.entrySet()) {
                            EmpWiseSummaryReportTimeDto empWiseSummaryReportTimeDto = new EmpWiseSummaryReportTimeDto();
                            empWiseSummaryReportTimeDto.setHotelName(hotelEntry.getKey());

                            long totalMills = 0;
                            for (UserAttendanceHotelDto ewr : hotelEntry.getValue()) {
                                long gapMills = Duration.between(ewr.getSystemCheckOut(), ewr.getSystemCheckIn()).toMillis();
                                totalMills = totalMills + gapMills;
                            }
                            long timeDurHourMill = (totalMills / 1000) / 60 / 60;
                            long timeDurMinMill = (totalMills / 1000) / 60 % 60;
                            empWiseSummaryReportTimeDto.setTimeDuration(timeDurHourMill + "." + timeDurMinMill);

                            double hourWage = hotelEntry.getValue().get(0).getWagePerMin() * 60;
                            BigDecimal bdHourWage = BigDecimal.valueOf(hourWage);
                            bdHourWage = bdHourWage.setScale(2, RoundingMode.HALF_DOWN);

                            empWiseSummaryReportTimeDto.setWagePerHour(Double.toString(bdHourWage.doubleValue()));
                            empWiseSummaryReportTimeDto.setTotal(Double.toString(hotelEntry.getValue().get(0).getWagePerMin() * (totalMills / 1000 / 60)));
                            empWiseSummaryReportTimeList.add(empWiseSummaryReportTimeDto);
                        }

                        empWiseSummaryReportDto.setEmpWiseSummaryReportTimes(empWiseSummaryReportTimeList);
                        empWiseSummaryReportList.add(empWiseSummaryReportDto);
                    }
                }

                request.setAttribute("empWiseSummaryReportList", empWiseSummaryReportList);
            }
        }

        if (request.getParameter("genReport") != null && rptSelection.equalsIgnoreCase("3")) {

            // Summary Report Hotel Wise

            String from = request.getParameter("from");
            String to = request.getParameter("to");
            String selectedHotel = request.getParameter("selectedHotel");
            if ((from == null || from.equals("")) || (to == null || to.equals("")) || (selectedHotel == null || selectedHotel.equals(""))) {
                request.setAttribute("noRecordFound_hotelWiseReport", "true");
            } else {

                UserAttendanceHotelDAO userAttendanceHotelDAO = new UserAttendanceHotelDAO();
                List<UserAttendanceHotelDto> userAttendanceHotelList = userAttendanceHotelDAO.hotelWiseReport(from, to, selectedHotel);

                List<HotelWiseSummaryReportDto> hotelWiseSummaryReportList = new ArrayList<>();

                if (userAttendanceHotelList.isEmpty()) {
                    request.setAttribute("noRecordFound_hotelWiseReport", "true");
                } else  {

                    Map<String, List<UserAttendanceHotelDto>> hotelWiseReportMap = userAttendanceHotelList.stream()
                            .collect(Collectors.groupingBy(UserAttendanceHotelDto::getHotelName));

                    for (Map.Entry<String, List<UserAttendanceHotelDto>> hotelWiseReportEntry : hotelWiseReportMap.entrySet()) {
                        HotelWiseSummaryReportDto hotelWiseSummaryReportDto = new HotelWiseSummaryReportDto();
                        hotelWiseSummaryReportDto.setHotelName(hotelWiseReportEntry.getKey());


                        Map<String, List<UserAttendanceHotelDto>> employeeMap = hotelWiseReportEntry.getValue()
                                .stream()
                                .collect(Collectors.groupingBy(val -> val.getUserName()));

                        List<HotelWiseSummaryReportTimeDto> hotelWiseSummaryReportTimeList = new ArrayList<>();
                        long netTotalMills = 0;
                        double netTotalWage = 0;
                        for (Map.Entry<String, List<UserAttendanceHotelDto>> employeeEntry : employeeMap.entrySet()) {
                            HotelWiseSummaryReportTimeDto hotelWiseSummaryReportTimeDto = new HotelWiseSummaryReportTimeDto();
                            hotelWiseSummaryReportTimeDto.setUserName(employeeEntry.getKey());
                            hotelWiseSummaryReportTimeDto.setUserId(employeeEntry.getValue().get(0).getUserId());

                            long totalMills = 0;
                            for (UserAttendanceHotelDto ewr : employeeEntry.getValue()) {
                                long gapMills = Duration.between(ewr.getSystemCheckOut(), ewr.getSystemCheckIn()).toMillis();
                                totalMills = totalMills + gapMills;
                            }
                            long timeDurHourMill = (totalMills / 1000) / 60 / 60;
                            long timeDurMinMill = (totalMills / 1000) / 60 % 60;
                            hotelWiseSummaryReportTimeDto.setWorkDuration(timeDurHourMill + "." + timeDurMinMill);

                            double hourWage = employeeEntry.getValue().get(0).getWagePerMin() * 60;
                            double totalWage = employeeEntry.getValue().get(0).getWagePerMin() * (totalMills / 1000 / 60);

                            BigDecimal bdHourWage = BigDecimal.valueOf(hourWage);
                            bdHourWage = bdHourWage.setScale(2, RoundingMode.HALF_DOWN);

                            hotelWiseSummaryReportTimeDto.setHourWage(Double.toString(bdHourWage.doubleValue()));
                            hotelWiseSummaryReportTimeDto.setTotal(Double.toString(totalWage));

                            hotelWiseSummaryReportTimeList.add(hotelWiseSummaryReportTimeDto);
                            netTotalMills = netTotalMills + totalMills;
                            netTotalWage = netTotalWage + totalWage;
                        }

                        long netTotalTimeDurHourMill = (netTotalMills / 1000) / 60 / 60;
                        long netTotalTimeDurMinMill = (netTotalMills / 1000) / 60 % 60;
                        request.setAttribute("hotelWiseTotalHour", netTotalTimeDurHourMill + "." + netTotalTimeDurMinMill);
                        request.setAttribute("hotelWiseNetTotalWage", netTotalWage);

                        hotelWiseSummaryReportDto.setHotelWiseSummaryReportTimes(hotelWiseSummaryReportTimeList);
                        hotelWiseSummaryReportList.add(hotelWiseSummaryReportDto);
                    }
                }

                request.setAttribute("hotelWiseSummaryReportList", hotelWiseSummaryReportList);
            }
        }

        request.getRequestDispatcher("reports.jsp").include(request, response);

        doGet(request, response);
    }
}