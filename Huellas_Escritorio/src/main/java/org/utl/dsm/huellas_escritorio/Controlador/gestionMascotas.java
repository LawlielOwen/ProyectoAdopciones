package org.utl.dsm.huellas_escritorio.Controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.utl.dsm.huellas_escritorio.Modelo.Animales;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
public class gestionMascotas implements  Initializable{
    @FXML
    private VBox barra;

    @FXML
    private Button btnAdoptante;

    @FXML
    private Button btnAfiliacion;

    @FXML
    private Button btnAgregar;

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
    private Label contadorAdoptados;

    @FXML
    private Label contadorDisponibles;

    @FXML
    private AnchorPane contenedorBarra;

    @FXML
    private HBox contenedorBusqueda;

    @FXML
    private ComboBox<?> filtroEspecie;

    @FXML
    private ComboBox<?> filtroEstatus;

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
    private MenuItem cerrarSesion;
    @FXML
    private Button mini;

    @FXML
    private TableColumn<Animales, String> tcolClave;

    @FXML
    private TableColumn<Animales, String> tcolEdad;

    @FXML
    private TableColumn<Animales, String> tcolEspecie;

    @FXML
    private TableColumn<Animales, String> tcolEstatus;

    @FXML
    private TableColumn<Animales, String> tcolNombre;

    @FXML
    private TableColumn<Animales, Void> tcolOpciones;
    @FXML
    private TableView<Animales> tablaAnimales;
    @FXML
    private TableColumn<Animales, Double> tcolPeso;
    @FXML
    private Button btnAnimales;
    @FXML
    private TableColumn<Animales, String> tcolRaza;

    @FXML
    private TableColumn<Animales, String> tcolSexo;

    ObservableList <Animales> listAnimales;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cambioModulo c = new cambioModulo();
        Animales a = new Animales();
        listAnimales = FXCollections.observableArrayList();

        tablaAnimales.setItems(listAnimales);
        tcolNombre.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()
                .getNombreAnimal()));
        tcolClave.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()
                .getCodigoAnimal()));
        tcolEdad.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()
                .getEdad()));
        tcolEstatus.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()
                .getEstatusTexto()));
        tcolEspecie.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()
                .getEspecie()));
        tcolPeso.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()
                .getPeso()));
        tcolRaza.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()
                .getRaza()));
        tcolSexo.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()
                .getGenero()));
        tcolOpciones.setCellFactory(param -> new TableCell<Animales, Void>() {
                    private final Button btnModificar = new Button();
                    private final Button btnEliminar = new Button();
                    private final Button btnInfo = new Button();
                    private final HBox contenedorBotones = new HBox(10, btnModificar, btnEliminar, btnInfo);

                    {

                        btnModificar.setText("✏️");
                        btnEliminar.setText("🗑️");
                        btnInfo.setText("ℹ️");

                        btnModificar.setStyle("-fx-background-color: transparent;");
                        btnEliminar.setStyle("-fx-background-color: transparent;");
                        btnInfo.setStyle("-fx-background-color: transparent;");


                        btnModificar.setOnAction(e -> {
                            Animales animal = getTableView().getItems().get(getIndex());
                        });

                        btnEliminar.setOnAction(e -> {
                            Animales animal = getTableView().getItems().get(getIndex());
                        });


                        btnInfo.setOnAction(e -> {
                            Animales animal = getTableView().getItems().get(getIndex());

                        });

                        contenedorBotones.setAlignment(Pos.CENTER);
                    }
                });
        cargarAnimales();
        btnAfiliacion.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Afiliaciones.fxml", "Gestion de afiliados", btnAfiliacion));
        btnDonaciones.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Donaciones.fxml", "Gestion de donaciones", btnDonaciones));
        btnAdoptante.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Adoptantes.fxml", "Gestion de adoptantes", btnAdoptante));
        btnCentro.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Centros.fxml", "Gestion de centros", btnCentro));
        btnAnimales.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Mascotas.fxml", "Gestion de mascotas", btnAnimales));
        btnEmpleado.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Empleados.fxml", "Gestion de empleados", btnEmpleado));
        cerrarSesion.setOnAction(event -> c.cambiarPantallaMenu("/org/utl/dsm/huellas_escritorio/Empleados/loginEmpleado.fxml", "Iniciar sesion", cerrarSesion));


    }
    public void cargarAnimales() {
        HttpResponse<String> response = Unirest.get("http://localhost:8080/ProyectoHuellas/api/mascotas/getAll")
                .asString();


        if (response.getStatus() == 200) {
            Gson gson = new Gson();
            Animales[] lista = gson.fromJson(response.getBody(), Animales[].class);
                listAnimales.addAll(Arrays.asList(lista));

        }
    }

}
