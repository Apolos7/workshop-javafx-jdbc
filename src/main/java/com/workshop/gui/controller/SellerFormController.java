package com.workshop.gui.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.workshop.db.DbException;
import com.workshop.gui.listeners.DataChangeListener;
import com.workshop.gui.util.Alerts;
import com.workshop.gui.util.Constraints;
import com.workshop.gui.util.Utils;
import com.workshop.model.entities.Seller;
import com.workshop.model.exceptions.ValidationException;
import com.workshop.model.services.SellerService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class SellerFormController implements Initializable {

    private Seller entity;

    private SellerService service;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private DatePicker datePickerBirthDate;

    @FXML
    private TextField txtBaseSalary;

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
            throw new IllegalStateException("Seller was null");
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
            Alerts.showAlert("Error saving seller", null, e.getMessage(), AlertType.ERROR);
        } catch (ValidationException e) {
            setErrorsMessages(e.getErros());
        }
    }

    @FXML
    public void onBtCancelAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }

    public void setSeller(Seller entity) {
        this.entity = entity;
    }

    public void setSellerService(SellerService service) {
        this.service = service;
    }

    public void UpdateFormData() {
        if (entity == null) {
            throw new IllegalStateException("Seller was null!");
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

    private Seller getFormData() throws ValidationException {
        Seller seller = new Seller();

        ValidationException exception = new ValidationException("Validation Error");

        seller.setId(Utils.tryParseToInt(txtId.getText()));

        if (txtName.getText() == null || txtName.getText().trim().equals("")) {
            exception.addError("name", "field can't be empty");
        }
        seller.setName(txtName.getText());

        if (exception.getErros().size() > 0) {
            throw exception;
        }

        return seller;
    }

    private void setErrorsMessages(Map<String, String> erros) {
        Set<String> fields = erros.keySet();

        if (fields.contains("name")) {
            labelErrorName.setText(erros.get("name"));
        }

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
