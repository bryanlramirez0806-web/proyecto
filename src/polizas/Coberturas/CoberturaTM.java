package polizas.Coberturas;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class CoberturaTM extends AbstractTableModel {
    private List<Cobertura> lista = new ArrayList<>();
    private final String[] encabezados = {"ID", "Nombre", "Descripción"};

    @Override
    public int getRowCount() { return lista.size(); }

    @Override
    public int getColumnCount() { return encabezados.length; }

    @Override
    public String getColumnName(int col) { return encabezados[col]; }

    @Override
    public Class<?> getColumnClass(int col) {
        switch (col) {
            case 0: return Integer.class;
            default: return String.class;
        }
    }

    @Override
    public Object getValueAt(int fila, int col) {
        Cobertura x = lista.get(fila);
        switch (col) {
            case 0: return x.getIdCobertura();
            case 1: return x.getNombre();
            case 2: return x.getDescripcion();
            default: return null;
        }
    }

    public Cobertura getCobertura(int fila) { return lista.get(fila); }

    public void llenar(List<Cobertura> datos) { lista.addAll(datos); }

    public void vaciarLista() { lista.clear(); }
}
