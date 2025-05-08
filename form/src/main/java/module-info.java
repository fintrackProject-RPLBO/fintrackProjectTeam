module com.fintrack.form {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.fintrack.form to javafx.fxml;
    exports com.fintrack.form;
}