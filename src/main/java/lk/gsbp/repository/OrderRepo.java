package lk.gsbp.repository;

import lk.gsbp.db.DbConnection;
import lk.gsbp.model.Order;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        String sql = "INSERT INTO Orders ( OrderId , Date , CustomerId ) VALUES (?, ?, ?)";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1, order.getOrderId());
        pstm.setString(2, order.getDate());
        pstm.setString(3, order.getCustomerId());

        //pstm.setDate(3, Date.valueOf(order.getDate()));

        return pstm.executeUpdate() > 0;
    }
}
