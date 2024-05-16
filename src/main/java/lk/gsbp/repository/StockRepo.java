package lk.gsbp.repository;

import lk.gsbp.db.DbConnection;
import lk.gsbp.model.Stock;
import lk.gsbp.model.stockDetails;

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

            Stock stock = new Stock(StockId, ItemName, CatogaryName,QTY);

            stockList.add(stock);
        }
        return stockList;
    }

    public static boolean update2(String StockId, String ItemName, String CatogaryName, String QTY) throws SQLException {
        String sql = "UPDATE Stock SET ItemName =?, CatogaryName =?, QTY =? WHERE StockId =?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, ItemName);
        pstm.setObject(2, CatogaryName);
        pstm.setObject(3, QTY);
        pstm.setObject(4, StockId);

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

    public static boolean update(List<stockDetails> stList) throws SQLException {
        for (stockDetails st : stList) {
            boolean isUpdateQty = UpdateQty(st.getStockId(), String.valueOf(st.getQTY()));
            if (!isUpdateQty) {
                return false;
            }
        }
        return true;
    }

    private static boolean UpdateQty(String StockId, String QTY) throws SQLException {
        String sql = "UPDATE stock SET QTY = (QTY + ?) WHERE StockId = ?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1, QTY);
        pstm.setString(2, StockId);

        return pstm.executeUpdate() > 0;
    }


    public static boolean save(Stock stock) throws SQLException {
        String sql = "INSERT INTO stock (StockId,ItemName,CatogaryName,QTY) VALUES (?,?,?,?)";


            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, stock.getStockId());
            pstm.setString(2, stock.getItemName());
            pstm.setString(3, stock.getCatogaryName());
            pstm.setString(4, stock.getQTY());

            return pstm.executeUpdate() > 0;
    }

    public static String GetStockId() throws SQLException {
        String sql = "SELECT StockId FROM stock ORDER BY StockId DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String stockId = resultSet.getString(1);
            return stockId;
        }
        return null;
    }

    public static Stock searchById(String id) throws SQLException {
        String sql = "SELECT * FROM stock WHERE  StockId = ?";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1, id);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return new Stock(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        }
        return null;
    }

    public static List<String> getAllStock() throws SQLException {
        String sql = "SELECT StockId FROM stock";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        List<String> Stockids = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            Stockids.add(resultSet.getString(1));
        }
        return Stockids;
    }
}