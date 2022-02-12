/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.carssalessystem;

/**
 *
 * @author Alaa
 */
class car {
    private int ID;
    private String type;
    private String model ;
    private String year;
    private String price;
    public car (int ID, String type , String model,String year,String price ){
       this.ID = ID;
       this.type=type;
       this.model=model;
       this.year=year;
       this.price=price;
    }

    public int getID() {
        return ID;
    }

    public String getYear() {
        return year;
    }

    public String getPrice() {
        return price;
    }

   

    public String getType() {
        return type;
    }

    public String getModel() {
        return model;
    }
    
}
