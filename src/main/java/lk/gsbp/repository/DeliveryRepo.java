package lk.gsbp.repository;

import lk.gsbp.db.DbConnection;
import lk.gsbp.model.Delivery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeliveryRepo {
    public static List<Delivery> getAllDelivery() throws SQLException {
        String sql = "SELECT * FROM dilivery";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Delivery> deliveryList = new ArrayList<>();

        while (resultSet.next()) {
            String DeliveryId = resultSet.getString(1);
            String Date = resultSet.getString(2);
            String Address = resultSet.getString(3);
            String Stetus = resultSet.getString(4);

            Delivery delivery = new Delivery(DeliveryId, Date, Address, Stetus);

            deliveryList.add(delivery);
        }

        return deliveryList;
    }
    public static boolean update (Delivery delivery) throws SQLException {
        String sql = "UPDATE dilivery SET DiliveryId =?, Date =?, Address =?, Stetus =? WHERE DiliveryId";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, delivery.getDeliveryId());
        pstm.setObject(2, delivery.getDate());
        pstm.setObject(3, delivery.getAddress());
        pstm.setObject(4, delivery.getStetus());

        return pstm.executeUpdate() > 0;
    }
    public static boolean update2(String DeliveryId, String Date, String Address, String Stetus ) throws SQLException {
        String sql = "UPDATE dilivery SET Date =?, Address =?, Stetus =? WHERE DiliveryId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);


        pstm.setObject(1,Date);
        pstm.setObject(2,Address);
        pstm.setObject(3,Stetus);
        pstm.setObject(4,DeliveryId);

        return pstm.executeUpdate() > 0;
    }
}
