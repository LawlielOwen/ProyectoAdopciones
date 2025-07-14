package org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionAfiliado;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import org.utl.dsm.huellas_escritorio.Controlador.PanelAdoptantes.cambioModulo;
import org.utl.dsm.huellas_escritorio.Modelo.Afiliados;

import java.net.URL;
import java.util.*;

public class gestionAfiliados implements  Initializable{
    @FXML
    private Button btnAdoptante;
    @FXML
    private Button btnAnimales;
    @FXML
    private Button btnAfiliacion;

    @FXML
    private Button btnCentro;
    @FXML
    private MenuItem cerrarSesion;
    @FXML
    private Button btnDonaciones;

    @FXML
    private Button btnEmpleado;

    @FXML
    private Button btnSolicitud;

    @FXML
    private TextField buscador;


    @FXML
    private Button mini;

    @FXML
    private TableColumn<?, ?> tcolCorreo;

    @FXML
    private TableColumn<?, ?> tcolDisponibilidad;

    @FXML
    private TableColumn<?, ?> tcolMedio;

    @FXML
    private TableColumn<?, ?> tcolNombre;

    @FXML
    private TableColumn<Afiliados, Void> tcolOpciones;

    @FXML
    private TableColumn<?, ?> tcolTelefono;

    @FXML
    private TableColumn<?, ?> tcolTipo;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cambioModulo c = new cambioModulo();
        // de una vez les genero los botones para que no batallen ya namas encarguense del resto de la tabla
        // los botones de aqui funcional igual que los demas
        tcolOpciones.setCellFactory(param -> new TableCell<Afiliados, Void>() {
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
                    Afiliados f = getTableView().getItems().get(getIndex());

                });

                btnEliminar.setOnAction(event -> {
                    Afiliados f = getTableView().getItems().get(getIndex());

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
