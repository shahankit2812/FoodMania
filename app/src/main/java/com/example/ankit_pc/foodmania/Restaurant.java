package com.example.ankit_pc.foodmania;

/**
 * Created by Ankit_PC on 05/27/2015.
 */
public class Restaurant {

    private int _id;
    private String name;
    private String area;
    private int zipcode;
    private int min;
    private int dt;
    private String img;

    public Restaurant(int id,String name,String area,int zip,int min,int dt)
    {
        this._id=id;
        this.name=name;
        this.area=area;
        this.zipcode=zip;
        this.min=min;
        this.dt=dt;

    }
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }
}
