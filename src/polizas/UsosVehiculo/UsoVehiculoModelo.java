package polizas.UsosVehiculo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import polizas.General.Conexion;
import polizas.General.DAO;

public class UsoVehiculoModelo extends Conexion implements DAO {

    private static final String SELECT_BASE = "SELECT t.* FROM usovehiculo t ";

    public UsoVehiculoModelo() {
        super();
    }

    // Convierte el renglón actual del ResultSet en un objeto UsoVehiculo
    private UsoVehiculo leer() throws SQLException {
        UsoVehiculo x = new UsoVehiculo(rs.getInt("idUso"), rs.getString("nombre"));
        return x;
    }

    @Override
    public List read() {
        List<UsoVehiculo> lista = new ArrayList<>();
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

    // Busca por nombre
    public List<UsoVehiculo> buscar(String texto) {
        List<UsoVehiculo> lista = new ArrayList<>();
        try {
            this.conectar();
            if (cnx == null) return lista;
            ps = cnx.prepareStatement(SELECT_BASE + "WHERE t.nombre LIKE ? ORDER BY t.nombre");
            String patron = "%" + texto.trim() + "%";
            ps.setString(1, patron);
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
            UsoVehiculo x = (UsoVehiculo) obj;
            ps = cnx.prepareStatement("INSERT INTO usovehiculo (nombre) VALUES (?)");
            ps.setString(1, x.getNombre().trim());
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
            UsoVehiculo x = (UsoVehiculo) obj;
            ps = cnx.prepareStatement("UPDATE usovehiculo SET nombre = ? WHERE idUso = ?");
            ps.setString(1, x.getNombre().trim());
            ps.setInt(2, x.getIdUso());
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
            UsoVehiculo x = (UsoVehiculo) obj;
            ps = cnx.prepareStatement("DELETE FROM usovehiculo WHERE idUso = ?");
            ps.setInt(1, x.getIdUso());
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
