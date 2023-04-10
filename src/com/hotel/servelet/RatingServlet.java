package com.hotel.servelet;

import com.hotel.dao.RatingDAO;
import com.hotel.dto.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/RatingServlet")
public class RatingServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public RatingServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        RatingDAO ratingDAO = new RatingDAO();
        List<RatingHotelDto> ratingHotels = ratingDAO.getAllRatingHotels();

        request.setAttribute("ratingHotels", ratingHotels);
        session.setAttribute("ratingHotels", ratingHotels);

        List<RatingFeedbackCategoryDto> feedbackCategories = ratingDAO.getAllRatingFeedbackCategories();

        request.setAttribute("feedbackCategories", feedbackCategories);
        session.setAttribute("feedbackCategories", feedbackCategories);

        List<RatingCategoryDto> categories = ratingDAO.getAllCategories();

        request.setAttribute("categories", categories);
        session.setAttribute("categories", categories);

        List<RatingDto> ratings = ratingDAO.getAllRatings();

        request.setAttribute("ratings", ratings);
        session.setAttribute("ratings", ratings);

        RequestDispatcher rd=request.getRequestDispatcher("/servicepara.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UsersDto user = (UsersDto) session.getAttribute("user");

        request.setAttribute("rateAddSuccess", "false");
        request.setAttribute("rateAddError", "false");

        RatingDAO ratingDAO = new RatingDAO();

        if (request.getParameter("add") != null) {
            int hotelId = Integer.parseInt(request.getParameter("ratingHotel"));
            int feedbackCategoryId = Integer.parseInt(request.getParameter("feedbackCategory"));
            int categoryId = Integer.parseInt(request.getParameter("ratingCategory"));
            String ratingSource = request.getParameter("ratingSource");
            int ratingValue = Integer.parseInt(request.getParameter("ratingValue"));
            String ratingDesc = request.getParameter("ratingDesc");

            int i = ratingDAO.addRating(user.getUserName(), hotelId, feedbackCategoryId, categoryId, ratingSource, ratingValue, ratingDesc);
            if (i > 0) {
                request.setAttribute("rateAddSuccess", "true");
            } else {
                request.setAttribute("rateAddError", "true");
            }
        }

        request.getRequestDispatcher("servicepara.jsp").include(request, response);
        doGet(request, response);
    }
}
