package polizas.General;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexion {
    protected Connection cnx;
    protected PreparedStatement ps;
    protected ResultSet rs;

    protected String enumVal(String s) {
        return s == null || s.trim().isEmpty() ? null : s.trim().toUpperCase();
    }

    protected String nulo(String s) {
        return s == null || s.trim().isEmpty() ? null : s.trim();
    }

    public void conectar() {
        try {
            if (cnx == null || cnx.isClosed()) {
                String url = "jdbc:mysql://localhost/polizas?useSSL=false&serverTimezone=America/Cancun";
                cnx = DriverManager.getConnection(url, "root", "");
            }
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }

    public void close() {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error al cerrar ResultSet: " + ex.getMessage());
        } finally {
            rs = null;
        }

        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error al cerrar PreparedStatement: " + ex.getMessage());
        } finally {
            ps = null;
        }

        try {
            if (cnx != null) {
                cnx.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error al cerrar Connection: " + ex.getMessage());
        } finally {
            cnx = null;
        }
    }
}