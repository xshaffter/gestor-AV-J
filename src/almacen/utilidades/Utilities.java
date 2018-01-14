package almacen.utilidades;

import almacen.Global;

/**
 *
 * @author Alfredo Antonio Martinez Armendariz
 */
public final class Utilities {

        /**
         *
         * @param text
         * @param size
         * @return
         */
        public static String formatString(final String text, final int size) {
                final byte formatSize = (byte) (size - text.length());
                try {
                        String result = "";
                        result += text;
                        for (byte i = 0; i < formatSize; i++) {
                                result += " ";
                        }

                        return result;
                } catch (Exception ex) {
                        return "";
                }
        }

        public static String equiparableString(final String text, final int size) {
                String result = "";
                final int formatSize = size - text.length();
                final byte first = (byte) (formatSize / 2);
                final byte last = (byte) (formatSize - first);

                for (byte i = 0; i < first; i++) {
                        result += " ";
                }
                result += text;
                for (byte i = 0; i < last; i++) {
                        result += " ";
                }
                return result;
        }

        /**
         *
         * @param text1
         * @param text2
         * @param size
         * @return
         */
        public static String separateStrings(final String text1, final String text2, final int size) {
                String result = "";
                final byte formatSize = (byte) (size - (text1.length() + text2.length()));
                for (byte i = 0; i < formatSize; i++) {
                        result += " ";
                }
                result += text2;
                return result;
        }

        /**
         *
         * @param producto
         * @param cantidad
         */
        static void removeFromDB(final String producto, final String cantidad) {
                Global.DRIVER.connect();
                Global.DRIVER.editProductos(producto, Integer.parseInt(cantidad));
                Global.DRIVER.disconnect();
        }
}
