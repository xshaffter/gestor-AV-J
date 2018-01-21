package almacen.utilidades;

import almacen.Global;

/**
 * @author Alfredo Antonio Martinez Armendariz
 */
public final class Utilities {

    /**
     * @param text texto que se quiere formatear
     * @param size tamaño al que se quiere formatear
     * @return texto formateado al tamaño deseado con los espacios a la derecha
     */
    public static String formatString(final String text, final int size) {
        final byte formatSize = (byte) (size - text.length());
        try {
            final StringBuilder result = new StringBuilder();
            result.append(text);
            for (byte i = 0; i < formatSize; i++) {
                result.append(" ");
            }

            return result.toString();
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     *
     * @param text texto que se quiere formatear
     * @param size tamaño al que se quiere formatear
     * @return texto centrado al tamaño deseado
     */
    public static String equiparableString(final String text, final int size) {
        final StringBuilder result = new StringBuilder();
        final int formatSize = size - text.length();
        final byte first = (byte) (formatSize / 2);
        final byte last = (byte) (formatSize - first);

        for (byte i = 0; i < first; i++) {
            result.append(" ");
        }
        result.append(text);
        for (byte i = 0; i < last; i++) {
            result.append(" ");
        }
        return result.toString();
    }

    /**
     * @param text1 primer texto que se desea formatear
     * @param text2 segundo texto que se quiere formatear
     * @param size tamaño al que se quiere formatear
     * @return textos separados al tamaño deseado
     */
    public static String separateStrings(final String text1, final String text2, final int size) {
        final StringBuilder result = new StringBuilder();
        final byte formatSize = (byte) (size - (text1.length() + text2.length()));
        for (byte i = 0; i < formatSize; i++) {
            result.append(" ");
        }
        result.append(text2);
        return result.toString();
    }

    /**
     * @param producto producto que se quiere reducir en la base de datos
     * @param cantidad cantidad en la que se va a reducir de la base de datos
     */
    static void removeFromDB(final String producto, final String cantidad) {
        Global.DRIVER.connect();
        Global.DRIVER.editProductos(producto, Integer.parseInt(cantidad));
        Global.DRIVER.disconnect();
    }
}
