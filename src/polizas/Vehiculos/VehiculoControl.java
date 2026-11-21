package polizas.Vehiculos;

import java.awt.event.MouseAdapter;
import java.util.List;
import javax.swing.JOptionPane;

public class VehiculoControl {
    private VehiculoVista vv;
    private VehiculoModelo vm;
    private List<Vehiculo> lista;

    public VehiculoControl(VehiculoVista vehiculoVista) {
        this.vv = vehiculoVista;
        this.vm = new VehiculoModelo();
        vehiculoEventos();
    }

    public void vehiculoEventos() {
        // Consultar
        vv.btnConsultar.addActionListener(e -> {
            lista = vm.read();
            vv.actualizarVista(lista);
        });

        // Agregar
        vv.btnAgregar.addActionListener(e -> {
            try {
                int idCliente = Integer.parseInt(vv.txtIdCliente.getText().trim());
                int idSubmarca = Integer.parseInt(vv.txtIdSubmarca.getText().trim());
                String modelo = vv.txtModelo.getText().trim();
                String tipoVehiculo = vv.txtTipoVehiculo.getText().trim();
                String numeroSerie = vv.txtNumeroSerie.getText().trim();
                String placas = vv.txtPlacas.getText().trim();

                if (idCliente <= 0 || idSubmarca <= 0 || modelo.isEmpty() || tipoVehiculo.isEmpty()) {
                    JOptionPane.showMessageDialog(
                        vv,
                        "Ingresa IDs válidos y completa Modelo y Tipo de Vehículo.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                Vehiculo v = new Vehiculo(
                    0,
                    idCliente,
                    idSubmarca,
                    modelo,
                    tipoVehiculo,
                    numeroSerie,
                    placas,
                    true
                );

                int resultado = vm.create(v);
                if (resultado > 0) {
                    JOptionPane.showMessageDialog(vv, "¡Vehículo agregado correctamente!");
                    vv.actualizarVista(vm.read());
                } else {
                    JOptionPane.showMessageDialog(
                        vv,
                        "No se pudo agregar el vehículo. Verifica que los IDs de Cliente y Submarca existan y que no haya duplicados.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    vv,
                    "El ID de Cliente y el ID de Submarca deben ser números válidos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Actualizar
        vv.btnActualizar.addActionListener(e -> {
            try {
                int idVehiculo = Integer.parseInt(vv.txtIdVehiculo.getText().trim());
                int idCliente = Integer.parseInt(vv.txtIdCliente.getText().trim());
                int idSubmarca = Integer.parseInt(vv.txtIdSubmarca.getText().trim());
                String modelo = vv.txtModelo.getText().trim();
                String tipoVehiculo = vv.txtTipoVehiculo.getText().trim();
                String numeroSerie = vv.txtNumeroSerie.getText().trim();
                String placas = vv.txtPlacas.getText().trim();

                if (idVehiculo <= 0 || idCliente <= 0 || idSubmarca <= 0 || modelo.isEmpty() || tipoVehiculo.isEmpty()) {
                    JOptionPane.showMessageDialog(
                        vv,
                        "Verifica los IDs y completa Modelo y Tipo de Vehículo.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                Vehiculo v = new Vehiculo(
                    idVehiculo,
                    idCliente,
                    idSubmarca,
                    modelo,
                    tipoVehiculo,
                    numeroSerie,
                    placas,
                    true
                );

                int resultado = vm.update(v);
                JOptionPane.showMessageDialog(
                    vv,
                    resultado > 0 ? "Vehículo actualizado correctamente" : "No se pudo actualizar"
                );
                vv.actualizarVista(vm.read());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    vv,
                    "Los IDs deben ser números válidos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Eliminar
        vv.btnEliminar.addActionListener(e -> {
            try {
                int idVehiculo = Integer.parseInt(vv.txtIdVehiculo.getText().trim());
                Vehiculo v = new Vehiculo(idVehiculo, 0, 0, "", "", "", "", true);

                int resultado = vm.delete(v);
                JOptionPane.showMessageDialog(
                    vv,
                    resultado > 0 ? "Vehículo eliminado correctamente" : "No se pudo eliminar"
                );
                vv.actualizarVista(vm.read());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    vv,
                    "El ID de Vehículo debe ser un número válido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Clic en la tabla
        vv.tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = vv.tabla.getSelectedRow();
                if (fila != -1) {
                    vv.txtIdVehiculo.setText(String.valueOf(vv.vehiculoModelo.getValueAt(fila, 0)));
                    vv.txtIdCliente.setText(String.valueOf(vv.vehiculoModelo.getValueAt(fila, 1)));
                    vv.txtIdSubmarca.setText(String.valueOf(vv.vehiculoModelo.getValueAt(fila, 2)));

                    Object modelo = vv.vehiculoModelo.getValueAt(fila, 3);
                    vv.txtModelo.setText(modelo != null ? String.valueOf(modelo) : "");

                    Object tipo = vv.vehiculoModelo.getValueAt(fila, 4);
                    vv.txtTipoVehiculo.setText(tipo != null ? String.valueOf(tipo) : "");

                    Object serie = vv.vehiculoModelo.getValueAt(fila, 5);
                    vv.txtNumeroSerie.setText(serie != null ? String.valueOf(serie) : "");

                    Object placas = vv.vehiculoModelo.getValueAt(fila, 6);
                    vv.txtPlacas.setText(placas != null ? String.valueOf(placas) : "");
                }
            }
        });
    }
}