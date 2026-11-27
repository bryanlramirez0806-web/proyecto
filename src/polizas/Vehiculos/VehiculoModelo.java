package polizas.Vehiculos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import polizas.General.Conexion;
import polizas.General.DAO;

public class VehiculoModelo extends Conexion implements DAO {

    // Consulta base: une vehículo con su cliente, modelo, marca y uso para mostrar los nombres
    private static final String SELECT_BASE =
        "SELECT v.*, CONCAT_WS(' ', c.nombre, c.apellidoPaterno, c.apellidoMaterno) AS nombreCliente, "
        + "CONCAT(ma.nombre, ' ', mo.nombre) AS nombreModelo, u.nombre AS nombreUso "
        + "FROM vehiculo v "
        + "JOIN cliente c ON v.idCliente = c.idCliente "
        + "JOIN modelo mo ON v.idModelo = mo.idModelo "
        + "JOIN marca ma ON mo.idMarca = ma.idMarca "
        + "JOIN usovehiculo u ON v.idUso = u.idUso ";

    public VehiculoModelo() {
        super();
    }

    // Convierte el renglón actual del ResultSet en un objeto Vehiculo
    private Vehiculo leerVehiculo() throws SQLException {
        Vehiculo v = new Vehiculo(
            rs.getInt("idVehiculo"),
            rs.getInt("anio"),
            rs.getString("numSerie"),
            rs.getString("placas"),
            rs.getString("color"),
            rs.getInt("idCliente"),
            rs.getInt("idModelo"),
            rs.getInt("idUso"),
            rs.getBoolean("activo")
        );
        v.setNombreCliente(rs.getString("nombreCliente"));
        v.setNombreModelo(rs.getString("nombreModelo"));
        v.setNombreUso(rs.getString("nombreUso"));
        return v;
    }

    // Ejecuta un SELECT ya preparado y regresa la lista de vehículos
    private List<Vehiculo> llenarLista() throws SQLException {
        List<Vehiculo> lista = new ArrayList<>();
        rs = ps.executeQuery();
        while (rs.next()) {
            lista.add(leerVehiculo());
        }
        return lista;
    }

    @Override
    public List read() {
        try {
            this.conectar();
            if (cnx == null) return new ArrayList<>();
            ps = cnx.prepareStatement(SELECT_BASE + "ORDER BY v.idVehiculo");
            return llenarLista();
        } catch (SQLException ex) {
            guardarError(ex);
            return new ArrayList<>();
        } finally {
            this.close();
        }
    }

    // Solo los vehículos activos (para escogerlos en Pólizas)
    public List<Vehiculo> readActivos() {
        try {
            this.conectar();
            if (cnx == null) return new ArrayList<>();
            ps = cnx.prepareStatement(SELECT_BASE + "WHERE v.activo = TRUE ORDER BY v.placas");
            return llenarLista();
        } catch (SQLException ex) {
            guardarError(ex);
            return new ArrayList<>();
        } finally {
            this.close();
        }
    }

    // RF-07: buscar por placas, número de serie (VIN), nombre del cliente o modelo
    public List<Vehiculo> buscar(String texto) {
        try {
            this.conectar();
            if (cnx == null) return new ArrayList<>();
            ps = cnx.prepareStatement(SELECT_BASE
                + "WHERE v.placas LIKE ? OR v.numSerie LIKE ? "
                + "OR CONCAT_WS(' ', c.nombre, c.apellidoPaterno, c.apellidoMaterno) LIKE ? "
                + "OR CONCAT(ma.nombre, ' ', mo.nombre) LIKE ? ORDER BY v.idVehiculo");
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
            Vehiculo v = (Vehiculo) obj;
            ps = cnx.prepareStatement("INSERT INTO vehiculo (anio, numSerie, placas, color, idCliente, idModelo, idUso, activo) VALUES (?, ?, ?, ?, ?, ?, ?, TRUE)");
            ps.setInt(1, v.getAnio());
            ps.setString(2, v.getNumSerie().toUpperCase());
            ps.setString(3, v.getPlacas().toUpperCase());
            ps.setString(4, nulo(v.getColor()));
            ps.setInt(5, v.getIdCliente());
            ps.setInt(6, v.getIdModelo());
            ps.setInt(7, v.getIdUso());
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
            ps = cnx.prepareStatement("UPDATE vehiculo SET anio = ?, numSerie = ?, placas = ?, color = ?, idCliente = ?, idModelo = ?, idUso = ?, activo = ? WHERE idVehiculo = ?");
            ps.setInt(1, v.getAnio());
            ps.setString(2, v.getNumSerie().toUpperCase());
            ps.setString(3, v.getPlacas().toUpperCase());
            ps.setString(4, nulo(v.getColor()));
            ps.setInt(5, v.getIdCliente());
            ps.setInt(6, v.getIdModelo());
            ps.setInt(7, v.getIdUso());
            ps.setBoolean(8, v.isActivo());
            ps.setInt(9, v.getIdVehiculo());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            guardarError(ex);
        } finally {
            this.close();
        }
        return 0;
    }

    /**
     * RN-14: no se puede dar de baja un vehículo con pólizas activas o en trámite.
     * Regresa true si tiene alguna.
     */
    public boolean tienePolizasVigentes(int idVehiculo) {
        try {
            this.conectar();
            if (cnx == null) return true;
            ps = cnx.prepareStatement("SELECT COUNT(*) FROM poliza WHERE idVehiculo = ? AND estado IN ('ACTIVA', 'EN_TRAMITE')");
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
