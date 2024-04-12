package lk.gsbp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lk.gsbp.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationForm {
    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    public void btnRegisterOnAction(ActionEvent actionEvent) {
        String userId = txtUserId.getText();
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        try {
            boolean isSaved = saveUser(userId,username,password);
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION, "Registered Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean saveUser(String userId, String username, String password) throws SQLException {
        String sql = "INSERT INTO admin VALUES (?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,userId);
        pstm.setString(2,username);
        pstm.setString(3,password);

        return pstm.executeUpdate() > 0;
    }
}
