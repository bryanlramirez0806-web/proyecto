package polizas.Clientes;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ClienteTM extends AbstractTableModel {
    private List<Cliente> lista = new ArrayList<>();
    private final String[] encabezados = {"ID", "Tipo", "Nombre", "RFC", "CURP", "Teléfono", "Correo", "Registro", "Activo"};

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
            case 8: return Boolean.class;
            default: return String.class;
        }
    }

    @Override
    public Object getValueAt(int fila, int col) {
        Cliente c = lista.get(fila);
        switch (col) {
            case 0: return c.getIdCliente();
            case 1: return c.getTipoPersona();
            case 2: return c.getNombreCompleto();
            case 3: return c.getRfc();
            case 4: return c.getCurp();
            case 5: return c.getTelefono();
            case 6: return c.getCorreo();
            case 7: return c.getFechaRegistro();
            case 8: return c.isActivo();
            default: return null;
        }
    }

    public Cliente getCliente(int fila) { return lista.get(fila); }

    public void llenar(List<Cliente> datos) { lista.addAll(datos); }

    public void vaciarLista() { lista.clear(); }
}
