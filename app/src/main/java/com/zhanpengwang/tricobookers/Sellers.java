package com.zhanpengwang.tricobookers;

public class Sellers {

    private String username;
    private String price;
    private String contacts;

    public Sellers(String username, String price, String contacts) {
        this.username = username;
        this.price = price;
        this.contacts = contacts;
    }

    public String getUsername() {
        return username;
    }

    public String getPrice() {
        return price;
    }

    public String getContacts() {
        return contacts;
    }
}
