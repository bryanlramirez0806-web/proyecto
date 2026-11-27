package polizas.Marcas;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class MarcaTM extends AbstractTableModel {
    private List<Marca> lista = new ArrayList<>();
    private final String[] encabezados = {"ID", "Nombre"};

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
        Marca x = lista.get(fila);
        switch (col) {
            case 0: return x.getIdMarca();
            case 1: return x.getNombre();
            default: return null;
        }
    }

    public Marca getMarca(int fila) { return lista.get(fila); }

    public void llenar(List<Marca> datos) { lista.addAll(datos); }

    public void vaciarLista() { lista.clear(); }
}
