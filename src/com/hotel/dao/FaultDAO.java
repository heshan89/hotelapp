package com.hotel.dao;

import com.hotel.dto.FaultTypeDto;
import com.hotel.input.FaultInput;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FaultDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/hotel";
    private String jdbcUsername = "root";
    private String jdbcPassword = "harsha";

    private static final String INSERT_FAULT = "INSERT INTO fault (floor, room, fault_type_id, fault_status_id, description, attachment, created_by, updated_by) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

    private static final String GET_ALL_FAULT_TYPES = "SELECT id, name FROM fault_type;";

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

    public int insertFault(FaultInput faultInput) {
        int id = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FAULT, Statement.RETURN_GENERATED_KEYS)) {

            try {
                preparedStatement.setInt(1, faultInput.getFloor());
                preparedStatement.setString(2, faultInput.getRoom());
                preparedStatement.setInt(3, faultInput.getFaultTypeId());
                preparedStatement.setInt(4, 1);
                preparedStatement.setString(5, faultInput.getDescription());
                preparedStatement.setString(6, faultInput.getAttachment());
                preparedStatement.setString(7, faultInput.getCreatedBy());
                preparedStatement.setString(8, faultInput.getCreatedBy());

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

    public List<FaultTypeDto> getAllFaultTypes() {
        List<FaultTypeDto> faultTypes = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_FAULT_TYPES)) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                FaultTypeDto faultTypeDto = new FaultTypeDto();
                faultTypeDto.setId(rs.getInt("id"));
                faultTypeDto.setName(rs.getString("name"));
                faultTypes.add(faultTypeDto);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return faultTypes;
    }
}
