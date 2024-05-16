package lk.gsbp.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import lk.gsbp.model.Employee;
import lk.gsbp.model.tm.EmployeeTm;
import lk.gsbp.repository.EmployeeRepo;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class EmployeeFormController {

    public AnchorPane EmployeeRoot;
    public TextField txtID;
    public TextField txtAddress;
    public TextField txtJobStartDate;
    public TextField txtName;
    public TextField txtContact;
    public TextField txtSalary;
    public TextField txtPosition;
    public TableColumn tblName;
    public TableColumn tblAddress;
    public TableColumn tblContact;
    public TableColumn tblStartDate;
    public TableColumn tblSalary;
    public TableColumn tblPosition;
    public TableView tblEmployee;
    public TableColumn tblEmployeeId;

    public void initialize() {
        setCellValueFactory();
        loadAllEmployees();
    }

    private void loadAllEmployees() {
        ObservableList<EmployeeTm> objects = FXCollections.observableArrayList();

        try {
            List<Employee> employeeList = EmployeeRepo.getEmployees();

            for (Employee employee : employeeList) {
                EmployeeTm employeeTm = new EmployeeTm(
                        employee.getEmployeeId(),
                        employee.getName(),
                        employee.getAddress(),
                        employee.getContact(),
                        employee.getDate(),
                        employee.getPosition(),
                        employee.getSalary()

                );
                objects.add(employeeTm);
                tblEmployee.setItems(objects);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        tblEmployeeId.setCellValueFactory(new PropertyValueFactory<>("EmployeeId"));
        tblName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        tblAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        tblContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        tblStartDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        tblSalary.setCellValueFactory(new PropertyValueFactory<>("Salary"));
        tblPosition.setCellValueFactory(new PropertyValueFactory<>("Position"));
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        if (isValied()){
        String id = txtID.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        String jobStartDate = txtJobStartDate.getText();
        String salary = txtSalary.getText();
        String position = txtPosition.getText();

        String sql = "INSERT INTO employee (EmployeeId,Name,Address,Contact,Date,Position,Salary) Values(?,?,?,?,?,?,?)";

        try {
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,id);
            pstm.setString(2,name);
            pstm.setString(3,address);
            pstm.setString(4,contact);
            pstm.setString(5,jobStartDate);
            pstm.setString(6,salary);
            pstm.setString(7,position);

            boolean isSaved = pstm.executeUpdate() > 0;
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION, "Employee Saved Successfully").show();
                clearFields();
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

    public void btnClearOnAction(ActionEvent actionEvent) {
        clearFields();
    }

    private void clearFields() {
        txtID.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtContact.setText("");
        txtJobStartDate.setText("");
        txtSalary.setText("");
        txtPosition.setText("");
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String EmployeeId = txtID.getText();
        String Name = txtName.getText();
        String Address = txtAddress.getText();
        String Contact = txtContact.getText();
        String Date = txtJobStartDate.getText();
        String Position = txtPosition.getText();
        String Salary = txtSalary.getText();

        String sql = "UPDATE employee SET Name =?, Address =?, Contact =?, Date =?, Salary =?, Position =? WHERE EmployeeId =?";

        try{
            boolean isUpdate = EmployeeRepo.update2(EmployeeId, Name, Address, Contact, Date, Position, Salary);
            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Employee Updated Successfully").show();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Employee Not Updated").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String Id = txtID.getText();

        String sql = "DELETE FROM employee WHERE EmployeeId =?";

        try{
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, Id);

            boolean isDelete = pstm.executeUpdate() > 0;
            if (isDelete){
                new Alert(Alert.AlertType.INFORMATION, "Employee Deleted Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("/View/dashboard_form.fxml"));
        Stage stage = (Stage) EmployeeRoot.getScene().getWindow();

        stage.setScene(new Scene(rootNode));
        stage.setTitle("Gs Book Plaza Dashboard");
        stage.centerOnScreen();
    }

    public void txtEmployeeIdOnAction(ActionEvent actionEvent) throws SQLException {
        String Id = txtID.getText();

        String sql = "SELECT * FROM employee WHERE EmployeeId =?";

        try{
            Connection connection = DbConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, Id);
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String Name = resultSet.getString(3);
                String Address = resultSet.getString(4);
                String Contact = resultSet.getString(5);
                String Date = resultSet.getString(6);
                String Position = resultSet.getString(7);
                String Salary = resultSet.getString(8);

                txtName.setText(Name);
                txtAddress.setText(Address);
                txtContact.setText(Contact);
                txtJobStartDate.setText(Date);
                txtSalary.setText(Salary);
                txtPosition.setText(Position);

            } else {
                new Alert(Alert.AlertType.ERROR, "Employee Not Found").show();
            }
        } catch (SQLException e){
            new Alert(Alert.AlertType.INFORMATION,"Employee ID Not Found!").show();
        }
    }
    public boolean isValied(){

        boolean idValid = Regex.setTextColor(lk.gsbp.Utill.TextField.IDE, txtID);
        boolean nameValid = Regex.setTextColor(lk.gsbp.Utill.TextField.NAME, txtName);
        boolean addressValid = Regex.setTextColor(lk.gsbp.Utill.TextField.ADDRESS, txtAddress);
        boolean contactValid = Regex.setTextColor(lk.gsbp.Utill.TextField.CONTACT, txtContact);
        boolean positionValid = Regex.setTextColor(lk.gsbp.Utill.TextField.POSITION, txtPosition);
        boolean jobStartDateValid = Regex.setTextColor(lk.gsbp.Utill.TextField.DATE, txtJobStartDate);

        return idValid && nameValid && addressValid && contactValid && positionValid && jobStartDateValid;
    }
    public void EmployeeOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.gsbp.Utill.TextField.IDE, txtID);
    }

    public void AddressOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.gsbp.Utill.TextField.ADDRESS, txtAddress);
    }

    public void NameOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.gsbp.Utill.TextField.NAME, txtName);
    }

    public void ContactOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.gsbp.Utill.TextField.CONTACT, txtContact);
    }

    public void PositionOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.gsbp.Utill.TextField.POSITION, txtPosition);
    }

    public void DateOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.gsbp.Utill.TextField.DATE, txtJobStartDate);
    }
}
