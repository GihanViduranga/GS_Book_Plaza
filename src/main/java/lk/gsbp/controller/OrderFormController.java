package lk.gsbp.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class OrderFormController {

    @FXML
    private AnchorPane root;

    @FXML
    private TableColumn<?, ?> colItemID;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colQTY;

    @FXML
    private TableColumn<?, ?> colTotalPrice;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TextField txtDeliveryId;

    @FXML
    private JFXComboBox<?> cmbCustomerID;

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
    private JFXComboBox<?> cmbItemID;

    @FXML
    private Label lblNetTotal;

    @FXML
    void btnAddNewCustomerOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {

    }

    @FXML
    void btnBackOnAction(ActionEvent event) {

    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

    }

    @FXML
    void cmbCustomerIdOnAction(ActionEvent event) {

    }

}
