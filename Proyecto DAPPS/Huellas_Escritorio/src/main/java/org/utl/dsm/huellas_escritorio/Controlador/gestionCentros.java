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
import org.utl.dsm.huellas_escritorio.Modelo.Centros;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
public class gestionCentros implements  Initializable{
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
    private MenuItem cerrarSesion;
    @FXML
    private ComboBox<?> filtroEstatus;


    @FXML
    private Button mini;

    @FXML
    private TableColumn<?, ?> tcolCorreo;

    @FXML
    private TableColumn<?, ?> tcolDireccion;

    @FXML
    private TableColumn<?, ?> tcolEstatus;
    @FXML
    private Button btnAnimales;
    @FXML
    private TableColumn<?, ?> tcolHorario;

    @FXML
    private TableColumn<?, ?> tcolNombre;

    @FXML
    private TableColumn<Centros,Void> tcolOpciones;

    @FXML
    private TableColumn<?, ?> tcolTelefono;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cambioModulo c = new cambioModulo();

 //De una vez les genere la fila para los botones ustedes namas hagan el resto de la funcionalidad
        // los botones de aqui funcional igual que los demas
        tcolOpciones.setCellFactory(param -> new TableCell<Centros, Void>() {
            private final HBox container = new HBox(5);
            private final Button btnEditar = new Button();
            private final Button btnEliminar = new Button();


            {
                ImageView modifica= new ImageView(new Image(getClass().getResourceAsStream("/Iconos/Gestiones/editar.png")));
                ImageView borra = new ImageView(new Image(getClass().getResourceAsStream("/Iconos/borrar.png")));


                container.setAlignment(Pos.CENTER);

                btnEditar.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: #6c757d; -fx-border-radius: 8px;");
                btnEliminar.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-border-color: #dc3545; -fx-border-radius: 8px;");
                modifica.setFitHeight(16);
                modifica.setFitWidth(16);
                borra.setFitHeight(16);
                borra.setFitWidth(16);


                btnEditar.setGraphic(modifica);
                btnEliminar.setGraphic(borra);

                btnEditar.setPrefWidth(70);
                btnEliminar.setPrefWidth(70);

                container.getChildren().addAll( btnEditar, btnEliminar);

                btnEditar.setOnAction(event -> {
                    Centros c = getTableView().getItems().get(getIndex());

                });

                btnEliminar.setOnAction(event -> {
                    Centros c = getTableView().getItems().get(getIndex());

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
        btnAfiliacion.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Afiliaciones.fxml", "Gestion de afiliados", btnAfiliacion));
        btnDonaciones.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Donaciones.fxml", "Gestion de donaciones", btnDonaciones));
        btnAdoptante.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Adoptantes.fxml", "Gestion de adoptantes", btnAdoptante));
        btnCentro.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Centros.fxml", "Gestion de centros", btnCentro));
        btnAnimales.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Mascotas.fxml", "Gestion de mascotas", btnAnimales));
        btnEmpleado.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/Empleados.fxml", "Gestion de empleados", btnEmpleado));
        cerrarSesion.setOnAction(event -> c.cambiarPantallaMenu("/org/utl/dsm/huellas_escritorio/Empleados/loginEmpleado.fxml", "Iniciar sesion", cerrarSesion));
    }
}
