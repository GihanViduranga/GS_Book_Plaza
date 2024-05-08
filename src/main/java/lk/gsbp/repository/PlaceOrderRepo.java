package lk.gsbp.repository;

import javafx.scene.control.Alert;
import lk.gsbp.db.DbConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderRepo {
    public static boolean placeOrder(PlaceOrder po) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        try {
            boolean isOrderSaved = OrderRepo.save(po.getOrder());
            System.out.println("1st "+isOrderSaved);
            if (isOrderSaved) {
                boolean isQtyUpdated = ItemRepo.update(po.getOdList());
                System.out.println("2nd "+isQtyUpdated);
                if (isQtyUpdated) {
                    boolean isOrderDetailSaved = OrderDetailRepo.save(po.getOdList());
                    System.out.println("3rd "+isOrderDetailSaved);
                    if (isOrderDetailSaved) {
                        connection.commit();
                        return true;
                    }
                }
            }
            System.out.println("Sakabum");
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }
    /*public static boolean placeOrder(PlaceOrder po)  {
        Connection connection = DbConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        try{
            boolean isOrderSaved = OrderRepo.save(po.getOrder());
                System.out.println(isOrderSaved);
            if (isOrderSaved) {
                boolean isQtyUpdated = ItemRepo.update(po.getOdList());
                if (isQtyUpdated) {
                    boolean isOrderDetailSaved = OrderDetailRepo.save(po.getOdList());
                    if (isOrderDetailSaved) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }*/
}
