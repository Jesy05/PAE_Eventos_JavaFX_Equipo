package ni.edu.uam.pae_eventos_javafx_equipo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.util.Optional;

public class ArtesaniasController {

    @FXML private TableView<Artesania> tablaArtesanias;
    @FXML private TextField txtBuscar;
    @FXML private Label lblEstado;

    private final ObservableList<Artesania> artesanias = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        artesanias.addAll(
                new Artesania("A-001", "Hamaca de Masaya", "Textil", 950.0),
                new Artesania("A-002", "Ceramica de San Juan de Oriente", "Ceramica", 480.0),
                new Artesania("A-003", "Mascara de madera de Monimbo", "Madera", 1200.0),
                new Artesania("A-004", "Sombrero de pita de Camoapa", "Fibra", 320.0)
        );
        tablaArtesanias.setItems(artesanias);
    }

    // Menu Catalogo / ToolBar: Nuevo  | Evento: ActionEvent
    @FXML
    private void nuevoProducto(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nuevo producto");
        dialog.setHeaderText("Registrar artesania");
        dialog.setContentText("Nombre;Categoria;Precio  (ej: Jarron;Ceramica;540)");

        Optional<String> entrada = dialog.showAndWait();
        if (entrada.isEmpty()) {
            return;
        }
        String[] p = entrada.get().split(";");
        if (p.length != 3 || p[0].trim().isEmpty()) {
            lblEstado.setText("Formato invalido. Use Nombre;Categoria;Precio");
            return;
        }
        try {
            double precio = Double.parseDouble(p[2].trim());
            String codigo = String.format("A-%03d", artesanias.size() + 1);
            artesanias.add(new Artesania(codigo, p[0].trim(), p[1].trim(), precio));
            lblEstado.setText("Producto agregado: " + p[0].trim());
        } catch (NumberFormatException e) {
            lblEstado.setText("El precio debe ser numerico.");
        }
    }

    // Menu Catalogo / ToolBar: Guardar | Evento: ActionEvent
    @FXML
    private void guardarCatalogo(ActionEvent event) {
        lblEstado.setText("Catalogo guardado (" + artesanias.size() + " productos).");
    }

    // Menu Ventas: Registrar venta | Evento: ActionEvent
    @FXML
    private void registrarVenta(ActionEvent event) {
        Artesania sel = tablaArtesanias.getSelectionModel().getSelectedItem();
        if (sel == null) {
            lblEstado.setText("Seleccione un producto para registrar la venta.");
            return;
        }
        lblEstado.setText(String.format("Venta registrada: %s por C$ %.2f",
                sel.getNombre(), sel.getPrecio()));
    }

    // Menu Ayuda: Acerca de | Evento: ActionEvent
    @FXML
    private void acercaDe(ActionEvent event) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION,
                "PAE - Eventos y navegacion en JavaFX\n\n"
                        + "Integrantes:\n"
                        + "- Jose Cristo Carvallo Herrera\n"
                        + "- Jesy Nicole Gonzalez Jarquin\n\n"
                        + "Tienda de artesanias nicaraguenses.",
                ButtonType.OK);
        alerta.setHeaderText(null);
        alerta.setTitle("Acerca de");
        alerta.showAndWait();
    }

    // ToolBar boton Buscar | Evento: ActionEvent
    @FXML
    private void buscarPorBoton(ActionEvent event) {
        buscar();
    }

    // ToolBar campo de texto | Evento: KeyEvent (ENTER)
    @FXML
    private void buscarProducto(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            buscar();
        }
    }

    private void buscar() {
        String criterio = txtBuscar.getText().trim().toLowerCase();
        if (criterio.isEmpty()) {
            lblEstado.setText("Escriba un nombre para buscar.");
            return;
        }
        for (Artesania a : artesanias) {
            if (a.getNombre().toLowerCase().contains(criterio)) {
                tablaArtesanias.getSelectionModel().select(a);
                tablaArtesanias.scrollTo(a);
                lblEstado.setText("Encontrado: " + a.getNombre());
                return;
            }
        }
        tablaArtesanias.getSelectionModel().clearSelection();
        lblEstado.setText("Sin coincidencias para: " + criterio);
    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
        HelloApplication.setRoot("main-view");
    }
}