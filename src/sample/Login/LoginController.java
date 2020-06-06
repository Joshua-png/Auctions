package sample.Login;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import sample.DbConnection.DbConnection;
import sample.MainPage.MainPageController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    private static String uname;
    private static int userId;
    //Instantiating a connection variable
    DbConnection connection = new DbConnection();
    boolean login = false;
    //String uname;

    //SQL statements
    private String SQL = "SELECT * FROM user WHERE username = ? and password = ?";


    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label errorMessage;

    @FXML
    private JFXButton forgotPassword;

    @FXML
    private JFXButton btnSignIn;

    @FXML
    private JFXButton btnSignUp;


    @FXML
    public void onClickSignIn(ActionEvent event) throws IOException {
        if(event.getSource() == btnSignIn) {
            try(Connection connect = connection.getConnection();
                PreparedStatement stmt = connect.prepareStatement(SQL)) {
                String user = username.getText();
                String pass = password.getText();

                stmt.setString(1,user);
                stmt.setString(2,pass);
                ResultSet rs = stmt.executeQuery();

                if(rs.next()){
                    //i = getId(rs.getInt("user_id"));
                    setUsername(rs.getString("username"));
                    setUserId(rs.getInt("user_id"));
                    errorMessage.setText("Login Successful");
                    Stage stage = (Stage) btnSignIn.getScene().getWindow();
                    stage.close();
                    //Parent root = FXMLLoader.load(getClass().getResource("../MainPage/MainPage.fxml"));

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../MainPage/MainPage.fxml"));
                    Parent root = loader.load();
                    MainPageController change_user = loader.getController();
                    change_user.getUser(rs.getString("username"));


                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    login = true;
                }else{
                    errorMessage.setTextFill(Color.TOMATO);
                    errorMessage.setText("Login Failed");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }else if(event.getSource() == btnSignUp){
            Stage stage = (Stage) btnSignIn.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("../SignUp/SignUp.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /*public int i;
    public  int getId(String user)
    {

        return id;
    }*/

    public String getUsername(){
        return uname;
    }

    public void setUsername(String user){
        uname = user;
    }

    public int getUserId(){
        return userId;
    }

    public void setUserId(int id){
        userId = id;
    }
}
