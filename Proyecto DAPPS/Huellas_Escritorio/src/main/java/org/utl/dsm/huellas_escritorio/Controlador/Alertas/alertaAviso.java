package org.utl.dsm.huellas_escritorio.Controlador.Alertas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class alertaAviso implements  Initializable {
    @FXML
    private Button btnIniciarSesion;
    private Stage ventanaPrincipal;

    public void setVentanaPrincipal(Stage ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
    }
    private Stage ventanaInformacion;
    public void setVentanaInformacion(Stage ventanaInformacion) {
        this.ventanaInformacion = ventanaInformacion;
    }

    @FXML
    private Button cerrar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cerrar.setOnAction(event -> {
            try {
                cerrarVentana();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
   btnIniciarSesion.setOnAction(event -> cambiarVentanaALogin());
    }


    private void cerrarVentana() {
        Stage stage = (Stage) cerrar.getScene().getWindow();
        stage.close();
    }
    private void cambiarVentanaALogin() {
        // Cerrar alerta
        Stage stageActual = (Stage) btnIniciarSesion.getScene().getWindow();
        stageActual.close();

        try {
            if (ventanaInformacion != null && ventanaInformacion != ventanaPrincipal) {
                ventanaInformacion.close();
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/utl/dsm/huellas_escritorio/Clientes/Login/login.fxml"));
            Parent root = loader.load();

            // Cierra ventana principal para reinicializar
            if (ventanaPrincipal != null) {
                ventanaPrincipal.close();
            }


            Stage nuevoStage = new Stage();
            nuevoStage.setScene(new Scene(root));
            nuevoStage.setTitle("Iniciar sesión");
            nuevoStage.setMaximized(true);
            nuevoStage.show();

            this.ventanaPrincipal = nuevoStage;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
