package com.hotel.dao;

import com.hotel.input.UserCheckInInput;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;

public class UserAttendanceHotelDAO {

    private String jdbcURL = "jdbc:mysql://hotel-app-db.cogytyzmggle.ap-southeast-1.rds.amazonaws.com/hotel";
    private String jdbcUsername = "admin";
    private String jdbcPassword = "Pass#word1";

    private static final String INSERT_CHECK_IN = "INSERT INTO user_attendance_hotel (user_id, hotel_id, created_by, updated_by) " +
            "VALUES (?, ?, ?, ?);";

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

    public int userCheckIn(UserCheckInInput userCheckInInput) {
        int id = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CHECK_IN, Statement.RETURN_GENERATED_KEYS)) {

            try {
                preparedStatement.setInt(1, userCheckInInput.getUserId());
                preparedStatement.setInt(2, userCheckInInput.getHotelId());
                preparedStatement.setString(3, userCheckInInput.getCreatedBy());
                preparedStatement.setString(4, userCheckInInput.getCreatedBy());

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
}
