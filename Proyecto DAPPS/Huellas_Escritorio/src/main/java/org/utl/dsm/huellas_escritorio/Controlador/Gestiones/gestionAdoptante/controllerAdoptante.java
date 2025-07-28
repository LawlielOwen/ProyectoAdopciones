package org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionAdoptante;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
import org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionMascotas.modificarAnimal;
import org.utl.dsm.huellas_escritorio.Controlador.PanelAdoptantes.cambioModulo;
import org.utl.dsm.huellas_escritorio.Modelo.Adoptante;
import org.utl.dsm.huellas_escritorio.Modelo.Animales;
import org.utl.dsm.huellas_escritorio.Modelo.Centros;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class controllerAdoptante implements Initializable {

    @FXML
    private VBox barra;

    @FXML
    private Button btnAdoptante;
    @FXML
    private Button btnAnimales;

    @FXML
    private Button btnAfiliacion;

    @FXML
    private Button btnCentro;

    @FXML
    private Button btnDonaciones;

    @FXML
    private Button btnEmpleado;

    @FXML
    private Button btnSolicitud;

    @FXML
    private TextField buscador;


    @FXML
    private Label contadorRegistrados;
    @FXML
    private AnchorPane contenedorBarra;

    @FXML
    private HBox contenedorBusqueda;

    @FXML
    private HBox header;

    @FXML
    private ImageView iconoBoton;

    @FXML
    private Label labelAdoptantes;

    @FXML
    private Label labelAfiliaciones;

    @FXML
    private Label labelAnimales;

    @FXML
    private Label labelCentros;

    @FXML
    private Label labelDonaciones;

    @FXML
    private Label labelEmpleados;

    @FXML
    private Label labelSolicitudes;

    @FXML
    private Button mini;

    @FXML
    private TableColumn<Adoptante, String> tcolCorreo;

    @FXML
    private TableColumn<Adoptante, String> tcolFecha;

    @FXML
    private TableColumn<Adoptante, String> tcolNombre;
    @FXML
    private MenuItem cerrarSesion;
    @FXML
    private TableColumn<Adoptante, Void> tcolOpciones;

    @FXML
    private TableColumn<Adoptante, String> tcolSexo;
    @FXML
    private TableView<Adoptante> tablaAdop;
    @FXML
    private TableColumn<Adoptante, String> tcolTelefono;
    public ObservableList<Adoptante> listAdoptante;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cambioModulo c = new cambioModulo();
        Adoptante a = new Adoptante();
        listAdoptante = FXCollections.observableArrayList();
        cargarAdoptantes();
        cargarContador();
        tablaAdop.setItems(listAdoptante);
        tcolNombre.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()
                .getNombre()));
        tcolFecha.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getFechaNacimiento()));
        tcolCorreo.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getCorreo()));
        tcolSexo.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getGenero()));
        tcolTelefono.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getTelefono()));
        tcolOpciones.setCellFactory(param -> new TableCell<Adoptante, Void>() {
            private final HBox container = new HBox(5);
            private final Button btnEditar = new Button();
            private final Button btnEliminar = new Button();


            {
                ImageView modifica = new ImageView(new Image(getClass().getResourceAsStream("/Iconos/Gestiones/editar.png")));
                ImageView borra = new ImageView(new Image(getClass().getResourceAsStream("/Iconos/borrar.png")));


                container.setAlignment(Pos.CENTER);


                btnEditar.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: #6c757d; -fx-border-radius: 8px; "
                        + "-fx-cursor: hand; -fx-padding: 5px;");
                btnEliminar.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: #dc3545; -fx-border-radius: 8px; "
                        + "-fx-cursor: hand; -fx-padding: 5px;");


                btnEditar.setOnMouseEntered(e -> {
                    btnEditar.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-border-color: #6c757d; -fx-border-radius: 8px; "
                            + "-fx-cursor: hand; -fx-padding: 5px;");
                    ColorAdjust whiteEffect = new ColorAdjust();
                    whiteEffect.setBrightness(1.0);
                    modifica.setEffect(whiteEffect);
                });

                btnEditar.setOnMouseExited(e -> {
                    btnEditar.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: #6c757d; -fx-border-radius: 8px; "
                            + "-fx-cursor: hand; -fx-padding: 5px;");
                    modifica.setEffect(null);
                });

                btnEliminar.setOnMouseEntered(e -> {
                    btnEliminar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-border-color: #dc3545; -fx-border-radius: 8px; "
                            + "-fx-cursor: hand; -fx-padding: 5px;");
                    ColorAdjust whiteEffect = new ColorAdjust();
                    whiteEffect.setBrightness(1.0);
                    borra.setEffect(whiteEffect);
                });

                btnEliminar.setOnMouseExited(e -> {
                    btnEliminar.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: #dc3545; -fx-border-radius: 8px; "
                            + "-fx-cursor: hand; -fx-padding: 5px;");
                    borra.setEffect(null);
                });
                modifica.setFitHeight(16);
                modifica.setFitWidth(16);
                borra.setFitHeight(16);
                borra.setFitWidth(16);


                btnEditar.setGraphic(modifica);
                btnEliminar.setGraphic(borra);

                btnEditar.setPrefWidth(70);
                btnEliminar.setPrefWidth(70);


                container.getChildren().addAll(btnEditar, btnEliminar);

                btnEditar.setOnAction(event -> {
                    Adoptante a = getTableView().getItems().get(getIndex());

                    abrirModificar(a);
                });
                btnEliminar.setOnAction(event -> {
                    Adoptante a = getTableView().getItems().get(getIndex());
                    abrirEliminar(a);

                });


            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(container);
                }
            }
        });

        btnSolicitud.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Solicitudes.fxml", "Gestion de Solicitudes", btnSolicitud));
        btnAfiliacion.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Afiliaciones.fxml", "Gestion de afiliados", btnAfiliacion));
        btnDonaciones.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Donaciones.fxml", "Gestion de donaciones", btnDonaciones));
        btnAdoptante.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Adoptantes.fxml", "Gestion de adoptantes", btnAdoptante));
        btnCentro.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Centros.fxml", "Gestion de centros", btnCentro));
        btnAnimales.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Mascotas.fxml", "Gestion de mascotas", btnAnimales));
        btnEmpleado.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Empleados.fxml", "Gestion de empleados", btnEmpleado));
        cerrarSesion.setOnAction(event -> c.cambiarPantallaMenu("/org/utl/dsm/huellas_escritorio/Empleados/loginEmpleado.fxml", "Iniciar sesion", cerrarSesion));
        buscador.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    buscarAdoptante();
                }
            }
        });
    }

    // aqui al llamar al get all l respuesta que me de la api la metere en un array
    // posteriormente con lo que tenga en mi array añadire ese array a mi lista de la tabla
    public void cargarAdoptantes() {
        HttpResponse<String> response = Unirest.get("http://localhost:8080/ProyectoHuellas/api/adoptante/getAll")
                .asString();


        if (response.getStatus() == 200) {
            Gson gson = new Gson();
            Adoptante[] lista = gson.fromJson(response.getBody(), Adoptante[].class);
            listAdoptante.addAll(Arrays.asList(lista));

        }
    }

    public void cargarContador() {
        HttpResponse<String> response = Unirest.get("http://localhost:8080/ProyectoHuellas/api/adoptante/contarDisponibles")
                .asString();

        String adoptantesD = (String) response.getBody();

        contadorRegistrados.setText(adoptantesD);
    }

    public void abrirModificar(Adoptante a) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/utl/dsm/huellas_escritorio/Empleados/Formularios/GestionAdoptantes/modificarAdoptante.fxml"));
            Parent root = loader.load();

            modificarAdoptante controller = loader.getController();
            controller.setController(this);
            controller.cargarAdoptante(a);

            Stage stage = new Stage();
            stage.setTitle("Modificar adoptante");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void abrirEliminar(Adoptante a) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/utl/dsm/huellas_escritorio/Empleados/Formularios/GestionAdoptantes/eliminarAdoptante.fxml"));
            Parent root = loader.load();

            eliminarAdoptante controller = loader.getController();
            controller.setController(this);
            controller.cargarNombre(a);

            Stage stage = new Stage();
            stage.setTitle("Eliminar adoptante");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buscarAdoptante() {
        String nombre = buscador.getText().trim();
        String json = "{ \"nombre\": \"" + nombre + "\" }";
        HttpResponse<JsonNode> response = Unirest.post("http://localhost:8080/ProyectoHuellas/api/adoptante/buscarA")
                .header("Content-Type", "application/json")
                .body(json)
                .asJson();
        listAdoptante.clear();
        Gson gson = new Gson();
        Adoptante[] lista = gson.fromJson(String.valueOf(response.getBody()), Adoptante[].class);
        listAdoptante.addAll(Arrays.asList(lista));
        tablaAdop.setItems(listAdoptante);
        if (nombre.isEmpty()) {
            listAdoptante.clear();
            cargarAdoptantes();
        }
    }
}



