package polizas.Submarcas;

import java.util.*;
import javax.swing.table.AbstractTableModel;

public class SubmarcaTM extends AbstractTableModel {
    protected List<Submarca> lista = new ArrayList<>();
    protected String[] encabezados = {"ID Submarca", "ID Marca", "Nombre"};

    @Override public String getColumnName(int c) { return encabezados[c]; }
    @Override public int getRowCount() { return lista.size(); }
    @Override public int getColumnCount() { return encabezados.length; }
    
    @Override public Object getValueAt(int r, int c) {
        Submarca s = lista.get(r);
        switch(c) {
            case 0: return s.getIdSubmarca();
            case 1: return s.getIdMarca();
            case 2: return s.getNombre();
            default: return null;
        }
    }
    public void vaciarLista() { lista.clear(); }
    public void llenar(List<Submarca> l) { this.lista = l; }
}