package org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionMascotas;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.utl.dsm.huellas_escritorio.Modelo.Animales;
import org.utl.dsm.huellas_escritorio.Modelo.Centros;
import javafx.event.ActionEvent;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;

public class infoAnimal implements Initializable {
private int id;
    @FXML
    private Button BtnCerrar;

    @FXML
    private Text centroNombre;

    @FXML
    private Text codigo;

    @FXML
    private Rectangle colorEstatus;

    @FXML
    private Text descripcion;

    @FXML
    private Text edad;

    @FXML
    private Text especie;

    @FXML
    private Text estatus;

    @FXML
    private ImageView fotoAnimal;

    @FXML
    private Text genero;

    @FXML
    private Text peso;

    @FXML
    private Text raza;
    @FXML
    private Text nombreAnimal;
    @FXML
    private Pane estatusColor;
    @FXML
    private Text tamano;
    private gestionMascotas controller;
    public gestionMascotas getController() {
        return controller;
    }

    public void setController(gestionMascotas controller) {
        this.controller = controller;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        BtnCerrar.setOnAction(event -> cerrarVentana());
    }

    public void cargarInfo(Animales a, Centros c){
        this.id = a.getIdAnimal();
        nombreAnimal.setText(a.getNombreAnimal());
        codigo.setText(a.getCodigoAnimal());
        genero.setText(a.getGenero());
        raza.setText(a.getRaza());
        especie.setText(a.getEspecie());
        edad.setText(a.getEdad());
        tamano.setText(a.getTamano());
        peso.setText( String.format("%.1f", a.getPeso()) + " kg");
        centroNombre.setText(c.getNombreCentro());
        descripcion.setText(a.getDescripcion());
        String base64 = a.getFoto().split(",")[1];
        byte[] imgBytes = Base64.getDecoder().decode(base64);
        Image img = new Image(new ByteArrayInputStream(imgBytes));

        fotoAnimal.setImage(img);
        fotoAnimal.setPreserveRatio(false);
        fotoAnimal.setFitWidth(214);
        fotoAnimal.setFitHeight(215);
        Rectangle clip = new Rectangle(214, 215);
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        fotoAnimal.setClip(clip);

        if(a.getEstatus() == 1){
            estatusColor.setStyle("-fx-background-color: #198754;  -fx-border-radius: 8; -fx-background-radius: 8;");
        }else{
            estatusColor.setStyle("-fx-background-color: #ffc107;  -fx-border-radius: 8; -fx-background-radius: 8;");
        }
        estatus.setText(a.getEstatusTexto());

    }
    private int obtenerCentros(String nombreCentro) throws Exception {
        HttpResponse<String> response = Unirest.get("http://localhost:8080/ProyectoHuellas/api/centros/getAll").asString();

        if (response.getStatus() == 200) {
            Gson gson = new Gson();
            Centros[] centros = gson.fromJson(response.getBody(), Centros[].class);

            for (Centros centro : centros) {
                if (centro.getNombreCentro().equals(nombreCentro)) {
                    return centro.getIdCentro();
                }
            }
            throw new Exception("Centro no encontrado");
        }
        throw new Exception("Error al obtener centros");
    }
    private void cerrarVentana() {
        Stage stage = (Stage) BtnCerrar.getScene().getWindow();
        stage.close();
    }
}
