module ni.edu.uam.pae_eventos_javafx_equipo {
    requires javafx.controls;
    requires javafx.fxml;


    opens ni.edu.uam.pae_eventos_javafx_equipo to javafx.fxml;
    exports ni.edu.uam.pae_eventos_javafx_equipo;
}