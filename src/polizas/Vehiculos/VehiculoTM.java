package polizas.Vehiculos;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class VehiculoTM extends AbstractTableModel {
    private List<Vehiculo> lista = new ArrayList<>();
    private final String[] encabezados = {"ID", "Cliente", "Marca y modelo", "Año", "Color", "Uso", "N° Serie", "Placas", "Activo"};

    @Override
    public int getRowCount() { return lista.size(); }

    @Override
    public int getColumnCount() { return encabezados.length; }

    @Override
    public String getColumnName(int col) { return encabezados[col]; }

    @Override
    public Class<?> getColumnClass(int col) {
        switch (col) {
            case 0:
            case 3:
                return Integer.class;
            case 8:
                return Boolean.class;
            default:
                return String.class;
        }
    }

    @Override
    public Object getValueAt(int fila, int col) {
        Vehiculo v = lista.get(fila);
        switch (col) {
            case 0: return v.getIdVehiculo();
            case 1: return v.getNombreCliente();
            case 2: return v.getNombreModelo();
            case 3: return v.getAnio();
            case 4: return v.getColor();
            case 5: return v.getNombreUso();
            case 6: return v.getNumSerie();
            case 7: return v.getPlacas();
            case 8: return v.isActivo();
            default: return null;
        }
    }

    public Vehiculo getVehiculo(int fila) { return lista.get(fila); }

    public void llenar(List<Vehiculo> datos) { lista.addAll(datos); }

    public void vaciarLista() { lista.clear(); }
}
