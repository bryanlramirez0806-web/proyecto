package polizas.Aseguradoras;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import polizas.General.Conexion;
import polizas.General.DAO;

public class AseguradoraModelo extends Conexion implements DAO {

    private static final String SELECT_BASE = "SELECT t.* FROM aseguradora t ";

    public AseguradoraModelo() {
        super();
    }

    // Convierte el renglón actual del ResultSet en un objeto Aseguradora
    private Aseguradora leer() throws SQLException {
        Aseguradora x = new Aseguradora(rs.getInt("idAseguradora"), rs.getString("nombre"), rs.getString("rfc"), rs.getString("telefono"), rs.getString("correo"), rs.getString("nombreContacto"));
        return x;
    }

    @Override
    public List read() {
        List<Aseguradora> lista = new ArrayList<>();
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

    // Busca por nombre, rfc, nombreContacto
    public List<Aseguradora> buscar(String texto) {
        List<Aseguradora> lista = new ArrayList<>();
        try {
            this.conectar();
            if (cnx == null) return lista;
            ps = cnx.prepareStatement(SELECT_BASE + "WHERE t.nombre LIKE ? OR t.rfc LIKE ? OR t.nombreContacto LIKE ? ORDER BY t.nombre");
            String patron = "%" + texto.trim() + "%";
            ps.setString(1, patron);
            ps.setString(2, patron);
            ps.setString(3, patron);
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
            Aseguradora x = (Aseguradora) obj;
            ps = cnx.prepareStatement("INSERT INTO aseguradora (nombre, rfc, telefono, correo, nombreContacto) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, x.getNombre().trim());
            ps.setString(2, x.getRfc().trim().toUpperCase());
            ps.setString(3, nulo(x.getTelefono()));
            ps.setString(4, nulo(x.getCorreo()));
            ps.setString(5, nulo(x.getNombreContacto()));
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
            Aseguradora x = (Aseguradora) obj;
            ps = cnx.prepareStatement("UPDATE aseguradora SET nombre = ?, rfc = ?, telefono = ?, correo = ?, nombreContacto = ? WHERE idAseguradora = ?");
            ps.setString(1, x.getNombre().trim());
            ps.setString(2, x.getRfc().trim().toUpperCase());
            ps.setString(3, nulo(x.getTelefono()));
            ps.setString(4, nulo(x.getCorreo()));
            ps.setString(5, nulo(x.getNombreContacto()));
            ps.setInt(6, x.getIdAseguradora());
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
            Aseguradora x = (Aseguradora) obj;
            ps = cnx.prepareStatement("DELETE FROM aseguradora WHERE idAseguradora = ?");
            ps.setInt(1, x.getIdAseguradora());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1451) {
                mensajeError = "No se puede eliminar porque tiene pólizas registradas.";
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
