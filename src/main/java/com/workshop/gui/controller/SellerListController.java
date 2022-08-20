package com.workshop.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.workshop.Program;
import com.workshop.db.DbIntegrityException;
import com.workshop.gui.listeners.DataChangeListener;
import com.workshop.gui.util.Alerts;
import com.workshop.gui.util.Utils;
import com.workshop.model.entities.Seller;
import com.workshop.model.services.SellerService;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SellerListController implements Initializable, DataChangeListener {

    private SellerService service;

    @FXML
    private TableView<Seller> tableViewSellers;

    @FXML
    private TableColumn<Seller, Integer> tableColumnId;

    @FXML
    private TableColumn<Seller, String> tableColumnName;

    @FXML
    private TableColumn<Seller, String> tableColumnEmail;

    @FXML
    private TableColumn<Seller, Date> tableColumnBirthDate;

    @FXML
    private TableColumn<Seller, Double> tableColumnSalary;

    @FXML
    private TableColumn<Seller, Seller> tableColumnEDIT;

    @FXML
    private TableColumn<Seller, Seller> tableColumnREMOVE;

    @FXML
    private Button btNew;

    private ObservableList<Seller> obsList;

    public void onBtNewAction(ActionEvent event) {
        Seller seller = new Seller();
        createDialogForm(seller, Utils.currentStage(event), "SellerForm");
    }

    public void setSellerService(SellerService service) {
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

        List<Seller> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);

        tableViewSellers.setItems(obsList);

        initEditButtons();
        initRemoveButtons();
    }

    private void initializeNodes() {
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        tableColumnSalary.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));

        Stage stage = (Stage) Program.getMainScene().getWindow();

        tableViewSellers.prefHeightProperty().bind(stage.heightProperty());
    }

    private void initEditButtons() {
        tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {
            private final Button button = new Button("edit");

            @Override
            protected void updateItem(Seller obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> createDialogForm(obj, Utils.currentStage(event), "SellerForm"));
            }
        });
    }

    private void initRemoveButtons() {
        tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
            private final Button button = new Button("remove");

            @Override
            protected void updateItem(Seller obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> removeEntity(obj));
            }
        });
    }

    private void removeEntity(Seller Seller) {
        Optional<ButtonType> optional = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");

        optional.ifPresent(result -> {
            if (service == null) {
                throw new IllegalStateException("Service was null");
            }

            if (result == ButtonType.OK) {
                try {
                    service.remove(Seller);
                    updateTableView();
                } catch (DbIntegrityException e) {
                    Alerts.showAlert("Error removing Seller", null, e.getMessage(), AlertType.ERROR);
                }
                
            }
        });

    }

    private void createDialogForm(Seller Seller, Stage parentStage, String viewName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Program.class.getResource(viewName + ".fxml"));
            Pane pane = fxmlLoader.load();

            SellerFormController controller = fxmlLoader.getController();
            controller.setSeller(Seller);
            controller.setSellerService(new SellerService());
            controller.subscribeDataChangeListeners(this);
            controller.UpdateFormData();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter Seller data");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();

        } catch (IOException e) {
            Alerts.showAlert("IO Exception", "Error loading view", e.getMessage(), AlertType.ERROR);
        }
    }

    @Override
    public void onDataChange() {
        updateTableView();
    }

}
