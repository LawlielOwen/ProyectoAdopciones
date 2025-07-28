module org.utl.dsm.huellas_escritorio {
    requires javafx.controls;
    requires javafx.fxml;
    requires unirest.java;
    requires com.google.gson;
    requires java.desktop;

    opens org.utl.dsm.huellas_escritorio.Modelo to com.google.gson;


    opens org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionMascotas to javafx.fxml;
    opens org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionAfiliado to javafx.fxml;
    opens org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionCentros to javafx.fxml;
    opens org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionDonacion to javafx.fxml;
    opens org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionEmpleado to javafx.fxml;
    opens org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionAdoptante to javafx.fxml;
    opens org.utl.dsm.huellas_escritorio.Controlador.Gestiones.gestionSolicitud to javafx.fxml;
    opens org.utl.dsm.huellas_escritorio.Controlador.PanelAdoptantes to javafx.fxml;

    exports org.utl.dsm.huellas_escritorio;
}
