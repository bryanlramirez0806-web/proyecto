package polizas.TiposPoliza;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import polizas.General.Conexion;
import polizas.General.DAO;

public class TipoPolizaModelo extends Conexion implements DAO {

    public TipoPolizaModelo() {
        super();
    }

    @Override
    public List read() {
        List<TipoPoliza> lista = new ArrayList<>();
        try {
            this.conectar();
            ps = cnx.prepareStatement("SELECT * FROM tipopoliza");
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new TipoPoliza(
                    rs.getInt("idTipo"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getBoolean("activo")
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
            TipoPoliza tp = (TipoPoliza) obj;
            ps = cnx.prepareStatement("INSERT INTO tipopoliza (nombre, descripcion, activo) VALUES (?, ?, ?)");
            ps.setString(1, tp.getNombre());
            ps.setString(2, tp.getDescripcion());
            ps.setBoolean(3, true);
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
            TipoPoliza tp = (TipoPoliza) obj;
            ps = cnx.prepareStatement("UPDATE tipopoliza SET nombre = ?, descripcion = ? WHERE idTipo = ?");
            ps.setString(1, tp.getNombre());
            ps.setString(2, tp.getDescripcion());
            ps.setInt(3, tp.getIdTipo());
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
            TipoPoliza tp = (TipoPoliza) obj;
            ps = cnx.prepareStatement("DELETE FROM tipopoliza WHERE idTipo = ?");
            ps.setInt(1, tp.getIdTipo());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            this.close();
        }
        return 0;
    }
}