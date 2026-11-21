package polizas.DetalleCobertura;

import java.util.*;
import javax.swing.table.AbstractTableModel;

public class DetalleCoberturaTM extends AbstractTableModel {
    protected List<DetalleCobertura> lista = new ArrayList<>();
    protected String[] encabezados = {"ID Póliza", "ID Cobertura", "Suma Asegurada", "Deducible"};

    @Override public String getColumnName(int c) { return encabezados[c]; }
    @Override public int getRowCount() { return lista.size(); }
    @Override public int getColumnCount() { return encabezados.length; }
    @Override public Object getValueAt(int r, int c) {
        DetalleCobertura d = lista.get(r);
        switch(c) {
            case 0: return d.getIdPoliza();
            case 1: return d.getIdCobertura();
            case 2: return d.getSumaAsegurada();
            case 3: return d.getDeducible();
            default: return null;
        }
    }
    public void vaciarLista() { lista.clear(); }
    public void llenar(List<DetalleCobertura> l) { this.lista = l; }
}