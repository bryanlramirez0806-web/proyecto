package polizas.Notificaciones;

import java.util.*;
import javax.swing.table.AbstractTableModel;

public class NotificacionTM extends AbstractTableModel {
    protected List<Notificacion> lista = new ArrayList<>();
    protected String[] encabezados = {"ID", "Póliza", "Usuario", "Tipo", "Fecha", "Medio", "Costo Ren.", "Realizada"};

    @Override public String getColumnName(int c) { return encabezados[c]; }
    @Override public int getRowCount() { return lista.size(); }
    @Override public int getColumnCount() { return encabezados.length; }
    @Override public Object getValueAt(int r, int c) {
        Notificacion n = lista.get(r);
        switch(c) {
            case 0: return n.getIdNotificacion(); case 1: return n.getIdPoliza(); case 2: return n.getIdUsuario();
            case 3: return n.getTipo(); case 4: return n.getFecha(); case 5: return n.getMedio();
            case 6: return n.getCostoRenovacion(); case 7: return n.isRealizada(); default: return null;
        }
    }
    public void vaciarLista() { lista.clear(); }
    public void llenar(List<Notificacion> l) { this.lista = l; }
}