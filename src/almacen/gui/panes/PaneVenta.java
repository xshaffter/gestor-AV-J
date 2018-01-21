package almacen.gui.panes;

import almacen.Global;
import almacen.gui.control.PLabel;
import almacen.gui.control.PTextField;
import almacen.utilidades.Producto;
import almacen.utilidades.TableRow;
import almacen.utilidades.Ticket;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.TextInputDialog;

/**
 * @author HP
 */
public class PaneVenta extends BorderPane {

    private final PLabel precioFinal;
    private final FlowPane cantidades, precios, totales, productos;
    private String[] prods, cants, precs;

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    PaneVenta() {

        //declaraciones
        final int height = 70_000;
        final ScrollPane scroll = new ScrollPane();
        final FlowPane abarrotesPane = new FlowPane();
        final FlowPane ventaPane = new FlowPane();
        final GridPane bot = new GridPane();
        final GridPane registros = new GridPane();
        final PLabel nombre, cantidad, precio, total;
        final PTextField edtCodigos = new PTextField();

        scroll.setContent(registros);

        registros.setMinWidth(Global.WIDTH);
        registros.setMinHeight(height);
        registros.setMaxWidth(Global.WIDTH);
        scroll.setMaxWidth(Global.WIDTH);
        scroll.setMinWidth(Global.WIDTH);

        //end-declaraciones
        //deiniciones
        cantidades = new FlowPane(Orientation.VERTICAL);
        precios = new FlowPane(Orientation.VERTICAL);
        totales = new FlowPane(Orientation.VERTICAL);
        productos = new FlowPane(Orientation.VERTICAL);

        nombre = new PLabel("Producto");
        precio = new PLabel("Prec");
        cantidad = new PLabel("Cant");
        total = new PLabel("Total");
        precioFinal = new PLabel("$0.0");

        //end-definiciones
        nombre.setFont(Font.font(32));
        cantidad.setFont(Font.font(32));
        precio.setFont(Font.font(32));
        total.setFont(Font.font(32));
        precioFinal.setFont(Font.font(32));

        bot.add(abarrotesPane, 0, 0);
        bot.add(ventaPane, 0, 1);

        registros.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        abarrotesPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        ventaPane.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        registros.setAlignment(Pos.TOP_CENTER);
        registros.setHgap(128);

        productos.getChildren().add(nombre);
        cantidades.getChildren().add(cantidad);
        precios.getChildren().add(precio);
        totales.getChildren().add(total);

        registros.add(productos, 0, 0);
        registros.add(cantidades, 1, 0);
        registros.add(precios, 2, 0);
        registros.add(totales, 3, 0);

        //columnas
        final double gap = 1.5;
        productos.setVgap(gap);
        cantidades.setVgap(3.5);
        precios.setVgap(gap);
        totales.setVgap(gap);

        productos.autosize();
        cantidades.autosize();
        precios.autosize();
        totales.autosize();

        productos.setPrefHeight(height);
        cantidades.setPrefHeight(height);
        precios.setPrefHeight(height);
        totales.setPrefHeight(height);
        //end-columnas

        this.setOnKeyPressed((KeyEvent e) -> {
            switch (e.getCode()) {
                case ENTER:
                    if (!edtCodigos.getFirst().equals("abrr")) {
                        add(Global.DRIVER.getProducto(edtCodigos.getText()));
                    } else {
                        add(Producto.createProducto(edtCodigos.getAbarrotes()));
                    }
                    edtCodigos.setText("");
                    break;
                case SHIFT:
                    cants = new String[productos.getChildren().size()];
                    prods = new String[productos.getChildren().size()];
                    precs = new String[productos.getChildren().size()];
                    for (byte i = 0; i < cants.length; i++) {
                        cants[i] = cantidades.getChildren().get(i).toString();
                        prods[i] = productos.getChildren().get(i).toString();
                        precs[i] = precios.getChildren().get(i).toString();
                    }
                    final TextInputDialog input = new TextInputDialog();
                    input.setTitle("ingrese el pago");
                    input.setContentText("Ingrese cuanto le pagaron");
                    final Optional<String> response = input.showAndWait();

                    if (response.isPresent()) {
                        try {
                            new Ticket(cants, prods, precs, response.get());
                        } catch (NumberFormatException ignored) {
                        }
                    }
                    break;
            }
        });

        ventaPane.getChildren().add(edtCodigos);
        ventaPane.getChildren().add(precioFinal);

        bot.setMinWidth(Global.WIDTH);
        bot.setMaxWidth(Global.WIDTH);
        abarrotesPane.setMinWidth(Global.WIDTH);
        abarrotesPane.setMaxWidth(Global.WIDTH);

        setCenter(scroll);
        setBottom(bot);

    }

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void add(final Producto producto) {
        new TableRow(producto, productos, cantidades, precios, totales, precioFinal);
    }

}
