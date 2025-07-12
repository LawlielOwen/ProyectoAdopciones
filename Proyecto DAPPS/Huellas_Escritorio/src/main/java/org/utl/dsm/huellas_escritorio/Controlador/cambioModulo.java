package org.utl.dsm.huellas_escritorio.Controlador;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class cambioModulo {
    public void cambiarPantalla(String rutaFXML, String titulo, Button botonReferencia) {
        try {
            Stage stage = (Stage) botonReferencia.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(rutaFXML));


            Scene nuevaScene = new Scene(root, stage.getWidth(), stage.getHeight());
            stage.setScene(nuevaScene);
            stage.setTitle(titulo);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void cambiarPantallaMenu(String rutaFXML, String titulo,  MenuItem botonReferencia) {
        try {
            Stage stage = (Stage) ((Node) botonReferencia.getParentPopup().getOwnerNode()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(rutaFXML));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.setWidth(1306);
            stage.setHeight(800);
            stage.setTitle(titulo);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
