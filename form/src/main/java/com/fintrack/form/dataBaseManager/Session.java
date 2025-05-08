package com.fintrack.form.dataBaseManager;

public class Session {
    String username;
    Object[] clickedData;
    Object[] clickedDataKategori;

    private static Session instance;

    // Private constructor prevents instantiation from other classes
    private Session() {}

    // Global access point
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void unsetUsername(){this.username = null;}

    public void setClickedData(Object[] clickedData){this.clickedData = clickedData;}
    public void unsetClickedData(Object[] clickedData){this.clickedData = null;}

    public Object[] getClickedData(){return  this.clickedData;}

    public void setClickedDataKategori(Object[] clickedDataKategori) {
        this.clickedDataKategori = clickedDataKategori;
    }
    public void unsetClickedDataKategori() {
        this.clickedDataKategori = null;
    }

    public Object[] getClickedDataKategori() {
        return clickedDataKategori;
    }

}
