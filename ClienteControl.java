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
    }

    private boolean nombreInvalido(String nombre) {
        if (nombre == null) {
            return true;
        }
        String limpio = nombre.trim();
        return limpio.isEmpty() || limpio.equalsIgnoreCase("Nombre completo");
    }

    public void clienteEventos() {
        // Consultar
        cv.btnConsultar.addActionListener(e -> {
            lista = cm.read();
            cv.actualizarVista(lista);
        });

        // Agregar
        cv.btnAgregar.addActionListener(e -> {
            String nombre = cv.txtNombre.getText().trim();
            if (nombreInvalido(nombre)) {
                JOptionPane.showMessageDialog(cv, "Ingresa un nombre válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Cliente c = new Cliente(
                0,
                nombre,
                cv.txtTelefono.getText().trim(),
                cv.txtCorreo.getText().trim(),
                cv.txtRfc.getText().trim().toUpperCase(),
                cv.txtDireccion.getText().trim(),
                cv.txtMedioContacto.getText().trim().toUpperCase(),
                true
            );

            int resultado = cm.create(c);
            if (resultado > 0) {
                JOptionPane.showMessageDialog(cv, "¡Cliente agregado correctamente!");
                cv.actualizarVista(cm.read());
            } else {
                JOptionPane.showMessageDialog(cv, "No se pudo agregar el cliente. Verifica los datos y restricciones.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Actualizar
        cv.btnActualizar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(cv.txtId.getText().trim());
                String nombre = cv.txtNombre.getText().trim();

                if (nombreInvalido(nombre)) {
                    JOptionPane.showMessageDialog(cv, "Ingresa un nombre válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Cliente c = new Cliente(
                    id,
                    nombre,
                    cv.txtTelefono.getText().trim(),
                    cv.txtCorreo.getText().trim(),
                    cv.txtRfc.getText().trim().toUpperCase(),
                    cv.txtDireccion.getText().trim(),
                    cv.txtMedioContacto.getText().trim().toUpperCase(),
                    true
                );

                int resultado = cm.update(c);
                JOptionPane.showMessageDialog(cv, resultado > 0 ? "Cliente actualizado correctamente" : "No se pudo actualizar");
                cv.actualizarVista(cm.read());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(cv, "El ID debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Eliminar
        cv.btnEliminar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(cv.txtId.getText().trim());
                Cliente c = new Cliente(id, "", "", "", "", "", "", true);

                int resultado = cm.delete(c);
                JOptionPane.showMessageDialog(cv, resultado > 0 ? "Cliente eliminado correctamente" : "No se pudo eliminar");
                cv.actualizarVista(cm.read());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(cv, "El ID debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Clic en la tabla para rellenar campos
        cv.tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = cv.tabla.getSelectedRow();
                if (fila != -1) {
                    cv.txtId.setText(String.valueOf(cv.clienteModelo.getValueAt(fila, 0)));

                    Object nombre = cv.clienteModelo.getValueAt(fila, 1);
                    cv.txtNombre.setText(nombre != null ? String.valueOf(nombre) : "");

                    Object telefono = cv.clienteModelo.getValueAt(fila, 2);
                    cv.txtTelefono.setText(telefono != null ? String.valueOf(telefono) : "");

                    Object correo = cv.clienteModelo.getValueAt(fila, 3);
                    cv.txtCorreo.setText(correo != null ? String.valueOf(correo) : "");

                    Object rfc = cv.clienteModelo.getValueAt(fila, 4);
                    cv.txtRfc.setText(rfc != null ? String.valueOf(rfc) : "");

                    Object direccion = cv.clienteModelo.getValueAt(fila, 5);
                    cv.txtDireccion.setText(direccion != null ? String.valueOf(direccion) : "");

                    Object medio = cv.clienteModelo.getValueAt(fila, 6);
                    cv.txtMedioContacto.setText(medio != null ? String.valueOf(medio) : "");
                }
            }
        });
    }
}