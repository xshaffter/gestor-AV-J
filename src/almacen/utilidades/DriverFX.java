package almacen.utilidades;

import java.io.*;
import java.sql.*;
import java.util.Optional;

import almacen.Global;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javax.xml.transform.Result;

/**
 * Fecha de la clase: 17/06/2017 Autor: Alfredo
 */
public final class DriverFX {

    private Connection conn = null;
    private final String servidor, baseDatos;

    public DriverFX(final String servidor, final String dataBase) {
        disconnect();
        this.servidor = servidor;
        this.baseDatos = dataBase;
    }

    public boolean conected() {
        return conn != null;
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
        } catch (NullPointerException npe) {
        }
    }

    public boolean create() {
        try {
            final File carpeta = new File("C:/GAFX/");
            carpeta.mkdirs();
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

    private void execute(final String sql) throws SQLException {
        final Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

    //return Querys
    public ResultSet select(final String condition) {
        ResultSet sesion = null;
        try {
            final String sql = condition.equals("none") ? "select * from productos" : "select * from productos where codigo = ? and cantidad > 0";
            final PreparedStatement stmt = conn.prepareStatement(sql);
            try {
                stmt.setString(1, condition);
            } catch (NullPointerException noe) {
            } finally {
                sesion = stmt.executeQuery();
            }
        } catch (NullPointerException npe) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("No se ha podido conectar con la base de datos");
            alert.setContentText("" + npe);
        } catch (SQLException ex) {
        }
        return sesion;
    }

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

    private void refreshProducts(final String codigo) {
        try {
            connect();
            final String sql = "update productos set cantidad = cantidad + 1 where codigo = ?";
            final PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, codigo);
            stmt.executeUpdate();
        } catch (SQLException sqlEX) {

        } finally {
            disconnect();
        }
    }

    public void editProductos(final String producto, final int cantidad) {
        try {
            final String sql = "update productos set cantidad = cantidad - ? where producto = ?";
            final PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, cantidad);
            stmt.setString(2, producto);
            stmt.executeUpdate();
        } catch (SQLException ex) {
        }

    }

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

        } catch (SQLException ex) {
        } finally {
            try {
                final String sql = "update informacion set num_ticket = num_ticket + 1";
                final PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.executeUpdate();
            } catch (SQLException ex) {
            }
        }
        return result;
    }

    public String getKP() {
        String ekp = "";
        try {
            connect();
            final String sql = "select * from informacion";
            final PreparedStatement stmt = conn.prepareStatement(sql);
            final ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ekp = rs.getString("keyword");
            }
        } catch (SQLException e) {
        } finally {
            disconnect();
        }

        return ekp;
    }

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
            while(res.next()) {
                rank = res.getInt("rango");
            }
        } catch (SQLException e) {
        } finally {
            disconnect();
        }

        return rank;
    }

    public void inertKP(String s) {
        try {
            connect();
            final String sql = "insert into informacion values(1,?)";
            final PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, Decryptor.setKP(s));
            stmt.executeUpdate();
        } catch (SQLException e) {
        } finally {
            disconnect();
        }
    }

    public void addUser(final String password, final String nombre, final int rango) {
        try {
            connect();
            final String sql = "insert into usuarios values(?,?,?)";
            final PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, Decryptor.encrypt(password));
            stmt.setInt(2, rango);
            stmt.setString(3, nombre);
            stmt.executeUpdate();
        } catch (SQLException sqlex) {
        } finally {
            disconnect();
        }
    }

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
