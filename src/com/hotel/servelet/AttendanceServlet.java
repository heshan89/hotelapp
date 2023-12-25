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
		HotelDAO hotelDAO = new HotelDAO();
		List<HotelDto> allActiveHotels = hotelDAO.getAllActiveHotels();

		request.setAttribute("allActiveHotels", allActiveHotels);

		HttpSession session = request.getSession();
		session.setAttribute("allActiveHotels", allActiveHotels);

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

		if (request.getParameter("checkInOut") != null) {
			int hotelId = Integer.parseInt(request.getParameter("hotel"));

			UserCheckInInput userCheckInInput = new UserCheckInInput();
			userCheckInInput.setUserId(user.getId());
			userCheckInInput.setHotelId(hotelId);
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
