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

    // Convierte el renglón actual del ResultSet en un objeto Cliente
    private Cliente leerCliente() throws SQLException {
        return new Cliente(
            rs.getInt("idCliente"),
            rs.getString("nombre"),
            rs.getString("telefono"),
            rs.getString("correo"),
            rs.getString("rfc"),
            rs.getString("direccion"),
            rs.getString("medioContacto"),
            rs.getBoolean("activo")
        );
    }

    @Override
    public List read() {
        List<Cliente> lista = new ArrayList<>();
        try {
            this.conectar();
            if (cnx == null) return lista;
            ps = cnx.prepareStatement("SELECT * FROM cliente ORDER BY nombre");
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(leerCliente());
            }
        } catch (SQLException ex) {
            guardarError(ex);
        } finally {
            this.close();
        }
        return lista;
    }

    // Solo los clientes activos (para escogerlos en Vehículos)
    public List<Cliente> readActivos() {
        List<Cliente> lista = new ArrayList<>();
        try {
            this.conectar();
            if (cnx == null) return lista;
            ps = cnx.prepareStatement("SELECT * FROM cliente WHERE activo = TRUE ORDER BY nombre");
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(leerCliente());
            }
        } catch (SQLException ex) {
            guardarError(ex);
        } finally {
            this.close();
        }
        return lista;
    }

    // RF-02: buscar por nombre, RFC o teléfono
    public List<Cliente> buscar(String texto) {
        List<Cliente> lista = new ArrayList<>();
        try {
            this.conectar();
            if (cnx == null) return lista;
            ps = cnx.prepareStatement("SELECT * FROM cliente WHERE nombre LIKE ? OR rfc LIKE ? OR telefono LIKE ? ORDER BY nombre");
            String patron = "%" + texto.trim() + "%";
            ps.setString(1, patron);
            ps.setString(2, patron);
            ps.setString(3, patron);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(leerCliente());
            }
        } catch (SQLException ex) {
            guardarError(ex);
        } finally {
            this.close();
        }
        return lista;
    }

    @Override
    public int create(Object obj) {
        try {
            this.conectar();
            if (cnx == null) return 0;
            Cliente c = (Cliente) obj;
            ps = cnx.prepareStatement("INSERT INTO cliente (nombre, telefono, correo, rfc, direccion, medioContacto, activo) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, c.getNombre().trim());
            ps.setString(2, c.getTelefono().trim());
            ps.setString(3, nulo(c.getCorreo()));
            ps.setString(4, c.getRfc().trim().toUpperCase());
            ps.setString(5, nulo(c.getDireccion()));
            ps.setString(6, enumVal(c.getMedioContacto()));
            ps.setBoolean(7, true);
            return ps.executeUpdate();
        } catch (SQLException ex) {
            guardarError(ex);
        } finally {
            this.close();
        }
        return 0;
    }

    @Override
    public int update(Object obj) {
        try {
            this.conectar();
            if (cnx == null) return 0;
            Cliente c = (Cliente) obj;
            ps = cnx.prepareStatement("UPDATE cliente SET nombre = ?, telefono = ?, correo = ?, rfc = ?, direccion = ?, medioContacto = ?, activo = ? WHERE idCliente = ?");
            ps.setString(1, c.getNombre().trim());
            ps.setString(2, c.getTelefono().trim());
            ps.setString(3, nulo(c.getCorreo()));
            ps.setString(4, c.getRfc().trim().toUpperCase());
            ps.setString(5, nulo(c.getDireccion()));
            ps.setString(6, enumVal(c.getMedioContacto()));
            ps.setBoolean(7, c.isActivo());
            ps.setInt(8, c.getIdCliente());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            guardarError(ex);
        } finally {
            this.close();
        }
        return 0;
    }

    /**
     * RN-14: no se puede dar de baja a un cliente que tenga pólizas activas
     * o en trámite, o pagos pendientes. Regresa true si tiene alguno.
     */
    public boolean tienePendientes(int idCliente) {
        try {
            this.conectar();
            if (cnx == null) return true;
            ps = cnx.prepareStatement(
                "SELECT COUNT(*) FROM poliza p JOIN vehiculo v ON p.idVehiculo = v.idVehiculo "
                + "WHERE v.idCliente = ? AND (p.estatus IN ('ACTIVA', 'EN_TRAMITE') "
                + "OR EXISTS (SELECT 1 FROM pago g WHERE g.idPoliza = p.idPoliza AND g.estatus = 'PENDIENTE'))");
            ps.setInt(1, idCliente);
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException ex) {
            guardarError(ex);
            return true;
        } finally {
            this.close();
        }
    }

    /**
     * Baja del cliente (RF-04). No se borra el registro, solo se desactiva
     * para guardar el historial (RN-14).
     */
    @Override
    public int delete(Object obj) {
        try {
            this.conectar();
            if (cnx == null) return 0;
            Cliente c = (Cliente) obj;
            ps = cnx.prepareStatement("UPDATE cliente SET activo = FALSE WHERE idCliente = ?");
            ps.setInt(1, c.getIdCliente());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            guardarError(ex);
        } finally {
            this.close();
        }
        return 0;
    }
}
