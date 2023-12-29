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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            TodayAttendanceDto todayAttendance = todayAttendanceList.get(i);

            todayAttendance.setFormattedUserCheckIn(todayAttendance.getUserCheckIn().format(formatter));
            todayAttendance.setFormattedSystemCheckIn(todayAttendance.getSystemCheckIn().format(formatter));

            if (todayAttendance.getUserCheckOut() != null) {

                todayAttendance.setFormattedUserCheckOut(todayAttendance.getUserCheckOut().format(formatter));
                todayAttendance.setFormattedSystemCheckOut(todayAttendance.getSystemCheckOut().format(formatter));

                Duration duration = Duration.between(todayAttendance.getSystemCheckIn(), todayAttendance.getSystemCheckOut());

                // Extract components of the duration
                long durationHours = duration.toHours();
                long durationMinutes = duration.toMinutes() % 60;

                todayAttendance.setDuration((durationHours > 9 ? durationHours : "0" + durationHours) + ":" +
                        (durationMinutes > 9 ? durationMinutes : "0" + durationMinutes));

                if (i + 1 < todayAttendanceList.size()) {
                    TodayAttendanceDto nextAttendance = todayAttendanceList.get(i + 1);

                    Duration breakTime = Duration.between(todayAttendance.getUserCheckOut(), nextAttendance.getUserCheckIn());

                    // Extract components of the duration
                    long breakTimeHours = breakTime.toHours();
                    long breakTimeMinutes = breakTime.toMinutes() % 60;

                    todayAttendance.setBreakTime((breakTimeHours > 9 ? breakTimeHours : "0" + breakTimeHours) + ":" +
                            (breakTimeMinutes > 9 ? breakTimeMinutes : "0" + breakTimeMinutes));
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
        request.setAttribute("invalidCheckInError_notWithinHotelCheckInCheckOut", "false");
        request.setAttribute("invalidCheckInError_checkInAfterHotelCheckout", "false");
        request.setAttribute("invalidCheckInError_checkInAfterMidNight", "false");

        request.setAttribute("checkOutSuccess", "false");
        request.setAttribute("checkOutError", "false");
        request.setAttribute("invalidCheckOutError_checkOutBeforeHotelCheckIn", "false");
        request.setAttribute("invalidCheckOutError_checkOutAfterMidNight", "false");
        request.setAttribute("invalidCheckOutError_checkOutBeforeSystemCheckIn", "false");

        UserAttendanceHotelDAO userAttendanceHotelDAO = new UserAttendanceHotelDAO();

        HotelDAO hotelDAO = new HotelDAO();

        UserCheckInInput userCheckInInput = new UserCheckInInput();

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        if (request.getParameter("checkIn") != null) {
            int hotelId = Integer.parseInt(request.getParameter("hotel"));

            HotelDto hotel = hotelDAO.getHotelInfo(hotelId);

            Timestamp systemCheckInTimestamp = null;

            LocalTime hotelCheckIn = hotel.getHotelCheckIn().toLocalTime();
            LocalTime hotelCheckOut = hotel.getHotelCheckOut().toLocalTime();
            LocalTime hotelCheckInThreshold = hotel.getHotelCheckInThreshold().toLocalTime();

            if (currentTimestamp.toLocalDateTime().toLocalTime().isBefore(hotelCheckIn)) { // check In time is less than or equal to hotel check in time
                systemCheckInTimestamp = Timestamp.valueOf(currentTimestamp.toLocalDateTime().toLocalDate().atTime(hotelCheckIn.getHour(), hotelCheckIn.getMinute(), hotelCheckIn.getSecond()));

                insertCheckIn(request, user, userAttendanceHotelDAO, userCheckInInput, currentTimestamp, hotelId, systemCheckInTimestamp, hotelCheckOut);
            } else if (currentTimestamp.toLocalDateTime().toLocalTime().isAfter(hotelCheckIn) && currentTimestamp.toLocalDateTime().toLocalTime().isBefore(hotelCheckOut)) { // check in time is after hotel check in time and before hotel checkout time
                int hourToAdd = 0;
                int minuteToAdd = 0;

                int hour = currentTimestamp.toLocalDateTime().getHour();
                int minute = currentTimestamp.toLocalDateTime().getMinute();

                if (hotelCheckInThreshold.getHour() > 0) {
                    if (hour == hotelCheckIn.getHour() && minute == hotelCheckIn.getMinute()) {
                    } else {
                        hourToAdd = hotelCheckInThreshold.getHour() - (hour % hotelCheckInThreshold.getHour());
                    }
                }

                if (hotelCheckInThreshold.getMinute() > 0) {
                    for (int i = hotelCheckInThreshold.getMinute(); i < 60; i += hotelCheckInThreshold.getMinute()) {
                        if ((hour == hotelCheckIn.getHour() && minute == hotelCheckIn.getMinute()) || minute == i) {
                            break;
                        } else if (minute % hotelCheckInThreshold.getMinute() != 0) {
                            minuteToAdd = hotelCheckInThreshold.getMinute() - (minute % hotelCheckInThreshold.getMinute());
                            break;
                        }
                    }
                }

                int addSystemCheckInHour = hourToAdd == 0 ? currentTimestamp.toLocalDateTime().getHour() : currentTimestamp.toLocalDateTime().getHour() + hourToAdd;
                int addSystemCheckInMinute = minuteToAdd == 0 ? currentTimestamp.toLocalDateTime().getMinute() : currentTimestamp.toLocalDateTime().getMinute() + minuteToAdd;

                if (addSystemCheckInMinute == 60) {
                    addSystemCheckInHour = addSystemCheckInHour + 1;
                    addSystemCheckInMinute = 0;
                }

                if (addSystemCheckInHour == 24) {
                    request.setAttribute("invalidCheckInError_checkInAfterMidNight", "true");
                } else {
                    systemCheckInTimestamp = Timestamp.valueOf(currentTimestamp.toLocalDateTime().toLocalDate().atTime(addSystemCheckInHour, addSystemCheckInMinute, 0));

                    if (systemCheckInTimestamp.toLocalDateTime().toLocalTime().isAfter(hotel.getHotelCheckOut().toLocalTime())) {
                        request.setAttribute("invalidCheckInError_checkInAfterHotelCheckout", "true");
                    } else {
                        insertCheckIn(request, user, userAttendanceHotelDAO, userCheckInInput, currentTimestamp, hotelId, systemCheckInTimestamp, hotelCheckOut);
                    }
                }

            } else { // check in time is grater than hotel check out time
                request.setAttribute("invalidCheckInError_notWithinHotelCheckInCheckOut", "true");
            }
        } else if (request.getParameter("checkOut") != null) {

            HotelDto hotel = hotelDAO.getHotelInfoForUser(user.getId());

            Timestamp systemCheckOutTimestamp = null;

            LocalTime hotelCheckIn = hotel.getHotelCheckIn().toLocalTime();
            LocalTime hotelCheckOut = hotel.getHotelCheckOut().toLocalTime();
            LocalTime hotelCheckOutThreshold = hotel.getHotelCheckOutThreshold().toLocalTime();
            Timestamp userSystemCheckIn = hotel.getSystemCheckIn();

            if (currentTimestamp.toLocalDateTime().toLocalTime().isAfter(hotelCheckOut)) { // check out time is greater than or equal to hotel check out time
                systemCheckOutTimestamp = Timestamp.valueOf(currentTimestamp.toLocalDateTime().toLocalDate().atTime(hotelCheckOut.getHour(), hotelCheckOut.getMinute(), hotelCheckOut.getSecond()));

                updateCheckOut(request, user, userAttendanceHotelDAO, userCheckInInput, currentTimestamp, systemCheckOutTimestamp);
            } else if (currentTimestamp.toLocalDateTime().toLocalTime().isAfter(hotelCheckIn) && currentTimestamp.toLocalDateTime().toLocalTime().isBefore(hotelCheckOut)) { // check out time is after hotel check in time and before hotel checkout time
                int hourToDeduct = 0;
                int minuteToDeduct = 0;

                int hour = currentTimestamp.toLocalDateTime().getHour();
                int minute = currentTimestamp.toLocalDateTime().getMinute();

                if (hotelCheckOutThreshold.getHour() > 0) {
                    if ((hour == hotelCheckOut.getHour() && minute == hotelCheckOut.getMinute())) {
                    } else {
                        hourToDeduct = hour % hotelCheckOutThreshold.getHour();
                    }
                }

                if (hotelCheckOutThreshold.getMinute() > 0) {
                    for (int i = hotelCheckOutThreshold.getMinute(); i < 60; i += hotelCheckOutThreshold.getMinute()) {
                        if ((hour == hotelCheckOut.getHour() && minute == hotelCheckOut.getMinute()) || minute == i) {
                            break;
                        } else if (minute % hotelCheckOutThreshold.getMinute() != 0) {
                            minuteToDeduct = minute % hotelCheckOutThreshold.getMinute();
                            break;
                        }
                    }
                }

                int addSystemCheckOutHour = hourToDeduct == 0 ? currentTimestamp.toLocalDateTime().getHour() : currentTimestamp.toLocalDateTime().getHour() - hourToDeduct;
                int addSystemCheckOutMinute = minuteToDeduct == 0 ? currentTimestamp.toLocalDateTime().getMinute() : currentTimestamp.toLocalDateTime().getMinute() - minuteToDeduct;

                if (addSystemCheckOutMinute == 60) {
                    addSystemCheckOutHour = addSystemCheckOutHour - 1;
                    addSystemCheckOutMinute = 0;
                }

                if (addSystemCheckOutHour == 24) {
                    request.setAttribute("invalidCheckOutError_checkOutAfterMidNight", "true");
                } else {
                    systemCheckOutTimestamp = Timestamp.valueOf(currentTimestamp.toLocalDateTime().toLocalDate().atTime(addSystemCheckOutHour, addSystemCheckOutMinute, 0));

                    if (systemCheckOutTimestamp.toLocalDateTime().toLocalTime().isBefore(hotel.getHotelCheckIn().toLocalTime())) {
                        request.setAttribute("invalidCheckOutError_checkOutBeforeHotelCheckIn", "true");
                    } else if (systemCheckOutTimestamp.before(userSystemCheckIn)) {
                        request.setAttribute("invalidCheckOutError_checkOutBeforeSystemCheckIn", "true");
                    } else {
                        updateCheckOut(request, user, userAttendanceHotelDAO, userCheckInInput, currentTimestamp, systemCheckOutTimestamp);
                    }
                }
            }
        }

        doGet(request, response);
    }

    private void updateCheckOut(HttpServletRequest request, UsersDto user, UserAttendanceHotelDAO userAttendanceHotelDAO, UserCheckInInput userCheckInInput, Timestamp currentTimestamp, Timestamp systemCheckOutTimestamp) {
        userCheckInInput.setUserId(user.getId());
        userCheckInInput.setUserCheckOut(currentTimestamp);
        userCheckInInput.setSystemCheckOut(systemCheckOutTimestamp);
        userCheckInInput.setCreatedBy(user.getUserName());

        int i = userAttendanceHotelDAO.userCheckOut(userCheckInInput);

        if (i > 0) {
            request.setAttribute("checkOutSuccess", "true");
        } else {
            request.setAttribute("checkOutError", "true");
        }
    }

    private void insertCheckIn(HttpServletRequest request, UsersDto user, UserAttendanceHotelDAO userAttendanceHotelDAO, UserCheckInInput userCheckInInput, Timestamp currentTimestamp, int hotelId, Timestamp systemCheckInTimestamp, LocalTime hotelCheckOut) {
        Timestamp userCheckOutTimestamp = Timestamp.valueOf(currentTimestamp.toLocalDateTime().toLocalDate().atTime(hotelCheckOut.getHour(), hotelCheckOut.getMinute(), hotelCheckOut.getSecond()));

        userCheckInInput.setUserId(user.getId());
        userCheckInInput.setHotelId(hotelId);
        userCheckInInput.setUserCheckIn(currentTimestamp);
        userCheckInInput.setSystemCheckIn(systemCheckInTimestamp);
        userCheckInInput.setUserCheckOut(userCheckOutTimestamp);
        userCheckInInput.setSystemCheckOut(userCheckOutTimestamp);
        userCheckInInput.setCreatedBy(user.getUserName());

        int i = userAttendanceHotelDAO.userCheckIn(userCheckInInput);

        if (i > 0) {
            request.setAttribute("checkInSuccess", "true");
        } else {
            request.setAttribute("checkInError", "true");
        }
    }

}
