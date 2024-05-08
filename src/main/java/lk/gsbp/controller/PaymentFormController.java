package lk.gsbp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lk.gsbp.db.DbConnection;
import lk.gsbp.repository.PaymentRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class PaymentFormController {

    public Label lblDate;
    @FXML
    private TextField txtPaymentId;

    @FXML
    private TextField txtPaymentMethod;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtPayment;

    @FXML
    private TableView<?> tblPayment;

    @FXML
    private TableColumn<?, ?> colPaymentID;

    @FXML
    private TableColumn<?, ?> colPaymentMethod;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colPayment;

    @FXML
    void btnPayClearOnAction(ActionEvent event) {
        ClearFields();
    }

    private void ClearFields() {
        txtPaymentId.setText("");
        txtPaymentMethod.setText("");
        txtDate.setText("");
        txtPayment.setText("");
    }
    public void initialize(){
        SetDate();
    }

    @FXML
    void btnPayDeleteOnAction(ActionEvent event) {
        String Id = txtPaymentId.getText();

        String sql = "DELETE FROM payment WHERE PaymentId =?";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,Id);

            boolean isDeleted = pstm.executeUpdate() > 0;

            if(isDeleted){
                new Alert(Alert.AlertType.INFORMATION, "Payment Deleted Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    @FXML
    public void txtPaymentSearchOnAction(ActionEvent actionEvent) {
        String id = txtPaymentId.getText();

        String sql = "SELECT * FROM payment WHERE PaymentId =?";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,id);

            ResultSet resultSet = pstm.executeQuery();

            if(resultSet.next()){
                String PaymentId = resultSet.getString(1);
                String PaymentMethod = resultSet.getString(2);
                String Date = resultSet.getString(3);
                String Payment = resultSet.getString(4);

                txtPaymentId.setText(PaymentId);
                txtPaymentMethod.setText(PaymentMethod);
                txtDate.setText(Date);
                txtPayment.setText(Payment);
            } else {
                new Alert(Alert.AlertType.ERROR, "Payment not found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION, e.getMessage()).show();
        }
    }
    private void SetDate(){
        LocalDate Date = LocalDate.now();
        lblDate.setText(String.valueOf(Date));
    }
}
