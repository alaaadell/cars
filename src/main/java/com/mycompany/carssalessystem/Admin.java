/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carssalessystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Alaa
 */
public class Admin extends UserOrAdmin{
    //overloading
    public void updateCar(connection conn, JTable colorTable, int ID) throws SQLException{
        DefaultTableModel colorModel = (DefaultTableModel) colorTable.getModel();
            String query = "Delete from colors where ID = "+ID;
            PreparedStatement pst = conn.conn.prepareStatement(query);
            pst.executeUpdate();
            for (int i=0;i<colorModel.getRowCount();i++)
            {
                query ="Insert into colors values(?,?,?)";
                pst = conn.conn.prepareStatement(query);
                pst.setInt(1, ID);
                pst.setString(2, colorModel.getValueAt(i, 0).toString());
                pst.setInt(3, Integer.parseInt(colorModel.getValueAt(i, 1).toString()));
                //to not add the color if the admin sets the number to 0
                if(Integer.parseInt(colorModel.getValueAt(i, 1).toString())> 0){
                        pst.executeUpdate();
                }
            }            
    }
    //overloading
    public void updateCar(connection conn, JTextField type, JTextField model, JTextField year, JTextField price,
            FileInputStream filename, int ID, File file) throws SQLException, IOException{
        String query = "Update cars set manufacturer_type = ?, model = ?, manufacture_year = ?, price = ? where ID = "+ID;
            PreparedStatement pst = conn.conn.prepareStatement(query);
            pst.setString(1, type.getText());
            pst.setString(2, model.getText());
            pst.setString(3, year.getText());
            pst.setString(4, price.getText());
            pst.executeUpdate();
            //to update image only if admin changes it
                if(file!=null){
                    byte[] image = new byte[(int) file.length()];
                    filename.read(image);
                    query = "update cars set car_image = ? where ID = "+ID;
                    pst = conn.conn.prepareStatement(query);
                    pst.setBytes(1, image);
                    pst.executeUpdate();
                }
    }
    @Override
     public boolean correctLogin(connection conn,JTextField username, JPasswordField password) throws SQLException {
            ResultSet rs;
//             TODO add your handling code here:
          String sql = new String();
          //to check the admin logins by email or username
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
     public FileInputStream addImage(JFileChooser chooser, FileInputStream filename, JTextField txt_path){
         int returnVal = chooser.showOpenDialog(chooser);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = chooser.getSelectedFile();
                txt_path.setText(file.getAbsolutePath());
                filename = new FileInputStream(file);
                
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
                return filename;
     }
    @Override
     public String loginByUsername(){
        
           String sql = "Select * from admins where username=? and user_password=?";
           return sql;   
    }
    @Override
    public String loginByEmail(){
        
           String sql = "Select * from admins where email=? and user_password=?";
           return sql;  
    }
    public String colorExists(){
        String sql = "Select * from colors where color=? and ID = ?";
        return sql;
    }
    //overloading
    public String increaseColors(){
        String sql = "Update colors set number_of_cars = number_of_cars + ? where ID = ? and color = ?";
        return sql;
    }
    //overloading
    public void increaseColors(String color, int ID, String num, connection conn) throws SQLException{
                    
                    String sql = Global.admin1.colorExists();
                    PreparedStatement pst = conn.conn.prepareStatement(sql);            
                    pst.setString(1, color);
                    pst.setInt(2, ID);
                    ResultSet rsColor = pst.executeQuery();
                    if(rsColor.next()){
                        String sqlUpdate = Global.admin1.increaseColors();
                        PreparedStatement pst1 = conn.conn.prepareStatement(sqlUpdate);
                        pst1.setString(1, num);
                        pst1.setInt(2, ID);
                        pst1.setString(3, color);
                        pst1.execute();
                        }
                    else{
                        String sqlUpdate2 = addColors();
                        pst = conn.conn.prepareStatement(sqlUpdate2);
                        if(!(num.equals("0")||color.equals("null")))
                        addColors(pst, ID, color, num);
                    }
                    
    }
    public ResultSet carExists(connection conn, JTextField type, JTextField model, JTextField price
    , JTextField year) throws SQLException{
        ResultSet rs;
                String sql1 = new String();
                //check if the car exists
                sql1 = "Select * from cars where manufacturer_type=? and model=? and price=? and manufacture_year=?";
                PreparedStatement pst1 = conn.conn.prepareStatement(sql1);
                pst1.setString(1, type.getText());
                pst1.setString(2, model.getText());
                pst1.setString(3, price.getText());
                pst1.setString(4, year.getText());
                rs = pst1.executeQuery();
                return rs;
    }
    public void addCar(connection conn, JTextField type, JTextField model, JTextField price
    , JTextField year, FileInputStream filename, int ID) throws SQLException{
                    String sql = "insert into cars values(?,?,?,?,?,?)";
                    PreparedStatement pst = conn.conn.prepareStatement(sql);
                    pst.setInt(1, ID);
                    pst.setString(2, type.getText());
                    pst.setString(3, model.getText());
                    pst.setString(4, price.getText());
                    pst.setString(5, year.getText());
                    pst.setBlob(6, filename);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Successfully added!");
                   
    }
    //overloading
    public void addColors(PreparedStatement pst, int carID, String color, String num) throws SQLException{
                    pst.setInt(1, carID);
                    pst.setString(2, color);
                    pst.setString(3, num);
                    pst.execute();
    }
    //overloading
    public String addColors(){
       String sql = "insert into colors values(?,?,?)"; 
       return sql;
    }
    public int getCarIndex() throws SQLException{
           connection conn = new connection();
           String sql = "Select next value for cars_ID";
           PreparedStatement pst = conn.conn.prepareStatement(sql);
           ResultSet rs = pst.executeQuery();
           if(rs.next())
            return rs.getInt(1);
            return 0;
    }
    @Override
    public void deleteCar(connection conn, int ID) throws SQLException{
            String colorQuery = "Delete from colors where ID = "+ID;
            String query = "Delete from cars where ID = "+ID;
            PreparedStatement pst = conn.conn.prepareStatement(colorQuery);
            pst.executeUpdate();
            pst = conn.conn.prepareStatement(query);
            pst.executeUpdate();
    }
    
    
  }
