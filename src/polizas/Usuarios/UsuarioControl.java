package polizas.Usuarios;

import java.awt.event.MouseAdapter;
import java.util.List;
import javax.swing.JOptionPane;

public class UsuarioControl {
    private UsuarioVista uv;
    private UsuarioModelo um;
    private List<Usuario> lista;

    public UsuarioControl(UsuarioVista usuarioVista) {
        this.uv = usuarioVista;
        this.um = new UsuarioModelo();
        usuarioEventos();
    }

    public void usuarioEventos() {
        // Consultar
        uv.btnConsultar.addActionListener(e -> {
            lista = um.read();
            uv.actualizarVista(lista);
        });

        // Agregar
        uv.btnAgregar.addActionListener(e -> {
            String nombre = uv.txtNombre.getText();
            if (nombre.trim().isEmpty() || nombre.equals("Nombre")) {
                JOptionPane.showMessageDialog(uv, "Ingresa un nombre válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Usuario u = new Usuario(
                0,
                nombre,
                uv.txtTelefono.getText(),
                uv.txtCorreo.getText(),
                uv.txtRol.getText(),
                true
            );

            int resultado = um.create(u);
            if (resultado > 0) {
                JOptionPane.showMessageDialog(uv, "¡Usuario agregado correctamente!");
                uv.actualizarVista(um.read());
            } else {
                JOptionPane.showMessageDialog(uv, "No se pudo agregar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Actualizar
        uv.btnActualizar.addActionListener(e -> {
            try {
                int idUsuario = Integer.parseInt(uv.txtIdUsuario.getText());
                Usuario u = new Usuario(
                    idUsuario,
                    uv.txtNombre.getText(),
                    uv.txtTelefono.getText(),
                    uv.txtCorreo.getText(),
                    uv.txtRol.getText(),
                    true
                );

                int resultado = um.update(u);
                JOptionPane.showMessageDialog(uv, resultado > 0 ? "Actualizado correctamente" : "No se pudo actualizar");
                uv.actualizarVista(um.read());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(uv, "El ID debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Eliminar
        uv.btnEliminar.addActionListener(e -> {
            try {
                int idUsuario = Integer.parseInt(uv.txtIdUsuario.getText());
                Usuario u = new Usuario(idUsuario, "", "", "", "", true);

                int resultado = um.delete(u);
                JOptionPane.showMessageDialog(uv, resultado > 0 ? "Eliminado correctamente" : "No se pudo eliminar");
                uv.actualizarVista(um.read());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(uv, "El ID debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Clic en la tabla
        uv.tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = uv.tabla.getSelectedRow();
                if (fila != -1) {
                    uv.txtIdUsuario.setText(String.valueOf(uv.usuarioModelo.getValueAt(fila, 0)));
                    uv.txtNombre.setText((String) uv.usuarioModelo.getValueAt(fila, 1));
                    uv.txtTelefono.setText((String) uv.usuarioModelo.getValueAt(fila, 2));
                    uv.txtCorreo.setText((String) uv.usuarioModelo.getValueAt(fila, 3));
                    uv.txtRol.setText((String) uv.usuarioModelo.getValueAt(fila, 4));
                }
            }
        });
    }
}