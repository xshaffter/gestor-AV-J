package almacen.utilidades;

import almacen.Global;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import almacen.gui.control.PLabel;

/**
 *
 * @author HP
 */
public class Producto {

        private String codigo, nombre, precio;

        public Producto(final String codigo) {

                try {
                        Global.DRIVER.connect();
                        final ResultSet rs = Global.DRIVER.select(codigo);

                        this.codigo = codigo;
                        while (rs.next()) {
                                this.nombre = rs.getString("producto");
                                this.precio = rs.getString("precio");
                        }

                        Global.DRIVER.disconnect();
                } catch (SQLException ex) {
                        final Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("No se han podido obtener los datos");
                        alert.setContentText("" + ex);
                        alert.showAndWait();
                }
        }

        public Producto(final double precio) {
                nombre = "Extra";
                this.precio = "" + precio;
                codigo = "";
        }

        public PLabel getCodigo() {
                return new PLabel(codigo);
        }

        public PLabel getNombre() {
                return new PLabel(nombre);
        }

        public PLabel getPrecio() {
                return new PLabel(precio);
        }

}
