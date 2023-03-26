package com.hotel.servelet;

import com.hotel.dao.FaultDAO;
import com.hotel.dto.FaultDto;
import com.hotel.dto.FaultTypeDto;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@WebServlet("/AdminFaultServlet")
public class AdminFaultServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public AdminFaultServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FaultDAO faultDAO = new FaultDAO();
        List<FaultTypeDto> allFaultTypes = faultDAO.getAllFaultTypes();

        request.setAttribute("allFaultTypes", allFaultTypes);

        HttpSession session = request.getSession();
        session.setAttribute("allFaultTypes", allFaultTypes);

        RequestDispatcher rd=request.getRequestDispatcher("/faultlist.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FaultDAO faultDAO = new FaultDAO();
        HttpSession session = request.getSession();

        if (request.getParameter("filter") != null) {
            LocalDate faultDate = !Objects.equals(request.getParameter("faultDate"), "") ? LocalDate.parse(request.getParameter("faultDate"), formatter) : null;
            int filterFloor = Integer.parseInt(request.getParameter("filterFloor"));
            String filterRoom = request.getParameter("faultRoom");
            int filterFaultType = Integer.parseInt(request.getParameter("faultType"));

            List<FaultDto> faults = faultDAO.getFaultBy(faultDate, filterFloor, filterRoom, filterFaultType);
            session.setAttribute("faults", faults);
            request.setAttribute("faults", faults);

            RequestDispatcher rd=request.getRequestDispatcher("/faultlist.jsp");
            rd.forward(request, response);
        }

        doGet(request, response);
    }
}
