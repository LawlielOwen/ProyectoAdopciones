package org.utl.dsm.huellas_escritorio.Controlador.PanelAdoptantes;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.text.Font;

public class afiliacion implements  Initializable {
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



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Font interRegular = Font.loadFont(getClass().getResourceAsStream("/Fuentes/Inter_28pt-Regular.ttf"), 28);
        Font interBold = Font.loadFont(getClass().getResourceAsStream("/Fuentes/Inter_24pt-Bold.ttf"), 24);
        cambioModulo c = new cambioModulo();
        btnAfiliacion.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Clientes/Afiliacion.fxml", "Afiliacion", btnAfiliacion));
        btnEmpleado.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/loginEmpleado.fxml", "Empleados", btnAfiliacion));
        btnLogin.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Clientes/Login/login.fxml", "Iniciar Sesion", btnAfiliacion));
        btnAdopta.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Clientes/inicio.fxml", "Iniciar Sesion", btnAfiliacion));

    }
    private void cambiarPantalla(String rutaFXML, String titulo, Button botonReferencia) {
        try {
            Stage stage = (Stage) botonReferencia.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(rutaFXML));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.setWidth(1106);
            stage.setHeight(600);
            stage.setTitle(titulo);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
