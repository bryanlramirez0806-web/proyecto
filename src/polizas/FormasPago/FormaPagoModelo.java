package polizas.FormasPago;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import polizas.General.Conexion;
import polizas.General.DAO;

public class FormaPagoModelo extends Conexion implements DAO {

    private static final String SELECT_BASE = "SELECT t.* FROM formapago t ";

    public FormaPagoModelo() {
        super();
    }

    // Convierte el renglón actual del ResultSet en un objeto FormaPago
    private FormaPago leer() throws SQLException {
        FormaPago x = new FormaPago(rs.getInt("idFormaPago"), rs.getString("nombre"), rs.getInt("numeroPagos"));
        return x;
    }

    @Override
    public List read() {
        List<FormaPago> lista = new ArrayList<>();
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
    public List<FormaPago> buscar(String texto) {
        List<FormaPago> lista = new ArrayList<>();
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
            FormaPago x = (FormaPago) obj;
            ps = cnx.prepareStatement("INSERT INTO formapago (nombre, numeroPagos) VALUES (?, ?)");
            ps.setString(1, x.getNombre().trim());
            ps.setInt(2, x.getNumeroPagos());
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
            FormaPago x = (FormaPago) obj;
            ps = cnx.prepareStatement("UPDATE formapago SET nombre = ?, numeroPagos = ? WHERE idFormaPago = ?");
            ps.setString(1, x.getNombre().trim());
            ps.setInt(2, x.getNumeroPagos());
            ps.setInt(3, x.getIdFormaPago());
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
            FormaPago x = (FormaPago) obj;
            ps = cnx.prepareStatement("DELETE FROM formapago WHERE idFormaPago = ?");
            ps.setInt(1, x.getIdFormaPago());
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
