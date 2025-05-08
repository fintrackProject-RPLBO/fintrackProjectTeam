package com.fintrack.form.uiController;

import com.fintrack.form.dataBaseManager.Session;
import com.fintrack.form.tableManager.CatatanKeuanganTable;
import com.fintrack.form.tableManager.CategoryTable;
import com.fintrack.form.tableManager.UserData;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class AddCatatanPageController {
    UserData userData = UserData.getInstance();
    Session session = Session.getInstance();
    MethodCollection method = new MethodCollection();
    CatatanKeuanganTable catatanKeuanganTable = CatatanKeuanganTable.getInstance();
    CategoryTable categoryTable = CategoryTable.getInstance();

    @FXML private ComboBox<String> category;
    @FXML private TextField priceField;
    @FXML private DatePicker date;
    @FXML private TextField descriptionField;

    private FormSetController formSetController;

    public AddCatatanPageController() throws SQLException {
    }

    public void setFormSetController(FormSetController controller) {
        this.formSetController = controller;
    }

    @FXML
    private void initialize() throws SQLException {
        ArrayList<Object[]> data = categoryTable.getAllDataKategori();
        for (Object[] i : data){
            category.getItems().add(i[0].toString());
        }

        priceField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.startsWith("Rp. ")) {
                priceField.setText("Rp. " + newVal.replace("Rp. ", ""));
                }});

    }

    @FXML
    private void addCatatan() throws SQLException {
        if (category.getValue() == null){
            method.confirmationAlert("Kategori tidak boleh kosong");
        }
        else if (priceField.getText().isEmpty()){
            method.confirmationAlert("harga tidak boleh kosong");
        }
        else if(date.getValue() == null){
            method.confirmationAlert("tanggal tidak boleh kosong");
        }
        else if (!(category.getValue() == null && priceField.getText().isEmpty() && date.getValue() == null)){
            String kategori = category.getValue();
            Double harga = Double.parseDouble(priceField.getText().split(" ")[1]);
            String tanggal = date.getValue().toString();
            String deskripsi = descriptionField.getText();if (deskripsi == null){deskripsi = "";}
            boolean result = false;
            if (catatanKeuanganTable.countingTotalSpend(kategori,tanggal)+harga >= categoryTable.getLimitKategori(kategori)){
                if (method.confirmationAlert("Anda Sudah Melebihi Batas Harian "+ kategori+" Klick ok untuk abaikan")){
                    result = true;
                }
            }else{
                result = true;
            }
            if (result){
                catatanKeuanganTable.addCatatan(kategori,harga,tanggal,deskripsi, method.getNowDateTime());
                method.confirmationAlert("Catatan Berhasil Di Tambahkan");
            }else{
                method.confirmationAlert("Catatan Gagal Di Tambahkan");
            }
            formSetController.refreshTable();
        }
    }
    Boolean isThereAnyLetter(String str){
        for(Character i : str.toCharArray()){
            if (!method.isAlpha(i)){
                return false;
            }
        }
        return true;
    }


}
