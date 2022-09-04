package com.hotel.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import com.hotel.dto.OrderDTO;
import com.hotel.dto.OrderItemDTO;
import com.hotel.dto.PlacedOrderItemDTO;
import com.hotel.exception.ObjectAlreadyExistException;

public class OrderDAO {
	
	private String jdbcURL = "jdbc:mysql://localhost:3306/hotel";
    private String jdbcUsername = "root";
    private String jdbcPassword = "root";
	
	
	private static final String INSERT_ORDER_SQL = "INSERT INTO room_order (floor, order_date, created_date, created_by, updated_date, updated_by) VALUES " +
	        " (?, ?, ?, ?, ?, ?);";
	
	private static final String SELECT_ORDER_BY_DATE_FLOOR_USER = "SELECT ID FROM room_order WHERE ORDER_DATE=? AND FLOOR=? AND CREATED_BY=?;";
	
	private static final String INSERT_ORDER_ITEM_SQL = "INSERT INTO room_order_item (order_id, item, amount, created_date, created_by, updated_date, updated_by) VALUES " +
	        " (?, ?, ?, ?, ?, ?, ?);";
	
	private static final String SELECT_ITEM_ORDER_BY_DATE_FLOOR_USER = "SELECT ordItem.id as item_id, ord.id, ord.order_date, ord.floor, ordItem.item, ordItem.amount FROM room_order ord\r\n" + 
			"INNER JOIN room_order_item ordItem ON ord.id=ordItem.order_id WHERE ord.ORDER_DATE=?";
	
	
	
	
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
	
	public void insertOrder(List<OrderDTO> orderList) {
		
		System.out.println(INSERT_ORDER_SQL);
        // try-with-resource statement will auto close the connection.
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_SQL, Statement.RETURN_GENERATED_KEYS)) {
            
            orderList.forEach(order -> {
            	try {
						preparedStatement.setInt(1, order.getFloor());
						preparedStatement.setDate(2, java.sql.Date.valueOf(order.getDate()));
						preparedStatement.setDate(3, java.sql.Date.valueOf(java.time.LocalDate.now()));
						preparedStatement.setString(4, order.getUserName());
						preparedStatement.setDate(5, java.sql.Date.valueOf(java.time.LocalDate.now()));
						preparedStatement.setString(6, order.getUserName());
						
						preparedStatement.executeUpdate();
						ResultSet rs = preparedStatement.getGeneratedKeys();
					    rs.next();
					    Integer autoId = rs.getInt(1);
					    System.out.println("autoId : "+autoId);
					    
					    order.getOrderItemList().forEach(orderItem -> {
					    	orderItem.setOrderId(autoId);
					    	orderItem.setUserName(order.getUserName());
					    });
					    
					    insertOrderItem(order.getOrderItemList());
						
            		} catch (SQLException e) {
						e.printStackTrace();
					} 
            });
            
        } catch (SQLException e) {
        	e.printStackTrace();
        }
    }
	
public void insertOrderItem(List<OrderItemDTO> orderItemLst) {
		
		System.out.println(INSERT_ORDER_SQL);
        // try-with-resource statement will auto close the connection.
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_ITEM_SQL, Statement.RETURN_GENERATED_KEYS)) {
            
        	orderItemLst.forEach(orderItem -> {
            	try {
            			preparedStatement.setInt(1, orderItem.getOrderId());
            			preparedStatement.setString(2, orderItem.getItemName());
            			preparedStatement.setInt(3, orderItem.getQuantity());
            			preparedStatement.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));
            			preparedStatement.setString(5, orderItem.getUserName());
            			preparedStatement.setDate(6, java.sql.Date.valueOf(java.time.LocalDate.now()));
            			preparedStatement.setString(7, orderItem.getUserName());
            			preparedStatement.addBatch();
            		} catch (SQLException e) {
						e.printStackTrace();
					} 
            });
        	preparedStatement.executeBatch();
            
        } catch (SQLException e) {
        	e.printStackTrace();
        }
    }
	
	
	public Integer selectOrderByDateFloorUser(LocalDate orderDate, int floor, String userName) {
		Integer orderId = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_BY_DATE_FLOOR_USER);) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(orderDate));
            preparedStatement.setInt(2, floor);
            preparedStatement.setString(3, userName);
            
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
            	orderId = rs.getInt("ID");
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return orderId;
    }
	
	
	public List<PlacedOrderItemDTO> selectOrderItemByDateFloorUser(LocalDate orderDate, Integer floor, String userName) {
		String sql = SELECT_ITEM_ORDER_BY_DATE_FLOOR_USER;
		List<PlacedOrderItemDTO> placedOrderItemDTOs = new ArrayList<>();
		
		if(userName!=null) {
			sql = sql.concat(" AND ord.CREATED_BY=?");
		}
		
		if(floor!=null) {
			sql = sql.concat(" AND ord.FLOOR=?");
		}
		
		
        try (Connection connection = getConnection();
   	
            PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
        	int parametrCount = 2;
            preparedStatement.setDate(1, java.sql.Date.valueOf(orderDate));
            
            if(userName!=null) {
            	preparedStatement.setString(parametrCount, userName);
            	parametrCount ++;
    		}
    		
    		if(floor!=null) {
    			preparedStatement.setInt(parametrCount, floor);
            	parametrCount ++;
    		}

		    System.out.println("preparedStatement : "+preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
            	Integer id = rs.getInt("ITEM_ID");
            	Integer orderId = rs.getInt("ID");
            	LocalDate ordDate = rs.getDate("order_date").toLocalDate();
            	Integer floorId = rs.getInt("FLOOR");
            	String item = rs.getString("ITEM");
            	Integer amount = rs.getInt("AMOUNT");
            	placedOrderItemDTOs.add(new PlacedOrderItemDTO(id, orderId, ordDate, floorId, item, amount));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return placedOrderItemDTOs;
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
