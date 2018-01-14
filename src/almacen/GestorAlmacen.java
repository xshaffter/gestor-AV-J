package almacen;

import almacen.gui.panes.MenuPrincipal;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.sql.SQLException;
import java.util.Arrays;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.*;

public class GestorAlmacen extends Application {

        private BorderPane main;
        private PasswordField password;

        @Override
        public void start(final Stage primaryStage) {
            try {
                Global.DRIVER.create();
            } catch (SQLException ex) {
            }

            main = new BorderPane();
            final FlowPane paneLogIn = new FlowPane(Orientation.VERTICAL);
            final FlowPane paneBotones = new FlowPane();
            final Button iniciar = new Button("Iniciar Sesion");
            final Scene scene = new Scene(main);

            paneBotones.getChildren().add(iniciar);

            primaryStage.setTitle(Global.NOMBRE_LOCAL);

            password = new PasswordField();
            password.setPromptText("Contraseña");

            password.setOnKeyPressed((KeyEvent e) -> {
                switch (e.getCode()) {
                    case ENTER:
                        verifyPassword(password.getText());
                        break;
                }
            });
            iniciar.setOnAction((ActionEvent) -> {
                verifyPassword(password.getText());
            });

            main.setCenter(paneLogIn);

            paneLogIn.setAlignment(Pos.CENTER);
            paneLogIn.getChildren().add(password);
            paneLogIn.getChildren().add(paneBotones);

            primaryStage.setMaximized(true);
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        public static void main(final String[] args) {
            try {
                launch(args);
            } catch (Exception ex) {
            }
        }

        private void verifyPassword(final String pass) {
            final String p = DXDecryptoresO6hxM0.decode("ZRSzL8T3APpXyGfOPFxagg==");
            if(pass.equals(p)) {
                final MenuPrincipal menu = new MenuPrincipal();
                menu.setAlignment(Pos.CENTER);
                main.setCenter(menu);
            } else {
                final Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Contraseña incorrecta");
                alert.setContentText("La contraseña ingresada no es calida");
                alert.showAndWait();
                password.clear();
            }
        }

}

class DXDecryptoresO6hxM0 {
    static String algo = "DESede";
    static String kp = "kQ6Gthp364CzeSnV";

    public static String decode(final String texto) {
        String decrypted = "";
        try {
            final byte[] message = Base64.decodeBase64(texto.getBytes("utf-8"));
            final MessageDigest md = MessageDigest.getInstance("MD5");
            final byte[] digestOfPassword = md.digest(kp.getBytes("utf-8"));
            final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            final SecretKey key = new SecretKeySpec(keyBytes, algo);

            final Cipher decipher = Cipher.getInstance(algo);
            decipher.init(Cipher.DECRYPT_MODE, key);

            final byte[] plainText = decipher.doFinal(message);

            decrypted = new String(plainText, "UTF-8");

        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("fallo al desencriptar datos");
            alert.setHeaderText("fallo al desencriptar datos");
            alert.setContentText("" + ex);
            alert.showAndWait();
        }
        return decrypted;
    }

    public static String encrypt(final String cadena) {
        String encrypted = "";
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            final byte[] digestOfPassword = md.digest(kp.getBytes("utf-8"));
            final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            final SecretKeySpec key = new SecretKeySpec(keyBytes, "DESede");
            final Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            final byte[] plainTextBytes = cadena.getBytes("utf-8");
            final byte[] buf = cipher.doFinal(plainTextBytes);
            final byte[] base64Bytes = Base64.encodeBase64(buf);
            encrypted = new String(base64Bytes);
        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("fallo al encriptar datos");
            alert.setHeaderText("fallo al encriptar datos");
            alert.setContentText("" + ex);
            alert.showAndWait();
        }
        return encrypted;
    }
}
