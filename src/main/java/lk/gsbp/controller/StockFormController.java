package lk.gsbp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class StockFormController {

    @FXML
    private AnchorPane StockRoot;

    @FXML
    private TextField txtStokeId;

    @FXML
    private TextField txtCatogaryName;

    @FXML
    private TextField txtQTY;

    @FXML
    private TableColumn<?, ?> tblCostPrice;

    @FXML
    private TableColumn<?, ?> tblQTY;

    @FXML
    private TableColumn<?, ?> tblCatagoryName;

    @FXML
    private TableColumn<?, ?> tblSellingPrice;

    @FXML
    private TextField txtItemName;

    @FXML
    void btnBackOnAction(ActionEvent event) {

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

}