package org.utl.dsm.huellas_escritorio.Controlador.PanelAdoptantes;
import java.util.*;
import java.io.ByteArrayInputStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import com.google.gson.Gson;
import org.utl.dsm.huellas_escritorio.Modelo.Animales;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.net.URL;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.fxml.Initializable;
import javafx.application.Platform;
import org.utl.dsm.huellas_escritorio.Modelo.Sesion;
import javafx.stage.Stage;
import org.utl.dsm.huellas_escritorio.Modelo.Centros;

public class inicioAdoptante implements  Initializable {
    private Stage cerrarVentanas;
    @FXML
    private Button btnAdopta;
    @FXML
    private HBox contenedorCartasBusqueda;
    @FXML
    private Button btnAfiliacion;

    @FXML
    private Text correoUsuario;
    @FXML
    private Text NombreUsuario;

    @FXML
    private Button btnLogout;

    @FXML
    private MenuButton menuSesion;

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
    private VBox contenedorTarjetas;
    @FXML
    private TextField buscador;
    @FXML
    private Rectangle rectanguloFondo;
    @FXML
    private HBox contenedorCartitas;
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

    @FXML
    private StackPane seccionGatos;

    @FXML
    private StackPane seccionPerros;
 // filtros
 @FXML
    private Button btnFiltro;

    @FXML
    private ComboBox<String> caracter;
    @FXML
    private ComboBox<String> edad;

    @FXML
    private ComboBox<String> genero;
    @FXML
    private CheckBox seleccionGatos;

    @FXML
    private CheckBox seleccionPerros;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cambioModulo c = new cambioModulo();
        imagenFondo.fitWidthProperty().bind(container.widthProperty());
        rectanguloFondo.widthProperty().bind(container.widthProperty());
        cargarAnimales();
        buscador.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String texto = buscador.getText().trim();
                if (texto.isEmpty()) {
                    mostrarVistaPrincipal();
                } else {
                    buscarAnimal(texto);
                }
            }
        });

        btnAfiliacion.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Clientes/Afiliacion.fxml", "Afiliación", btnAfiliacion));
        btnEmpleado.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/loginEmpleado.fxml", "Empleados", btnEmpleado));
        btnLogin.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Clientes/Login/login.fxml", "Iniciar Sesión", btnLogin));
        btnAdopta.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Clientes/inicio.fxml", "Inicio", btnAdopta));
        seccionPerros.setOnMouseClicked(event -> {
          c.cambiarPantallaStackPane("/org/utl/dsm/huellas_escritorio/Clientes/seccionPerros.fxml","Seccion de perros", seccionPerros);
        });
        seccionGatos.setOnMouseClicked(event -> {
            c.cambiarPantallaStackPane("/org/utl/dsm/huellas_escritorio/Clientes/seccionGatos.fxml","Seccion de gatos", seccionGatos);
        });

        if(Sesion.getAdoptanteActual() != null){
            btnEmpleado.setVisible(false);
            btnEmpleado.setManaged(false);
            btnLogin.setManaged(false);
            btnLogin.setVisible(false);
            menuSesion.setVisible(true);
            menuSesion.setManaged(true);
            menuSesion.setText(Sesion.getAdoptanteActual().getNombre());
            NombreUsuario.setText(Sesion.getAdoptanteActual().getNombre() +" " + Sesion.getAdoptanteActual().getApp() + " "+ Sesion.getAdoptanteActual().getApm());
            correoUsuario.setText(Sesion.getAdoptanteActual().getCorreo());
            ImageView img = (ImageView) menuSesion.getGraphic();

            ColorAdjust blanco = new ColorAdjust();
            blanco.setBrightness(0);

            ColorAdjust negro = new ColorAdjust();
            negro.setBrightness(-1);

            img.setEffect(blanco);


            menuSesion.setOnMouseEntered(e -> {
                menuSesion.setStyle("-fx-background-color: white; -fx-text-fill: black;");
                img.setEffect(negro);
            });
            menuSesion.setOnMouseExited(e -> {
                menuSesion.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
                img.setEffect(blanco);
            });
        }
        btnLogout.setOnAction(
                event -> {
                    Sesion.cerrarSesion();
                    btnEmpleado.setManaged(true);
                    btnEmpleado.setVisible(true);
                    btnLogin.setManaged(true);
                    btnLogin.setVisible(true);
                    menuSesion.setVisible(false);
                    menuSesion.setManaged(false);
                    NombreUsuario.setText("");
                    correoUsuario.setText("");
                    menuSesion.setText("");
                });
        ObservableList<String> Edad = FXCollections.observableArrayList(
                "Todos", "1 - 4", "5 - 8", "9 - en adelante"
        );
        edad.setItems(Edad);
        edad.getSelectionModel().selectFirst();

        ObservableList<String> caracterA = FXCollections.observableArrayList(
                "Todos", "Juguetón", "Tranquilo", "Guardián", "Tímido", "Activo"
        );
        caracter.setItems(caracterA);
        caracter.getSelectionModel().selectFirst();

        ObservableList<String> GeneroA = FXCollections.observableArrayList(
                "Todos", "Macho", "Hembra"
        );
        genero.setItems(GeneroA);
        genero.getSelectionModel().selectFirst();
        btnFiltro.setOnAction(event -> filtros());
    }
    private void buscarAnimal(String nombre) {
        HttpResponse<String> response = Unirest.post("http://localhost:8080/ProyectoHuellas/api/inicio/buscarAnimal")
                .header("Content-Type", "application/json")
                .body("{\"nombreAnimal\":\"" + nombre + "\"}")
                .asString();

        if (response.getStatus() == 200) {
            Animales[] encontrados = new Gson().fromJson(response.getBody(), Animales[].class);

            Platform.runLater(() -> {
                contenedorBusqueda.setVisible(true);
                contenedorBusqueda.setManaged(true);

                contenedorSecciones.setVisible(false);
                contenedorSecciones.setManaged(false);
                contenedorTarjetas.setVisible(false);
                contenedorTarjetas.setManaged(false);

                contenedorResultados.getChildren().clear();


                HBox filaActual = null;
                for (int i = 0; i < encontrados.length; i++) {
                    if (i % 3 == 0) {
                        filaActual = new HBox(20);
                        filaActual.setAlignment(Pos.CENTER);
                        contenedorResultados.getChildren().add(filaActual);
                    }

                    VBox carta = crearCartaAnimal(encontrados[i]);
                    filaActual.getChildren().add(carta);
                }

                if (filaActual != null && filaActual.getChildren().size() < 3) {
                    while (filaActual.getChildren().size() < 3) {
                        filaActual.getChildren().add(new Pane());
                    }
                }
            });
        } else {
            System.out.println("Error al buscar animales: " + response.getStatus());
        }
    }

    private void mostrarVistaPrincipal() {
        contenedorBusqueda.setVisible(false);
        contenedorBusqueda.setManaged(false);

        contenedorSecciones.setVisible(true);
        contenedorSecciones.setManaged(true);

        contenedorTarjetas.setVisible(true);
        contenedorTarjetas.setManaged(true);

        contenedorCartasBusqueda.setVisible(false);
        contenedorCartasBusqueda.setManaged(false);

        contenedorCartasBusqueda.getChildren().clear();

        contenedorResultados.getChildren().clear();
    }

    public void cargarAnimales() {
        HttpResponse<String> response = Unirest.get("http://localhost:8080/ProyectoHuellas/api/mascotas/getAll")
                .asString();

        if (response.getStatus() == 200) {
            Gson gson = new Gson();
            Animales[] lista = gson.fromJson(response.getBody(), Animales[].class);

            Platform.runLater(() -> {
                contenedorCartas.getChildren().clear();

                int maxCartas = 4;
                for (int i = 0; i < lista.length && i < maxCartas; i++) {
                    VBox carta = crearCartaAnimal(lista[i]);
                    contenedorCartas.getChildren().add(carta);
                }
            });
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/utl/dsm/huellas_escritorio/Clientes/Tarjeta de informacion/tarjetaAnimal.fxml"));
            Parent root = loader.load();



            Stage stage = new Stage();

            controllerCartaAnimal controller = loader.getController();
            controller.setVentanaPrincipal((Stage) btnAdopta.getScene().getWindow()); // ventana principal real
            controller.setVentanaInformacion(stage);

            controller.cargarInfo(animal, centro);


            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Información del animal");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            cerrarVentanas= stage;
            stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void filtros() {
        String edadA = "";
        String gen = genero.getValue() != null && !"Todos".equals(genero.getValue()) ? genero.getValue() : "";
        String caracterA = caracter.getValue() != null && !"Todos".equals(caracter.getValue()) ? caracter.getValue() : "";
        edadA = edad.getValue() != null && !"Todos".equals(edad.getValue()) ? edad.getValue() : "";
        String animalSeleccion = "";

        if (seleccionPerros.isSelected() && seleccionGatos.isSelected()) {
            animalSeleccion = "";
        } else if (seleccionPerros.isSelected()) {
            animalSeleccion = "Perros";
        } else if (seleccionGatos.isSelected()) {
            animalSeleccion = "Gatos";
        }
        switch(edad.getValue()){
            case "1 - 4":
                edadA = "joven";
                break;
            case "5 - 8":
                edadA = "adulto";
                break;
            case "9 - en adelante":
                edadA = "mayor";
                break;
        }
        if (gen.isEmpty() && caracterA.isEmpty() && edadA.isEmpty() && animalSeleccion.isEmpty()) {
            contenedorTarjetas.getChildren().clear();
            cargarAnimales();
            return;
        }

        try {
            String json = String.format("{\"especie\":\"%s\",\"genero\":\"%s\",\"edad\":\"%s\",\"caracter\":\"%s\"}",
                    animalSeleccion, gen, edadA, caracterA);

            HttpResponse<JsonNode> response = Unirest.post("http://localhost:8080/ProyectoHuellas/api/inicio/filtroTodos")
                    .header("Content-Type", "application/json")
                    .body(json)
                    .asJson();


            if (response.getStatus() == 200) {
                Gson gson = new Gson();
                Animales[] lista = gson.fromJson(response.getBody().toString(), Animales[].class);

                Platform.runLater(() -> {
                    contenedorBusqueda.setVisible(true);
                    contenedorBusqueda.setManaged(true);
                    contenedorSecciones.setVisible(false);
                    contenedorSecciones.setManaged(false);
                    contenedorTarjetas.setVisible(false);
                    contenedorTarjetas.setManaged(false);
                    contenedorResultados.getChildren().clear();
                    HBox filaActual = null;
                    for (int i = 0; i < lista.length; i++) {
                        if (i % 3 == 0) {
                            filaActual = new HBox(20);
                            filaActual.setAlignment(Pos.CENTER);
                            contenedorResultados.getChildren().add(filaActual);
                        }

                        VBox carta = crearCartaAnimal(lista[i]);
                        filaActual.getChildren().add(carta);
                    }


                    if (filaActual != null && filaActual.getChildren().size() < 3) {
                        while (filaActual.getChildren().size() < 3) {
                            filaActual.getChildren().add(new Pane());
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
