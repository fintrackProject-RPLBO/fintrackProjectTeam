package com.fintrack.form.uiController;

import com.fintrack.form.dataBaseManager.Session;
import com.fintrack.form.tableManager.CatatanKeuanganTable;
import com.fintrack.form.tableManager.CategoryTable;
import com.fintrack.form.tableManager.UserData;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class EditKategoriPageController {
    UserData userData = UserData.getInstance();
    Session session = Session.getInstance();
    MethodCollection method = new MethodCollection();
    CategoryTable categoryTable = CategoryTable.getInstance();

    @FXML private TextField namaKategori;
    @FXML private TextField limit;

    private FormSetController formSetController;


    public EditKategoriPageController() throws SQLException {}

    public void setFormSetController(FormSetController controller) {
        this.formSetController = controller;
    }

    @FXML
    private void initialize() throws SQLException {
        limit.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.startsWith("Rp. ")) {
                limit.setText("Rp. " + newVal.replace("Rp. ", ""));
            }});

        setupValue();
    }

    @FXML
    private void setupValue() throws SQLException {
        String kategori = session.getClickedDataKategori()[0].toString();
        Double harga = Double.parseDouble( session.getClickedDataKategori()[1].toString());

        namaKategori.setText(kategori);
        limit.setText(String.valueOf(harga));
    }

    @FXML
    private void editKategori() throws SQLException {
        categoryTable.editKategori(namaKategori.getText(), Double.parseDouble(limit.getText().split(" ")[1]));
        formSetController.refreshTable();
        formSetController.removeForm();
        session.setClickedDataKategori(null);
    }

    @FXML
    private void deleteKategori() throws SQLException {
        categoryTable.deleteKategori();
        formSetController.refreshTable();
        formSetController.removeForm();
        session.setClickedDataKategori(null);
    }
}
