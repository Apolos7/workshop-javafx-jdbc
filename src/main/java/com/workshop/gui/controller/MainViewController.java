package com.workshop.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable {

    @FXML
    private MenuItem menuItemSeller;

    @FXML
    private MenuItem menuItemDepartment;

    @FXML
    private MenuItem menuItemAbout;

    @FXML
    public void onMenuitemSellerAction() {
        System.out.println("onMenuitemSellerAction");
    }

    @FXML
    public void onMenuitemDepartmentAction() {
        System.out.println("onMenuitemDepartmentAction");
    }

    @FXML
    public void onMenuitemAboutAction() {
        System.out.println("onMenuitemAboutAction");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


    
}
