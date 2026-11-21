package polizas.Polizas;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import polizas.General.Conexion;
import polizas.General.DAO;

public class PolizaModelo extends Conexion implements DAO {

    public PolizaModelo() {
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
        List<PolizaEntidad> lista = new ArrayList<>();
        try {
            this.conectar();
            ps = cnx.prepareStatement("SELECT * FROM poliza");
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new PolizaEntidad(
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
                ));
            }
        } catch (SQLException ex) {
            System.out.println("Error al leer pólizas: " + ex.getMessage());
        } finally {
            this.close();
        }
        return lista;
    }

    @Override
    public int create(Object obj) {
        try {
            this.conectar();
            PolizaEntidad p = (PolizaEntidad) obj;
            ps = cnx.prepareStatement(
                "INSERT INTO poliza (numeroPoliza, idCotizacion, idVehiculo, idCompania, idUsuario, idTipo, idPolizaAnterior, fechaEmision, fechaInicio, fechaVencimiento, primaTotal, estatus) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            ps.setString(1, limpiarTexto(p.getNumeroPoliza()));
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
            ps.setString(8, limpiarTexto(p.getFechaEmision()));
            ps.setString(9, p.getFechaInicio());
            ps.setString(10, p.getFechaVencimiento());
            ps.setDouble(11, p.getPrimaTotal());
            ps.setString(12, normalizarEnum(p.getEstatus()));
            return ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error al crear póliza: " + ex.getMessage());
        } finally {
            this.close();
        }
        return 0;
    }

    @Override
    public int update(Object obj) {
        try {
            this.conectar();
            PolizaEntidad p = (PolizaEntidad) obj;
            ps = cnx.prepareStatement(
                "UPDATE poliza SET numeroPoliza = ?, idCotizacion = ?, idVehiculo = ?, idCompania = ?, idUsuario = ?, idTipo = ?, " +
                "idPolizaAnterior = ?, fechaEmision = ?, fechaInicio = ?, fechaVencimiento = ?, primaTotal = ?, estatus = ? " +
                "WHERE idPoliza = ?"
            );
            ps.setString(1, limpiarTexto(p.getNumeroPoliza()));
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
            ps.setString(8, limpiarTexto(p.getFechaEmision()));
            ps.setString(9, p.getFechaInicio());
            ps.setString(10, p.getFechaVencimiento());
            ps.setDouble(11, p.getPrimaTotal());
            ps.setString(12, normalizarEnum(p.getEstatus()));
            ps.setInt(13, p.getIdPoliza());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error al actualizar póliza: " + ex.getMessage());
        } finally {
            this.close();
        }
        return 0;
    }

    @Override
    public int delete(Object obj) {
        try {
            this.conectar();
            PolizaEntidad p = (PolizaEntidad) obj;
            ps = cnx.prepareStatement("DELETE FROM poliza WHERE idPoliza = ?");
            ps.setInt(1, p.getIdPoliza());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error al eliminar póliza: " + ex.getMessage());
        } finally {
            this.close();
        }
        return 0;
    }
}