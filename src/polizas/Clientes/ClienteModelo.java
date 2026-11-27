package polizas.Clientes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import polizas.General.Conexion;
import polizas.General.DAO;

public class ClienteModelo extends Conexion implements DAO {

    private static final String ORDEN = " ORDER BY nombre, apellidoPaterno";

    public ClienteModelo() {
        super();
    }

    // Convierte el renglón actual del ResultSet en un objeto Cliente
    private Cliente leerCliente() throws SQLException {
        return new Cliente(
            rs.getInt("idCliente"),
            rs.getString("tipoPersona"),
            rs.getString("nombre"),
            rs.getString("apellidoPaterno"),
            rs.getString("apellidoMaterno"),
            rs.getString("rfc"),
            rs.getString("curp"),
            rs.getString("telefono"),
            rs.getString("correo"),
            rs.getString("fechaRegistro"),
            rs.getBoolean("activo")
        );
    }

    // Ejecuta un SELECT ya preparado y regresa la lista de clientes
    private List<Cliente> llenarLista() throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        rs = ps.executeQuery();
        while (rs.next()) {
            lista.add(leerCliente());
        }
        return lista;
    }

    @Override
    public List read() {
        try {
            this.conectar();
            if (cnx == null) return new ArrayList<>();
            ps = cnx.prepareStatement("SELECT * FROM cliente" + ORDEN);
            return llenarLista();
        } catch (SQLException ex) {
            guardarError(ex);
            return new ArrayList<>();
        } finally {
            this.close();
        }
    }

    // Solo los clientes activos (para escogerlos en Vehículos)
    public List<Cliente> readActivos() {
        try {
            this.conectar();
            if (cnx == null) return new ArrayList<>();
            ps = cnx.prepareStatement("SELECT * FROM cliente WHERE activo = TRUE" + ORDEN);
            return llenarLista();
        } catch (SQLException ex) {
            guardarError(ex);
            return new ArrayList<>();
        } finally {
            this.close();
        }
    }

    // RF-02: buscar por nombre, apellidos, RFC, CURP o teléfono
    public List<Cliente> buscar(String texto) {
        try {
            this.conectar();
            if (cnx == null) return new ArrayList<>();
            ps = cnx.prepareStatement("SELECT * FROM cliente WHERE "
                + "CONCAT_WS(' ', nombre, apellidoPaterno, apellidoMaterno) LIKE ? "
                + "OR rfc LIKE ? OR curp LIKE ? OR telefono LIKE ?" + ORDEN);
            String patron = "%" + texto.trim() + "%";
            for (int i = 1; i <= 4; i++) {
                ps.setString(i, patron);
            }
            return llenarLista();
        } catch (SQLException ex) {
            guardarError(ex);
            return new ArrayList<>();
        } finally {
            this.close();
        }
    }

    @Override
    public int create(Object obj) {
        try {
            this.conectar();
            if (cnx == null) return 0;
            Cliente c = (Cliente) obj;
            ps = cnx.prepareStatement("INSERT INTO cliente (tipoPersona, nombre, apellidoPaterno, apellidoMaterno, rfc, curp, telefono, correo, fechaRegistro, activo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURDATE(), TRUE)");
            ps.setString(1, c.getTipoPersona());
            ps.setString(2, c.getNombre().trim());
            ps.setString(3, nulo(c.getApellidoPaterno()));
            ps.setString(4, nulo(c.getApellidoMaterno()));
            ps.setString(5, c.getRfc().trim().toUpperCase());
            ps.setString(6, enumVal(c.getCurp()));
            ps.setString(7, c.getTelefono().trim());
            ps.setString(8, nulo(c.getCorreo()));
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
            // La fecha de registro no se cambia
            ps = cnx.prepareStatement("UPDATE cliente SET tipoPersona = ?, nombre = ?, apellidoPaterno = ?, apellidoMaterno = ?, "
                + "rfc = ?, curp = ?, telefono = ?, correo = ?, activo = ? WHERE idCliente = ?");
            ps.setString(1, c.getTipoPersona());
            ps.setString(2, c.getNombre().trim());
            ps.setString(3, nulo(c.getApellidoPaterno()));
            ps.setString(4, nulo(c.getApellidoMaterno()));
            ps.setString(5, c.getRfc().trim().toUpperCase());
            ps.setString(6, enumVal(c.getCurp()));
            ps.setString(7, c.getTelefono().trim());
            ps.setString(8, nulo(c.getCorreo()));
            ps.setBoolean(9, c.isActivo());
            ps.setInt(10, c.getIdCliente());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            guardarError(ex);
        } finally {
            this.close();
        }
        return 0;
    }

    /**
     * RN-14: no se puede dar de baja a un cliente que tenga pólizas
     * activas o en trámite. Regresa true si tiene alguna.
     */
    public boolean tienePolizasVigentes(int idCliente) {
        try {
            this.conectar();
            if (cnx == null) return true;
            ps = cnx.prepareStatement(
                "SELECT COUNT(*) FROM poliza p JOIN vehiculo v ON p.idVehiculo = v.idVehiculo "
                + "WHERE v.idCliente = ? AND p.estado IN ('ACTIVA', 'EN_TRAMITE')");
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
     * para guardar el historial de sus pólizas (RN-14).
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
