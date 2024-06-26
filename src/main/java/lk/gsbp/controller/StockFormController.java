package lk.gsbp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import lk.gsbp.model.Stock;
import lk.gsbp.model.stockDetails;
import lk.gsbp.model.tm.StockTm;
import lk.gsbp.repository.PlaceStock;
import lk.gsbp.repository.PlaceStockRepo;
import lk.gsbp.repository.StockDetailRepo;
import lk.gsbp.repository.StockRepo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class StockFormController {

    public TableColumn tblStockId;
    public TableColumn tblItemName;
    public TableColumn tblQTY;
    public TableView tblStock;
    @FXML
    private AnchorPane StockRoot;

    @FXML
    private TextField txtStokeId;

    @FXML
    private TextField txtCatogaryName;

    @FXML
    private TextField txtQTY;

    @FXML
    private TableColumn<?, ?> tblCatagoryName;

    @FXML
    private TextField txtItemName;

    public void initialize()  {
        setCellValueFactory();
        loadAllStocks();
    }

    private void loadAllStocks() {
        ObservableList<StockTm> StockList = FXCollections.observableArrayList();

        try {
            List<Stock> stockList = StockRepo.getAll();

            for (Stock stock : stockList) {
                StockTm stockTm = new StockTm(
                        stock.getStockId(),
                        stock.getItemName(),
                        stock.getCatogaryName(),
                        stock.getQTY()
                );
                StockList.add(stockTm);
                tblStock.setItems(StockList);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        tblStockId.setCellValueFactory(new PropertyValueFactory<>("StockId"));
        tblItemName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        tblCatagoryName.setCellValueFactory(new PropertyValueFactory<>("CatogaryName"));
        tblQTY.setCellValueFactory(new PropertyValueFactory<>("QTY"));

    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/View/dashboard_form.fxml"));
        Stage stage = (Stage) StockRoot.getScene().getWindow();

        stage.setScene(new Scene(rootNode));
        stage.setTitle("Gs Book Plaza Dashboard");
        stage.centerOnScreen();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        ClearFields();
    }

    private void ClearFields() {
        txtStokeId.setText("");
        txtCatogaryName.setText("");
        txtQTY.setText("");
        txtItemName.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String Id = txtStokeId.getText();

        String sql = "DELETE FROM Stock WHERE StockId =?";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,Id);

            boolean isDeleted = pstm.executeUpdate() > 0;
            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION, "Stock Deleted Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        if (isValied()){
        String stockId = txtStokeId.getText();
        String itemName = txtItemName.getText();
        String catogaryName = txtCatogaryName.getText();
        String QTY = txtQTY.getText();

        String sql = "INSERT INTO Stock VALUES (?,?,?,?)";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,stockId);
            pstm.setString(2,itemName);
            pstm.setString(3,catogaryName);
            pstm.setString(4,QTY);


            boolean isSaved = pstm.executeUpdate() > 0;
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Stock Saved Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Validation Failed");
            alert.setContentText("Please fill in all fields correctly.");
            alert.showAndWait();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String StockId = txtStokeId.getText();
        String itemName = txtItemName.getText();
        String catogaryName = txtCatogaryName.getText();
        String QTY = txtQTY.getText();

        String sql = "UPDATE Stock SET ItemName =?, CatogaryName =?, QTY =? WHERE StockId =?";

        try {
            boolean isUpdate = StockRepo.update2(StockId,itemName,catogaryName,QTY);
            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Stock Updated Successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Stock Not Updated").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void txtStockeSearchOnAction(ActionEvent actionEvent) throws SQLException {
        String Id = txtStokeId.getText();

        String sql = "SELECT * FROM Stock WHERE StockId =?";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, Id);

            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()) {
                String catagaryName = resultSet.getString(2);
                String QTY = resultSet.getString(3);
                String itemName = resultSet.getString(4);

                txtCatogaryName.setText(resultSet.getString("CatogaryName"));
                txtQTY.setText(resultSet.getString("QTY"));
                txtItemName.setText(resultSet.getString("ItemName"));
            } else {
                new Alert(Alert.AlertType.ERROR, "Stock not found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION, "Stock Id not found").show();
        }
    }

    public boolean isValied(){
        boolean idValid = Regex.setTextColor(lk.gsbp.Utill.TextField.IDS, txtStokeId);
        boolean nameValid = Regex.setTextColor(lk.gsbp.Utill.TextField.NAME, txtItemName);
        boolean catogaryValid = Regex.setTextColor(lk.gsbp.Utill.TextField.NAME, txtCatogaryName);
        boolean QTYValid = Regex.setTextColor(lk.gsbp.Utill.TextField.QTY, txtQTY);

        return idValid && nameValid && catogaryValid && QTYValid;
    }
    public void StockIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.gsbp.Utill.TextField.IDS, txtStokeId);
    }

    public void CatagaryNameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.gsbp.Utill.TextField.NAME, txtCatogaryName);
    }

    public void QtyOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.gsbp.Utill.TextField.QTY, txtQTY);
    }

    public void NameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.gsbp.Utill.TextField.NAME, txtItemName);
    }
}
