package polizas.Cancelaciones;

import java.util.*;
import javax.swing.table.AbstractTableModel;

public class CancelacionTM extends AbstractTableModel {
    protected List<Cancelacion> lista = new ArrayList<>();
    protected String[] encabezados = {"ID", "Póliza", "Usuario", "F. Solicitud", "Motivo", "Monto Dev.", "Estatus Dev."};

    @Override public String getColumnName(int c) { return encabezados[c]; }
    @Override public int getRowCount() { return lista.size(); }
    @Override public int getColumnCount() { return encabezados.length; }
    @Override public Object getValueAt(int r, int c) {
        Cancelacion x = lista.get(r);
        switch(c) {
            case 0: return x.getIdCancelacion(); case 1: return x.getIdPoliza(); case 2: return x.getIdUsuario();
            case 3: return x.getFechaSolicitud(); case 4: return x.getMotivo(); case 5: return x.getMontoDevolucion();
            case 6: return x.getEstatusDevolucion(); default: return null;
        }
    }
    public void vaciarLista() { lista.clear(); }
    public void llenar(List<Cancelacion> l) { this.lista = l; }
}