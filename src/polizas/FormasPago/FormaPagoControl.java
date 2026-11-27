package polizas.FormasPago;

import java.awt.event.MouseAdapter;
import java.util.List;
import javax.swing.JOptionPane;

public class FormaPagoControl {
    private FormaPagoVista vista;
    private FormaPagoModelo modelo;
    private List<FormaPago> lista;

    public FormaPagoControl(FormaPagoVista vista) {
        this.vista = vista;
        this.modelo = new FormaPagoModelo();
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
            return "El campo Nombre es obligatorio.";
        }
        if (nombre.length() > 50) {
            return "Nombre no puede tener más de 50 caracteres.";
        }
        String numeroPagos = vista.txtNumeroPagos.getText().trim();
        if (numeroPagos.isEmpty()) {
            return "El campo N° de pagos es obligatorio.";
        }
        if (!numeroPagos.matches("\\d+") || Integer.parseInt(numeroPagos) < 1 || Integer.parseInt(numeroPagos) > 12) {
            return "N° de pagos debe ser un número entre 1 y 12.";
        }
        return null;
    }

    // Arma un objeto FormaPago con lo que está en el formulario (ya validado)
    private FormaPago leerFormulario(int id) {
        return new FormaPago(id, vista.txtNombre.getText().trim(), Integer.parseInt(vista.txtNumeroPagos.getText().trim()));
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
                JOptionPane.showMessageDialog(vista, "No se encontraron formas de pago con \"" + texto + "\".");
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
                JOptionPane.showMessageDialog(vista, "¡Forma de pago agregada correctamente!");
                vista.limpiarCampos();
                vista.actualizarVista(modelo.read());
            } else {
                mostrarError("No se pudo agregar la forma de pago.\n" + modelo.getMensajeError());
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
                JOptionPane.showMessageDialog(vista, "Forma de pago actualizada correctamente.");
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
                "¿Eliminar la forma de pago \"" + vista.txtNombre.getText() + "\"?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }
            FormaPago x = new FormaPago(Integer.parseInt(vista.txtId.getText()), "", 0);
            if (modelo.delete(x) > 0) {
                JOptionPane.showMessageDialog(vista, "Forma de pago eliminada correctamente.");
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
                    FormaPago x = vista.tablaModelo.getFormaPago(fila);
                    vista.txtId.setText(String.valueOf(x.getIdFormaPago()));
                    vista.txtNombre.setText(x.getNombre() == null ? "" : x.getNombre());
                    vista.txtNumeroPagos.setText(String.valueOf(x.getNumeroPagos()));
                }
            }
        });
    }
}
