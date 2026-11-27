package polizas.Vehiculos;

import java.awt.event.MouseAdapter;
import java.time.Year;
import java.util.List;
import javax.swing.JOptionPane;
import polizas.Clientes.Cliente;
import polizas.Clientes.ClienteModelo;
import polizas.Modelos.Modelo;
import polizas.Modelos.ModeloModelo;
import polizas.UsosVehiculo.UsoVehiculo;
import polizas.UsosVehiculo.UsoVehiculoModelo;

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

    // Reutiliza los modelos de Clientes, Modelos y Usos para llenar las listas
    private void cargarCombos() {
        List<Cliente> clientes = new ClienteModelo().readActivos();
        List<Modelo> modelos = new ModeloModelo().read();
        List<UsoVehiculo> usos = new UsoVehiculoModelo().read();
        vv.llenarCombos(clientes, modelos, usos);
    }

    /**
     * Revisa los datos antes de guardarlos.
     * Regresa el mensaje del problema, o null si todo está bien.
     */
    private String validar() {
        if (vv.cmbCliente.getSelectedItem() == null) return "Escoge el cliente dueño del vehículo.";
        if (vv.cmbModelo.getSelectedItem() == null) return "Escoge el modelo del vehículo.";
        if (vv.cmbUso.getSelectedItem() == null) return "Escoge el uso del vehículo.";

        String anio = vv.txtAnio.getText().trim();
        int anioMaximo = Year.now().getValue() + 1;
        if (!anio.matches("\\d{4}") || Integer.parseInt(anio) < 1950 || Integer.parseInt(anio) > anioMaximo) {
            return "El año debe estar entre 1950 y " + anioMaximo + ".";
        }
        // El número de serie (VIN) tiene 17 caracteres y no usa las letras I, O ni Q
        if (!vv.txtNumSerie.getText().trim().toUpperCase().matches("[A-HJ-NPR-Z0-9]{17}")) {
            return "El número de serie (VIN) debe tener 17 caracteres, sin las letras I, O ni Q.";
        }
        String placas = vv.txtPlacas.getText().trim();
        if (placas.isEmpty() || placas.length() > 15) {
            return "Escribe las placas (máximo 15 caracteres).";
        }
        return null;
    }

    // Arma un objeto Vehiculo con lo que está en el formulario (ya validado)
    private Vehiculo leerFormulario(int id) {
        Cliente cliente = (Cliente) vv.cmbCliente.getSelectedItem();
        Modelo modelo = (Modelo) vv.cmbModelo.getSelectedItem();
        UsoVehiculo uso = (UsoVehiculo) vv.cmbUso.getSelectedItem();
        return new Vehiculo(
            id,
            Integer.parseInt(vv.txtAnio.getText().trim()),
            vv.txtNumSerie.getText().trim(),
            vv.txtPlacas.getText().trim(),
            vv.txtColor.getText().trim(),
            cliente.getIdCliente(),
            modelo.getIdModelo(),
            uso.getIdUso(),
            vv.chkActivo.isSelected()
        );
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vv, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarAviso(String mensaje) {
        JOptionPane.showMessageDialog(vv, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
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

        // Buscar por placas, VIN, cliente o modelo (RF-07)
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
                mostrarAviso(problema);
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
                mostrarAviso(problema);
                return;
            }
            Vehiculo v = leerFormulario(Integer.parseInt(vv.txtIdVehiculo.getText()));
            if (!v.isActivo() && vm.tienePolizasVigentes(v.getIdVehiculo())) {
                mostrarError("No se puede desactivar: el vehículo tiene pólizas activas o en trámite.");
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

            // RN-14: no se da de baja si tiene pólizas activas o en trámite
            if (vm.tienePolizasVigentes(id)) {
                mostrarError("No se puede dar de baja: el vehículo tiene pólizas activas o en trámite.");
                return;
            }
            int respuesta = JOptionPane.showConfirmDialog(vv,
                "¿Dar de baja el vehículo con placas " + vv.txtPlacas.getText() + "?\nQuedará inactivo, pero su historial se guarda.",
                "Confirmar baja", JOptionPane.YES_NO_OPTION);
            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }
            Vehiculo v = new Vehiculo(id, 0, "", "", "", 0, 0, 0, false);
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
                    vv.seleccionar(v.getIdCliente(), v.getIdModelo(), v.getIdUso());
                    vv.txtAnio.setText(String.valueOf(v.getAnio()));
                    vv.txtNumSerie.setText(v.getNumSerie());
                    vv.txtPlacas.setText(v.getPlacas());
                    vv.txtColor.setText(v.getColor() == null ? "" : v.getColor());
                    vv.chkActivo.setSelected(v.isActivo());
                }
            }
        });
    }
}
