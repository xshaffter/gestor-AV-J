package almacen.utilidades;

import almacen.Global;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Alfredo Antonio Martinez Armendariz
 */
public class WordTicket {

    @SuppressWarnings("unused")
    public static void construir(final String[] content, final String[] datos) {
        int sum = 0;
        for (byte i = 0; i < content.length; i++) {
            if (i != 5) {
                Global.RUN.setText(content[i], i + sum);
                Global.RUN.addCarriageReturn();
            } else {
                int s = 0;
                for (byte x = 0; x < datos.length; x++) {

                    Global.RUN.setText(datos[x], i + x);
                    Global.RUN.addCarriageReturn();
                    s = x;
                }
                sum = s;
            }
        }
        Global.RUN.setFontFamily("Consolas");

        final File dir = new File(System.getProperty("user.home") + "/Documents/ventas/");
        final boolean success = dir.mkdirs();
        final File file = new File(dir.getAbsolutePath() + "/ticket" + Objects.requireNonNull(dir.listFiles()).length + ".docx");
        try {
            final FileOutputStream fos;
            fos = new FileOutputStream(file);

            Global.DOCUMENTO.write(fos);
            fos.close();
        } catch (IOException | NullPointerException ignored) {
        }

    }

    @Override
    public String toString() {
        return "WordTicket";
    }

}
