package lk.gsbp.repository;

import lk.gsbp.db.DbConnection;
import lk.gsbp.model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemRepo {
    public static boolean update(Item item) throws SQLException {
        String sql = "UPDATE Items SET ItemName =?, QTY =?, UnitPrice =? WHERE ItemsId =?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1, item.getItemName());
        pstm.setString(2, item.getQTY());
        pstm.setString(3, item.getUnitPrice());
        pstm.setString(4, item.getItemsId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean Update2(String ItemsId, String ItemName, String QTY, String UnitPrice) throws SQLException {
        String sql = "UPDATE Items SET ItemName =?, QTY =?, UnitPrice =? WHERE ItemsId =?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);


        pstm.setString(1,ItemName);
        pstm.setString(2,QTY);
        pstm.setObject(3,UnitPrice);
        pstm.setObject(4,ItemsId);

        return pstm.executeUpdate() > 0;
    }

    public static List<String> getItemID() throws SQLException {
        String sql = "SELECT * FROM Items";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        List<String> ItemIds = new ArrayList<String>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String ItemsId = resultSet.getString(1);
            ItemIds.add(ItemsId);
        }
        return ItemIds;
    }

    public static Item searchById(String id) throws SQLException {
        String sql = "SELECT * FROM Items WHERE ItemsId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1, id);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
        }
        return null;
    }

    public static List<Item> getAll() throws SQLException {
        String sql = "SELECT * FROM Items";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Item> items = new ArrayList<>();

        while (resultSet.next()) {
            String ItemId = resultSet.getNString(1);
            String ItemName = resultSet.getNString(2);
            String QTY = resultSet.getNString(3);
            String UnitPrice = resultSet.getNString(4);
            String StockId = resultSet.getNString(5);

            Item item = new Item(ItemId, ItemName, QTY, UnitPrice, StockId);

            items.add(item);
        }
        return items;
    }
}
