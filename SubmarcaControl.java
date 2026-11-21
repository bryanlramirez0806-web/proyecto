package polizas.Submarcas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

public class SubmarcaControl {
    public SubmarcaControl(SubmarcaVista v) {
        SubmarcaModelo m = new SubmarcaModelo();
        v.btnConsultar.addActionListener(e -> v.actualizarVista(m.read()));
        v.btnAgregar.addActionListener(e -> guardar(v, m, false));
        v.btnActualizar.addActionListener(e -> guardar(v, m, true));

        v.btnEliminar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(v.txtIdSubmarca.getText().trim());
                int resultado = m.delete(new Submarca(id, 0, ""));
                JOptionPane.showMessageDialog(v, resultado > 0 ? "Submarca eliminada" : "No se pudo eliminar la submarca");
                v.actualizarVista(m.read());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(v, "Escribe un ID de submarca válido", "Dato inválido", JOptionPane.WARNING_MESSAGE);
            }
        });

        v.tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = v.tabla.getSelectedRow();
                if (fila != -1) {
                    v.txtIdSubmarca.setText(String.valueOf(v.modelo.getValueAt(fila, 0)));
                    v.txtIdMarca.setText(String.valueOf(v.modelo.getValueAt(fila, 1)));
                    v.txtNombreSubmarca.setText(String.valueOf(v.modelo.getValueAt(fila, 2)));
                }
            }
        });
    }

    private void guardar(SubmarcaVista v, SubmarcaModelo m, boolean actualizar) {
        try {
            int idMarca = Integer.parseInt(v.txtIdMarca.getText().trim());
            int idSubmarca = actualizar ? Integer.parseInt(v.txtIdSubmarca.getText().trim()) : 0;
            String nombre = v.txtNombreSubmarca.getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(v, "Escribe el nombre de la submarca", "Dato inválido", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int resultado = actualizar
                ? m.update(new Submarca(idSubmarca, idMarca, nombre))
                : m.create(new Submarca(0, idMarca, nombre));
            JOptionPane.showMessageDialog(v, resultado > 0
                ? (actualizar ? "Submarca actualizada" : "Submarca agregada")
                : "No se pudo guardar. Verifica que la marca exista y que no haya duplicados");
            v.actualizarVista(m.read());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(v, "Escribe IDs válidos para la marca y la submarca", "Dato inválido", JOptionPane.WARNING_MESSAGE);
        }
    }
}