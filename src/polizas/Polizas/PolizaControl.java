package polizas.Polizas;

import java.awt.event.MouseAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.JOptionPane;
import polizas.Aseguradoras.Aseguradora;
import polizas.Aseguradoras.AseguradoraModelo;
import polizas.FormasPago.FormaPago;
import polizas.FormasPago.FormaPagoModelo;
import polizas.Vehiculos.Vehiculo;
import polizas.Vehiculos.VehiculoModelo;

public class PolizaControl {
    private PolizaVista pv;
    private PolizaModelo pm;
    private List<PolizaEntidad> lista;

    public PolizaControl(PolizaVista polizaVista) {
        this.pv = polizaVista;
        this.pm = new PolizaModelo();
        cargarCombos();
        polizaEventos();
        pv.actualizarVista(pm.read()); // Carga la lista al abrir la pantalla
    }

    // Reutiliza los modelos de Vehículos, Aseguradoras y Formas de pago para llenar las listas
    private void cargarCombos() {
        List<Vehiculo> vehiculos = new VehiculoModelo().readActivos();
        List<Aseguradora> aseguradoras = new AseguradoraModelo().read();
        List<FormaPago> formas = new FormaPagoModelo().read();
        pv.llenarCombos(vehiculos, aseguradoras, formas);
    }

    // Revisa que una fecha tenga el formato AAAA-MM-DD
    private boolean fechaValida(String texto) {
        try {
            LocalDate.parse(texto);
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }

    /**
     * Revisa que los datos del formulario estén completos y bien escritos.
     * Regresa el mensaje del problema, o null si todo está bien.
     */
    private String validarFormulario() {
        if (pv.cmbVehiculo.getSelectedItem() == null) return "Escoge el vehículo.";
        if (pv.cmbAseguradora.getSelectedItem() == null) return "Escoge la aseguradora.";
        if (pv.cmbFormaPago.getSelectedItem() == null) return "Escoge la forma de pago.";

        String inicio = pv.txtFechaInicio.getText().trim();
        String fin = pv.txtFechaFin.getText().trim();
        if (!fechaValida(inicio) || !fechaValida(fin)) {
            return "Las fechas de inicio y fin deben escribirse como AAAA-MM-DD.";
        }
        // RN-1: la fecha de fin debe ser después de la de inicio
        if (!LocalDate.parse(fin).isAfter(LocalDate.parse(inicio))) {
            return "La fecha de fin debe ser después de la fecha de inicio.";
        }

        try {
            if (Double.parseDouble(pv.txtPrimaTotal.getText().trim().replace(",", "")) <= 0) {
                return "La prima total debe ser mayor a cero.";
            }
        } catch (NumberFormatException ex) {
            return "La prima total debe ser un número, por ejemplo 8500.00";
        }

        // Renovación: la póliza anterior debe existir y ser del mismo vehículo
        String ant = pv.txtIdPolizaAnterior.getText().trim();
        if (!ant.isEmpty()) {
            if (!ant.matches("\\d+")) return "El ID de la póliza anterior debe ser un número.";
            int vehiculoAnterior = pm.vehiculoDePoliza(Integer.parseInt(ant));
            if (vehiculoAnterior == 0) return "La póliza anterior #" + ant + " no existe.";
            Vehiculo v = (Vehiculo) pv.cmbVehiculo.getSelectedItem();
            if (vehiculoAnterior != v.getIdVehiculo()) return "La póliza anterior debe ser del mismo vehículo.";
            int idActual = pv.txtIdPoliza.getText().isEmpty() ? 0 : Integer.parseInt(pv.txtIdPoliza.getText());
            if (Integer.parseInt(ant) == idActual) {
                return "Una póliza no puede ser su propia póliza anterior.";
            }
            // RN-12: cada póliza se renueva una sola vez
            if (pm.yaFueRenovada(Integer.parseInt(ant), idActual)) {
                return "La póliza #" + ant + " ya fue renovada por otra póliza. Cada póliza se renueva una sola vez.";
            }
        }
        return null;
    }

    // Arma un objeto PolizaEntidad con lo que está en el formulario (ya validado)
    private PolizaEntidad leerFormulario(int id) {
        Vehiculo v = (Vehiculo) pv.cmbVehiculo.getSelectedItem();
        Aseguradora a = (Aseguradora) pv.cmbAseguradora.getSelectedItem();
        FormaPago f = (FormaPago) pv.cmbFormaPago.getSelectedItem();
        String ant = pv.txtIdPolizaAnterior.getText().trim();

        return new PolizaEntidad(
            id,
            pv.txtNumeroPoliza.getText().trim(),
            pv.txtFechaInicio.getText().trim(),
            pv.txtFechaFin.getText().trim(),
            Double.parseDouble(pv.txtPrimaTotal.getText().trim().replace(",", "")),
            pv.cmbEstado.getSelectedItem().toString(),
            v.getIdVehiculo(),
            a.getIdAseguradora(),
            f.getIdFormaPago(),
            ant.isEmpty() ? null : Integer.parseInt(ant)
        );
    }

    /**
     * Reglas de negocio de la póliza que necesitan consultar la base de datos.
     * Regresa el mensaje del problema, o null si se puede guardar.
     */
    private String validarReglas(PolizaEntidad p, boolean esNueva) {
        String estado = p.getEstado();

        if (esNueva) {
            // RF-15: una póliza nueva se crea en trámite
            if (!estado.equals("EN_TRAMITE")) {
                return "Una póliza nueva se registra EN_TRAMITE.\nCuando la aseguradora la emita y tenga coberturas y pago, cámbiala a ACTIVA.";
            }
        } else {
            String actual = pm.estadoActual(p.getIdPoliza());
            // RN-11: una póliza vencida no se modifica, solo se renueva
            if ("VENCIDA".equals(actual)) {
                return "La póliza está VENCIDA y ya no se puede modificar.\nPara extenderla registra una renovación (póliza nueva con esta como anterior).";
            }
            // RN-13: una póliza cancelada no se modifica
            if ("CANCELADA".equals(actual)) {
                return "La póliza está CANCELADA y ya no se puede modificar.";
            }
            // RN-13: solo se cancelan pólizas activas con una solicitud de cancelación aprobada
            if (estado.equals("CANCELADA")) {
                if (!"ACTIVA".equals(actual)) return "Solo se pueden cancelar pólizas ACTIVAS.";
                if (!pm.tieneCancelacionAprobada(p.getIdPoliza())) {
                    return "Para cancelar la póliza primero debe haber una solicitud de Cancelación APROBADA.";
                }
            }
            // RN-9: solo pasa a vencida si ya pasó su fecha de fin
            if (estado.equals("VENCIDA") && !LocalDate.parse(p.getFechaFin()).isBefore(LocalDate.now())) {
                return "La póliza todavía no llega a su fecha de fin.";
            }
        }

        // RN-8: un vehículo no puede tener dos pólizas vigentes con fechas que se crucen
        if (estado.equals("ACTIVA") || estado.equals("EN_TRAMITE")) {
            if (pm.hayPolizaCruzada(p.getIdVehiculo(), p.getFechaInicio(), p.getFechaFin(), p.getIdPoliza())) {
                return "Este vehículo ya tiene otra póliza activa o en trámite con fechas que se cruzan.";
            }
        }

        // RN-6 y RN-3: requisitos para pasar a ACTIVA
        if (estado.equals("ACTIVA")) {
            if (p.getNumeroPoliza().isEmpty()) {
                return "Para activar la póliza escribe el número que dio la aseguradora.";
            }
            if (pm.contarCoberturas(p.getIdPoliza()) == 0) {
                return "Para activar la póliza debe tener al menos una cobertura.";
            }
            if (pm.contarPagosValidos(p.getIdPoliza()) == 0) {
                return "Para activar la póliza debe tener al menos un pago registrado (no anulado).";
            }
        }
        return null;
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(pv, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarAviso(String mensaje) {
        JOptionPane.showMessageDialog(pv, mensaje, "Advertencia", JOptionPane.WARNING_MESSAGE);
    }

    public void polizaEventos() {
        // Consultar (también pasa a VENCIDA las pólizas que ya vencieron, RF-29)
        pv.btnConsultar.addActionListener(e -> {
            cargarCombos();
            lista = pm.read();
            pv.actualizarVista(lista);
            if (!pm.getMensajeError().isEmpty()) {
                mostrarError(pm.getMensajeError());
            }
        });

        // Buscar por número, cliente o placas (RF-17)
        pv.btnBuscar.addActionListener(e -> {
            String texto = pv.txtBuscar.getText().trim();
            lista = texto.isEmpty() ? pm.read() : pm.buscar(texto);
            pv.actualizarVista(lista);
            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(pv, "No se encontraron pólizas con \"" + texto + "\".");
            }
        });

        // Agregar (RF-15)
        pv.btnAgregar.addActionListener(e -> {
            String problema = validarFormulario();
            if (problema != null) {
                mostrarAviso(problema);
                return;
            }
            PolizaEntidad p = leerFormulario(0);
            problema = validarReglas(p, true);
            if (problema != null) {
                mostrarAviso(problema);
                return;
            }
            if (pm.create(p) > 0) {
                JOptionPane.showMessageDialog(pv, "¡Póliza registrada correctamente!");
                pv.limpiarCampos();
                pv.actualizarVista(pm.read());
            } else {
                mostrarError("No se pudo registrar la póliza.\n" + pm.getMensajeError());
            }
        });

        // Actualizar (RF-16 y RF-19)
        pv.btnActualizar.addActionListener(e -> {
            if (pv.txtIdPoliza.getText().isEmpty()) {
                JOptionPane.showMessageDialog(pv, "Selecciona una póliza de la tabla.");
                return;
            }
            String problema = validarFormulario();
            if (problema != null) {
                mostrarAviso(problema);
                return;
            }
            PolizaEntidad p = leerFormulario(Integer.parseInt(pv.txtIdPoliza.getText()));
            problema = validarReglas(p, false);
            if (problema != null) {
                mostrarAviso(problema);
                return;
            }
            if (pm.update(p) > 0) {
                JOptionPane.showMessageDialog(pv, "Póliza actualizada correctamente.");
                pv.actualizarVista(pm.read());
            } else {
                mostrarError("No se pudo actualizar.\n" + pm.getMensajeError());
            }
        });

        // Eliminar: solo pólizas EN_TRAMITE (RN-14, las demás guardan su historial)
        pv.btnEliminar.addActionListener(e -> {
            if (pv.txtIdPoliza.getText().isEmpty()) {
                JOptionPane.showMessageDialog(pv, "Selecciona una póliza de la tabla.");
                return;
            }
            int id = Integer.parseInt(pv.txtIdPoliza.getText());
            if (!"EN_TRAMITE".equals(pm.estadoActual(id))) {
                mostrarAviso("Solo se pueden eliminar pólizas EN_TRAMITE.\nLas pólizas activas se cancelan con una solicitud de Cancelación, para guardar el historial.");
                return;
            }
            int respuesta = JOptionPane.showConfirmDialog(pv,
                "¿Eliminar la póliza #" + id + "? Esta acción no se puede deshacer.",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }
            PolizaEntidad p = new PolizaEntidad(id, "", "", "", 0.0, "", 0, 0, 0, null);
            if (pm.delete(p) > 0) {
                JOptionPane.showMessageDialog(pv, "Póliza eliminada correctamente.");
                pv.limpiarCampos();
                pv.actualizarVista(pm.read());
            } else {
                mostrarError("No se pudo eliminar.\n" + pm.getMensajeError());
            }
        });

        // Limpiar el formulario
        pv.btnLimpiar.addActionListener(e -> pv.limpiarCampos());

        // Clic en la tabla para rellenar campos
        pv.tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int fila = pv.tabla.getSelectedRow();
                if (fila != -1) {
                    PolizaEntidad p = pv.polizaModelo.getPoliza(fila);
                    pv.txtIdPoliza.setText(String.valueOf(p.getIdPoliza()));
                    pv.txtNumeroPoliza.setText(p.getNumeroPoliza() == null ? "" : p.getNumeroPoliza());
                    pv.seleccionar(p.getIdVehiculo(), p.getIdAseguradora(), p.getIdFormaPago());
                    pv.txtIdPolizaAnterior.setText(p.getIdPolizaAnterior() != null ? String.valueOf(p.getIdPolizaAnterior()) : "");
                    pv.txtFechaInicio.setText(p.getFechaInicio());
                    pv.txtFechaFin.setText(p.getFechaFin());
                    pv.txtPrimaTotal.setText(String.format(java.util.Locale.US, "%.2f", p.getPrimaTotal()));
                    pv.cmbEstado.setSelectedItem(EstadoPoliza.valueOf(p.getEstado()));
                }
            }
        });
    }
}
