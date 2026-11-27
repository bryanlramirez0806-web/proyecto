package polizas.Clientes;

import java.awt.event.MouseAdapter;
import java.util.List;
import javax.swing.JOptionPane;

public class ClienteControl {
    private ClienteVista cv;
    private ClienteModelo cm;
    private List<Cliente> lista;

    public ClienteControl(ClienteVista clienteVista) {
        this.cv = clienteVista;
        this.cm = new ClienteModelo();
        clienteEventos();
        cv.ajustarPorTipo();
        cv.actualizarVista(cm.read()); // Carga la lista al abrir la pantalla
    }

    // Arma un objeto Cliente con lo que está escrito en el formulario
    private Cliente leerFormulario(int id) {
        return new Cliente(
            id,
            cv.cmbTipoPersona.getSelectedItem().toString(),
            cv.txtNombre.getText().trim(),
            cv.txtApellidoPaterno.getText().trim(),
            cv.txtApellidoMaterno.getText().trim(),
            cv.txtRfc.getText().trim().toUpperCase(),
            cv.txtCurp.getText().trim().toUpperCase(),
            cv.txtTelefono.getText().trim(),
            cv.txtCorreo.getText().trim(),
            cv.txtFechaRegistro.getText(),
            cv.chkActivo.isSelected()
        );
    }

    /**
     * Revisa los datos antes de mandarlos a la base de datos.
     * Regresa el mensaje del problema, o null si todo está bien.
     */
    private String validar(Cliente c) {
        boolean fisica = c.getTipoPersona().equals("FISICA");

        if (c.getNombre().isEmpty()) {
            return fisica ? "Escribe el nombre del cliente." : "Escribe la razón social de la empresa.";
        }
        if (fisica) {
            if (c.getApellidoPaterno().isEmpty()) {
                return "Escribe el apellido paterno.";
            }
            // RFC de persona física: 4 letras, 6 números de la fecha y 3 de homoclave
            if (!c.getRfc().matches("[A-ZÑ&]{4}\\d{6}[A-Z0-9]{3}")) {
                return "El RFC de una persona física debe tener 13 caracteres (ej. MELC850214AB3).";
            }
            // CURP: 18 caracteres
            if (!c.getCurp().matches("[A-Z]{4}\\d{6}[HM][A-Z]{5}[A-Z0-9]\\d")) {
                return "La CURP debe tener 18 caracteres con el formato oficial (ej. MELC850214HQRNPR05).";
            }
        } else {
            // RFC de persona moral: 3 letras, 6 números y 3 de homoclave
            if (!c.getRfc().matches("[A-ZÑ&]{3}\\d{6}[A-Z0-9]{3}")) {
                return "El RFC de una persona moral debe tener 12 caracteres (ej. TRM150310AB2).";
            }
        }
        // RN-5: todo cliente debe tener teléfono (WhatsApp)
        if (!c.getTelefono().matches("\\d{10}")) {
            return "El teléfono debe tener 10 números, sin espacios ni guiones.";
        }
        if (!c.getCorreo().isEmpty() && !c.getCorreo().matches("^[\\w.+-]+@[\\w-]+\\.[\\w.]+$")) {
            return "El correo no tiene un formato válido.";
        }
        return null;
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(cv, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarAviso(String mensaje) {
        JOptionPane.showMessageDialog(cv, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    public void clienteEventos() {
        // Al cambiar el tipo de persona se activan o desactivan los apellidos y la CURP
        cv.cmbTipoPersona.addActionListener(e -> cv.ajustarPorTipo());

        // Consultar
        cv.btnConsultar.addActionListener(e -> {
            lista = cm.read();
            cv.actualizarVista(lista);
            if (!cm.getMensajeError().isEmpty()) {
                mostrarError(cm.getMensajeError());
            }
        });

        // Buscar por nombre, RFC, CURP o teléfono (RF-02)
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
                mostrarAviso(problema);
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
                mostrarAviso(problema);
                return;
            }
            // Si lo quieren desactivar desde aquí también se revisa la RN-14
            if (!c.isActivo() && cm.tienePolizasVigentes(c.getIdCliente())) {
                mostrarError("No se puede desactivar: el cliente tiene pólizas activas o en trámite.");
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
            Cliente c = leerFormulario(Integer.parseInt(cv.txtId.getText()));

            // RN-14: no se da de baja si tiene pólizas activas o en trámite
            if (cm.tienePolizasVigentes(c.getIdCliente())) {
                mostrarError("No se puede dar de baja: el cliente tiene pólizas activas o en trámite.");
                return;
            }
            int respuesta = JOptionPane.showConfirmDialog(cv,
                "¿Dar de baja a " + c.getNombreCompleto() + "?\nEl cliente quedará inactivo, pero su historial se guarda.",
                "Confirmar baja", JOptionPane.YES_NO_OPTION);
            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }
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
                    cv.cmbTipoPersona.setSelectedItem(TipoPersona.valueOf(c.getTipoPersona()));
                    cv.txtNombre.setText(c.getNombre());
                    cv.txtApellidoPaterno.setText(c.getApellidoPaterno() == null ? "" : c.getApellidoPaterno());
                    cv.txtApellidoMaterno.setText(c.getApellidoMaterno() == null ? "" : c.getApellidoMaterno());
                    cv.txtRfc.setText(c.getRfc());
                    cv.txtCurp.setText(c.getCurp() == null ? "" : c.getCurp());
                    cv.txtTelefono.setText(c.getTelefono());
                    cv.txtCorreo.setText(c.getCorreo() == null ? "" : c.getCorreo());
                    cv.txtFechaRegistro.setText(c.getFechaRegistro());
                    cv.chkActivo.setSelected(c.isActivo());
                }
            }
        });
    }
}
