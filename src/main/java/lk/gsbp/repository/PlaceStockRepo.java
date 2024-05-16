package lk.gsbp.repository;

import javafx.scene.control.Alert;
import lk.gsbp.controller.StockFormController;
import lk.gsbp.db.DbConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceStockRepo {
    public static boolean placeStock(PlaceStock ps) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        try {
            boolean isStockSaved = StockRepo.save(ps.getStock());
            if (isStockSaved) {
                boolean isQtyUpdated = StockRepo.update(ps.getStList());
                if (isQtyUpdated) {
                    boolean isStockDetailSaved = StockDetailRepo.save(ps.getStList());

                    if (isStockDetailSaved) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
