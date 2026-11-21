package polizas.Companias;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import polizas.General.Conexion;
import polizas.General.DAO;

public class CompaniaModelo extends Conexion implements DAO {

    public CompaniaModelo() {
        super();
    }

    @Override
    public List read() {
        List<Compania> lista = new ArrayList<>();
        try {
            this.conectar();
            ps = cnx.prepareStatement("SELECT * FROM companiaseguros");
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Compania(
                    rs.getInt("idCompania"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getString("sitioValidacion"),
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
            Compania c = (Compania) obj;
            ps = cnx.prepareStatement("INSERT INTO companiaseguros (nombre, telefono, correo, sitioValidacion, activo) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getTelefono());
            ps.setString(3, c.getCorreo());
            ps.setString(4, c.getSitioValidacion());
            ps.setBoolean(5, true);
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
            Compania c = (Compania) obj;
            ps = cnx.prepareStatement("UPDATE companiaseguros SET nombre = ?, telefono = ?, correo = ?, sitioValidacion = ? WHERE idCompania = ?");
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getTelefono());
            ps.setString(3, c.getCorreo());
            ps.setString(4, c.getSitioValidacion());
            ps.setInt(5, c.getIdCompania());
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
            Compania c = (Compania) obj;
            ps = cnx.prepareStatement("DELETE FROM companiaseguros WHERE idCompania = ?");
            ps.setInt(1, c.getIdCompania());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            this.close();
        }
        return 0;
    }
}