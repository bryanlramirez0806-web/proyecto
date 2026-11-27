package polizas.Coberturas;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import polizas.General.Conexion;
import polizas.General.DAO;

public class CoberturaModelo extends Conexion implements DAO {

    private static final String SELECT_BASE = "SELECT t.* FROM cobertura t ";

    public CoberturaModelo() {
        super();
    }

    // Convierte el renglón actual del ResultSet en un objeto Cobertura
    private Cobertura leer() throws SQLException {
        Cobertura x = new Cobertura(rs.getInt("idCobertura"), rs.getString("nombre"), rs.getString("descripcion"));
        return x;
    }

    @Override
    public List read() {
        List<Cobertura> lista = new ArrayList<>();
        try {
            this.conectar();
            if (cnx == null) return lista;
            ps = cnx.prepareStatement(SELECT_BASE + "ORDER BY t.nombre");
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(leer());
            }
        } catch (SQLException ex) {
            guardarError(ex);
        } finally {
            this.close();
        }
        return lista;
    }

    // Busca por nombre, descripcion
    public List<Cobertura> buscar(String texto) {
        List<Cobertura> lista = new ArrayList<>();
        try {
            this.conectar();
            if (cnx == null) return lista;
            ps = cnx.prepareStatement(SELECT_BASE + "WHERE t.nombre LIKE ? OR t.descripcion LIKE ? ORDER BY t.nombre");
            String patron = "%" + texto.trim() + "%";
            ps.setString(1, patron);
            ps.setString(2, patron);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(leer());
            }
        } catch (SQLException ex) {
            guardarError(ex);
        } finally {
            this.close();
        }
        return lista;
    }

    @Override
    public int create(Object obj) {
        try {
            this.conectar();
            if (cnx == null) return 0;
            Cobertura x = (Cobertura) obj;
            ps = cnx.prepareStatement("INSERT INTO cobertura (nombre, descripcion) VALUES (?, ?)");
            ps.setString(1, x.getNombre().trim());
            ps.setString(2, nulo(x.getDescripcion()));
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
            Cobertura x = (Cobertura) obj;
            ps = cnx.prepareStatement("UPDATE cobertura SET nombre = ?, descripcion = ? WHERE idCobertura = ?");
            ps.setString(1, x.getNombre().trim());
            ps.setString(2, nulo(x.getDescripcion()));
            ps.setInt(3, x.getIdCobertura());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            guardarError(ex);
        } finally {
            this.close();
        }
        return 0;
    }

    @Override
    public int delete(Object obj) {
        try {
            this.conectar();
            if (cnx == null) return 0;
            Cobertura x = (Cobertura) obj;
            ps = cnx.prepareStatement("DELETE FROM cobertura WHERE idCobertura = ?");
            ps.setInt(1, x.getIdCobertura());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1451) {
                mensajeError = "No se puede eliminar porque está asignada a pólizas.";
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
