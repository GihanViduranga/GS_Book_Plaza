package lk.gsbp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class DashboardFormController {
    @FXML
    private AnchorPane rootNode;

    @FXML
    private Label lblCustomarCount;

    @FXML
    private Label lblOrderCount;
    public void btnCustomerOnAction(ActionEvent actionEvent) {
        
    }

    public void btnEmployeeOnAction(ActionEvent actionEvent) {
        
    }

    public void btnItemOnAction(ActionEvent actionEvent) {
        
    }

    public void btnStockeOnAction(ActionEvent actionEvent) {
        
    }

    public void btnSupplierOnAction(ActionEvent actionEvent) {

    }

    public void btnLogoutOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/View/Login_form.fxml"));

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("GS Book Plaza Login");

    }

    public void btnDeliveryOnAction(ActionEvent actionEvent) {

    }
}
