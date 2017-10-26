package com.techneekfactory.popularcars.popularcars.extras;

/**
 * Created by arafat on 17/08/17.
 */

public class Vehicles {

    private int  vehicleID;
private int  year;
    private String  price, image, make, model;

    public Vehicles(int vehicleID, int year, String price, String image, String make, String model) {
        this.vehicleID = vehicleID;
        this.year = year;
        this.price = price;
        this.image = image;
        this.make = make;
        this.model = model;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
