package polizas.Documentos;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

public class DocumentoControl {
    public DocumentoControl(DocumentoVista v) {
        DocumentoModelo m = new DocumentoModelo();
        v.btnConsultar.addActionListener(e -> v.actualizarVista(m.read()));
        v.btnAgregar.addActionListener(e -> {
            if(m.create(new Documento(0, Integer.parseInt(v.txtIdCliente.getText()), v.txtTipo.getText(), v.txtFecha.getText(), v.txtRuta.getText())) > 0) {
                JOptionPane.showMessageDialog(v, "Documento agregado"); v.actualizarVista(m.read());
            }
        });
        v.btnActualizar.addActionListener(e -> {
            if(m.update(new Documento(Integer.parseInt(v.txtId.getText()), Integer.parseInt(v.txtIdCliente.getText()), v.txtTipo.getText(), v.txtFecha.getText(), v.txtRuta.getText())) > 0) {
                JOptionPane.showMessageDialog(v, "Actualizado"); v.actualizarVista(m.read());
            }
        });
        v.btnEliminar.addActionListener(e -> {
            if(m.delete(new Documento(Integer.parseInt(v.txtId.getText()), 0, "", "", "")) > 0) {
                JOptionPane.showMessageDialog(v, "Eliminado"); v.actualizarVista(m.read());
            }
        });
        v.tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int f = v.tabla.getSelectedRow();
                if(f != -1) {
                    v.txtId.setText(String.valueOf(v.modelo.getValueAt(f, 0)));
                    v.txtIdCliente.setText(String.valueOf(v.modelo.getValueAt(f, 1)));
                    v.txtTipo.setText((String) v.modelo.getValueAt(f, 2));
                    v.txtFecha.setText((String) v.modelo.getValueAt(f, 3));
                    v.txtRuta.setText((String) v.modelo.getValueAt(f, 4));
                }
            }
        });
    }
}