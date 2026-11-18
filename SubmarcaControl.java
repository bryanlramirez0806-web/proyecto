package polizas.Submarcas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

public class SubmarcaControl {
    public SubmarcaControl(SubmarcaVista v) {
        SubmarcaModelo m = new SubmarcaModelo();
        v.btnConsultar.addActionListener(e -> v.actualizarVista(m.read()));
        
        v.btnAgregar.addActionListener(e -> {
            if(m.create(new Submarca(0, Integer.parseInt(v.txtIdMarca.getText()), v.txtNombreSubmarca.getText())) > 0) {
                JOptionPane.showMessageDialog(v, "Submarca agregada con éxito");
                v.actualizarVista(m.read());
            }
        });
        
        v.btnActualizar.addActionListener(e -> {
            if(m.update(new Submarca(Integer.parseInt(v.txtIdSubmarca.getText()), Integer.parseInt(v.txtIdMarca.getText()), v.txtNombreSubmarca.getText())) > 0) {
                JOptionPane.showMessageDialog(v, "Submarca actualizada");
                v.actualizarVista(m.read());
            }
        });
        
        v.btnEliminar.addActionListener(e -> {
            if(m.delete(new Submarca(Integer.parseInt(v.txtIdSubmarca.getText()), 0, "")) > 0) {
                JOptionPane.showMessageDialog(v, "Submarca eliminada");
                v.actualizarVista(m.read());
            }
        });
        
        v.tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int f = v.tabla.getSelectedRow();
                if(f != -1) {
                    v.txtIdSubmarca.setText(String.valueOf(v.modelo.getValueAt(f, 0)));
                    v.txtIdMarca.setText(String.valueOf(v.modelo.getValueAt(f, 1)));
                    v.txtNombreSubmarca.setText((String) v.modelo.getValueAt(f, 2));
                }
            }
        });
    }
}