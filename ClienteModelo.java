package polizas.Clientes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import polizas.General.Conexion;
import polizas.General.DAO;

public class ClienteModelo extends Conexion implements DAO {

    public ClienteModelo() {
        super();
    }

    private String limpiarTexto(String valor) {
        if (valor == null) {
            return null;
        }
        String limpio = valor.trim();
        return limpio.isEmpty() ? null : limpio;
    }

    private String normalizarEnum(String valor) {
        if (valor == null) {
            return null;
        }
        String limpio = valor.trim().toUpperCase();
        return limpio.isEmpty() ? null : limpio;
    }

    @Override
    public List read() {
        List<Cliente> lista = new ArrayList<>();
        try {
            this.conectar();
            ps = cnx.prepareStatement("SELECT * FROM cliente");
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Cliente(
                    rs.getInt("idCliente"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getString("rfc"),
                    rs.getString("direccion"),
                    rs.getString("medioContacto"),
                    rs.getBoolean("activo")
                ));
            }
        } catch (SQLException ex) {
            System.out.println("Error al leer clientes: " + ex.getMessage());
        } finally {
            this.close();
        }
        return lista;
    }

    @Override
    public int create(Object obj) {
        try {
            this.conectar();
            Cliente c = (Cliente) obj;
            ps = cnx.prepareStatement(
                "INSERT INTO cliente (nombre, telefono, correo, rfc, direccion, medioContacto, activo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, c.getNombre() != null ? c.getNombre().trim() : null);
            ps.setString(2, limpiarTexto(c.getTelefono()));
            ps.setString(3, limpiarTexto(c.getCorreo()));
            ps.setString(4, limpiarTexto(c.getRfc()));
            ps.setString(5, limpiarTexto(c.getDireccion()));
            ps.setString(6, normalizarEnum(c.getMedioContacto()));
            ps.setBoolean(7, true);
            return ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error al crear cliente: " + ex.getMessage());
        } finally {
            this.close();
        }
        return 0;
    }

    @Override
    public int update(Object obj) {
        try {
            this.conectar();
            Cliente c = (Cliente) obj;
            ps = cnx.prepareStatement(
                "UPDATE cliente SET nombre = ?, telefono = ?, correo = ?, rfc = ?, direccion = ?, medioContacto = ? " +
                "WHERE idCliente = ?"
            );
            ps.setString(1, c.getNombre() != null ? c.getNombre().trim() : null);
            ps.setString(2, limpiarTexto(c.getTelefono()));
            ps.setString(3, limpiarTexto(c.getCorreo()));
            ps.setString(4, limpiarTexto(c.getRfc()));
            ps.setString(5, limpiarTexto(c.getDireccion()));
            ps.setString(6, normalizarEnum(c.getMedioContacto()));
            ps.setInt(7, c.getIdCliente());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error al actualizar cliente: " + ex.getMessage());
        } finally {
            this.close();
        }
        return 0;
    }

    @Override
    public int delete(Object obj) {
        try {
            this.conectar();
            Cliente c = (Cliente) obj;
            ps = cnx.prepareStatement("DELETE FROM cliente WHERE idCliente = ?");
            ps.setInt(1, c.getIdCliente());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error al eliminar cliente: " + ex.getMessage());
        } finally {
            this.close();
        }
        return 0;
    }
}