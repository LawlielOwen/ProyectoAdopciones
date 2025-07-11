package org.utl.dsm.huellas_escritorio.Controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class registro implements Initializable {
    @FXML
    private Button btnRegistrar;

    @FXML
    private Button btnVolver;

    @FXML
    private TextField contra;

    @FXML
    private TextField txtApm;

    @FXML
    private TextField txtApp;

    @FXML
    private TextField txtCorreo;

    @FXML
    private DatePicker txtFechaNacimiento;

    @FXML
    private TextField txtGenero;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnVolver.setOnAction(event -> cambiarPantalla("/org/utl/dsm/huellas_escritorio/Clientes/inicio.fxml", "Registro", btnVolver));

    }

    private void cambiarPantalla(String rutaFXML, String titulo, Button botonReferencia) {
        try {
            Stage stage = (Stage) botonReferencia.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(rutaFXML));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.setWidth(1106);
            stage.setHeight(600);
            stage.setTitle(titulo);

            stage.setTitle(titulo);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
