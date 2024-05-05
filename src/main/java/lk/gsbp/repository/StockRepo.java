package lk.gsbp.repository;

import lk.gsbp.db.DbConnection;
import lk.gsbp.model.Customer;
import lk.gsbp.model.Stock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockRepo {
    public static List<Stock> getAll() throws SQLException {
        String sql = "SELECT * FROM Stock";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Stock> stockList = new ArrayList<>();

        while (resultSet.next()) {
            String StockId = resultSet.getNString(1);
            String ItemName = resultSet.getNString(2);
            String CatogaryName = resultSet.getNString(3);
            String QTY = resultSet.getNString(4);

            Stock stock = new Stock(StockId, ItemName, CatogaryName, QTY);

            stockList.add(stock);
        }
        return stockList;
    }

    public static boolean update2(String StockId,String ItemName,String CatogaryName, String QTY) throws SQLException {
        String sql = "UPDATE Stock SET ItemName =?, CatogaryName =?, QTY =? WHERE StockId =?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,ItemName);
        pstm.setObject(2,CatogaryName);
        pstm.setObject(3,QTY);
        pstm.setObject(4,StockId);

        return pstm.executeUpdate() > 0;
    }
    public static boolean update(Stock stock) throws SQLException {
        String sql = "UPDATE Stock SET ItemName =?, CatogaryName =?, QTY =? WHERE StockId =?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1, stock.getItemName());
        pstm.setString(2, stock.getCatogaryName());
        pstm.setString(3, stock.getQTY());
        pstm.setObject(4, stock.getStockId());

        return pstm.executeUpdate() > 0;
    }
}
