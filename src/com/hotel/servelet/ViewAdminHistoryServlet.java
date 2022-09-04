package com.hotel.servelet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hotel.dao.OrderDAO;
import com.hotel.dto.PlacedOrderItemDTO;
import com.hotel.dto.UsersDto;

/**
 * Servlet implementation class ViewRequestOrderServlet
 */
@WebServlet("/ViewAdminHistoryServlet")
public class ViewAdminHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewAdminHistoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("filterDate", LocalDate.now().format(formatter));
		request.setAttribute("filterFloor", null);
        response.sendRedirect("ahistory.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
