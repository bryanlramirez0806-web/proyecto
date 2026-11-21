package polizas.Pagos;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

public class PagoControl {
    public PagoControl(PagoVista v) {
        PagoModelo m = new PagoModelo();
        v.btnConsultar.addActionListener(e -> v.actualizarVista(m.read()));
        v.btnAgregar.addActionListener(e -> {
            if(m.create(new Pago(0, Integer.parseInt(v.txtIdPoliza.getText()), Integer.parseInt(v.txtParcialidad.getText()), Double.parseDouble(v.txtMonto.getText()), v.txtFLimite.getText(), v.txtFPago.getText(), v.txtMetodo.getText(), v.txtComprobante.getText(), v.txtEstatus.getText())) > 0) {
                JOptionPane.showMessageDialog(v, "Pago registrado"); v.actualizarVista(m.read());
            }
        });
        v.btnActualizar.addActionListener(e -> {
            if(m.update(new Pago(Integer.parseInt(v.txtIdPago.getText()), Integer.parseInt(v.txtIdPoliza.getText()), Integer.parseInt(v.txtParcialidad.getText()), Double.parseDouble(v.txtMonto.getText()), v.txtFLimite.getText(), v.txtFPago.getText(), v.txtMetodo.getText(), v.txtComprobante.getText(), v.txtEstatus.getText())) > 0) {
                JOptionPane.showMessageDialog(v, "Pago actualizado"); v.actualizarVista(m.read());
            }
        });
        v.btnEliminar.addActionListener(e -> {
            if(m.delete(new Pago(Integer.parseInt(v.txtIdPago.getText()), 0, 0, 0, "", "", "", "", "")) > 0) {
                JOptionPane.showMessageDialog(v, "Pago eliminado"); v.actualizarVista(m.read());
            }
        });
        v.tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int f = v.tabla.getSelectedRow();
                if(f != -1) {
                    v.txtIdPago.setText(String.valueOf(v.modelo.getValueAt(f, 0)));
                    v.txtIdPoliza.setText(String.valueOf(v.modelo.getValueAt(f, 1)));
                    v.txtParcialidad.setText(String.valueOf(v.modelo.getValueAt(f, 2)));
                    v.txtMonto.setText(String.valueOf(v.modelo.getValueAt(f, 3)));
                    v.txtFLimite.setText((String) v.modelo.getValueAt(f, 4));
                    v.txtFPago.setText((String) v.modelo.getValueAt(f, 5));
                    v.txtMetodo.setText((String) v.modelo.getValueAt(f, 6));
                    v.txtComprobante.setText((String) v.modelo.getValueAt(f, 7));
                    v.txtEstatus.setText((String) v.modelo.getValueAt(f, 8));
                }
            }
        });
    }
}