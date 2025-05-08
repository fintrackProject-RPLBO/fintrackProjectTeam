package com.fintrack.form.uiController;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class MethodCollection {

    public boolean confirmationAlert(String messege){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(null);
        alert.setHeaderText(null); // or set a custom header
        alert.setContentText(messege);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }

    public boolean isValid(String str){
        int alphaCounter = 0;
        int numberCounter = 0;
        int symbolCounter = 0;


        for(Character i:str.toCharArray()){
            if (isAlpha(i)){
                alphaCounter++;
            }
            if (isNum(i.toString())){
                numberCounter++;
            }
            if (!isAlpha(i) && !isNum(i.toString())){
                symbolCounter++;
            }
        }
        if (alphaCounter < 6 && numberCounter < 1 && symbolCounter < 1){
            return false;
        }
        return true;
    }

    public boolean isAlpha(Character chr) {
        if (chr == null) {
            return false;
        }

        if (Character.isLetter(chr)){
            return true;
        }
        return true;
    }

    public boolean isNum(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str); // or Integer.parseInt(str) if you want only integers
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String getNowDateTime(){
        LocalDate currentDate = LocalDate.now();
        System.out.println("Date: " + currentDate);

        // Current time
        LocalTime currentTime = LocalTime.now();
        System.out.println("Time: " + currentTime);

        // Current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println("DateTime: " + currentDateTime);

        // Formatted date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatted = currentDateTime.format(formatter);
        System.out.println("Formatted: " + formatted);
        return formatted;
    }
}
