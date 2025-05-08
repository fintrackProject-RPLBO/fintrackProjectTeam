package com.fintrack.form.uiController;

import com.fintrack.form.tableManager.UserData;
import com.fintrack.form.dataBaseManager.Session;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.sql.SQLException;

public class LoginPageController {
    UserData userdata = UserData.getInstance();
    Session session = Session.getInstance();
    MethodCollection method = new MethodCollection();

    @FXML
    private TextField tfUsername;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private Button loginBtn;

    private FormSetController formSetController;

    public void setFormSetController(FormSetController controller) {
        this.formSetController = controller;
    }

    public LoginPageController() throws SQLException {

    }

    @FXML
    private void initialize() {
        tfUsername.setOnAction(event -> loginBtn.fire());
        pfPassword.setOnAction(event -> loginBtn.fire());
    }

    @FXML
    protected void loginBtn() throws SQLException {
        String username = tfUsername.getText().strip();
        String password = pfPassword.getText().strip();
        if(username.isEmpty() || password.isEmpty()){
            method.confirmationAlert("username dan password tidak boleh kosong");
        }
        else if(userdata.login(username,password) == 0){
            method.confirmationAlert("login berhasil!");
            session.setUsername(username);
            formSetController.setCurrentUserLabel("Current User: "+session.getUsername());
            formSetController.refreshTable();
        }
        else if(userdata.login(username,password) == 1){
            method.confirmationAlert("password salah!");
        }
        else{
            method.confirmationAlert("akun tidak ditemukan!");
        }


    }


    @FXML
    protected void registerBtn() throws SQLException {
        if (session.getUsername() != null){
            formSetController.logoutBtn();
        }
        formSetController.ShowRegisterForm();
    }


    public void setKeyAction(Scene scene){
        scene.setOnKeyPressed((KeyEvent event) -> {
            if(event.getCode() == KeyCode.ENTER){

            }
        });
    }

}