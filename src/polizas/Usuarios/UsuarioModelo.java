package polizas.Usuarios;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import polizas.General.Conexion;
import polizas.General.DAO;

public class UsuarioModelo extends Conexion implements DAO {

    public UsuarioModelo() {
        super();
    }

    @Override
    public List read() {
        List<Usuario> lista = new ArrayList<>();
        try {
            this.conectar();
            ps = cnx.prepareStatement("SELECT * FROM usuario");
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Usuario(
                    rs.getInt("idUsuario"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getString("rol"),
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
            Usuario u = (Usuario) obj;
            ps = cnx.prepareStatement("INSERT INTO usuario (nombre, telefono, correo, rol, activo) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getTelefono());
            ps.setString(3, u.getCorreo());
            ps.setString(4, enumVal(u.getRol()));
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
            Usuario u = (Usuario) obj;
            ps = cnx.prepareStatement("UPDATE usuario SET nombre = ?, telefono = ?, correo = ?, rol = ? WHERE idUsuario = ?");
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getTelefono());
            ps.setString(3, u.getCorreo());
            ps.setString(4, enumVal(u.getRol()));
            ps.setInt(5, u.getIdUsuario());
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
            Usuario u = (Usuario) obj;
            ps = cnx.prepareStatement("DELETE FROM usuario WHERE idUsuario = ?");
            ps.setInt(1, u.getIdUsuario());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            this.close();
        }
        return 0;
    }
}