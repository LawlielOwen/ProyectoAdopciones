package org.utl.dsm.huellas_escritorio.Controlador.PanelAdoptantes;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class afiliacion implements Initializable {

    @FXML private Button btnAdopta;
    @FXML private Button btnDonacion;
    @FXML private Button btnAfiliacion;
    @FXML private Button btnQuien;
    @FXML private Button btnEmpleado;
    @FXML private Button btnLogin;

    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtHorario;
    @FXML private ComboBox<String> txtMascota;
    @FXML private ComboBox<String> txtFrecuencia;
    @FXML private ComboBox<String> txtApoyo;
    @FXML private ComboBox<String> txtContacto;
    @FXML private RadioButton btnSi;
    @FXML private RadioButton btnNo;
    @FXML private ToggleGroup contacto;
    @FXML private TextField txtMensaje;
    @FXML private Button btnUnirme;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cambioModulo c = new cambioModulo();
        btnAfiliacion.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Clientes/Afiliacion.fxml", "Afiliacion", btnAfiliacion));
        btnEmpleado.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Empleados/loginEmpleado.fxml", "Empleados", btnAfiliacion));
        btnLogin.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Clientes/login.fxml", "Iniciar Sesion", btnAfiliacion));
        btnAdopta.setOnAction(event -> c.cambiarPantalla("/org/utl/dsm/huellas_escritorio/Clientes/inicio.fxml", "Iniciar Sesion", btnAfiliacion));


        txtMascota.getItems().addAll("Sí", "No", "Otros animales");
        txtFrecuencia.getItems().addAll("Una vez", "Semanal", "Mensual");
        txtApoyo.getItems().addAll("Voluntariado", "Rescate", "Donaciones");
        txtContacto.getItems().addAll("Teléfono", "WhatsApp", "Correo");


        txtMascota.getSelectionModel().selectFirst();
        txtFrecuencia.getSelectionModel().selectFirst();
        txtApoyo.getSelectionModel().selectFirst();
        txtContacto.getSelectionModel().selectFirst();


        btnUnirme.setOnAction(event -> agregarAfiliado());
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

    // AGREGAR AFILIADO
    private void agregarAfiliado() {
        String nombre = txtNombre.getText();
        String correo = txtCorreo.getText();
        String telefono = txtTelefono.getText();
        String mascotasAfiliado = txtMascota.getValue();
        String tipoAfiliado = txtApoyo.getValue();
        String frecuenciaAfiliado = txtFrecuencia.getValue();
        String disponibilidadAfiliado = txtHorario.getText();
        String deseoContactoAfiliado = btnSi.isSelected() ? "Sí" : "No";
        String medioContactoAfiliado = txtContacto.getValue();
        String comentariosAfiliado = txtMensaje.getText();
        int idCentro = 1;

        String json = String.format(
                "{" +
                        "\"nombre\":\"%s\"," +
                        "\"correo\":\"%s\"," +
                        "\"telefono\":\"%s\"," +
                        "\"mascotasAfiliado\":\"%s\"," +
                        "\"tipoAfiliado\":\"%s\"," +
                        "\"frecuenciaAfiliado\":\"%s\"," +
                        "\"disponibilidadAfiliado\":\"%s\"," +
                        "\"deseoContactoAfiliado\":\"%s\"," +
                        "\"medioContactoAfiliado\":\"%s\"," +
                        "\"comentariosAfiliado\":\"%s\"," +
                        "\"idCentro\":%d" +
                        "}",
                nombre, correo, telefono, mascotasAfiliado, tipoAfiliado, frecuenciaAfiliado,
                disponibilidadAfiliado, deseoContactoAfiliado, medioContactoAfiliado, comentariosAfiliado, idCentro
        );

        new Thread(() -> {
            try {
                String url = "http://localhost:8080/ProyectoHuellas/api/Afiliacion/agregar";
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                con.setDoOutput(true);

                String parametros = "datos=" + URLEncoder.encode(json, StandardCharsets.UTF_8);

                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = parametros.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                int code = con.getResponseCode();
                if (code == 200) {
                    Platform.runLater(() -> {
                        mostrarAlerta("¡Afiliado registrado!", Alert.AlertType.INFORMATION);
                        limpiarCampos();
                    });
                } else {
                    Platform.runLater(() -> mostrarAlerta("Error al registrar: " + code, Alert.AlertType.ERROR));
                }
            } catch (Exception ex) {
                Platform.runLater(() -> mostrarAlerta("Error de conexión:\n" + ex.getMessage(), Alert.AlertType.ERROR));
            }
        }).start();
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtHorario.clear();
        txtMascota.getSelectionModel().selectFirst();
        txtFrecuencia.getSelectionModel().selectFirst();
        txtApoyo.getSelectionModel().selectFirst();
        txtContacto.getSelectionModel().selectFirst();
        btnSi.setSelected(false);
        btnNo.setSelected(false);
        txtMensaje.clear();
    }
}
