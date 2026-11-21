package polizas.TiposPoliza;

import java.awt.event.MouseAdapter;
import java.util.List;
import javax.swing.JOptionPane;

public class TipoPolizaControl {
    private TipoPolizaVista tpv;
    private TipoPolizaModelo tpm;
    private List<TipoPoliza> lista;

    public TipoPolizaControl(TipoPolizaVista tipoPolizaVista) {
        this.tpv = tipoPolizaVista;
        this.tpm = new TipoPolizaModelo();
        tipoPolizaEventos();
    }

    public void tipoPolizaEventos() {
        // Consultar
        tpv.btnConsultar.addActionListener(e -> {
            lista = tpm.read();
            tpv.actualizarVista(lista);
        });

        // Agregar
        tpv.btnAgregar.addActionListener(e -> {
            String nombre = tpv.txtNombre.getText();
            if (nombre.trim().isEmpty() || nombre.equals("Nombre")) {
                JOptionPane.showMessageDialog(tpv, "Ingresa un nombre válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            TipoPoliza tp = new TipoPoliza(
                0,
                nombre,
                tpv.txtDescripcion.getText(),
                true
            );

            int resultado = tpm.create(tp);
            if (resultado > 0) {
                JOptionPane.showMessageDialog(tpv, "¡Tipo de póliza agregado correctamente!");
                tpv.actualizarVista(tpm.read());
            } else {
                JOptionPane.showMessageDialog(tpv, "No se pudo agregar el tipo de póliza.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Actualizar
        tpv.btnActualizar.addActionListener(e -> {
            try {
                int idTipo = Integer.parseInt(tpv.txtIdTipo.getText());
                TipoPoliza tp = new TipoPoliza(
                    idTipo,
                    tpv.txtNombre.getText(),
                    tpv.txtDescripcion.getText(),
                    true
                );

                int resultado = tpm.update(tp);
                JOptionPane.showMessageDialog(tpv, resultado > 0 ? "Actualizado correctamente" : "No se pudo actualizar");
                tpv.actualizarVista(tpm.read());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(tpv, "El ID debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Eliminar
        tpv.btnEliminar.addActionListener(e -> {
            try {
                int idTipo = Integer.parseInt(tpv.txtIdTipo.getText());
                TipoPoliza tp = new TipoPoliza(idTipo, "", "", true);

                int resultado = tpm.delete(tp);
                JOptionPane.showMessageDialog(tpv, resultado > 0 ? "Eliminado correctamente" : "No se pudo eliminar");
                tpv.actualizarVista(tpm.read());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(tpv, "El ID debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Clic en la tabla
        tpv.tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = tpv.tabla.getSelectedRow();
                if (fila != -1) {
                    tpv.txtIdTipo.setText(String.valueOf(tpv.tipoPolizaModelo.getValueAt(fila, 0)));
                    tpv.txtNombre.setText((String) tpv.tipoPolizaModelo.getValueAt(fila, 1));
                    tpv.txtDescripcion.setText((String) tpv.tipoPolizaModelo.getValueAt(fila, 2));
                }
            }
        });
    }
}