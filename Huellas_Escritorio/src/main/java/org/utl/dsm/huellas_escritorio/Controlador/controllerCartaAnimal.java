package org.utl.dsm.huellas_escritorio.Controlador;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.utl.dsm.huellas_escritorio.Modelo.Animales;
import org.utl.dsm.huellas_escritorio.Modelo.Centros;
import javafx.event.ActionEvent;

import java.io.ByteArrayInputStream;
import java.util.Base64;
public class controllerCartaAnimal {
    @FXML
    private Text centro;

    @FXML
    private Button adoptar;
    @FXML
    private VBox contenedorInfo;
    @FXML
    private Button cerrar;

    @FXML
    private Button cerrarTacha;

    @FXML
    private Text descripcion;
    @FXML
    private Label edad;

    @FXML
    private ImageView foto;

    @FXML
    private Label genero;

    @FXML
    private Label nombreAnimal;

    @FXML
    private Label peso;

    @FXML
    private Text raza;

    @FXML
    private Label tamano;

    public void cargarInfo(Animales a, Centros c){
      nombreAnimal.setText(a.getNombreAnimal());
        peso.setText( String.format("%.1f", a.getPeso()) + " kg");
        raza.setText(a.getRaza());
        tamano.setText(a.getTamano());
        genero.setText(a.getGenero());
        edad.setText(a.getEdad() + " años");
        centro.setText(c.getNombreCentro());
        descripcion.setText(a.getDescripcion());

        String base64 = a.getFoto().split(",")[1];
        byte[] imgBytes = Base64.getDecoder().decode(base64);
        Image img = new Image(new ByteArrayInputStream(imgBytes));

        foto.setImage(img);
        foto.setPreserveRatio(false);
        foto.setFitWidth(230);
        foto.setFitHeight(270);
        Rectangle clip = new Rectangle(230, 270);
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        foto.setClip(clip);
    }
    @FXML
    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
