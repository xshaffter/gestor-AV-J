package almacen.utilidades;

import almacen.gui.control.PLabel;
import almacen.gui.control.PTextField;

import java.util.regex.Pattern;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;

/**
 * @author HP
 */
public class TableRow {

    /**
     *
     * @param producto
     * @param productos
     * @param cantidades
     * @param precios
     * @param totales
     * @param total
     */
    public TableRow(final Producto producto, final FlowPane productos, final FlowPane cantidades, final FlowPane precios, final FlowPane totales, final Label total) {
        try {
            final PLabel txtTotal = new PLabel();
            final PTextField edtCantidad;
            final PLabel product = producto.getNombre(), precio = producto.getPrecio();
            edtCantidad = new PTextField("01", false);

            final byte size = 18;
            product.setFont(new Font(size));
            precio.setFont(new Font(size));
            txtTotal.setFont(new Font(size));

            if (!product.getText().equals("")) {
                productos.getChildren().add(product);
                precios.getChildren().add(precio);
                totales.getChildren().add(txtTotal);
                cantidades.getChildren().add(edtCantidad);
            }

            edtCantidad.setOnKeyPressed((KeyEvent e) -> {
                String cantidad;
                int cantidadReal;
                Double precioUnitario, precioFinal;
                double preTotal, preTotalReal, subTotal;
                switch (e.getText()) {
                    case "1":
                    case "2":
                    case "3":
                    case "4":
                    case "5":
                    case "6":
                    case "7":
                    case "8":
                    case "9":
                    case "0":
                        preTotal = Double.parseDouble(total.getText().replaceAll(Pattern.quote("$"), ""));
                        preTotalReal = preTotal - Double.parseDouble(edtCantidad.getText()) * Double.parseDouble(producto.getPrecio().getText());
                        cantidad = edtCantidad.getText() + e.getText();
                        cantidadReal = Integer.parseInt(cantidad);
                        precioUnitario = Double.parseDouble(producto.getPrecio().getText());
                        precioFinal = cantidadReal * precioUnitario;
                        txtTotal.setText("$" + precioFinal);
                        subTotal = preTotalReal + precioFinal;
                        total.setText("$" + subTotal);
                    default:
                        try {
                            if (e.getCode() == KeyCode.BACK_SPACE) {
                                final int ultimaLetram1 = edtCantidad.getText().length() - 1;
                                preTotal = Double.parseDouble(total.getText().replaceAll(Pattern.quote("$"), ""));
                                preTotalReal = preTotal - Double.parseDouble(edtCantidad.getText()) * Double.parseDouble(producto.getPrecio().getText());
                                cantidad = edtCantidad.getText().substring(0, ultimaLetram1);
                                cantidadReal = Integer.parseInt(cantidad);
                                precioUnitario = Double.parseDouble(producto.getPrecio().getText());
                                precioFinal = cantidadReal * precioUnitario;
                                txtTotal.setText("$" + precioFinal);
                                subTotal = preTotalReal + precioFinal;
                                total.setText("$" + subTotal);
                            } else {
                                e.consume();
                            }
                        } catch (NumberFormatException nfe) {
                            e.consume();
                        }
                }

            });

            final double precioFinal = edtCantidad.getInt() * Double.parseDouble(producto.getPrecio().getText());
            txtTotal.setText("$" + precioFinal);
            double preTotal = Double.parseDouble(total.getText().replaceAll(Pattern.quote("$"), ""));
            final double subTotal = preTotal + precioFinal;
            total.setText("$" + subTotal);
        } catch (NullPointerException npe) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Problema al buscar");
            alert.setContentText("No hay existencias de ese producto");
            alert.showAndWait();
        }
    }
}
