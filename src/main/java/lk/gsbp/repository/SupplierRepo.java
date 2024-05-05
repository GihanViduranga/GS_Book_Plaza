package lk.gsbp.repository;

import lk.gsbp.db.DbConnection;
import lk.gsbp.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepo {
    public static boolean update (Supplier supplier) throws SQLException {
        String sql = "UPDATE supplier SET Name = ?, Contact = ?, Products = ?, DeliveryTerms = ?, SupplierRating = ? WHERE SupplierId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1, supplier.getSuppName());
        pstm.setString(2, supplier.getContact());
        pstm.setString(3, supplier.getProduct());
        pstm.setString(4, supplier.getDeliveryTerms());
        pstm.setString(5, supplier.getSupplierRating());
        pstm.setString(6, supplier.getSupplierId());

        return pstm.executeUpdate() > 0;
    }
    public static boolean update2(String SupplierId,String SuppName,String Contact,String Products,String DeliveryTerms,String SupplierRating) throws SQLException {
        String sql = "UPDATE supplier SET Name =?, Contact =?, Products =?, DeliveryTerms =?, SupplierRating =? WHERE SupplierId =?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        pstm.setObject(1,SuppName);
        pstm.setObject(2,Contact);
        pstm.setObject(3,Products);
        pstm.setObject(4,DeliveryTerms);
        pstm.setObject(5,SupplierRating);
        pstm.setObject(6,SupplierId);

        return pstm.executeUpdate() > 0;
    }

    public static List<Supplier> getAll() throws SQLException {
        String sql = "SELECT * FROM supplier";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

            List<Supplier> suppliers = new ArrayList<>();

            while (resultSet.next()) {
                String SupplierId = resultSet.getNString(1);
                String SupplierName = resultSet.getNString(2);
                String Contact = resultSet.getNString(3);
                String Products = resultSet.getNString(4);
                String DeliveryTerms = resultSet.getNString(5);
                String SupplierRating = resultSet.getNString(6);

                Supplier supplier = new Supplier(SupplierId, SupplierName, Contact, Products, DeliveryTerms, SupplierRating);
                suppliers.add(supplier);
            }
            return suppliers;
        }
    }
