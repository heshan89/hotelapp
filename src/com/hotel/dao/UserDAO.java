package com.hotel.dao;

import com.hotel.dto.UsersDto;
import com.hotel.input.UserInput;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class UserDAO {

	private String jdbcURL = "jdbc:mysql://hotel-app-db.cogytyzmggle.ap-southeast-1.rds.amazonaws.com/hotel";
    private String jdbcUsername = "admin";
    private String jdbcPassword = "Pass#word1";

    private static final String SELECT_ACTIVE_USERS_BY_USERNAME_PASSWORD = "SELECT u.user_id, u.user_name, u.is_password_change, ur.role_code FROM hotel.users u\n" +
            "JOIN hotel.user_roles ur ON ur.id = u.user_role_id AND u.user_name=? AND u.password=? AND u.is_active=true;";

    private static final String UPDATE_USER_PASSWORD = "UPDATE users\n" +
            "SET password=?, is_password_change = false, updated_by = ?, updated_date = ? WHERE user_name=?;";

    private static final String GET_ALL_USERS = "SELECT u.id, u.user_id, ur.role_code, u.user_name, u.is_password_change, u.is_ask_for_pw_reset, u.password FROM hotel.users u " +
            "JOIN hotel.user_roles ur ON ur.id = u.user_role_id;";

    private static final String INSERT_USER = "INSERT INTO users (user_id, user_name, password, is_password_change, created_by, updated_by, is_active, user_role_id, is_ask_for_pw_reset) VALUES\n" +
            "(?, ?, ?, true, ?, ?, true, ?, false);";

    private static final String SELECT_USER_ROLE_ID_FOR_CODE = "SELECT id FROM user_roles WHERE role_code = ?;";

    private static final String SELECT_USER_FOR_USER_ID = "SELECT id FROM users WHERE user_id = ?;";

    private static final String SELECT_USER_FOR_USER_NAME = "SELECT id FROM users WHERE user_name = ?;";

    private static final String DELETE_USER_FOR_USER_NAME = "DELETE FROM users\n" +
            "WHERE user_name = ?;";

    private static final String UPDATE_USER = "UPDATE users\n" +
            "SET user_id=?, user_name = ?, updated_by = ?, updated_date = ? WHERE id = ?;";

    private static final String UPDATE_USER_WITH_PW = "UPDATE users\n" +
            "SET user_id=?, user_name = ?, password=?, is_password_change = true, is_ask_for_pw_reset = false, updated_by = ?, updated_date = ? WHERE id = ?;";

    private static final String SELECT_PW_FOR_ID = "SELECT password FROM users WHERE id = ?;";

    private static final String SELECT_USER_ID_USER_NAME_FOR_ID = "SELECT user_id, user_name FROM users WHERE id = ?;";

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

    public UsersDto userLogin(String userName, String password) {
        UsersDto usersDto = new UsersDto();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ACTIVE_USERS_BY_USERNAME_PASSWORD)) {

            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                usersDto.setUserId(rs.getString("user_id"));
                usersDto.setUserName(rs.getString("user_name"));
                usersDto.setPasswordChange(rs.getBoolean("is_password_change"));
                usersDto.setRoleCode(rs.getString("role_code"));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return usersDto;
    }

    public int passwordReset(String userName, String password) {
        int id = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_PASSWORD)) {

            preparedStatement.setString(1, password);
            preparedStatement.setString(2, userName);
            preparedStatement.setTimestamp(3, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            preparedStatement.setString(4, userName);

            id = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            printSQLException(e);
        }
        return id;
    }

    public List<UsersDto> getAllUsers() {
        List<UsersDto> users = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS)) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                UsersDto usersDto = new UsersDto();
                usersDto.setId(rs.getInt("id"));
                usersDto.setUserId(rs.getString("user_id"));
                usersDto.setUserName(rs.getString("user_name"));
                usersDto.setPasswordChange(rs.getBoolean("is_password_change"));
                usersDto.setRoleCode(rs.getString("role_code"));
                usersDto.setAskForPwReset(rs.getBoolean("is_ask_for_pw_reset"));
                usersDto.setPassword(rs.getString("password"));
                users.add(usersDto);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }

    public int insertUser(UserInput user) {
        int id = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {

            try {
                preparedStatement.setString(1, user.getUserId());
                preparedStatement.setString(2, user.getUserName());
                preparedStatement.setString(3, user.getPassword());
                preparedStatement.setString(4, user.getCreatedBy());
                preparedStatement.setString(5, user.getCreatedBy());
                preparedStatement.setInt(6, getUserRoleIdForCode(user.getRoleCode()));

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

    public int getUserRoleIdForCode(String roleCode) {
        int id = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_ROLE_ID_FOR_CODE)) {

            preparedStatement.setString(1, roleCode.toUpperCase(Locale.ROOT));

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return id;
    }

    public int getUserForUserId(String userId) {
        int id = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_FOR_USER_ID)) {

            preparedStatement.setString(1, userId);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return id;
    }

    public int getUserForUserName(String userName) {
        int id = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_FOR_USER_NAME)) {

            preparedStatement.setString(1, userName);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return id;
    }

    public int deleteUser(String userName) {
        int id = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_FOR_USER_NAME)) {

            preparedStatement.setString(1, userName);

            id = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            printSQLException(e);
        }
        return id;
    }

    public int updateUser(String userId, String userName, String updatedBy, int id) {
        int i = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {

            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, userName);
            preparedStatement.setString(3, updatedBy);
            preparedStatement.setTimestamp(4, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            preparedStatement.setInt(5, id);

            i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            printSQLException(e);
        }
        return i;
    }

    public int updateUserWithPw(String userId, String userName, String password, String updatedBy, int id) {
        int i = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_WITH_PW)) {

            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, userName);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, updatedBy);
            preparedStatement.setTimestamp(5, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            preparedStatement.setInt(6, id);

            i = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            printSQLException(e);
        }
        return i;
    }

    public String selectPwForId(int id) {
        String password = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PW_FOR_ID)) {

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                password = rs.getString("password");
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return password;
    }

    public Map<String, String> selectUserIdUserNameForId(int id) {
        Map<String, String> user = new HashMap<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_ID_USER_NAME_FOR_ID)) {

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                user.put("userId", rs.getString("user_id"));
                user.put("userName", rs.getString("user_name"));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
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

}
