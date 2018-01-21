package almacen.gui.stages;


import almacen.Global;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class CreateUser extends Stage {

    public CreateUser() {
        final GridPane gPane = new GridPane();
        final Scene scene = new Scene(gPane);
        final Label label = new Label("Creacion de usuario");
        final PasswordField pass = new PasswordField();
        final TextField nombre = new TextField();
        final Button btnAceptar = new Button("Aceptar");

        gPane.add(label, 0, 0);
        gPane.add(nombre, 1, 1);
        gPane.add(pass, 1, 2);
        gPane.add(new Label("nombre: "), 0, 1);
        gPane.add(new Label("contraseña: "), 0, 2);
        gPane.add(btnAceptar, 1, 3);

        gPane.setVgap(10);
        gPane.setPadding(new Insets(10));

        btnAceptar.setOnAction((ActionEvent e) -> createUser(nombre.getText(), pass.getText()));
        gPane.setOnKeyPressed((KeyEvent e) -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                createUser(nombre.getText(), pass.getText());
            }
        });

        setScene(scene);
        show();
    }

    private void createUser(final String nombre, final String password) {
        Global.DRIVER.addUser(password, nombre, 2);
        hide();
        close();
    }
}
