package polizas.Polizas;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import polizas.General.Conexion;
import polizas.General.DAO;

public class PolizaModelo extends Conexion implements DAO {

    // Consulta base: une la póliza con su vehículo, cliente, compañía y tipo para mostrar los nombres
    private static final String SELECT_BASE =
        "SELECT p.*, v.placas, c.nombre AS nombreCliente, cs.nombre AS nombreCompania, t.nombre AS nombreTipo "
        + "FROM poliza p "
        + "JOIN vehiculo v ON p.idVehiculo = v.idVehiculo "
        + "JOIN cliente c ON v.idCliente = c.idCliente "
        + "JOIN companiaseguros cs ON p.idCompania = cs.idCompania "
        + "JOIN tipopoliza t ON p.idTipo = t.idTipo ";

    public PolizaModelo() {
        super();
    }

    // Convierte el renglón actual del ResultSet en un objeto PolizaEntidad
    private PolizaEntidad leerPoliza() throws SQLException {
        PolizaEntidad p = new PolizaEntidad(
            rs.getInt("idPoliza"),
            rs.getString("numeroPoliza"),
            rs.getInt("idCotizacion"),
            rs.getInt("idVehiculo"),
            rs.getInt("idCompania"),
            rs.getInt("idUsuario"),
            rs.getInt("idTipo"),
            rs.getObject("idPolizaAnterior") != null ? rs.getInt("idPolizaAnterior") : null,
            rs.getString("fechaEmision"),
            rs.getString("fechaInicio"),
            rs.getString("fechaVencimiento"),
            rs.getDouble("primaTotal"),
            rs.getString("estatus")
        );
        p.setPlacas(rs.getString("placas"));
        p.setNombreCliente(rs.getString("nombreCliente"));
        p.setNombreCompania(rs.getString("nombreCompania"));
        p.setNombreTipo(rs.getString("nombreTipo"));
        return p;
    }

    /**
     * RF-29 / RN-9: toda póliza activa cuya fecha de vencimiento ya pasó
     * cambia a VENCIDA. Se ejecuta cada vez que se consultan las pólizas.
     */
    public int actualizarVencidas() {
        try {
            this.conectar();
            if (cnx == null) return 0;
            ps = cnx.prepareStatement("UPDATE poliza SET estatus = 'VENCIDA' WHERE estatus = 'ACTIVA' AND fechaVencimiento < CURDATE()");
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
        List<PolizaEntidad> lista = new ArrayList<>();
        try {
            this.conectar();
            if (cnx == null) return lista;
            ps = cnx.prepareStatement(SELECT_BASE + "ORDER BY p.idPoliza");
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(leerPoliza());
            }
        } catch (SQLException ex) {
            guardarError(ex);
        } finally {
            this.close();
        }
        return lista;
    }

    // RF-17: buscar por número de póliza, nombre del cliente o placas
    public List<PolizaEntidad> buscar(String texto) {
        actualizarVencidas();
        List<PolizaEntidad> lista = new ArrayList<>();
        try {
            this.conectar();
            if (cnx == null) return lista;
            ps = cnx.prepareStatement(SELECT_BASE
                + "WHERE p.numeroPoliza LIKE ? OR c.nombre LIKE ? OR v.placas LIKE ? ORDER BY p.idPoliza");
            String patron = "%" + texto.trim() + "%";
            ps.setString(1, patron);
            ps.setString(2, patron);
            ps.setString(3, patron);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(leerPoliza());
            }
        } catch (SQLException ex) {
            guardarError(ex);
        } finally {
            this.close();
        }
        return lista;
    }

    // Regresa el estatus que tiene guardado la póliza, o null si no existe
    public String estatusActual(int idPoliza) {
        return consultarTexto("SELECT estatus FROM poliza WHERE idPoliza = ?", idPoliza);
    }

    // Regresa el estatus de una cotización, o null si no existe
    public String estatusCotizacion(int idCotizacion) {
        return consultarTexto("SELECT estatus FROM cotizacion WHERE idCotizacion = ?", idCotizacion);
    }

    // RN-3: cuántas coberturas tiene la póliza
    public int contarCoberturas(int idPoliza) {
        return contar("SELECT COUNT(*) FROM detallecobertura WHERE idPoliza = ?", idPoliza);
    }

    // RN-6: cuántos pagos ya se hicieron de la póliza
    public int contarPagosRealizados(int idPoliza) {
        return contar("SELECT COUNT(*) FROM pago WHERE idPoliza = ? AND estatus = 'PAGADO'", idPoliza);
    }

    // RN-6: cuántos tipos de documento distintos entregó el dueño del vehículo (deben ser 3)
    public int contarDocumentosCliente(int idVehiculo) {
        return contar("SELECT COUNT(DISTINCT d.tipo) FROM documento d JOIN vehiculo v ON d.idCliente = v.idCliente WHERE v.idVehiculo = ?", idVehiculo);
    }

    // RN-13: la póliza tiene registrada una cancelación
    public boolean tieneCancelacion(int idPoliza) {
        return contar("SELECT COUNT(*) FROM cancelacion WHERE idPoliza = ?", idPoliza) > 0;
    }

    /**
     * RN-8: revisa si el vehículo ya tiene otra póliza activa o en trámite
     * cuyas fechas se crucen con las de esta póliza.
     */
    public boolean hayPolizaCruzada(int idVehiculo, String inicio, String vencimiento, int idPolizaActual) {
        try {
            this.conectar();
            if (cnx == null) return true;
            ps = cnx.prepareStatement(
                "SELECT COUNT(*) FROM poliza WHERE idVehiculo = ? AND idPoliza <> ? "
                + "AND estatus IN ('ACTIVA', 'EN_TRAMITE') AND fechaInicio < ? AND fechaVencimiento > ?");
            ps.setInt(1, idVehiculo);
            ps.setInt(2, idPolizaActual);
            ps.setString(3, vencimiento);
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
        if (p.getIdCotizacion() > 0) {
            ps.setInt(2, p.getIdCotizacion());
        } else {
            ps.setNull(2, java.sql.Types.INTEGER);
        }
        ps.setInt(3, p.getIdVehiculo());
        ps.setInt(4, p.getIdCompania());
        ps.setInt(5, p.getIdUsuario());
        ps.setInt(6, p.getIdTipo());
        if (p.getIdPolizaAnterior() != null && p.getIdPolizaAnterior() > 0) {
            ps.setInt(7, p.getIdPolizaAnterior());
        } else {
            ps.setNull(7, java.sql.Types.INTEGER);
        }
        ps.setString(8, nulo(p.getFechaEmision()));
        ps.setString(9, p.getFechaInicio());
        ps.setString(10, p.getFechaVencimiento());
        ps.setDouble(11, p.getPrimaTotal());
        ps.setString(12, enumVal(p.getEstatus()));
    }

    @Override
    public int create(Object obj) {
        try {
            this.conectar();
            if (cnx == null) return 0;
            PolizaEntidad p = (PolizaEntidad) obj;
            ps = cnx.prepareStatement("INSERT INTO poliza (numeroPoliza, idCotizacion, idVehiculo, idCompania, idUsuario, idTipo, idPolizaAnterior, fechaEmision, fechaInicio, fechaVencimiento, primaTotal, estatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            llenarParametros(p);
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
            ps = cnx.prepareStatement("UPDATE poliza SET numeroPoliza = ?, idCotizacion = ?, idVehiculo = ?, idCompania = ?, idUsuario = ?, idTipo = ?, idPolizaAnterior = ?, fechaEmision = ?, fechaInicio = ?, fechaVencimiento = ?, primaTotal = ?, estatus = ? WHERE idPoliza = ?");
            llenarParametros(p);
            ps.setInt(13, p.getIdPoliza());
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
            ps = cnx.prepareStatement("DELETE FROM poliza WHERE idPoliza = ? AND estatus = 'EN_TRAMITE'");
            ps.setInt(1, p.getIdPoliza());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1451) {
                mensajeError = "La póliza ya tiene coberturas, pagos o avisos registrados. Bórralos primero o regístrale una cancelación.";
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
