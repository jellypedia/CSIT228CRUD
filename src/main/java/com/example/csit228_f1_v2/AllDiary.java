package com.example.csit228_f1_v2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AllDiary {
//    @FXML
//    public ListView<String> listDiaryEntries;
    public Button btnDisplay, btnReturn;
    public Text txtDisplay;
    public AnchorPane pnDisplay;

    public void onDisplayEntries() {
        System.out.println("displayentries! start");
        StringBuilder sb = new StringBuilder();

        try(Connection c = mySQLConnection.getConnection();
            PreparedStatement stmt = c.prepareStatement(
                    "SELECT diaryTitle FROM diary WHERE userid = ?"
            )) {
            stmt.setInt(1,MainController.loggedInID);
            System.out.println(MainController.loggedInID);
            ResultSet res = stmt.executeQuery();
            while(res.next()) {
                String title = res.getString(1);
                sb.append(title+"\n");
            }
            txtDisplay.setText(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onReturn() throws IOException {
        AnchorPane x = pnDisplay;
        x.getScene().getStylesheets().clear();
        Parent p = FXMLLoader.load(getClass().getResource("Diary-view.fxml"));
        x.getChildren().clear();
        x.getChildren().add(p);
    }

}
