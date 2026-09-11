package com.gym.controller;

import com.gym.model.Pago;
import com.gym.model.Socio;
import com.gym.service.SocioService;
import com.gym.util.NavigationManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;

public class DetalleSocioController {
    @FXML private Label fullNameLabel, dniLabel, phoneLabel, emailLabel, dateLabel, statusLabel;
    @FXML private TableView<Pago> paymentsTable;
    @FXML private TableColumn<Pago, String> periodColumn, stateColumn;
    @FXML private TableColumn<Pago, Double> amountColumn;
    @FXML private TableColumn<Pago, LocalDate> dueColumn, paidColumn;
    private final SocioService service; private final NavigationManager navigation; private final Socio member;
    public DetalleSocioController(SocioService service, NavigationManager navigation, Object parameter) { this.service = service; this.navigation = navigation; this.member = parameter instanceof Socio ? (Socio) parameter : service.obtenerSocios().get(0); }
    @FXML private void initialize() {
        fullNameLabel.setText(member.getNombreCompleto()); dniLabel.setText(member.getDni()); phoneLabel.setText(member.getTelefono()); emailLabel.setText(member.getEmail()); dateLabel.setText(member.getFechaAlta().toString()); statusLabel.setText(member.isActivo() ? "Activo" : "Inactivo");
        periodColumn.setCellValueFactory(new PropertyValueFactory<>("periodo")); amountColumn.setCellValueFactory(new PropertyValueFactory<>("monto")); dueColumn.setCellValueFactory(new PropertyValueFactory<>("fechaVencimiento")); paidColumn.setCellValueFactory(new PropertyValueFactory<>("fechaPago")); stateColumn.setCellValueFactory(cell -> javafx.beans.binding.Bindings.createStringBinding(() -> cell.getValue().getEstado().name()));
        paymentsTable.setItems(FXCollections.observableArrayList(service.obtenerPagos().stream().filter(p -> p.getSocioId() == member.getId()).toList()));
    }
    @FXML private void back() { navigation.navigate("socios"); }
}