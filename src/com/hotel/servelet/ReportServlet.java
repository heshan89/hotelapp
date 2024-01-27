package com.hotel.servelet;

import com.hotel.dao.HotelDAO;
import com.hotel.dto.HotelDto;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/ReportServlet")
public class ReportServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public ReportServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // load hotels
        HotelDAO hotelDAO = new HotelDAO();
        List<HotelDto> allActiveHotels = hotelDAO.getAllActiveHotels();

        request.setAttribute("allActiveHotels", allActiveHotels);

        session.setAttribute("allActiveHotels", allActiveHotels);

        RequestDispatcher rd=request.getRequestDispatcher("/reports.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("reports.jsp").include(request, response);
        doGet(request, response);
    }
}
