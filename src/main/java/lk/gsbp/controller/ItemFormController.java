package lk.gsbp.controller;

import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.gsbp.db.DbConnection;
import lk.gsbp.model.Customer;
import lk.gsbp.model.Item;
import lk.gsbp.model.tm.CustomerTm;
import lk.gsbp.model.tm.ItemTm;
import lk.gsbp.repository.CustomerRepo;
import lk.gsbp.repository.ItemRepo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemFormController {

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

        @FXML
        private TextField txtStockID;

    public void initialize() {
        setCellValueFactory();
        loadAllItems();
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
        txtStockID.setText("");
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
            String id = txtId.getText();
            String qty = txtQTY.getText();
            String name = txtName.getText();
            String price = txtPrice.getText();

            String sql = "INSERT INTO Items (ItemsId,ItemName,QTY,UnitPrice) VALUES(?,?,?,?)";

            try{
                Connection connection = DbConnection.getInstance().getConnection();
                PreparedStatement pstm = connection.prepareStatement(sql);

                pstm.setString(1,id);
                pstm.setString(2,name);
                pstm.setString(3,qty);
                pstm.setString(4,price);

                boolean isSaved = pstm.executeUpdate() > 0;
                if (isSaved){
                    new Alert(Alert.AlertType.INFORMATION, "Item Saved Successfully").show();
                    ClearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }

        }

        @FXML
        void btnUpdateOnAction(ActionEvent event) {
            String Id = txtId.getText();
            String Name = txtName.getText();
            String Qty = txtQTY.getText();
            String Price = txtPrice.getText();

            String sql = "UPDATE items SET ItemName =?, QTY =?, UnitPrice =? WHERE ItemsId =?";

            try {
                boolean isUpdate = ItemRepo.Update2(Id, Name, Qty, Price);
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

    }
