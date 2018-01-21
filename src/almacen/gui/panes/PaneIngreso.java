package almacen.gui.panes;

import almacen.Global;
import almacen.gui.control.PTextField;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

class PaneIngreso extends BorderPane {

    PaneIngreso() {
        final FlowPane flow = new FlowPane();
        final Label informacion = new Label();
        final PTextField edtCodigo, edtNombre, edtPrecio, edtUbicacion;
        edtCodigo = new PTextField("codigo de barras");
        edtNombre = new PTextField("nombre del producto");
        edtPrecio = new PTextField("precio del producto");
        edtUbicacion = new PTextField("ubicacion del producto");
        setTop(informacion);
        setCenter(flow);
        flow.getChildren().add(edtNombre);
        flow.getChildren().add(edtCodigo);
        flow.getChildren().add(edtPrecio);
        flow.getChildren().add(edtUbicacion);

        analize(edtCodigo.getText(),edtNombre.getText(), edtPrecio.getDouble(), edtUbicacion.getInt());

    }

    private void analize(final String codigo, final String nombre, final double precio, final int ubicacion) {
        Global.DRIVER.addToProducts(codigo, nombre, precio, ubicacion);
    }

}
