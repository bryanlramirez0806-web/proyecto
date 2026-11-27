package polizas.General;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Conexion {
    public Connection cnx;
    public PreparedStatement ps;
    public ResultSet rs;

    // Último error en palabras sencillas, para mostrarlo en la pantalla
    protected String mensajeError = "";

    protected String enumVal(String s) {
        return s == null || s.trim().isEmpty() ? null : s.trim().toUpperCase();
    }

    protected String nulo(String s) {
        return s == null || s.trim().isEmpty() ? null : s.trim();
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void conectar() {
        mensajeError = "";
        try {
            cnx = DriverManager.getConnection("jdbc:mysql://localhost/polizas", "root", "");
        } catch (SQLException e) {
            cnx = null;
            mensajeError = "No hay conexión con la base de datos. Revisa que MariaDB esté encendido.";
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }

    /**
     * Traduce los errores de la base de datos a un mensaje que entienda el usuario.
     * Los códigos son los que regresa MariaDB/MySQL.
     */
    protected void guardarError(SQLException ex) {
        System.out.println("Error: " + ex.getMessage());
        switch (ex.getErrorCode()) {
            case 1062:
                mensajeError = "Ya existe un registro con ese dato (RFC, CURP, número de serie, placas o número de póliza repetido).";
                break;
            case 1451:
                mensajeError = "No se puede borrar porque tiene otros registros ligados.";
                break;
            case 1452:
                mensajeError = "Uno de los ID que escribiste no existe en la base de datos.";
                break;
            case 3819:
            case 4025:
                mensajeError = "Algún dato no cumple las reglas de la base de datos (montos, fechas o valores permitidos).";
                break;
            default:
                mensajeError = "Error en la base de datos: " + ex.getMessage();
        }
    }

    public void close() {
        try {
            if (cnx != null) {
                cnx.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
