package polizas.Cancelaciones;

import java.sql.SQLException;
import java.util.*;
import polizas.General.Conexion;
import polizas.General.DAO;

public class CancelacionModelo extends Conexion implements DAO {
    @Override public List read() {
        List<Cancelacion> l = new ArrayList<>();
        try { conectar(); ps = cnx.prepareStatement("SELECT * FROM cancelacion"); rs = ps.executeQuery();
            while(rs.next()) l.add(new Cancelacion(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getDouble(6), rs.getString(7)));
        } catch(SQLException e) {} finally { close(); }
        return l;
    }
    @Override public int create(Object obj) {
        try { conectar(); Cancelacion x = (Cancelacion) obj;
            ps = cnx.prepareStatement("INSERT INTO cancelacion (idPoliza, idUsuario, fechaSolicitud, motivo, montoDevolucion, estatusDevolucion) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, x.getIdPoliza()); ps.setInt(2, x.getIdUsuario()); ps.setString(3, x.getFechaSolicitud());
            ps.setString(4, x.getMotivo()); ps.setDouble(5, x.getMontoDevolucion()); ps.setString(6, enumVal(x.getEstatusDevolucion()));
            return ps.executeUpdate();
        } catch(SQLException e) { return 0; } finally { close(); }
    }
    @Override public int update(Object obj) {
        try { conectar(); Cancelacion x = (Cancelacion) obj;
            ps = cnx.prepareStatement("UPDATE cancelacion SET idPoliza = ?, idUsuario = ?, fechaSolicitud = ?, motivo = ?, montoDevolucion = ?, estatusDevolucion = ? WHERE idCancelacion = ?");
            ps.setInt(1, x.getIdPoliza()); ps.setInt(2, x.getIdUsuario()); ps.setString(3, x.getFechaSolicitud());
            ps.setString(4, x.getMotivo()); ps.setDouble(5, x.getMontoDevolucion()); ps.setString(6, enumVal(x.getEstatusDevolucion())); ps.setInt(7, x.getIdCancelacion());
            return ps.executeUpdate();
        } catch(SQLException e) { return 0; } finally { close(); }
    }
    @Override public int delete(Object obj) {
        try { conectar(); Cancelacion x = (Cancelacion) obj;
            ps = cnx.prepareStatement("DELETE FROM cancelacion WHERE idCancelacion = ?"); ps.setInt(1, x.getIdCancelacion());
            return ps.executeUpdate();
        } catch(SQLException e) { return 0; } finally { close(); }
    }
}