package lk.gsbp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeFormController {

    public AnchorPane EmployeeRoot;
    public TextField txtID;
    public TextField txtAddress;
    public TextField txtJobStartDate;
    public TextField txtName;
    public TextField txtContact;
    public TextField txtSalary;
    public TextField txtPosition;
    public TableColumn tblName;
    public TableColumn tblAddress;
    public TableColumn tblContact;
    public TableColumn tblStartDate;
    public TableColumn tblSalary;
    public TableColumn tblPosition;

    public void btnSaveOnAction(ActionEvent actionEvent) {

    }

    public void btnClearOnAction(ActionEvent actionEvent) {

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {

    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/View/dashboard_form.fxml"));
        Stage stage = (Stage) EmployeeRoot.getScene().getWindow();

        stage.setScene(new Scene(rootNode));
        stage.setTitle("Gs Book Plaza Dashboard");
        stage.centerOnScreen();
    }
}
