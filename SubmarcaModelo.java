package polizas.Submarcas;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import polizas.General.Conexion;
import polizas.General.DAO;

public class SubmarcaModelo extends Conexion implements DAO {

    public SubmarcaModelo() {
        super();
    }

    @Override
    public List read() {
        List<Submarca> lista = new ArrayList<>();
        try {
            this.conectar();
            ps = cnx.prepareStatement("SELECT * FROM submarca");
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Submarca(
                    rs.getInt("idSubmarca"),
                    rs.getInt("idMarca"),
                    rs.getString("nombre")
                ));
            }
        } catch (SQLException ex) {
            System.out.println("Error al leer submarcas: " + ex.getMessage());
        } finally {
            this.close();
        }
        return lista;
    }

    @Override
    public int create(Object obj) {
        try {
            this.conectar();
            Submarca s = (Submarca) obj;
            ps = cnx.prepareStatement("INSERT INTO submarca (idMarca, nombre) VALUES (?, ?)");
            ps.setInt(1, s.getIdMarca());
            ps.setString(2, s.getNombre());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error al crear submarca: " + ex.getMessage());
        } finally {
            this.close();
        }
        return 0;
    }

    @Override
    public int update(Object obj) {
        try {
            this.conectar();
            Submarca s = (Submarca) obj;
            ps = cnx.prepareStatement("UPDATE submarca SET idMarca = ?, nombre = ? WHERE idSubmarca = ?");
            ps.setInt(1, s.getIdMarca());
            ps.setString(2, s.getNombre());
            ps.setInt(3, s.getIdSubmarca());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error al actualizar submarca: " + ex.getMessage());
        } finally {
            this.close();
        }
        return 0;
    }

    @Override
    public int delete(Object obj) {
        try {
            this.conectar();
            Submarca s = (Submarca) obj;
            ps = cnx.prepareStatement("DELETE FROM submarca WHERE idSubmarca = ?");
            ps.setInt(1, s.getIdSubmarca());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error al eliminar submarca: " + ex.getMessage());
        } finally {
            this.close();
        }
        return 0;
    }
}