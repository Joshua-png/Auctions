package sample.SignUp;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.DbConnection.DbConnection;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpController {
    DbConnection connection = new DbConnection();
    private String SQL = "INSERT into user (fname,lname,email,DoB,username,password) VALUES (?,?,?,?,?,?)";
    private String SQL_1 = "SELECT username FROM user WHERE username = ?";

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField username;

    @FXML
    private DatePicker Dob;

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField cpassword;

    @FXML
    private JFXButton alreadyAMember;

    @FXML
    private JFXButton btnSignUp;

    @FXML
    private Label message;


    @FXML
    void onClickSignUp(ActionEvent event) {
        String fname = firstName.getText();
        String lname = lastName.getText();
        String mail = email.getText();
        String user = username.getText();
        String pass = password.getText();
        String dob = (Dob.getEditor()).getText();

   try (Connection connect = connection.getConnection();
        PreparedStatement stmt = connect.prepareStatement(SQL);
        PreparedStatement stmt_username = connect.prepareStatement(SQL_1);) {

       stmt_username.setString(1,user);

        if (event.getSource() == btnSignUp) {
            if (!(fname.isEmpty() || lname.isEmpty() || mail.isEmpty() || user.isEmpty() || pass.isEmpty() || dob.isEmpty())) {
                if (validateEmail()) {
                    if (password.getText().equals(cpassword.getText())) {


                            stmt.setString(1, fname);
                            stmt.setString(2, lname);
                            stmt.setString(3, mail);
                            stmt.setString(4, dob);
                            stmt.setString(5, user);
                            stmt.setString(6, pass);

                            boolean rs = stmt.execute();

                            if (rs == false) {
                                Stage stage = (Stage) btnSignUp.getScene().getWindow();
                                stage.close();
                                Parent root = FXMLLoader.load(getClass().getResource("../Login/Login.fxml"));
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();

                                message.setText("User successfully created");
                            } else {
                                message.setText("User creation failed");
                                message.setTextFill(Color.TOMATO);

                            }

                        }
                    }
                }
            } else {
                //JOptionPane.showMessageDialog(null,"Fill all fields");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Empty Fields");
                alert.setHeaderText(null);
                alert.setContentText("Fill all fields");
                alert.showAndWait();
            }
        } catch (SQLException | IOException e) {
       e.printStackTrace();
   }
    }


    private boolean validateEmail() {
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(email.getText());
        if (m.find() && m.group().equals(email.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate email");
            alert.setHeaderText(null);
            alert.setContentText("Please enter valid email");
            alert.showAndWait();

            return false;
        }

    }
}