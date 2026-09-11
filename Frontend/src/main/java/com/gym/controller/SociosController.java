package com.gym.controller;

import com.gym.model.Socio;
import com.gym.service.SocioService;
import com.gym.util.NavigationManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import java.time.LocalDate;

public class SociosController {
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> statusFilter;
    @FXML
    private TableView<Socio> membersTable;
    @FXML
    private TableColumn<Socio, String> nameColumn, dniColumn, phoneColumn, statusColumn;
    @FXML
    private TableColumn<Socio, LocalDate> dateColumn;
    @FXML
    private TableColumn<Socio, Socio> actionColumn;
    private final SocioService service;
    private final NavigationManager navigation;

    public SociosController(SocioService service, NavigationManager navigation) {
        this.service = service;
        this.navigation = navigation;
    }

    @FXML
    private void initialize() {
        statusFilter.getItems().setAll("Todos", "Activos", "Inactivos");
        statusFilter.getSelectionModel().selectFirst();
        nameColumn.setCellValueFactory(
                cell -> javafx.beans.binding.Bindings.createStringBinding(() -> cell.getValue().getNombreCompleto()));
        dniColumn.setCellValueFactory(new PropertyValueFactory<>("dni"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("fechaAlta"));
        statusColumn.setCellValueFactory(cell -> javafx.beans.binding.Bindings
                .createStringBinding(() -> cell.getValue().isActivo() ? "Activo" : "Inactivo"));
        actionColumn.setCellFactory(column -> new TableCell<>() {
            private final Button view = new Button("Ver");
            private final Button edit = new Button("Editar");
            private final HBox box = new HBox(8, view, edit);
            {
                view.getStyleClass().add("table-action");
                edit.getStyleClass().add("table-action");
                view.setOnAction(e -> navigation.navigate("detalle-socio", getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Socio item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });
        searchField.textProperty().addListener((obs, old, value) -> refresh());
        statusFilter.valueProperty().addListener((obs, old, value) -> refresh());
        refresh();
    }

    private void refresh() {
        String query = searchField.getText() == null ? "" : searchField.getText().toLowerCase();
        String filter = statusFilter.getValue();
        membersTable.setItems(FXCollections.observableArrayList(service.obtenerSocios().stream()
                .filter(s -> s.getNombreCompleto().toLowerCase().contains(query) || s.getDni().contains(query))
                .filter(s -> "Todos".equals(filter) || ("Activos".equals(filter) == s.isActivo())).toList()));
    }

    @FXML
    private void newMember() {
        navigation.navigate("nuevo-socio");
    }
}