package almacen;

import almacen.gui.stages.PrimaryStage;
import almacen.utilidades.Decryptor;
import almacen.utilidades.DriverFX;

import java.text.DecimalFormat;

import almacen.utilidades.Session;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 * @author HP
 */
public final class Global {

    public static Session currentSession;

    public static final int WIDTH = (int) Screen.getPrimary().getBounds().getWidth();
    public static final DriverFX DRIVER = new DriverFX("jdbc:sqlite:C:/GAFX/", "41m4c3n.db");
    public static final PrimaryStage PSTAGE = new PrimaryStage(new BorderPane());
    public static final DecimalFormat FOR00 = new DecimalFormat("00");
    public static final DecimalFormat DFO00_00 = new DecimalFormat("00.00");

    public static final XWPFDocument DOCUMENTO = new XWPFDocument();
    private static final XWPFParagraph PARRAFO = DOCUMENTO.createParagraph();
    public static final XWPFRun RUN = PARRAFO.createRun();

    public static final String NOMBRE_LOCAL = "PAPELERÍA AV-J";
    public static final String SEPARADOR = "=============================";

    public static final boolean FIRST_ACCESS = DRIVER.create();
    public static final int TICKET_LENGTH = SEPARADOR.length();

    public static String KEYWORD;

    public static void initKP() {
        KEYWORD = Decryptor.getKP();

    }

    public static void initKP(final String t) {
        KEYWORD = t;

    }

}
