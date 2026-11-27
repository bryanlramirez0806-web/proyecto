package polizas.Modelos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import polizas.General.Conexion;
import polizas.General.DAO;

public class ModeloModelo extends Conexion implements DAO {

    private static final String SELECT_BASE = "SELECT t.*, m.nombre AS nombreMarca FROM modelo t JOIN marca m ON t.idMarca = m.idMarca ";

    public ModeloModelo() {
        super();
    }

    // Convierte el renglón actual del ResultSet en un objeto Modelo
    private Modelo leer() throws SQLException {
        Modelo x = new Modelo(rs.getInt("idModelo"), rs.getString("nombre"), rs.getInt("idMarca"));
        x.setNombreMarca(rs.getString("nombreMarca"));
        return x;
    }

    @Override
    public List read() {
        List<Modelo> lista = new ArrayList<>();
        try {
            this.conectar();
            if (cnx == null) return lista;
            ps = cnx.prepareStatement(SELECT_BASE + "ORDER BY m.nombre, t.nombre");
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

    // Busca por nombre, nombre
    public List<Modelo> buscar(String texto) {
        List<Modelo> lista = new ArrayList<>();
        try {
            this.conectar();
            if (cnx == null) return lista;
            ps = cnx.prepareStatement(SELECT_BASE + "WHERE t.nombre LIKE ? OR m.nombre LIKE ? ORDER BY m.nombre, t.nombre");
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
            Modelo x = (Modelo) obj;
            ps = cnx.prepareStatement("INSERT INTO modelo (nombre, idMarca) VALUES (?, ?)");
            ps.setString(1, x.getNombre().trim());
            ps.setInt(2, x.getIdMarca());
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
            Modelo x = (Modelo) obj;
            ps = cnx.prepareStatement("UPDATE modelo SET nombre = ?, idMarca = ? WHERE idModelo = ?");
            ps.setString(1, x.getNombre().trim());
            ps.setInt(2, x.getIdMarca());
            ps.setInt(3, x.getIdModelo());
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
            Modelo x = (Modelo) obj;
            ps = cnx.prepareStatement("DELETE FROM modelo WHERE idModelo = ?");
            ps.setInt(1, x.getIdModelo());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1451) {
                mensajeError = "No se puede eliminar porque tiene vehículos registrados.";
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
