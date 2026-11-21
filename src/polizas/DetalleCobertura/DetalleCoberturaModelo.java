package polizas.DetalleCobertura;

import java.sql.SQLException;
import java.util.*;
import polizas.General.Conexion;
import polizas.General.DAO;

public class DetalleCoberturaModelo extends Conexion implements DAO {
    @Override public List read() {
        List<DetalleCobertura> l = new ArrayList<>();
        try { conectar(); ps = cnx.prepareStatement("SELECT * FROM detallecobertura"); rs = ps.executeQuery();
            while(rs.next()) l.add(new DetalleCobertura(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getDouble(4)));
        } catch(SQLException e) {} finally { close(); }
        return l;
    }
    @Override public int create(Object obj) {
        try { conectar(); DetalleCobertura d = (DetalleCobertura) obj;
            ps = cnx.prepareStatement("INSERT INTO detallecobertura VALUES (?, ?, ?, ?)");
            ps.setInt(1, d.getIdPoliza()); ps.setInt(2, d.getIdCobertura()); ps.setDouble(3, d.getSumaAsegurada()); ps.setDouble(4, d.getDeducible());
            return ps.executeUpdate();
        } catch(SQLException e) { return 0; } finally { close(); }
    }
    @Override public int update(Object obj) {
        try { conectar(); DetalleCobertura d = (DetalleCobertura) obj;
            ps = cnx.prepareStatement("UPDATE detallecobertura SET sumaAsegurada = ?, deducible = ? WHERE idPoliza = ? AND idCobertura = ?");
            ps.setDouble(1, d.getSumaAsegurada()); ps.setDouble(2, d.getDeducible()); ps.setInt(3, d.getIdPoliza()); ps.setInt(4, d.getIdCobertura());
            return ps.executeUpdate();
        } catch(SQLException e) { return 0; } finally { close(); }
    }
    @Override public int delete(Object obj) {
        try { conectar(); DetalleCobertura d = (DetalleCobertura) obj;
            ps = cnx.prepareStatement("DELETE FROM detallecobertura WHERE idPoliza = ? AND idCobertura = ?");
            ps.setInt(1, d.getIdPoliza()); ps.setInt(2, d.getIdCobertura());
            return ps.executeUpdate();
        } catch(SQLException e) { return 0; } finally { close(); }
    }
}
