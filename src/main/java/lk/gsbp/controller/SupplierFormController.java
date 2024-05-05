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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.gsbp.db.DbConnection;
import lk.gsbp.model.Supplier;
import lk.gsbp.model.tm.SupplierTm;
import lk.gsbp.repository.SupplierRepo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SupplierFormController {

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
    private TextField txtDeliveryTerms;

    @FXML
    private TextField txtSupplierRating;

    public void initialize() {
        setCellValueFactory();
        loadAllSuppliers();
    }

    private void loadAllSuppliers() {
        ObservableList<SupplierTm> SupplierList = FXCollections.observableArrayList();

        try {
            List<Supplier> supplierList = SupplierRepo.getAll();

            for (Supplier supplier : supplierList) {
                SupplierTm supplierTm = new SupplierTm(
                    supplier.getSupplierId(),
                    supplier.getSuppName(),
                    supplier.getContact(),
                    supplier.getProduct()
                );
                SupplierList.add(supplierTm);
                tblSupplier.setItems(SupplierList);
            }
            } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void setCellValueFactory() {
        tblSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        tblSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        tblContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblProduct.setCellValueFactory(new PropertyValueFactory<>("product"));
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
        txtDeliveryTerms.setText("");
        txtSupplierRating.setText("");
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
        String deliveryTerms = txtDeliveryTerms.getText();
        String supplierRating = txtSupplierRating.getText();

        String sql = "INSERT INTO supplier Values(?,?,?,?,?,?)";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,supplierID);
            pstm.setString(2,supplierName);
            pstm.setString(3,contact);
            pstm.setString(4,product);
            pstm.setString(5,deliveryTerms);
            pstm.setString(6,supplierRating);

            boolean isSaved = pstm.executeUpdate() > 0;
            if (isSaved){
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
        String DeliveryTerms = txtDeliveryTerms.getText();
        String SupplierRating = txtSupplierRating.getText();

        String sql = "UPDATE supplier SET SupplierName =?, Contact =?, Product =?, DeliveryTerms =?, SupplierRating =? WHERE SupplierId =?";

        try {
            boolean isUpdate = SupplierRepo.update2(SupplierId, Name, Contact, Product, DeliveryTerms, SupplierRating);
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
                String DeliveryTerms = resultSet.getString(5);
                String SupplierRating = resultSet.getString(6);

                txtSupplierName.setText(name);
                txtContact.setText(Contact);
                txtProduct.setText(Product);
                txtDeliveryTerms.setText(DeliveryTerms);
                txtSupplierRating.setText(SupplierRating);
            } else {
                new Alert(Alert.AlertType.ERROR, "Supplier Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"Supplier ID Not Found!").show();
        }
    }

}
