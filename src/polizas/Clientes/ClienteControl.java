package polizas.Clientes;

import java.awt.event.MouseAdapter;
import java.util.List;
import javax.swing.JOptionPane;
import polizas.General.MedioContacto;

public class ClienteControl {
    private ClienteVista cv;
    private ClienteModelo cm;
    private List<Cliente> lista;

    public ClienteControl(ClienteVista clienteVista) {
        this.cv = clienteVista;
        this.cm = new ClienteModelo();
        clienteEventos();
        cv.actualizarVista(cm.read()); // Carga la lista al abrir la pantalla
    }

    // Arma un objeto Cliente con lo que está escrito en el formulario
    private Cliente leerFormulario(int id) {
        return new Cliente(
            id,
            cv.txtNombre.getText().trim(),
            cv.txtTelefono.getText().trim(),
            cv.txtCorreo.getText().trim(),
            cv.txtRfc.getText().trim().toUpperCase(),
            cv.txtDireccion.getText().trim(),
            cv.cmbMedioContacto.getSelectedItem().toString(),
            cv.chkActivo.isSelected()
        );
    }

    /**
     * Revisa los datos antes de mandarlos a la base de datos.
     * Regresa el mensaje del problema, o null si todo está bien.
     */
    private String validar(Cliente c) {
        if (c.getNombre().isEmpty()) {
            return "Escribe el nombre del cliente.";
        }
        // RN-5: todo cliente debe tener teléfono (WhatsApp)
        if (!c.getTelefono().matches("\\d{10}")) {
            return "El teléfono debe tener 10 números, sin espacios ni guiones.";
        }
        if (!c.getCorreo().isEmpty() && !c.getCorreo().matches("^[\\w.+-]+@[\\w-]+\\.[\\w.]+$")) {
            return "El correo no tiene un formato válido.";
        }
        // El RFC de persona física tiene 13 caracteres y el de persona moral 12
        if (c.getRfc().length() < 12 || c.getRfc().length() > 13) {
            return "El RFC debe tener 12 o 13 caracteres.";
        }
        return null;
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(cv, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void clienteEventos() {
        // Consultar
        cv.btnConsultar.addActionListener(e -> {
            lista = cm.read();
            cv.actualizarVista(lista);
            if (!cm.getMensajeError().isEmpty()) {
                mostrarError(cm.getMensajeError());
            }
        });

        // Buscar por nombre, RFC o teléfono (RF-02)
        cv.btnBuscar.addActionListener(e -> {
            String texto = cv.txtBuscar.getText().trim();
            lista = texto.isEmpty() ? cm.read() : cm.buscar(texto);
            cv.actualizarVista(lista);
            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(cv, "No se encontraron clientes con \"" + texto + "\".");
            }
        });

        // Agregar (RF-01)
        cv.btnAgregar.addActionListener(e -> {
            Cliente c = leerFormulario(0);
            String problema = validar(c);
            if (problema != null) {
                JOptionPane.showMessageDialog(cv, problema, "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (cm.create(c) > 0) {
                JOptionPane.showMessageDialog(cv, "¡Cliente agregado correctamente!");
                cv.limpiarCampos();
                cv.actualizarVista(cm.read());
            } else {
                mostrarError("No se pudo agregar el cliente.\n" + cm.getMensajeError());
            }
        });

        // Actualizar (RF-03)
        cv.btnActualizar.addActionListener(e -> {
            if (cv.txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(cv, "Selecciona un cliente de la tabla.");
                return;
            }
            Cliente c = leerFormulario(Integer.parseInt(cv.txtId.getText()));
            String problema = validar(c);
            if (problema != null) {
                JOptionPane.showMessageDialog(cv, problema, "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Si lo quieren desactivar desde aquí también se revisa la RN-14
            if (!c.isActivo() && cm.tienePendientes(c.getIdCliente())) {
                mostrarError("No se puede desactivar: el cliente tiene pólizas activas, en trámite o pagos pendientes.");
                return;
            }

            if (cm.update(c) > 0) {
                JOptionPane.showMessageDialog(cv, "Cliente actualizado correctamente.");
                cv.actualizarVista(cm.read());
            } else {
                mostrarError("No se pudo actualizar.\n" + cm.getMensajeError());
            }
        });

        // Dar de baja (RF-04): se desactiva, no se borra
        cv.btnEliminar.addActionListener(e -> {
            if (cv.txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(cv, "Selecciona un cliente de la tabla.");
                return;
            }
            int id = Integer.parseInt(cv.txtId.getText());

            // RN-14: no se da de baja si tiene pólizas activas, en trámite o pagos pendientes
            if (cm.tienePendientes(id)) {
                mostrarError("No se puede dar de baja: el cliente tiene pólizas activas, en trámite o pagos pendientes.");
                return;
            }

            int respuesta = JOptionPane.showConfirmDialog(cv,
                "¿Dar de baja a " + cv.txtNombre.getText() + "?\nEl cliente quedará inactivo, pero su historial se guarda.",
                "Confirmar baja", JOptionPane.YES_NO_OPTION);
            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }

            Cliente c = new Cliente(id, "", "", "", "", "", "", false);
            if (cm.delete(c) > 0) {
                JOptionPane.showMessageDialog(cv, "Cliente dado de baja correctamente.");
                cv.limpiarCampos();
                cv.actualizarVista(cm.read());
            } else {
                mostrarError("No se pudo dar de baja.\n" + cm.getMensajeError());
            }
        });

        // Limpiar el formulario
        cv.btnLimpiar.addActionListener(e -> cv.limpiarCampos());

        // Clic en la tabla para rellenar campos
        cv.tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = cv.tabla.getSelectedRow();
                if (fila != -1) {
                    Cliente c = cv.clienteModelo.getCliente(fila);
                    cv.txtId.setText(String.valueOf(c.getIdCliente()));
                    cv.txtNombre.setText(c.getNombre());
                    cv.txtTelefono.setText(c.getTelefono());
                    cv.txtCorreo.setText(c.getCorreo() == null ? "" : c.getCorreo());
                    cv.txtRfc.setText(c.getRfc());
                    cv.txtDireccion.setText(c.getDireccion() == null ? "" : c.getDireccion());
                    if (c.getMedioContacto() != null) {
                        cv.cmbMedioContacto.setSelectedItem(MedioContacto.valueOf(c.getMedioContacto()));
                    }
                    cv.chkActivo.setSelected(c.isActivo());
                }
            }
        });
    }
}
