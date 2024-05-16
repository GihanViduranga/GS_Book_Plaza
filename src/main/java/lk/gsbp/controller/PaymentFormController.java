package lk.gsbp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.gsbp.Utill.Regex;
import lk.gsbp.db.DbConnection;
import lk.gsbp.model.Order;
import lk.gsbp.model.Payment;
import lk.gsbp.model.tm.PaymentTm;
import lk.gsbp.repository.ItemRepo;
import lk.gsbp.repository.OrderRepo;
import lk.gsbp.repository.PaymentRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PaymentFormController {

    @FXML
    public Label lblDate;
    @FXML
    public ComboBox<String> cmbOrderId;
    @FXML
    private TextField txtPaymentId;

    @FXML
    private TextField txtPaymentMethod;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtPayment;

    @FXML
    private TableView<PaymentTm> tblPayment;

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
        setCellValueFactory();
        loadAllPayments();
        SetDate();
        setPayment();
        getCurrentOrderId();
    }

    private void setCellValueFactory() {
        colPaymentID.setCellValueFactory(new PropertyValueFactory<>("PaymentId"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("PaymentMethod"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        colPayment.setCellValueFactory(new PropertyValueFactory<>("Payment"));
    }

    private void loadAllPayments() {
        ObservableList<PaymentTm> Object = FXCollections.observableArrayList();

        try {
            List<Payment> payList = PaymentRepo.getAllPayments();

            for (Payment payment : payList) {
                PaymentTm paymentTm = new PaymentTm(
                        payment.getPaymentId(),
                        payment.getPaymentMethod(),
                        payment.getDate(),
                        payment.getPayment()
                );
                Object.add(paymentTm);
                tblPayment.setItems(Object);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
                lblDate.setText(Date);
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
    public boolean isValied(){

        boolean IdValid = Regex.setTextColor(lk.gsbp.Utill.TextField.IDP, txtPaymentId);
        boolean MethodValid = Regex.setTextColor(lk.gsbp.Utill.TextField.METHOD, txtPaymentMethod);
        boolean PaymentValid = Regex.setTextColor(lk.gsbp.Utill.TextField.AMOUNT, txtPayment);

        return IdValid && MethodValid && PaymentValid;
    }
    public void PaymentIDOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.gsbp.Utill.TextField.IDP, txtPaymentId);
    }

    public void MethodeOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.gsbp.Utill.TextField.METHOD, txtPaymentMethod);
    }

    public void PaymentOnKeyRelesed(KeyEvent keyEvent) {
        Regex.setTextColor(lk.gsbp.Utill.TextField.AMOUNT, txtPayment);
    }

    public void btnPaySaveOnAction(ActionEvent actionEvent) throws SQLException {
        if (isValied()){
        String PaymentId = txtPaymentId.getText();
        String PaymentMethod = txtPaymentMethod.getText();
        String Date = lblDate.getText();
        String Payment = txtPayment.getText();

        PaymentRepo paymentRepo = new PaymentRepo();

        boolean isSaved = paymentRepo.save(PaymentId,PaymentMethod,Date,Payment);

        if(isSaved){
            new Alert(Alert.AlertType.INFORMATION, "Payment Saved Successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Payment Not Saved").show();
        }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Validation Failed");
            alert.setContentText("Please fill in all fields correctly.");
            alert.showAndWait();
        }
    }

    public void setPayment(){
        ObservableList<String> OrderIdList = FXCollections.observableArrayList();

        try {
            List<String> orderList = OrderRepo.getAllOrders();
            for (String order : orderList) {
                OrderIdList.add(order);
            }
            cmbOrderId.setItems(OrderIdList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbOrderIdOnAction(ActionEvent actionEvent) {
        String id = cmbOrderId.getValue();

        try {
            Order order = OrderRepo.searchById(id);
            if (order != null) {
                txtPayment.setText(String.valueOf(order.getNetTotal()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getCurrentOrderId() {
        try {
            String PaymentId = PaymentRepo.GetPaymentId();

            String nextPaymentId = generateNextAssestId();
            txtPaymentId.setText(nextPaymentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String generateNextAssestId() throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();

        String sql = "SELECT PaymentId FROM payment ORDER BY PaymentId DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        if(resultSet.next()) {
            return splitItemId(resultSet.getString(1));
        }
        return splitItemId(null);
    }

    private static String splitItemId(String string) {
        if(string != null) {
            String[] strings = string.split("P0");
            int id = Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if (length < 2){
                return "P00"+id;
            }else {
                if (length < 3){
                    return "P0"+id;
                }else {
                    return "P"+id;
                }
            }
        }
        return "P001";
    }
}
