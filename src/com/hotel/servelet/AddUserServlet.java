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
import java.util.Optional;
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

		HttpSession session = request.getSession();
		session.setAttribute("allUsers", allUsers);

		RequestDispatcher rd=request.getRequestDispatcher("/adduser.jsp");
        rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UsersDto user = (UsersDto) session.getAttribute("user");

		request.setAttribute("nullAddUserError", "false");
		request.setAttribute("userIdExistError", "false");
		request.setAttribute("userNameExistError", "false");
		request.setAttribute("userAddSuccess", "false");
		request.setAttribute("userAddError", "false");
		request.setAttribute("userDeleteSuccess", "false");
		request.setAttribute("userDeleteError", "false");
		request.setAttribute("userUpdateSuccess", "false");
		request.setAttribute("userUpdateError", "false");

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
			String selectUserName = request.getParameter("del");

			int i = userDAO.deleteUser(selectUserName);

			if (i > 0) {
				request.setAttribute("userDeleteSuccess", "true");
			} else {
				request.setAttribute("userDeleteError", "true");
			}

			request.getRequestDispatcher("adduser.jsp").include(request, response);
			doGet(request, response);
		}

		if (request.getParameter("edt") != null) {
			String selectUserName = request.getParameter("edt");

			List<UsersDto> availableUsers = (List<UsersDto>) session.getAttribute("allUsers");
			availableUsers.stream()
					.filter(u -> u.getUserName().equals(selectUserName)).findFirst()
					.ifPresent(pu -> pu.setEdit(true));

			session.setAttribute("allUsers", availableUsers);

			request.getRequestDispatcher("adduser.jsp").forward(request, response);
		}

		if (request.getParameter("upd") != null) {
			String updateId = request.getParameter("upd");

			String userId = request.getParameter("userId"+updateId);
			String userName = request.getParameter("userName"+updateId);
			String password = request.getParameter("password"+updateId);

			if ((null != userId && !"".equals(userId)) && (null != userName && !"".equals(userName)) && (null != password && !"".equals(password))) {

				Map<String, String> oldUserMap = userDAO.selectUserIdUserNameForId(Integer.parseInt(updateId));

				if (!oldUserMap.get("userId").equals(userId)) {
					int userForUserId = userDAO.getUserForUserId(userId);
					if (userForUserId > 0) {
						request.setAttribute("userIdExistError", "true");
					}
				}

				if (!oldUserMap.get("userName").equals(userName)) {
					int userForUserName = userDAO.getUserForUserName(userName);
					if (userForUserName > 0) {
						request.setAttribute("userNameExistError", "true");
					}
				}

				String oldPw = userDAO.selectPwForId(Integer.parseInt(updateId));
				int i;
				if (oldPw.equals(password)) {
					i = userDAO.updateUser(userId, userName, user.getUserName(), Integer.parseInt(updateId));
				} else {
					i = userDAO.updateUserWithPw(userId, userName, password, user.getUserName(), Integer.parseInt(updateId));
				}
				if (i > 0) {
					request.setAttribute("userUpdateSuccess", "true");
				} else {
					request.setAttribute("userUpdateError", "true");
				}


			} else {
				request.setAttribute("nullAddUserError", "true");
			}

			List<UsersDto> allUsers = userDAO.getAllUsers();

			request.setAttribute("allUsers", allUsers);

			session.setAttribute("allUsers", allUsers);

			request.getRequestDispatcher("adduser.jsp").forward(request, response);
		}
	}

}
