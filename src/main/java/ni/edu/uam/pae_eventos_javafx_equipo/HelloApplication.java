package ni.edu.uam.pae_eventos_javafx_equipo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(cargarVista("main-view"), 900, 600);
        stage.setTitle("PAE - Eventos y navegacion en JavaFX (Equipo)");
        stage.setScene(scene);
        stage.show();
    }

    /** Cambia el contenido de la ventana sin abrir una ventana nueva. */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(cargarVista(fxml));
    }

    private static Parent cargarVista(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxml + ".fxml"));
        return loader.load();
    }

    static void salir() {
        Platform.exit();
    }

    public static void main(String[] args) {
        launch();
    }
}