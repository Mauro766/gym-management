package com.gym.controller;

import com.gym.model.EstadoPago;
import com.gym.model.Pago;
import com.gym.model.Socio;
import com.gym.service.SocioService;
import com.gym.util.NavigationManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;

public class PagosController {
    @FXML
    private Label titleLabel;
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> stateFilter;
    @FXML
    private TableView<Pago> paymentsTable;
    @FXML
    private TableColumn<Pago, String> memberColumn, periodColumn, stateColumn;
    @FXML
    private TableColumn<Pago, Double> amountColumn;
    @FXML
    private TableColumn<Pago, LocalDate> dueColumn, paidColumn;
    @FXML
    private TableColumn<Pago, Pago> actionColumn;
    private final SocioService service;
    private final boolean pendingOnly;

    public PagosController(SocioService service, NavigationManager navigation, Object parameter) {
        this.service = service;
        this.pendingOnly = Boolean.TRUE.equals(parameter);
    }

    @FXML
    private void initialize() {
        titleLabel.setText(pendingOnly ? "Pagos pendientes" : "Pagos");
        stateFilter.getItems().setAll("Todos", "Pagados", "Pendientes");
        stateFilter.getSelectionModel().select(pendingOnly ? "Pendientes" : "Todos");
        memberColumn.setCellValueFactory(cell -> javafx.beans.binding.Bindings.createStringBinding(
                () -> service.obtenerSocioDePago(cell.getValue()).map(Socio::getNombreCompleto).orElse("-")));
        periodColumn.setCellValueFactory(new PropertyValueFactory<>("periodo"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("monto"));
        dueColumn.setCellValueFactory(new PropertyValueFactory<>("fechaVencimiento"));
        paidColumn.setCellValueFactory(new PropertyValueFactory<>("fechaPago"));
        stateColumn.setCellValueFactory(
                cell -> javafx.beans.binding.Bindings.createStringBinding(() -> cell.getValue().getEstado().name()));
        actionColumn.setCellFactory(col -> new TableCell<>() {
            private final Button button = new Button("Marcar como pagado");
            {
                button.getStyleClass().add("table-action");
                button.setOnAction(e -> {
                    service.marcarPagoComoPagado(getTableView().getItems().get(getIndex()));
                    refresh();
                });
            }

            @Override
            protected void updateItem(Pago item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty || item.getEstado() == EstadoPago.PAGADO ? null : button);
            }
        });
        searchField.textProperty().addListener((obs, old, value) -> refresh());
        stateFilter.valueProperty().addListener((obs, old, value) -> refresh());
        refresh();
    }

    private void refresh() {
        String query = searchField.getText() == null ? "" : searchField.getText().toLowerCase();
        String filter = stateFilter.getValue();
        paymentsTable.setItems(FXCollections.observableArrayList(service.obtenerPagos().stream()
                .filter(p -> service.obtenerSocioDePago(p).map(s -> s.getNombreCompleto().toLowerCase().contains(query))
                        .orElse(false))
                .filter(p -> "Todos".equals(filter) || ("Pagados".equals(filter) ? p.getEstado() == EstadoPago.PAGADO
                        : p.getEstado() == EstadoPago.PENDIENTE))
                .toList()));
    }
}