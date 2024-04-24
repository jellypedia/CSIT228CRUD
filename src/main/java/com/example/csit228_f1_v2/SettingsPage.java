package com.example.csit228_f1_v2;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SettingsPage {

    public AnchorPane index;
    public TextField txtNewUsern, txtConfirmUsern;
    public Button btnReturn;

    public void onDeleteAcc() throws IOException {
        String username = MainController.loggedInUsern;
        System.out.println(username);
        try(Connection c = mySQLConnection.getConnection();
            PreparedStatement stmt = c.prepareStatement(
                    "DELETE FROM crudusers WHERE username = ?")) {
            System.out.println("to be deleted unta: " + username);
            stmt.setString(1, username);
            int rows = stmt.executeUpdate();

            System.out.println("Rows deleted: " + rows);

            index.getScene().getStylesheets().clear();
            Parent p = FXMLLoader.load(getClass().getResource("login-view.fxml"));
            index.getChildren().clear();
            index.getChildren().add(p);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        AnchorPane x = index;
//        x.getScene().getStylesheets().clear();
        Parent p = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        x.setBackground(null);
        x.getChildren().clear();
        x.getChildren().add(p);
    }

    public void onUpdateUsern() {
        String newUsern = txtNewUsern.getText(), confirmUsern = txtConfirmUsern.getText();
        int currUsernID = MainController.loggedInID;

        System.out.println("newusern: " + newUsern + "currusernID: " + currUsernID);

        if(newUsern.equals(confirmUsern)) {
            try(Connection c = mySQLConnection.getConnection();
                PreparedStatement stmt = c.prepareStatement(
                        "UPDATE crudusers SET username = ? WHERE userID = ?"
                )) {
                stmt.setString(1,newUsern);
                stmt.setInt(2,currUsernID);
                stmt.executeUpdate();

                System.out.println("Updated username successfully");
                MainController.loggedInUsern = newUsern;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void onReturn() throws IOException {
        index.getScene().getStylesheets().clear();
        Parent p = FXMLLoader.load(getClass().getResource("Diary-view.fxml"));
        index.getChildren().clear();
        index.getChildren().add(p);
    }

}
