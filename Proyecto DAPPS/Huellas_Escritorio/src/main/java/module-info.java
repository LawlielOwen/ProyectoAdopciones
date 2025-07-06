module org.utl.dsm.huellas_escritorio {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.utl.dsm.huellas_escritorio.Controlador to javafx.fxml;
    exports org.utl.dsm.huellas_escritorio;
}
