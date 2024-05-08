package lk.gsbp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.gsbp.db.DbConnection;
import lk.gsbp.model.Customer;
import lk.gsbp.model.tm.CustomerTm;
import lk.gsbp.repository.CustomerRepo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

public class CustomerFormController {

    public AnchorPane root;
    public TextField txtCustomerId;
    public TextField txtCustomerAddress;
    public TextField txtCustomerContact;
    public TextField txtCustomerName;
    public TextField txtCustomerEmail;
    public TableView<CustomerTm> tblCusTable;
    public TableColumn colID;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colEmail;


    public void btnCusSaveOnAction(ActionEvent actionEvent) {
        String id = txtCustomerId.getText();
        String name = txtCustomerName.getText();
        String address = txtCustomerAddress.getText();
        String contact = txtCustomerContact.getText();
        String email = txtCustomerEmail.getText();

        String sql = "INSERT INTO customer Values(?,?,?,?,?)";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,id);
            pstm.setString(2,name);
            pstm.setString(3,address);
            pstm.setString(4,contact);
            pstm.setString(5,email);

            boolean isSaved = pstm.executeUpdate() > 0;
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void initialize() {
        setCellValueFactory();
        loadAllCustomers();
    }

    private void loadAllCustomers() {
        ObservableList<CustomerTm> objects = FXCollections.observableArrayList();

        try {
            List<Customer> customerList = CustomerRepo.getAll();

            for (Customer customer : customerList) {
                CustomerTm customerTm = new CustomerTm(
                        customer.getCustomerId(),
                        customer.getName(),
                        customer.getAddress(),
                        customer.getContact(),
                        customer.getEmail()
                );
                objects.add(customerTm);
                tblCusTable.setItems(objects);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValueFactory() {
        colID.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
    }

    public void btnCusClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        txtCustomerId.setText("");
        txtCustomerName.setText("");
        txtCustomerAddress.setText("");
        txtCustomerContact.setText("");
        txtCustomerEmail.setText("");
    }

    public void btnCusUpdateOnAction(ActionEvent actionEvent) {
        String CustomerId = txtCustomerId.getText();
        String Name = txtCustomerName.getText();
        String Address = txtCustomerAddress.getText();
        String Contact = txtCustomerContact.getText();
        String Email = txtCustomerEmail.getText();

        String sql = "UPDATE customer SET Name =?, Address =?, Contact =?, Email =? WHERE CustomerId =?";

       try {
           boolean isUpdate = CustomerRepo.update2(CustomerId, Name, Address, Contact, Email);
           if (isUpdate) {
               new Alert(Alert.AlertType.INFORMATION, "Customer Updated Successfully").show();
           }else {
               new Alert(Alert.AlertType.ERROR, "Customer Not Updated").show();
           }
       } catch (SQLException e) {
           new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
       }
    }

    public void btnCusDeleteOnAction(ActionEvent actionEvent) {
        String id = txtCustomerId.getText();

        String sql = "DELETE FROM customer WHERE CustomerId =?";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,id);

            boolean isDeleted = pstm.executeUpdate() > 0;
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Deleted Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    public void txtSearchOnAction(ActionEvent actionEvent) {
        String id = txtCustomerId.getText();

        String sql = "SELECT * FROM customer WHERE CustomerId =?";

        try{
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,id);

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                String contact = resultSet.getString(4);
                String email = resultSet.getString(5);

                txtCustomerName.setText(name);
                txtCustomerAddress.setText(address);
                txtCustomerContact.setText(contact);
                txtCustomerEmail.setText(email);
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"Customer ID Not Found!").show();
        }
    }
}
