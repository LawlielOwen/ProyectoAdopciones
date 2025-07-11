module org.utl.dsm.huellas_escritorio {
    requires javafx.controls;
    requires javafx.fxml;
    requires unirest.java;
    requires com.google.gson;
    requires java.desktop;
    opens org.utl.dsm.huellas_escritorio.Modelo to com.google.gson;
    opens org.utl.dsm.huellas_escritorio.Controlador to javafx.fxml;
    exports org.utl.dsm.huellas_escritorio;
}
