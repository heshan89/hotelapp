package com.hotel.servelet;

import com.hotel.dao.FaultDAO;
import com.hotel.dto.FaultDto;
import com.hotel.dto.FaultTypeDto;
import com.hotel.dto.UsersDto;
import com.hotel.input.FaultInput;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)

@WebServlet("/AddFaultServlet")
public class AddFaultServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public AddFaultServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FaultDAO faultDAO = new FaultDAO();
        List<FaultTypeDto> allFaultTypes = faultDAO.getAllFaultTypes();

        request.setAttribute("allFaultTypes", allFaultTypes);

        HttpSession session = request.getSession();
        session.setAttribute("allFaultTypes", allFaultTypes);

        UsersDto user = (UsersDto) session.getAttribute("user");

        List<FaultDto> userFaults = faultDAO.getUserLast10Faults(user.getUserName());
        request.setAttribute("userFaults", userFaults);
        session.setAttribute("userFaults", userFaults);

        RequestDispatcher rd=request.getRequestDispatcher("/cfaultreport.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UsersDto user = (UsersDto) session.getAttribute("user");

        request.setAttribute("faultAddSuccess", "false");
        request.setAttribute("faultAddError", "false");

        FaultDAO faultDAO = new FaultDAO();

        if (request.getParameter("add") != null) {
            int filterFloor = Integer.parseInt(request.getParameter("filterFloor"));
            String filterRoom = request.getParameter("faultRoom");
            int filterFaultType = Integer.parseInt(request.getParameter("faultType"));
            String filterFaultDescription = request.getParameter("faultDescription");

            Path filePath = null;
            Part filePart = request.getPart("faultAttachment");
            if (filePart != null) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                if (fileName != null && !fileName.isEmpty()) {
                    String extension = fileName.substring(fileName.lastIndexOf('.'));
                    String nameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String newFileName = nameWithoutExtension + "_" + timeStamp + extension;

                    InputStream fileContent = filePart.getInputStream();

                    String uploadPath = "c:/hotel-app/fault-uploads";
                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdir();
                    }

                    filePath = Paths.get(uploadPath + File.separator + newFileName);
                    Files.copy(fileContent, filePath);
                }
            }

            FaultInput faultInput = new FaultInput();
            faultInput.setFloor(filterFloor);
            faultInput.setRoom(filterRoom);
            faultInput.setFaultTypeId(filterFaultType);
            faultInput.setDescription(filterFaultDescription);
            faultInput.setAttachment(filePath != null ? filePath.toString() : "");
            faultInput.setCreatedBy(user.getUserName());

            int i = faultDAO.insertFault(faultInput);

            if (i > 0) {
                request.setAttribute("faultAddSuccess", "true");
            } else {
                request.setAttribute("faultAddError", "true");
            }
        }

        request.getRequestDispatcher("cfaultreport.jsp").include(request, response);
        doGet(request, response);
    }
}
