package com.workshop.gui.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.workshop.App;
import com.workshop.model.entities.Department;
import com.workshop.model.services.DepartmentService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

    public void onBtNewAction() {
        System.out.println("onBtNewAction");
    }

    public void setDepartmentService(DepartmentService service) {
        this.service = service;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
        setDepartmentService(new DepartmentService());
        updateTableView();
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

        Stage stage = (Stage) App.getMainScene().getWindow();

        tableViewDepartments.prefHeightProperty().bind(stage.heightProperty());
    }
    
}
