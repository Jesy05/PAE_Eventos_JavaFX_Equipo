package ni.edu.uam.pae_eventos_javafx_equipo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class MainController {

    // Fuente: Button | Evento: ActionEvent | Manejador: abre la vista del reto
    @FXML
    private void abrirInventario(ActionEvent event) throws IOException {
        HelloApplication.setRoot("inventario-view");
    }

    @FXML
    private void abrirCafe(ActionEvent event) throws IOException {
        HelloApplication.setRoot("cafe-view");
    }

    @FXML
    private void abrirArtesanias(ActionEvent event) throws IOException {
        HelloApplication.setRoot("artesanias-view");
    }

    @FXML
    private void salir(ActionEvent event) {
        HelloApplication.salir();
    }
}