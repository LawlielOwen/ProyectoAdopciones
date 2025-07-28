package org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionSolicitud;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import com.google.gson.Gson;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionSolicitud.gestionSolicitud;
import org.utl.dsm.huellas_escritorio.Modelo.Solicitudes;
import java.net.URL;
import java.util.ResourceBundle;
public class borrarSolicitud implements Initializable {
    private gestionSolicitud controller;
    @FXML
    private Button borrar;

    @FXML
    private Button cancelar;

    @FXML
    private Label nombreAd;

    private int id;

    public gestionSolicitud getController() {
        return controller;
    }

    public void setController(gestionSolicitud controller) {
        this.controller = controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        borrar.setOnAction(event -> {
            try {
                eliminarSoli();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        cancelar.setOnAction(event -> {
            try {
                cerrarVentana();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void cerrarVentana() {
        Stage stage = (Stage) cancelar.getScene().getWindow();
        stage.close();
    }

    public void cargarNombre(Solicitudes a) {
        this.id = a.getIdSolicitud();

    }

    private void eliminarSoli() {
        int idA = id;

        String json = "{\"idSolicitud\":" + idA + "}";
        HttpResponse<String> response = Unirest
                .delete("http://localhost:8080/ProyectoHuellas/api/solicitudes/eliminarSoli")
                .header("Content-Type", "application/json")
                .body(json)
                .asString();

        controller.listSolicitudes.clear();
        controller.cargarDatos();
        controller.cargarContador();
        cerrarVentana();
    }
}