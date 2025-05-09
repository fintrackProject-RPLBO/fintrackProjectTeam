package com.fintrack.form.uiController;

import com.fintrack.form.dataBaseManager.Session;
import com.fintrack.form.dataBaseManager.Encryption;
import com.fintrack.form.tableManager.UserData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import java.sql.SQLException;


public class DeleteAccountPageController {
    UserData userData = UserData.getInstance();
    Session session = Session.getInstance();
    Encryption encryption = new Encryption(10);
    MethodCollection method = new MethodCollection();

    @FXML private PasswordField passwordField;
    @FXML private PasswordField passwordFieldRe_enter;
    @FXML private Button deleteButton;

    private FormSetController formSetController;
    public void setFormSetController(FormSetController controller) {
        this.formSetController = controller;
    }

    public DeleteAccountPageController() throws SQLException {
    }

    @FXML
    private void initialize() {
        passwordField.setOnAction(event -> deleteButton.fire());
        passwordFieldRe_enter.setOnAction(event -> deleteButton.fire());
    }

    @FXML
    void deleteBtn() throws SQLException {
        if (session.getUsername() != null){
            System.out.println(session.getUsername());
            String username = session.getUsername();
            String password = passwordField.getText().strip();
            String rePassword = passwordFieldRe_enter.getText().strip();

            String userPassword = encryption.decryption(userData.getUserPassword(username));

            if (password.isEmpty() || rePassword.isEmpty()){
                method.confirmationAlert("password tidak boleh kosong");
            }else{
                if (password.equals(rePassword) && password.equals(userPassword)){
                    if (method.confirmationAlert("Are you sure?")){
                        formSetController.logoutBtn();
                        userData.deleteAccount(username,password);
                        formSetController.addingUserDataToTable();
                    }else{
                        method.confirmationAlert("Delete akun dibatalkan");
                    }
                }else{
                    method.confirmationAlert("Password Salah!");
                }
            }
        }else{
            method.confirmationAlert("anda Belum Login!");
        }
    }



    @FXML
    void cancelBtn(){
        passwordField.clear();
        passwordFieldRe_enter.clear();
        formSetController.removeForm();
    }

}
