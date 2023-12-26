package com.hotel.servelet;

import com.hotel.dao.HotelDAO;
import com.hotel.dao.UserAttendanceHotelDAO;
import com.hotel.dto.HotelDto;
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
import java.time.*;
import java.time.temporal.ChronoUnit;
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

		// enable check in check out button
		UsersDto user = (UsersDto) session.getAttribute("user");
		UserAttendanceHotelDAO userAttendanceHotelDAO = new UserAttendanceHotelDAO();
		if (userAttendanceHotelDAO.isInCompleteWorkToday(user.getId())) { // enable the checkout button
			request.setAttribute("checkInBtnEnable", "false");
			request.setAttribute("checkOutBteEnable", "true");
		} else { // enable check in button
			request.setAttribute("checkInBtnEnable", "true");
			request.setAttribute("checkOutBteEnable", "false");
		}

		RequestDispatcher rd=request.getRequestDispatcher("/attendance.jsp");
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

		if (request.getParameter("checkIn") != null) {
			int hotelId = Integer.parseInt(request.getParameter("hotel"));

			Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

			// set initial checkout

			//
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.HOUR, 8);
			Timestamp checkOutTimestamp = new Timestamp(calendar.getTimeInMillis());

			// today's midnight
			Calendar date = new GregorianCalendar();
			// reset hour, minutes, seconds and millis
			date.set(Calendar.HOUR_OF_DAY, 0);
			date.set(Calendar.MINUTE, 0);
			date.set(Calendar.SECOND, 0);
			date.set(Calendar.MILLISECOND, 0);
			Timestamp midNightTimestamp = new Timestamp(date.getTimeInMillis());

			UserCheckInInput userCheckInInput = new UserCheckInInput();

			// Check if the future timestamp is greater than today's midnight
			if (checkOutTimestamp.after(midNightTimestamp)) { // Future timestamp is greater than today's midnight
				userCheckInInput.setCheckOut(midNightTimestamp);
			} else { // Future timestamp is not greater than today's midnight
				userCheckInInput.setCheckOut(checkOutTimestamp);
			}

			userCheckInInput.setUserId(user.getId());
			userCheckInInput.setHotelId(hotelId);
			userCheckInInput.setCheckIn(currentTimestamp);
			userCheckInInput.setCreatedBy(user.getUserName());

			UserAttendanceHotelDAO userAttendanceHotelDAO = new UserAttendanceHotelDAO();
			int i = userAttendanceHotelDAO.userCheckIn(userCheckInInput);

			if (i > 0) {
				request.setAttribute("checkInSuccess", "true");
			} else {
				request.setAttribute("checkInError", "true");
			}
		}

		doGet(request, response);
	}

}
