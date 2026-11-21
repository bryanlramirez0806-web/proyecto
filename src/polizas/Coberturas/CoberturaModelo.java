package polizas.Coberturas;

import java.sql.SQLException;
import java.util.*;
import polizas.General.Conexion;
import polizas.General.DAO;

public class CoberturaModelo extends Conexion implements DAO {
    @Override public List read() {
        List<Cobertura> l = new ArrayList<>();
        try {
            conectar();
            ps = cnx.prepareStatement("SELECT * FROM cobertura");
            rs = ps.executeQuery();
            while(rs.next()) l.add(new Cobertura(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4)));
        } catch(SQLException e) { System.out.println("Error: " + e.getMessage()); }
        finally { close(); }
        return l;
    }
    @Override public int create(Object obj) {
        try {
            conectar(); Cobertura c = (Cobertura) obj;
            ps = cnx.prepareStatement("INSERT INTO cobertura (nombre, descripcion, activo) VALUES (?, ?, ?)");
            ps.setString(1, c.getNombre()); ps.setString(2, c.getDescripcion()); ps.setBoolean(3, true);
            return ps.executeUpdate();
        } catch(SQLException e) { return 0; } finally { close(); }
    }
    @Override public int update(Object obj) {
        try {
            conectar(); Cobertura c = (Cobertura) obj;
            ps = cnx.prepareStatement("UPDATE cobertura SET nombre = ?, descripcion = ? WHERE idCobertura = ?");
            ps.setString(1, c.getNombre()); ps.setString(2, c.getDescripcion()); ps.setInt(3, c.getIdCobertura());
            return ps.executeUpdate();
        } catch(SQLException e) { return 0; } finally { close(); }
    }
    @Override public int delete(Object obj) {
        try {
            conectar(); Cobertura c = (Cobertura) obj;
            ps = cnx.prepareStatement("DELETE FROM cobertura WHERE idCobertura = ?");
            ps.setInt(1, c.getIdCobertura());
            return ps.executeUpdate();
        } catch(SQLException e) { return 0; } finally { close(); }
    }
}