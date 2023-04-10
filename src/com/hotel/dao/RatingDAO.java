package com.hotel.dao;

import com.hotel.dto.*;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RatingDAO {

    private String jdbcURL = "jdbc:mysql://hotel-app-db.cogytyzmggle.ap-southeast-1.rds.amazonaws.com/hotel";
    private String jdbcUsername = "admin";
    private String jdbcPassword = "Pass#word1";

    private static final String GET_ALL_RATING_HOTELS = "SELECT id, name FROM rating_hotel;";
    private static final String GET_ALL_RATING_FEEDBACK_CATEGORIES = "SELECT id, name FROM rating_feedback_category;";
    private static final String GET_ALL_RATING_CATEGORIES = "SELECT id, name FROM rating_category;";
    private static final String INSERT_RATING = "INSERT INTO rating (rating_hotel_id, rating_feedback_category_id, rating_category_id, source, rating, description, created_by, updated_by)" +
            "VALUES (?,?,?,?,?,?,?,?);";
    private static final String GET_ALL_RATINGS = "SELECT `r`.`id`, `rh`.`name` AS `rating_hotel_name`, `rfc`.`name` AS `rating_feedback_category_name`, `rc`.`name` AS `rating_category_name`, `r`.`source`, `r`.`rating`, `r`.`description`, `r`.`created_date`, `r`.`created_by`, `r`.`updated_date`, `r`.`updated_by`\n" +
            "FROM `rating` AS `r`\n" +
            "JOIN `rating_hotel` AS `rh` ON `r`.`rating_hotel_id` = `rh`.`id`\n" +
            "JOIN `rating_feedback_category` AS `rfc` ON `r`.`rating_feedback_category_id` = `rfc`.`id`\n" +
            "JOIN `rating_category` AS `rc` ON `r`.`rating_category_id` = `rc`.`id`\n" +
            "ORDER BY `r`.`created_date` DESC;";

    @Resource(name = "jdbc/hotel")
    DataSource ds;

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            printSQLException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    public List<RatingHotelDto> getAllRatingHotels() {
        List<RatingHotelDto> ratingHotels = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_RATING_HOTELS)) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                RatingHotelDto ratingHotel = new RatingHotelDto();
                ratingHotel.setId(rs.getInt("id"));
                ratingHotel.setName(rs.getString("name"));
                ratingHotels.add(ratingHotel);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return ratingHotels;
    }

    public List<RatingFeedbackCategoryDto> getAllRatingFeedbackCategories() {
        List<RatingFeedbackCategoryDto> feedbackCategories = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_RATING_FEEDBACK_CATEGORIES)) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                RatingFeedbackCategoryDto ratingFeedbackCategor = new RatingFeedbackCategoryDto();
                ratingFeedbackCategor.setId(rs.getInt("id"));
                ratingFeedbackCategor.setName(rs.getString("name"));
                feedbackCategories.add(ratingFeedbackCategor);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return feedbackCategories;
    }

    public List<RatingCategoryDto> getAllCategories() {
        List<RatingCategoryDto> categories = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_RATING_CATEGORIES)) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                RatingCategoryDto ratingCategory = new RatingCategoryDto();
                ratingCategory.setId(rs.getInt("id"));
                ratingCategory.setName(rs.getString("name"));
                categories.add(ratingCategory);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return categories;
    }

    public int addRating(String userName, int hotelId, int feedbackCategoryId, int categoryId, String ratingSource, int ratingValue, String ratingDesc) {
        int id = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RATING, Statement.RETURN_GENERATED_KEYS)) {

            try {
                preparedStatement.setInt(1, hotelId);
                preparedStatement.setInt(2, feedbackCategoryId);
                preparedStatement.setInt(3, categoryId);
                preparedStatement.setString(4, ratingSource);
                preparedStatement.setInt(5, ratingValue);
                preparedStatement.setString(6, ratingDesc);
                preparedStatement.setString(7, userName);
                preparedStatement.setString(8, userName);

                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();

                while (rs.next()) {
                    id = rs.getInt(1);
                }

            } catch (SQLException e) {
                printSQLException(e);
            }

        } catch (SQLException e) {
            printSQLException(e);
        }
        return id;
    }

    public List<RatingDto> getAllRatings() {
        List<RatingDto> ratings = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_RATINGS)) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                RatingDto ratingDto = new RatingDto();
                ratingDto.setId(rs.getInt("id"));
                ratingDto.setRatingHotelName(rs.getString("rating_hotel_name"));
                ratingDto.setRatingFeedbackCategoryName(rs.getString("rating_feedback_category_name"));
                ratingDto.setRatingCategoryName(rs.getString("rating_category_name"));
                ratingDto.setRatingSource(rs.getString("source"));
                ratingDto.setRatingValue(rs.getInt("rating"));
                ratingDto.setRatingDesc(rs.getString("description"));
                ratingDto.setCreatedBy(rs.getString("created_by"));
                ratingDto.setUpdatedBy(rs.getString("updated_by"));
                ratingDto.setCreatedDate(rs.getDate("created_date"));
                ratingDto.setUpdatedDate(rs.getDate("updated_date"));
                ratings.add(ratingDto);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return ratings;
    }
}
