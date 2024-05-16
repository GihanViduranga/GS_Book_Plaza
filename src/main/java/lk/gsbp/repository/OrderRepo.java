package lk.gsbp.repository;

import lk.gsbp.db.DbConnection;
import lk.gsbp.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepo {

    public static String GetOrderId() throws SQLException {
        String sql = "SELECT OrderId FROM Orders ORDER BY OrderId DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String OrderId = resultSet.getString(1);
            return OrderId;
        }
        return null;
    }

    public static boolean save(Order order) throws SQLException {
        String sql = "INSERT INTO Orders ( OrderId , Date , CustomerId,NetTotal ) VALUES (?, ?, ?,?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1, order.getOrderId());
        pstm.setString(2, order.getDate());
        pstm.setString(3, order.getCustomerId());
        pstm.setDouble(4, order.getNetTotal());

        //pstm.setDate(3, Date.valueOf(order.getDate()));

        return pstm.executeUpdate() > 0;
    }

    public static Order searchById(String id) throws SQLException {
        String sql = "SELECT *  FROM orders WHERE OrderId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1, id);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return new Order(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            );
        }
        return null;
    }

    public static List<String> getAllOrders() throws SQLException {
        String sql = "SELECT OrderId FROM orders";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        List<String> orderIds = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            orderIds.add(resultSet.getString(1));
        }
        return orderIds;
    }
}
