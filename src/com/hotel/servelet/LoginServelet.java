package com.hotel.servelet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServelet
 */
@WebServlet("/LoginServlet")
public class LoginServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String userID = "admin";
	private final String password = "password";
       
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     // get request parameters for userID and password
        String user = request.getParameter("uname");
        String pwd = request.getParameter("password");
        
        System.out.println("username : "+user);
        System.out.println("password : "+pwd);
     		
     		if(userID.equals(user) && password.equals(pwd)){
     			HttpSession session = request.getSession();
     			session.setAttribute("user", user);
     			//setting session to expiry in 30 mins
     			session.setMaxInactiveInterval(30*60);
     			Cookie userName = new Cookie("user", user);
     			response.addCookie(userName);
     			//Get the encoded URL string
     			String encodedURL = response.encodeRedirectURL("checkerhome.jsp");
     			response.sendRedirect(encodedURL);
     		}else{
     			RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.html");
     			PrintWriter out= response.getWriter();
     			out.println("<font color=red>Either user name or password is wrong.</font>");
     			rd.include(request, response);
     		}
	}

}
