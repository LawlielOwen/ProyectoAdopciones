package org.utl.dsm.huellas_escritorio.Controlador.PanelAdoptantes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.utl.dsm.huellas_escritorio.Modelo.Solicitudes;
import org.utl.dsm.huellas_escritorio.Modelo.Sesion;
public class solicitudesController implements Initializable {
    @FXML
    private TextField animal;

    @FXML
    private Text correoUsuario;
    @FXML
    private Text NombreUsuario;

    @FXML
    private Button btnLogout;

    @FXML
    private MenuButton menuSesion;

    @FXML
    private Button btnAdopta;

    @FXML
    private Button btnAfiliacion;

    @FXML
    private Button btnDonacion;

    @FXML
    private Button btnEmpleado;

    @FXML
    private Button btnEnviar;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnQuien;

    @FXML
    private TextField correo;

    @FXML
    private TextField direccion;

    @FXML
    private TextField motivo;

    @FXML
    private TextField nombre;

    @FXML
    private TextField telefono;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Font interRegular = Font.loadFont(getClass().getResourceAsStream("/Fuentes/Inter_28pt-Regular.ttf"), 28);
        Font interBold = Font.loadFont(getClass().getResourceAsStream("/Fuentes/Inter_24pt-Bold.ttf"), 24);
        cambioModulo c = new cambioModulo();
        btnAfiliacion.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Clientes/Afiliacion.fxml", "Afiliacion", btnAfiliacion));
        btnEmpleado.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/loginEmpleado.fxml", "Empleados", btnEmpleado));
        btnLogin.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Clientes/Login/login.fxml", "Iniciar Sesion", btnLogin));
        btnAdopta.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Clientes/inicio.fxml", "Iniciar Sesion", btnAdopta));
        if(Sesion.getAdoptanteActual() != null){
            btnEmpleado.setVisible(false);
            btnEmpleado.setManaged(false);
            btnLogin.setManaged(false);
            btnLogin.setVisible(false);
            menuSesion.setVisible(true);
            menuSesion.setManaged(true);
            menuSesion.setText(Sesion.getAdoptanteActual().getNombre());
            NombreUsuario.setText(Sesion.getAdoptanteActual().getNombre() +" " + Sesion.getAdoptanteActual().getApp() + " "+ Sesion.getAdoptanteActual().getApm());
            correoUsuario.setText(Sesion.getAdoptanteActual().getCorreo());
            nombre.setText(Sesion.getAdoptanteActual().getNombre() +" " + Sesion.getAdoptanteActual().getApp() + " "+ Sesion.getAdoptanteActual().getApm());
            animal.setText(Sesion.getAnimalActual().getNombreAnimal());
            ImageView img = (ImageView) menuSesion.getGraphic();

            ColorAdjust blanco = new ColorAdjust();
            blanco.setBrightness(0);

            ColorAdjust negro = new ColorAdjust();
            negro.setBrightness(-1);

            img.setEffect(blanco);


            menuSesion.setOnMouseEntered(e -> {
                menuSesion.setStyle("-fx-background-color: white; -fx-text-fill: black;");
                img.setEffect(negro);
            });
            menuSesion.setOnMouseExited(e -> {
                menuSesion.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
                img.setEffect(blanco);
            });
        }
        btnLogout.setOnAction(
                event -> {
                    Sesion.cerrarSesion();
                    btnEmpleado.setManaged(true);
                    btnEmpleado.setVisible(true);
                    btnLogin.setManaged(true);
                    btnLogin.setVisible(true);
                    menuSesion.setVisible(false);
                    menuSesion.setManaged(false);
                    NombreUsuario.setText("");
                    correoUsuario.setText("");
                    menuSesion.setText("");
                });
        btnEnviar.setOnAction(event -> enviar());
        }

        public void enviar(){
        int idAni = Sesion.getAnimalActual().getIdAnimal();
        int idAdop = Sesion.getAdoptanteActual().getIdAdoptante();

            String json = "{"
                    + "\"motivo\":\"" + motivo.getText() + "\","
                    + "\"telefono\":\"" + telefono.getText() + "\","
                    + "\"correo\":\"" + correo.getText() + "\","
                    + "\"direccion\":\"" + direccion.getText() + "\","
                    + "\"idAnimal\":\"" + idAni+ "\","
                    + "\"idAdoptante\":" + idAdop
                    + "}";
            HttpResponse<JsonNode> response = Unirest.post("http://localhost:8080/ProyectoHuellas/api/solicitudes/saveSolicitud")
                    .header("Content-Type", "application/json")
                    .body(json)
                    .asJson();
            if(response.getStatus() == 200){

                cambioModulo c = new cambioModulo();
                c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Clientes/inicio.fxml", "Inicio",btnEnviar);
            }
        }
    }
