package org.utl.dsm.huellas_escritorio;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
           Parent root = FXMLLoader.load(Main.class.getResource("/org/utl/dsm/huellas_escritorio/Clientes/inicio.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setTitle("Huellitas Suaves");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true); // Esto la pone al tamaño completo de la pantalla (ventana maximizada)
        primaryStage.show();



        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
