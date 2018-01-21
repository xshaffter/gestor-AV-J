package almacen.utilidades;

import almacen.Global;
import javafx.scene.control.Alert;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Decryptor {
    private static final String algo = "DESede";

    public static String decode(final String texto) {
        String decrypted = "";
        try {
            final byte[] message = Base64.decodeBase64(texto.getBytes("utf-8"));
            final MessageDigest md = MessageDigest.getInstance("MD5");
            final byte[] digestOfPassword = md.digest(Global.KEYWORD.getBytes("utf-8"));
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

    @SuppressWarnings("unused")
    public static String encrypt(final String cadena) {
        String encrypted = "";
        final String kp = "kQ6Gthp364CzeSnV";
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            final byte[] digestOfPassword = md.digest(kp.getBytes("utf-8"));
            final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            final SecretKeySpec key = new SecretKeySpec(keyBytes, algo);
            final Cipher cipher = Cipher.getInstance(algo);
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

    @SuppressWarnings("unused")
    public static String setKP(final String cadena) {
        String encrypted = "";
        final String kp = "kQ6Gthp364CzeSnV";
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            final byte[] digestOfPassword = md.digest(kp.getBytes("utf-8"));
            final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

            final SecretKeySpec key = new SecretKeySpec(keyBytes, algo);
            final Cipher cipher = Cipher.getInstance(algo);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            final byte[] plainTextBytes = cadena.getBytes("utf-8");
            final byte[] buf = cipher.doFinal(plainTextBytes);
            final byte[] base64Bytes = Base64.encodeBase64(buf);
            encrypted = new String(base64Bytes);
        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException ex) {

            final Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("fallo al encriptar kp");
            alert.setHeaderText("fallo al encriptar kp");
            alert.setContentText("" + ex);
            alert.showAndWait();
        }
        return encrypted;
    }

    public static String getKP() {
        String texto;
        String decrypted = "";
        final String kp = "kQ6Gthp364CzeSnV";

        texto = Global.DRIVER.getKP();

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
            if(!Global.FIRST_ACCESS) {
                final Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("fallo al desencriptar kp");
                alert.setHeaderText("fallo al desencriptar kp");
                alert.setContentText("" + ex);
                alert.showAndWait();
            }
        }
        return decrypted;
    }
}
