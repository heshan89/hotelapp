package com.hotel.dao;

import com.hotel.dto.UserAttendanceHotelDto;
import com.hotel.dto.TodayAttendanceDto;
import com.hotel.input.UserCheckInInput;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class UserAttendanceHotelDAO {

    private String jdbcURL = "jdbc:mysql://hotel-app-db.cogytyzmggle.ap-southeast-1.rds.amazonaws.com/hotel";
    private String jdbcUsername = "admin";
    private String jdbcPassword = "Pass#word1";

    private static final String INSERT_CHECK_IN = "INSERT INTO user_attendance_hotel (user_id, hotel_id, user_check_in, user_check_out, system_check_in, system_check_out, created_by, updated_by) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

    private static final String GET_ALL_INCOMPLETE_WORK_TODAY = "SELECT id FROM user_attendance_hotel WHERE user_id = ? AND DATE(created_date) = CURDATE() AND is_completed = false;";

    private static final String UPDATE_CHECK_OUT = "UPDATE user_attendance_hotel SET user_check_out = ?, system_check_out = ?, is_completed = true, updated_date = ?, updated_by = ? WHERE user_id = ? AND DATE(created_date) = CURDATE() AND is_completed = false;";

    private static final String GET_ALL_TODAY_ATTENDANCE = "SELECT h.name AS hotel_name, uah.user_check_in AS user_check_in, uah.user_check_out AS user_check_out, uah.system_check_in AS system_check_in, uah.system_check_out AS system_check_out, uah.is_completed AS is_completed FROM hotel.user_attendance_hotel uah INNER JOIN hotel.hotels h ON uah.hotel_id = h.id WHERE DATE(uah.created_date) = CURDATE() AND uah.user_id = ? ORDER BY uah.created_date;";

    private static final String GET_ALL_DATE_WISE_ATTENDANCE = "SELECT u.id, u.user_id, u.user_name, h.name, uah.user_check_in, uah.user_check_out, uah.system_check_in, uah.system_check_out \n" +
            "FROM ((hotel.user_attendance_hotel uah\n" +
            "RIGHT JOIN hotel.hotels h ON uah.hotel_id = h.id)\n" +
            "INNER JOIN hotel.users u ON u.id = uah.user_id)\n" +
            "WHERE u.user_role_id = 3 \n" +
            "AND u.is_active = true \n" +
            "AND uah.is_completed = true \n" +
            "AND uah.system_check_in BETWEEN ? AND ?;";

    private static final String GET_ALL_DATE_HOTEL_WISE_ATTENDANCE = "SELECT u.id, u.user_id, u.user_name, h.name, uah.user_check_in, uah.user_check_out, uah.system_check_in, uah.system_check_out \n" +
            "FROM ((hotel.user_attendance_hotel uah\n" +
            "RIGHT JOIN hotel.hotels h ON uah.hotel_id = h.id)\n" +
            "INNER JOIN hotel.users u ON u.id = uah.user_id)\n" +
            "WHERE u.user_role_id = 3 \n" +
            "AND u.is_active = true \n" +
            "AND uah.is_completed = true \n" +
            "AND uah.system_check_in BETWEEN ? AND ?  \n" +
            "AND uah.hotel_id = ?;";

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
                preparedStatement.setTimestamp(3, userCheckInInput.getUserCheckIn());
                preparedStatement.setTimestamp(4, userCheckInInput.getUserCheckOut());
                preparedStatement.setTimestamp(5, userCheckInInput.getSystemCheckIn());
                preparedStatement.setTimestamp(6, userCheckInInput.getSystemCheckOut());
                preparedStatement.setString(7, userCheckInInput.getCreatedBy());
                preparedStatement.setString(8, userCheckInInput.getCreatedBy());

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
        return !ids.isEmpty();
    }

    public int userCheckOut(UserCheckInInput userCheckInInput) {
        int i = 0;
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CHECK_OUT)) {

            try {
                preparedStatement.setTimestamp(1, userCheckInInput.getUserCheckOut());
                preparedStatement.setTimestamp(2, userCheckInInput.getSystemCheckOut());
                preparedStatement.setTimestamp(3, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
                preparedStatement.setString(4, userCheckInInput.getCreatedBy());
                preparedStatement.setInt(5, userCheckInInput.getUserId());
                preparedStatement.addBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public List<TodayAttendanceDto> getTodayAttendance(int userId) {
        List<TodayAttendanceDto> todayAttendanceList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_TODAY_ATTENDANCE)) {

            preparedStatement.setInt(1, userId);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                TodayAttendanceDto todayAttendance = new TodayAttendanceDto();
                todayAttendance.setHotelName(rs.getString("hotel_name"));
                todayAttendance.setUserCheckIn(rs.getTimestamp("user_check_in").toLocalDateTime());
                todayAttendance.setSystemCheckIn(rs.getTimestamp("system_check_in").toLocalDateTime());
                if (rs.getBoolean("is_completed")) {
                    todayAttendance.setUserCheckOut(rs.getTimestamp("user_check_out").toLocalDateTime());
                    todayAttendance.setSystemCheckOut(rs.getTimestamp("system_check_out").toLocalDateTime());
                }
                todayAttendanceList.add(todayAttendance);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return todayAttendanceList;
    }

    public List<UserAttendanceHotelDto> employeeWiseReport(String from, String to) {
        List<UserAttendanceHotelDto> employeeWiseReportList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_DATE_WISE_ATTENDANCE)) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date convertedFromDate = sdf.parse(from);
            java.util.Date convertedToDate = sdf.parse(to);

            preparedStatement.setDate(1, new java.sql.Date(convertedFromDate.getTime()));
            preparedStatement.setDate(2, new java.sql.Date(convertedToDate.getTime()));

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                UserAttendanceHotelDto employeeWiseReport = new UserAttendanceHotelDto();
                employeeWiseReport.setUserId(rs.getString("user_id"));
                employeeWiseReport.setUserName(rs.getString("user_name"));
                employeeWiseReport.setHotelName(rs.getString("name"));
                employeeWiseReport.setUserCheckIn(rs.getTimestamp("user_check_in").toLocalDateTime());
                employeeWiseReport.setSystemCheckIn(rs.getTimestamp("system_check_in").toLocalDateTime());
                employeeWiseReport.setUserCheckOut(rs.getTimestamp("user_check_out").toLocalDateTime());
                employeeWiseReport.setSystemCheckOut(rs.getTimestamp("system_check_out").toLocalDateTime());
                employeeWiseReportList.add(employeeWiseReport);
            }
        } catch (SQLException e) {
            printSQLException(e);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return employeeWiseReportList;
    }

    public List<UserAttendanceHotelDto> hotelWiseReport(String from, String to, String hotelId) {
        List<UserAttendanceHotelDto> hotelWiseReportList = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_DATE_HOTEL_WISE_ATTENDANCE)) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date convertedFromDate = sdf.parse(from);
            java.util.Date convertedToDate = sdf.parse(to);

            preparedStatement.setDate(1, new java.sql.Date(convertedFromDate.getTime()));
            preparedStatement.setDate(2, new java.sql.Date(convertedToDate.getTime()));
            preparedStatement.setInt(3, Integer.parseInt(hotelId));

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                UserAttendanceHotelDto employeeWiseReport = new UserAttendanceHotelDto();
                employeeWiseReport.setUserId(rs.getString("user_id"));
                employeeWiseReport.setUserName(rs.getString("user_name"));
                employeeWiseReport.setHotelName(rs.getString("name"));
                employeeWiseReport.setUserCheckIn(rs.getTimestamp("user_check_in").toLocalDateTime());
                employeeWiseReport.setSystemCheckIn(rs.getTimestamp("system_check_in").toLocalDateTime());
                employeeWiseReport.setUserCheckOut(rs.getTimestamp("user_check_out").toLocalDateTime());
                employeeWiseReport.setSystemCheckOut(rs.getTimestamp("system_check_out").toLocalDateTime());
                hotelWiseReportList.add(employeeWiseReport);
            }
        } catch (SQLException e) {
            printSQLException(e);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return hotelWiseReportList;
    }
}
