package polizas.Pagos;

import java.util.*;
import javax.swing.table.AbstractTableModel;

public class PagoTM extends AbstractTableModel {
    protected List<Pago> lista = new ArrayList<>();
    protected String[] encabezados = {"ID", "Póliza", "Parcialidad", "Monto", "F. Límite", "F. Pago", "Método", "Comprobante", "Estatus"};

    @Override public String getColumnName(int c) { return encabezados[c]; }
    @Override public int getRowCount() { return lista.size(); }
    @Override public int getColumnCount() { return encabezados.length; }
    @Override public Object getValueAt(int r, int c) {
        Pago p = lista.get(r);
        switch(c) {
            case 0: return p.getIdPago(); case 1: return p.getIdPoliza(); case 2: return p.getNumeroParcialidad();
            case 3: return p.getMonto(); case 4: return p.getFechaLimite(); case 5: return p.getFechaPago();
            case 6: return p.getMetodoPago(); case 7: return p.getComprobante(); case 8: return p.getEstatus();
            default: return null;
        }
    }
    public void vaciarLista() { lista.clear(); }
    public void llenar(List<Pago> l) { this.lista = l; }
}