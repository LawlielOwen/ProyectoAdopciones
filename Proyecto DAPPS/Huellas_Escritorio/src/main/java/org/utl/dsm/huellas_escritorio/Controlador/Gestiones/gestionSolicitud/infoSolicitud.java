package org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionSolicitud;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionSolicitud.gestionSolicitud;
import org.utl.dsm.huellas_escritorio.Modelo.Animales;
import org.utl.dsm.huellas_escritorio.Modelo.Adoptante;
import org.utl.dsm.huellas_escritorio.Modelo.Solicitudes;
import javafx.event.ActionEvent;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;
public class infoSolicitud implements  Initializable{
    private int id;
    private gestionSolicitud controller;

    public gestionSolicitud getController() {
        return controller;
    }

    public void setController(gestionSolicitud controller) {
        this.controller = controller;
    }

    @FXML
    private Button BtnCerrar;
    @FXML
    private Button btnAprobar;

    @FXML
    private Button btnRechazar;

    @FXML
    private Text adoptante;

    @FXML
    private Text correo;

    @FXML
    private Text direccion;

    @FXML
    private Text estatus;

    @FXML
    private Pane estatusColor;

    @FXML
    private Text fecha;

    @FXML
    private Text mascota;

    @FXML
    private Text motivo;
    private int idA;
    @FXML
    private Text telefono;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BtnCerrar.setOnAction(event -> cerrarVentana());
        btnRechazar.setOnAction(event -> rechazarSolicitud());
        btnAprobar.setOnAction(event -> aceptarSolicitud());
    }


    public void cargarInfo(Solicitudes s){
        this.id = s.getIdSolicitud();
        this.idA = s.getIdAnimal();
        String estatusTexto = "";
        mascota.setText(s.getNombreAnimal());
        adoptante.setText(s.getNombreAdoptante());
        fecha.setText(s.getFecha());
        telefono.setText(s.getTelefono());
        correo.setText(s.getCorreo());
        direccion.setText(s.getDireccion());
        motivo.setText(s.getMotivo());
        switch (s.getEstatus()){
            case 1:
              estatusTexto = "Aceptada";
                estatusColor.setStyle("-fx-background-color: #198754;  -fx-border-radius: 8; -fx-background-radius: 8;");
                btnAprobar.setVisible(false);
                btnAprobar.setManaged(false);
                btnRechazar.setVisible(false);
                btnRechazar.setManaged(false);
                break;
            case 2:
                estatusTexto = "Pendiente";
                estatusColor.setStyle("-fx-background-color: #ffc107;  -fx-border-radius: 8; -fx-background-radius: 8;");
                break;
            case 3:
                estatusTexto = "Rechazada";
                estatusColor.setStyle("-fx-background-color: #dc3545;  -fx-border-radius: 8; -fx-background-radius: 8;");
                btnAprobar.setVisible(false);
                btnAprobar.setManaged(false);
                btnRechazar.setVisible(false);
                btnRechazar.setManaged(false);
                break;
        }

        estatus.setText(estatusTexto);
    }
    private void aceptarSolicitud(){
        Solicitudes s = new Solicitudes();
        int idSoli = id;
        int idAnimal = idA;

        String json = "{ \"idSolicitud\": \"" + idSoli + "\", \"idAnimal\": \"" + idAnimal + "\" }";
        HttpResponse<JsonNode> response = Unirest.post("http://localhost:8080/ProyectoHuellas/api/solicitudes/aceptarSoli")
                .header("Content-Type", "application/json")
                .body(json)
                .asJson();
        controller.listSolicitudes.clear();
        controller.cargarDatos();
        controller.cargarContador();
        cerrarVentana();
    }
    private void rechazarSolicitud(){
        int idSoli = id;
        String json = "{ \"idSolicitud\": \"" + idSoli + "\" }";
        HttpResponse<JsonNode> response = Unirest.post("http://localhost:8080/ProyectoHuellas/api/solicitudes/rechazarSoli")
                .header("Content-Type", "application/json")
                .body(json)
                .asJson();
        controller.listSolicitudes.clear();
        controller.cargarDatos();
        controller.cargarContador();
        cerrarVentana();
    }
    private void cerrarVentana() {
        Stage stage = (Stage) BtnCerrar.getScene().getWindow();
        stage.close();
    }
    }
