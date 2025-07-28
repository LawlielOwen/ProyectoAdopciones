package org.utl.dsm.huellas_escritorio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/org/utl/dsm/huellas_escritorio/Clientes/inicio.fxml"));
        Scene scene = new Scene(root);

        try {

            Image icon = new Image(getClass().getResourceAsStream("/Iconos/favicon/favicon-256x256.png"));
            primaryStage.getIcons().add(icon);
        } catch (Exception e) {
            System.err.println("Error al cargar el icono: " + e.getMessage());

        }

        primaryStage.setTitle("Huellitas Suaves");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true); // Corregido el nombre del método
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}