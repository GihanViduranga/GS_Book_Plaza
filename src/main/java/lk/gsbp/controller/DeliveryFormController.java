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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.gsbp.Utill.Regex;
import lk.gsbp.db.DbConnection;
import lk.gsbp.model.Delivery;
import lk.gsbp.model.tm.DeliveryTm;
import lk.gsbp.repository.DeliveryRepo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DeliveryFormController {

    public AnchorPane deliveryRootNode;
    public TextField txtDeliveryId;
    public TextField txtDate;
    public TextField txtAddress;
    public TableColumn tblDeliveryId;
    public TableColumn tblAddress;
    public TableColumn tblDate;
    public TableColumn tblStetus;
    public TextField txtStatus;
    public TableView tblDelivery;
    public TextField txtDeliverName;
    public TableColumn tblDeliverName;

    public void btnSaveOnAction(ActionEvent actionEvent) throws SQLException {
        if (isValied()) {
            String Id = txtDeliveryId.getText();
            String Deliver = txtDeliverName.getText();
            String Date = txtDate.getText();
            String Address = txtAddress.getText();
            String Status = txtStatus.getText();

            String sql = "INSERT INTO Delivery VALUES (?,?,?,?,?)";

            try {
                Connection connection = DbConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(sql);

                pstm.setString(1, Id);
                pstm.setString(2, Deliver);
                pstm.setString(3, Date);
                pstm.setString(4, Address);
                pstm.setString(5, Status);

                boolean isSaved = pstm.executeUpdate() > 0;
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Delivery Saved Successfully").show();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Validation Failed");
            alert.setContentText("Please fill in all fields correctly.");
            alert.showAndWait();
        }
    }
    public void initialize() {
        setCellValueFactory();
        loadAllDeliveries();
    }

    private void loadAllDeliveries() {
        ObservableList<DeliveryTm> objects = FXCollections.observableArrayList();

        try{
            List<Delivery> deliveryList = DeliveryRepo.getAllDelivery();

            for (Delivery delivery : deliveryList) {
                DeliveryTm deliveryTm = new DeliveryTm(
                        delivery.getDeliveryId(),
                        delivery.getDeliverName(),
                        delivery.getDate(),
                        delivery.getAddress(),
                        delivery.getStetus()
                );
                objects.add(deliveryTm);
                tblDelivery.setItems(objects);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        tblDeliveryId.setCellValueFactory(new PropertyValueFactory<>("DeliveryId"));
        tblDeliverName.setCellValueFactory(new PropertyValueFactory<>("DeliverName"));
        tblAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        tblDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        tblStetus.setCellValueFactory(new PropertyValueFactory<>("Stetus"));
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        txtDeliveryId.setText("");
        txtDeliverName.setText("");
        txtDate.setText("");
        txtAddress.setText("");
        txtStatus.setText("");
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String DeliveryId = txtDeliveryId.getText();
        String DeliverName = txtDeliverName.getText();
        String Date = txtDate.getText();
        String Address = txtAddress.getText();
        String Stetus = txtStatus.getText();

        String sql = "UPDATE Delivery SET DeliverName = ?, Date =?, Address =?, Stetus =? WHERE DeliveryId =?";

        try{
            boolean isUpdate = DeliveryRepo.update2(DeliveryId, DeliverName, Date, Address, Stetus);
            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Delivery Updated Successfully").show();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Delivery Not Updated").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException {
        String DeliveryId = txtDeliveryId.getText();

        String sql = "DELETE FROM Delivery WHERE DeliveryId =?";

        try{
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, DeliveryId);

            boolean isDelete = pstm.executeUpdate() > 0;
            if (isDelete) {
                new Alert(Alert.AlertType.INFORMATION, "Delivery Deleted Successfully").show();
                clearFields();
            }
        } catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/View/dashboard_form.fxml"));
        Stage stage = (Stage) deliveryRootNode.getScene().getWindow();

        stage.setScene(new Scene(rootNode));
        stage.setTitle("Gs Book Plaza Dashboard");
        stage.centerOnScreen();
    }

    public void txtDeliverySearchOnAction(ActionEvent actionEvent) throws SQLException {
        String DeliveryId = txtDeliveryId.getText();

        String sql = "SELECT * FROM Delivery WHERE DeliveryId =?";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, DeliveryId);

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String DeliverName = resultSet.getString(2);
                String Date = resultSet.getString(3);
                String Address = resultSet.getString(4);
                String Status = resultSet.getString(5);

                txtDeliverName.setText(DeliverName);
                txtDate.setText(Date);
                txtAddress.setText(Address);
                txtStatus.setText(Status);
            } else {
                new Alert(Alert.AlertType.ERROR, "Delivery Not Found").show();
            }
        } catch (SQLException e){
            new Alert(Alert.AlertType.INFORMATION, "Delivery ID Not Found").show();
        }
    }

    public boolean isValied(){

        boolean nameValid = Regex.setTextColor(lk.gsbp.Utill.TextField.NAME, txtDeliverName);
        boolean idValid = Regex.setTextColor(lk.gsbp.Utill.TextField.IDD, txtDeliveryId);
        boolean AddressValid = Regex.setTextColor(lk.gsbp.Utill.TextField.ADDRESS, txtAddress );
        boolean dateValid = Regex.setTextColor(lk.gsbp.Utill.TextField.DATE, txtDate);

        return nameValid && idValid && AddressValid && dateValid;
    }
    public void DateOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.gsbp.Utill.TextField.DATE, txtDate);
    }

    public void AddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.gsbp.Utill.TextField.ADDRESS, txtAddress);
    }

    public void DeliveryIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.gsbp.Utill.TextField.IDD, txtDeliveryId);
    }

    public void DeliverNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.gsbp.Utill.TextField.NAME, txtDeliverName);
    }
}
