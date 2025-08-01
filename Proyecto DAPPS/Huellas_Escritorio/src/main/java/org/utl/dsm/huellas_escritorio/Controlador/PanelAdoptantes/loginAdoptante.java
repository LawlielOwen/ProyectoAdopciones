package org.utl.dsm.huellas_escritorio.Controlador.PanelAdoptantes;

import com.google.gson.Gson;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.utl.dsm.huellas_escritorio.Controlador.Alertas.alertaError;
import org.utl.dsm.huellas_escritorio.Modelo.Adoptante;
import org.utl.dsm.huellas_escritorio.Modelo.Sesion;

public class loginAdoptante implements Initializable {
    @FXML
    private Button btnLogin;

    @FXML
    private Button btnVolver;

    @FXML
    private TextField contra;

    @FXML
    private Label labelContra;

    @FXML
    private Label labelCorreo;
    @FXML
    private TextField correo;

    @FXML
    private Button registro;

    @FXML
    private Label welcomeText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cambioModulo c = new cambioModulo();
        configurarFloatingLabel(correo, labelCorreo);
        configurarFloatingLabel(contra, labelContra);
        btnLogin.setOnAction(event -> logIn());
        btnVolver.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Clientes/inicio.fxml", "Iniciar Sesión", btnVolver));
        registro.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Clientes/Registro/registro.fxml", "Registro", registro));
    }
    private void configurarFloatingLabel(TextField campo, Label etiqueta) {
        campo.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal || !campo.getText().isEmpty()) {
                if (!etiqueta.getStyleClass().contains("activa")) {
                    etiqueta.getStyleClass().add("activa");
                }
            } else {
                etiqueta.getStyleClass().remove("activa");
            }
        });

        if (!campo.getText().isEmpty()) {
            etiqueta.getStyleClass().add("activa");
        }
    }

    private void logIn(){
        if(correo.getText().isEmpty() || contra.getText().isEmpty()) {
            mostrarAlerta("Por favor, completa todos los campos.");
            return;
        }
        String json = "{ \"correo\": \"" + correo.getText()+ "\", \"contraseña\": \"" + contra.getText() + "\" }";
        HttpResponse<JsonNode> response = Unirest.post("http://localhost:8080/ProyectoHuellas/api/adoptante/consultaAd")
                .header("Content-Type", "application/json")
                .body(json)
                .asJson();

        if(response.getStatus() == 200){
            Adoptante adoptante = new Gson().fromJson(response.getBody().toString(), Adoptante.class);
            Sesion.setAdoptanteActual(adoptante);
            cambioModulo c = new cambioModulo();
         c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Clientes/inicio.fxml", "Inicio", btnLogin);
        }else if(response.getStatus() == 404 ){
            mostrarAlerta("No existe tu cuenta");

        }
    }

    private void mostrarAlerta(String Mensaje) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/org/utl/dsm/huellas_escritorio/Alertas/alertaError.fxml"));
            Parent alerta = loader.load();
            alertaError controller = loader.getController();
            controller.setMensaje(Mensaje);
            Stage stage = new Stage();
            stage.setScene(new Scene(alerta));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            Stage primaryStage = (Stage) btnLogin.getScene().getWindow();
            stage.setX(primaryStage.getX() + primaryStage.getWidth() - alerta.getLayoutBounds().getWidth() - 390);
            stage.setY(primaryStage.getY() + 40);


            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
