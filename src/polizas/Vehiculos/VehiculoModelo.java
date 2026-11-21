package polizas.Vehiculos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import polizas.General.Conexion;
import polizas.General.DAO;

public class VehiculoModelo extends Conexion implements DAO {

    // Consulta base: une vehículo con su cliente, submarca y marca para mostrar los nombres
    private static final String SELECT_BASE =
        "SELECT v.*, c.nombre AS nombreCliente, CONCAT(m.nombre, ' ', s.nombre) AS nombreSubmarca "
        + "FROM vehiculo v "
        + "JOIN cliente c ON v.idCliente = c.idCliente "
        + "JOIN submarca s ON v.idSubmarca = s.idSubmarca "
        + "JOIN marca m ON s.idMarca = m.idMarca ";

    public VehiculoModelo() {
        super();
    }

    // Convierte el renglón actual del ResultSet en un objeto Vehiculo
    private Vehiculo leerVehiculo() throws SQLException {
        Vehiculo v = new Vehiculo(
            rs.getInt("idVehiculo"),
            rs.getInt("idCliente"),
            rs.getInt("idSubmarca"),
            rs.getString("modelo"),
            rs.getString("tipoVehiculo"),
            rs.getString("numeroSerie"),
            rs.getString("placas"),
            rs.getBoolean("activo")
        );
        v.setNombreCliente(rs.getString("nombreCliente"));
        v.setNombreSubmarca(rs.getString("nombreSubmarca"));
        return v;
    }

    @Override
    public List read() {
        List<Vehiculo> lista = new ArrayList<>();
        try {
            this.conectar();
            if (cnx == null) return lista;
            ps = cnx.prepareStatement(SELECT_BASE + "ORDER BY v.idVehiculo");
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(leerVehiculo());
            }
        } catch (SQLException ex) {
            guardarError(ex);
        } finally {
            this.close();
        }
        return lista;
    }

    // Solo los vehículos activos (para escogerlos en Pólizas)
    public List<Vehiculo> readActivos() {
        List<Vehiculo> lista = new ArrayList<>();
        try {
            this.conectar();
            if (cnx == null) return lista;
            ps = cnx.prepareStatement(SELECT_BASE + "WHERE v.activo = TRUE ORDER BY v.placas");
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(leerVehiculo());
            }
        } catch (SQLException ex) {
            guardarError(ex);
        } finally {
            this.close();
        }
        return lista;
    }

    // RF-07: buscar por placas, número de serie (VIN) o nombre del cliente
    public List<Vehiculo> buscar(String texto) {
        List<Vehiculo> lista = new ArrayList<>();
        try {
            this.conectar();
            if (cnx == null) return lista;
            ps = cnx.prepareStatement(SELECT_BASE
                + "WHERE v.placas LIKE ? OR v.numeroSerie LIKE ? OR c.nombre LIKE ? ORDER BY v.idVehiculo");
            String patron = "%" + texto.trim() + "%";
            ps.setString(1, patron);
            ps.setString(2, patron);
            ps.setString(3, patron);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(leerVehiculo());
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
            Vehiculo v = (Vehiculo) obj;
            ps = cnx.prepareStatement("INSERT INTO vehiculo (idCliente, idSubmarca, modelo, tipoVehiculo, numeroSerie, placas, activo) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, v.getIdCliente());
            ps.setInt(2, v.getIdSubmarca());
            ps.setString(3, v.getModelo());
            ps.setString(4, v.getTipoVehiculo());
            ps.setString(5, v.getNumeroSerie().toUpperCase());
            ps.setString(6, v.getPlacas().toUpperCase());
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
            Vehiculo v = (Vehiculo) obj;
            ps = cnx.prepareStatement("UPDATE vehiculo SET idCliente = ?, idSubmarca = ?, modelo = ?, tipoVehiculo = ?, numeroSerie = ?, placas = ?, activo = ? WHERE idVehiculo = ?");
            ps.setInt(1, v.getIdCliente());
            ps.setInt(2, v.getIdSubmarca());
            ps.setString(3, v.getModelo());
            ps.setString(4, v.getTipoVehiculo());
            ps.setString(5, v.getNumeroSerie().toUpperCase());
            ps.setString(6, v.getPlacas().toUpperCase());
            ps.setBoolean(7, v.isActivo());
            ps.setInt(8, v.getIdVehiculo());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            guardarError(ex);
        } finally {
            this.close();
        }
        return 0;
    }

    /**
     * RN-14: no se puede dar de baja un vehículo con pólizas activas o en trámite,
     * o con pagos pendientes. Regresa true si tiene alguno.
     */
    public boolean tienePendientes(int idVehiculo) {
        try {
            this.conectar();
            if (cnx == null) return true;
            ps = cnx.prepareStatement(
                "SELECT COUNT(*) FROM poliza p WHERE p.idVehiculo = ? AND (p.estatus IN ('ACTIVA', 'EN_TRAMITE') "
                + "OR EXISTS (SELECT 1 FROM pago g WHERE g.idPoliza = p.idPoliza AND g.estatus = 'PENDIENTE'))");
            ps.setInt(1, idVehiculo);
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
     * Baja del vehículo (RF-09). No se borra, solo se desactiva (RN-14).
     */
    @Override
    public int delete(Object obj) {
        try {
            this.conectar();
            if (cnx == null) return 0;
            Vehiculo v = (Vehiculo) obj;
            ps = cnx.prepareStatement("UPDATE vehiculo SET activo = FALSE WHERE idVehiculo = ?");
            ps.setInt(1, v.getIdVehiculo());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            guardarError(ex);
        } finally {
            this.close();
        }
        return 0;
    }
}
