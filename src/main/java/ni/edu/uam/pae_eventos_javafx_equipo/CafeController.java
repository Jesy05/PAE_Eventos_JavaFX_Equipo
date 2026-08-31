package ni.edu.uam.pae_eventos_javafx_equipo;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Optional;

public class CafeController {

    @FXML private TableView<LoteCafe> tablaLotes;
    @FXML private TableColumn<LoteCafe, String> colCodigo;
    @FXML private TableColumn<LoteCafe, String> colProductor;
    @FXML private TableColumn<LoteCafe, String> colQuintales;
    @FXML private TableColumn<LoteCafe, String> colHumedad;
    @FXML private Label lblDetalle;

    private final ObservableList<LoteCafe> lotes = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        colCodigo.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getCodigo()));
        colProductor.setCellValueFactory(c -> new ReadOnlyStringWrapper(c.getValue().getProductor()));
        colQuintales.setCellValueFactory(c ->
                new ReadOnlyStringWrapper(String.format("%.2f", c.getValue().getQuintales())));
        colHumedad.setCellValueFactory(c ->
                new ReadOnlyStringWrapper(String.format("%.1f", c.getValue().getHumedad())));
        tablaLotes.setItems(lotes);

        lotes.addAll(
                new LoteCafe("L-001", "Coop. San Ramon", 12.5, 11.2),
                new LoteCafe("L-002", "Finca La Esperanza", 8.0, 12.4),
                new LoteCafe("L-003", "Productores de Jinotega", 20.0, 10.5)
        );
    }

    // Fuente: fila del TableView | Evento: MouseEvent
    @FXML
    private void mostrarDetalle(MouseEvent event) {
        LoteCafe lote = tablaLotes.getSelectionModel().getSelectedItem();
        if (lote == null) {
            return;
        }
        lblDetalle.setText(String.format(
                "Lote %s | Productor: %s | Quintales: %.2f | Humedad: %.1f%%",
                lote.getCodigo(), lote.getProductor(), lote.getQuintales(), lote.getHumedad()));
    }

    // Fuente: ContextMenu (Editar) | Evento: ActionEvent
    @FXML
    private void editarLote(ActionEvent event) {
        LoteCafe lote = tablaLotes.getSelectionModel().getSelectedItem();
        if (lote == null) {
            informar("Seleccione un lote para editar.");
            return;
        }
        TextInputDialog dialog = new TextInputDialog(String.valueOf(lote.getQuintales()));
        dialog.setTitle("Editar lote");
        dialog.setHeaderText("Lote " + lote.getCodigo());
        dialog.setContentText("Nuevos quintales:");

        Optional<String> respuesta = dialog.showAndWait();
        if (respuesta.isPresent()) {
            try {
                lote.setQuintales(Double.parseDouble(respuesta.get().trim()));
                tablaLotes.refresh();
                lblDetalle.setText("Lote " + lote.getCodigo() + " actualizado.");
            } catch (NumberFormatException e) {
                informar("El valor debe ser numerico.");
            }
        }
    }

    // Fuente: ContextMenu (Eliminar) | Evento: ActionEvent + confirmacion con Alert
    @FXML
    private void eliminarLote(ActionEvent event) {
        LoteCafe lote = tablaLotes.getSelectionModel().getSelectedItem();
        if (lote == null) {
            informar("Seleccione un lote para eliminar.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Eliminar el lote " + lote.getCodigo() + " de " + lote.getProductor() + "?",
                ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText(null);
        confirm.setTitle("Confirmar eliminacion");

        Optional<ButtonType> opcion = confirm.showAndWait();
        if (opcion.isPresent() && opcion.get() == ButtonType.YES) {
            lotes.remove(lote);
            lblDetalle.setText("Lote " + lote.getCodigo() + " eliminado.");
        }
    }

    private void informar(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION, mensaje, ButtonType.OK);
        alerta.setHeaderText(null);
        alerta.showAndWait();
    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
        HelloApplication.setRoot("main-view");
    }
}