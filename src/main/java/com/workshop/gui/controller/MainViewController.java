package com.workshop.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import com.workshop.Program;
import com.workshop.gui.util.Alerts;
import com.workshop.model.services.DepartmentService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;

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
        loadView("DepartmentList", (DepartmentListController controller) -> {
            controller.setDepartmentService(new DepartmentService());
            controller.updateTableView();
        });
    }

    @FXML
    public void onMenuitemAboutAction() {
        loadView("About", x -> {});
    }

    private synchronized <T> void loadView(String viewName, Consumer<T> initializationFunction) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Program.class.getResource(viewName + ".fxml"));

            VBox newVbBox = (VBox) fxmlLoader.load();

            Scene mainScene = Program.getMainScene();
            VBox mainVbox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

            Node mainMenu = mainVbox.getChildren().get(0);
            mainVbox.getChildren().clear();
            mainVbox.getChildren().add(mainMenu);
            mainVbox.getChildren().addAll(newVbBox.getChildren());

            T controller = fxmlLoader.getController();
            initializationFunction.accept(controller);

        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

}
