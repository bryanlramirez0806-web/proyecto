package polizas.Pagos;

import java.sql.SQLException;
import java.util.*;
import polizas.General.Conexion;
import polizas.General.DAO;

public class PagoModelo extends Conexion implements DAO {
    @Override public List read() {
        List<Pago> l = new ArrayList<>();
        try { conectar(); ps = cnx.prepareStatement("SELECT * FROM pago"); rs = ps.executeQuery();
            while(rs.next()) l.add(new Pago(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDouble(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));
        } catch(SQLException e) {} finally { close(); }
        return l;
    }
    @Override public int create(Object obj) {
        try { conectar(); Pago p = (Pago) obj;
            ps = cnx.prepareStatement("INSERT INTO pago (idPoliza, numeroParcialidad, monto, fechaLimite, fechaPago, metodoPago, comprobante, estatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, p.getIdPoliza()); ps.setInt(2, p.getNumeroParcialidad()); ps.setDouble(3, p.getMonto());
            ps.setString(4, p.getFechaLimite()); ps.setString(5, nulo(p.getFechaPago())); ps.setString(6, enumVal(p.getMetodoPago()));
            ps.setString(7, nulo(p.getComprobante())); ps.setString(8, enumVal(p.getEstatus()));
            return ps.executeUpdate();
        } catch(SQLException e) { return 0; } finally { close(); }
    }
    @Override public int update(Object obj) {
        try { conectar(); Pago p = (Pago) obj;
            ps = cnx.prepareStatement("UPDATE pago SET idPoliza = ?, numeroParcialidad = ?, monto = ?, fechaLimite = ?, fechaPago = ?, metodoPago = ?, comprobante = ?, estatus = ? WHERE idPago = ?");
            ps.setInt(1, p.getIdPoliza()); ps.setInt(2, p.getNumeroParcialidad()); ps.setDouble(3, p.getMonto());
            ps.setString(4, p.getFechaLimite()); ps.setString(5, nulo(p.getFechaPago())); ps.setString(6, enumVal(p.getMetodoPago()));
            ps.setString(7, nulo(p.getComprobante())); ps.setString(8, enumVal(p.getEstatus())); ps.setInt(9, p.getIdPago());
            return ps.executeUpdate();
        } catch(SQLException e) { return 0; } finally { close(); }
    }
    @Override public int delete(Object obj) {
        try { conectar(); Pago p = (Pago) obj;
            ps = cnx.prepareStatement("DELETE FROM pago WHERE idPago = ?"); ps.setInt(1, p.getIdPago());
            return ps.executeUpdate();
        } catch(SQLException e) { return 0; } finally { close(); }
    }
}