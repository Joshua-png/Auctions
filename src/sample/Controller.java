package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    private Button btn;

    @FXML
    void clicked(ActionEvent event) throws IOException {
        System.out.println("clicked");
        System.out.println(getClass().getResource("sample.fxml").getPath());
        Stage stage = (Stage)btn.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Login/Second.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
