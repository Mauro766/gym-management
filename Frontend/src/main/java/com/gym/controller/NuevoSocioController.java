package com.gym.controller;

import com.gym.model.Socio;
import com.gym.service.SocioService;
import com.gym.util.NavigationManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;

public class NuevoSocioController {
    @FXML
    private TextField nameField, lastNameField, dniField, phoneField, emailField;
    @FXML
    private DatePicker datePicker;
    private final SocioService service;
    private final NavigationManager navigation;

    public NuevoSocioController(SocioService service, NavigationManager navigation) {
        this.service = service;
        this.navigation = navigation;
    }

    @FXML
    private void initialize() {
        datePicker.setValue(LocalDate.now());
    }

    @FXML
    private void cancel() {
        navigation.navigate("socios");
    }

    @FXML
    private void save() {
        if (nameField.getText().isBlank() || lastNameField.getText().isBlank() || dniField.getText().isBlank())
            return;
        int id = service.obtenerSocios().stream().mapToInt(Socio::getId).max().orElse(0) + 1;
        service.guardarSocio(new Socio(id, nameField.getText(), lastNameField.getText(), dniField.getText(),
                phoneField.getText(), emailField.getText(), datePicker.getValue(), true));
        navigation.navigate("socios");
    }
}