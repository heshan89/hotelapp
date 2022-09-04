package com.hotel.servelet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hotel.dto.UsersDto;
import org.apache.commons.lang3.tuple.Pair;

import com.hotel.dao.OrderDAO;
import com.hotel.dto.OrderDTO;
import com.hotel.dto.OrderItemDTO;
import com.hotel.exception.ObjectAlreadyExistException;

/**
 * Servlet implementation class Order
 */
@WebServlet("/OrderServelet")
public class OrderServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final String PROCESSED= "PROCESSED";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderServelet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Map<Integer, List<OrderItemDTO>> floorWiseItem = (HashMap<Integer, List<OrderItemDTO>>)Optional.ofNullable(session.getAttribute("orderList")).orElse(new HashMap<>());
					
		// set the order item values
		String itemName = request.getParameter("item");
		Integer quantity = Integer.parseInt(request.getParameter("quantity"));
		OrderItemDTO orderItemDTO = new OrderItemDTO();
		orderItemDTO.setItemName(itemName);
		orderItemDTO.setQuantity(quantity);
		
		List<OrderItemDTO> orderItemList = new ArrayList<>();
		orderItemList.add(orderItemDTO);
		
		
		// set order values
		LocalDate orderDate = LocalDate.parse(request.getParameter("date"), formatter);
		Integer floor = Integer.parseInt(request.getParameter("floor"));
		OrderDTO orderDTO= new OrderDTO();
		orderDTO.setDate(orderDate);
		orderDTO.setFloor(floor);
		
		OrderDAO orderDAO = new OrderDAO();
		UsersDto userDto = (UsersDto) session.getAttribute("user");
		Integer orderId = orderDAO.selectOrderByDateFloor(orderDate, floor, PROCESSED);
		if(orderId!=null || orderDate.isBefore(LocalDate.now())) {
			request.setAttribute("alreadyAddedFloor", true);
			RequestDispatcher rd=request.getRequestDispatcher("/order.jsp");  
	        rd.forward(request, response);
	        return;
    	}
		
		if(floorWiseItem.containsKey(floor)) {
			Optional<OrderItemDTO> optionalItem = floorWiseItem.get(floor)
			.stream()
			.filter(item -> itemName.equalsIgnoreCase(item.getItemName()))
			.findFirst();
			
			if(optionalItem.isPresent()) {
				optionalItem.get().setQuantity(quantity);
			}else {
				floorWiseItem.get(floor).add(orderItemDTO);
			}
		} else {
			floorWiseItem.put(floor, orderItemList);
		}
		
		
		request.setAttribute("newOrder", orderDTO);
		request.setAttribute("newOrderItem", orderItemDTO);
		session.setAttribute("orderList", floorWiseItem);
		session.setAttribute("orderDate", orderDate);
		
		RequestDispatcher rd=request.getRequestDispatcher("/order.jsp");  
        rd.forward(request, response); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Order servelet method post");
		List<OrderDTO> orderList =  new ArrayList<>();
		HttpSession session = request.getSession();
		Map<Integer, List<OrderItemDTO>> floorWiseItem = (HashMap<Integer, List<OrderItemDTO>>)Optional.ofNullable(session.getAttribute("orderList")).orElse(new HashMap<>());
		LocalDate orderDate = (LocalDate) session.getAttribute("orderDate");

		UsersDto userDto = (UsersDto) session.getAttribute("user");
		floorWiseItem.keySet().forEach(floor -> {
			orderList.add(new OrderDTO(0, orderDate, floor, 0, userDto.getUserName(), floorWiseItem.get(floor)));
		});
		
		OrderDAO orderDAO = new OrderDAO();
		orderDAO.insertOrder(orderList);
		

		session.setAttribute("orderList", null);
		session.setAttribute("orderDate", null);
		
		RequestDispatcher rd=request.getRequestDispatcher("/order.jsp");  
        rd.forward(request, response);
	}

}
