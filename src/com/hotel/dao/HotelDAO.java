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

    private static final String GET_HOTEL_INFO_BY_ID = "SELECT id, hotel_code, name, hotel_check_in, hotel_check_out, hotel_check_in_threshold, hotel_check_out_threshold, is_active FROM hotels WHERE is_active = true AND id = ?;";

    private static final String GET_HOTEL_INFO_FOR_USER = "SELECT DISTINCT h.id AS id, h.hotel_code AS hotel_code, h.name AS name, H.hotel_check_in AS hotel_check_in, h.hotel_check_out AS hotel_check_out, h.hotel_check_in_threshold AS hotel_check_in_threshold, h.hotel_check_out_threshold AS hotel_check_out_threshold, h.is_active AS is_active, uah.system_check_in AS system_check_in FROM user_attendance_hotel uah JOIN hotels h ON h.id = uah.hotel_id WHERE uah.user_id = ?;";

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

    public HotelDto getHotelInfo(int hotelId) {
        HotelDto hotelDto = new HotelDto();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_HOTEL_INFO_BY_ID)) {

            preparedStatement.setInt(1, hotelId);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                hotelDto.setId(rs.getInt("id"));
                hotelDto.setHotelCode(rs.getString("hotel_code"));
                hotelDto.setName(rs.getString("name"));
                hotelDto.setHotelCheckIn(rs.getTime("hotel_check_in"));
                hotelDto.setHotelCheckInThreshold(rs.getTime("hotel_check_in_threshold"));
                hotelDto.setHotelCheckOut(rs.getTime("hotel_check_out"));
                hotelDto.setHotelCheckOutThreshold(rs.getTime("hotel_check_out_threshold"));
                hotelDto.setActive(rs.getBoolean("is_active"));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return hotelDto;
    }

    public HotelDto getHotelInfoForUser(int userId) {
        HotelDto hotelDto = new HotelDto();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_HOTEL_INFO_FOR_USER)) {

            preparedStatement.setInt(1, userId);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                hotelDto.setId(rs.getInt("id"));
                hotelDto.setHotelCode(rs.getString("hotel_code"));
                hotelDto.setName(rs.getString("name"));
                hotelDto.setHotelCheckIn(rs.getTime("hotel_check_in"));
                hotelDto.setHotelCheckInThreshold(rs.getTime("hotel_check_in_threshold"));
                hotelDto.setHotelCheckOut(rs.getTime("hotel_check_out"));
                hotelDto.setHotelCheckOutThreshold(rs.getTime("hotel_check_out_threshold"));
                hotelDto.setActive(rs.getBoolean("is_active"));
                hotelDto.setSystemCheckIn(rs.getTimestamp("system_check_in"));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return hotelDto;
    }
}
