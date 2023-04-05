package com.hotel.dao;

import com.hotel.dto.FaultDto;
import com.hotel.dto.FaultStatusDto;
import com.hotel.dto.FaultTypeDto;
import com.hotel.input.FaultInput;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FaultDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/hotel";
    private String jdbcUsername = "root";
    private String jdbcPassword = "harsha";

    private static final String INSERT_FAULT = "INSERT INTO fault (floor, room, fault_type_id, fault_status_id, attachment, created_by, updated_by) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?);";

    private static final String INSERT_FAULT_COMMENT = "INSERT INTO fault_comment (fault_id, comment, created_by, updated_by) " +
            "VALUES (?, ?, ?, ?);";

    private static final String GET_ALL_FAULT_TYPES = "SELECT id, name FROM fault_type;";

    private static final String GET_ALL_FAULT_STATUSES = "SELECT id, name FROM fault_status;";

    private static final String GET_USER_LAST_10_FAULTS = "SELECT `f`.`id`, `f`.`floor`, `f`.`room`, `ft`.`name` AS `fault_type_name`, `fs`.`name` AS `fault_status_name`, `f`.`attachment`, `f`.`created_date`, `f`.`created_by`, `f`.`updated_date`, `f`.`updated_by`\n" +
            "FROM `fault` AS `f`\n" +
            "JOIN `fault_type` AS `ft` ON `f`.`fault_type_id` = `ft`.`id`\n" +
            "JOIN `fault_status` AS `fs` ON `f`.`fault_status_id` = `fs`.`id`\n" +
            "WHERE `f`.`created_by` = ?\n" +
            "ORDER BY `f`.`created_date` DESC\n" +
            "LIMIT 10;\n";

    private static final String GET_FAULTS_BY = "SELECT `f`.`id`, `f`.`floor`, `f`.`room`, `ft`.`name` AS `fault_type_name`, `fs`.`name` AS `fault_status_name`, `f`.`attachment`, `f`.`created_date`, `f`.`created_by`, `f`.`updated_date`, `f`.`updated_by`\n" +
            "FROM `fault` AS `f`\n" +
            "JOIN `fault_type` AS `ft` ON `f`.`fault_type_id` = `ft`.`id`\n" +
            "JOIN `fault_status` AS `fs` ON `f`.`fault_status_id` = `fs`.`id` ";

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
                preparedStatement.setString(5, faultInput.getAttachment());
                preparedStatement.setString(6, faultInput.getCreatedBy());
                preparedStatement.setString(7, faultInput.getCreatedBy());

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

    public List<FaultDto> getUserLast10Faults(String userName) {
        List<FaultDto> faults = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_LAST_10_FAULTS)) {

            preparedStatement.setString(1, userName);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                FaultDto faultDto = new FaultDto();
                faultDto.setId(rs.getInt("id"));
                faultDto.setFloor(rs.getString("floor"));
                faultDto.setRoom(rs.getString("room"));
                faultDto.setFaultTypeName(rs.getString("fault_type_name"));
                faultDto.setFaultStatusName(rs.getString("fault_status_name"));
                faultDto.setAttachment(rs.getString("attachment"));
                faultDto.setCreatedBy(rs.getString("created_by"));
                faultDto.setUpdatedBy(rs.getString("updated_by"));
                faultDto.setCreatedDate(rs.getDate("created_date"));
                faultDto.setUpdatedDate(rs.getDate("updated_date"));
                faults.add(faultDto);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return faults;
    }

    public List<FaultDto> getFaultBy(LocalDate faultDate, int filterFloor, String filterRoom, int filterFaultType) {
        List<FaultDto> faults = new ArrayList<>();

        String sql = GET_FAULTS_BY;

        if (faultDate != null) {
            sql = sql + " WHERE `f`.`created_date` = ? ";
        } else {
            sql = sql + " WHERE 1=1 ";
        }

        if (filterFloor != 0) {
            sql = sql + " AND `f`.`floor` = ? ";
        }

        if (filterRoom != null && !filterRoom.equals("ALL")) {
            sql = sql + " AND `f`.`room` = ? ";
        }

        if (filterFaultType != 0) {
            sql = sql + " AND `f`.`fault_type_id` = ? ";
        }

        sql = sql + "ORDER BY `f`.`created_date` DESC\n" +
                "LIMIT 10;\n";


        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            if (faultDate != null) {
                preparedStatement.setDate(1, java.sql.Date.valueOf(faultDate));
            }

            if (faultDate != null && filterFloor != 0) {
                preparedStatement.setInt(2, filterFloor);
            } else if (faultDate == null && filterFloor != 0) {
                preparedStatement.setInt(1, filterFloor);
            }

            if (faultDate != null && filterFloor != 0 && filterRoom != null && !filterRoom.equals("ALL")) {
                preparedStatement.setString(3, filterRoom);
            } else if (faultDate == null && filterFloor != 0 && filterRoom != null && !filterRoom.equals("ALL")) {
                preparedStatement.setString(2, filterRoom);
            } else if (faultDate != null && filterFloor == 0 && filterRoom != null && !filterRoom.equals("ALL")) {
                preparedStatement.setString(2, filterRoom);
            } else if (faultDate == null && filterFloor == 0 && (filterRoom != null && !filterRoom.equals("ALL"))) {
                preparedStatement.setString(1, filterRoom);
            }

            if (faultDate != null && filterFloor != 0 && filterRoom != null && !filterRoom.equals("ALL") && filterFaultType != 0) {
                preparedStatement.setInt(4, filterFaultType);
            } else if (faultDate == null && filterFloor != 0 && filterRoom != null && !filterRoom.equals("ALL") && filterFaultType != 0) {
                preparedStatement.setInt(3, filterFaultType);
            } else if (faultDate != null && filterFloor == 0 && filterRoom != null && !filterRoom.equals("ALL") && filterFaultType != 0) {
                preparedStatement.setInt(3, filterFaultType);
            } else if (faultDate != null && filterFloor != 0 && (filterRoom == null || filterRoom.equals("ALL")) && filterFaultType != 0) {
                preparedStatement.setInt(3, filterFaultType);
            } else if (faultDate == null && filterFloor == 0 && filterRoom != null && !filterRoom.equals("ALL") && filterFaultType != 0) {
                preparedStatement.setInt(2, filterFaultType);
            } else if (faultDate != null && filterFloor == 0 && (filterRoom == null || filterRoom.equals("ALL")) && filterFaultType != 0) {
                preparedStatement.setInt(2, filterFaultType);
            } else if (faultDate == null && filterFloor != 0 && (filterRoom == null || filterRoom.equals("ALL")) && filterFaultType != 0) {
                preparedStatement.setInt(2, filterFaultType);
            } else if (faultDate == null && filterFloor == 0 && (filterRoom == null || filterRoom.equals("ALL")) && filterFaultType != 0) {
                preparedStatement.setInt(1, filterFaultType);
            }

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                FaultDto faultDto = new FaultDto();
                faultDto.setId(rs.getInt("id"));
                faultDto.setFloor(rs.getString("floor"));
                faultDto.setRoom(rs.getString("room"));
                faultDto.setFaultTypeName(rs.getString("fault_type_name"));
                faultDto.setFaultStatusName(rs.getString("fault_status_name"));
                faultDto.setAttachment(rs.getString("attachment"));
                faultDto.setCreatedBy(rs.getString("created_by"));
                faultDto.setUpdatedBy(rs.getString("updated_by"));
                faultDto.setCreatedDate(rs.getDate("created_date"));
                faultDto.setUpdatedDate(rs.getDate("updated_date"));
                faults.add(faultDto);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return faults;
    }

    public int insertFaultComment(String filterFaultDescription, int i, String userName) {
        int id = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FAULT_COMMENT, Statement.RETURN_GENERATED_KEYS)) {

            try {
                preparedStatement.setInt(1, i);
                preparedStatement.setString(2, filterFaultDescription);
                preparedStatement.setString(3, userName);
                preparedStatement.setString(4, userName);

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

    public List<FaultStatusDto> getAllFaultStatus() {
        List<FaultStatusDto> faultStatuses = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_FAULT_STATUSES)) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                FaultStatusDto faultStatusDto = new FaultStatusDto();
                faultStatusDto.setId(rs.getInt("id"));
                faultStatusDto.setName(rs.getString("name"));
                faultStatuses.add(faultStatusDto);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return faultStatuses;
    }
}
