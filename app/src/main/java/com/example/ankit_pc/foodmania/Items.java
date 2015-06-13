package com.example.ankit_pc.foodmania;

/**
 * Created by Ankit_PC on 06/09/2015.
 */
public class Items {
    private int id2;
    private String fooditem;
    private double price;
    private String vng;
    public Items(int id2,String fooditem,double price,String vng) {
        this.id2 = id2;
        this.fooditem = fooditem;
        this.vng = vng;
        this.price = price;
    }

    public int getId2() {
        return id2;
    }

    public void setId2(int id2) {
        this.id2 = id2;
    }

    public String getFooditem() {
        return fooditem;
    }

    public void setFooditem(String fooditem) {
        this.fooditem = fooditem;
    }

    public String getVng() {
        return vng;
    }

    public void setVng(String vng) {
        this.vng = vng;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
