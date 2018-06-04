package com.whosmyserver.model;

import java.util.ArrayList;

public class Restaurant {
	private String title, thumbnailUrl, foodType;
    private double status;
    private double rating;
    private ArrayList<String> address;
    
    
    public Restaurant() {
    }
    public Restaurant(String name, String thumbnailUrl, int status, double rating,
    		ArrayList<String> address, String foodType) {
        this.title = name;
        this.thumbnailUrl = thumbnailUrl;
        this.status = status;
        this.rating = rating;
        this.foodType = foodType;
        this.address = address;
    }
		
    
    public String getTitle() {
        return title;
    }
 
    public void setTitle(String name) {
        this.title = name;
    }
 
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
 
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
 
    public double getStatus() {
        return status;
    }
 
    public void setStatus(double distance) {
        this.status = distance;
    }
 
    public double getRating() {
        return rating;
    }
 
    public void setRating(double rating) {
        this.rating = rating;
    }
 
    public ArrayList<String> getAddress() {
        return address;
    }
 
    public void setAddress(ArrayList<String> address) {
        this.address = address;
    }
    public String getFoodType() {
        return foodType;
    }
 
    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }
}
