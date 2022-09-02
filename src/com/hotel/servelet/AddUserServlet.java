package com.hotel.servelet;

import com.hotel.dao.OrderDAO;
import com.hotel.dao.UserDAO;
import com.hotel.dto.PlacedOrderItemDTO;
import com.hotel.dto.UsersDto;
import com.hotel.input.UserInput;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Servlet implementation class AddUserServlet
 */
@WebServlet("/AddUserServlet")
public class AddUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDAO userDAO = new UserDAO();
		List<UsersDto> allUsers = userDAO.getAllUsers();

		request.setAttribute("allUsers", allUsers);

		RequestDispatcher rd=request.getRequestDispatcher("/adduser.jsp");
        rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String userType = request.getParameter("userType");
		String password = request.getParameter("password");

		UserDAO userDAO = new UserDAO();

		int userForUserId = userDAO.getUserForUserId(userId);
		if (userForUserId > 0) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/adduser.jsp");
			PrintWriter out = response.getWriter();
			out.println("<font color=red>User Id already exist.</font>");
			rd.include(request, response);
		}

		int userForUserName = userDAO.getUserForUserName(userName);
		if (userForUserName > 0) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/adduser.jsp");
			PrintWriter out = response.getWriter();
			out.println("<font color=red>User Name already exist.</font>");
			rd.include(request, response);
		}

		HttpSession session = request.getSession();
		UsersDto user = (UsersDto) session.getAttribute("user");

		UserInput userInput = new UserInput();
		userInput.setUserId(userId);
		userInput.setUserName(userName);
		userInput.setRoleCode(userType);
		userInput.setPassword(password);
		userInput.setCreatedBy(user.getUserName());

		int i = userDAO.insertUser(userInput);
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/adduser.jsp");
		PrintWriter out = response.getWriter();
		if (i > 0) {
			out.println("<font color=green>User added successfully.</font>");
		} else {
			out.println("<font color=red>Could not add User.</font>");
		}
		rd.include(request, response);
		doGet(request, response);
	}

}
