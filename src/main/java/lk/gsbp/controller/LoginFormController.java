package lk.gsbp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.gsbp.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {
    public AnchorPane rootNode;
    public TextField txtUsernameJ;
    public TextField txtPasswordj;
    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;


    public void btnLoginOnAction(ActionEvent actionEvent) {
        String username = txtUsernameJ.getText();
        String password = txtPasswordj.getText();

        try {
            checkCredentials(username, password);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void checkCredentials(String username, String password) throws SQLException, IOException {
        String sql = "SELECT Username,Password FROM admin WHERE AdminId = ?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, username);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String Password = resultSet.getString("Password");
            if (password.equals(Password)) {
                navigateToDashboard();
            } else {
                new Alert(Alert.AlertType.ERROR,"Sorry! password is incorrect try again").show();
            }
        }else{
            new Alert(Alert.AlertType.INFORMATION,"Sorry! username Can't be find").show();
        }

    }

    private void navigateToDashboard()  {
        AnchorPane rootNode = null;
        try {
            rootNode = FXMLLoader.load(this.getClass().getResource("/View/dashboard_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("GS Book Plaza Dashboard");

    }


    public void linkRegistrationOnAction(ActionEvent actionEvent)  {
        Parent rootNode = null;
        try {
            rootNode = FXMLLoader.load(this.getClass().getResource("/View/registration_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(rootNode);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.setTitle("Registration Form");
        stage.show();
    }
}