package lk.gsbp.repository;

import lk.gsbp.db.DbConnection;
import lk.gsbp.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepo {
    public static List<Customer> getAll() throws SQLException {
        String sql = "SELECT * FROM customer";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();

        List<Customer> customers = new ArrayList<>();

        while (resultSet.next()) {
            String CustomerId = resultSet.getNString(1);
            String Name = resultSet.getNString(2);
            String Address = resultSet.getNString(3);
            String Contact = resultSet.getNString(4);
            String Email = resultSet.getNString(5);

            Customer customer = new Customer(CustomerId, Name, Address, Contact, Email);

            customers.add(customer);
        }
        return customers;
    }
    public static List<String> getIds() throws SQLException {
        String sql = "SELECT CustomerId FROM customer";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        List<String> ids = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String CustomerId = resultSet.getNString(1);
            ids.add(CustomerId);
        }
        return ids;
    }

    public static boolean update (Customer customer) throws SQLException {
        String sql = "UPDATE customer SET Name =?, Address =?, Contact =?, Email =? WHERE CustomerId =?";

        Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

            pstm.setString(1, customer.getName());
            pstm.setString(2, customer.getAddress());
            pstm.setString(3, customer.getContact());
            pstm.setString(4, customer.getEmail());
            pstm.setString(5, customer.getCustomerId());

            return pstm.executeUpdate() > 0;
        }

        public static boolean update2(String customerId, String Name, String Address, String Contact, String Email) throws SQLException {
        String sql = "UPDATE customer SET Name =?, Address =?, Contact =?, Email =? WHERE CustomerId =?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,Name);
        pstm.setObject(2,Address);
        pstm.setObject(3,Contact);
        pstm.setObject(4,Email);
        pstm.setObject(5,customerId);

        return pstm.executeUpdate() > 0;
        }
}
