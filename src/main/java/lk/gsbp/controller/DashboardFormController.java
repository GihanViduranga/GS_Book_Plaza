package lk.gsbp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class DashboardFormController {
    public Label lblCustomerCount;
    public Label lblOrderCount;
    public BarChart chrOrderProgress;
    @FXML
    private AnchorPane rootNode;

    public void btnCustomerOnAction(ActionEvent actionEvent) {
        AnchorPane anchorPane = null;
        try {
            anchorPane = FXMLLoader.load(getClass().getResource("/View/customer_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Customer Management Form");
        stage.centerOnScreen();
    }

    public void btnEmployeeOnAction(ActionEvent actionEvent)  {
        AnchorPane anchorPane = null;
        try {
            anchorPane = FXMLLoader.load(getClass().getResource("/View/employee_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Employee Management Form");
        stage.centerOnScreen();
    }

    public void btnItemOnAction(ActionEvent actionEvent)  {
        AnchorPane anchorPane = null;
        try {
            anchorPane = FXMLLoader.load(getClass().getResource("/View/item_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Item Management Form");
        stage.centerOnScreen();
    }

    public void btnStockOnAction(ActionEvent actionEvent) {
        AnchorPane anchorPane = null;
        try {
            anchorPane = FXMLLoader.load(getClass().getResource("/View/stock_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Item Management Form");
        stage.centerOnScreen();
    }

    public void btnSupplierOnAction(ActionEvent actionEvent) {
        AnchorPane anchorPane = null;
        try {
            anchorPane = FXMLLoader.load(getClass().getResource("/View/supplier_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Supplier Management Form");
        stage.centerOnScreen();
    }
    public void btnDeliveryOnAction(ActionEvent actionEvent) {
        AnchorPane anchorPane = null;
        try {
            anchorPane = FXMLLoader.load(getClass().getResource("/View/delivery_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Delivery Management Form");
        stage.centerOnScreen();
    }
    public void btnLogoutOnAction(ActionEvent actionEvent) {
        AnchorPane rootNode = null;
        try {
            rootNode = FXMLLoader.load(this.getClass().getResource("/View/Login_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("GS Book Plaza Login");

    }

    public void btnOrdersOnAction(ActionEvent actionEvent) {
        AnchorPane anchorPane = null;
        try {
            anchorPane = FXMLLoader.load(getClass().getResource("/View/order_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) rootNode.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Order Management Form");
        stage.centerOnScreen();
    }
}
