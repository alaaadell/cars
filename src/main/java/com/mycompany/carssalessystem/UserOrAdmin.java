/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carssalessystem;
import java.sql.SQLException;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
/**
 *
 * @author Alaa
 */
public abstract class UserOrAdmin extends person{
    public abstract boolean correctLogin(connection conn,JTextField username, JPasswordField password)throws SQLException;
     public abstract String loginByUsername();
     public abstract String loginByEmail();
     public abstract void deleteCar(connection con, int ID)throws SQLException;
}
