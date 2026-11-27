package polizas.Polizas;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class PolizaTM extends AbstractTableModel {
    private List<PolizaEntidad> lista = new ArrayList<>();
    private final String[] encabezados = {
        "ID", "N° Póliza", "Cliente", "Placas", "Aseguradora", "Forma pago", "Ant.",
        "Inicio", "Fin", "Prima total", "Estado"
    };

    @Override
    public int getRowCount() { return lista.size(); }

    @Override
    public int getColumnCount() { return encabezados.length; }

    @Override
    public String getColumnName(int col) { return encabezados[col]; }

    @Override
    public Object getValueAt(int fila, int col) {
        PolizaEntidad p = lista.get(fila);
        switch (col) {
            case 0: return p.getIdPoliza();
            case 1: return p.getNumeroPoliza();
            case 2: return p.getNombreCliente();
            case 3: return p.getPlacas();
            case 4: return p.getNombreAseguradora();
            case 5: return p.getNombreFormaPago();
            case 6: return p.getIdPolizaAnterior();
            case 7: return p.getFechaInicio();
            case 8: return p.getFechaFin();
            case 9: return String.format("%,.2f", p.getPrimaTotal());
            case 10: return p.getEstado();
            default: return null;
        }
    }

    public PolizaEntidad getPoliza(int fila) { return lista.get(fila); }

    public void llenar(List<PolizaEntidad> datos) { lista.addAll(datos); }

    public void vaciarLista() { lista.clear(); }
}
