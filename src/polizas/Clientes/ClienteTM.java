package polizas.Clientes;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class ClienteTM extends AbstractTableModel {
    protected List<Cliente> clienteLista;
    protected String[] encabezados = {"ID", "Nombre", "Teléfono", "Correo", "RFC", "Dirección", "Medio Contacto", "Activo"};

    public ClienteTM() {
        clienteLista = new ArrayList<>();
        this.setColumnNames(encabezados);
    }

    @Override
    public String getColumnName(int cantidad) {
        return encabezados[cantidad];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: return Integer.class;
            case 7: return Boolean.class;
            default: return String.class;
        }
    }

    public void setColumnNames(String[] nuevosNombres) {
        if (nuevosNombres.length == encabezados.length) {
            this.encabezados = nuevosNombres;
            fireTableStructureChanged();
        } else {
            throw new IllegalArgumentException("Valide los campos");
        }
    }

    public void vaciarLista() {
        clienteLista.clear();
    }

    @Override
    public int getRowCount() {
        return clienteLista.size();
    }

    @Override
    public int getColumnCount() {
        return encabezados.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex > -1 && rowIndex < clienteLista.size()) {
            Cliente c = clienteLista.get(rowIndex);
            switch (columnIndex) {
                case 0: return c.getIdCliente();
                case 1: return c.getNombre();
                case 2: return c.getTelefono();
                case 3: return c.getCorreo();
                case 4: return c.getRfc();
                case 5: return c.getDireccion();
                case 6: return c.getMedioContacto();
                case 7: return c.isActivo();
                default: return null;
            }
        }
        return null;
    }

    public void addCliente(Cliente c) {
        clienteLista.add(c);
        fireTableRowsInserted(clienteLista.size() - 1, clienteLista.size() - 1);
    }

    public void updateCliente(int fila, Cliente nueva) {
        clienteLista.set(fila, nueva);
        fireTableRowsUpdated(fila, fila);
    }

    public Cliente getCliente(int fila) {
        return clienteLista.get(fila);
    }

    public void removeCliente(int fila) {
        clienteLista.remove(fila);
        fireTableRowsDeleted(fila, fila);
    }

    public void llenar(List<Cliente> lista) {
        this.clienteLista = (lista != null) ? lista : new ArrayList<>();
    }
}