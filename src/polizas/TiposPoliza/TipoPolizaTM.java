package polizas.TiposPoliza;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TipoPolizaTM extends AbstractTableModel {
    protected List<TipoPoliza> tipoLista;
    protected String[] encabezados = {"ID Tipo", "Nombre", "Descripción", "Activo"};

    public TipoPolizaTM() {
        tipoLista = new ArrayList<>();
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
        tipoLista.clear();
    }

    @Override
    public int getRowCount() {
        return tipoLista.size();
    }

    @Override
    public int getColumnCount() {
        return encabezados.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex > -1) {
            TipoPoliza tp = tipoLista.get(rowIndex);
            switch (columnIndex) {
                case 0: return tp.getIdTipo();
                case 1: return tp.getNombre();
                case 2: return tp.getDescripcion();
                case 3: return tp.isActivo();
                default: return null;
            }
        }
        return null;
    }

    public void addTipoPoliza(TipoPoliza tp) {
        tipoLista.add(tp);
        fireTableRowsInserted(tipoLista.size() - 1, tipoLista.size() - 1);
    }

    public void updateTipoPoliza(int fila, TipoPoliza nueva) {
        tipoLista.set(fila, nueva);
        fireTableRowsUpdated(fila, fila);
    }

    public TipoPoliza getTipoPoliza(int fila) {
        return tipoLista.get(fila);
    }

    public void removeTipoPoliza(int fila) {
        tipoLista.remove(fila);
        fireTableRowsDeleted(fila, fila);
    }

    public void llenar(List<TipoPoliza> lista) {
        this.tipoLista = lista;
    }
}
