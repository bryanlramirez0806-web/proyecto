package polizas.FormasPago;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class FormaPagoTM extends AbstractTableModel {
    private List<FormaPago> lista = new ArrayList<>();
    private final String[] encabezados = {"ID", "Nombre", "N° de pagos"};

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
            case 2: return Integer.class;
            default: return String.class;
        }
    }

    @Override
    public Object getValueAt(int fila, int col) {
        FormaPago x = lista.get(fila);
        switch (col) {
            case 0: return x.getIdFormaPago();
            case 1: return x.getNombre();
            case 2: return x.getNumeroPagos();
            default: return null;
        }
    }

    public FormaPago getFormaPago(int fila) { return lista.get(fila); }

    public void llenar(List<FormaPago> datos) { lista.addAll(datos); }

    public void vaciarLista() { lista.clear(); }
}
