package com.fintrack.form.uiController;

import com.fintrack.form.dataBaseManager.Session;
import com.fintrack.form.tableManager.CatatanKeuanganTable;
import com.fintrack.form.tableManager.CategoryTable;
import com.fintrack.form.tableManager.UserData;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class FormSetController {
    CategoryTable categoryTable = CategoryTable.getInstance();
    Session session = Session.getInstance();
    MethodCollection method = new MethodCollection();


    @FXML private TableView<Object[]> tableView;
    @FXML private TableColumn<Object[], String> usernameColumn;
    @FXML private TableColumn<Object[], String> passwordColumn;

    @FXML private TableView<Object[]> kategoriTV;
    @FXML private TableColumn<Object[], String> kategoriTC;
    @FXML private TableColumn<Object[], String> limitTC;
    @FXML private TableColumn<Object[], String> userKategoriTC;


    @FXML private TableView<Object[]> catatanTV;
    @FXML private TableColumn<Object[], String> kategoriCatatanTC;
    @FXML private TableColumn<Object[], String> hargaTC;
    @FXML private TableColumn<Object[], String> tanggalTC;
    @FXML private TableColumn<Object[], String> deskripsiTC;
    @FXML private TableColumn<Object[], String> userTC;
    @FXML private TableColumn<Object[], String> dateUpdateTC;

    @FXML private Button editCatatanButton;
    @FXML private Button editKategoriButton;


    @FXML private Label currentUserLabel;

    @FXML
    private VBox mainVBox;

    private Node formNode; // To keep track of the form.fxml content
    private String nodePath = "";

    Object[] clickedData;
    Object[] clickedDataKategori;

    public FormSetController() throws SQLException {
    }


    String getCurrentUserLabel(){
        return currentUserLabel.getText();
    }

    void setCurrentUserLabel(String str){
        currentUserLabel.setText(str);
    }


    @FXML
    private void initialize(){
        catatanTV.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {  // or == 2 for double click
                Object[] selected = catatanTV.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    clickedData = selected;
                    System.out.println("Selected: " + Arrays.toString(clickedData));
                }
                String path = "/com/fintrack/form/EditCatatanPage.fxml";
                if (nodePath.equals(path)) {
                    // Same reference
                    editCatatanButton.fire();
                }
            }
        });

        kategoriTV.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {  // or == 2 for double click
                Object[] selected = kategoriTV.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    clickedDataKategori = selected;
                    System.out.println("Selected: " + Arrays.toString(clickedDataKategori));
                }
                String path = "/com/fintrack/form/EditKategoriPage.fxml";
                if (nodePath.equals(path)) {
                    // Same reference
                    editKategoriButton.fire();
                }

            }
        });

    }

    @FXML
    public void ShowLoginForm() {
        String path = "/com/fintrack/form/LoginPage.fxml";
        try {
            removeForm();
            formNode = FXMLLoader.load(getClass().getResource(path));
            nodePath = path;
            mainVBox.getChildren().add(formNode); // Add form.fxml below the button
            refreshTable();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent loginRoot = loader.load();
            // Get controller of login page
            LoginPageController loginController = loader.getController();
            // Inject this controller to login page
            loginController.setFormSetController(this);
            // Show login page inside mainVBox
            mainVBox.getChildren().setAll(loginRoot);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void ShowRegisterForm(){
        String path = "/com/fintrack/form/RegisterPage.fxml";
        try {
            removeForm();
            formNode = FXMLLoader.load(getClass().getResource(path));
            nodePath = path;
            mainVBox.getChildren().add(formNode); // Add form.fxml below the button
            refreshTable();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent loginRoot = loader.load();
            // Get controller of login page
            RegisterPageControl registerController = loader.getController();
            // Inject this controller to login page
            registerController.setFormSetController(this);
            // Show login page inside mainVBox
            mainVBox.getChildren().setAll(loginRoot);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void deleteAccountForm(){
        String path = "/com/fintrack/form/DeleteAccountPage.fxml";
        try {
            if (session.getUsername() != null){
                removeForm();
                formNode = FXMLLoader.load(getClass().getResource(path));
                nodePath = path;
                mainVBox.getChildren().add(formNode); // Add form.fxml below the button
                refreshTable();

                FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
                Parent loginRoot = loader.load();
                // Get controller of login page
                DeleteAccountPageController deleteAccountController = loader.getController();
                // Inject this controller to login page
                deleteAccountController.setFormSetController(this);
                // Show login page inside mainVBox
                mainVBox.getChildren().setAll(loginRoot);
            }else {
                method.confirmationAlert("Anda Belum Login!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void addCatatanForm(){
        String path = "/com/fintrack/form/AddCatatanPage.fxml";
        try {
            if (session.getUsername() != null){
                if (categoryTable.getAllDataKategori().size() > 0){
                    removeForm();
                    formNode = FXMLLoader.load(getClass().getResource(path));
                    nodePath = path;
                    mainVBox.getChildren().add(formNode); // Add form.fxml below the button
                    refreshTable();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
                    Parent loginRoot = loader.load();
                    // Get controller of login page
                    AddCatatanPageController addCatatanPageController = loader.getController();
                    // Inject this controller to login page
                    addCatatanPageController.setFormSetController(this);
                    // Show login page inside mainVBox
                    mainVBox.getChildren().setAll(loginRoot);
                }else{
                    method.confirmationAlert("Tambahkan Kategori Terlebih Dahulu!, Kategori Minimal 1");
                }

            }else{
                method.confirmationAlert("Anda Belum Login!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void addEditCatatanForm(){
        String path = "/com/fintrack/form/EditCatatanPage.fxml";
        try {
            if (session.getUsername() != null){
                session.setClickedData(clickedData);
                if (session.getClickedData() != null){
                    removeForm();
                    formNode = FXMLLoader.load(getClass().getResource(path));
                    nodePath = path;
                    mainVBox.getChildren().add(formNode); // Add form.fxml below the button
                    refreshTable();


                    FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
                    Parent loginRoot = loader.load();
                    // Get controller of login page
                    EditCatatanPageController editCatatanPageController = loader.getController();
                    // Inject this controller to login page
                    editCatatanPageController.setFormSetController(this);
                    // Show login page inside mainVBox
                    mainVBox.getChildren().setAll(loginRoot);
                }else{
                    method.confirmationAlert("Click Data Pada Tabel Terlebih Dahulu");
                }
            }else{
                method.confirmationAlert("Anda Belum Login!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void showAddCategoryForm(){
        String path = "/com/fintrack/form/AddCategoryPage.fxml";
        try {
            if (session.getUsername() != null){
                removeForm();
                formNode = FXMLLoader.load(getClass().getResource(path));
                nodePath = path;
                mainVBox.getChildren().add(formNode); // Add form.fxml below the button
                refreshTable();

                FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
                Parent loginRoot = loader.load();
                // Get controller of login page
                AddKategoriController addKategoriPageController = loader.getController();
                // Inject this controller to login page
                addKategoriPageController.setFormSetController(this);
                // Show login page inside mainVBox
                mainVBox.getChildren().setAll(loginRoot);

            }else{
                method.confirmationAlert("Anda Belum Login!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void addEditKategoriForm(){
        String path = "/com/fintrack/form/EditKategoriPage.fxml";
        try {
            if (session.getUsername() != null){
                session.setClickedDataKategori(clickedDataKategori);
                if (session.getClickedDataKategori() != null){
                    removeForm();
                    formNode = FXMLLoader.load(getClass().getResource(path));
                    nodePath = path;
                    mainVBox.getChildren().add(formNode); // Add form.fxml below the button
                    refreshTable();


                    FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
                    Parent loginRoot = loader.load();
                    // Get controller of login page
                    EditKategoriPageController editKategoriPageController = loader.getController();
                    // Inject this controller to login page
                    editKategoriPageController.setFormSetController(this);
                    // Show login page inside mainVBox
                    mainVBox.getChildren().setAll(loginRoot);
                }else{
                    method.confirmationAlert("Click Data Pada Tabel Terlebih Dahulu");
                }
            }else{
                method.confirmationAlert("Anda Belum Login!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void logoutBtn() throws SQLException {
        if (session.getUsername() == null){
            method.confirmationAlert("Anda Belum Login!");
        }else{
            session.setUsername(null);
            session.setClickedDataKategori(null);
            session.setClickedData(null);
            setCurrentUserLabel("Current User: ");
            removeForm();
            refreshTable();
        }
    }


    public void removeForm() {
        if (formNode != null) {
//            mainVBox.getChildren().remove(formNode);
            mainVBox.getChildren().clear();
            formNode = null;
            nodePath = null;
        }
    }
    void refreshTable() throws SQLException {
        addingUserDataToTable();
        addingDataCatatanToTable();
        addingCategoryDataToTable();
        clickedData = null;
    }
    @FXML
    public void addingUserDataToTable() throws SQLException {
        ArrayList<Object[]> rawData = UserData.getInstance().getAllData();

        System.out.println(rawData);
        for (Object[] i : rawData){
            System.out.print(i[0]+" ");
            System.out.println(i[1]);
        }

        ObservableList<Object[]> table = FXCollections.observableArrayList(rawData);
        tableView.setItems(table);
        usernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0].toString()));
        passwordColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1].toString()));
    }

    @FXML
    public void addingCategoryDataToTable() throws SQLException {
        ArrayList<Object[]> rawData = CategoryTable.getInstance().getAllDataKategori();

        System.out.println(rawData);
        for (Object[] i : rawData){
            System.out.print(i[0]+"---");
            System.out.println(i[1]);
        }

        ObservableList<Object[]> table = FXCollections.observableArrayList(rawData);
        kategoriTV.setItems(table);
        kategoriTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0].toString()));
        limitTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1].toString()));
        userKategoriTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2].toString()));
    }

    @FXML
    public void addingDataCatatanToTable() throws SQLException {
        ArrayList<Object[]> rawData = CatatanKeuanganTable.getInstance().getAllDataCatatan();

        System.out.println(rawData);
        for (Object[] i : rawData){
            System.out.print(i[0]+" ");
            System.out.println(i[1]);
        }

        ObservableList<Object[]> table = FXCollections.observableArrayList(rawData);
        catatanTV.setItems(table);


        kategoriCatatanTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0].toString()));
        hargaTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1].toString()));
        tanggalTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2].toString()));
        deskripsiTC.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3].toString()));
        userTC.setCellValueFactory(cellData -> {Object value = cellData.getValue()[4]; return new SimpleStringProperty(value != null ? value.toString() : " ");});
        dateUpdateTC.setCellValueFactory(cellData -> {Object dateTime = cellData.getValue()[5]; return new SimpleStringProperty(dateTime != null ? dateTime.toString() : " ");});

    }


    // BELUM JADII AKAN DI PERBAIKI :)
    public void editTableViewRowColor(TableView tableView){
//        tableView.setRowFactory(tv -> new TableRow<YourModel>() {
//            @Override
//            protected void updateItem(YourModel item, boolean empty) {
//                super.updateItem(item, empty);
//
//                if (item == null || empty) {
//                    setStyle("");
//                } else {
//                    // Example condition: make row red if value is below 0
//                    if (item.getAmount() < 0) {
//                        setStyle("-fx-background-color: #ffcccc;"); // light red
//                    } else {
//                        setStyle(""); // reset to default
//                    }
//                }
//            }
//        });

    }

}
