package polizas.Vehiculos;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class VehiculoTM extends AbstractTableModel {
    protected List<Vehiculo> vehiculoLista;
    // Se actualiza el encabezado para mostrar "ID Submarca" en lugar de Marca y Submarca separadas
    protected String[] encabezados = {"ID Vehículo", "ID Cliente", "ID Submarca", "Modelo", "Tipo", "N° Serie", "Placas", "Activo"};

    public VehiculoTM() {
        vehiculoLista = new ArrayList<>();
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
        vehiculoLista.clear();
    }

    @Override
    public int getRowCount() {
        return vehiculoLista.size();
    }

    @Override
    public int getColumnCount() {
        return encabezados.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex > -1) {
            Vehiculo v = vehiculoLista.get(rowIndex);
            switch (columnIndex) {
                case 0: return v.getIdVehiculo();
                case 1: return v.getIdCliente();
                case 2: return v.getIdSubmarca(); // Reemplazado Marca por ID Submarca
                case 3: return v.getModelo();
                case 4: return v.getTipoVehiculo();
                case 5: return v.getNumeroSerie();
                case 6: return v.getPlacas();
                case 7: return v.isActivo();
                default: return null;
            }
        }
        return null;
    }

    public void addVehiculo(Vehiculo v) {
        vehiculoLista.add(v);
        fireTableRowsInserted(vehiculoLista.size() - 1, vehiculoLista.size() - 1);
    }

    public void updateVehiculo(int fila, Vehiculo nueva) {
        vehiculoLista.set(fila, nueva);
        fireTableRowsUpdated(fila, fila);
    }

    public Vehiculo getVehiculo(int fila) {
        return vehiculoLista.get(fila);
    }

    public void removeVehiculo(int fila) {
        vehiculoLista.remove(fila);
        fireTableRowsDeleted(fila, fila);
    }

    public void llenar(List<Vehiculo> lista) {
        this.vehiculoLista = lista;
    }
}