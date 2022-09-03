package com.hotel.servelet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LogoutServelet
 */
@WebServlet("/LogoutServelet")
public class LogoutServelet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServelet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get the print writer object to write into the response
        PrintWriter out = response.getWriter();
  
        // Set the content type of response to "text/html"
        response.setContentType("text/html");
        
        request.getRequestDispatcher("index.jsp").include(request, response);
  
        // For understanding purpose, print the session object in the console before
        // invalidating the session.
        System.out.println("Session before invalidate: "+ request.getSession(false));
  
        // Invalidate the session.
        request.getSession(false).invalidate();
  
        // Print the session object in the console after invalidating the session.
        System.out.println("Session after invalidate: "+ request.getSession(false));
  
        // Print success message to the user and close the print writer object.
        out.println("Thank you! You are successfully logged out.");
        out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
