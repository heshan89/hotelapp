package com.hotel.servelet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.hotel.dto.PlacedOrderItemDTO;

/**
 * Servlet implementation class AdminAddListServlet
 */
@WebServlet("/AdminAddListServlet")
public class AdminAddListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private static final String STATUS= "ACTIVE";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminAddListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		@SuppressWarnings("unchecked")
		Map<Integer, List<PlacedOrderItemDTO>> groupedFloorItem = (Map<Integer, List<PlacedOrderItemDTO>>)session.getAttribute("hystoryData");
		if(Objects.isNull(groupedFloorItem)) {
			LocalDate filterOrderDate = LocalDate.parse(Optional.ofNullable(request.getParameter("addListFilterDate")).orElse(LocalDate.now().toString()), formatter);
			session.setAttribute("filterOrderDate", filterOrderDate);
			Integer filterFloor = null;
			groupedFloorItem = getApprovedPlacedOrderList(filterOrderDate, filterFloor, STATUS)
					.stream()
					.collect(Collectors.groupingBy(PlacedOrderItemDTO::getFloor));
		}
		
		String itemName = request.getParameter("addListFilterItem");
		if(itemName.equalsIgnoreCase("select")) {
			request.setAttribute("invalidItemName", Boolean.TRUE);
	        response.sendRedirect("requestlist.jsp");
	        return;
//			RequestDispatcher rd=request.getRequestDispatcher("/requestlist.jsp");  
//	        rd.forward(request, response);
		}
		Integer quantity = Integer.parseInt(request.getParameter("addListFilterQuantity"));
		if(quantity==0) {
			request.setAttribute("invalidQuantity", Boolean.TRUE);
			RequestDispatcher rd=request.getRequestDispatcher("/requestlist.jsp");  
	        rd.forward(request, response);
		}
		
		LocalDate orderDate = LocalDate.parse(request.getParameter("addListFilterDate"), formatter);
		if(request.getParameter("addListFilterFloor").equalsIgnoreCase("All")) {
			request.setAttribute("invalidItemName", Boolean.TRUE);
			RequestDispatcher rd=request.getRequestDispatcher("/requestlist.jsp");  
	        rd.forward(request, response);
		}
		Integer floor = Integer.parseInt(request.getParameter("addListFilterFloor"));
		
		List<PlacedOrderItemDTO> floorItemList = Optional.ofNullable(groupedFloorItem.get(floor)).orElse(new ArrayList<PlacedOrderItemDTO>());
		
		PlacedOrderItemDTO placedOrderItemDTO = floorItemList
				.stream()
				.filter(item -> item.getItemName().equalsIgnoreCase(itemName))
				.findFirst().orElse(null);
		
		if(Objects.isNull(placedOrderItemDTO)) {
			placedOrderItemDTO = new PlacedOrderItemDTO();
			placedOrderItemDTO.setId(ItemEnum.getEnum(itemName).getValue());
			placedOrderItemDTO.setAmount(quantity);
			placedOrderItemDTO.setFloor(floor);
			placedOrderItemDTO.setItemName(itemName);
			placedOrderItemDTO.setOrderDate(orderDate);
			if(!floorItemList.isEmpty()) {
				placedOrderItemDTO.setOrderId(floorItemList.get(0).getOrderId());
			}else {
				placedOrderItemDTO.setOrderId(0);
			}
			placedOrderItemDTO.setStatus("New");
			floorItemList.add(placedOrderItemDTO);
		}else {
			placedOrderItemDTO.setAmount(placedOrderItemDTO.getAmount()+quantity);
		}
		
		groupedFloorItem.put(floor, floorItemList);
		session.setAttribute("hystoryData", groupedFloorItem);
		session.setAttribute("newfilteration", true);
		RequestDispatcher rd=request.getRequestDispatcher("/requestlist.jsp");  
        rd.forward(request, response); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
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
