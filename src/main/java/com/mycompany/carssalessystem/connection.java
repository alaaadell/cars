/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carssalessystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Alaa
 */
public class connection {
    Connection conn;

    public connection() throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=cars sales system;user=sa;password=12345"; 
        conn = DriverManager.getConnection(url);
    }

    public void Close() throws SQLException
    {
        conn.close(); 
    }
}
