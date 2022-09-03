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
		request.setAttribute("nullAddUserError", "false");
		request.setAttribute("userIdExistError", "false");
		request.setAttribute("userNameExistError", "false");
		request.setAttribute("userAddSuccess", "false");
		request.setAttribute("userAddError", "false");

		UserDAO userDAO = new UserDAO();
		if (request.getParameter("add") != null) {
			String userId = request.getParameter("userId");
			String userName = request.getParameter("userName");
			String userType = request.getParameter("userType");
			String password = request.getParameter("password");

			if ((null != userId && !"".equals(userId)) &&
					(null != userName && !"".equals(userName)) &&
					(null != userType && !"".equals(userType)) &&
					(null != password && !"".equals(password))) {

				int userForUserId = userDAO.getUserForUserId(userId);
				int userForUserName = userDAO.getUserForUserName(userName);
				if (userForUserId > 0) {
					request.setAttribute("userIdExistError", "true");
				} else if (userForUserName > 0) {
					request.setAttribute("userNameExistError", "true");
				} else {
					HttpSession session = request.getSession();
					UsersDto user = (UsersDto) session.getAttribute("user");

					UserInput userInput = new UserInput();
					userInput.setUserId(userId);
					userInput.setUserName(userName);
					userInput.setRoleCode(userType);
					userInput.setPassword(password);
					userInput.setCreatedBy(user.getUserName());

					int i = userDAO.insertUser(userInput);
					if (i > 0) {
						request.setAttribute("userAddSuccess", "true");
					} else {
						request.setAttribute("userAddError", "true");
					}
				}
			} else {
				request.setAttribute("nullAddUserError", "true");
			}
			request.getRequestDispatcher("adduser.jsp").include(request, response);
			doGet(request, response);
		}

		if (request.getParameter("del") != null) {
			String selectUserName = request.getParameter("selectUserName");

			int i = userDAO.deleteUser(selectUserName);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/adduser.jsp");
			PrintWriter out = response.getWriter();
			if (i > 0) {
				out.println("<font color=green>User delete successfully.</font>");
			} else {
				out.println("<font color=red>Could not delete User.</font>");
			}
			rd.include(request, response);
			doGet(request, response);
		}

	}

}
