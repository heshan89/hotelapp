package com.hotel.dao;

import com.hotel.dto.HotelDto;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelDAO {

    private String jdbcURL = "jdbc:mysql://hotel-app-db.cogytyzmggle.ap-southeast-1.rds.amazonaws.com/hotel";
    private String jdbcUsername = "admin";
    private String jdbcPassword = "Pass#word1";

    private static final String GET_ALL_ACTIVE_HOTELS = "SELECT id, hotel_code, name, is_active FROM hotels WHERE is_active = true;";

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

    public List<HotelDto> getAllActiveHotels() {
        List<HotelDto> hotels = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ACTIVE_HOTELS)) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                HotelDto hotel = new HotelDto();
                hotel.setId(rs.getInt("id"));
                hotel.setHotelCode(rs.getString("hotel_code"));
                hotel.setName(rs.getString("name"));
                hotel.setActive(rs.getBoolean("is_active"));
                hotels.add(hotel);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return hotels;
    }
}
