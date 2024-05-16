package lk.gsbp.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.gsbp.Utill.Regex;
import lk.gsbp.db.DbConnection;
import lk.gsbp.model.Customer;
import lk.gsbp.model.Item;
import lk.gsbp.model.Stock;
import lk.gsbp.model.tm.CustomerTm;
import lk.gsbp.model.tm.ItemTm;
import lk.gsbp.repository.CustomerRepo;
import lk.gsbp.repository.ItemRepo;
import lk.gsbp.repository.OrderRepo;
import lk.gsbp.repository.StockRepo;

import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemFormController {

        @FXML
        public ComboBox <String> cmbStocId;
        @FXML
        private AnchorPane ItemRoot;

        @FXML
        private TextField txtId;

        @FXML
        private TextField txtQTY;

        @FXML
        private TextField txtName;

        @FXML
        private TextField txtPrice;

        @FXML
        private TableView<ItemTm> tblItemTable;

        @FXML
        private TableColumn<?, ?> tblID;

        @FXML
        private TableColumn<?, ?> tblName;

        @FXML
        private TableColumn<?, ?> tblQTY;

        @FXML
        private TableColumn<?, ?> tblUnitPrice;

       /* @FXML
        private Label lblStockId;*/


    public void initialize() {
        setCellValueFactory();
        loadAllItems();
        setStockId();
        getCurrentOrderId();
    }

    private void loadAllItems() {
        ObservableList<ItemTm> ItemList = FXCollections.observableArrayList();

        try {
            List<Item> items = ItemRepo.getAll();

            for (Item item : items) {
                ItemTm itemTm = new ItemTm(
                        item.getItemsId(),
                        item.getItemName(),
                        item.getQTY(),
                        item.getUnitPrice()
                );
                ItemList.add(itemTm);
                tblItemTable.setItems(ItemList);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        tblID.setCellValueFactory(new PropertyValueFactory<>("ItemsId"));
        tblName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        tblQTY.setCellValueFactory(new PropertyValueFactory<>("QTY"));
        tblUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
    }

    @FXML
        void btnBackOnAction(ActionEvent event) throws IOException {
            AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/View/dashboard_form.fxml"));
            Stage stage = (Stage) ItemRoot.getScene().getWindow();

            stage.setScene(new Scene(rootNode));
            stage.setTitle("Gs Book Plaza Dashboard");
            stage.centerOnScreen();
        }

        @FXML
        void btnClearOnAction(ActionEvent event) {
            ClearFields();
        }
    private void ClearFields() {
        txtId.setText("");
        txtQTY.setText("");
        txtName.setText("");
        txtPrice.setText("");
    }

        @FXML
        void btnDeleteOnAction(ActionEvent event) {
            String id = txtId.getText();

            String sql = "DELETE FROM Items WHERE ItemsId =?";

            try {
                Connection connection = DbConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(sql);

                pstm.setString(1,id);

                boolean isDeleted = pstm.executeUpdate() > 0;
                if (isDeleted){
                    new Alert(Alert.AlertType.INFORMATION, "Item Deleted Successfully").show();
                    ClearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

        }

        @FXML
        void btnSaveOnAction(ActionEvent event) {
            if (isValied()){
            String id = txtId.getText();
            String qty = txtQTY.getText();
            String name = txtName.getText();
            String price = txtPrice.getText();
            String stockId = cmbStocId.getSelectionModel().getSelectedItem();

            String sql = "INSERT INTO Items (ItemsId,ItemName,QTY,UnitPrice,StockId) VALUES(?,?,?,?,?)";

            try{
                Connection connection = DbConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(sql);

                pstm.setString(1,id);
                pstm.setString(2,name);
                pstm.setString(3,qty);
                pstm.setString(4,price);
                pstm.setString(5,stockId);


                boolean isSaved = pstm.executeUpdate() > 0;
                if (isSaved){
                    new Alert(Alert.AlertType.INFORMATION, "Item Saved Successfully").show();
                    ClearFields();
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
            String Id = txtId.getText();
            String Name = txtName.getText();
            String Qty = txtQTY.getText();
            String Price = txtPrice.getText();
            String stockId = cmbStocId.getSelectionModel().getSelectedItem();

            String sql = "UPDATE items SET ItemName =?, QTY =?, UnitPrice =? WHERE ItemsId =?";

            try {
                boolean isUpdate = ItemRepo.Update2(Id, Name, Qty, Price,stockId);
                if (isUpdate) {
                    new Alert(Alert.AlertType.INFORMATION, "Item updated").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Item is Not updated").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

        }

        @FXML
        void txtItemSearchOnAction(ActionEvent event) {
            String id = txtId.getText();

            String sql = "SELECT * FROM Items WHERE ItemsId =?";

            try {
                Connection connection = DbConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(sql);

                pstm.setString(1, id);

                ResultSet resultSet = pstm.executeQuery();
                if (resultSet.next()) {
                    String name = resultSet.getString(2);
                    String qty = resultSet.getString(3);
                    String price = resultSet.getString(4);

                    txtName.setText(name);
                    txtQTY.setText(qty);
                    txtPrice.setText(price);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Item not found").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.INFORMATION, "Item Id not found").show();
            }

        }
    public boolean isValied(){
        boolean idValid = Regex.setTextColor(lk.gsbp.Utill.TextField.IDI, txtId);
        boolean nameValid = Regex.setTextColor(lk.gsbp.Utill.TextField.NAME, txtName);
        boolean qtyValid = Regex.setTextColor(lk.gsbp.Utill.TextField.QTY, txtQTY);

        return idValid && nameValid && qtyValid;

    }

    public void ItemIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.gsbp.Utill.TextField.IDI, txtId);
    }

    public void QTYOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.gsbp.Utill.TextField.QTY, txtQTY);
    }

    public void NameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.gsbp.Utill.TextField.NAME, txtName);
    }

    public void cmbStockIdOnAction(ActionEvent actionEvent) {
        String id = cmbStocId.getValue();

        try {
            Stock stock = StockRepo.searchById(id);
            if (stock != null) {
                txtName.setText(stock.getItemName());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void setStockId() {
        ObservableList<String> StockIdList = FXCollections.observableArrayList();

        try {
            List<String> stockList = StockRepo.getAllStock();

            for (String stock : stockList) {
                StockIdList.add(stock);
            }
            cmbStocId.setItems(StockIdList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCurrentOrderId() {
        try {
            String itemId = ItemRepo.GetItemIds();

            String nextItemId = generateNextAssestId();
            txtId.setText(nextItemId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String generateNextAssestId() throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();

        String sql = "SELECT ItemsId FROM items ORDER BY ItemsId DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        if(resultSet.next()) {
            return splitItemId(resultSet.getString(1));
        }
        return splitItemId(null);
    }

    private static String splitItemId(String string) {
        if(string != null) {
            String[] strings = string.split("I0");
            int id = Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if (length < 2){
                return "I00"+id;
            }else {
                if (length < 3){
                    return "I0"+id;
                }else {
                    return "I"+id;
                }
            }
        }
        return "I001";
    }
}
