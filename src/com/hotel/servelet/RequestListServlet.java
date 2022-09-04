package com.hotel.servelet;


import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hotel.dao.ApprovedOrderDAO;
import com.hotel.dao.OrderDAO;
import com.hotel.dto.OrderDTO;
import com.hotel.dto.OrderItemDTO;
import com.hotel.dto.PlacedOrderItemDTO;
import com.hotel.dto.UsersDto;
import com.hotel.util.EmailUtil;

/**
 * Servlet implementation class RequestListServlet
 */
@WebServlet("/RequestListServlet")
public class RequestListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		LocalDate filterOrderDate = LocalDate.parse(request.getParameter("filterDate"), formatter);
		Integer filterFloor = "ALL".equalsIgnoreCase((String)request.getParameter("filterFloor")) ? null 
																	: Integer.parseInt(request.getParameter("filterFloor"));
		
		Map<Integer, List<PlacedOrderItemDTO>> groupedFloorItem = getApprovedPlacedOrderList(filterOrderDate, filterFloor)
				.stream()
				.collect(Collectors.groupingBy(PlacedOrderItemDTO::getFloor));
		
		request.setAttribute("hystoryData", groupedFloorItem);
		request.setAttribute("filterOrderDate", filterOrderDate);
		request.setAttribute("filterFloor", filterFloor);
		session.setAttribute("filterOrderDate", filterOrderDate);
		session.setAttribute("filterFloor", filterFloor);
		
		
		RequestDispatcher rd=request.getRequestDispatcher("/requestlist.jsp");  
        rd.forward(request, response); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UsersDto usersDto = (UsersDto)session.getAttribute("user");
		
		LocalDate filterOrderDate = (LocalDate)session.getAttribute("filterOrderDate");
		Integer filterFloor = "ALL".equalsIgnoreCase((String)session.getAttribute("filterFloor")) ? null 
				: (Integer)session.getAttribute("filterFloor");
		List<PlacedOrderItemDTO> getApprovedPlacedOrderList = getApprovedPlacedOrderList(filterOrderDate, filterFloor);
		
		if (request.getParameter("reset") != null) {
			Map<Integer, List<PlacedOrderItemDTO>> groupedFloorItem = getApprovedPlacedOrderList
					.stream()
					.collect(Collectors.groupingBy(PlacedOrderItemDTO::getFloor));
			request.setAttribute("hystoryData", groupedFloorItem);
			request.setAttribute("filterOrderDate", filterOrderDate);
			request.setAttribute("filterFloor", filterFloor);
			RequestDispatcher rd=request.getRequestDispatcher("/requestlist.jsp");  
	        rd.forward(request, response);
		} else if (request.getParameter("update") != null) {
	        
	        getApprovedPlacedOrderList.forEach(placedOrder -> {
	        	String name = "floor-"+placedOrder.getFloor()+"-"+placedOrder.getId();
	        	placedOrder.setAmount(Integer.parseInt(request.getParameter(name)));
	        });
	        
	        Map<Integer, Map<Integer, List<PlacedOrderItemDTO>>> orderFloorMap = getApprovedPlacedOrderList.stream()
	        		.collect(
	        				Collectors.groupingBy(PlacedOrderItemDTO::getOrderId, Collectors.groupingBy(PlacedOrderItemDTO::getFloor)));
	        List<OrderDTO> orderDtoList = new ArrayList<>();
	        if(Objects.nonNull(orderFloorMap)) {
	        	orderFloorMap.keySet().forEach(order -> {
	        		OrderDTO orderDto = new OrderDTO();
	        		orderDto.setId(order);
	        		orderDto.setDate(filterOrderDate);
	        		orderDto.setUserName(usersDto.getUserName());
		        	Map<Integer, List<PlacedOrderItemDTO>> floorMap = orderFloorMap.get(order);
		        	floorMap.keySet().forEach(floor -> {
		        		orderDto.setFloor(floor);
		        		List<OrderItemDTO> itemList = new ArrayList<>();
		        		List<PlacedOrderItemDTO> valueList = floorMap.get(floor);
		        		valueList.forEach(item -> {
		        			OrderItemDTO orderItemDto = new OrderItemDTO();
		        			orderItemDto.setId(item.getId());
		        			orderItemDto.setItemName(item.getItemName());
		        			orderItemDto.setOrderId(order);
		        			orderItemDto.setQuantity(item.getAmount());
		        			itemList.add(orderItemDto);
		        		});
		        		orderDto.setOrderItemList(itemList);
		        	});
		        	orderDtoList.add(orderDto);
		        });
	        }
	        
	        ApprovedOrderDAO approvedOrderDAO = new ApprovedOrderDAO();
	        approvedOrderDAO.insertUpdateOrder(orderDtoList);
	        
	        Map<Integer, List<PlacedOrderItemDTO>> groupedFloorItem = getApprovedPlacedOrderList
					.stream()
					.collect(Collectors.groupingBy(PlacedOrderItemDTO::getFloor));
	        
	        System.out.println("getApprovedPlacedOrderList : "+getApprovedPlacedOrderList);
	        Map<String, Integer> itemQuantityList = getApprovedPlacedOrderList.stream()
	        		  .collect(Collectors.groupingBy(PlacedOrderItemDTO::getItemName, Collectors.summingInt(PlacedOrderItemDTO::getAmount)));
	        System.out.println("itemQuantityList : "+itemQuantityList);
	        
	        request.setAttribute("summaryItemAmount", itemQuantityList);
	        request.setAttribute("hystoryData", groupedFloorItem);
			request.setAttribute("filterOrderDate", filterOrderDate);
			request.setAttribute("filterFloor", filterFloor);
			RequestDispatcher rd=request.getRequestDispatcher("/requestlist.jsp");  
	        rd.forward(request, response);
		} else if (request.getParameter("sendEmail") != null) {
			Map<String, Integer> itemQuantityMap = getApprovedPlacedOrderList.stream()
	        		  .collect(Collectors.groupingBy(PlacedOrderItemDTO::getItemName, Collectors.summingInt(PlacedOrderItemDTO::getAmount)));
			EmailUtil emailUtil = new EmailUtil();
			emailUtil.sendEmail(filterOrderDate, itemQuantityMap);

			RequestDispatcher rd=request.getRequestDispatcher("/requestlist.jsp");  
	        rd.forward(request, response);
		}
	}
	
	
	/**
	 * get the already saved item list
	 * @param orderDate
	 * @param floorNumber
	 * @return
	 */
	private List<PlacedOrderItemDTO> getApprovedPlacedOrderList(LocalDate orderDate, Integer floorNumber){
		
		ApprovedOrderDAO approvedOrderDAO= new ApprovedOrderDAO();
		List<PlacedOrderItemDTO> placedOrderItemDTOs = approvedOrderDAO.selectOrderItemByDateFloor(orderDate, floorNumber);
		
		if(placedOrderItemDTOs.isEmpty()) {
			OrderDAO orderDAO= new OrderDAO();
			placedOrderItemDTOs = orderDAO.selectOrderItemByDateFloorUser(orderDate, floorNumber, null);
		}
		System.out.println("placedOrderItemDTOs : "+placedOrderItemDTOs);
		return placedOrderItemDTOs;
	}

}
