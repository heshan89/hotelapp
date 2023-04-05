package com.hotel.servelet;

import com.hotel.dao.FaultDAO;
import com.hotel.dto.FaultDto;
import com.hotel.dto.FaultStatusDto;
import com.hotel.dto.FaultTypeDto;
import com.hotel.dto.UsersDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/CheckerFaultHistoryServlet")
public class CheckerFaultHistoryServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FaultDAO faultDAO = new FaultDAO();
        List<FaultTypeDto> allFaultTypes = faultDAO.getAllFaultTypes();

        List<FaultStatusDto> allFaultStatus = faultDAO.getAllFaultStatus();

        request.setAttribute("allFaultTypes", allFaultTypes);
        request.setAttribute("allFaultStatus", allFaultStatus);

        HttpSession session = request.getSession();
        session.setAttribute("allFaultTypes", allFaultTypes);
        session.setAttribute("allFaultStatus", allFaultStatus);

        request.getRequestDispatcher("/cfaulthistory.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FaultDAO faultDAO = new FaultDAO();

        HttpSession session = request.getSession();
        UsersDto user = (UsersDto) session.getAttribute("user");

        if (request.getParameter("filter") != null) {
            LocalDate filterDate = (LocalDate) request.getAttribute("filterDate");
            int filterFloor = Integer.parseInt(request.getParameter("filterFloor"));
            String filterRoom = request.getParameter("faultRoom");
            int filterFaultType = Integer.parseInt(request.getParameter("faultType"));
            int filterFaultStatus = Integer.parseInt(request.getParameter("faultStatus"));

            List<FaultDto> faults = faultDAO.checkerFaultFilter(filterDate, filterFloor, filterRoom, filterFaultType, filterFaultStatus, user.getUserName());
            request.setAttribute("faultsList", faults);
        }
        request.getRequestDispatcher("cfaulthistory.jsp").forward(request, response);
        doGet(request, response);
    }
}
