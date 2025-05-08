package com.fintrack.form.tableManager;

import com.fintrack.form.dataBaseManager.DBConnection;
import com.fintrack.form.dataBaseManager.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class CategoryTable {
    DBConnection db;
    private static CategoryTable instance;
    Session session = Session.getInstance();

    public static CategoryTable getInstance() throws SQLException {
        if (instance == null){
            instance = new CategoryTable();
        }
        return instance;
    }

    public CategoryTable() throws SQLException {
        this.db = new DBConnection("/form/fintrackDatabase.db");
    }

    public ArrayList<Object[]> getAllDataKategori() throws SQLException {
        ArrayList<Object[]> temp = new ArrayList<>();
        ArrayList<Object[]> data = db.RQuery("SELECT * FROM kategori");

        for (Object[] i : data){
            if(i[2].toString().equals(Session.getInstance().getUsername())){
                temp.add(i);
            }
        }
        return temp;
    }

    public boolean isExist(String kategori) throws SQLException {
        ArrayList<Object[]> data = getAllDataKategori();
        for (Object[] i : data){
            if (i[0].toString().toLowerCase().strip().equals(kategori.toLowerCase().strip())){
                return true;
            }
        }
        return false;
    }

    public Boolean addKategori(Double limit, String namaKategori) throws SQLException {
        String user = session.getUsername();
        if (user == null){
            return false;
        }else{
            if (isExist(namaKategori)){
                return false;
            }
            else{
                addKategori(limit,namaKategori,user);
                return true;
            }
        }
    }

    public Boolean addKategori(Double limit, String namaKategori,String username) throws SQLException {
        db.CUDQuery("INSERT INTO kategori VALUES (?,?,?)",new String[] {namaKategori,limit.toString(),username}, "TEXT NUMERIC TEXT");
        return true;
    }

    public boolean editKategori(String category,Double limit) throws SQLException {
        String user = session.getUsername();
        if (user == null){
            return false;
        }else{
            String kategori = session.getClickedDataKategori()[0].toString();
            db.CUDQuery("UPDATE kategori SET category = ?, priceLimit = ? WHERE category = ?",new String[] {category,limit.toString(),kategori}, "TEXT NUMERIC TEXT");
            return true;
        }
    }

    public boolean deleteKategori() throws SQLException {
        String user = session.getUsername();
        if (user == null){
            return false;
        }else{
            String kategori = session.getClickedDataKategori()[0].toString();
            if (kategori != null || !kategori.isEmpty()){
                db.CUDQuery("DELETE FROM kategori WHERE category = ?",new String[] {kategori}, "TEXT");
                return true;
            }
            else{
                return false;
            }
        }
    }



}
