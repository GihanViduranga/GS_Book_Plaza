package lk.gsbp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) {
        try {
            stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/View/Login_form.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("GS Book Plaza Login");
        stage.centerOnScreen();
        stage.show();
    }
}
