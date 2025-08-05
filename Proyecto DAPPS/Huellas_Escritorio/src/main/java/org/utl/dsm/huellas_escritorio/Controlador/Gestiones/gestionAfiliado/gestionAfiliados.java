package org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionAfiliado;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import com.google.gson.Gson;
import org.utl.dsm.huellas_escritorio.Controlador.PanelAdoptantes.cambioModulo;
import org.utl.dsm.huellas_escritorio.Modelo.Afiliados;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class gestionAfiliados implements  Initializable{
    @FXML private Button btnAdoptante;
    @FXML private Button btnAnimales;
    @FXML private Button btnAfiliacion;
    @FXML private Button btnCentro;
    @FXML private Button btnDonaciones;
    @FXML private Button btnEmpleado;
    @FXML private Button btnSolicitud;
    @FXML private MenuItem cerrarSesion;

    @FXML private TextField buscador;
    @FXML private TableView<Afiliados> tablaAfiliados;
    @FXML private TableColumn<Afiliados, String> tcolNombre;
    @FXML private TableColumn<Afiliados, String> tcolCorreo;
    @FXML private TableColumn<Afiliados, String> tcolTelefono;
    @FXML private TableColumn<Afiliados, String> tcolTipo;
    @FXML private TableColumn<Afiliados, String> tcolDisponibilidad;
    @FXML private TableColumn<Afiliados, String> tcolMedio;
    @FXML private TableColumn<Afiliados, Void> tcolOpciones;

    public ObservableList<Afiliados> listAfiliados;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cambioModulo c = new cambioModulo();
        listAfiliados = FXCollections.observableArrayList();
        tablaAfiliados.setItems(listAfiliados);


        tcolNombre.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getNombre()));
        tcolCorreo.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getCorreo()));
        tcolTelefono.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getTelefono()));
        tcolTipo.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getTipoAfiliado()));
        tcolDisponibilidad.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getDisponibilidadAfiliado()));
        tcolMedio.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getMedioContactoAfiliado()));


        tcolOpciones.setCellFactory(param -> new TableCell<Afiliados, Void>() {
            private final HBox container = new HBox(5);
            private final Button btnEditar = new Button();
            private final Button btnEliminar = new Button();


            {
                ImageView modifica = new ImageView(new Image(getClass().getResourceAsStream("/Iconos/Gestiones/editar.png")));
                ImageView borra = new ImageView(new Image(getClass().getResourceAsStream("/Iconos/borrar.png")));


                container.setAlignment(Pos.CENTER);

                btnEditar.setStyle("-fx-background-color: transparent; -fx-border-color: #6c757d; -fx-border-radius: 8px;");
                btnEliminar.setStyle("-fx-background-color: transparent; -fx-border-color: #dc3545; -fx-border-radius: 8px;");
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

                modifica.setFitHeight(16); modifica.setFitWidth(16);
                borra.setFitHeight(16); borra.setFitWidth(16);


                btnEditar.setGraphic(modifica);
                btnEliminar.setGraphic(borra);

                btnEditar.setPrefWidth(70); btnEliminar.setPrefWidth(70);

                container.getChildren().addAll(btnEditar, btnEliminar);

                btnEditar.setOnAction(event -> {
                    Afiliados A = getTableView().getItems().get(getIndex());
                    abrirModificar(A);
                });

                btnEliminar.setOnAction(event -> {
                    Afiliados A = getTableView().getItems().get(getIndex());
                    abrirEliminar(A);
                });


            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : container);
            }
        });

        cargarAfiliados();

        buscador.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) buscarAfiliados();
        });
        buscador.textProperty().addListener((obs, oldText, newText) -> {
            if (newText.isEmpty()) cargarAfiliados();
        });
        btnSolicitud.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Solicitudes.fxml", "Gestion de Solicitudes", btnSolicitud));
        btnAfiliacion.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Afiliaciones.fxml", "Gestion de afiliados", btnAfiliacion));
        btnDonaciones.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Donaciones.fxml", "Gestion de donaciones", btnDonaciones));
        btnAdoptante.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Adoptantes.fxml", "Gestion de adoptantes", btnAdoptante));
        btnCentro.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Centros.fxml", "Gestion de centros", btnCentro));
        btnAnimales.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Mascotas.fxml", "Gestion de mascotas", btnAnimales));
        btnEmpleado.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Empleados.fxml", "Gestion de empleados", btnEmpleado));
        cerrarSesion.setOnAction(event -> c.cambiarPantallaMenu("/org/utl/dsm/huellas_escritorio/Empleados/loginEmpleado.fxml", "Iniciar sesion", cerrarSesion));
    }
    public void cargarAfiliados() {
        HttpResponse<String> response = Unirest.get("http://localhost:8080/ProyectoHuellas/api/Afiliacion/getAll")
                .asString();
        listAfiliados.clear();
        if (response.getStatus() == 200) {
            Gson gson = new Gson();
            Afiliados[] lista = gson.fromJson(response.getBody(), Afiliados[].class);
            listAfiliados.addAll(Arrays.asList(lista));
        }
    }

    public void buscarAfiliados() {
        String texto = buscador.getText().trim();
        if (texto.isEmpty()) {
            cargarAfiliados();
            return;
        }
        HttpResponse<String> response = Unirest.post("http://localhost:8080/ProyectoHuellas/api/Afiliacion/buscar")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body("texto=" + texto)
                .asString();
        listAfiliados.clear();
        if (response.getStatus() == 200) {
            Gson gson = new Gson();
            Afiliados[] lista = gson.fromJson(response.getBody(), Afiliados[].class);
            listAfiliados.addAll(Arrays.asList(lista));
        }
    }


    public void abrirModificar(Afiliados afiliado) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/utl/dsm/huellas_escritorio/Empleados/Formularios/GestionAfiliados/modificarAfiliado.fxml"));
            Parent root = loader.load();

            modificarAfiliados controller = loader.getController();
            controller.setController(this);
            controller.cargarAfiliado(afiliado);

            Stage stage = new Stage();
            stage.setTitle("Modificar afiliado");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void abrirEliminar(Afiliados A) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/utl/dsm/huellas_escritorio/Empleados/Formularios/GestionAfiliados/borrarAfiliado.fxml"));
            Parent root = loader.load();

            eliminarAfiliados controller = loader.getController();
            controller.setController(this);
            controller.cargarNombre(A);

            Stage stage = new Stage();
            stage.setTitle("Eliminar afiliado");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
