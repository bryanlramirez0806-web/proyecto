package polizas.DetalleCobertura;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

public class DetalleCoberturaControl {
    public DetalleCoberturaControl(DetalleCoberturaVista v) {
        DetalleCoberturaModelo m = new DetalleCoberturaModelo();
        v.btnConsultar.addActionListener(e -> v.actualizarVista(m.read()));
        v.btnAgregar.addActionListener(e -> {
            if(m.create(new DetalleCobertura(Integer.parseInt(v.txtIdPoliza.getText()), Integer.parseInt(v.txtIdCobertura.getText()), Double.parseDouble(v.txtSuma.getText()), Double.parseDouble(v.txtDeducible.getText()))) > 0) {
                JOptionPane.showMessageDialog(v, "Agregado correctamente"); v.actualizarVista(m.read());
            }
        });
        v.btnActualizar.addActionListener(e -> {
            if(m.update(new DetalleCobertura(Integer.parseInt(v.txtIdPoliza.getText()), Integer.parseInt(v.txtIdCobertura.getText()), Double.parseDouble(v.txtSuma.getText()), Double.parseDouble(v.txtDeducible.getText()))) > 0) {
                JOptionPane.showMessageDialog(v, "Actualizado correctamente"); v.actualizarVista(m.read());
            }
        });
        v.btnEliminar.addActionListener(e -> {
            if(m.delete(new DetalleCobertura(Integer.parseInt(v.txtIdPoliza.getText()), Integer.parseInt(v.txtIdCobertura.getText()), 0, 0)) > 0) {
                JOptionPane.showMessageDialog(v, "Eliminado correctamente"); v.actualizarVista(m.read());
            }
        });
        v.tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int f = v.tabla.getSelectedRow();
                if(f != -1) {
                    v.txtIdPoliza.setText(String.valueOf(v.modelo.getValueAt(f, 0)));
                    v.txtIdCobertura.setText(String.valueOf(v.modelo.getValueAt(f, 1)));
                    v.txtSuma.setText(String.valueOf(v.modelo.getValueAt(f, 2)));
                    v.txtDeducible.setText(String.valueOf(v.modelo.getValueAt(f, 3)));
                }
            }
        });
    }
}
