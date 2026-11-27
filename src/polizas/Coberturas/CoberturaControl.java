package polizas.Coberturas;

import java.awt.event.MouseAdapter;
import java.util.List;
import javax.swing.JOptionPane;

public class CoberturaControl {
    private CoberturaVista vista;
    private CoberturaModelo modelo;
    private List<Cobertura> lista;

    public CoberturaControl(CoberturaVista vista) {
        this.vista = vista;
        this.modelo = new CoberturaModelo();
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
        if (nombre.length() > 100) {
            return "Nombre no puede tener más de 100 caracteres.";
        }
        String descripcion = vista.txtDescripcion.getText().trim();
        if (descripcion.length() > 500) {
            return "Descripción no puede tener más de 500 caracteres.";
        }
        return null;
    }

    // Arma un objeto Cobertura con lo que está en el formulario (ya validado)
    private Cobertura leerFormulario(int id) {
        return new Cobertura(id, vista.txtNombre.getText().trim(), vista.txtDescripcion.getText().trim());
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
                JOptionPane.showMessageDialog(vista, "No se encontraron coberturas con \"" + texto + "\".");
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
                JOptionPane.showMessageDialog(vista, "¡Cobertura agregada correctamente!");
                vista.limpiarCampos();
                vista.actualizarVista(modelo.read());
            } else {
                mostrarError("No se pudo agregar la cobertura.\n" + modelo.getMensajeError());
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
                JOptionPane.showMessageDialog(vista, "Cobertura actualizada correctamente.");
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
                "¿Eliminar la cobertura \"" + vista.txtNombre.getText() + "\"?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }
            Cobertura x = new Cobertura(Integer.parseInt(vista.txtId.getText()), "", "");
            if (modelo.delete(x) > 0) {
                JOptionPane.showMessageDialog(vista, "Cobertura eliminada correctamente.");
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
                    Cobertura x = vista.tablaModelo.getCobertura(fila);
                    vista.txtId.setText(String.valueOf(x.getIdCobertura()));
                    vista.txtNombre.setText(x.getNombre() == null ? "" : x.getNombre());
                    vista.txtDescripcion.setText(x.getDescripcion() == null ? "" : x.getDescripcion());
                }
            }
        });
    }
}
