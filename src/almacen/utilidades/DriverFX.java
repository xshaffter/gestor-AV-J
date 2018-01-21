package almacen.utilidades;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


/**
 * Fecha de la clase: 17/06/2017 Autor: Alfredo
 */
public final class DriverFX {

    private Connection conn = null;
    private final String servidor, baseDatos;

    /**
     * @param servidor ingrese el servidor al que se quiere acceder y utilizar
     * @param dataBase ingrese el nombre de la base de datos con extension incluida
     */
    public DriverFX(final String servidor, final String dataBase) {
        disconnect();
        this.servidor = servidor;
        this.baseDatos = dataBase;
    }

    public void connect() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection(servidor + baseDatos);
        } catch (ClassNotFoundException ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("No se ha encontrado el serivor");
            alert.setContentText("" + ex);
        } catch (SQLException ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("No se ha podido conectar con la base de datos");
            alert.setContentText("" + ex);
        }
    }

    public void disconnect() {
        try {
            conn.close();
            conn = null;
        } catch (SQLException ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("No se ha podido cerrar la conexion");
            alert.setContentText("" + ex);
        } catch (NullPointerException ignored) {
        }
    }

    @SuppressWarnings("unused")
    public boolean create() {
        try {
            final File carpeta = new File("C:/GAFX/");
            final boolean success = carpeta.mkdirs();
            final Path path = Paths.get(carpeta.toURI());
            try {
                Files.setAttribute(path, "dos:hidden", Boolean.TRUE, LinkOption.NOFOLLOW_LINKS);
            } catch (IOException ignored) {
            }
            connect();
            execute("CREATE TABLE 'productos' ( `producto` VARCHAR ( 255 ) UNIQUE, `precio` DOUBLE, `codigo` INTEGER UNIQUE, `ubicacion` INTEGER NOT NULL, `cantidad` INTEGER NOT NULL DEFAULT 0, PRIMARY KEY(`codigo`) )");
            execute("CREATE TABLE \"informacion\" ( `num_ticket` INTEGER DEFAULT 1, `keyword` varchar(255) )");
            execute("CREATE TABLE \"usuarios\" ( `password` varchar ( 255 ) NOT NULL DEFAULT 'unable' UNIQUE, `rango` INTEGER NOT NULL DEFAULT 0, `nombre` varchar(70), PRIMARY KEY(`password`) )");
            return true;
        } catch (SQLException sqlex) {
            return false;
        } finally {
            disconnect();
        }

    }

    /**
     * @param sql la query que se quiere realizar/ejecutar
     * @throws SQLException si ocurriese algun fallo, se devuelve la excepcion para un mejor manejo de la misma en el exterior
     */
    private void execute(final String sql) throws SQLException {
        final Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

    //return Querys

    /**
     * @param codigo el codigo de barras del producto a leer
     * @return el producto obtenido por medio del codigo
     */
    public Producto getProducto(final String codigo) {
        String nombre = "", precio = "";
        try {
            try {
                connect();
                final ResultSet res;
                final String sql = codigo.equals("none") ? "select * from productos" : "select * from productos where codigo = ? and cantidad > 0";
                final PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, codigo);
                res = stmt.executeQuery();

                while (res.next()) {
                    nombre = res.getString("producto");
                    precio = res.getString("precio");
                }

            } catch (NullPointerException ignored) {
            } finally {
                disconnect();
            }
        } catch (NullPointerException npe) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("No se ha podido conectar con la base de datos");
            alert.setContentText("" + npe);
        } catch (SQLException ignored) {
        }
        return Producto.createProducto(nombre, precio);
    }

    /**
     * @param codigo    codigo de barras del producto que se quiere ingresar
     * @param producto  nombre del producto que se quiere ingresar
     * @param precio    precio del producto a ingresar
     * @param ubicacion lugar en el que se va a estar el producto en ambito fisico
     */
    public void addToProducts(final String codigo, final String producto, final double precio, final int ubicacion) {
        try {
            connect();
            final String sql = "insert into productos values(?,?,?,?,?)";
            final PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, producto);
            stmt.setDouble(2, precio);
            stmt.setString(3, codigo);
            stmt.setInt(4, ubicacion);
            stmt.setInt(5, 1);
            stmt.executeUpdate();
        } catch (NullPointerException npe) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("No se ha podido conectar con la base de datos");
            alert.setContentText("" + npe);
        } catch (SQLException e) {
            refreshProducts(codigo);
        } finally {
            disconnect();
        }
    }

    /**
     * @param codigo codigo del producto que se quiere actualizar
     */
    private void refreshProducts(final String codigo) {
        try {
            connect();
            final String sql = "update productos set cantidad = cantidad + 1 where codigo = ?";
            final PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, codigo);
            stmt.executeUpdate();
        } catch (SQLException ignored) {

        } finally {
            disconnect();
        }
    }

    /**
     * @param producto producto que se quiere actualizar
     * @param cantidad cantidad a consumir del producto en cuestion
     */
    public void editProductos(final String producto, final int cantidad) {
        try {
            final String sql = "update productos set cantidad = cantidad - ? where producto = ?";
            final PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cantidad);
            stmt.setString(2, producto);
            stmt.executeUpdate();
        } catch (SQLException ignored) {
        }

    }

    @SuppressWarnings("unused")
    public void clear() throws SQLException {
        try {
            final String sql = "delete from productos";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (NullPointerException npe) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("No se ha podido conectar con la base de datos");
            alert.setContentText("" + npe);
        }
    }

    /**
     * @return devuelve el numero de ticket y actualiza el numero
     */
    public int getNextTicket() {
        int result = 1;
        try {
            connect();
            final String sql = "select * from informacion";
            final PreparedStatement stmt = conn.prepareStatement(sql);

            final ResultSet res = stmt.executeQuery();

            while (res.next()) {
                result = res.getInt("num_ticket");
            }

        } catch (SQLException ignored) {
        } finally {
            try {
                final String sql = "update informacion set num_ticket = num_ticket + 1";
                final PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.executeUpdate();
            } catch (SQLException ignored) {
            } finally {
                disconnect();
            }
        }
        return result;
    }

    public String gKP() {
        String ekp = "";
        try {
            connect();
            final String sql = "select * from informacion";
            final PreparedStatement stmt = conn.prepareStatement(sql);
            final ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ekp = rs.getString("keyword");
            }
        } catch (SQLException ignored) {
        } finally {
            disconnect();
        }

        return ekp;
    }

    /**
     * @param password usuario en cuestion que se quiere analizar
     * @return devuelve el rango que tiene el usuario analizado
     */
    public int getUserRank(final String password) {
        int rank = 3;
        try {
            connect();
            final ResultSet res;
            final String sql = "select rango from usuarios where password = ?";
            final String encryptedPass = Decryptor.encrypt(password);
            final PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, encryptedPass);
            res = stmt.executeQuery();
            while (res.next()) {
                rank = res.getInt("rango");
            }
        } catch (SQLException ignored) {
        } finally {
            disconnect();
        }

        return rank;
    }

    public void iKP(String s) {
        try {
            connect();
            final String sql = "insert into informacion values(1,?)";
            final PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, Decryptor.setKP(s));
            stmt.executeUpdate();
        } catch (SQLException ignored) {
        } finally {
            disconnect();
        }
    }

    /**
     * @param password identificador del usuario
     * @param nombre   nombre real del usuario en sesion
     * @param rango    rango que se le otorgara al usuario
     */
    public void addUser(final String password, final String nombre, final int rango) {
        try {
            connect();
            final String sql = "insert into usuarios values(?,?,?)";
            final PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, Decryptor.encrypt(password));
            stmt.setInt(2, rango);
            stmt.setString(3, nombre);
            stmt.executeUpdate();
        } catch (SQLException ignored) {
        } finally {
            disconnect();
        }
    }

    /**
     * @param password id del usuario que se quiere revisar
     * @return crea una sesion en base a los datos que se ingresaron
     */
    public Session getUser(final String password) {
        String name = "";
        int rank = 3;
        try {
            connect();
            final ResultSet res;
            final String sql = "select * from usuarios where password = ?";
            final String encryptedPass = Decryptor.encrypt(password);
            final PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, encryptedPass);
            res = stmt.executeQuery();
            while (res.next()) {
                rank = res.getInt("rango");
                name = res.getString("nombre");
            }
            res.close();
        } catch (Exception ex) {
            return null;
        } finally {
            disconnect();
        }
        return Session.createSesion(rank, name);
    }
}
