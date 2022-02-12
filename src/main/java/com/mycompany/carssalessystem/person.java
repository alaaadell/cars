/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carssalessystem;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Alaa
 */
public class person {
    public boolean search(JTextField txt_search, JTable colorTable, ArrayList<car> list, JTable table){
        String s =txt_search.getText().toLowerCase();
            Object[] row = new Object[3];
            DefaultTableModel colorModel = (DefaultTableModel)colorTable.getModel();
            colorModel.setRowCount(0);
            DefaultTableModel model = (DefaultTableModel)table.getModel();
            model.setRowCount(0);
            boolean isFound=false;
            for (int i =0;i<list.size();i++){
            if(list.get(i).getType().toLowerCase().equals(s) || list.get(i).getPrice().equals(s) || list.get(i).getYear().equals(s) || list.get(i).getModel().toLowerCase().equals(s)){
            row[0]=list.get(i).getType();
            row[1]=list.get(i).getModel();
            row[2]=list.get(i).getID();
            isFound=true;
            model.addRow(row);
            }
           
            }
            return isFound;
    }
    
    public void viewCarDetails(connection conn, TableModel tableModel, int i, int ID,
            JTextField type, JTextField model, JTextField year, JTextField price, JLabel imageLabel) throws SQLException, IOException{
            String query1="Select * from cars where ID = "+ID;
            PreparedStatement pst = conn.conn.prepareStatement(query1);
            ResultSet rs = pst.executeQuery();
            rs.next();
            type.setText(tableModel.getValueAt(i, 0).toString());
            model.setText(tableModel.getValueAt(i, 1).toString());
            year.setText(rs.getString(5));
            price.setText(rs.getString(4));
            InputStream is = rs.getBinaryStream("car_image");
            //to make sure there's an image related to the car
            if(is!=null){
            BufferedImage bimg = ImageIO.read(is);
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(bimg).getImage().getScaledInstance(640, 480, Image.SCALE_DEFAULT));
            imageLabel.setIcon(imageIcon);
            is.close();
            }
            else{
            imageLabel.setIcon(null);
            }
    }
}
