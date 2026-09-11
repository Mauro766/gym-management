package com.gym.controller;

import com.gym.model.EstadoPago;
import com.gym.model.Pago;
import com.gym.model.Socio;
import com.gym.service.SocioService;
import com.gym.util.NavigationManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;

public class DashboardController {
    @FXML
    private Label totalSociosLabel, activosLabel, pagosRealizadosLabel, pendientesLabel;
    @FXML
    private TableView<Pago> pendingTable;
    @FXML
    private TableColumn<Pago, String> pendingMemberColumn, pendingPeriodColumn;
    @FXML
    private TableColumn<Pago, Double> pendingAmountColumn;
    @FXML
    private TableColumn<Pago, LocalDate> pendingDueColumn;
    @FXML
    private TableView<Socio> recentTable;
    @FXML
    private TableColumn<Socio, String> recentNameColumn, recentDniColumn;
    @FXML
    private TableColumn<Socio, LocalDate> recentDateColumn;
    private final SocioService service;
    private final NavigationManager navigation;

    public DashboardController(SocioService service, NavigationManager navigation) {
        this.service = service;
        this.navigation = navigation;
    }

    @FXML
    private void initialize() {
        totalSociosLabel.setText(String.valueOf(service.obtenerSocios().size()));
        activosLabel.setText(String.valueOf(service.contarActivos()));
        pagosRealizadosLabel.setText(String.valueOf(service.contarPagos(EstadoPago.PAGADO)));
        pendientesLabel.setText(String.valueOf(service.contarPagos(EstadoPago.PENDIENTE)));
        pendingMemberColumn.setCellValueFactory(cell -> javafx.beans.binding.Bindings.createStringBinding(
                () -> service.obtenerSocioDePago(cell.getValue()).map(Socio::getNombreCompleto).orElse("-")));
        pendingPeriodColumn.setCellValueFactory(new PropertyValueFactory<>("periodo"));
        pendingAmountColumn.setCellValueFactory(new PropertyValueFactory<>("monto"));
        pendingDueColumn.setCellValueFactory(new PropertyValueFactory<>("fechaVencimiento"));
        pendingTable.setItems(FXCollections.observableArrayList(
                service.obtenerPagos().stream().filter(p -> p.getEstado() == EstadoPago.PENDIENTE).limit(5).toList()));
        recentNameColumn.setCellValueFactory(
                cell -> javafx.beans.binding.Bindings.createStringBinding(() -> cell.getValue().getNombreCompleto()));
        recentDniColumn.setCellValueFactory(new PropertyValueFactory<>("dni"));
        recentDateColumn.setCellValueFactory(new PropertyValueFactory<>("fechaAlta"));
        recentTable.setItems(FXCollections
                .observableArrayList(service.obtenerSocios().subList(0, Math.min(5, service.obtenerSocios().size()))));
    }

    @FXML
    private void openPending() {
        navigation.navigate("pagos", true);
    }
}