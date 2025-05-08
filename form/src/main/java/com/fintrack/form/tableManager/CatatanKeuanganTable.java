package com.fintrack.form.tableManager;

import com.fintrack.form.dataBaseManager.DBConnection;
import com.fintrack.form.dataBaseManager.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class CatatanKeuanganTable {
    DBConnection db;
    Session session = Session.getInstance();

    private static CatatanKeuanganTable instance;
    public static CatatanKeuanganTable getInstance() throws SQLException {
        if (instance == null){
            instance = new CatatanKeuanganTable();
        }
        return instance;
    }

    public CatatanKeuanganTable() throws SQLException {
        this.db = new DBConnection("/form/fintrackDatabase.db");
    }

    public ArrayList<Object[]> getAllDataCatatan() throws SQLException {
        ArrayList<Object[]> temp = new ArrayList<>();
        ArrayList<Object[]> data = db.RQuery("SELECT * FROM catatanKeuangan");

        for (Object[] i : data){
            if(i[4].toString().equals(Session.getInstance().getUsername())){
                temp.add(i);
            }
        }
        return temp;
    }


    public boolean isExist(String category,Double price, String date,String description) throws SQLException {
        String user = session.getUsername();
        ArrayList<Object[]> catatanDatabase = db.RQuery("SELECT  * FROM catatanKeuangan");

        for (Object[] i : catatanDatabase){
            String kategori = i[0].toString();
            Double harga = (Double) i[1];
            String tanggal = i[2].toString().split(" ")[0];
            String deskripsi = i[3].toString();
            String username = i[4].toString();

            if (kategori.equals(category) && Objects.equals(harga, price) && tanggal.equals(date) && deskripsi.equals(description) && username.equals(user)){
                return true;
            }
        }
        return  false;
    }

    public boolean addCatatan(String category,Double price,String date, String description,String dateTime) throws SQLException {
        String user = session.getUsername();
        if (user == null){
            return false;
        }else{
            db.CUDQuery("INSERT INTO catatanKeuangan VALUES (?,?,?,?,?,?)",new String[] {category,price.toString(),date,description,user,dateTime}, "TEXT NUMERIC TEXT TEXT TEXT TEXT");
            return true;
        }
    }

    public boolean editCatatan(String category,Double price,String date, String description,String dateTime) throws SQLException {
        String user = session.getUsername();
        if (user == null){
            return false;
        }else{
            String dataTime = session.getClickedData()[5].toString();
            db.CUDQuery("UPDATE catatanKeuangan SET kategori = ?, harga = ?, tanggal = ?, deskripsi = ?, user = ?, updateDate = ? WHERE updateDate = ?",new String[] {category,price.toString(),date,description,user,dateTime,dataTime}, "TEXT NUMERIC TEXT TEXT TEXT TEXT TEXT");
            return true;
        }
    }

    public boolean deleteCatatan() throws SQLException {
        String user = session.getUsername();
        if (user == null){
            return false;
        }else{
            String dataTime = session.getClickedData()[5].toString();
            if (dataTime != null || !dataTime.isEmpty()){
                db.CUDQuery("DELETE FROM catatanKeuangan WHERE updateDate = ?",new String[] {dataTime}, "TEXT");
                return true;
            }
            else{
                return false;
            }
        }
    }

    public Double countingTotalSpend(String kategori, String date) throws SQLException {
        ArrayList<Object[]> data = getAllDataCatatan();
        double Counter = 0.0;
        for (Object[] i : data){
            if (i[0].toString().equals(kategori) && i[2].toString().equals(date)){
                Counter += Double.parseDouble(i[1].toString());
            }
        }
        System.out.println(Counter+"<---");
        return Counter;
    }

    public boolean deleteCatatan(String username,String password) throws SQLException {

        return false;
    }

    public static void main(String[] args) throws SQLException {
        CatatanKeuanganTable ckt = new CatatanKeuanganTable();
        Session.getInstance().setUsername("Nicsap");
        System.out.println(ckt.getAllDataCatatan().toString());
    }
}
