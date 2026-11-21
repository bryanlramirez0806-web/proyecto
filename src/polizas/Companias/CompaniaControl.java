package polizas.Companias;

import java.awt.event.MouseAdapter;
import java.util.List;
import javax.swing.JOptionPane;

public class CompaniaControl {
    private CompaniaVista cv;
    private CompaniaModelo cm;
    private List<Compania> lista;

    public CompaniaControl(CompaniaVista companiaVista) {
        this.cv = companiaVista;
        this.cm = new CompaniaModelo();
        companiaEventos();
    }

    public void companiaEventos() {
        // Consultar
        cv.btnConsultar.addActionListener(e -> {
            lista = cm.read();
            cv.actualizarVista(lista);
        });

        // Agregar
        cv.btnAgregar.addActionListener(e -> {
            String nombre = cv.txtNombre.getText();
            if (nombre.trim().isEmpty() || nombre.equals("Nombre")) {
                JOptionPane.showMessageDialog(cv, "Ingresa un nombre de compañía válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Compania c = new Compania(
                0,
                nombre,
                cv.txtTelefono.getText(),
                cv.txtCorreo.getText(),
                cv.txtSitioValidacion.getText(),
                true
            );

            int resultado = cm.create(c);
            if (resultado > 0) {
                JOptionPane.showMessageDialog(cv, "¡Compañía agregada correctamente!");
                cv.actualizarVista(cm.read());
            } else {
                JOptionPane.showMessageDialog(cv, "No se pudo agregar la compañía.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Actualizar
        cv.btnActualizar.addActionListener(e -> {
            try {
                int idCompania = Integer.parseInt(cv.txtIdCompania.getText());
                Compania c = new Compania(
                    idCompania,
                    cv.txtNombre.getText(),
                    cv.txtTelefono.getText(),
                    cv.txtCorreo.getText(),
                    cv.txtSitioValidacion.getText(),
                    true
                );

                int resultado = cm.update(c);
                JOptionPane.showMessageDialog(cv, resultado > 0 ? "Compañía actualizada correctamente" : "No se pudo actualizar");
                cv.actualizarVista(cm.read());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(cv, "El ID debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Eliminar
        cv.btnEliminar.addActionListener(e -> {
            try {
                int idCompania = Integer.parseInt(cv.txtIdCompania.getText());
                Compania c = new Compania(idCompania, "", "", "", "", true);

                int resultado = cm.delete(c);
                JOptionPane.showMessageDialog(cv, resultado > 0 ? "Compañía eliminada correctamente" : "No se pudo eliminar");
                cv.actualizarVista(cm.read());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(cv, "El ID debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Clic en la tabla
        cv.tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = cv.tabla.getSelectedRow();
                if (fila != -1) {
                    cv.txtIdCompania.setText(String.valueOf(cv.companiaModelo.getValueAt(fila, 0)));
                    cv.txtNombre.setText((String) cv.companiaModelo.getValueAt(fila, 1));
                    cv.txtTelefono.setText((String) cv.companiaModelo.getValueAt(fila, 2));
                    cv.txtCorreo.setText((String) cv.companiaModelo.getValueAt(fila, 3));
                    cv.txtSitioValidacion.setText((String) cv.companiaModelo.getValueAt(fila, 4));
                }
            }
        });
    }
}