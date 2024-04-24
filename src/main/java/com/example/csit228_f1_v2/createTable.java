package com.example.csit228_f1_v2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class createTable {

    public void userTable() {
        Connection c = mySQLConnection.getConnection();
        String query = "CREATE TABLE IF NOT EXISTS CRUDusers (" +
                "userID INT PRIMARY KEY AUTO_INCREMENT," +
                "username VARCHAR(50) NOT NULL," +
                "password VARCHAR(50) NOT NULL)";
        try {
            Statement stmt = c.createStatement();
            stmt.execute(query);
            System.out.println("User table has been created!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("User table already exists!");
    }

    public void diaryTable() {
        Connection c = mySQLConnection.getConnection();
        String query = "CREATE TABLE IF NOT EXISTS diary (" +
                "diaryID INT PRIMARY KEY AUTO_INCREMENT," +
                "userID INT NOT NULL," +
                "FOREIGN KEY(userID) REFERENCES crudusers(userID) ON DELETE CASCADE," +
                "diaryTitle VARCHAR(50) NOT NULL," +
                "diaryContent VARCHAR(1500))";
        try {
            Statement stmt = c.createStatement();
            stmt.execute(query);
            System.out.println("Diary table has been created!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Diary table already exists!");
    }

}
