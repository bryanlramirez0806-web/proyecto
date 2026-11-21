package polizas.Polizas;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class PolizaTM extends AbstractTableModel {
    protected List<PolizaEntidad> polizaLista;
    protected String[] encabezados = {
        "ID", "N° Póliza", "Cot.", "Veh.", "Comp.", "Usu.", "Tipo", "Ant.",
        "Emisión", "Inicio", "Vencimiento", "Prima Total", "Estatus"
    };

    public PolizaTM() {
        polizaLista = new ArrayList<>();
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
        polizaLista.clear();
    }

    @Override
    public int getRowCount() {
        return polizaLista.size();
    }

    @Override
    public int getColumnCount() {
        return encabezados.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex > -1 && rowIndex < polizaLista.size()) {
            PolizaEntidad p = polizaLista.get(rowIndex);
            switch (columnIndex) {
                case 0: return p.getIdPoliza();
                case 1: return p.getNumeroPoliza();
                case 2: return p.getIdCotizacion();
                case 3: return p.getIdVehiculo();
                case 4: return p.getIdCompania();
                case 5: return p.getIdUsuario();
                case 6: return p.getIdTipo();
                case 7: return p.getIdPolizaAnterior();
                case 8: return p.getFechaEmision();
                case 9: return p.getFechaInicio();
                case 10: return p.getFechaVencimiento();
                case 11: return p.getPrimaTotal();
                case 12: return p.getEstatus();
                default: return null;
            }
        }
        return null;
    }

    public void addPoliza(PolizaEntidad p) {
        polizaLista.add(p);
        fireTableRowsInserted(polizaLista.size() - 1, polizaLista.size() - 1);
    }

    public void updatePoliza(int fila, PolizaEntidad nueva) {
        polizaLista.set(fila, nueva);
        fireTableRowsUpdated(fila, fila);
    }

    public PolizaEntidad getPoliza(int fila) {
        return polizaLista.get(fila);
    }

    public void removePoliza(int fila) {
        polizaLista.remove(fila);
        fireTableRowsDeleted(fila, fila);
    }

    public void llenar(List<PolizaEntidad> lista) {
        this.polizaLista = (lista != null) ? lista : new ArrayList<>();
    }
}