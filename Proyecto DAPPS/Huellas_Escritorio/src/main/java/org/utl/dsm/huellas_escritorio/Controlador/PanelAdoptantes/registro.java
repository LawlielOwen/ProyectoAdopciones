package org.utl.dsm.huellas_escritorio.Controlador.PanelAdoptantes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;
import javafx.stage.StageStyle;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.utl.dsm.huellas_escritorio.Controlador.Alertas.alertaError;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class registro implements Initializable {
    @FXML
    private Button btnRegistrar;

    @FXML
    private Button btnVolver;

    @FXML
    private TextField contra;

    @FXML
    private TextField txtApm;

    @FXML
    private TextField txtApp;

    @FXML
    private TextField txtCorreo;

    @FXML
    private DatePicker txtFechaNacimiento;

    @FXML
    private ComboBox<String> genero;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cambioModulo c = new cambioModulo();
        btnVolver.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Clientes/inicio.fxml", "Registro", btnVolver));
        btnRegistrar.setOnAction(event -> registro());
        ObservableList<String> sexo = FXCollections.observableArrayList(
                "Masculino", "Femenino","Otro"
        );
        genero.setItems(sexo);
    }

    private void registro(){
        if (!validacion()) {
            return;
        }
        String fecha = "";
        fecha = txtFechaNacimiento.getValue().toString();
        String json = "{"
                + "\"nombre\":\"" + txtNombre.getText() + "\","
                + "\"app\":\"" + txtApp.getText() + "\","
                + "\"apm\":\"" + txtApm.getText() + "\","
                + "\"fechaNacimiento\":" + fecha + ","
                + "\"correo\":\"" + txtCorreo.getText() + "\","
                + "\"contraseña\":\"" + contra.getText() + "\","
                + "\"telefono\":\"" + txtTelefono.getText() + "\","
                + "\"genero\":" + genero.getValue()
                + "}";
        HttpResponse<JsonNode> response = Unirest.post("http://localhost:8080/ProyectoHuellas/api/adoptante/registroAd")
                .header("Content-Type", "application/json")
                .body(json)
                .asJson();
        if(response.getStatus() == 200){

            cambioModulo c = new cambioModulo();
            c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Clientes/Login/login.fxml", "Iniciar Sesion", btnRegistrar);
        }
    }
    private boolean validacion() {
        if(txtNombre.getText().isEmpty() || txtApp.getText().isEmpty() ||
                txtApm.getText().isEmpty() || txtCorreo.getText().isEmpty() ||
                txtTelefono.getText().isEmpty() || contra.getText().isEmpty() ||
                genero.getValue() == null || txtFechaNacimiento.getValue() == null) {

            mostrarAlerta("Por favor, completa todos los campos.");
            return false;
        } else if(contra.getText().length() < 6){
            mostrarAlerta("La contraseña debe de contener al menos 6 caracteres");
            return false;
        }

        return true;
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
            Stage primaryStage = (Stage) btnRegistrar.getScene().getWindow();
            stage.setX(primaryStage.getX() + primaryStage.getWidth() - alerta.getLayoutBounds().getWidth() - 390);
            stage.setY(primaryStage.getY() + 40);


            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }

