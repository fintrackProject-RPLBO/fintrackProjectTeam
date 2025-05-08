package com.fintrack.form.uiController;

import com.fintrack.form.dataBaseManager.DBConnection;
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

public class EditCatatanPageController {
    UserData userData = UserData.getInstance();
    Session session = Session.getInstance();
    MethodCollection method = new MethodCollection();
    CatatanKeuanganTable catatanKeuanganTable = CatatanKeuanganTable.getInstance();
    CategoryTable categoryTable = CategoryTable.getInstance();

    @FXML
    private ComboBox<String> category;
    @FXML private TextField priceField;
    @FXML private DatePicker date;
    @FXML private TextField descriptionField;

    private FormSetController formSetController;


    public EditCatatanPageController() throws SQLException {}

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

        setupValue();
    }

    @FXML
    private void setupValue() throws SQLException {
        String kategori = session.getClickedData()[0].toString();
        Double harga = Double.parseDouble( session.getClickedData()[1].toString());
        String tanggal =  session.getClickedData()[2].toString();
        String deskripsi =  session.getClickedData()[3].toString();

        category.setValue(kategori);
        priceField.setText(String.valueOf(harga));
        date.setValue(LocalDate.parse(tanggal));
        descriptionField.setText(deskripsi);
    }

    @FXML
    private void editCatatan() throws SQLException {
        catatanKeuanganTable.editCatatan(category.getValue().toString(), Double.parseDouble(priceField.getText().split(" ")[1]), date.getValue().toString(),descriptionField.getText(), method.getNowDateTime());
        formSetController.refreshTable();
        formSetController.removeForm();
        session.unsetClickedDataKategori();
    }

    @FXML
    private void deleteCatatan() throws SQLException {
        catatanKeuanganTable.deleteCatatan();
        formSetController.refreshTable();
        formSetController.removeForm();
        session.unsetClickedDataKategori();
    }

}
