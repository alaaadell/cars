/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carssalessystem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Alaa
 */
public class user extends UserOrAdmin{
    @Override
    public String loginByUsername(){
        
           String sql = "Select * from users where username=? and user_password=?";
           return sql;   
    }
    @Override
    public String loginByEmail(){
        
           String sql = "Select * from users where email=? and user_password=?";
           return sql;  
    }
    @Override
    public boolean correctLogin(connection conn,JTextField username, JPasswordField password) throws SQLException {
            ResultSet rs;
//             TODO add your handling code here:
          String sql = new String();
          //to check if the user logins by username or email
            if(username.getText().contains(".com")){
               sql = this.loginByEmail();
            }
            else{
                sql = this.loginByUsername();
            }
            PreparedStatement pst = conn.conn.prepareStatement(sql);
            pst.setString(1, username.getText());
            pst.setString(2, password.getText());
            rs = pst.executeQuery();
            return rs.next();
    }
    public void addUser(connection conn, JTextField username, JPasswordField password, JTextField email) throws SQLException{
        String sql = "insert into users values(next value for users_ID,?,?,?)";
                            PreparedStatement pst = conn.conn.prepareStatement(sql);
                            pst.setString(1, username.getText());
                            pst.setString(2, password.getText());
                            pst.setString(3, email.getText());
                            pst.execute();
    }
    public boolean checkPassword(String password, String confirmPassword){
        if(password.equals(confirmPassword)){
            return true;
        }
        else
            return false;
    }
  
    
    public boolean emailExists(connection conn, JTextField email) throws SQLException{
        String sql = "Select * from users where email=?";
                    PreparedStatement pst = conn.conn.prepareStatement(sql);
                    pst.setString(1, email.getText());
                    ResultSet rs = pst.executeQuery();
                    return rs.next();
        
    }
     public boolean usernameExists(connection conn, JTextField username) throws SQLException{
                ResultSet rs;
                // TODO add your handling code here:
                String sql = new String();
                //check if the username exists
                sql = "Select * from users where username=?";
                PreparedStatement pst = conn.conn.prepareStatement(sql);
                pst.setString(1, username.getText());
                rs = pst.executeQuery();
                return rs.next();
    }
     
      public void decreaseNumber(connection conn, int ID, DefaultTableModel colorModel, JTable colorTable) throws SQLException{
        String update = "update colors set number_of_cars = number_of_cars -1 where ID =? and color = ?";
        PreparedStatement pst = conn.conn.prepareStatement(update);
        pst.setInt(1, ID);
        pst.setString(2, colorModel.getValueAt(colorTable.getSelectedRow(), 0).toString());
        pst.executeUpdate();
    }
      //overloading
      //to delete only one color when it's 0
       public int getCarNumber(connection conn, int ID, DefaultTableModel colorModel, JTable colorTable) throws SQLException{
                String query = "select number_of_cars from colors where ID = ? and color = ?";
                PreparedStatement pst = conn.conn.prepareStatement(query);
                pst.setInt(1, ID);
                pst.setString(2, colorModel.getValueAt(colorTable.getSelectedRow(), 0).toString());
                ResultSet rs = pst.executeQuery();
                rs.next();
                return rs.getInt("number_of_cars");
    }
       //overloading
       //to delete the car if there's no colors
       public boolean getCarNumber(int ID, connection conn) throws SQLException{
           String query = "Select * from colors where ID = "+ID;
                Statement st = conn.conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                return rs.next();
       }
    public void deleteColor(connection conn, int ID, DefaultTableModel colorModel, JTable colorTable) throws SQLException{
                String update = "Delete from colors where ID = ? and color = ?";
                PreparedStatement pst = conn.conn.prepareStatement(update);
                pst.setInt(1, ID);
                pst.setString(2, colorModel.getValueAt(colorTable.getSelectedRow(), 0).toString());
                pst.executeUpdate();
    }
    @Override
    public void deleteCar(connection conn, int ID) throws SQLException{
         String update = "Delete from cars where ID = "+ID;
                    PreparedStatement pst = conn.conn.prepareStatement(update);
                    pst.executeUpdate();
    }
}
