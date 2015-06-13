package com.example.ankit_pc.foodmania;

/**
 * Created by Ankit_PC on 06/09/2015.
 */
public class Category {
    private int id1;
    private String category;

    public Category(int id,String name)
    {
        this.id1=id;
        this.category=name;
    }

    public int getId() {
        return id1;
    }

    public void setId(int id) {
        this.id1 = id;
    }

    public String getCat() {
        return category;
    }

    public void setCat(String cat) {
        this.category = cat;
    }
}


