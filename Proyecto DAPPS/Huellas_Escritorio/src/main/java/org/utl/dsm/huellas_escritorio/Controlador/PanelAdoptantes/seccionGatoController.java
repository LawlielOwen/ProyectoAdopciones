package org.utl.dsm.huellas_escritorio.Controlador.PanelAdoptantes;
import java.util.*;
import java.io.ByteArrayInputStream;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import com.google.gson.Gson;
import org.utl.dsm.huellas_escritorio.Modelo.Animales;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.fxml.Initializable;
import javafx.application.Platform;

import javafx.stage.Stage;
import org.utl.dsm.huellas_escritorio.Modelo.Centros;
public class seccionGatoController implements  Initializable {
    private Stage cerrarVentanas;
    @FXML
    private Button btnAdopta;
    @FXML
    private HBox contenedorCartasBusqueda;
    @FXML
    private Button btnAfiliacion;

    @FXML
    private Button btnDonacion;

    @FXML
    private Button btnEmpleado;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnQuien;
    @FXML
    private VBox contenedorSecciones;

    @FXML
    private FlowPane contenedorTarjetas;
    @FXML
    private TextField buscador;
    @FXML
    private Rectangle rectanguloFondo;

    @FXML
    private ImageView imagenFondo;
    @FXML
    private StackPane container;
    @FXML
    private HBox contenedorBusqueda;

    @FXML
    private VBox contenedorFiltros;

    @FXML
    private VBox contenedorResultados;

    @FXML
    private HBox contenedorCartas;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cambioModulo c = new cambioModulo();
        imagenFondo.fitWidthProperty().bind(container.widthProperty());
        rectanguloFondo.widthProperty().bind(container.widthProperty());
        cargarAnimales();


        btnAfiliacion.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Clientes/Afiliacion.fxml", "Afiliación", btnAfiliacion));
        btnEmpleado.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/loginEmpleado.fxml", "Empleados", btnEmpleado));
        btnLogin.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Clientes/login.fxml", "Iniciar Sesión", btnLogin));
        btnAdopta.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Clientes/inicio.fxml", "Inicio", btnAdopta));
        buscador.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String texto = buscador.getText().trim();
                if (texto.isEmpty()) {
                    buscarAnimal(texto);
                } else {
                    buscarAnimal(texto);
                }
            }
        });
    }




    public void cargarAnimales() {
        HttpResponse<String> response = Unirest.get("http://localhost:8080/ProyectoHuellas/api/mascotas/getGatos")
                .asString();

        if (response.getStatus() == 200) {
            Gson gson = new Gson();
            Animales[] lista = gson.fromJson(response.getBody(), Animales[].class);

            Platform.runLater(() -> {


                int maxCartas = 6;
                for (int i = 0; i < lista.length && i < maxCartas; i++) {
                    VBox carta = crearCartaAnimal(lista[i]);
                    contenedorTarjetas.getChildren().add(carta);
                }
            });
        }
    }
    private void buscarAnimal(String nombre) {
        HttpResponse<String> response = Unirest.post("http://localhost:8080/ProyectoHuellas/api/inicio/buscarGatos")
                .header("Content-Type", "application/json")
                .body("{\"nombreAnimal\":\"" + nombre + "\"}")
                .asString();

        if (response.getStatus() == 200) {
            Animales[] encontrados = new Gson().fromJson(response.getBody(), Animales[].class);

            Platform.runLater(() -> {
                contenedorTarjetas.getChildren().clear();
                for (Animales animal : encontrados){
                    VBox carta = crearCartaAnimal(animal);
                    contenedorTarjetas.getChildren().add(carta);
                }
                if(encontrados.length == 0){
                    VBox vacio = new VBox();
                    vacio.setAlignment(Pos.CENTER);
                    Text mensaje = new Text("No se encontraron resultados.");
                    vacio.getChildren().add(mensaje);
                    contenedorTarjetas.getChildren().add(vacio);
                }
            });
        }
        if(nombre.isEmpty()){
            contenedorTarjetas.getChildren().clear();
            cargarAnimales();
        }
    }
    private VBox crearCartaAnimal(Animales animal) {

        String base64Image = animal.getFoto().split(",")[1];
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);


        Image img = new Image(new ByteArrayInputStream(imageBytes));

        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(158);
        imageView.setFitHeight(173);
        imageView.setPreserveRatio(false);
        imageView.setSmooth(true);

        Rectangle clip = new Rectangle(158, 173);
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        imageView.setClip(clip);

        VBox.setMargin(imageView, new Insets(20, 0, 0, 0));

        Label lblCategoria = new Label(animal.getEspecie());
        lblCategoria.getStyleClass().add("categoria");

        Text txtNombre = new Text(animal.getNombreAnimal());
        txtNombre.getStyleClass().add("nombreAnimal");


        Text txtSexo = new Text("Sexo: " + animal.getGenero());
        txtSexo.getStyleClass().add("infoAnimal");


        Text txtEdad = new Text("Edad: " + animal.getEdad());
        txtEdad.getStyleClass().add("infoAnimal");

        HBox contInfo = new HBox(10, txtSexo, txtEdad);

        Text txtCaracter = new Text("Carácter: " + animal.getCaracter());
        txtCaracter.getStyleClass().add("infoAnimal");

        Button btnAdoptame = new Button("Adóptame 🐾");
        btnAdoptame.getStyleClass().add("button-carta");
        btnAdoptame.setOnAction(e -> mostrarDetalleAnimal(animal));

        VBox contInfoBox = new VBox(4, lblCategoria, txtNombre, contInfo, txtCaracter);
        VBox.setMargin(contInfoBox, new Insets(10, 0, 20, 20));
        VBox.setMargin(btnAdoptame, new Insets(0, 0, 20, 0));

        // Contenedor final
        VBox carta = new VBox();
        carta.setAlignment(Pos.TOP_CENTER);
        carta.setPrefHeight(380);
        carta.setPrefWidth(260);
        carta.getStyleClass().add("cartaAnimal");
        carta.getChildren().addAll(imageView, contInfoBox, btnAdoptame);


        carta.setCache(true);
        carta.setCacheHint(CacheHint.SPEED);

        return carta;
    }
    private void mostrarDetalleAnimal(Animales animal) {
        try {
            if(cerrarVentanas !=null && cerrarVentanas.isShowing()){
                cerrarVentanas.close();
            }
            HttpResponse<String> response = Unirest.get("http://localhost:8080/ProyectoHuellas/api/centros/getAll").asString();
            Centros centro = new Centros();
            if (response.getStatus() == 200) {
                Gson gson = new Gson();
                Centros[] centros = gson.fromJson(response.getBody(), Centros[].class);
                for (Centros c : centros) {
                    if (c.getIdCentro() == animal.getIdCentro()) {
                        centro = c;
                        break;
                    }
                }
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/utl/dsm/huellas_escritorio/Clientes/tarjetaAnimal.fxml"));
            Parent root = loader.load();

            controllerCartaAnimal controller = loader.getController();
            controller.cargarInfo(animal,centro);
            Stage stage = new Stage();
            stage.setTitle("Información del animal");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            cerrarVentanas= stage;
            stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}

