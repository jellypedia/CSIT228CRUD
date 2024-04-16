package com.example.csit228_f1_v2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterController {
    public String username;
    String password;

    public RegisterController(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void insertData() {
        try(Connection c = mySQLConnection.getConnection();
            PreparedStatement stmt = c.prepareStatement(
                    "INSERT INTO crudusers (username,password) VALUES (?,?)"
            )){
            String usern = username;
            String pass = password;

            stmt.setString(1,usern);
            stmt.setString(2,pass);

            int rowsInsert = stmt.executeUpdate();
            System.out.println("Rows Inserted: " + rowsInsert);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
