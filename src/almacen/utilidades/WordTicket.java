package almacen.utilidades;

import almacen.Global;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Alfredo Antonio Martinez Armendariz
 */
public class WordTicket {

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
                dir.mkdirs();
                final File file = new File(dir.getAbsolutePath() + "/ticket" + dir.listFiles().length + ".docx");
                try {
                        final FileOutputStream fos;
                        fos = new FileOutputStream(file);

                        Global.DOCUMENTO.write(fos);
                        fos.close();
                } catch (IOException ex) {
                }

        }

        @Override
        public String toString() {
                return "WordTicket";
        }

}
