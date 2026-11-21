package polizas.Cotizaciones;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class CotizacionTM extends AbstractTableModel {
    protected List<Cotizacion> cotizacionLista;
    protected String[] encabezados = {"ID", "ID Vehículo", "ID Usuario", "ID Compañía", "ID Tipo", "Fecha", "Costo", "Opciones Pago", "Estatus"};

    public CotizacionTM() {
        cotizacionLista = new ArrayList<>();
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
        cotizacionLista.clear();
    }

    @Override
    public int getRowCount() {
        return cotizacionLista.size();
    }

    @Override
    public int getColumnCount() {
        return encabezados.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex > -1) {
            Cotizacion c = cotizacionLista.get(rowIndex);
            switch (columnIndex) {
                case 0: return c.getIdCotizacion();
                case 1: return c.getIdVehiculo();
                case 2: return c.getIdUsuario();
                case 3: return c.getIdCompania();
                case 4: return c.getIdTipo();
                case 5: return c.getFecha();
                case 6: return c.getCosto();
                case 7: return c.getOpcionesPago();
                case 8: return c.getEstatus();
                default: return null;
            }
        }
        return null;
    }

    public void addCotizacion(Cotizacion c) {
        cotizacionLista.add(c);
        fireTableRowsInserted(cotizacionLista.size() - 1, cotizacionLista.size() - 1);
    }

    public void updateCotizacion(int fila, Cotizacion nueva) {
        cotizacionLista.set(fila, nueva);
        fireTableRowsUpdated(fila, fila);
    }

    public Cotizacion getCotizacion(int fila) {
        return cotizacionLista.get(fila);
    }

    public void removeCotizacion(int fila) {
        cotizacionLista.remove(fila);
        fireTableRowsDeleted(fila, fila);
    }

    public void llenar(List<Cotizacion> lista) {
        this.cotizacionLista = lista;
    }
}