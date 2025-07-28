package org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionMascotas;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import org.utl.dsm.huellas_escritorio.Controlador.PanelAdoptantes.cambioModulo;
import org.utl.dsm.huellas_escritorio.Modelo.Animales;
import org.utl.dsm.huellas_escritorio.Modelo.Centros;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class gestionMascotas implements  Initializable{
    @FXML
    private Button cancelarMod;



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
    private ComboBox<String> filtroEspecie;

    @FXML
    private ComboBox<String> filtroEstatus;

    @FXML
    private MenuItem cerrarSesion;


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
    private Pagination paginacion;
    @FXML
    private TableColumn<Animales, Void> tcolOpciones;
    @FXML
    private TableView<Animales> tablaAnimales;
  @FXML
   private TableColumn<Animales, String> tcolPeso;
    @FXML
    private Button btnAnimales;
    @FXML
    private TableColumn<Animales, String> tcolRaza;

    @FXML
    private TableColumn<Animales, String> tcolSexo;
    private static final int ITEMS_PER_PAGE = 5;
    public ObservableList <Animales> listAnimales;

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
                .getEdad()+ " años"));

        tcolEstatus.setCellValueFactory(param ->
                new SimpleObjectProperty<>(param.getValue().getEstatusTexto())
        );

// Celda personalizada para mostrar un círculo de color y el texto del estatus
        tcolEstatus.setCellFactory(column -> new TableCell<Animales, String>() {
            private final HBox hbox = new HBox(6);
            private final Label circle = new Label();
            private final Label label = new Label();

            {
                hbox.setAlignment(Pos.CENTER_LEFT);

                circle.setMinSize(10, 10);
                circle.setMaxSize(10, 10);
                circle.setStyle("-fx-background-radius: 50%;");

                hbox.getChildren().addAll(circle, label);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    // Definir el color según el estatus
                    String color = switch (item.toLowerCase()) {
                        case "en adopcion" -> "#198754"; // verde
                        case "adoptado" -> "#ffc107";   // amarillo
                        default -> "#6c757d";           // gris neutro
                    };

                    circle.setStyle(
                            "-fx-background-color: " + color + ";" +
                                    "-fx-background-radius: 50%;" +
                                    "-fx-min-width: 10px;" +
                                    "-fx-min-height: 10px;" +
                                    "-fx-max-width: 10px;" +
                                    "-fx-max-height: 10px;"
                    );

                    label.setText(item);
                    label.setStyle("-fx-text-fill: #677788;");

                    setGraphic(hbox);
                }
            }
        });


        tcolEspecie.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()
                .getEspecie()));
        tcolPeso.setCellValueFactory(param ->
                new SimpleObjectProperty<>(param.getValue().getPeso() + " kg"));
        tcolRaza.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()
                .getRaza()));
        tcolSexo.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()
                .getGenero()));
        tcolOpciones.setCellFactory(param -> new TableCell<Animales, Void>() {
            private final HBox container = new HBox(5);
            private final Button btnEditar = new Button();
            private final Button btnEliminar = new Button();
            private final Button btnDetalles = new Button();


            private final ImageView modifica = new ImageView(new Image(getClass().getResourceAsStream("/Iconos/Gestiones/editar.png")));
            private final ImageView borra = new ImageView(new Image(getClass().getResourceAsStream("/Iconos/borrar.png")));
            private final ImageView muestra = new ImageView(new Image(getClass().getResourceAsStream("/Iconos/informacion.png")));

            {
                container.setAlignment(Pos.CENTER);


                modifica.setFitHeight(16);
                modifica.setFitWidth(16);
                borra.setFitHeight(16);
                borra.setFitWidth(16);
                muestra.setFitHeight(16);
                muestra.setFitWidth(16);


                btnEditar.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: #6c757d; -fx-border-radius: 8px; "
                        + "-fx-cursor: hand; -fx-padding: 5px;");
                btnEliminar.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: #dc3545; -fx-border-radius: 8px; "
                        + "-fx-cursor: hand; -fx-padding: 5px;");
                btnDetalles.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: #0dcaf0; -fx-border-radius: 8px; "
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

                btnDetalles.setOnMouseEntered(e -> {
                    btnDetalles.setStyle("-fx-background-color: #0dcaf0; -fx-text-fill: white; -fx-border-color: #0dcaf0; -fx-border-radius: 8px; "
                            + "-fx-cursor: hand; -fx-padding: 5px;");
                    ColorAdjust whiteEffect = new ColorAdjust();
                    whiteEffect.setBrightness(1.0);
                    muestra.setEffect(whiteEffect);
                });

                btnDetalles.setOnMouseExited(e -> {
                    btnDetalles.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: #0dcaf0; -fx-border-radius: 8px; "
                            + "-fx-cursor: hand; -fx-padding: 5px;");
                    muestra.setEffect(null);
                });


                btnEditar.setGraphic(modifica);
                btnEliminar.setGraphic(borra);
                btnDetalles.setGraphic(muestra);


                btnEditar.setPrefWidth(70);
                btnEliminar.setPrefWidth(70);
                btnDetalles.setPrefWidth(70);


                container.getChildren().addAll(btnEditar, btnEliminar, btnDetalles);


                btnEditar.setOnAction(event -> {
                    Animales animal = getTableView().getItems().get(getIndex());
                    Centros centro = obtenerCentro(animal);
                    abrirModificar(animal, centro);
                });

                btnEliminar.setOnAction(event -> {
                    Animales animal = getTableView().getItems().get(getIndex());
                    abrirBorrar(animal);
                });
                btnDetalles.setOnAction(event -> {
                    Animales animal = getTableView().getItems().get(getIndex());
                    Centros centro = obtenerCentro(animal);
                    try {
                        info(animal, centro);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
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
        tcolNombre.setCellFactory(column -> new TableCell<Animales, String>() {
            private final HBox hbox = new HBox(10);
            private final ImageView imageView = new ImageView();
            private final Label label = new Label();

            {
                hbox.setAlignment(Pos.CENTER_LEFT);
                imageView.setFitHeight(40);
                imageView.setFitWidth(40);
                imageView.setPreserveRatio(false);

                Rectangle clip = new Rectangle(40, 40);
                clip.setArcWidth(40);
                clip.setArcHeight(40);
                imageView.setClip(clip);

                hbox.getChildren().addAll(imageView, label);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    Animales animal = getTableView().getItems().get(getIndex());

                    try {
                        String base64Image = animal.getFoto().split(",")[1];
                        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                        imageView.setImage(new Image(new ByteArrayInputStream(imageBytes)));
                    } catch (Exception e) {
                        imageView.setImage(null);
                    }

                    label.setText(item);
                    label.setStyle("-fx-font-weight: bold; -fx-text-fill: #000000;   -fx-font-family:Inter Bold;");

                    setGraphic(hbox);
                }
            }
        });
        cargarAnimales();
        cargarContador();
        btnSolicitud.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Solicitudes.fxml", "Gestion de Solicitudes", btnSolicitud));
        btnAfiliacion.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Afiliaciones.fxml", "Gestion de afiliados", btnAfiliacion));
        btnDonaciones.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Donaciones.fxml", "Gestion de donaciones", btnDonaciones));
        btnAdoptante.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Adoptantes.fxml", "Gestion de adoptantes", btnAdoptante));
        btnCentro.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Centros.fxml", "Gestion de centros", btnCentro));
        btnAnimales.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Mascotas.fxml", "Gestion de mascotas", btnAnimales));
        btnEmpleado.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Empleados.fxml", "Gestion de empleados", btnEmpleado));
        cerrarSesion.setOnAction(event -> c.cambiarPantallaMenu("/org/utl/dsm/huellas_escritorio/Empleados/loginEmpleado.fxml", "Iniciar sesion", cerrarSesion));
        btnAgregar.setOnAction(event -> abrirAgregar());
        buscador.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    buscarAnimal();
                }
            }
        });
        filtroEspecie.setOnAction(event -> filtros());
        filtroEstatus.setOnAction(event -> filtrarE());

    }
    private void configurarPaginacion() {

        int totalPages = (int) Math.ceil((double) listAnimales.size() / ITEMS_PER_PAGE);
        paginacion.setPageCount(totalPages == 0 ? 1 : totalPages);

        paginacion.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            crearPagina(newIndex.intValue());
        });
        crearPagina(0);

    }

    private Node crearPagina(int pageIndex) {
        int fromIndex = pageIndex * ITEMS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, listAnimales.size());


        tablaAnimales.setItems(FXCollections.observableArrayList(
                listAnimales.subList(fromIndex, toIndex)
        ));


        return null;
    }
    public void cargarAnimales() {
        HttpResponse<String> response = Unirest.get("http://localhost:8080/ProyectoHuellas/api/mascotas/getAll")
                .asString();


        if (response.getStatus() == 200) {
            Gson gson = new Gson();
            Animales[] lista = gson.fromJson(response.getBody(), Animales[].class);
                listAnimales.addAll(Arrays.asList(lista));

        }
        ObservableList<String> filtroA = FXCollections.observableArrayList(
                "Todos", "Perros","Gatos"
        );
        filtroEspecie.setItems(filtroA);


        ObservableList<String> estatus = FXCollections.observableArrayList(
                "Todos","En adopcion", "Adoptado"
        );
        filtroEstatus.setItems(estatus);
        configurarPaginacion();
    }

    public void abrirAgregar()  {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/utl/dsm/huellas_escritorio/Empleados/Formularios/GestionAnimales/agregarAnimal.fxml"));
            Parent root = loader.load();

            formulariosAnimal controller = loader.getController();
            controller.setController(this);
            Stage stage = new Stage();
            stage.setTitle("Agregar animal");
            stage.setScene(new Scene(root));
            stage.setResizable(false);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void abrirBorrar(Animales animal)  {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/utl/dsm/huellas_escritorio/Empleados/Formularios/GestionAnimales/borrarAnimal.fxml"));
            Parent root = loader.load();
           borrarAnimal controller = loader.getController();
           controller.setController(this);
           controller.setAnimalAEliminar(animal);
            Stage stage = new Stage();
            stage.setTitle("Borrar animal");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarContador(){
        HttpResponse<String> response = Unirest.get("http://localhost:8080/ProyectoHuellas/api/mascotas/contarDisponibles")
                .asString();

        String animalesDisonibles = (String) response.getBody();

        contadorDisponibles.setText(animalesDisonibles);

        HttpResponse<String> respons = Unirest.get("http://localhost:8080/ProyectoHuellas/api/mascotas/contarAdoptados")
                .asString();

        String animalesAdoptados = (String) respons.getBody();

        contadorAdoptados.setText(animalesAdoptados);
    }

    public void buscarAnimal(){
        String nombre =buscador.getText().trim();
        String json = "{ \"nombreAnimal\": \"" + nombre + "\" }";
        HttpResponse<JsonNode> response = Unirest.post("http://localhost:8080/ProyectoHuellas/api/mascotas/buscarAnimal")
                .header("Content-Type", "application/json")
                .body(json)
                .asJson();
        listAnimales.clear();
        Gson gson = new Gson();
        Animales[] lista = gson.fromJson(String.valueOf(response.getBody()), Animales[].class);
        listAnimales.addAll(Arrays.asList(lista));
        tablaAnimales.setItems(listAnimales);
     if (nombre.isEmpty()){
         listAnimales.clear();
         cargarAnimales();
     }
        configurarPaginacion();
    }

    public void filtros(){
        String especies =  filtroEspecie.getValue();

        if (especies == null || especies.equals("Todos")) {
            listAnimales.clear();
            cargarAnimales();
            return;
        }
        String json = "{ \"especie\": \"" + especies + "\" }";
        HttpResponse<JsonNode> response = Unirest.post("http://localhost:8080/ProyectoHuellas/api/mascotas/filtroEspecie")
                .header("Content-Type", "application/json")
                .body(json)
                .asJson();
        listAnimales.clear();
        Gson gson = new Gson();
        Animales[] lista = gson.fromJson(String.valueOf(response.getBody()), Animales[].class);
        listAnimales.addAll(Arrays.asList(lista));
        tablaAnimales.setItems(listAnimales);
        configurarPaginacion();

    }
    public void filtrarE(){
        String estatus =  filtroEstatus.getValue();
        int estatusNum= 0;

        if (estatus.equals("Adoptado")) {
            estatusNum = 2;
        } else if(estatus.equals("En adopcion")){
            estatusNum = 1;
        }else{
            listAnimales.clear();
            cargarAnimales();
            return;
        }
        String json = "{ \"estatus\": \"" + estatusNum + "\" }";
        HttpResponse<JsonNode> response = Unirest.post("http://localhost:8080/ProyectoHuellas/api/mascotas/filtroEstatus")
                .header("Content-Type", "application/json")
                .body(json)
                .asJson();
        listAnimales.clear();
        Gson gson = new Gson();
        Animales[] lista = gson.fromJson(String.valueOf(response.getBody()), Animales[].class);
        listAnimales.addAll(Arrays.asList(lista));
        tablaAnimales.setItems(listAnimales);
        configurarPaginacion();
    }
    public void abrirModificar(Animales animal, Centros centro) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/utl/dsm/huellas_escritorio/Empleados/Formularios/GestionAnimales/modificarAnimal.fxml"));
            Parent root = loader.load();

            modificarAnimal controller = loader.getController();
            controller.setController(this);
            controller.cargarDatosAnimal(animal,centro);

            Stage stage = new Stage();
            stage.setTitle("Modificar animal");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
public void info(Animales a, Centros c) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/utl/dsm/huellas_escritorio/Empleados/Formularios/GestionAnimales/infoAnimal.fxml"));
    Parent root = loader.load();

    infoAnimal controller = loader.getController();
    controller.setController(this);
    controller.cargarInfo(a,c);

    Stage stage = new Stage();
    stage.setTitle("Informacion del animal");
    stage.setScene(new Scene(root));
    stage.setResizable(false);
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.show();
}
    private void cerrarVentana() {
        Stage stage = (Stage) cancelarMod.getScene().getWindow();
        stage.close();
    }
    private Centros obtenerCentro(Animales animal) {
        try {
            HttpResponse<String> response = Unirest.get("http://localhost:8080/ProyectoHuellas/api/centros/getAll").asString();
            if (response.getStatus() == 200) {
                Gson gson = new Gson();
                Centros[] centros = gson.fromJson(response.getBody(), Centros[].class);
                for (Centros c : centros) {
                    if (c.getIdCentro() == animal.getIdCentro()) {
                        return c;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
