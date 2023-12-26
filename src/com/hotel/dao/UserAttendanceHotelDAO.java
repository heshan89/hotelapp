package com.hotel.dao;

import com.hotel.input.UserCheckInInput;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserAttendanceHotelDAO {

    private String jdbcURL = "jdbc:mysql://hotel-app-db.cogytyzmggle.ap-southeast-1.rds.amazonaws.com/hotel";
    private String jdbcUsername = "admin";
    private String jdbcPassword = "Pass#word1";

    private static final String INSERT_CHECK_IN = "INSERT INTO user_attendance_hotel (user_id, hotel_id, check_in, check_out, created_by, updated_by) " +
            "VALUES (?, ?, ?, ?, ?, ?);";

    private static final String GET_ALL_INCOMPLETE_WORK_TODAY = "SELECT id FROM user_attendance_hotel WHERE user_id = ? AND DATE(created_date) = CURDATE() AND is_completed = false;";

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
                preparedStatement.setTimestamp(3, userCheckInInput.getCheckIn());
                preparedStatement.setTimestamp(4, userCheckInInput.getCheckOut());
                preparedStatement.setString(5, userCheckInInput.getCreatedBy());
                preparedStatement.setString(6, userCheckInInput.getCreatedBy());

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

    public boolean isInCompleteWorkToday(int userId) {
        List<Integer> ids = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_INCOMPLETE_WORK_TODAY)) {

            preparedStatement.setInt(1, userId);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return ids.size() > 0;
    }
}
