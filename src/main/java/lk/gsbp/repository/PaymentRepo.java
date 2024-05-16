package lk.gsbp.repository;

import lk.gsbp.db.DbConnection;
import lk.gsbp.model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public static List<Payment> getAllPayments() throws SQLException {
        String sql = "SELECT * FROM payment";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<Payment> payments = new ArrayList<>();

        while (resultSet.next()) {
            String PaymentId = resultSet.getString(1);
            String PaymentMethod = resultSet.getString(2);
            String Date = resultSet.getString(3);
            String Payment = resultSet.getString(4);

            Payment payment = new Payment(PaymentId, PaymentMethod, Date, Payment);
            payments.add(payment);
        }
        return payments;
    }

    public static String GetPaymentId() throws SQLException {
        String sql = "SELECT PaymentId FROM payment ORDER BY PaymentId DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            String PaymentId = resultSet.getString(1);
            return PaymentId;
        }
        return null;

    }

    public boolean save(String paymentId, String paymentMethod, String date, String payment) throws SQLException {
        String sql = "INSERT INTO payment VALUES (?,?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, paymentId);
        pstm.setString(2, paymentMethod);
        pstm.setString(3, date);
        pstm.setString(4, payment);

        return pstm.executeUpdate() > 0;
    }
}
