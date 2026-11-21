package polizas.Companias;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class CompaniaTM extends AbstractTableModel {
    protected List<Compania> companiaLista;
    protected String[] encabezados = {"ID", "Nombre", "Teléfono", "Correo", "Sitio Validación", "Activo"};

    public CompaniaTM() {
        companiaLista = new ArrayList<>();
        this.setColumnNames(encabezados);
    }

    @Override
    public String getColumnName(int cantidad) {
        return encabezados[cantidad];
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
        companiaLista.clear();
    }

    @Override
    public int getRowCount() {
        return companiaLista.size();
    }

    @Override
    public int getColumnCount() {
        return encabezados.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex > -1) {
            Compania c = companiaLista.get(rowIndex);
            switch (columnIndex) {
                case 0: return c.getIdCompania();
                case 1: return c.getNombre();
                case 2: return c.getTelefono();
                case 3: return c.getCorreo();
                case 4: return c.getSitioValidacion();
                case 5: return c.isActivo();
                default: return null;
            }
        }
        return null;
    }

    public void addCompania(Compania c) {
        companiaLista.add(c);
        fireTableRowsInserted(companiaLista.size() - 1, companiaLista.size() - 1);
    }

    public void updateCompania(int fila, Compania nueva) {
        companiaLista.set(fila, nueva);
        fireTableRowsUpdated(fila, fila);
    }

    public Compania getCompania(int fila) {
        return companiaLista.get(fila);
    }

    public void removeCompania(int fila) {
        companiaLista.remove(fila);
        fireTableRowsDeleted(fila, fila);
    }

    public void llenar(List<Compania> lista) {
        this.companiaLista = lista;
    }
}