package com.hotel.servelet;

import com.hotel.dao.HotelDAO;
import com.hotel.dao.UserAttendanceHotelDAO;
import com.hotel.dao.UserDAO;
import com.hotel.dto.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
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

        // Generate report
        if (request.getParameter("genReport") != null) {

            // Summary Report Employee Wise
            UserAttendanceHotelDAO userAttendanceHotelDAO = new UserAttendanceHotelDAO();
            String from = request.getParameter("from");
            String to = request.getParameter("to");
            List<EmployeeWiseReportDto> employeeWiseReportList = userAttendanceHotelDAO.employeeWiseReport(from, to);

            List<EmpWiseSummaryReportDto> empWiseSummaryReportList = new ArrayList<>();

            if (employeeWiseReportList.isEmpty()) {
                request.setAttribute("noRecordFound_employeeWiseReport", "true");
            } else {

                Map<String, List<EmployeeWiseReportDto>> employeeWiseReportMap = employeeWiseReportList.stream()
                        .collect(Collectors.groupingBy(EmployeeWiseReportDto::getUserId));

                for (Map.Entry<String, List<EmployeeWiseReportDto>> employeeWiseReportEntry : employeeWiseReportMap.entrySet()) {
                    EmpWiseSummaryReportDto empWiseSummaryReportDto = new EmpWiseSummaryReportDto();
                    empWiseSummaryReportDto.setUserId(employeeWiseReportEntry.getKey());
                    empWiseSummaryReportDto.setUserName(employeeWiseReportEntry.getValue().get(0).getUserName());

                    Map<String, List<EmployeeWiseReportDto>> hotelMap = employeeWiseReportEntry.getValue()
                            .stream()
                            .collect(Collectors.groupingBy(val -> val.getHotelName()));

                    List<EmpWiseSummaryReportTimeDto> empWiseSummaryReportTimeList = new ArrayList<>();
                    for (Map.Entry<String, List<EmployeeWiseReportDto>> hotelEntry : hotelMap.entrySet()) {
                        EmpWiseSummaryReportTimeDto empWiseSummaryReportTimeDto = new EmpWiseSummaryReportTimeDto();
                        empWiseSummaryReportTimeDto.setHotelName(hotelEntry.getKey());

                        long totalMills = 0;
                        for (EmployeeWiseReportDto ewr : hotelEntry.getValue()) {
                            long gapMills = Duration.between(ewr.getSystemCheckOut(), ewr.getSystemCheckIn()).toMillis();
                            totalMills = totalMills + gapMills;
                        }
                        long timeDurHourMill = (totalMills / 1000) / 60 / 60;
                        long timeDurMinMill = (totalMills / 1000) / 60 % 60;
                        empWiseSummaryReportTimeDto.setTimeDuration(timeDurHourMill + "." + timeDurMinMill);

//                        empWiseSummaryReportTimeDto.setWagePerHour(emp.getHotelName());
                        empWiseSummaryReportTimeList.add(empWiseSummaryReportTimeDto);
                    }

                    empWiseSummaryReportDto.setEmpWiseSummaryReportTimes(empWiseSummaryReportTimeList);
                    empWiseSummaryReportList.add(empWiseSummaryReportDto);
                }
            }

            request.setAttribute("empWiseSummaryReportList", empWiseSummaryReportList);
        }

        request.getRequestDispatcher("reports.jsp").include(request, response);

        doGet(request, response);
    }
}
