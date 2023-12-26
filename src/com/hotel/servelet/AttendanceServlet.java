package com.hotel.servelet;

import com.hotel.dao.HotelDAO;
import com.hotel.dao.UserAttendanceHotelDAO;
import com.hotel.dto.HotelDto;
import com.hotel.dto.TodayAttendanceDto;
import com.hotel.dto.UsersDto;
import com.hotel.input.UserCheckInInput;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Servlet implementation class AttendanceServlet
 */
@WebServlet("/AttendanceServlet")
public class AttendanceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AttendanceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("checkInBtnEnable", "false");
        request.setAttribute("checkOutBteEnable", "false");

        HttpSession session = request.getSession();

        // load hotels
        HotelDAO hotelDAO = new HotelDAO();
        List<HotelDto> allActiveHotels = hotelDAO.getAllActiveHotels();

        request.setAttribute("allActiveHotels", allActiveHotels);

        session.setAttribute("allActiveHotels", allActiveHotels);

        UserAttendanceHotelDAO userAttendanceHotelDAO = new UserAttendanceHotelDAO();

        // enable check in check out button
        UsersDto user = (UsersDto) session.getAttribute("user");

        if (userAttendanceHotelDAO.isInCompleteWorkToday(user.getId())) { // enable the checkout button
            request.setAttribute("checkInBtnEnable", "false");
            request.setAttribute("checkOutBteEnable", "true");
        } else { // enable check in button
            request.setAttribute("checkInBtnEnable", "true");
            request.setAttribute("checkOutBteEnable", "false");
        }

        // load the today's attendance
        List<TodayAttendanceDto> todayAttendanceList = userAttendanceHotelDAO.getTodayAttendance(user.getId());

        for (int i = 0; i < todayAttendanceList.size(); i++) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            TodayAttendanceDto todayAttendance = todayAttendanceList.get(i);

            todayAttendance.setFormattedCheckIn(todayAttendance.getCheckIn().format(formatter));

            if (todayAttendance.getCheckOut() != null) {

                todayAttendance.setFormattedCheckOut(todayAttendance.getCheckOut().format(formatter));

                Duration duration = Duration.between(todayAttendance.getCheckIn(), todayAttendance.getCheckOut());

                // Extract components of the duration
                long durationHours = duration.toHours();
                long durationMinutes = duration.toMinutes() % 60;
                long durationSeconds = duration.getSeconds() % 60;

                todayAttendance.setDuration((durationHours > 9 ? durationHours : "0" + durationHours) + ":" +
                        (durationMinutes > 9 ? durationMinutes : "0" + durationMinutes) + ":" +
                        (durationSeconds > 9 ? durationSeconds : "0" + durationSeconds));

                if (i+1 < todayAttendanceList.size()) {
                    TodayAttendanceDto nextAttendance = todayAttendanceList.get(i+1);

                    Duration breakTime = Duration.between(todayAttendance.getCheckOut(), nextAttendance.getCheckIn());

                    // Extract components of the duration
                    long breakTimeHours = breakTime.toHours();
                    long breakTimeMinutes = breakTime.toMinutes() % 60;
                    long breakTimeSeconds = breakTime.getSeconds() % 60;

                    todayAttendance.setBreakTime((breakTimeHours > 9 ? breakTimeHours : "0" + breakTimeHours) + ":" +
                            (breakTimeMinutes > 9 ? breakTimeMinutes : "0" + breakTimeMinutes) + ":" +
                            (breakTimeSeconds > 9 ? breakTimeSeconds : "0" + breakTimeSeconds));
                }
            }
        }

        request.setAttribute("todayAttendanceList", todayAttendanceList);

        RequestDispatcher rd = request.getRequestDispatcher("/attendance.jsp");
        rd.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UsersDto user = (UsersDto) session.getAttribute("user");

        request.setAttribute("checkInSuccess", "false");
        request.setAttribute("checkInError", "false");

        request.setAttribute("checkOutSuccess", "false");
        request.setAttribute("checkOutError", "false");

        UserAttendanceHotelDAO userAttendanceHotelDAO = new UserAttendanceHotelDAO();

        UserCheckInInput userCheckInInput = new UserCheckInInput();

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        if (request.getParameter("checkIn") != null) {
            int hotelId = Integer.parseInt(request.getParameter("hotel"));

            // set initial checkout

            // 8hours time duration
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.HOUR, 8);
            Timestamp checkOutTimestamp = new Timestamp(calendar.getTimeInMillis());

            // End of today (EOD)
            Calendar endOfDay = new GregorianCalendar();
            // Set time to just before midnight for the next day
            endOfDay.set(Calendar.HOUR_OF_DAY, 23);
            endOfDay.set(Calendar.MINUTE, 59);
            endOfDay.set(Calendar.SECOND, 59);
            endOfDay.set(Calendar.MILLISECOND, 999);
            Timestamp endOfDayTimestamp = new Timestamp(endOfDay.getTimeInMillis());


            // Check if the future timestamp is greater than today's midnight
            if (checkOutTimestamp.after(endOfDayTimestamp)) { // Future timestamp is greater than today's midnight
                userCheckInInput.setCheckOut(endOfDayTimestamp);
            } else { // Future timestamp is not greater than today's midnight
                userCheckInInput.setCheckOut(checkOutTimestamp);
            }

            userCheckInInput.setUserId(user.getId());
            userCheckInInput.setHotelId(hotelId);
            userCheckInInput.setCheckIn(currentTimestamp);
            userCheckInInput.setCreatedBy(user.getUserName());

            int i = userAttendanceHotelDAO.userCheckIn(userCheckInInput);

            if (i > 0) {
                request.setAttribute("checkInSuccess", "true");
            } else {
                request.setAttribute("checkInError", "true");
            }
        } else if (request.getParameter("checkOut") != null) {

            userCheckInInput.setUserId(user.getId());
            userCheckInInput.setCheckOut(currentTimestamp);
            userCheckInInput.setCreatedBy(user.getUserName());

            int i = userAttendanceHotelDAO.userCheckOut(userCheckInInput);

            if (i > 0) {
                request.setAttribute("checkOutSuccess", "true");
            } else {
                request.setAttribute("checkOutError", "true");
            }
        }

        doGet(request, response);
    }

}
