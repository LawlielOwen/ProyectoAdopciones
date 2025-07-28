package org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionAdoptante;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Pos;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import com.google.gson.Gson;
import org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionAdoptante.controllerAdoptante;
import org.utl.dsm.huellas_escritorio.Controlador.PanelAdoptantes.cambioModulo;
import org.utl.dsm.huellas_escritorio.Modelo.Adoptante;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
public class modificarAdoptante implements  Initializable{
    private controllerAdoptante controller;
    @FXML
    private TextField apm;

    @FXML
    private TextField app;

    @FXML
    private Button cancelar;


    @FXML
    private PasswordField contra;

    @FXML
    private TextField correo;

    @FXML
    private DatePicker fecha;


    @FXML
    private ComboBox<String> genero;

    @FXML
    private Button guardar;

    @FXML
    private TextField nombre;

    @FXML
    private TextField telefono;
    private int idAd;
    public controllerAdoptante getController() {
        return controller;
    }

    public void setController(controllerAdoptante controller) {
        this.controller = controller;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cargarDatos();
        cancelar.setOnAction(event -> cerrarVentana());
        guardar.setOnAction(event -> {
            try {
              modificar();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void cargarDatos(){
        ObservableList<String> generos = FXCollections.observableArrayList(
                "Masculino", "Femenino", "Otro"
        );
        genero.setItems(generos);
    }
    public void cargarAdoptante(Adoptante a){
    this.idAd = a.getIdAdoptante();
    nombre.setText(a.getNombre());
    app.setText(a.getApp());
    apm.setText(a.getApm());
        if (a.getFechaNacimiento() != null && !a.getFechaNacimiento().isEmpty()) {
            LocalDate localDate = LocalDate.parse(a.getFechaNacimiento());
            fecha.setValue(localDate);
        } else {
            fecha.setValue(null);
        }
    correo.setText(a.getCorreo());
    telefono.setText(a.getTelefono());
    genero.setValue(a.getGenero());
    contra.setText(a.getContraseña());
    }
    private void cerrarVentana() {
        Stage stage = (Stage) cancelar.getScene().getWindow();
        stage.close();
    }
    private void modificar(){
        int id = idAd;
        String nombreA = nombre.getText().trim();
        String APP = app.getText().trim();
        String APM = apm.getText().trim();
        String email = correo.getText().trim();
        String sexo = genero.getValue();
        String contrasena = contra.getText().trim();
        String fechaStr = fecha.getValue().toString();
        String tel = telefono.getText().trim();

        String json = "{"
                + "\"idAdoptante\":" + id + ","
                + "\"nombre\":\"" + nombreA + "\","
                + "\"app\":\"" + APP + "\","
                + "\"apm\":\"" + APM + "\","
                + "\"fechaNacimiento\":\"" + fechaStr + "\","
                + "\"genero\":\"" + sexo + "\","
                + "\"correo\":\"" + email + "\","
                + "\"contraseña\":\"" + contrasena + "\","
                + "\"telefono\":\"" + tel + "\""
                + "}";
        HttpResponse<JsonNode> response = Unirest.put("http://localhost:8080/ProyectoHuellas/api/adoptante/modAd")
                .header("Content-Type", "application/json")
                .body(json)
                .asJson();
        controller.listAdoptante.clear();
        controller.cargarAdoptantes();
        clear();
        Stage stage = (Stage) guardar.getScene().getWindow();
        stage.close();
    }
    private void clear(){
        nombre.clear();
        app.clear();
        apm.clear();
        fecha.setValue(null);
        correo.clear();
        contra.clear();
        telefono.clear();
        genero.setValue(null);
        nombre.requestFocus();
    }
}
