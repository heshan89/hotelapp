package com.hotel.servelet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;import java.util.stream.Collector;
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
 * Servlet implementation class CheckerHistoryServelet
 */
@WebServlet("/CheckerHistoryServelet")
public class CheckerHistoryServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final String STATUS= "PROCESSED";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckerHistoryServelet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UsersDto usersDto = (UsersDto)session.getAttribute("user");
		
		LocalDate filterOrderDate = LocalDate.parse(request.getParameter("filterDate"), formatter);
		Integer filterFloor = "ALL".equalsIgnoreCase((String)request.getParameter("filterFloor")) ? null 
																	: Integer.parseInt(request.getParameter("filterFloor"));
		
		OrderDAO orderDAO= new OrderDAO();
		List<PlacedOrderItemDTO> placedOrderItemDTOs = orderDAO.selectOrderItemByDateFloorUser(filterOrderDate, filterFloor, usersDto.getUserName(), null);
		
		Map<Integer, List<PlacedOrderItemDTO>> groupedFloorItem = placedOrderItemDTOs
				.stream()
				.collect(Collectors.groupingBy(PlacedOrderItemDTO::getFloor));
		
		request.setAttribute("hystoryData", groupedFloorItem);
		request.setAttribute("filterOrderDate", filterOrderDate);
		request.setAttribute("filterFloor", filterFloor);
		
		RequestDispatcher rd=request.getRequestDispatcher("/chistory.jsp");  
        rd.forward(request, response); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
