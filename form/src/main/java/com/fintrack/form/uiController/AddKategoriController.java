package com.fintrack.form.uiController;

import com.fintrack.form.dataBaseManager.Session;
import com.fintrack.form.tableManager.CatatanKeuanganTable;
import com.fintrack.form.tableManager.CategoryTable;
import com.fintrack.form.tableManager.UserData;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.sql.SQLException;

public class AddKategoriController {
    @FXML private TextField limit;
    @FXML private TextField namaKategori;


    UserData userData = UserData.getInstance();
    Session session = Session.getInstance();
    CatatanKeuanganTable catatanKeuanganTable = CatatanKeuanganTable.getInstance();
    MethodCollection method = new MethodCollection();
    CategoryTable categoryTable = CategoryTable.getInstance();


    private FormSetController formSetController;

    public AddKategoriController() throws SQLException {
    }

    public void setFormSetController(FormSetController controller) {
        this.formSetController = controller;
    }

    @FXML
    private void initialize() throws SQLException {
        limit.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.startsWith("Rp. ")) {
                char[] str = limit.getText().toCharArray();
                StringBuilder temp = new StringBuilder();

                for (int i = 0; i < str.length; i++){
                    if (method.isNum(String.valueOf(str[i]))){
                        temp.append(str[i]);
                    }
                }
                limit.setText("Rp. "+temp.toString());
            }});

    }

    @FXML
    private void addKategori() throws SQLException {
        if (method.isThereAnyLetter(limit.getText().split(" ")[1])){
            method.confirmationAlert("Limit tidak boleh memiliki huruf");
        }else if (!limit.getText().isEmpty() && !namaKategori.getText().isEmpty()){
                Double DailyLimit = Double.parseDouble(limit.getText().split(" ")[1]);
                String nama = namaKategori.getText().toString();
                if (categoryTable.addKategori(DailyLimit,nama)){
                    method.confirmationAlert("Kategori Berhasil Di Tambahkan");
                }else {
                    method.confirmationAlert("Kategori Sudah Ada Di Daftar Kategori!");
                }
                formSetController.refreshTable();
            }else {
                method.confirmationAlert("Limit Dan nama kategori tidak boleh kosong");
            }
    }



}
