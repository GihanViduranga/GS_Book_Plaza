package lk.gsbp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import lk.gsbp.Utill.Regex;
import lk.gsbp.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.mysql.cj.conf.PropertyKey.PASSWORD;

public class RegistrationForm {
    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    public void btnRegisterOnAction(ActionEvent actionEvent) {
        if (isValied()){

            String userId = txtUserId.getText();
            String username = txtUsername.getText();
            String password = txtPassword.getText();

            try {
                boolean isSaved = saveUser(userId,username,password);
                    if (isSaved){new Alert(Alert.AlertType.INFORMATION, "Registered Successfully").show();
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

    private boolean saveUser(String userId, String username, String password) throws SQLException {
        String sql = "INSERT INTO admin VALUES (?,?,?)";

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,userId);
        pstm.setString(2,username);
        pstm.setString(3,password);

        return pstm.executeUpdate() > 0;
    }

    public void UserIdOnKeyReleased(KeyEvent keyEvent) {
        Regex.setTextColor(lk.gsbp.Utill.TextField.AdminID, txtUserId);
    }
    public boolean isValied(){

        boolean nameValid = Regex.setTextColor(lk.gsbp.Utill.TextField.NAME, txtUsername);
        boolean idValid = Regex.setTextColor(lk.gsbp.Utill.TextField.AdminID, txtUserId);
        boolean passValid = Regex.setTextColor(lk.gsbp.Utill.TextField.PASSWORD, txtPassword);

        return nameValid && idValid && passValid;
    }

    public void UsernameOnKeyRelesead(KeyEvent keyEvent) {
        Regex.setTextColor(lk.gsbp.Utill.TextField.NAME, txtUsername);
    }

    public void PasswoardOnKeyRelesead(KeyEvent keyEvent) {
        Regex.setTextColor(lk.gsbp.Utill.TextField.Password, txtPassword);
    }
}
