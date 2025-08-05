package org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionMascotas;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.utl.dsm.huellas_escritorio.Modelo.Centros;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class formulariosAnimal implements Initializable  {
    private gestionMascotas controller;
    private File archivoSeleccionado;

    @FXML
    private TextArea descripcion;

    @FXML
    private TextField edad;

    @FXML
    private ComboBox<String> especie;

    @FXML
    private ComboBox<String> genero;

    @FXML
    private Button guardar;

    @FXML
    private TextField nombre;

    @FXML
    private ComboBox<String> nombreCentro;
    @FXML
    private TextField nombreFoto;

    @FXML
    private TextField peso;

    @FXML
    private TextField raza;

    @FXML
    private Button subirFoto;

    @FXML
    private ComboBox<String> caracter;
    @FXML
    private ComboBox<String> tamano;


    public gestionMascotas getController() {
        return controller;
    }

    public void setController(gestionMascotas controller) {
        this.controller = controller;
    }


    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

        cargarDatos();
        subirFoto.setOnAction(event -> seleccionarArchivo());
        guardar.setOnAction(event -> {
            try {
                agregarAnimal();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }
    private void cargarDatos() {

        ObservableList<String> tamanios = FXCollections.observableArrayList(
                "Pequeño", "Mediano", "Grande"
        );
        tamano.setItems(tamanios);


        ObservableList<String> generos = FXCollections.observableArrayList(
                "Macho", "Hembra"
        );
        genero.setItems(generos);


        ObservableList<String> especies = FXCollections.observableArrayList(
                "Perros","Gatos"
        );
        especie.setItems(especies);
        ObservableList<String> caracte = FXCollections.observableArrayList(
                "Juguetón", "Tranquilo","Guardián","Tímido","Activo"
        );
        caracter.setItems(caracte);
        HttpResponse<String> response = Unirest.get("http://localhost:8080/ProyectoHuellas/api/centros/getAll").asString();

        if (response.getStatus() == 200) {
            Gson gson = new Gson();
            Centros[] centros = gson.fromJson(response.getBody(), Centros[].class);


            ObservableList<String> nombresCentros = FXCollections.observableArrayList();
            for (Centros centro : centros) {
                nombresCentros.add(centro.getNombreCentro());
            }
            nombreCentro.setItems(nombresCentros);
        }
    }
    @FXML
    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    private void seleccionarArchivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        FileChooser.ExtensionFilter filtro = new FileChooser.ExtensionFilter(
                "Imágenes (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg"
        );
        fileChooser.getExtensionFilters().add(filtro);

        Stage stage = (Stage) subirFoto.getScene().getWindow();
        archivoSeleccionado = fileChooser.showOpenDialog(stage);

        if (archivoSeleccionado != null) {
            nombreFoto.setText(archivoSeleccionado.getName());
        }
    }


    public void agregarAnimal() throws Exception {
        String nombreAnimal = nombre.getText();
        String Genero = genero.getValue();
        nombreFoto.clear();
        String edadA = edad.getText();
        Double pesoA = Double.parseDouble(peso.getText());
        String razaA = raza.getText();
       String Descrip =  descripcion.getText();
        String Especie = especie.getValue();
        String nombreC = nombreCentro.getValue();
        String cara = caracter.getValue();
        String foto = "";
        if (archivoSeleccionado != null) {
            try {
                foto = convertirImagenABase64(archivoSeleccionado);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int idCentro = -1;
        idCentro = obtenerCentros(nombreC);
         String Tamano =  tamano.getValue();

        String json = "{"
                + "\"nombreAnimal\":\"" + nombreAnimal + "\","
                + "\"genero\":\"" + Genero + "\","
                + "\"edad\":\"" + edadA + "\","
                + "\"peso\":" + pesoA + ","
                + "\"especie\":\"" + Especie + "\","
                + "\"descripcion\":\"" + Descrip + "\","
                + "\"raza\":\"" + razaA + "\","
                + "\"tamano\":\"" + Tamano + "\","
                + "\"caracter\":\"" + cara + "\","
                + "\"foto\":\"" + foto + "\","
                + "\"idCentro\":" + idCentro
                + "}";

        HttpResponse<JsonNode> response = Unirest.post("http://localhost:8080/ProyectoHuellas/api/mascotas/saveAnimal")
                .header("Content-Type", "application/json")
                .body(json)
                .asJson();
        controller.listAnimales.clear();
        controller.cargarAnimales();
        controller.cargarContador();
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
        nombre.clear();
        genero.setValue(null);
        nombreFoto.clear();
        edad.clear();
        peso.clear();
        raza.clear();
        descripcion.clear();
        especie.setValue(null);
        nombreCentro.setValue(null);
        tamano.setValue(null);
        caracter.setValue(null);
    }
    private String convertirImagenABase64(File file) throws IOException {
        byte[] fileContent = Files.readAllBytes(file.toPath());
        return Base64.getEncoder().encodeToString(fileContent);
    }
}

