package org.utl.dsm.huellas_escritorio.Controlador.PanelAdoptantes;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.utl.dsm.huellas_escritorio.Controlador.Alertas.alertaAviso;
import org.utl.dsm.huellas_escritorio.Controlador.Alertas.alertaError;
import org.utl.dsm.huellas_escritorio.Modelo.Animales;
import org.utl.dsm.huellas_escritorio.Modelo.Centros;
import javafx.event.ActionEvent;
import org.utl.dsm.huellas_escritorio.Modelo.Sesion;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;
import  org.utl.dsm.huellas_escritorio.Controlador.PanelAdoptantes.inicioAdoptante;
public class controllerCartaAnimal implements Initializable {
    @FXML
    private Text centro;

    @FXML
    private Button adoptar;

    @FXML
    private Button cerrar;


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
    private Stage ventanaPrincipal;
    private Stage ventanaInformacion;

    public void setVentanaInformacion(Stage ventanaInformacion) {
        this.ventanaInformacion = ventanaInformacion;
    }

    public void setVentanaPrincipal(Stage ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
    }
    private Animales a;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cerrar.setOnAction(event -> cerrarVentana());
        adoptar.setOnAction(event -> {
            if(Sesion.getAdoptanteActual() == null){
            mostrarAlerta();
            }else{
                Sesion.setAnimalActual(this.a);
                 mandarSoli();
            }
        });
    }
    public void cargarInfo(Animales a, Centros c){
        this.a = a;
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
        foto.setFitWidth(250);
        foto.setFitHeight(255);
        Rectangle clip = new Rectangle(250, 255);
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        foto.setClip(clip);
    }
    private void cerrarVentana() {
        Stage stage = (Stage) cerrar.getScene().getWindow();
        stage.close();
    }
    private void mostrarAlerta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/utl/dsm/huellas_escritorio/Alertas/alertaAviso.fxml"));
            Parent alerta = loader.load();
            alertaAviso controller = loader.getController();

            Stage miVentana = (Stage) adoptar.getScene().getWindow();
            controller.setVentanaPrincipal(this.ventanaPrincipal);
            controller.setVentanaInformacion((Stage) adoptar.getScene().getWindow());


            Stage stage = new Stage();
            stage.setScene(new Scene(alerta));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(miVentana);
            Stage primaryStage = ventanaPrincipal;
            double anchoPrincipal = primaryStage.getWidth();
            double xPrincipal = primaryStage.getX();
            double yPrincipal = primaryStage.getY();


            stage.show();
            double anchoAlerta = stage.getWidth();
            double altoAlerta = stage.getHeight();
            stage.hide();


            double nuevaX = xPrincipal + anchoPrincipal - anchoAlerta - 20;
            double nuevaY = yPrincipal + 20;

            stage.setX(nuevaX);
            stage.setY(nuevaY);


            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mandarSoli() {
        try {

            Stage stageActual = (Stage) adoptar.getScene().getWindow();
            stageActual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/utl/dsm/huellas_escritorio/Clientes/Solicitud.fxml"));
            Parent root = loader.load();
           if(ventanaPrincipal != null){
               ventanaPrincipal.close();
           }
            Stage nuevaVentana = new Stage();
            nuevaVentana.setScene(new Scene(root));

            nuevaVentana.setMaximized(true);
            nuevaVentana.show();

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error al cargar la ventana").show();
        }
    }

}

