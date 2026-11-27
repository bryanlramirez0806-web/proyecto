package polizas.Aseguradoras;

import java.awt.event.MouseAdapter;
import java.util.List;
import javax.swing.JOptionPane;

public class AseguradoraControl {
    private AseguradoraVista vista;
    private AseguradoraModelo modelo;
    private List<Aseguradora> lista;

    public AseguradoraControl(AseguradoraVista vista) {
        this.vista = vista;
        this.modelo = new AseguradoraModelo();
        eventos();
        vista.actualizarVista(modelo.read()); // Carga la lista al abrir la pantalla
    }

    /**
     * Revisa los datos antes de guardarlos.
     * Regresa el mensaje del problema, o null si todo está bien.
     */
    private String validar() {
        String nombre = vista.txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            return "Escribe el nombre.";
        }
        if (nombre.length() > 100) {
            return "Nombre no puede tener más de 100 caracteres.";
        }
        String rfc = vista.txtRfc.getText().trim();
        if (rfc.isEmpty()) {
            return "Escribe el RFC.";
        }
        if (rfc.length() > 13) {
            return "RFC no puede tener más de 13 caracteres.";
        }
        if (!rfc.toUpperCase().matches("[A-ZÑ&]{3,4}\\d{6}[A-Z0-9]{3}")) {
            return "El RFC no tiene un formato válido (12 o 13 caracteres).";
        }
        String telefono = vista.txtTelefono.getText().trim();
        if (telefono.length() > 20) {
            return "Teléfono no puede tener más de 20 caracteres.";
        }
        if (!telefono.isEmpty() && !telefono.matches("\\d{10}")) {
            return "El teléfono debe tener 10 números.";
        }
        String correo = vista.txtCorreo.getText().trim();
        if (correo.length() > 100) {
            return "Correo no puede tener más de 100 caracteres.";
        }
        if (!correo.isEmpty() && !correo.matches("^[\\w.+-]+@[\\w-]+\\.[\\w.]+$")) {
            return "El correo no tiene un formato válido.";
        }
        String nombreContacto = vista.txtNombreContacto.getText().trim();
        if (nombreContacto.length() > 150) {
            return "Contacto no puede tener más de 150 caracteres.";
        }
        return null;
    }

    // Arma un objeto Aseguradora con lo que está en el formulario (ya validado)
    private Aseguradora leerFormulario(int id) {
        return new Aseguradora(id, vista.txtNombre.getText().trim(), vista.txtRfc.getText().trim(), vista.txtTelefono.getText().trim(), vista.txtCorreo.getText().trim(), vista.txtNombreContacto.getText().trim());
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarAviso(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    private void eventos() {
        // Consultar
        vista.btnConsultar.addActionListener(e -> {
            lista = modelo.read();
            vista.actualizarVista(lista);
            if (!modelo.getMensajeError().isEmpty()) {
                mostrarError(modelo.getMensajeError());
            }
        });

        // Buscar
        vista.btnBuscar.addActionListener(e -> {
            String texto = vista.txtBuscar.getText().trim();
            lista = texto.isEmpty() ? modelo.read() : modelo.buscar(texto);
            vista.actualizarVista(lista);
            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "No se encontraron aseguradoras con \"" + texto + "\".");
            }
        });

        // Agregar
        vista.btnAgregar.addActionListener(e -> {
            String problema = validar();
            if (problema != null) {
                mostrarAviso(problema);
                return;
            }
            if (modelo.create(leerFormulario(0)) > 0) {
                JOptionPane.showMessageDialog(vista, "¡Aseguradora agregada correctamente!");
                vista.limpiarCampos();
                vista.actualizarVista(modelo.read());
            } else {
                mostrarError("No se pudo agregar la aseguradora.\n" + modelo.getMensajeError());
            }
        });

        // Actualizar
        vista.btnActualizar.addActionListener(e -> {
            if (vista.txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Selecciona un registro de la tabla.");
                return;
            }
            String problema = validar();
            if (problema != null) {
                mostrarAviso(problema);
                return;
            }
            if (modelo.update(leerFormulario(Integer.parseInt(vista.txtId.getText()))) > 0) {
                JOptionPane.showMessageDialog(vista, "Aseguradora actualizada correctamente.");
                vista.actualizarVista(modelo.read());
            } else {
                mostrarError("No se pudo actualizar.\n" + modelo.getMensajeError());
            }
        });

        // Eliminar (la base de datos no deja borrar si el registro se está usando)
        vista.btnEliminar.addActionListener(e -> {
            if (vista.txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Selecciona un registro de la tabla.");
                return;
            }
            int respuesta = JOptionPane.showConfirmDialog(vista,
                "¿Eliminar la aseguradora \"" + vista.txtNombre.getText() + "\"?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }
            Aseguradora x = new Aseguradora(Integer.parseInt(vista.txtId.getText()), "", "", "", "", "");
            if (modelo.delete(x) > 0) {
                JOptionPane.showMessageDialog(vista, "Aseguradora eliminada correctamente.");
                vista.limpiarCampos();
                vista.actualizarVista(modelo.read());
            } else {
                mostrarError("No se pudo eliminar.\n" + modelo.getMensajeError());
            }
        });

        // Limpiar
        vista.btnLimpiar.addActionListener(e -> vista.limpiarCampos());

        // Clic en la tabla para rellenar campos
        vista.tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = vista.tabla.getSelectedRow();
                if (fila != -1) {
                    Aseguradora x = vista.tablaModelo.getAseguradora(fila);
                    vista.txtId.setText(String.valueOf(x.getIdAseguradora()));
                    vista.txtNombre.setText(x.getNombre() == null ? "" : x.getNombre());
                    vista.txtRfc.setText(x.getRfc() == null ? "" : x.getRfc());
                    vista.txtTelefono.setText(x.getTelefono() == null ? "" : x.getTelefono());
                    vista.txtCorreo.setText(x.getCorreo() == null ? "" : x.getCorreo());
                    vista.txtNombreContacto.setText(x.getNombreContacto() == null ? "" : x.getNombreContacto());
                }
            }
        });
    }
}
