package lk.gsbp.controller;

import com.jfoenix.controls.JFXButton;
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
import lk.gsbp.model.Stock;
import lk.gsbp.model.Supplier;
import lk.gsbp.model.stockDetails;
import lk.gsbp.model.tm.CartTm;
import lk.gsbp.model.tm.StockTm;
import lk.gsbp.model.tm.SupplierTm;
import lk.gsbp.repository.*;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SupplierFormController {

    @FXML
    public TextField txtItemName;
    @FXML
    public TextField txtQTY;
    @FXML
    public Label lblStockId;

    @FXML
    public TableColumn tblQTY;
    @FXML
    public TableColumn tblStockID;
    public TableColumn tblItemPrice;
    public Label lblNetTotal;
    public TextField txtItemPrice;

    @FXML
    private AnchorPane SupplierRootNode;

    @FXML
    private TextField txtSupplierID;

    @FXML
    private TextField txtSupplierName;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtProduct;

    @FXML
    private TableView<SupplierTm> tblSupplier;

    @FXML
    private TableColumn<?, ?> tblSupplierID;

    @FXML
    private TableColumn<?, ?> tblSupplierName;

    @FXML
    private TableColumn<?, ?> tblContact;

    @FXML
    private TableColumn<?, ?> tblProduct;
    @FXML
    private TableColumn<?,?> tblAction;

    private ObservableList<SupplierTm> stList = FXCollections.observableArrayList();


    public void initialize() {
        setCellValueFactory();
        loadAllSuppliers();
        //getCurrentOrderId();
    }

    private void loadAllSuppliers() {
        /*ObservableList<SupplierTm> SupplierList = FXCollections.observableArrayList();

        try {
            List<Supplier> supplierList = SupplierRepo.getAll();

            for (Supplier supplier : supplierList) {
                SupplierTm supplierTm = new SupplierTm(
                    supplier.getSupplierId(),
                    supplier.getSuppName(),
                    supplier.getContact(),
                    supplier.getProduct(),
                    supplier.getQty(),
                    supplier.
                );
                SupplierList.add(supplierTm);
                tblSupplier.setItems(SupplierList);
            }
            } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }*/
    }

    private void setCellValueFactory() {
        tblSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        tblSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        tblContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
        tblQTY.setCellValueFactory(new PropertyValueFactory<>("QTY"));
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/View/dashboard_form.fxml"));
        Stage stage = (Stage) SupplierRootNode.getScene().getWindow();

        stage.setScene(new Scene(rootNode));
        stage.setTitle("Gs Book Plaza Dashboard");
        stage.centerOnScreen();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        ClearFields();
    }

    private void ClearFields() {
        txtSupplierID.setText("");
        txtSupplierName.setText("");
        txtContact.setText("");
        txtProduct.setText("");
        txtItemName.setText("");
        txtQTY.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException {
        String Id = txtSupplierID.getText();

        String sql = "DELETE FROM supplier WHERE SupplierId =?";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,Id);

            boolean isDeleted = pstm.executeUpdate() > 0;
            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION, "Supplier Deleted Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnNewOnAction(ActionEvent event)  {
        AnchorPane anchorPane = null;
        try {
            anchorPane = FXMLLoader.load(getClass().getResource("/View/stock_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Supplier Form");
        stage.show();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        String supplierID = txtSupplierID.getText();
        String supplierName = txtSupplierName.getText();
        String contact = txtContact.getText();
        String product = txtProduct.getText();
        String itemName = txtItemName.getText();
        String qty = txtQTY.getText();

        String sql = "INSERT INTO supplier Values(?,?,?,?,?,?)";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,supplierID);
            pstm.setString(2,supplierName);
            pstm.setString(3,contact);
            pstm.setString(4,product);
            pstm.setString(5,itemName);
            pstm.setString(6,qty);

            boolean isSaved = pstm.executeUpdate() > 0;
            if (isSaved){
                initialize();
                new Alert(Alert.AlertType.INFORMATION, "Supplier Saved Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String SupplierId = txtSupplierID.getText();
        String Name = txtSupplierName.getText();
        String Contact = txtContact.getText();
        String Product = txtProduct.getText();
        String itemName = txtItemName.getText();
        String qty = txtQTY.getText();

        String sql = "UPDATE supplier SET SupplierName =?, Contact =?, Product =?, DeliveryTerms =?, SupplierRating =? WHERE SupplierId =?";

        try {
            boolean isUpdate = SupplierRepo.update2(SupplierId, Name, Contact, Product, itemName, qty);
            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier Updated Successfully").show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Supplier Not Updated").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    @FXML
    void txtSupplierSearchOnAction(ActionEvent event) throws SQLException {
        String id = txtSupplierID.getText();

        String sql = "SELECT * FROM supplier WHERE SupplierId =?";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,id);

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString(2);
                String Contact = resultSet.getString(3);
                String Product = resultSet.getString(4);
                String itemName = resultSet.getString(5);
                String qty = resultSet.getString(6);

                txtSupplierName.setText(name);
                txtContact.setText(Contact);
                txtProduct.setText(Product);
                txtItemName.setText(itemName);
                txtQTY.setText(qty);
            } else {
                new Alert(Alert.AlertType.ERROR, "Supplier Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"Supplier ID Not Found!").show();
        }
    }


    /*private void getCurrentOrderId() {
        try {
            String stockId = StockRepo.GetStockId();

            String nextStockID = generateNextAssestId();
            lblStockId.setText(nextStockID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static String generateNextAssestId() throws SQLException {
        Connection con = DbConnection.getInstance().getConnection();

        String sql = "SELECT StockId FROM stock ORDER BY StockId DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        if(resultSet.next()) {
            return splitStockID(resultSet.getString(1));
        }
        return splitStockID(null);
    }

    private static String splitStockID(String string) {
        if(string != null) {
            String[] strings = string.split("S0");
            int id = Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if (length < 2){
                return "S00"+id;
            }else {
                if (length < 3){
                    return "S0"+id;
                }else {
                    return "S"+id;
                }
            }
        }
        return "S001";
    }*/

    public void btnUpdateStockOnAction(ActionEvent actionEvent) throws SQLException {
            String SupplierId = txtSupplierID.getText();
            String ItemName = txtItemName.getText();
            String CatogaryName = txtProduct.getText();
            String QTY = txtQTY.getText();

            var stock = new Stock(SupplierId,ItemName,CatogaryName,QTY);

            List<stockDetails> StList = new ArrayList<>();

            for (int i = 0; i < txtQTY.getText().length(); i++) {
                SupplierTm tm = stList.get(i);
                stockDetails stockDetails = new stockDetails(
                        tm.getSupplierId(),
                        tm.getQTY(),
                        tm.getItemPrice()
                );
                StList.add(stockDetails);
            }
            PlaceStock ps = new PlaceStock(stock, StList);

            boolean isUpdateSupplier = PlaceStockRepo.placeStock(ps);

            if (isUpdateSupplier) {
                new Alert(Alert.AlertType.INFORMATION, "Stock Updated Successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Stock Not Updated").show();
            }

    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        String SupplierId = txtSupplierID.getText();
        String SupplierName = txtSupplierName.getText();
        String Contact = txtContact.getText();
        String Product = txtProduct.getText();
        int Qty = Integer.parseInt(txtQTY.getText());
        int ItemPrice = Integer.parseInt(txtItemPrice.getText());
        int TotalPrice = Qty * ItemPrice;
        JFXButton Remove = new JFXButton("Remove");
        Remove.setCursor(Cursor.HAND);

        Remove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION,"Are you sure to remove", yes, no).showAndWait();
            if (type.isPresent() && type.get() == yes) {
                int SelectIndex = tblSupplier.getSelectionModel().getSelectedIndex();
                stList.remove(SelectIndex);

                tblSupplier.refresh();
                CalculateNetTotal();
            }
        });
        if (!stList.isEmpty()) {
            for (int i = 0; i < tblSupplier.getItems().size(); i++){
                if (SupplierId.equals(tblSupplierID.getCellData(i))) {

                    Qty = Qty + (int) tblQTY.getCellData(i);
                    TotalPrice += Qty * ItemPrice;

                    stList.get(i).setQTY(Qty);
                    stList.get(i).setItemPrice(TotalPrice);

                    tblSupplier.refresh();

                    CalculateNetTotal();

                    return;
                }
            }
        }
        SupplierTm tm = new SupplierTm(SupplierId,SupplierName,Contact,Product,Qty,ItemPrice,Remove);
        stList.add(tm);
    }

    private void CalculateNetTotal() {
        double netTotal = 0.0;
        for (int i = 0; i <tblSupplier.getItems().size(); i++) {
            netTotal = netTotal + (double) tblItemPrice.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }
}
