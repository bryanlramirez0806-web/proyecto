package polizas.Modelos;

import java.awt.event.MouseAdapter;
import java.util.List;
import javax.swing.JOptionPane;
import polizas.Marcas.Marca;
import polizas.Marcas.MarcaModelo;

public class ModeloControl {
    private ModeloVista vista;
    private ModeloModelo modelo;
    private List<Modelo> lista;

    public ModeloControl(ModeloVista vista) {
        this.vista = vista;
        this.modelo = new ModeloModelo();
        cargarCombos();
        eventos();
        vista.actualizarVista(modelo.read()); // Carga la lista al abrir la pantalla
    }

    // Reutiliza el modelo del otro catálogo para llenar la lista
    private void cargarCombos() {
        vista.llenarMarcas(new MarcaModelo().read());
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
        if (vista.cmbMarca.getSelectedItem() == null) {
            return "Escoge la marca.";
        }
        return null;
    }

    // Arma un objeto Modelo con lo que está en el formulario (ya validado)
    private Modelo leerFormulario(int id) {
        return new Modelo(id, vista.txtNombre.getText().trim(), ((Marca) vista.cmbMarca.getSelectedItem()).getIdMarca());
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
            cargarCombos();
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
                JOptionPane.showMessageDialog(vista, "No se encontraron modelos con \"" + texto + "\".");
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
                JOptionPane.showMessageDialog(vista, "¡Modelo agregado correctamente!");
                vista.limpiarCampos();
                vista.actualizarVista(modelo.read());
            } else {
                mostrarError("No se pudo agregar el modelo.\n" + modelo.getMensajeError());
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
                JOptionPane.showMessageDialog(vista, "Modelo actualizado correctamente.");
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
                "¿Eliminar el modelo \"" + vista.txtNombre.getText() + "\"?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }
            Modelo x = new Modelo(Integer.parseInt(vista.txtId.getText()), "", 0);
            if (modelo.delete(x) > 0) {
                JOptionPane.showMessageDialog(vista, "Modelo eliminado correctamente.");
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
                    Modelo x = vista.tablaModelo.getModelo(fila);
                    vista.txtId.setText(String.valueOf(x.getIdModelo()));
                    vista.txtNombre.setText(x.getNombre() == null ? "" : x.getNombre());
                    vista.seleccionarMarca(x.getIdMarca());
                }
            }
        });
    }
}
