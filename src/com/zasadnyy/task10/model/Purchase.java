package com.zasadnyy.task10.model;

import java.util.Date;

public class Purchase {
    private int id;
    private User user;
    private Tour tour;
    private Date timeOfPurchase;
    private double price;
    private boolean checked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public Date getTimeOfPurchase() {
        return timeOfPurchase;
    }

    public void setTimeOfPurchase(Date timeOfPurchase) {
        this.timeOfPurchase = timeOfPurchase;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
