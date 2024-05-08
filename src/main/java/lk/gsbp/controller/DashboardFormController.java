package lk.gsbp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
    public AnchorPane PerentrootNode;
    public AnchorPane childRoot;


    public void btnCustomerOnAction(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/customer_form.fxml"));
        Parent PerentrootNode = null;
        try {
            PerentrootNode = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        childRoot.getChildren().clear();
        childRoot.getChildren().add(PerentrootNode);
    }

    public void btnEmployeeOnAction(ActionEvent actionEvent)  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/employee_form.fxml"));
        Parent PerentrootNode = null;
        try {
            PerentrootNode = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        childRoot.getChildren().clear();
        childRoot.getChildren().add(PerentrootNode);
    }

    public void btnItemOnAction(ActionEvent actionEvent)  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/item_form.fxml"));
        Parent PerentrootNode = null;
        try {
            PerentrootNode = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        childRoot.getChildren().clear();
        childRoot.getChildren().add(PerentrootNode);
    }

    public void btnStockOnAction(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/stock_form.fxml"));
        Parent PerentrootNode = null;
        try {
            PerentrootNode = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        childRoot.getChildren().clear();
        childRoot.getChildren().add(PerentrootNode);
    }

    public void btnSupplierOnAction(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/supplier_form.fxml"));
        Parent PerentrootNode = null;
        try {
            PerentrootNode = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        childRoot.getChildren().clear();
        childRoot.getChildren().add(PerentrootNode);
    }
    public void btnDeliveryOnAction(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/delivery_form.fxml"));
        Parent PerentrootNode = null;
        try {
            PerentrootNode = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        childRoot.getChildren().clear();
        childRoot.getChildren().add(PerentrootNode);
    }
    public void btnLogoutOnAction(ActionEvent actionEvent) {
        AnchorPane PerentrootNode = null;
        try {
            PerentrootNode = FXMLLoader.load(this.getClass().getResource("/View/Login_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(PerentrootNode);

        Stage stage = (Stage) this.PerentrootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login Form");

    }

    public void btnOrdersOnAction(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/order_form.fxml"));
        Parent PerentrootNode = null;
        try {
            PerentrootNode = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        childRoot.getChildren().clear();
        childRoot.getChildren().add(PerentrootNode);
    }

    public void btnRegisterOnAction(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/registration_form.fxml"));
        Parent PerentrootNode = null;
        try {
            PerentrootNode = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        childRoot.getChildren().clear();
        childRoot.getChildren().add(PerentrootNode);
    }

    public void btnPaymentOnAction(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/payment_form.fxml"));
        Parent PerentrootNode = null;
        try {
            PerentrootNode = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        childRoot.getChildren().clear();
        childRoot.getChildren().add(PerentrootNode);
    }
}
