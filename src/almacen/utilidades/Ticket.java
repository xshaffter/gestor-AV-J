package almacen.utilidades;

import almacen.Global;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Ticket {

    private String[] content = {
            "{{titulo}}",
            "--direccion--",
            "--RFC--",
            "--telefono--",
            Global.SEPARADOR,
            "{{datos}}",
            Global.SEPARADOR,
            "Total venta{{final}}",
            "Recibió{{recibo}}",
            "Cambio{{cambio}}",
            Global.SEPARADOR,
            "{{numero de ticket}}",
            "{{fecha}}",
            "Gracias por su compra"};

    /**
     * @param cantidades
     * @param productos
     * @param precios
     * @param totales
     * @param recibo
     */
    public Ticket(final String[] cantidades, final String[] productos, final String[] precios, final String recibo) {
        Alert alert;
        try {
            Double precio = 0.0;
            String precioFinal, cambio, datos[];

            datos = new String[productos.length];
            for (byte i = 0; i < datos.length; i++) {
                datos[i] = "";
            }

            datos[0] += Utilities.formatString(cantidades[0], 7);
            datos[0] += Utilities.formatString(productos[0], 10);
            datos[0] += Utilities.formatString(precios[0], 7);
            datos[0] += Utilities.formatString("Total", 7);
            datos[0] += "\n";

            for (byte i = 1; i < productos.length; i++) {
                String total;
                total = "" + Double.parseDouble(precios[i]) * Double.parseDouble(cantidades[i]);
                precio += Double.parseDouble(total);

                datos[i] += Utilities.formatString(cantidades[i], 7);
                datos[i] += Utilities.formatString(productos[i].length() < 8 ? productos[i] : productos[i].substring(0, 8), 10);
                datos[i] += Utilities.formatString("$" + Global.DFO00_00.format(Double.parseDouble(precios[i])), 7);
                datos[i] += Utilities.formatString("$" + Global.DFO00_00.format(Double.parseDouble(total)), 7);
                Utilities.removeFromDB(productos[i], cantidades[i]);
            }

            precioFinal = "" + precio;
            cambio = "" + (Double.parseDouble(recibo) - precio);
            if (Double.parseDouble(cambio) >= 0) {
                content[0] = Utilities.equiparableString(Global.NOMBRE_LOCAL, Global.TICKET_LENGTH);
                content[7] = "Total venta" + Utilities.separateStrings("Total venta", "$" + Global.DFO00_00.format(Double.parseDouble(precioFinal)), Global.TICKET_LENGTH);
                content[8] = "Recibió" + Utilities.separateStrings("Recibio", "$" + Global.DFO00_00.format(Double.parseDouble(recibo)), Global.TICKET_LENGTH);
                content[9] = "Cambio" + Utilities.separateStrings("Cambio", "$" + Global.DFO00_00.format(Double.parseDouble(cambio)), Global.TICKET_LENGTH);
                content[11] = "N. Ticket: " + new DecimalFormat("0000").format(Global.DRIVER.getNextTicket());
                content[12] = "Fecha: " + LocalDate.now().toString() + " a las " + LocalDateTime.now().getHour() + ":" + Global.FOR00.format(LocalDateTime.now().getMinute());

                WordTicket.construir(content, datos);
                Global.PSTAGE.hide();
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Dinero Insuficiente");
                alert.setContentText("El dinero otorgado es insuficiente");
            }

        } catch (NumberFormatException ex) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());
        }
    }
}
