package polizas.Cotizaciones;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import polizas.General.Conexion;
import polizas.General.DAO;

public class CotizacionModelo extends Conexion implements DAO {

    public CotizacionModelo() {
        super();
    }

    @Override
    public List read() {
        List<Cotizacion> lista = new ArrayList<>();
        try {
            this.conectar();
            ps = cnx.prepareStatement("SELECT * FROM cotizacion");
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Cotizacion(
                    rs.getInt("idCotizacion"),
                    rs.getInt("idVehiculo"),
                    rs.getInt("idUsuario"),
                    rs.getInt("idCompania"),
                    rs.getInt("idTipo"),
                    rs.getString("fecha"),
                    rs.getDouble("costo"),
                    rs.getString("opcionPago"),
                    rs.getString("estatus")
                ));
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            this.close();
        }
        return lista;
    }

    @Override
    public int create(Object obj) {
        try {
            this.conectar();
            Cotizacion c = (Cotizacion) obj;
            ps = cnx.prepareStatement("INSERT INTO cotizacion (idVehiculo, idUsuario, idCompania, idTipo, fecha, costo, opcionPago, estatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, c.getIdVehiculo());
            ps.setInt(2, c.getIdUsuario());
            ps.setInt(3, c.getIdCompania());
            ps.setInt(4, c.getIdTipo());
            ps.setString(5, c.getFecha());
            ps.setDouble(6, c.getCosto());
            ps.setString(7, enumVal(c.getOpcionesPago()));
            ps.setString(8, enumVal(c.getEstatus()));
            return ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            this.close();
        }
        return 0;
    }

    @Override
    public int update(Object obj) {
        try {
            this.conectar();
            Cotizacion c = (Cotizacion) obj;
            ps = cnx.prepareStatement("UPDATE cotizacion SET idVehiculo = ?, idUsuario = ?, idCompania = ?, idTipo = ?, fecha = ?, costo = ?, opcionPago = ?, estatus = ? WHERE idCotizacion = ?");
            ps.setInt(1, c.getIdVehiculo());
            ps.setInt(2, c.getIdUsuario());
            ps.setInt(3, c.getIdCompania());
            ps.setInt(4, c.getIdTipo());
            ps.setString(5, c.getFecha());
            ps.setDouble(6, c.getCosto());
            ps.setString(7, enumVal(c.getOpcionesPago()));
            ps.setString(8, enumVal(c.getEstatus()));
            ps.setInt(9, c.getIdCotizacion());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            this.close();
        }
        return 0;
    }

    @Override
    public int delete(Object obj) {
        try {
            this.conectar();
            Cotizacion c = (Cotizacion) obj;
            ps = cnx.prepareStatement("DELETE FROM cotizacion WHERE idCotizacion = ?");
            ps.setInt(1, c.getIdCotizacion());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            this.close();
        }
        return 0;
    }
}