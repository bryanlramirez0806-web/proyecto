package polizas.Coberturas;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class CoberturaTM extends AbstractTableModel {
    protected List<Cobertura> lista;
    protected String[] encabezados = {"ID", "Nombre", "Descripción", "Activo"};

    public CoberturaTM() { lista = new ArrayList<>(); }

    @Override public String getColumnName(int col) { return encabezados[col]; }
    @Override public int getRowCount() { return lista.size(); }
    @Override public int getColumnCount() { return encabezados.length; }
    
    @Override public Object getValueAt(int row, int col) {
        Cobertura c = lista.get(row);
        switch (col) {
            case 0: return c.getIdCobertura();
            case 1: return c.getNombre();
            case 2: return c.getDescripcion();
            case 3: return c.isActivo();
            default: return null;
        }
    }
    public void vaciarLista() { lista.clear(); }
    public void llenar(List<Cobertura> l) { this.lista = l; }
}