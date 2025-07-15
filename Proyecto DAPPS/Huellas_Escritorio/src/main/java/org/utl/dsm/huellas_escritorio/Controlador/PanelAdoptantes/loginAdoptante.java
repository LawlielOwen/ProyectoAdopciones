package org.utl.dsm.huellas_escritorio.Controlador.PanelAdoptantes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class loginAdoptante implements Initializable {
    @FXML
    private Button btnLogin;

    @FXML
    private Button btnVolver;

    @FXML
    private TextField contra;

    @FXML
    private Label labelContra;

    @FXML
    private Label labelCorreo;
    @FXML
    private TextField correo;

    @FXML
    private Button registro;

    @FXML
    private Label welcomeText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cambioModulo c = new cambioModulo();
        configurarFloatingLabel(correo, labelCorreo);
        configurarFloatingLabel(contra, labelContra);

        btnVolver.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Clientes/inicio.fxml", "Iniciar Sesión", btnVolver));
        registro.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Clientes/registro.fxml", "Registro", registro));
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
