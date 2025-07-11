package org.utl.dsm.huellas_escritorio.Controlador;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class barra {
  /*  @FXML
    private Label labelAnimales;
    @FXML private Label labelEmpleados;
    @FXML private Label labelSolicitudes;
    @FXML private Label labelCentros;
    @FXML private Label labelAdoptantes;
    @FXML private Label labelDonaciones;
    @FXML private Label labelAfiliaciones;
    @FXML
    private ImageView iconoBoton;
    @FXML
    private Button mini;
    private boolean minimizada = true;
    private final double ANCHO_MIN = 70;
    private final double ANCHO_MAX = 252;
    private final double ANCHO_BOTON = 28;
    private final double BOTON_MIN_X = ANCHO_MIN - 6;
    private final double BOTON_MAX_X = ANCHO_MAX - 14;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //  barra.setPrefWidth(ANCHO_MIN);

        double botonX = ANCHO_MIN - (ANCHO_BOTON / 2);
        mini.setLayoutX(botonX);
        mini.setLayoutY(20);

        setTextoVisible(false);
        girarChevron();

        mini.setOnAction(e -> toggleBarra());
    }

    private void toggleBarra() {
        double nuevoAncho = minimizada ? ANCHO_MAX : ANCHO_MIN;


        double nuevaXBoton = minimizada
                ? ANCHO_MAX - (ANCHO_BOTON / 2)
                : ANCHO_MIN - (ANCHO_BOTON / 2);

        Timeline animacionBarra = new Timeline(
                new KeyFrame(Duration.millis(300),
                        new KeyValue(barra.prefWidthProperty(), nuevoAncho))
        );

        Timeline animacionBoton = new Timeline(
                new KeyFrame(Duration.millis(300),
                        new KeyValue(mini.layoutXProperty(), nuevaXBoton))
        );

        animacionBarra.play();
        animacionBoton.play();

        setTextoVisible(minimizada);

        minimizada = !minimizada;
        girarChevron();
    }
    private void girarChevron() {
        iconoBoton.setRotate(minimizada ? 180 : 0);
    }
    private void setTextoVisible(boolean visible) {
        labelAnimales.setVisible(visible);
        labelEmpleados.setVisible(visible);
        labelSolicitudes.setVisible(visible);
        labelCentros.setVisible(visible);
        labelAdoptantes.setVisible(visible);
        labelDonaciones.setVisible(visible);
        labelAfiliaciones.setVisible(visible);
    }
}
*/
}
