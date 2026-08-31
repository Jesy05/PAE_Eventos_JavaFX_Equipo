package ni.edu.uam.pae_eventos_javafx_equipo;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

public class InventarioController {

    @FXML private TextField txtCodigo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtCantidad;
    @FXML private TextField txtBuscar;
    @FXML private Label lblMensaje;

    @FXML private TableView<Producto> tablaProductos;
    @FXML private TableColumn<Producto, String> colCodigo;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, String> colPrecio;
    @FXML private TableColumn<Producto, String> colCantidad;

    private final ObservableList<Producto> productos = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        colCodigo.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getCodigo()));
        colNombre.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getNombre()));
        colPrecio.setCellValueFactory(c ->
                new ReadOnlyStringWrapper(String.format("C$ %.2f", c.getValue().getPrecio())));
        colCantidad.setCellValueFactory(c ->
                new ReadOnlyStringWrapper(String.valueOf(c.getValue().getCantidad())));
        tablaProductos.setItems(productos);
    }

    // Fuente: Button "Guardar" | Evento: ActionEvent
    @FXML
    private void guardarProducto(ActionEvent event) {
        String codigo   = txtCodigo.getText().trim();
        String nombre   = txtNombre.getText().trim();
        String precioTx = txtPrecio.getText().trim();
        String cantTx   = txtCantidad.getText().trim();

        if (codigo.isEmpty() || nombre.isEmpty() || precioTx.isEmpty() || cantTx.isEmpty()) {
            lblMensaje.setText("Complete todos los campos.");
            return;
        }

        double precio;
        int cantidad;
        try {
            precio = Double.parseDouble(precioTx);
            cantidad = Integer.parseInt(cantTx);
        } catch (NumberFormatException e) {
            lblMensaje.setText("Precio y cantidad deben ser numericos.");
            return;
        }
        if (precio < 0 || cantidad < 0) {
            lblMensaje.setText("Precio y cantidad no pueden ser negativos.");
            return;
        }

        productos.add(new Producto(codigo, nombre, precio, cantidad));
        lblMensaje.setText("Producto guardado: " + nombre);
        limpiarFormulario(event);
    }

    // Fuente: TextField de busqueda | Evento: KeyEvent (ENTER)
    @FXML
    private void buscarConEnter(KeyEvent event) {
        if (event.getCode() != KeyCode.ENTER) {
            return;
        }
        String criterio = txtBuscar.getText().trim().toLowerCase();
        if (criterio.isEmpty()) {
            lblMensaje.setText("Escriba un codigo o nombre para buscar.");
            return;
        }
        for (Producto p : productos) {
            if (p.getCodigo().toLowerCase().equals(criterio)
                    || p.getNombre().toLowerCase().contains(criterio)) {
                tablaProductos.getSelectionModel().select(p);
                tablaProductos.scrollTo(p);
                lblMensaje.setText("Producto encontrado: " + p.getNombre());
                return;
            }
        }
        tablaProductos.getSelectionModel().clearSelection();
        lblMensaje.setText("Sin coincidencias para: " + criterio);
    }

    @FXML
    private void limpiarFormulario(ActionEvent event) {
        txtCodigo.clear();
        txtNombre.clear();
        txtPrecio.clear();
        txtCantidad.clear();
        txtCodigo.requestFocus();
    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
        HelloApplication.setRoot("main-view");
    }
}