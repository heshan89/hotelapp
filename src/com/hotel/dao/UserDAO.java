package com.hotel.dao;

import com.hotel.dto.UsersDto;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;

public class UserDAO {

	private String jdbcURL = "jdbc:mysql://localhost:3306/hotel";
    private String jdbcUsername = "root";
    private String jdbcPassword = "harsha";

	private static final String SELECT_ACTIVE_USERS_BY_USERNAME_PASSWORD = "SELECT u.user_id, u.user_name, u.is_password_change, ur.role_code FROM hotel.users u\n" +
			"JOIN hotel.user_roles ur ON ur.id = u.user_role_id AND u.user_name=? AND u.password=? AND u.is_active=true;";

    private static final String UPDATE_USER_PASSWORD = "UPDATE users\n" +
            "SET password=?, is_password_change = true WHERE user_name=?;";

	@Resource(name = "jdbc/hotel")
    DataSource ds;


	protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch(ClassNotFoundException e) {
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

    public UsersDto passwordReset(String userName, String password) {
        UsersDto usersDto = new UsersDto();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_PASSWORD)) {

            preparedStatement.setString(1, password);
            preparedStatement.setString(2, userName);

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
	
	
    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
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
