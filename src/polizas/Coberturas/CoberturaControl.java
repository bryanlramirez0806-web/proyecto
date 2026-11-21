package polizas.Coberturas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

public class CoberturaControl {
    private CoberturaVista v; private CoberturaModelo m;
    public CoberturaControl(CoberturaVista vista) {
        this.v = vista; this.m = new CoberturaModelo();
        v.btnConsultar.addActionListener(e -> v.actualizarVista(m.read()));
        v.btnAgregar.addActionListener(e -> {
            if(m.create(new Cobertura(0, v.txtNombre.getText(), v.txtDescripcion.getText(), true)) > 0) {
                JOptionPane.showMessageDialog(v, "Agregado con éxito"); v.actualizarVista(m.read());
            }
        });
        v.btnActualizar.addActionListener(e -> {
            if(m.update(new Cobertura(Integer.parseInt(v.txtId.getText()), v.txtNombre.getText(), v.txtDescripcion.getText(), true)) > 0) {
                JOptionPane.showMessageDialog(v, "Actualizado con éxito"); v.actualizarVista(m.read());
            }
        });
        v.btnEliminar.addActionListener(e -> {
            if(m.delete(new Cobertura(Integer.parseInt(v.txtId.getText()), "", "", true)) > 0) {
                JOptionPane.showMessageDialog(v, "Eliminado con éxito"); v.actualizarVista(m.read());
            }
        });
        v.tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int f = v.tabla.getSelectedRow();
                if(f != -1) {
                    v.txtId.setText(String.valueOf(v.modelo.getValueAt(f, 0)));
                    v.txtNombre.setText((String) v.modelo.getValueAt(f, 1));
                    v.txtDescripcion.setText((String) v.modelo.getValueAt(f, 2));
                }
            }
        });
    }
}
