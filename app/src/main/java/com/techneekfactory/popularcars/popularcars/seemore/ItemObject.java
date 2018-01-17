package com.techneekfactory.popularcars.popularcars.seemore;

public class ItemObject {



    private int id;
    private int year;
    private String price;

    private String imageUrl;
    private String make;
    private String model;


    public ItemObject(int id, int year, String price, String imageUrl, String make, String model) {
        this.id = id;
        this.year = year;
        this.price = price;
        this.imageUrl = imageUrl;
        this.make = make;
        this.model = model;
    }

    public ItemObject(String imageUrl, int id) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
