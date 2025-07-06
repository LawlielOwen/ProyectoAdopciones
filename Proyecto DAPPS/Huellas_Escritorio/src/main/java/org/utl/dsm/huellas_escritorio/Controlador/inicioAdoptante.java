package org.utl.dsm.huellas_escritorio.Controlador;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.net.URL;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class inicioAdoptante implements  Initializable {
    @FXML
    private Button btnAdopta;

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
    private TextField buscador;
    @FXML
    private Rectangle rectanguloFondo;

    @FXML
    private ImageView imagenFondo;
    @FXML
    private StackPane container;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imagenFondo.fitWidthProperty().bind(container.widthProperty());
        rectanguloFondo.widthProperty().bind(container.widthProperty());
        btnAfiliacion.setOnAction(event -> cambiarPantalla("/org/utl/dsm/huellas_escritorio/Clientes/Afiliacion.fxml", "Afiliación", btnAfiliacion));
        btnEmpleado.setOnAction(event -> cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/loginEmpleado.fxml", "Empleados", btnEmpleado));
        btnLogin.setOnAction(event -> cambiarPantalla("/org/utl/dsm/huellas_escritorio/Clientes/login.fxml", "Iniciar Sesión", btnLogin));
        btnAdopta.setOnAction(event -> cambiarPantalla("/org/utl/dsm/huellas_escritorio/Clientes/inicio.fxml", "Inicio", btnAdopta));

    }
    public void cambiarPantalla(String rutaFXML, String titulo, Button botonReferencia) {
        try {
            Stage stage = (Stage) botonReferencia.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(rutaFXML));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.setWidth(1106);
            stage.setHeight(600);
            stage.setTitle(titulo);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
