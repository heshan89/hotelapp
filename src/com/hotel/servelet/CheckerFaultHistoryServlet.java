package com.hotel.servelet;

import com.hotel.dao.FaultDAO;
import com.hotel.dto.FaultStatusDto;
import com.hotel.dto.FaultTypeDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
        doGet(request, response);
    }
}
