package com.hotel.servelet;


import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
import com.hotel.dao.enums.ItemEnum;
import com.hotel.dto.OrderDTO;
import com.hotel.dto.OrderItemDTO;
import com.hotel.dto.PlacedOrderItemDTO;
import com.hotel.dto.UsersDto;
import com.hotel.util.EmailUtil;
import com.hotel.util.ExcelGenerator;

/**
 * Servlet implementation class RequestListServlet
 */
@WebServlet("/RequestListServlet")
public class RequestListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final String STATUS= "ACTIVE";
	private static final String PROCESSED= "PROCESSED";
       
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

		if((Boolean)session.getAttribute("newfilteration")) {
			session.setAttribute("newfilteration", true);
			request.setAttribute("existUpdateDate", true);
			RequestDispatcher rd=request.getRequestDispatcher("/requestlist.jsp");  
	        rd.forward(request, response); 
	        return;
		}
		
		LocalDate filterOrderDate = LocalDate.parse(Optional.ofNullable(request.getParameter("filterDate")).orElse(LocalDate.now().toString()), formatter);
		
		String floor = Optional.ofNullable(request.getParameter("filterFloor")).orElse("0");
		Integer filterFloor = "ALL".equalsIgnoreCase(floor) ? null : Integer.parseInt(floor);
		
		Map<Integer, List<PlacedOrderItemDTO>> groupedFloorItem = getApprovedPlacedOrderList(filterOrderDate, filterFloor, STATUS)
				.stream()
				.collect(Collectors.groupingBy(PlacedOrderItemDTO::getFloor));
		
		request.setAttribute("filterOrderDate", filterOrderDate);
		request.setAttribute("filterFloor", filterFloor);
		request.setAttribute("sendEmailSuccess", false);
		session.setAttribute("hystoryData", groupedFloorItem);
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
//		Integer filterFloor = "ALL".equalsIgnoreCase((String)session.getAttribute("filterFloor")) ? null 
//				: (Integer)session.getAttribute("filterFloor");
		
		
		if (request.getParameter("reset") != null) {
			List<PlacedOrderItemDTO> getApprovedPlacedOrderList = getApprovedPlacedOrderList(filterOrderDate, null, STATUS);
			Map<Integer, List<PlacedOrderItemDTO>> groupedFloorItem = getApprovedPlacedOrderList
					.stream()
					.collect(Collectors.groupingBy(PlacedOrderItemDTO::getFloor));
			session.setAttribute("hystoryData", groupedFloorItem);
			request.setAttribute("filterOrderDate", filterOrderDate);
			session.setAttribute("newfilteration", false);
			RequestDispatcher rd=request.getRequestDispatcher("/requestlist.jsp");  
	        rd.forward(request, response);
		} else if (request.getParameter("update") != null) {
	        
			@SuppressWarnings("unchecked")
			Map<Integer, List<PlacedOrderItemDTO>> groupedFloorItem = (Map<Integer, List<PlacedOrderItemDTO>>)session.getAttribute("hystoryData");
			
			List<PlacedOrderItemDTO> getApprovedPlacedOrderList = new ArrayList<>();
			groupedFloorItem.values().stream().forEach(list -> {
				getApprovedPlacedOrderList.addAll(list);
			});
			
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
	        
	        Map<Integer, List<PlacedOrderItemDTO>> updateFloorItemMap = getApprovedPlacedOrderList
					.stream()
					.collect(Collectors.groupingBy(PlacedOrderItemDTO::getFloor));
	        
	        Map<String, Integer> itemQuantityList = getApprovedPlacedOrderList.stream()
	        		  .collect(Collectors.groupingBy(PlacedOrderItemDTO::getItemName, Collectors.summingInt(PlacedOrderItemDTO::getAmount)));
	        
	        request.setAttribute("summaryItemAmount", itemQuantityList);
			request.setAttribute("filterOrderDate", filterOrderDate);
			session.setAttribute("hystoryData", updateFloorItemMap);
			session.setAttribute("newfilteration", false);
			
			RequestDispatcher rd=request.getRequestDispatcher("/requestlist.jsp");  
	        rd.forward(request, response);
		} else if (request.getParameter("sendEmail") != null) {
			List<PlacedOrderItemDTO> getApprovedPlacedOrderList = getApprovedPlacedOrderList(filterOrderDate, null, STATUS);
			Map<String, Integer> itemQuantityMap = getApprovedPlacedOrderList.stream()
	        		  .collect(Collectors.groupingBy(PlacedOrderItemDTO::getItemName, Collectors.summingInt(PlacedOrderItemDTO::getAmount)));

			ExcelGenerator excelGenerator = new ExcelGenerator();
			Map<String, String> fileMap = excelGenerator.generate(getApprovedPlacedOrderList, usersDto.getUserName());

			EmailUtil emailUtil = new EmailUtil();
			emailUtil.sendEmail(filterOrderDate, itemQuantityMap, fileMap);
			
			OrderDAO orderDao= new OrderDAO();
			orderDao.updateOrderStatus(filterOrderDate, PROCESSED);
			
			ApprovedOrderDAO approvedOrderDAO= new ApprovedOrderDAO();
			approvedOrderDAO.updateOrderStatus(filterOrderDate, PROCESSED);
			

			request.setAttribute("sendEmailSuccess", true);

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
	private List<PlacedOrderItemDTO> getApprovedPlacedOrderList(LocalDate orderDate, Integer floorNumber, String status){
		
		ApprovedOrderDAO approvedOrderDAO= new ApprovedOrderDAO();
		List<PlacedOrderItemDTO> placedOrderItemDTOs = approvedOrderDAO.selectOrderItemByDateFloor(orderDate, floorNumber, status);
		
		if(placedOrderItemDTOs.isEmpty()) {
			OrderDAO orderDAO= new OrderDAO();
			placedOrderItemDTOs = orderDAO.selectOrderItemByDateFloorUser(orderDate, floorNumber, null, status);
		}
		System.out.println("placedOrderItemDTOs : "+placedOrderItemDTOs);
		return placedOrderItemDTOs;
	}

}
