package com.example.csit228_f1_v2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class createTable {

    public void table() {
        Connection c = mySQLConnection.getConnection();
        String query = "CREATE TABLE IF NOT EXISTS CRUDusers (" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "username VARCHAR(50) NOT NULL," +
                "password VARCHAR(50) NOT NULL)";
        try {
            Statement stmt = c.createStatement();
            stmt.execute(query);
            System.out.println("Table has been created!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Table already exists!");
    }

}
