package org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionSolicitud;

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
import org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionAdoptante.eliminarAdoptante;
import org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionMascotas.infoAnimal;
import org.utl.dsm.huellas_escritorio.Controlador.PanelAdoptantes.cambioModulo;
import org.utl.dsm.huellas_escritorio.Modelo.Animales;
import org.utl.dsm.huellas_escritorio.Modelo.Adoptante;
import org.utl.dsm.huellas_escritorio.Modelo.Centros;
import org.utl.dsm.huellas_escritorio.Modelo.Solicitudes;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;
public class gestionSolicitud implements  Initializable{

    @FXML
    private VBox barra;

    @FXML
    private Button btnAdoptante;

    @FXML
    private Button btnAfiliacion;

    @FXML
    private Button btnAnimales;

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
    private MenuItem cerrarSesion;

    @FXML
    private Label contadorAceptadas;

    @FXML
    private Label contadorPendiente;

    @FXML
    private Label contadorRechazadas;

    @FXML
    private AnchorPane contenedorBarra;

    @FXML
    private HBox contenedorBusqueda;

    @FXML
    private ComboBox<String> filtroEstatus;

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
    private TableView<Solicitudes> tblSolicitudes;
    @FXML
    private TableColumn<Solicitudes, String> tcolAdoptante;

    @FXML
    private TableColumn<Solicitudes, String> tcolContacto;

    @FXML
    private TableColumn<Solicitudes, String> tcolCorreo;

    @FXML
    private TableColumn<Solicitudes, Integer> tcolEstatus;

    @FXML
    private TableColumn<Solicitudes, String> tcolFecha;

    @FXML
    private TableColumn<Solicitudes, String> tcolNombre;

    @FXML
    private TableColumn<Solicitudes, Void> tcolRevisar;
    public ObservableList <Solicitudes> listSolicitudes;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cambioModulo c = new cambioModulo();
        Solicitudes s = new Solicitudes();
        listSolicitudes = FXCollections.observableArrayList();
        tblSolicitudes.setItems(listSolicitudes);
        cargarDatos();
        cargarContador();
        filtroEstatus.setOnAction(event -> filtrar());
        buscador.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    buscarSoli();
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

        tcolNombre.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()
                .getNombreAnimal()));
        tcolNombre.setCellFactory(column -> new TableCell<Solicitudes, String>() {
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
                    Solicitudes s = getTableView().getItems().get(getIndex());
                    HttpResponse<String> response = Unirest.get("http://localhost:8080/ProyectoHuellas/api/mascotas/getAll")
                            .asString();
                    if (response.getStatus() == 200) {
                        Gson gson = new Gson();
                        Animales[] animales = gson.fromJson(response.getBody(), Animales[].class);

                        Optional<Animales> animalOptional = Arrays.stream(animales)
                                .filter(a -> a.getIdAnimal() == s.getIdAnimal())
                                .findFirst();

                        if (animalOptional.isPresent()) {
                            try {
                                String foto = animalOptional.get().getFoto();
                                if (foto != null && foto.contains(",")) {
                                    String base64Image = foto.split(",")[1];
                                    byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                                    imageView.setImage(new Image(new ByteArrayInputStream(imageBytes)));
                                }
                            } catch (Exception e) {
                                imageView.setImage(null);
                            }
                        }
                    }
                    label.setText(item);
                    setGraphic(hbox);
                }
            }
        });
        tcolEstatus.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()
                .getEstatus() ));
        tcolEstatus.setCellFactory(column -> new TableCell<Solicitudes, Integer>() {
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
            protected void updateItem(Integer estatusInt, boolean empty) {
                super.updateItem(estatusInt, empty);

                if (empty || estatusInt == null) {
                    setGraphic(null);
                } else {

                    String textoEstatus;
                    String color;

                    switch (estatusInt) {
                        case 1:
                            textoEstatus = "Aceptada";
                            color = "#198754";
                            break;
                        case 2:
                            textoEstatus = "Pendiente";
                            color = "#ffc107";
                            break;
                        default:
                            textoEstatus = "Rechazada";
                            color = "#dc3545";
                    }


                    circle.setStyle(
                            "-fx-background-color: " + color + ";" +
                                    "-fx-background-radius: 50%;" +
                                    "-fx-min-width: 10px;" +
                                    "-fx-min-height: 10px;" +
                                    "-fx-max-width: 10px;" +
                                    "-fx-max-height: 10px;"
                    );

                    label.setText(textoEstatus);
                    label.setStyle("-fx-text-fill: #677788;");

                    setGraphic(hbox);
                }
            }
        });
        tcolFecha.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getFecha()));
        tcolAdoptante.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()
                .getNombreAdoptante()));
        tcolContacto.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getTelefono()));
        tcolCorreo.setCellValueFactory(param ->  new SimpleObjectProperty<>(param.getValue().getCorreo()));
        tcolRevisar.setCellFactory(param -> new TableCell<Solicitudes, Void>() {
            private final HBox container = new HBox(5);
            private final Button btnEliminar = new Button();
            private final Button btnDetalles = new Button();


            private final ImageView borra = new ImageView(new Image(getClass().getResourceAsStream("/Iconos/borrar.png")));
            private final ImageView muestra = new ImageView(new Image(getClass().getResourceAsStream("/Iconos/informacion.png")));

            {
                container.setAlignment(Pos.CENTER);


                borra.setFitHeight(16);
                borra.setFitWidth(16);
                muestra.setFitHeight(16);
                muestra.setFitWidth(16);


                btnEliminar.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: #dc3545; -fx-border-radius: 8px; "
                        + "-fx-cursor: hand; -fx-padding: 5px;");
                btnDetalles.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: #0dcaf0; -fx-border-radius: 8px; "
                        + "-fx-cursor: hand; -fx-padding: 5px;");


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


                btnEliminar.setGraphic(borra);
                btnDetalles.setGraphic(muestra);


                btnEliminar.setPrefWidth(70);
                btnDetalles.setPrefWidth(70);


                container.getChildren().addAll( btnDetalles,btnEliminar);

                btnDetalles.setOnAction(event -> {
                    Solicitudes s = getTableView().getItems().get(getIndex());

                    try {
                        info(s);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                btnEliminar.setOnAction(event -> {
                    Solicitudes a = getTableView().getItems().get(getIndex());
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
    }

    public void cargarDatos(){

        ObservableList<String> filtro = FXCollections.observableArrayList(
                "Todos", "Aceptada","Pendiente","Rechazada"
        );
        filtroEstatus.setItems(filtro);

        HttpResponse<String> res= Unirest.get("http://localhost:8080/ProyectoHuellas/api/solicitudes/getAll")
                .asString();
        if (res.getStatus() == 200) {
            Gson gson = new Gson();
            Solicitudes[] lista = gson.fromJson(res.getBody(), Solicitudes[].class);
            listSolicitudes.addAll(Arrays.asList(lista));

        }
    }
   public void cargarTodas(){
       HttpResponse<String> res= Unirest.get("http://localhost:8080/ProyectoHuellas/api/solicitudes/getTodas")
               .asString();
       if (res.getStatus() == 200) {
           Gson gson = new Gson();
           Solicitudes[] lista = gson.fromJson(res.getBody(), Solicitudes[].class);
           listSolicitudes.addAll(Arrays.asList(lista));

       }
   }
    public void filtrar(){
        String estatusSolicitud =  filtroEstatus.getValue();
        if ("Todos".equals(estatusSolicitud)) {
            listSolicitudes.clear();
            cargarTodas();
            return;
        }
        int estatusNum= 0;
        switch (estatusSolicitud) {
            case "Aceptada":
                estatusNum = 1;
                break;
            case "Pendiente":
                estatusNum = 2;
                break;
            case "Rechazada":
                estatusNum = 3;
                break;
        }
        String json = "{ \"estatus\": \"" + estatusNum + "\" }";
        HttpResponse<JsonNode> response = Unirest.post("http://localhost:8080/ProyectoHuellas/api/solicitudes/filtroEstatus")
                .header("Content-Type", "application/json")
                .body(json)
                .asJson();
        listSolicitudes.clear();
        Gson gson = new Gson();
        Solicitudes[] lista = gson.fromJson(String.valueOf(response.getBody()), Solicitudes[].class);
        listSolicitudes.addAll(Arrays.asList(lista));
        tblSolicitudes.setItems(listSolicitudes);

    }
    public void cargarContador(){
        HttpResponse<String> response = Unirest.get("http://localhost:8080/ProyectoHuellas/api/solicitudes/contarDisponibles")
                .asString();

        String soliPendientes = (String) response.getBody();
        contadorPendiente.setText(soliPendientes);

        HttpResponse<String> respons = Unirest.get("http://localhost:8080/ProyectoHuellas/api/solicitudes/contarAceptadas")
                .asString();

        String soliAceptadas = (String) respons.getBody();

        contadorAceptadas.setText(soliAceptadas);
        HttpResponse<String> res = Unirest.get("http://localhost:8080/ProyectoHuellas/api/solicitudes/contarRechazadas")
                .asString();

        String soliRechazadas = (String) res.getBody();

        contadorRechazadas.setText(soliRechazadas);
    }
    public void buscarSoli(){
        String nombre =buscador.getText().trim();
         String json = "{ \"nombreAnimal\": \"" + nombre+ "\", \"nombreAdoptante\": \"" + nombre + "\" }";
        HttpResponse<JsonNode> response = Unirest.post("http://localhost:8080/ProyectoHuellas/api/solicitudes/buscarSoli")
                .header("Content-Type", "application/json")
                .body(json)
                .asJson();
        listSolicitudes.clear();
        Gson gson = new Gson();
        Solicitudes[] lista = gson.fromJson(String.valueOf(response.getBody()), Solicitudes[].class);
        listSolicitudes.addAll(Arrays.asList(lista));
        tblSolicitudes.setItems(listSolicitudes);
        if (nombre.isEmpty()){
            listSolicitudes.clear();
            cargarDatos();
        }

    }
    public void info(Solicitudes s) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/utl/dsm/huellas_escritorio/Empleados/Formularios/GestionSolicitudes/revisarSolicitud.fxml"));
        Parent root = loader.load();

        infoSolicitud controller = loader.getController();
        controller.setController(this);
        controller.cargarInfo(s);

        Stage stage = new Stage();
        stage.setTitle("Solicitud");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    public void abrirEliminar(Solicitudes a) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/utl/dsm/huellas_escritorio/Empleados/Formularios/GestionSolicitudes/borrarSolicitud.fxml"));
            Parent root = loader.load();

            borrarSolicitud controller = loader.getController();
            controller.setController(this);
            controller.cargarNombre(a);

            Stage stage = new Stage();
            stage.setTitle("Eliminar solicitud");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




