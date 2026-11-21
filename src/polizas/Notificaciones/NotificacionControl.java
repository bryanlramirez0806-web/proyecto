package polizas.Notificaciones;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

public class NotificacionControl {
    public NotificacionControl(NotificacionVista v) {
        NotificacionModelo m = new NotificacionModelo();
        v.btnConsultar.addActionListener(e -> v.actualizarVista(m.read()));
        v.btnAgregar.addActionListener(e -> {
            if(m.create(new Notificacion(0, Integer.parseInt(v.txtIdPoliza.getText()), Integer.parseInt(v.txtIdUsuario.getText()), v.txtTipo.getText(), v.txtFecha.getText(), v.txtMedio.getText(), Double.parseDouble(v.txtCosto.getText()), true)) > 0) {
                JOptionPane.showMessageDialog(v, "Notificación agregada"); v.actualizarVista(m.read());
            }
        });
        v.btnActualizar.addActionListener(e -> {
            if(m.update(new Notificacion(Integer.parseInt(v.txtId.getText()), Integer.parseInt(v.txtIdPoliza.getText()), Integer.parseInt(v.txtIdUsuario.getText()), v.txtTipo.getText(), v.txtFecha.getText(), v.txtMedio.getText(), Double.parseDouble(v.txtCosto.getText()), true)) > 0) {
                JOptionPane.showMessageDialog(v, "Actualizado"); v.actualizarVista(m.read());
            }
        });
        v.btnEliminar.addActionListener(e -> {
            if(m.delete(new Notificacion(Integer.parseInt(v.txtId.getText()), 0, 0, "", "", "", 0, true)) > 0) {
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
                    v.txtTipo.setText((String) v.modelo.getValueAt(f, 3));
                    v.txtFecha.setText((String) v.modelo.getValueAt(f, 4));
                    v.txtMedio.setText((String) v.modelo.getValueAt(f, 5));
                    v.txtCosto.setText(String.valueOf(v.modelo.getValueAt(f, 6)));
                }
            }
        });
    }
}