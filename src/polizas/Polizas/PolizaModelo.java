package polizas.Polizas;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import polizas.General.Conexion;
import polizas.General.DAO;

public class PolizaModelo extends Conexion implements DAO {

    // Consulta base: une la póliza con su vehículo, cliente, aseguradora y forma de pago para mostrar los nombres
    private static final String SELECT_BASE =
        "SELECT p.*, v.placas, CONCAT_WS(' ', c.nombre, c.apellidoPaterno, c.apellidoMaterno) AS nombreCliente, "
        + "a.nombre AS nombreAseguradora, f.nombre AS nombreFormaPago "
        + "FROM poliza p "
        + "JOIN vehiculo v ON p.idVehiculo = v.idVehiculo "
        + "JOIN cliente c ON v.idCliente = c.idCliente "
        + "JOIN aseguradora a ON p.idAseguradora = a.idAseguradora "
        + "JOIN formapago f ON p.idFormaPago = f.idFormaPago ";

    public PolizaModelo() {
        super();
    }

    // Convierte el renglón actual del ResultSet en un objeto PolizaEntidad
    private PolizaEntidad leerPoliza() throws SQLException {
        PolizaEntidad p = new PolizaEntidad(
            rs.getInt("idPoliza"),
            rs.getString("numeroPoliza"),
            rs.getString("fechaInicio"),
            rs.getString("fechaFin"),
            rs.getDouble("primaTotal"),
            rs.getString("estado"),
            rs.getInt("idVehiculo"),
            rs.getInt("idAseguradora"),
            rs.getInt("idFormaPago"),
            rs.getObject("idPolizaAnterior") != null ? rs.getInt("idPolizaAnterior") : null
        );
        p.setPlacas(rs.getString("placas"));
        p.setNombreCliente(rs.getString("nombreCliente"));
        p.setNombreAseguradora(rs.getString("nombreAseguradora"));
        p.setNombreFormaPago(rs.getString("nombreFormaPago"));
        return p;
    }

    // Ejecuta un SELECT ya preparado y regresa la lista de pólizas
    private List<PolizaEntidad> llenarLista() throws SQLException {
        List<PolizaEntidad> lista = new ArrayList<>();
        rs = ps.executeQuery();
        while (rs.next()) {
            lista.add(leerPoliza());
        }
        return lista;
    }

    /**
     * RF-29 / RN-9: toda póliza activa cuya fecha de fin ya pasó
     * cambia a VENCIDA. Se ejecuta cada vez que se consultan las pólizas.
     */
    public int actualizarVencidas() {
        try {
            this.conectar();
            if (cnx == null) return 0;
            ps = cnx.prepareStatement("UPDATE poliza SET estado = 'VENCIDA' WHERE estado = 'ACTIVA' AND fechaFin < CURDATE()");
            return ps.executeUpdate();
        } catch (SQLException ex) {
            guardarError(ex);
        } finally {
            this.close();
        }
        return 0;
    }

    @Override
    public List read() {
        actualizarVencidas();
        try {
            this.conectar();
            if (cnx == null) return new ArrayList<>();
            ps = cnx.prepareStatement(SELECT_BASE + "ORDER BY p.idPoliza");
            return llenarLista();
        } catch (SQLException ex) {
            guardarError(ex);
            return new ArrayList<>();
        } finally {
            this.close();
        }
    }

    // RF-17: buscar por número de póliza, nombre del cliente o placas
    public List<PolizaEntidad> buscar(String texto) {
        actualizarVencidas();
        try {
            this.conectar();
            if (cnx == null) return new ArrayList<>();
            ps = cnx.prepareStatement(SELECT_BASE
                + "WHERE p.numeroPoliza LIKE ? OR CONCAT_WS(' ', c.nombre, c.apellidoPaterno, c.apellidoMaterno) LIKE ? "
                + "OR v.placas LIKE ? ORDER BY p.idPoliza");
            String patron = "%" + texto.trim() + "%";
            ps.setString(1, patron);
            ps.setString(2, patron);
            ps.setString(3, patron);
            return llenarLista();
        } catch (SQLException ex) {
            guardarError(ex);
            return new ArrayList<>();
        } finally {
            this.close();
        }
    }

    // Regresa el estado que tiene guardado la póliza, o null si no existe
    public String estadoActual(int idPoliza) {
        return consultarTexto("SELECT estado FROM poliza WHERE idPoliza = ?", idPoliza);
    }

    // Regresa el vehículo de una póliza (0 si no existe). Se usa para revisar la póliza anterior
    public int vehiculoDePoliza(int idPoliza) {
        return contar("SELECT COALESCE(MAX(idVehiculo), 0) FROM poliza WHERE idPoliza = ?", idPoliza);
    }

    // RN-3: cuántas coberturas tiene la póliza (tabla polizacobertura)
    public int contarCoberturas(int idPoliza) {
        return contar("SELECT COUNT(*) FROM polizacobertura WHERE idPoliza = ?", idPoliza);
    }

    // RN-6: cuántos pagos válidos (no anulados) tiene la póliza
    public int contarPagosValidos(int idPoliza) {
        return contar("SELECT COUNT(*) FROM pago WHERE idPoliza = ? AND anulado = FALSE", idPoliza);
    }

    // RN-13: la póliza tiene una solicitud de cancelación aprobada
    public boolean tieneCancelacionAprobada(int idPoliza) {
        return contar("SELECT COUNT(*) FROM solicitud s JOIN tiposolicitud t ON s.idTipoSolicitud = t.idTipoSolicitud "
            + "WHERE s.idPoliza = ? AND t.nombre = 'Cancelación' AND s.estado = 'APROBADA'", idPoliza) > 0;
    }

    /**
     * RN-8: revisa si el vehículo ya tiene otra póliza activa o en trámite
     * cuyas fechas se crucen con las de esta póliza.
     */
    public boolean hayPolizaCruzada(int idVehiculo, String inicio, String fin, int idPolizaActual) {
        try {
            this.conectar();
            if (cnx == null) return true;
            ps = cnx.prepareStatement(
                "SELECT COUNT(*) FROM poliza WHERE idVehiculo = ? AND idPoliza <> ? "
                + "AND estado IN ('ACTIVA', 'EN_TRAMITE') AND fechaInicio < ? AND fechaFin > ?");
            ps.setInt(1, idVehiculo);
            ps.setInt(2, idPolizaActual);
            ps.setString(3, fin);
            ps.setString(4, inicio);
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

    // Ayuda para las consultas que regresan un número
    private int contar(String sql, int id) {
        try {
            this.conectar();
            if (cnx == null) return 0;
            ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException ex) {
            guardarError(ex);
            return 0;
        } finally {
            this.close();
        }
    }

    // Ayuda para las consultas que regresan un texto
    private String consultarTexto(String sql, int id) {
        try {
            this.conectar();
            if (cnx == null) return null;
            ps = cnx.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            return rs.next() ? rs.getString(1) : null;
        } catch (SQLException ex) {
            guardarError(ex);
            return null;
        } finally {
            this.close();
        }
    }

    // Pasa los datos de la póliza al PreparedStatement (los usan create y update)
    private void llenarParametros(PolizaEntidad p) throws SQLException {
        ps.setString(1, nulo(p.getNumeroPoliza()));
        ps.setString(2, p.getFechaInicio());
        ps.setString(3, p.getFechaFin());
        ps.setDouble(4, p.getPrimaTotal());
        ps.setString(5, enumVal(p.getEstado()));
        ps.setInt(6, p.getIdVehiculo());
        ps.setInt(7, p.getIdAseguradora());
        ps.setInt(8, p.getIdFormaPago());
        if (p.getIdPolizaAnterior() != null) {
            ps.setInt(9, p.getIdPolizaAnterior());
        } else {
            ps.setNull(9, java.sql.Types.INTEGER);
        }
    }

    @Override
    public int create(Object obj) {
        try {
            this.conectar();
            if (cnx == null) return 0;
            ps = cnx.prepareStatement("INSERT INTO poliza (numeroPoliza, fechaInicio, fechaFin, primaTotal, estado, idVehiculo, idAseguradora, idFormaPago, idPolizaAnterior) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            llenarParametros((PolizaEntidad) obj);
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
            PolizaEntidad p = (PolizaEntidad) obj;
            ps = cnx.prepareStatement("UPDATE poliza SET numeroPoliza = ?, fechaInicio = ?, fechaFin = ?, primaTotal = ?, estado = ?, idVehiculo = ?, idAseguradora = ?, idFormaPago = ?, idPolizaAnterior = ? WHERE idPoliza = ?");
            llenarParametros(p);
            ps.setInt(10, p.getIdPoliza());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            guardarError(ex);
        } finally {
            this.close();
        }
        return 0;
    }

    /**
     * Elimina una póliza. Por la RN-14 solo se permite con pólizas EN_TRAMITE
     * (la condición va en el WHERE para que la base tampoco lo permita).
     */
    @Override
    public int delete(Object obj) {
        try {
            this.conectar();
            if (cnx == null) return 0;
            PolizaEntidad p = (PolizaEntidad) obj;
            ps = cnx.prepareStatement("DELETE FROM poliza WHERE idPoliza = ? AND estado = 'EN_TRAMITE'");
            ps.setInt(1, p.getIdPoliza());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1451) {
                mensajeError = "La póliza ya tiene coberturas, pagos o solicitudes registradas, por eso no se puede borrar.";
                System.out.println("Error: " + ex.getMessage());
            } else {
                guardarError(ex);
            }
        } finally {
            this.close();
        }
        return 0;
    }
}
