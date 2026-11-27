package polizas.Aseguradoras;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class AseguradoraTM extends AbstractTableModel {
    private List<Aseguradora> lista = new ArrayList<>();
    private final String[] encabezados = {"ID", "Nombre", "RFC", "Teléfono", "Correo", "Contacto"};

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
        Aseguradora x = lista.get(fila);
        switch (col) {
            case 0: return x.getIdAseguradora();
            case 1: return x.getNombre();
            case 2: return x.getRfc();
            case 3: return x.getTelefono();
            case 4: return x.getCorreo();
            case 5: return x.getNombreContacto();
            default: return null;
        }
    }

    public Aseguradora getAseguradora(int fila) { return lista.get(fila); }

    public void llenar(List<Aseguradora> datos) { lista.addAll(datos); }

    public void vaciarLista() { lista.clear(); }
}
