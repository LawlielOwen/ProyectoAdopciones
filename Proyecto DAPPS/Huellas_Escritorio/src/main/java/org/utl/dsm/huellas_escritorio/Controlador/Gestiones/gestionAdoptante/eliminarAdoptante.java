package org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionAdoptante;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import com.google.gson.Gson;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionAdoptante.controllerAdoptante;
import org.utl.dsm.huellas_escritorio.Modelo.Adoptante;
import java.net.URL;
import java.util.ResourceBundle;
public class eliminarAdoptante implements Initializable{
    private controllerAdoptante controller;
    @FXML
    private Button borrar;

    @FXML
    private Button cancelar;

    @FXML
    private Label nombreAd;

    private int id;
    public controllerAdoptante getController() {
        return controller;
    }

    public void setController(controllerAdoptante controller) {
        this.controller = controller;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        borrar.setOnAction(event -> {
            try {
                eliminarAdoptante();
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
    public void cargarNombre(Adoptante a){
        this.id = a.getIdAdoptante();
        nombreAd.setText(a.getNombre() + " " + a.getApp() + " " + a.getApm() + "?");
    }
    private void eliminarAdoptante(){
        int idA = id;

        String json = "{\"idAdoptante\":" + idA + "}";
        HttpResponse<String> response = Unirest
                .delete("http://localhost:8080/ProyectoHuellas/api/adoptante/deleteAdop")
                .header("Content-Type", "application/json")
                .body(json)
                .asString();

        controller.listAdoptante.clear();
        controller.cargarAdoptantes();
        controller.cargarContador();
        cerrarVentana();
    }
}
