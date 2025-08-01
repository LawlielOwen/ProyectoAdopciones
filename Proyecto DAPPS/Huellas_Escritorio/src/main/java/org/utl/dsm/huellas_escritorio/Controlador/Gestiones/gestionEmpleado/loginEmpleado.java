package org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionEmpleado;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import org.utl.dsm.huellas_escritorio.Controlador.PanelAdoptantes.cambioModulo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class loginEmpleado implements Initializable {
    cambioModulo c = new cambioModulo();
    @FXML
    private Button btnLogin;

    @FXML
    private Button btnVolver;

    @FXML
    private PasswordField contra;
    @FXML
    private Label labelContra;

    @FXML
    private Label labelCorreo;
    @FXML
    private TextField correo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cambioModulo c = new cambioModulo();
        btnVolver.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Clientes/inicio.fxml", "Iniciar Sesión", btnVolver));
        configurarFloatingLabel(correo, labelCorreo);
        configurarFloatingLabel(contra, labelContra);
        btnLogin.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Mascotas.fxml","Gestion Mascotas",btnLogin));
    }
    private void configurarFloatingLabel(TextField campo, Label etiqueta) {
        campo.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal || !campo.getText().isEmpty()) {
                if (!etiqueta.getStyleClass().contains("activa")) {
                    etiqueta.getStyleClass().add("activa");
                }
            } else {
                etiqueta.getStyleClass().remove("activa");
            }
        });

        if (!campo.getText().isEmpty()) {
            etiqueta.getStyleClass().add("activa");
        }
    }

    public void cambiarPantalla(String rutaFXML, String titulo, Button botonReferencia) {
        try {
            Stage stage = (Stage) botonReferencia.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(rutaFXML));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.setWidth(1106);
            stage.setHeight(600);
            stage.setTitle(titulo);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
