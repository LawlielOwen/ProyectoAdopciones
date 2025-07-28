package org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionMascotas;

import com.google.gson.Gson;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.utl.dsm.huellas_escritorio.Modelo.Centros;
import org.utl.dsm.huellas_escritorio.Modelo.Animales;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;

public class modificarAnimal implements  Initializable{

    private gestionMascotas controller;
    private Animales animalMod;
    private Centros centroMod;
    @FXML
    private Button cancelarMod;


    @FXML
    private TextArea descripcionMod;

    @FXML
    private TextField edadMod;

    @FXML
    private ComboBox<String> especieMod;

    @FXML
    private ImageView fotoMuestra;

    @FXML
    private ComboBox<String> generoMod;

    @FXML
    private Button guardar;

    @FXML
    private ComboBox<String> nombreCentroMod;

    @FXML
    private TextField nombreMod;

    @FXML
    private TextField pesoMod;
    @FXML
    private ComboBox<String> caracterMod;
    @FXML
    private TextField razaMod;

    @FXML
    private ComboBox<String> tamanoMod;
    private int idAnimalMod;

    public gestionMascotas getController() {
        return controller;
    }

    public void setController(gestionMascotas controller) {
        this.controller = controller;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cargarDatos();
        cancelarMod.setOnAction(event -> cerrarVentana());
        guardar.setOnAction(event -> {
            try {
                modificarAnimal();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void cargarDatos() {

        ObservableList<String> tamanios = FXCollections.observableArrayList(
                "Pequeño", "Mediano", "Grande"
        );
        tamanoMod.setItems(tamanios);


        ObservableList<String> generos = FXCollections.observableArrayList(
                "Macho", "Hembra"
        );
        generoMod.setItems(generos);


        ObservableList<String> especies = FXCollections.observableArrayList(
                "Perros", "Gatos"
        );
        especieMod.setItems(especies);
        ObservableList<String> caracte = FXCollections.observableArrayList(
                "Juguetón", "Tranquilo","Guardián","Tímido","Activo"
        );
        caracterMod.setItems(caracte);
        HttpResponse<String> response = Unirest.get("http://localhost:8080/ProyectoHuellas/api/centros/getAll").asString();

        if (response.getStatus() == 200) {
            Gson gson = new Gson();
            Centros[] centros = gson.fromJson(response.getBody(), Centros[].class);


            ObservableList<String> nombresCentros = FXCollections.observableArrayList();
            for (Centros centro : centros) {
                nombresCentros.add(centro.getNombreCentro());
            }
            nombreCentroMod.setItems(nombresCentros);
        }
    }
    private void cerrarVentana() {
        Stage stage = (Stage) cancelarMod.getScene().getWindow();
        stage.close();
    }

    public void cargarDatosAnimal(Animales a, Centros c){
        this.idAnimalMod = a.getIdAnimal();
        nombreMod.setText(a.getNombreAnimal());
        generoMod.setValue(a.getGenero());
        edadMod.setText(a.getEdad());
        pesoMod.setText(String.valueOf(a.getPeso()));
        especieMod.setValue(a.getEspecie());
        razaMod.setText(a.getRaza());
        tamanoMod.setValue(a.getTamano());
        nombreCentroMod.setValue(c.getNombreCentro());
        descripcionMod.setText(a.getDescripcion());
        caracterMod.setValue(a.getCaracter());
        String base64 = a.getFoto().split(",")[1];
        byte[] imgBytes = Base64.getDecoder().decode(base64);
        Image img = new Image(new ByteArrayInputStream(imgBytes));

        fotoMuestra.setImage(img);
        fotoMuestra.setPreserveRatio(false);
        fotoMuestra.setFitWidth(126);
        fotoMuestra.setFitHeight(112);
        Rectangle clip = new Rectangle(126, 112);
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        fotoMuestra.setClip(clip);
    }
    public void modificarAnimal() throws Exception {
        int idAnimal = idAnimalMod;
        String nombreAnimal = nombreMod.getText();
        String Genero = generoMod.getValue();
        String edadA = edadMod.getText();
        Double pesoA = Double.parseDouble(pesoMod.getText());
        String razaA = razaMod.getText();
        String Descrip =  descripcionMod.getText();
        String Especie = especieMod.getValue();
        String nombreC = nombreCentroMod.getValue();
        String caracter = caracterMod.getValue();
        int idCentro = -1;
        idCentro = obtenerCentros(nombreC);
        String Tamano =  tamanoMod.getValue();

        String json = "{"
                + "\"idAnimal\":" + idAnimal + ","
                + "\"nombreAnimal\":\"" + nombreAnimal + "\","
                + "\"genero\":\"" + Genero + "\","
                + "\"edad\":\"" + edadA + "\","
                + "\"peso\":" + pesoA + ","
                + "\"especie\":\"" + Especie + "\","
                + "\"descripcion\":\"" + Descrip + "\","
                + "\"raza\":\"" + razaA + "\","
                + "\"tamano\":\"" + Tamano + "\","
                + "\"caracter\":\"" + caracter + "\","
                + "\"idCentro\":" + idCentro
                + "}";

        HttpResponse<JsonNode> response = Unirest.put("http://localhost:8080/ProyectoHuellas/api/mascotas/updateAnimal")
                .header("Content-Type", "application/json")
                .body(json)
                .asJson();
        controller.listAnimales.clear();
        controller.cargarAnimales();
        clear();
        Stage stage = (Stage) guardar.getScene().getWindow();
        stage.close();
    }
    private int obtenerCentros(String nombreCentro) throws Exception {
        HttpResponse<String> response = Unirest.get("http://localhost:8080/ProyectoHuellas/api/centros/getAll").asString();

        if (response.getStatus() == 200) {
            Gson gson = new Gson();
            Centros[] centros = gson.fromJson(response.getBody(), Centros[].class);

            for (Centros centro : centros) {
                if (centro.getNombreCentro().equals(nombreCentro)) {
                    return centro.getIdCentro();
                }
            }
            throw new Exception("Centro no encontrado");
        }
        throw new Exception("Error al obtener centros");
    }
    public void clear() {
        nombreMod.clear();
        generoMod.setValue(null);
        edadMod.clear();
        pesoMod.clear();
        razaMod.clear();
        descripcionMod.clear();
        especieMod.setValue(null);
        nombreCentroMod.setValue(null);
        tamanoMod.setValue(null);
        fotoMuestra.setImage(null);
        caracterMod.setValue(null);
    }
}
