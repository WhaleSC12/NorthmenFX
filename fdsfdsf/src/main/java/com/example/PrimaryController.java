package com.example;

import com.DegreeEZ.DegreeWorksApplication;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PrimaryController {

    @FXML
    private void handleButtonClick() {
        //need to put login logic here
        DegreeWorksApplication app = DegreeWorksApplication.getInstance();

        if(app.login("braxa", "braxb") == null){
            System.out.println("Error loggin in");
        }
        else {
            System.out.println("YAY!");
        }
    }
}
