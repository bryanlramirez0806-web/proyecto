package polizas.Documentos;

import java.sql.SQLException;
import java.util.*;
import polizas.General.Conexion;
import polizas.General.DAO;

public class DocumentoModelo extends Conexion implements DAO {
    @Override public List read() {
        List<Documento> l = new ArrayList<>();
        try { conectar(); ps = cnx.prepareStatement("SELECT * FROM documento"); rs = ps.executeQuery();
            while(rs.next()) l.add(new Documento(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        } catch(SQLException e) {} finally { close(); }
        return l;
    }
    @Override public int create(Object obj) {
        try { conectar(); Documento d = (Documento) obj;
            ps = cnx.prepareStatement("INSERT INTO documento (idCliente, tipo, fechaRecepcion, rutaArchivo) VALUES (?, ?, ?, ?)");
            ps.setInt(1, d.getIdCliente()); ps.setString(2, enumVal(d.getTipo())); ps.setString(3, d.getFechaRecepcion()); ps.setString(4, d.getRutaArchivo());
            return ps.executeUpdate();
        } catch(SQLException e) { return 0; } finally { close(); }
    }
    @Override public int update(Object obj) {
        try { conectar(); Documento d = (Documento) obj;
            ps = cnx.prepareStatement("UPDATE documento SET idCliente = ?, tipo = ?, fechaRecepcion = ?, rutaArchivo = ? WHERE idDocumento = ?");
            ps.setInt(1, d.getIdCliente()); ps.setString(2, enumVal(d.getTipo())); ps.setString(3, d.getFechaRecepcion()); ps.setString(4, d.getRutaArchivo()); ps.setInt(5, d.getIdDocumento());
            return ps.executeUpdate();
        } catch(SQLException e) { return 0; } finally { close(); }
    }
    @Override public int delete(Object obj) {
        try { conectar(); Documento d = (Documento) obj;
            ps = cnx.prepareStatement("DELETE FROM documento WHERE idDocumento = ?"); ps.setInt(1, d.getIdDocumento());
            return ps.executeUpdate();
        } catch(SQLException e) { return 0; } finally { close(); }
    }
}
