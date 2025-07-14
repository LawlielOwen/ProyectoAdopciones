package org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionMascotas;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private TextArea descripcionMod;

    @FXML
    private TextField edadMod;

    @FXML
    private ComboBox<?> especieMod;

    @FXML
    private ImageView fotoMuestra;

    @FXML
    private ComboBox<?> generoMod;

    @FXML
    private Button guardar;

    @FXML
    private ComboBox<?> nombreCentroMod;

    @FXML
    private TextField nombreMod;

    @FXML
    private TextField pesoMod;

    @FXML
    private TextField razaMod;

    @FXML
    private ComboBox<?> tamanoMod;

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
        tcolEstatus.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue()
                .getEstatusTexto()));
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

            {
                ImageView modifica= new ImageView(new Image(getClass().getResourceAsStream("/Iconos/Gestiones/editar.png")));
                ImageView borra = new ImageView(new Image(getClass().getResourceAsStream("/Iconos/borrar.png")));
                ImageView muestra= new ImageView(new Image(getClass().getResourceAsStream("/Iconos/informacion.png")));

                container.setAlignment(Pos.CENTER);

                btnEditar.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: #6c757d; -fx-border-radius: 8px;");
                btnEliminar.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: #dc3545; -fx-border-radius: 8px;");
                btnDetalles.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: #0dcaf0;\n" +
                        "-fx-border-radius: 8px;");
                modifica.setFitHeight(16);
                modifica.setFitWidth(16);
                borra.setFitHeight(16);
                borra.setFitWidth(16);
                muestra.setFitHeight(16);
                muestra.setFitWidth(16);

                btnEditar.setGraphic(modifica);
                btnEliminar.setGraphic(borra);
                btnDetalles.setGraphic(muestra);
                btnEditar.setPrefWidth(70);
                btnEliminar.setPrefWidth(70);
                btnDetalles.setPrefWidth(70);

                container.getChildren().addAll( btnEditar, btnEliminar,btnDetalles);

                btnEditar.setOnAction(event -> {
                    Animales animal = getTableView().getItems().get(getIndex());
                    Centros centro = obtenerCentro(animal);
                   abrirModificar(animal,centro);
                });
                btnEliminar.setOnAction(event -> {
                    Animales animal = getTableView().getItems().get(getIndex());
                    abrirBorrar(animal);
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
                    label.setStyle("-fx-font-weight: 600; -fx-text-fill: #000000;");

                    setGraphic(hbox);
                }
            }
        });
        cargarAnimales();
        cargarContador();
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
    }
    public void abrirAgregar()  {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/utl/dsm/huellas_escritorio/Empleados/Formularios/agregarAnimal.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/utl/dsm/huellas_escritorio/Empleados/Formularios/borrarAnimal.fxml"));
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
    }
    public void abrirModificar(Animales animal, Centros centro) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/utl/dsm/huellas_escritorio/Empleados/Formularios/modificarAnimal.fxml"));
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
