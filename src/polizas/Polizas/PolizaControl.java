package polizas.Polizas;

import java.awt.event.MouseAdapter;
import java.util.List;
import javax.swing.JOptionPane;

public class PolizaControl {
    private PolizaVista pv;
    private PolizaModelo pm;
    private List<PolizaEntidad> lista;

    public PolizaControl(PolizaVista polizaVista) {
        this.pv = polizaVista;
        this.pm = new PolizaModelo();
        polizaEventos();
    }

    private Integer parseIdOpcional(String texto) {
        if (texto == null) {
            return null;
        }
        String limpio = texto.trim();
        if (limpio.isEmpty() || limpio.equals("0")) {
            return null;
        }
        return Integer.parseInt(limpio);
    }

    public void polizaEventos() {
        // Consultar
        pv.btnConsultar.addActionListener(e -> {
            lista = pm.read();
            pv.actualizarVista(lista);
        });

        // Agregar
        pv.btnAgregar.addActionListener(e -> {
            try {
                String numPoliza = pv.txtNumeroPoliza.getText().trim();
                int idCot = Integer.parseInt(pv.txtIdCotizacion.getText().trim());
                int idVeh = Integer.parseInt(pv.txtIdVehiculo.getText().trim());
                int idCom = Integer.parseInt(pv.txtIdCompania.getText().trim());
                int idUsu = Integer.parseInt(pv.txtIdUsuario.getText().trim());
                int idTip = Integer.parseInt(pv.txtIdTipo.getText().trim());
                Integer idAnt = parseIdOpcional(pv.txtIdPolizaAnterior.getText());

                String fechaEmision = pv.txtFechaEmision.getText().trim();
                String fechaInicio = pv.txtFechaInicio.getText().trim();
                String fechaVencimiento = pv.txtFechaVencimiento.getText().trim();
                String estatus = pv.txtEstatus.getText().trim().toUpperCase();
                double prima = Double.parseDouble(pv.txtPrimaTotal.getText().trim());

                if (fechaInicio.isEmpty() || fechaVencimiento.isEmpty() || estatus.isEmpty()) {
                    JOptionPane.showMessageDialog(
                        pv,
                        "Completa fecha de inicio, fecha de vencimiento y estatus.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                if (prima <= 0) {
                    JOptionPane.showMessageDialog(
                        pv,
                        "La prima total debe ser mayor a 0.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                PolizaEntidad p = new PolizaEntidad(
                    0,
                    numPoliza,
                    idCot,
                    idVeh,
                    idCom,
                    idUsu,
                    idTip,
                    idAnt,
                    fechaEmision,
                    fechaInicio,
                    fechaVencimiento,
                    prima,
                    estatus
                );

                int resultado = pm.create(p);
                if (resultado > 0) {
                    JOptionPane.showMessageDialog(pv, "¡Póliza registrada correctamente!");
                    pv.actualizarVista(pm.read());
                } else {
                    JOptionPane.showMessageDialog(
                        pv,
                        "No se pudo registrar. Verifica los IDs foráneos, fechas, prima o estatus.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    pv,
                    "Verifica que los campos numéricos (IDs y prima) sean correctos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Actualizar
        pv.btnActualizar.addActionListener(e -> {
            try {
                int idPol = Integer.parseInt(pv.txtIdPoliza.getText().trim());
                int idCot = Integer.parseInt(pv.txtIdCotizacion.getText().trim());
                int idVeh = Integer.parseInt(pv.txtIdVehiculo.getText().trim());
                int idCom = Integer.parseInt(pv.txtIdCompania.getText().trim());
                int idUsu = Integer.parseInt(pv.txtIdUsuario.getText().trim());
                int idTip = Integer.parseInt(pv.txtIdTipo.getText().trim());
                Integer idAnt = parseIdOpcional(pv.txtIdPolizaAnterior.getText());

                String numPoliza = pv.txtNumeroPoliza.getText().trim();
                String fechaEmision = pv.txtFechaEmision.getText().trim();
                String fechaInicio = pv.txtFechaInicio.getText().trim();
                String fechaVencimiento = pv.txtFechaVencimiento.getText().trim();
                String estatus = pv.txtEstatus.getText().trim().toUpperCase();
                double prima = Double.parseDouble(pv.txtPrimaTotal.getText().trim());

                if (prima <= 0) {
                    JOptionPane.showMessageDialog(
                        pv,
                        "La prima total debe ser mayor a 0.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                PolizaEntidad p = new PolizaEntidad(
                    idPol,
                    numPoliza,
                    idCot,
                    idVeh,
                    idCom,
                    idUsu,
                    idTip,
                    idAnt,
                    fechaEmision,
                    fechaInicio,
                    fechaVencimiento,
                    prima,
                    estatus
                );

                int resultado = pm.update(p);
                JOptionPane.showMessageDialog(
                    pv,
                    resultado > 0 ? "Actualizado correctamente" : "No se pudo actualizar"
                );
                pv.actualizarVista(pm.read());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    pv,
                    "Verifica los formatos numéricos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Eliminar
        pv.btnEliminar.addActionListener(e -> {
            try {
                int idPol = Integer.parseInt(pv.txtIdPoliza.getText().trim());
                PolizaEntidad p = new PolizaEntidad(idPol, "", 0, 0, 0, 0, 0, null, "", "", "", 0.0, "");

                int resultado = pm.delete(p);
                JOptionPane.showMessageDialog(
                    pv,
                    resultado > 0 ? "Eliminado correctamente" : "No se pudo eliminar"
                );
                pv.actualizarVista(pm.read());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    pv,
                    "El ID debe ser un número válido.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Clic en la tabla
        pv.tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = pv.tabla.getSelectedRow();
                if (fila != -1) {
                    pv.txtIdPoliza.setText(String.valueOf(pv.polizaModelo.getValueAt(fila, 0)));

                    Object numPol = pv.polizaModelo.getValueAt(fila, 1);
                    pv.txtNumeroPoliza.setText(numPol != null ? String.valueOf(numPol) : "");

                    pv.txtIdCotizacion.setText(String.valueOf(pv.polizaModelo.getValueAt(fila, 2)));
                    pv.txtIdVehiculo.setText(String.valueOf(pv.polizaModelo.getValueAt(fila, 3)));
                    pv.txtIdCompania.setText(String.valueOf(pv.polizaModelo.getValueAt(fila, 4)));
                    pv.txtIdUsuario.setText(String.valueOf(pv.polizaModelo.getValueAt(fila, 5)));
                    pv.txtIdTipo.setText(String.valueOf(pv.polizaModelo.getValueAt(fila, 6)));

                    Object ant = pv.polizaModelo.getValueAt(fila, 7);
                    pv.txtIdPolizaAnterior.setText(ant != null ? String.valueOf(ant) : "");

                    Object fEmision = pv.polizaModelo.getValueAt(fila, 8);
                    pv.txtFechaEmision.setText(fEmision != null ? String.valueOf(fEmision) : "");

                    Object fInicio = pv.polizaModelo.getValueAt(fila, 9);
                    pv.txtFechaInicio.setText(fInicio != null ? String.valueOf(fInicio) : "");

                    Object fVenc = pv.polizaModelo.getValueAt(fila, 10);
                    pv.txtFechaVencimiento.setText(fVenc != null ? String.valueOf(fVenc) : "");

                    pv.txtPrimaTotal.setText(String.valueOf(pv.polizaModelo.getValueAt(fila, 11)));

                    Object est = pv.polizaModelo.getValueAt(fila, 12);
                    pv.txtEstatus.setText(est != null ? String.valueOf(est) : "");
                }
            }
        });
    }
}