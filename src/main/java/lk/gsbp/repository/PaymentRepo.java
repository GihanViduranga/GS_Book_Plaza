package lk.gsbp.repository;

import lk.gsbp.db.DbConnection;
import lk.gsbp.model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentRepo {
    public static boolean update(Payment payment) throws SQLException {
        String sql = "UPDATE Payment SET PaymentMethod = ?, Date = ?, Payment = ? WHERE PaymentId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, payment.getPaymentId());
        pstm.setObject(2, payment.getPaymentMethod());
        pstm.setString(3, payment.getDate());
        pstm.setObject(4, payment.getPayment());


        return pstm.executeUpdate() > 0;
    }
    public static boolean update2(String PaymentId, String PaymentMethod, String Date,String Payment) throws SQLException {
        String sql = "UPDATE Payment SET PaymentMethod = ?, Date = ?, Payment = ? WHERE PaymentId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1, PaymentId);
        pstm.setObject(2, PaymentMethod);
        pstm.setObject(3, Date);
        pstm.setObject(4, Payment);


        return pstm.executeUpdate() > 0;
    }
}
