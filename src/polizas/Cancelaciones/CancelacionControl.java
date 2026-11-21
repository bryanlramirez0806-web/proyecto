package polizas.Cancelaciones;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

public class CancelacionControl {
    public CancelacionControl(CancelacionVista v) {
        CancelacionModelo m = new CancelacionModelo();
        v.btnConsultar.addActionListener(e -> v.actualizarVista(m.read()));
        v.btnAgregar.addActionListener(e -> {
            if(m.create(new Cancelacion(0, Integer.parseInt(v.txtIdPoliza.getText()), Integer.parseInt(v.txtIdUsuario.getText()), v.txtFecha.getText(), v.txtMotivo.getText(), Double.parseDouble(v.txtMonto.getText()), v.txtEstatus.getText())) > 0) {
                JOptionPane.showMessageDialog(v, "Cancelación registrada"); v.actualizarVista(m.read());
            }
        });
        v.btnActualizar.addActionListener(e -> {
            if(m.update(new Cancelacion(Integer.parseInt(v.txtId.getText()), Integer.parseInt(v.txtIdPoliza.getText()), Integer.parseInt(v.txtIdUsuario.getText()), v.txtFecha.getText(), v.txtMotivo.getText(), Double.parseDouble(v.txtMonto.getText()), v.txtEstatus.getText())) > 0) {
                JOptionPane.showMessageDialog(v, "Actualizado"); v.actualizarVista(m.read());
            }
        });
        v.btnEliminar.addActionListener(e -> {
            if(m.delete(new Cancelacion(Integer.parseInt(v.txtId.getText()), 0, 0, "", "", 0, "")) > 0) {
                JOptionPane.showMessageDialog(v, "Eliminado"); v.actualizarVista(m.read());
            }
        });
        v.tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int f = v.tabla.getSelectedRow();
                if(f != -1) {
                    v.txtId.setText(String.valueOf(v.modelo.getValueAt(f, 0)));
                    v.txtIdPoliza.setText(String.valueOf(v.modelo.getValueAt(f, 1)));
                    v.txtIdUsuario.setText(String.valueOf(v.modelo.getValueAt(f, 2)));
                    v.txtFecha.setText((String) v.modelo.getValueAt(f, 3));
                    v.txtMotivo.setText((String) v.modelo.getValueAt(f, 4));
                    v.txtMonto.setText(String.valueOf(v.modelo.getValueAt(f, 5)));
                    v.txtEstatus.setText((String) v.modelo.getValueAt(f, 6));
                }
            }
        });
    }
}
