package almacen.gui.panes;

import almacen.Global;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

/**
 * @author Alfredo
 */
public class MenuPrincipal extends GridPane {

    @SuppressWarnings({"OverridableMethodCallInConstructor", "ResultOfObjectAllocationIgnored"})
    public MenuPrincipal() {

        final Label venta, buscar, editar, scan;
        final Font font = new Font(64);


        venta = new Label("Venta");
        buscar = new Label("Buscar");
        editar = new Label("Editar");
        scan = new Label("Scan");

        venta.setFont(font);
        buscar.setFont(font);
        editar.setFont(font);
        scan.setFont(font);

        venta.setOnMouseClicked((MouseEvent) -> {
            //creamos una nueva ventana de ventas y la mostramos
            Global.PSTAGE.setScene(new Scene(new PaneVenta()));
            Global.PSTAGE.show();

        });

        buscar.setOnMouseClicked((MouseEvent) -> {
            //creamos una nueva ventana de busqueda y la mostramos
            //            Global.PSTAGE.setScene(new Scene(new PaneBusqueda()));
            //            Global.PSTAGE.show();

        });

        editar.setOnMouseClicked((MouseEvent) -> {
            //creamos una nueva ventana de edicion de tabla y la mostramos
            //            Global.PSTAGE.setScene(new Scene(new PaneTablas()));
            //            Global.PSTAGE.show();

        });

        scan.setOnMouseClicked((MouseEvent) -> {
            //creamos una nueva ventana de ingreso de productos y la mostramos
            Global.PSTAGE.setScene(new Scene(new PaneIngreso()));
            Global.PSTAGE.show();

        });

        add(buscar, 0, 0);
        add(venta, 1, 0);
        add(editar, 0, 1);
        add(scan, 1, 1);
        //00,10
        //01,11

    }

}
