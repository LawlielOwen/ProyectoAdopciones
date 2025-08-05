package org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionAfiliado;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.utl.dsm.huellas_escritorio.Modelo.Afiliados;
import java.net.URL;
import java.util.ResourceBundle;

public class eliminarAfiliados implements Initializable {
    private gestionAfiliados controller;

    @FXML private Button borrar;
    @FXML private Button cancelar;
    @FXML private Label nombreAfiliado;

    private int idAfiliado;

    public gestionAfiliados getController() {
        return controller;
    }

    public void setController(gestionAfiliados controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        borrar.setOnAction(event -> {
            try {
                eliminarAfiliado();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        cancelar.setOnAction(event -> cerrarVentana());
    }

    private void cerrarVentana() {
        Stage stage = (Stage) cancelar.getScene().getWindow();
        stage.close();
    }

    public void cargarNombre(Afiliados afiliado) {
        this.idAfiliado = afiliado.getIdAfiliado();
        nombreAfiliado.setText(afiliado.getNombre() + "?");
    }

    private void eliminarAfiliado() {
        HttpResponse<String> response = Unirest.post("http://localhost:8080/ProyectoHuellas/api/Afiliacion/eliminar")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body("idAfiliado=" + idAfiliado)
                .asString();

        controller.listAfiliados.clear();
        controller.cargarAfiliados();
        cerrarVentana();
    }
}
