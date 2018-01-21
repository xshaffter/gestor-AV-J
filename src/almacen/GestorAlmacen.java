package almacen;

import almacen.gui.control.PasswordDialog;
import almacen.gui.stages.CreateUser;
import almacen.gui.panes.MenuPrincipal;

import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class GestorAlmacen extends Application {

    private BorderPane main;
    private MenuBar menu = new MenuBar();
    private Menu sesion;
    private PasswordField password;

    @Override
    public void start(final Stage primaryStage) {

        if (Global.FIRST_ACCESS) {

            final PasswordDialog input = new PasswordDialog();
            final PasswordDialog input2 = new PasswordDialog();
            input.setTitle("clave de seguridad");
            input.setHeaderText("Esta palabra clave es la que sera usada para mantener segura su informacion, guardela en un lugar seguro");
            input.setContentText("ingrese la palabra clave");
            final Optional<String> response = input.showAndWait();
            if (response.isPresent()) {
                Global.DRIVER.iKP(response.get());
                Global.initKP(response.get());
            }
            input2.setTitle("clave de administrador");
            input2.setHeaderText("Esta contraseña sera la usada como el administrador del programa, no conservara un nombre\nsolo los permisos");
            input2.setContentText("ingrese su contraseña");
            final Optional<String> password = input2.showAndWait();
            password.ifPresent(s -> Global.DRIVER.addUser(s, "Administrador", 0));

        } else {
            Global.initKP();
        }


        main = new BorderPane();

        final Menu admin = new Menu("Administrador");
        final Menu usuarios = new Menu("usuarios");
        final MenuItem crearUsuario, verUsuarios, cerrarSesion, iniciarSesion;

        final FlowPane paneLogIn = new FlowPane(Orientation.VERTICAL);
        final FlowPane paneBotones = new FlowPane();
        final Button iniciar = new Button("Iniciar Sesion");
        final Scene scene = new Scene(main);

        //Tool bar
        crearUsuario = new MenuItem("Crear Usuario");
        verUsuarios = new MenuItem("Lista Usuarios");
        sesion = new Menu("Sesion");
        cerrarSesion = new MenuItem("Cerrar");
        iniciarSesion = new MenuItem("Iniciar");

        menu.getMenus().add(admin);
        admin.getItems().add(usuarios);
        usuarios.getItems().add(crearUsuario);
        usuarios.getItems().add(verUsuarios);

        menu.getMenus().add(sesion);
        sesion.getItems().add(iniciarSesion);

        main.setTop(menu);

        iniciarSesion.setOnAction((ActionEvent e) -> {
            sesion.getItems().removeAll(iniciarSesion);
            sesion.getItems().add(cerrarSesion);
            menu.getMenus().removeAll(sesion);
            main.setCenter(paneLogIn);
        });
        cerrarSesion.setOnAction((ActionEvent e) -> {
            if (Global.currentSession != null) {
                sesion.getItems().removeAll(cerrarSesion);
                sesion.getItems().add(iniciarSesion);
                Global.currentSession = null;
                main.setCenter(new FlowPane());
            }
        });

        crearUsuario.setOnAction((ActionEvent e) -> {
            if (verifyAdmin()) {
                new CreateUser();
            } else {
                final Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Usuario incorrecto");
                alert.setContentText("La contraseña ingresada es incorrecta");
                alert.showAndWait();
            }
        });
        verUsuarios.setOnAction((ActionEvent e) -> {
            if (verifyAdmin()) {
                //crear una ventana que muestre una lista de los usuarios dentro del sistema
                System.out.println("");
            } else {
                final Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Usuario incorrecto");
                alert.setContentText("La contraseña ingresada es incorrecta");
                alert.showAndWait();
            }
        });

        //Tool bar-end

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
        iniciar.setOnAction((ActionEvent) -> verifyPassword(password.getText()));

        paneLogIn.setAlignment(Pos.CENTER);
        paneLogIn.getChildren().add(password);
        paneLogIn.getChildren().add(paneBotones);
        paneBotones.setAlignment(Pos.CENTER);

        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(final String[] args) {
        try {
            launch(args);
        } catch (Exception ignored) {
        }
    }

    private void verifyPassword(final String pass) {
        Global.currentSession = Global.DRIVER.getUser(pass);
        if (!Global.currentSession.getName().isEmpty()) {
            final MenuPrincipal menu = new MenuPrincipal();
            menu.setAlignment(Pos.CENTER);
            main.setCenter(menu);
            this.menu.getMenus().add(sesion);
        } else {
            final Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Contraseña incorrecta");
            alert.setContentText("La contraseña ingresada no es valida");
            alert.showAndWait();
        }
        password.clear();

    }

    private boolean verifyAdmin() {
        final PasswordDialog input = new PasswordDialog();
        input.setTitle("verificacion");
        input.setContentText("ingrese su contraseña");
        final Optional<String> response = input.showAndWait();
        return response.isPresent() && Global.DRIVER.getUserRank(response.get()) == 0;
    }

}

