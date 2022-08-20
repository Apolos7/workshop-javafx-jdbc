package com.workshop.gui.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.workshop.db.DbException;
import com.workshop.gui.listeners.DataChangeListener;
import com.workshop.gui.util.Alerts;
import com.workshop.gui.util.Constraints;
import com.workshop.gui.util.Utils;
import com.workshop.model.entities.Department;
import com.workshop.model.services.DepartmentService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class DepartmentFormController implements Initializable {

    private Department entity;

    private DepartmentService service;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private Label labelErrorId;

    @FXML
    private Label labelErrorName;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    @FXML
    public void onBtSaveAction(ActionEvent event) {
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }
        try {
            entity = getFormData();
            service.saveOrUpdate(entity);
            notifyDataChangeListeners();
            Utils.currentStage(event).close();
        } catch (DbException e) {
            Alerts.showAlert("Error saving department", null, e.getMessage(), AlertType.ERROR);
        }

    }

    @FXML
    public void onBtCancelAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }

    public void setDepartment(Department entity) {
        this.entity = entity;
    }

    public void setDepartmentService(DepartmentService service) {
        this.service = service;
    }

    public void UpdateFormData() {
        if (entity == null) {
            throw new IllegalStateException("Entity was null!");
        }
        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 30);
    }

    private Department getFormData() {
        Department department = new Department();
        department.setId(Utils.tryParseToInt(txtId.getText()));
        department.setName(txtName.getText());
        return department;
    }

    public void subscribeDataChangeListeners(DataChangeListener dataChangeListener) {
        dataChangeListeners.add(dataChangeListener);
    }

    private void notifyDataChangeListeners() {
        dataChangeListeners.forEach(listener -> {
            listener.onDataChange();
        });
    }

}
