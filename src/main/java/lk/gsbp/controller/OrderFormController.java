package lk.gsbp.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.gsbp.db.DbConnection;
import lk.gsbp.model.Customer;
import lk.gsbp.model.Item;
import lk.gsbp.model.Order;
import lk.gsbp.model.tm.CartTm;
import lk.gsbp.repository.*;
import lk.gsbp.model.orderDetails;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class OrderFormController {
    @FXML
    public TableColumn colItemID;
    @FXML
    public TableColumn colItemName;
    @FXML
    public TableColumn colUnitPrice;
    @FXML
    public TableColumn colQTY;
    @FXML
    public TableColumn colTotalPrice;
    @FXML
    public TableColumn colAction;
    @FXML
    private Label lblTotalAmount;
    @FXML
    private TableView<CartTm> tblOrderCart;

    @FXML
    private AnchorPane root;



    @FXML
    private JFXComboBox <String> cmbCustomerID;

    @FXML
    private Label lblOrderID;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblItemName;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private Label lblQuentityOnHand;

    @FXML
    private TextField txtQTY;

    @FXML
    private JFXComboBox<String> cmbItemID;

    @FXML
    private Label lblNetTotal;

    private static int idCounter = 0;

    private ObservableList<CartTm> obList = FXCollections.observableArrayList();

    public void initialize(){
        setCellValueFactory();
        getCurrentOrderId();
        SetDate();
        setCustomerId();
        setItemID();
    }


    private void setCellValueFactory() {
        colItemID.setCellValueFactory(new PropertyValueFactory<>("ItemId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("QTY"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("TotalAmount"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("Action"));

    }

    private void getCurrentOrderId() {
        try {
            String orderId = OrderRepo.GetOrderId();

            String nextOrderId = generateNextAssestId();
            lblOrderID.setText(nextOrderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String generateNextAssestId() throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();

        String sql = "SELECT OrderId FROM orders ORDER BY OrderId DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        if(resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String string) {
        if(string != null) {
            String[] strings = string.split("O0");
            int id = Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if (length < 2){
                return "O00"+id;
            }else {
                if (length < 3){
                    return "O0"+id;
                }else {
                    return "O"+id;
                }
            }
        }
        return "O001";
    }

    private void setItemID() {
        ObservableList<String> oblist = FXCollections.observableArrayList();
        try {
            List<String> itemList = ItemRepo.getItemID();
            for (String item : itemList) {
                oblist.add(item);
            }
            cmbItemID.setItems(oblist);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddNewCustomerOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/View/customer_form.fxml"));

        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Customer Form");
        stage.show();

    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String ItemId = cmbItemID.getValue();
        String ItemName = lblItemName.getText();
        int UnitPrice = Integer.parseInt(lblUnitPrice.getText());
        int QTY = Integer.parseInt(txtQTY.getText());
        int TotalPrice = QTY * UnitPrice;
        JFXButton Action = new JFXButton("Remove");
        Action.setCursor(Cursor.HAND);


        Action.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION,"Are you sure to remove", yes, no).showAndWait();
            if (type.orElse(no) == yes){
                int selectIndex = tblOrderCart.getSelectionModel().getSelectedIndex();
                obList.remove(selectIndex);

                tblOrderCart.refresh();
                calculateNetTotal();
            }

        });
        if (!obList.isEmpty()){
            for (int i = 0; i < tblOrderCart.getItems().size(); i++){
                if (ItemId.equals(colItemID.getCellData(i))) {


                    QTY = QTY + (int) colQTY.getCellData(i);
                    TotalPrice += QTY * UnitPrice;

                    obList.get(i).setQTY(QTY);
                    obList.get(i).setTotalAmount(TotalPrice);

                    tblOrderCart.refresh();

                    calculateNetTotal();
                    return;
                }
            }
        }
        CartTm tm = new CartTm(ItemId,ItemName,UnitPrice,QTY,TotalPrice,Action);
        obList.add(tm);

        tblOrderCart.setItems(obList);
        calculateNetTotal();
        txtQTY.setText("");

    }

    private void calculateNetTotal() {
        double netTotal = 0.0;
        for (int i = 0; i <tblOrderCart.getItems().size(); i++) {
            netTotal = netTotal + (double) colTotalPrice.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/View/dashboard_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(rootNode));
        stage.setTitle("Gs Book Plaza Dashboard");
        stage.centerOnScreen();
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) throws SQLException, JRException {
        String orderId = lblOrderID.getText();
        String orderDate = lblOrderDate.getText();
        String customerId = cmbCustomerID.getValue();
        double total = Double.parseDouble(lblNetTotal.getText());

        System.out.println(total);

        var order = new Order(orderId, orderDate, customerId, total);

        List<orderDetails> odList = new ArrayList<>();

        for (int i = 0;i < tblOrderCart.getItems().size(); i++) {
            CartTm tm = obList.get(i);
            orderDetails orderDetails = new orderDetails(

                    tm.getItemId(),
                    orderId,
                    tm.getQTY(),
                    tm.getUnitPrice());
            odList.add(orderDetails);
        }
        PlaceOrder po = new PlaceOrder(order, odList);
        boolean isPlaceOrder = PlaceOrderRepo.placeOrder(po);

        if(isPlaceOrder){
            new Alert(Alert.AlertType.CONFIRMATION, "Order pleased").show();
            PrintBill();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order pleased Unsuccessful").show();
        }

    }

    @FXML
    void cmbCustomerIdOnAction(ActionEvent event) {
        setCustomerId();

        String customerId = cmbCustomerID.getValue();

        try {
            Customer customer = CustomerRepo.searchByCustomerId(customerId);
            if (customer!= null) {
                lblCustomerName.setText(customer.getName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCustomerId() {
        ObservableList<String> custIdList = FXCollections.observableArrayList();

        try{
            List<String> idList = CustomerRepo.getIds();
            for (String id : idList) {
                custIdList.add(id);
            }

            cmbCustomerID.setItems(custIdList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbItemIdOnAction(ActionEvent actionEvent) throws SQLException {
        String Id = cmbItemID.getValue();

        try {
            Item item = ItemRepo.searchById(Id);
            if (item != null) {
                lblItemName.setText(item.getItemName());
                lblUnitPrice.setText(String.valueOf(item.getUnitPrice()));
                lblQuentityOnHand.setText(String.valueOf(item.getQTY()));
            }
            txtQTY.requestFocus();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void SetDate(){
        LocalDate Date = LocalDate.now();
        lblOrderDate.setText(String.valueOf(Date));
    }
    public void setTotal() {

    }

    public void btnBillOnAction(ActionEvent actionEvent) throws JRException {
        JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/Reports/OrderReportFoeFinal.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        Map<String, Object> data = new HashMap<>();
        data.put("OrderId", lblOrderID.getText());

        JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, data, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);

    }

    public void PrintBill() throws JRException, SQLException {

    }
}
