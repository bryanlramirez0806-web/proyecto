package polizas.Cotizaciones;

import java.awt.event.MouseAdapter;
import java.util.List;
import javax.swing.JOptionPane;

public class CotizacionControl {
    private CotizacionVista cv;
    private CotizacionModelo cm;
    private List<Cotizacion> lista;

    public CotizacionControl(CotizacionVista cotizacionVista) {
        this.cv = cotizacionVista;
        this.cm = new CotizacionModelo();
        cotizacionEventos();
    }

    public void cotizacionEventos() {
        // Consultar
        cv.btnConsultar.addActionListener(e -> {
            lista = cm.read();
            cv.actualizarVista(lista);
        });

        // Agregar
        cv.btnAgregar.addActionListener(e -> {
            try {
                int idVehiculo = Integer.parseInt(cv.txtIdVehiculo.getText());
                int idUsuario = Integer.parseInt(cv.txtIdUsuario.getText());
                int idCompania = Integer.parseInt(cv.txtIdCompania.getText());
                int idTipo = Integer.parseInt(cv.txtIdTipo.getText());
                double costo = Double.parseDouble(cv.txtCosto.getText());

                Cotizacion c = new Cotizacion(
                    0, idVehiculo, idUsuario, idCompania, idTipo,
                    cv.txtFecha.getText(), costo,
                    cv.txtOpcionesPago.getText(),
                    cv.txtEstatus.getText()
                );

                int resultado = cm.create(c);
                if (resultado > 0) {
                    JOptionPane.showMessageDialog(cv, "¡Cotización agregada correctamente!");
                    cv.actualizarVista(cm.read());
                } else {
                    JOptionPane.showMessageDialog(cv, "No se pudo agregar. Verifica que los IDs foráneos existan.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(cv, "Verifica que los campos numéricos sean correctos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Actualizar
        cv.btnActualizar.addActionListener(e -> {
            try {
                int idCotizacion = Integer.parseInt(cv.txtIdCotizacion.getText());
                int idVehiculo = Integer.parseInt(cv.txtIdVehiculo.getText());
                int idUsuario = Integer.parseInt(cv.txtIdUsuario.getText());
                int idCompania = Integer.parseInt(cv.txtIdCompania.getText());
                int idTipo = Integer.parseInt(cv.txtIdTipo.getText());
                double costo = Double.parseDouble(cv.txtCosto.getText());

                Cotizacion c = new Cotizacion(
                    idCotizacion, idVehiculo, idUsuario, idCompania, idTipo,
                    cv.txtFecha.getText(), costo,
                    cv.txtOpcionesPago.getText(),
                    cv.txtEstatus.getText()
                );

                int resultado = cm.update(c);
                JOptionPane.showMessageDialog(cv, resultado > 0 ? "Actualizado correctamente" : "No se pudo actualizar");
                cv.actualizarVista(cm.read());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(cv, "Verifica los formatos numéricos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Eliminar
        cv.btnEliminar.addActionListener(e -> {
            try {
                int idCotizacion = Integer.parseInt(cv.txtIdCotizacion.getText());
                Cotizacion c = new Cotizacion(idCotizacion, 0, 0, 0, 0, "", 0.0, "", "");

                int resultado = cm.delete(c);
                JOptionPane.showMessageDialog(cv, resultado > 0 ? "Eliminado correctamente" : "No se pudo eliminar");
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
                    cv.txtIdCotizacion.setText(String.valueOf(cv.cotizacionModelo.getValueAt(fila, 0)));
                    cv.txtIdVehiculo.setText(String.valueOf(cv.cotizacionModelo.getValueAt(fila, 1)));
                    cv.txtIdUsuario.setText(String.valueOf(cv.cotizacionModelo.getValueAt(fila, 2)));
                    cv.txtIdCompania.setText(String.valueOf(cv.cotizacionModelo.getValueAt(fila, 3)));
                    cv.txtIdTipo.setText(String.valueOf(cv.cotizacionModelo.getValueAt(fila, 4)));
                    cv.txtFecha.setText((String) cv.cotizacionModelo.getValueAt(fila, 5));
                    cv.txtCosto.setText(String.valueOf(cv.cotizacionModelo.getValueAt(fila, 6)));
                    cv.txtOpcionesPago.setText((String) cv.cotizacionModelo.getValueAt(fila, 7));
                    cv.txtEstatus.setText((String) cv.cotizacionModelo.getValueAt(fila, 8));
                }
            }
        });
    }
}