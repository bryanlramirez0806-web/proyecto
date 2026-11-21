package polizas.Vehiculos;

import java.awt.event.MouseAdapter;
import java.time.Year;
import java.util.List;
import javax.swing.JOptionPane;
import polizas.Clientes.Cliente;
import polizas.Clientes.ClienteModelo;
import polizas.Submarcas.Submarca;
import polizas.Submarcas.SubmarcaModelo;

public class VehiculoControl {
    private VehiculoVista vv;
    private VehiculoModelo vm;
    private List<Vehiculo> lista;

    public VehiculoControl(VehiculoVista vehiculoVista) {
        this.vv = vehiculoVista;
        this.vm = new VehiculoModelo();
        cargarCombos();
        vehiculoEventos();
        vv.actualizarVista(vm.read()); // Carga la lista al abrir la pantalla
    }

    // Reutiliza los modelos de Clientes y Submarcas para llenar las listas
    private void cargarCombos() {
        List<Cliente> clientes = new ClienteModelo().readActivos();
        List<Submarca> submarcas = new SubmarcaModelo().read();
        vv.llenarCombos(clientes, submarcas);
    }

    /**
     * Revisa los datos antes de guardarlos.
     * Regresa el mensaje del problema, o null si todo está bien.
     */
    private String validar() {
        if (vv.cmbCliente.getSelectedItem() == null) {
            return "Escoge el cliente dueño del vehículo.";
        }
        if (vv.cmbSubmarca.getSelectedItem() == null) {
            return "Escoge la submarca del vehículo.";
        }
        String modelo = vv.txtModelo.getText().trim();
        int anioMaximo = Year.now().getValue() + 1;
        if (!modelo.matches("\\d{4}") || Integer.parseInt(modelo) < 1950 || Integer.parseInt(modelo) > anioMaximo) {
            return "El modelo debe ser un año entre 1950 y " + anioMaximo + ".";
        }
        Object tipo = vv.cmbTipoVehiculo.getSelectedItem();
        if (tipo == null || tipo.toString().trim().isEmpty()) {
            return "Escribe el tipo de vehículo.";
        }
        if (vv.txtNumeroSerie.getText().trim().isEmpty()) {
            return "Escribe el número de serie (VIN).";
        }
        if (vv.txtPlacas.getText().trim().isEmpty()) {
            return "Escribe las placas.";
        }
        return null;
    }

    // Arma un objeto Vehiculo con lo que está en el formulario
    private Vehiculo leerFormulario(int id) {
        Cliente cliente = (Cliente) vv.cmbCliente.getSelectedItem();
        Submarca submarca = (Submarca) vv.cmbSubmarca.getSelectedItem();
        return new Vehiculo(
            id,
            cliente.getIdCliente(),
            submarca.getIdSubmarca(),
            vv.txtModelo.getText().trim(),
            vv.cmbTipoVehiculo.getSelectedItem().toString().trim(),
            vv.txtNumeroSerie.getText().trim(),
            vv.txtPlacas.getText().trim(),
            vv.chkActivo.isSelected()
        );
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vv, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void vehiculoEventos() {
        // Consultar
        vv.btnConsultar.addActionListener(e -> {
            cargarCombos();
            lista = vm.read();
            vv.actualizarVista(lista);
            if (!vm.getMensajeError().isEmpty()) {
                mostrarError(vm.getMensajeError());
            }
        });

        // Buscar por placas, VIN o cliente (RF-07)
        vv.btnBuscar.addActionListener(e -> {
            String texto = vv.txtBuscar.getText().trim();
            lista = texto.isEmpty() ? vm.read() : vm.buscar(texto);
            vv.actualizarVista(lista);
            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(vv, "No se encontraron vehículos con \"" + texto + "\".");
            }
        });

        // Agregar (RF-06)
        vv.btnAgregar.addActionListener(e -> {
            String problema = validar();
            if (problema != null) {
                JOptionPane.showMessageDialog(vv, problema, "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (vm.create(leerFormulario(0)) > 0) {
                JOptionPane.showMessageDialog(vv, "¡Vehículo agregado correctamente!");
                vv.limpiarCampos();
                vv.actualizarVista(vm.read());
            } else {
                mostrarError("No se pudo agregar el vehículo.\n" + vm.getMensajeError());
            }
        });

        // Actualizar (RF-08)
        vv.btnActualizar.addActionListener(e -> {
            if (vv.txtIdVehiculo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vv, "Selecciona un vehículo de la tabla.");
                return;
            }
            String problema = validar();
            if (problema != null) {
                JOptionPane.showMessageDialog(vv, problema, "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Vehiculo v = leerFormulario(Integer.parseInt(vv.txtIdVehiculo.getText()));
            if (!v.isActivo() && vm.tienePendientes(v.getIdVehiculo())) {
                mostrarError("No se puede desactivar: el vehículo tiene pólizas activas, en trámite o pagos pendientes.");
                return;
            }
            if (vm.update(v) > 0) {
                JOptionPane.showMessageDialog(vv, "Vehículo actualizado correctamente.");
                vv.actualizarVista(vm.read());
            } else {
                mostrarError("No se pudo actualizar.\n" + vm.getMensajeError());
            }
        });

        // Dar de baja (RF-09): se desactiva, no se borra
        vv.btnEliminar.addActionListener(e -> {
            if (vv.txtIdVehiculo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vv, "Selecciona un vehículo de la tabla.");
                return;
            }
            int id = Integer.parseInt(vv.txtIdVehiculo.getText());

            // RN-14: no se da de baja si tiene pólizas activas, en trámite o pagos pendientes
            if (vm.tienePendientes(id)) {
                mostrarError("No se puede dar de baja: el vehículo tiene pólizas activas, en trámite o pagos pendientes.");
                return;
            }

            int respuesta = JOptionPane.showConfirmDialog(vv,
                "¿Dar de baja el vehículo con placas " + vv.txtPlacas.getText() + "?\nQuedará inactivo, pero su historial se guarda.",
                "Confirmar baja", JOptionPane.YES_NO_OPTION);
            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }

            Vehiculo v = new Vehiculo(id, 0, 0, "", "", "", "", false);
            if (vm.delete(v) > 0) {
                JOptionPane.showMessageDialog(vv, "Vehículo dado de baja correctamente.");
                vv.limpiarCampos();
                vv.actualizarVista(vm.read());
            } else {
                mostrarError("No se pudo dar de baja.\n" + vm.getMensajeError());
            }
        });

        // Limpiar el formulario
        vv.btnLimpiar.addActionListener(e -> vv.limpiarCampos());

        // Clic en la tabla para rellenar campos
        vv.tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = vv.tabla.getSelectedRow();
                if (fila != -1) {
                    Vehiculo v = vv.vehiculoModelo.getVehiculo(fila);
                    vv.txtIdVehiculo.setText(String.valueOf(v.getIdVehiculo()));
                    vv.seleccionarCliente(v.getIdCliente());
                    vv.seleccionarSubmarca(v.getIdSubmarca());
                    vv.txtModelo.setText(v.getModelo());
                    vv.cmbTipoVehiculo.setSelectedItem(v.getTipoVehiculo());
                    vv.txtNumeroSerie.setText(v.getNumeroSerie());
                    vv.txtPlacas.setText(v.getPlacas());
                    vv.chkActivo.setSelected(v.isActivo());
                }
            }
        });
    }
}
