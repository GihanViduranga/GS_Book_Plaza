package lk.gsbp.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.gsbp.model.Customer;

import java.io.IOException;

public class CustomerFormController {

    public AnchorPane root;
    public TextField txtCustomerId;
    public TextField txtCustomerAddress;
    public TextField txtCustomerContact;
    public TextField txtCustomerName;
    public TextField txtCustomerEmail;
    public TableColumn colCustomerName;
    public TableColumn colCustomerAddress;
    public TableColumn colCustomerContact;
    public TableColumn colCustomerEmail;


    public void btnCusSaveOnAction(ActionEvent actionEvent) {
        String id = txtCustomerId.getText();
        String name = txtCustomerName.getText();
        String address = txtCustomerAddress.getText();
        String contact = txtCustomerContact.getText();
        String email = txtCustomerEmail.getText();

        Customer customer = new Customer(id, name, address, contact, email);
        //thawa methanata danna tinawa
    }

    public void btnCusClearOnAction(ActionEvent actionEvent) {

    }

    public void btnCusUpdateOnAction(ActionEvent actionEvent) {

    }

    public void btnCusDeleteOnAction(ActionEvent actionEvent) {

    }

    public void btnCusBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/View/dashboard_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(rootNode));
        stage.setTitle("Gs Book Plaza Dashboard");
        stage.centerOnScreen();
    }
}
