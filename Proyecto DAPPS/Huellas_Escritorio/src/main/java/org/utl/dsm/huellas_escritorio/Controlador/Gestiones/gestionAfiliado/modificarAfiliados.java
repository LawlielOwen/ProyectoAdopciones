package org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionAfiliado;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.utl.dsm.huellas_escritorio.Modelo.Afiliados;

import java.net.URL;
import java.util.ResourceBundle;

public class modificarAfiliados implements Initializable {
    private gestionAfiliados controller;

    @FXML private TextField nombre;
    @FXML private TextField correo;
    @FXML private TextField telefono;
    @FXML private Button guardar;
    @FXML private Button cancelar;

    @FXML private ComboBox<String> tieneMascotas;
    @FXML private ComboBox<String> tipoAfiliado;
    @FXML private ComboBox<String> frecuenciaAyuda;
    @FXML private ComboBox<String> medioContacto;
    @FXML private ComboBox<String> disponibilidad;

    private int idAfiliado;
    private int idPersona;

    public gestionAfiliados getController() { return controller; }
    public void setController(gestionAfiliados controller) { this.controller = controller; }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarCombos();
        cancelar.setOnAction(event -> cerrarVentana());
        guardar.setOnAction(event -> {
            try { modificar(); }
            catch (Exception e) { throw new RuntimeException(e); }
        });
    }

    private void cargarCombos() {
        tieneMascotas.setItems(FXCollections.observableArrayList("Sí", "No", "Otros animales"));
        tipoAfiliado.setItems(FXCollections.observableArrayList("Voluntariado", "Rescate", "Donaciones"));
        frecuenciaAyuda.setItems(FXCollections.observableArrayList("Una vez", "Semanal", "Mensual"));
        disponibilidad.setItems(FXCollections.observableArrayList(
                "Entre semana", "Fines de semana", "Mañana", "Tarde", "Noche", "Flexible"
        ));
        medioContacto.setItems(FXCollections.observableArrayList("Teléfono", "WhatsApp", "Correo"));
    }

    public void cargarAfiliado(Afiliados a) {
        this.idAfiliado = a.getIdAfiliado();
        this.idPersona = a.getIdPersona();
        nombre.setText(a.getNombre());
        correo.setText(a.getCorreo());
        telefono.setText(a.getTelefono());
        tieneMascotas.setValue(a.getMascotasAfiliado());
        tipoAfiliado.setValue(a.getTipoAfiliado());
        frecuenciaAyuda.setValue(a.getFrecuenciaAfiliado());
        disponibilidad.setValue(a.getDisponibilidadAfiliado());
        medioContacto.setValue(a.getMedioContactoAfiliado());
    }

    private void cerrarVentana() {
        Stage stage = (Stage) cancelar.getScene().getWindow();
        stage.close();
    }

    private void modificar() {

        if (nombre.getText().trim().isEmpty()) {
            mostrarAlerta("El nombre es obligatorio.");
            return;
        }
        if (correo.getText().trim().isEmpty()) {
            mostrarAlerta("El correo es obligatorio.");
            return;
        }
        if (telefono.getText().trim().isEmpty()) {
            mostrarAlerta("El teléfono es obligatorio.");
            return;
        }


        String json = "{"
                + "\"idAfiliado\":" + idAfiliado + ","
                + "\"idPersona\":" + idPersona + ","
                + "\"nombre\":\"" + nombre.getText().trim() + "\","
                + "\"correo\":\"" + correo.getText().trim() + "\","
                + "\"telefono\":\"" + telefono.getText().trim() + "\","
                + "\"mascotasAfiliado\":\"" + (tieneMascotas.getValue() == null ? "" : tieneMascotas.getValue()) + "\","
                + "\"tipoAfiliado\":\"" + (tipoAfiliado.getValue() == null ? "" : tipoAfiliado.getValue()) + "\","
                + "\"frecuenciaAfiliado\":\"" + (frecuenciaAyuda.getValue() == null ? "" : frecuenciaAyuda.getValue()) + "\","
                + "\"disponibilidadAfiliado\":\"" + (disponibilidad.getValue() == null ? "" : disponibilidad.getValue()) + "\","
                + "\"medioContactoAfiliado\":\"" + (medioContacto.getValue() == null ? "" : medioContacto.getValue()) + "\""
                + "}";


        HttpResponse<String> response = Unirest.post("http://localhost:8080/ProyectoHuellas/api/Afiliacion/modificar")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body("datos=" + json)
                .asString();


        if (response.getStatus() == 200) {
            controller.listAfiliados.clear();
            controller.cargarAfiliados();
            clear();
            cerrarVentana();
        } else {
            mostrarAlerta("Ocurrió un error al modificar el afiliado. Código: " + response.getStatus());
        }
    }

    private void clear() {
        nombre.clear();
        correo.clear();
        telefono.clear();
        tieneMascotas.setValue(null);
        tipoAfiliado.setValue(null);
        frecuenciaAyuda.setValue(null);
        disponibilidad.setValue(null);
        medioContacto.setValue(null);
        nombre.requestFocus();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
