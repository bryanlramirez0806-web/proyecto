package polizas.Notificaciones;

import java.sql.SQLException;
import java.util.*;
import polizas.General.Conexion;
import polizas.General.DAO;

public class NotificacionModelo extends Conexion implements DAO {
    @Override public List read() {
        List<Notificacion> l = new ArrayList<>();
        try { conectar(); ps = cnx.prepareStatement("SELECT * FROM notificacion"); rs = ps.executeQuery();
            while(rs.next()) l.add(new Notificacion(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getDouble(7), rs.getBoolean(8)));
        } catch(SQLException e) {} finally { close(); }
        return l;
    }
    @Override public int create(Object obj) {
        try { conectar(); Notificacion n = (Notificacion) obj;
            ps = cnx.prepareStatement("INSERT INTO notificacion (idPoliza, idUsuario, tipo, fecha, medio, costoRenovacion, realizada) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, n.getIdPoliza()); ps.setInt(2, n.getIdUsuario()); ps.setString(3, enumVal(n.getTipo()));
            ps.setString(4, n.getFecha()); ps.setString(5, enumVal(n.getMedio())); ps.setDouble(6, n.getCostoRenovacion()); ps.setBoolean(7, true);
            return ps.executeUpdate();
        } catch(SQLException e) { return 0; } finally { close(); }
    }
    @Override public int update(Object obj) {
        try { conectar(); Notificacion n = (Notificacion) obj;
            ps = cnx.prepareStatement("UPDATE notificacion SET idPoliza = ?, idUsuario = ?, tipo = ?, fecha = ?, medio = ?, costoRenovacion = ? WHERE idNotificacion = ?");
            ps.setInt(1, n.getIdPoliza()); ps.setInt(2, n.getIdUsuario()); ps.setString(3, enumVal(n.getTipo()));
            ps.setString(4, n.getFecha()); ps.setString(5, enumVal(n.getMedio())); ps.setDouble(6, n.getCostoRenovacion()); ps.setInt(7, n.getIdNotificacion());
            return ps.executeUpdate();
        } catch(SQLException e) { return 0; } finally { close(); }
    }
    @Override public int delete(Object obj) {
        try { conectar(); Notificacion n = (Notificacion) obj;
            ps = cnx.prepareStatement("DELETE FROM notificacion WHERE idNotificacion = ?"); ps.setInt(1, n.getIdNotificacion());
            return ps.executeUpdate();
        } catch(SQLException e) { return 0; } finally { close(); }
    }
}