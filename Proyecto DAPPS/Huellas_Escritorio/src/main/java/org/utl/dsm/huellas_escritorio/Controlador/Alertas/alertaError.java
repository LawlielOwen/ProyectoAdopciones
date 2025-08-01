package org.utl.dsm.huellas_escritorio.Controlador.Alertas;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
public class alertaError  implements Initializable {
    @FXML
    private Button cerrar;

    @FXML
    private Text textoModificacion;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        cerrar.setOnAction(event -> {
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
    public void setMensaje(String mensaje) {
        textoModificacion.setText(mensaje);
    }
}
