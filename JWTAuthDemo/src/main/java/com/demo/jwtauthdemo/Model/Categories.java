package com.demo.jwtauthdemo.Model;


public class Categories {

    private Integer category_ID,userId;
    private String title, description;
    private double totalExpense;

    public Categories(Integer category_ID, Integer userId, String title, String description,double totalExpense) {
        this.category_ID = category_ID;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.totalExpense=totalExpense;
    }

    public void setCategory_ID(Integer category_ID) {
        this.category_ID = category_ID;
    }

    public int getCategory_ID() {
        return category_ID;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
    }
}
