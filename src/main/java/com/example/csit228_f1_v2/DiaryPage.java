package com.example.csit228_f1_v2;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DiaryPage {
    public TextField txtDiaryTitle;
    public TextArea txtDiaryText;
    public Button btnSubmit, btnEdit, btnDelete, btnSettings;
    public AnchorPane pnDiary;
    static int diaryID;
    int currUserID = MainController.loggedInID;
    String username = MainController.loggedInUsern;
    public void onSubmitEntry() {
        String diaryTitle = txtDiaryTitle.getText();
        String diaryContent = txtDiaryText.getText();

        try(Connection c = mySQLConnection.getConnection();
            PreparedStatement stmt = c.prepareStatement(
                    "INSERT INTO diary (userID, diaryTitle, diaryContent) values (?,?,?)"
            )) {
            stmt.setInt(1, currUserID);
            stmt.setString(2, diaryTitle);
            stmt.setString(3,diaryContent);

            stmt.executeUpdate();
            getLastEntry();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        System.out.println("DiaryID SUBMIT: "+diaryID);
    }

    public void onEditEntry() {
        String diaryTitle = txtDiaryTitle.getText();
        String diaryContent = txtDiaryText.getText();

        try(Connection c = mySQLConnection.getConnection();
            PreparedStatement stmt = c.prepareStatement(
                    "UPDATE diary SET diaryTitle = ?, diaryContent = ? WHERE diaryID = ?"
            )) {
            stmt.setString(1, diaryTitle);
            stmt.setString(2, diaryContent);
            stmt.setInt(3,diaryID);

            stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void onDeleteEntry() throws IOException {
        System.out.println("DiaryID DELETE: " + diaryID);

        try(Connection c = mySQLConnection.getConnection();
            PreparedStatement stmt = c.prepareStatement(
                    "DELETE FROM diary WHERE diaryID = ?")) {
            stmt.setInt(1,diaryID);
            int rows = stmt.executeUpdate();

            System.out.println("Rows deleted: " + rows);

            pnDiary.getScene().getStylesheets().clear();
            Parent p = FXMLLoader.load(getClass().getResource("login-view.fxml"));
            pnDiary.getChildren().clear();
            pnDiary.getChildren().add(p);

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        AnchorPane x = pnDiary;
//        x.getScene().getStylesheets().clear();
        Parent p = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        x.setBackground(null);
        x.getChildren().clear();
        x.getChildren().add(p);
    }

    public void onSettings() throws IOException {
        AnchorPane x = pnDiary;
        Parent p = FXMLLoader.load(getClass().getResource("settings.fxml"));
        x.getChildren().clear();
        x.getChildren().add(p);

    }

    public void getLastEntry() {
        try (Connection c = mySQLConnection.getConnection();
             PreparedStatement stmt = c.prepareStatement(
                     "SELECT diaryID FROM diary WHERE userID = ? ORDER BY diaryID DESC LIMIT 1")) {
            stmt.setInt(1,currUserID);

            ResultSet res = stmt.executeQuery();
            if(res.next()) {
                diaryID = res.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onDisplayEntry() throws IOException {
        AnchorPane x = pnDiary;
        Parent p = FXMLLoader.load(getClass().getResource("AllDiary-view.fxml"));
        x.setBackground(null);
        x.getChildren().clear();
        x.getChildren().add(p);
    }
}
