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
import java.util.Optional;

import javax.annotation.Resource;
import javax.sql.DataSource;

import com.hotel.dto.OrderDTO;
import com.hotel.dto.OrderItemDTO;
import com.hotel.dto.PlacedOrderItemDTO;

public class ApprovedOrderDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/hotel";
    private String jdbcUsername = "root";
    private String jdbcPassword = "root";
	
	
	private static final String INSERT_ORDER_SQL = "INSERT INTO approved_room_order (id, floor, order_date, created_date, created_by, updated_date, updated_by) VALUES " +
	        " (?, ?, ?, ?, ?, ?, ?);";
	
	private static final String SELECT_ORDER_BY_DATE_FLOOR = "SELECT ID FROM approved_room_order WHERE ORDER_DATE=? AND FLOOR=?;";
	
	private static final String SELECT_ORDER_ITEM_BY_ORDER_FLOOR = "SELECT ID FROM approved_room_order_item WHERE ITEM=? AND ORDER_ID=?;";
	
	private static final String INSERT_ORDER_ITEM_SQL = "INSERT INTO approved_room_order_item (id, order_id, item, amount, created_date, created_by, updated_date, updated_by) VALUES " +
	        " (?, ?, ?, ?, ?, ?, ?, ?);";
	
	private static final String UPDATE_ORDER_ITEM_SQL = "UPDATE approved_room_order_item SET amount=? WHERE id=?;";
	
	private static final String SELECT_ITEM_ORDER_BY_DATE_FLOOR = "SELECT ordItem.id as item_id,  ord.id, ord.order_date, ord.floor, ordItem.item, ordItem.amount FROM approved_room_order ord\r\n" + 
			"INNER JOIN approved_room_order_item ordItem ON ord.id=ordItem.order_id WHERE ord.ORDER_DATE=?";
	
	
	
	
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
	
	
	public void insertUpdateOrder(List<OrderDTO> orderList) {
		List<OrderDTO> addOrderList = new ArrayList<>();
		List<OrderDTO> updOrderList = new ArrayList<>();
		orderList.forEach(order -> {
			Integer id = Optional.ofNullable(selectOrderByDateFloor(order.getDate(), order.getFloor())).orElse(0);
			if(id>0) {
				order.setId(id);
				updOrderList.add(order);
			} else {
				addOrderList.add(order);
			}
		});
		insertOrder(addOrderList);
		updOrderList.forEach(order1->{
			insertUpdateItemDetails(order1.getOrderItemList());
		});
	}
	
	
	private void insertOrder(List<OrderDTO> orderList) {
		try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_SQL, Statement.RETURN_GENERATED_KEYS)) {
            
            orderList.forEach(order -> {
            	try {
            		    preparedStatement.setInt(1, order.getId());
						preparedStatement.setInt(2, order.getFloor());
						preparedStatement.setDate(3, java.sql.Date.valueOf(order.getDate()));
						preparedStatement.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));
						preparedStatement.setString(5, order.getUserName());
						preparedStatement.setDate(6, java.sql.Date.valueOf(java.time.LocalDate.now()));
						preparedStatement.setString(7, order.getUserName());
						
						preparedStatement.executeUpdate();
					    
					    order.getOrderItemList().forEach(orderItem -> {
					    	orderItem.setOrderId(order.getId());
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
	
	
	private void insertUpdateItemDetails(List<OrderItemDTO> orderItemLst) {
		List<OrderItemDTO> updateItemList = new ArrayList<>();
		List<OrderItemDTO> addItemList = new ArrayList<>();
		orderItemLst.forEach(order -> {
			int id = selectOrderItemByOrderFloor(order.getOrderId(), order.getItemName());
			if(id>0) {
				order.setId(id);
				updateItemList.add(order);
			} else {
				addItemList.add(order);
			}
		});
		
		insertOrderItem(addItemList);
		updateOrderItem(updateItemList);
	}
	
	
	private void insertOrderItem(List<OrderItemDTO> orderItemLst) {
		
		try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_ITEM_SQL, Statement.RETURN_GENERATED_KEYS)) {
            
        	orderItemLst.forEach(orderItem -> {
            	try {
            		    preparedStatement.setInt(1, orderItem.getId());
            			preparedStatement.setInt(2, orderItem.getOrderId());
            			preparedStatement.setString(3, orderItem.getItemName());
            			preparedStatement.setInt(4, orderItem.getQuantity());
            			preparedStatement.setDate(5, java.sql.Date.valueOf(java.time.LocalDate.now()));
            			preparedStatement.setString(6, orderItem.getUserName());
            			preparedStatement.setDate(7, java.sql.Date.valueOf(java.time.LocalDate.now()));
            			preparedStatement.setString(8, orderItem.getUserName());
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
	
	private void updateOrderItem(List<OrderItemDTO> orderItemLst) {
		
		try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_ITEM_SQL)) {
            
        	orderItemLst.forEach(orderItem -> {
            	try {
            		    preparedStatement.setInt(1, orderItem.getQuantity());
            			preparedStatement.setInt(2, orderItem.getId());
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
	
	
	public Integer selectOrderByDateFloor(LocalDate orderDate, int floor) {
		Integer orderId = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_BY_DATE_FLOOR);) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(orderDate));
            preparedStatement.setInt(2, floor);
            
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
	
	
	public Integer selectOrderItemByOrderFloor(int orderId, String itemName) {
		Integer orderItemId = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_ITEM_BY_ORDER_FLOOR);) {
            preparedStatement.setString(1, itemName);
            preparedStatement.setInt(2, orderId);
            
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
            	orderItemId = rs.getInt("ID");
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return orderItemId;
    }
	
	
	public List<PlacedOrderItemDTO> selectOrderItemByDateFloor(LocalDate orderDate, Integer floor) {
		String sql = SELECT_ITEM_ORDER_BY_DATE_FLOOR;
		List<PlacedOrderItemDTO> placedOrderItemDTOs = new ArrayList<>();
		
		if(floor!=null) {
			sql = sql.concat(" AND ord.FLOOR=?");
		}
		
		
        try (Connection connection = getConnection();
   	
            PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
        	int parametrCount = 2;
            preparedStatement.setDate(1, java.sql.Date.valueOf(orderDate));
    		
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
