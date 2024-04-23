package com.example.csit228_f1_v2;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoggedInPage {

    public ToggleButton tbNight;
    public ProgressIndicator piProgress;
    public Slider slSlider;
    public ProgressBar pbProgress;
    public AnchorPane index;

//    public HomeController(String username) {
//        this.username = username;
//    }

    public void onSliderChange() {
        double val = slSlider.getValue();
        System.out.println(val);
        piProgress.setProgress(val/100);
        pbProgress.setProgress(val/100);
        if (val == 100) {
            System.exit(0);
        }
    }

    public void onNightModeClick() {
        if (tbNight.isSelected()) {
            tbNight.getParent().setStyle("-fx-background-color: BLACK");
            tbNight.setText("DAY");
        } else {
            tbNight.getParent().setStyle("-fx-background-color: WHITE");
            tbNight.setText("NIGHT");
        }
    }

    public void onDeleteAcc() throws IOException {
        String username = MainController.loggedInUsern;
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

}
