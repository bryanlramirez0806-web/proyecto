package polizas.Modelos;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ModeloTM extends AbstractTableModel {
    private List<Modelo> lista = new ArrayList<>();
    private final String[] encabezados = {"ID", "Nombre", "Marca"};

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
        Modelo x = lista.get(fila);
        switch (col) {
            case 0: return x.getIdModelo();
            case 1: return x.getNombre();
            case 2: return x.getNombreMarca();
            default: return null;
        }
    }

    public Modelo getModelo(int fila) { return lista.get(fila); }

    public void llenar(List<Modelo> datos) { lista.addAll(datos); }

    public void vaciarLista() { lista.clear(); }
}
