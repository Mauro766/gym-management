package com.gym.util;

import com.gym.controller.*;
import com.gym.service.SocioService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import java.io.IOException;

public class NavigationManager {
    private final StackPane content;
    private final SocioService service;
    public NavigationManager(StackPane content, SocioService service) { this.content = content; this.service = service; }
    public void navigate(String view) { navigate(view, null); }
    public void navigate(String view, Object parameter) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gym/view/" + view + ".fxml"));
            loader.setControllerFactory(type -> createController(type, parameter));
            content.getChildren().setAll((Node) loader.load());
        } catch (IOException exception) { throw new IllegalStateException("No se pudo cargar la vista " + view, exception); }
    }
    private Object createController(Class<?> type, Object parameter) {
        if (type == DashboardController.class) return new DashboardController(service, this);
        if (type == SociosController.class) return new SociosController(service, this);
        if (type == NuevoSocioController.class) return new NuevoSocioController(service, this);
        if (type == DetalleSocioController.class) return new DetalleSocioController(service, this, parameter);
        if (type == PagosController.class) return new PagosController(service, this, parameter);
        throw new IllegalArgumentException("Controlador no registrado: " + type.getName());
    }
}