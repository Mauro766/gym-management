package com.gym.controller;

import com.gym.service.SocioService;
import com.gym.util.NavigationManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class MainController {
    @FXML
    private StackPane contentPane;
    @FXML
    private Label pageHeading;
    private final SocioService service;
    private NavigationManager navigation;

    public MainController(SocioService service) {
        this.service = service;
    }

    @FXML
    private void initialize() {
        navigation = new NavigationManager(contentPane, service);
        goDashboard();
    }

    @FXML
    private void goDashboard() {
        pageHeading.setText("Resumen general");
        navigation.navigate("dashboard");
    }

    @FXML
    private void goSocios() {
        pageHeading.setText("Socios");
        navigation.navigate("socios");
    }

    @FXML
    private void goPagos() {
        pageHeading.setText("Pagos");
        navigation.navigate("pagos");
    }

    @FXML
    private void goPendientes() {
        pageHeading.setText("Pagos pendientes");
        navigation.navigate("pagos", true);
    }
}