package org.utl.dsm.huellas_escritorio.Controlador.PanelAdoptantes;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
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
            Scene nuevaScene = new Scene(root, stage.getWidth(), stage.getHeight());
            stage.setScene(nuevaScene);
            stage.setTitle(titulo);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void cambiarPantallaStackPane(String rutaFXML, String titulo,  StackPane botonReferencia) {
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
}
