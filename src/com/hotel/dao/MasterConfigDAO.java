package com.hotel.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.annotation.Resource;
import javax.sql.DataSource;

public class MasterConfigDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/hotel";
    private String jdbcUsername = "root";
    private String jdbcPassword = "harsha";
	
	private static final String SELECT_MASTER_CONFIG_DATA = "SELECT VALUE FROM master_config WHERE CONFIG_NAME=? AND STATUS='ACTIVE';";
	
	
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
	
	public String selectMasterConfigValue(String masterConfigData) {
		String configValue = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MASTER_CONFIG_DATA);) {
            preparedStatement.setString(1, masterConfigData);
            
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
            	configValue = rs.getString("VALUE");
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return configValue;
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
