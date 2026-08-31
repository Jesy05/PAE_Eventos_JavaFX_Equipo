package ni.edu.uam.pae_eventos_javafx_equipo;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.Optional;

public class ArtesaniasController {

    @FXML private TableView<Artesania> tablaArtesanias;
    @FXML private TableColumn<Artesania, String> colImagen;
    @FXML private TableColumn<Artesania, String> colCodigo;
    @FXML private TableColumn<Artesania, String> colNombre;
    @FXML private TableColumn<Artesania, String> colCategoria;
    @FXML private TableColumn<Artesania, String> colPrecio;
    @FXML private TextField txtBuscar;
    @FXML private Label lblEstado;

    private final ObservableList<Artesania> artesanias = FXCollections.observableArrayList();
    private int contador = 0;

    @FXML
    private void initialize() {
        colCodigo.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getCodigo()));
        colNombre.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getNombre()));
        colCategoria.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getCategoria()));
        colPrecio.setCellValueFactory(c ->
                new ReadOnlyStringWrapper(String.format("%.2f", c.getValue().getPrecio())));

        // Columna de imagen: miniatura generada a partir del nombre del producto
        colImagen.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getNombre()));
        colImagen.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String nombre, boolean empty) {
                super.updateItem(nombre, empty);
                setText(null);
                setGraphic((empty || nombre == null) ? null : miniatura(nombre));
            }
        });

        tablaArtesanias.setItems(artesanias);

        agregar("Hamaca de Masaya", "Textil", 950.0);
        agregar("Ceramica de San Juan de Oriente", "Ceramica", 480.0);
        agregar("Mascara de madera de Monimbo", "Madera", 1200.0);
        agregar("Sombrero de pita de Camoapa", "Fibra", 320.0);
    }

    private Artesania agregar(String nombre, String categoria, double precio) {
        contador++;
        Artesania a = new Artesania(String.format("A-%03d", contador), nombre, categoria, precio);
        artesanias.add(a);
        return a;
    }

    /** Genera una imagen en miniatura a partir del nombre del producto. */
    private ImageView miniatura(String texto) {
        double lado = 44;
        Canvas canvas = new Canvas(lado, lado);
        GraphicsContext g = canvas.getGraphicsContext2D();

        int hash = Math.abs(texto.hashCode());
        g.setFill(Color.hsb(hash % 360, 0.45, 0.9));
        g.fillRect(0, 0, lado, lado);
        g.setStroke(Color.web("#666666"));
        g.strokeRect(0.5, 0.5, lado - 1, lado - 1);

        g.setFill(Color.web("#333333"));
        g.setFont(Font.font(18));
        g.fillText(texto.substring(0, 1).toUpperCase(), 15, 29);

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        WritableImage imagen = canvas.snapshot(params, null);
        return new ImageView(imagen);
    }

    // Menu Catalogo / ToolBar: Nuevo | Evento: ActionEvent
    @FXML
    private void nuevoProducto(ActionEvent event) {
        Dialog<Artesania> dialog = new Dialog<>();
        dialog.setTitle("Nuevo producto");
        dialog.setHeaderText("Registrar artesania en el catalogo");

        ButtonType btnAgregar = new ButtonType("Agregar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnAgregar, ButtonType.CANCEL);

        TextField tfNombre = new TextField();
        tfNombre.setPromptText("Nombre");
        TextField tfCategoria = new TextField();
        tfCategoria.setPromptText("Categoria");
        TextField tfPrecio = new TextField();
        tfPrecio.setPromptText("Precio en cordobas");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.addRow(0, new Label("Nombre:"), tfNombre);
        grid.addRow(1, new Label("Categoria:"), tfCategoria);
        grid.addRow(2, new Label("Precio:"), tfPrecio);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(boton -> {
            if (boton != btnAgregar) {
                return null;
            }
            String nombre = tfNombre.getText().trim();
            String categoria = tfCategoria.getText().trim();
            if (nombre.isEmpty() || categoria.isEmpty()) {
                return null;
            }
            try {
                double precio = Double.parseDouble(tfPrecio.getText().trim());
                if (precio < 0) {
                    return null;
                }
                return agregar(nombre, categoria, precio);
            } catch (NumberFormatException e) {
                return null;
            }
        });

        Optional<Artesania> resultado = dialog.showAndWait();
        if (resultado.isPresent()) {
            Artesania a = resultado.get();
            tablaArtesanias.getSelectionModel().select(a);
            tablaArtesanias.scrollTo(a);
            lblEstado.setText("Producto agregado: " + a.getNombre()
                    + "  (" + artesanias.size() + " en el catalogo)");
        } else {
            lblEstado.setText("No se agrego: complete nombre, categoria y un precio numerico.");
        }
    }

    // Menu Catalogo / ToolBar: Guardar | Evento: ActionEvent
    @FXML
    private void guardarCatalogo(ActionEvent event) {
        StringBuilder sb = new StringBuilder("Catalogo guardado (" + artesanias.size() + " productos):\n\n");
        for (Artesania a : artesanias) {
            sb.append(String.format("%s  -  %s  -  %s  -  C$ %.2f%n",
                    a.getCodigo(), a.getNombre(), a.getCategoria(), a.getPrecio()));
        }
        Alert alerta = new Alert(Alert.AlertType.INFORMATION, sb.toString(), ButtonType.OK);
        alerta.setHeaderText(null);
        alerta.setTitle("Guardar catalogo");
        alerta.showAndWait();
        lblEstado.setText("Catalogo guardado: " + artesanias.size() + " productos.");
    }

    // Menu Ventas: Registrar venta | Evento: ActionEvent
    @FXML
    private void registrarVenta(ActionEvent event) {
        Artesania sel = tablaArtesanias.getSelectionModel().getSelectedItem();
        if (sel == null) {
            lblEstado.setText("Seleccione un producto de la tabla para registrar la venta.");
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
            if (a.getNombre().toLowerCase().contains(criterio)
                    || a.getCodigo().toLowerCase().contains(criterio)) {
                tablaArtesanias.getSelectionModel().select(a);
                tablaArtesanias.scrollTo(a);
                lblEstado.setText("Encontrado: " + a.getNombre());
                return;
            }
        }
        tablaArtesanias.getSelectionModel().clearSelection();
        lblEstado.setText("Sin coincidencias para: " + criterio);
    }

    // ToolBar / Menu Catalogo: Volver | Evento: ActionEvent
    @FXML
    private void volver(ActionEvent event) throws IOException {
        HelloApplication.setRoot("main-view");
    }
}
