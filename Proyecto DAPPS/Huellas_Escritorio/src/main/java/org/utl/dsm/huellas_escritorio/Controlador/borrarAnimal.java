package org.utl.dsm.huellas_escritorio.Controlador;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.utl.dsm.huellas_escritorio.Modelo.Animales;

import java.net.URL;
import java.util.ResourceBundle;

public class borrarAnimal implements Initializable {
    private gestionMascotas controller;
    private Animales animalAEliminar;
    @FXML
    private Button borrar;

    @FXML
    private Button cancelar;

    @FXML
    private Button cerrar;

    public void setController(gestionMascotas controller) {
        this.controller = controller;
    }

    public void setAnimalAEliminar(Animales animal) {
        this.animalAEliminar = animal;
    }

    @FXML
    private void eliminarAnimal() {
        if (animalAEliminar != null) {
            try {
                String json = new Gson().toJson(animalAEliminar);
                HttpResponse<String> response = Unirest
                        .delete("http://localhost:8080/ProyectoHuellas/api/mascotas/deleteAnimal")
                        .header("Content-Type", "application/json")
                        .body(json)
                        .asString();

                if (response.getStatus() == 200) {
                    controller.listAnimales.clear();
                    controller.cargarAnimales();
                    controller.cargarContador();
                    Stage stage = (Stage) borrar.getScene().getWindow();
                    stage.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        borrar.setOnAction(event -> {
            try {
                eliminarAnimal();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        cerrar.setOnAction(event -> {
            try {
                cerrarVentana();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        cancelar.setOnAction(event -> {
            try {
                cerrarVentana();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
    private void cerrarVentana() {
        Stage stage = (Stage) cerrar.getScene().getWindow();
        stage.close();
    }
}
