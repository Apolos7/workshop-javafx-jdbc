package com.workshop.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.workshop.Program;
import com.workshop.gui.util.Alerts;
import com.workshop.gui.util.Utils;
import com.workshop.model.entities.Department;
import com.workshop.model.services.DepartmentService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DepartmentListController implements Initializable {

    private DepartmentService service;

    @FXML
    private TableView<Department> tableViewDepartments;

    @FXML
    private TableColumn<Department, Integer> tableColumnId;

    @FXML
    private TableColumn<Department, String> tableColumnName;

    @FXML
    private Button btNew;

    private ObservableList<Department> obsList;

    public void onBtNewAction(ActionEvent event) {
        Department department = new Department();
        createDialogForm(department ,Utils.currentStage(event), "DepartmentForm");
    }

    public void setDepartmentService(DepartmentService service) {
        this.service = service;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    public void updateTableView() {
        if (service == null) {
            throw new IllegalStateException("Service was null.");
        }

        List<Department> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);

        tableViewDepartments.setItems(obsList);
    }

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

        Stage stage = (Stage) Program.getMainScene().getWindow();

        tableViewDepartments.prefHeightProperty().bind(stage.heightProperty());
    }

    private void createDialogForm(Department department, Stage parentStage, String viewName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Program.class.getResource(viewName + ".fxml"));
            Pane pane = fxmlLoader.load();

            DepartmentFormController controller = fxmlLoader.getController();
            controller.setDepartment(department);
            controller.UpdateFormData();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter Department data");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();

        }
        catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
        }
    }
    
}
