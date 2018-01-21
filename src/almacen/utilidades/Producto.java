package almacen.utilidades;

import almacen.Global;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert;
import almacen.gui.control.PLabel;

/**
 * @author HP
 */
public class Producto {

    private String nombre, precio;

    private Producto(final String nombre, final String precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    private Producto(final double precio) {
        nombre = "Extra";
        this.precio = "" + precio;
    }

    static Producto createProducto(final String nombre, final String precio) {
        return !precio.isEmpty() ? new Producto(nombre, precio) : null;
    }

    public static Producto createProducto(final double precio) {
        return precio != 0 ? new Producto(precio) : null;
    }

    public PLabel getNombre() {
        return new PLabel(nombre);
    }

    public PLabel getPrecio() {
        return new PLabel(precio);
    }

}
