package com.gym;

import javafx.application.Application;
import javafx.scene.Scene;
import com.gym.controller.MainController;
import com.gym.repository.SocioRepository;
import com.gym.service.SocioService;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        SocioService socioService = new SocioService(new SocioRepository());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gym/view/main.fxml"));
        loader.setControllerFactory(type -> type == MainController.class
            ? new MainController(socioService) : null);
        Scene scene = new Scene(loader.load(), 1360, 820);
        scene.getStylesheets().add(getClass().getResource("/com/gym/css/style.css").toExternalForm());

        stage.setTitle("Sistema de Gimnasio");
        stage.setScene(scene);
        stage.setMinWidth(1100);
        stage.setMinHeight(700);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
