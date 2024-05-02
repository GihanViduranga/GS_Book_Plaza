package lk.gsbp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ItemFormController {

    public AnchorPane ItemRoot;
    public TextField txtId;
    public TextField txtQTY;
    public TextField txtName;
    public TextField txtPrice;
    public TableColumn tblID;
    public TableColumn tblName;
    public TableColumn tblQTY;
    public TableColumn tblUnitPrice;

    public void btnClearOnAction(ActionEvent actionEvent) {

    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/View/dashboard_form.fxml"));
        Stage stage = (Stage) ItemRoot.getScene().getWindow();

        stage.setScene(new Scene(rootNode));
        stage.setTitle("Gs Book Plaza Dashboard");
        stage.centerOnScreen();
    }
}
