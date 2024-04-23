package com.example.csit228_f1_v2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainController {
    public GridPane pnLogin;
    public AnchorPane pnMain;
    public VBox pnHome;
    public TextField txtUsern;
    public TextField txtPass;
    @FXML
    private Label welcomeText;
//    Stage stage;

    static String loggedInUsern;
    String username, password;



    public void onRegisterClick() {
        RegisterController reg = new RegisterController(txtUsern.getText(),txtPass.getText());
        reg.insertData();
    }

    public void onLoginClick() {
        username = txtUsern.getText();
        password = txtPass.getText();
        if(checkLogIn(username,password)) {
            try {
                AnchorPane x = pnMain;
                Parent p = FXMLLoader.load(getClass().getResource("homepage.fxml"));
                x.getChildren().clear();
                x.getChildren().add(p);

                loggedInUsern = username;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Log in details don't match our database.");
        }
    }

    public boolean checkLogIn(String usern, String pass) {
        boolean check = false;
        try (Connection c = mySQLConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement("SELECT * FROM crudusers WHERE username = ? AND password = ?")) {
            stmt.setString(1, usern);
            stmt.setString(2, pass);
            System.out.println("checkLOGIN usern: " + usern + " pass: " + pass);

            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }
}