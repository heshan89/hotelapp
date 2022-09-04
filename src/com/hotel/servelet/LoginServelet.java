package com.hotel.servelet;

import com.hotel.dao.UserDAO;
import com.hotel.dto.UsersDto;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class LoginServelet
 */
@WebServlet("/LoginServlet")
public class LoginServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String ADMIN = "ADMIN";
	private static final String CHECKER = "CHECKER";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServelet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get request parameters for userID and password
		request.setAttribute("invalidLogin", false);
		String user = request.getParameter("uname");
		String pwd = request.getParameter("password");

		UserDAO userDAO = new UserDAO();
		UsersDto usersDto = userDAO.userLogin(user, pwd);

		if (null != usersDto && user.equals(usersDto.getUserName())) {
			HttpSession session = request.getSession();
			session.setAttribute("user", usersDto);
			//setting session to expiry in 30 mins
			session.setMaxInactiveInterval(30 * 60);
			Cookie userName = new Cookie("user", usersDto.getUserName());
			response.addCookie(userName);
			//Get the encoded URL string
			if (usersDto.isPasswordChange()) {
				// call password change page
			}
			if (CHECKER.equals(usersDto.getRoleCode())) {
				String encodedURL = response.encodeRedirectURL("checkerhome.jsp");
				response.sendRedirect(encodedURL);
			}
			if (ADMIN.equals(usersDto.getRoleCode())) {
				String encodedURL = response.encodeRedirectURL("adminhome.jsp");
				response.sendRedirect(encodedURL);
			}
		} else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
			request.setAttribute("invalidLogin", true);
			rd.include(request, response);
		}
	}

}
