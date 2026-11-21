package polizas.Polizas;

import java.awt.event.MouseAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import polizas.Companias.Compania;
import polizas.Companias.CompaniaModelo;
import polizas.TiposPoliza.TipoPoliza;
import polizas.TiposPoliza.TipoPolizaModelo;
import polizas.Usuarios.Usuario;
import polizas.Usuarios.UsuarioModelo;
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

    // Reutiliza los modelos de los otros módulos para llenar las listas (solo registros activos)
    private void cargarCombos() {
        List<Vehiculo> vehiculos = new VehiculoModelo().readActivos();

        List<Compania> companias = new ArrayList<>();
        for (Object o : new CompaniaModelo().read()) {
            Compania c = (Compania) o;
            if (c.isActivo()) companias.add(c);
        }
        List<Usuario> usuarios = new ArrayList<>();
        for (Object o : new UsuarioModelo().read()) {
            Usuario u = (Usuario) o;
            if (u.isActivo()) usuarios.add(u);
        }
        List<TipoPoliza> tipos = new ArrayList<>();
        for (Object o : new TipoPolizaModelo().read()) {
            TipoPoliza t = (TipoPoliza) o;
            if (t.isActivo()) tipos.add(t);
        }
        pv.llenarCombos(vehiculos, companias, usuarios, tipos);
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
        if (pv.cmbCompania.getSelectedItem() == null) return "Escoge la compañía de seguros.";
        if (pv.cmbUsuario.getSelectedItem() == null) return "Escoge el vendedor que registra la póliza.";
        if (pv.cmbTipo.getSelectedItem() == null) return "Escoge el tipo de póliza.";

        String inicio = pv.txtFechaInicio.getText().trim();
        String vencimiento = pv.txtFechaVencimiento.getText().trim();
        String emision = pv.txtFechaEmision.getText().trim();
        if (!fechaValida(inicio) || !fechaValida(vencimiento)) {
            return "Las fechas de inicio y vencimiento deben escribirse como AAAA-MM-DD.";
        }
        if (!emision.isEmpty() && !fechaValida(emision)) {
            return "La fecha de emisión debe escribirse como AAAA-MM-DD (o dejarse vacía).";
        }
        // RN-1: la fecha de vencimiento debe ser después de la de inicio
        if (!LocalDate.parse(vencimiento).isAfter(LocalDate.parse(inicio))) {
            return "La fecha de vencimiento debe ser después de la fecha de inicio.";
        }

        try {
            if (Double.parseDouble(pv.txtPrimaTotal.getText().trim()) <= 0) {
                return "La prima total debe ser mayor a cero.";
            }
        } catch (NumberFormatException ex) {
            return "La prima total debe ser un número, por ejemplo 8500.00";
        }

        String cot = pv.txtIdCotizacion.getText().trim();
        if (!cot.isEmpty()) {
            if (!cot.matches("\\d+")) return "El ID de cotización debe ser un número.";
            // RN-15: solo una cotización aceptada puede convertirse en póliza
            String estatusCot = pm.estatusCotizacion(Integer.parseInt(cot));
            if (estatusCot == null) return "La cotización " + cot + " no existe.";
            if (!estatusCot.equals("ACEPTADA")) return "La cotización " + cot + " está " + estatusCot + ". Solo una cotización ACEPTADA puede volverse póliza.";
        }
        String ant = pv.txtIdPolizaAnterior.getText().trim();
        if (!ant.isEmpty() && !ant.matches("\\d+")) {
            return "El ID de la póliza anterior debe ser un número.";
        }
        return null;
    }

    // Arma un objeto PolizaEntidad con lo que está en el formulario (ya validado)
    private PolizaEntidad leerFormulario(int id) {
        Vehiculo v = (Vehiculo) pv.cmbVehiculo.getSelectedItem();
        Compania c = (Compania) pv.cmbCompania.getSelectedItem();
        Usuario u = (Usuario) pv.cmbUsuario.getSelectedItem();
        TipoPoliza t = (TipoPoliza) pv.cmbTipo.getSelectedItem();
        String cot = pv.txtIdCotizacion.getText().trim();
        String ant = pv.txtIdPolizaAnterior.getText().trim();

        return new PolizaEntidad(
            id,
            pv.txtNumeroPoliza.getText().trim(),
            cot.isEmpty() ? 0 : Integer.parseInt(cot),
            v.getIdVehiculo(),
            c.getIdCompania(),
            u.getIdUsuario(),
            t.getIdTipo(),
            ant.isEmpty() ? null : Integer.parseInt(ant),
            pv.txtFechaEmision.getText().trim(),
            pv.txtFechaInicio.getText().trim(),
            pv.txtFechaVencimiento.getText().trim(),
            Double.parseDouble(pv.txtPrimaTotal.getText().trim()),
            pv.cmbEstatus.getSelectedItem().toString()
        );
    }

    /**
     * Reglas de negocio de la póliza que necesitan consultar la base de datos.
     * Regresa el mensaje del problema, o null si se puede guardar.
     */
    private String validarReglas(PolizaEntidad p, boolean esNueva) {
        String estatus = p.getEstatus();

        if (esNueva) {
            // RF-15: una póliza nueva se crea en trámite
            if (!estatus.equals("EN_TRAMITE")) {
                return "Una póliza nueva se registra EN_TRAMITE.\nCuando la compañía la emita, agrégale coberturas y pago, y cámbiala a ACTIVA.";
            }
        } else {
            String actual = pm.estatusActual(p.getIdPoliza());
            // RN-11: una póliza vencida no se modifica, solo se renueva
            if ("VENCIDA".equals(actual)) {
                return "La póliza está VENCIDA y ya no se puede modificar.\nPara extenderla registra una renovación (póliza nueva ligada a esta).";
            }
            // RN-13: una póliza cancelada no se modifica
            if ("CANCELADA".equals(actual)) {
                return "La póliza está CANCELADA y ya no se puede modificar.";
            }
            // RN-13: solo se cancelan pólizas activas que tengan su cancelación registrada
            if (estatus.equals("CANCELADA")) {
                if (!"ACTIVA".equals(actual)) return "Solo se pueden cancelar pólizas ACTIVAS.";
                if (!pm.tieneCancelacion(p.getIdPoliza())) return "Primero registra la cancelación en el módulo de Cancelaciones.";
            }
            // RN-9: solo pasa a vencida si ya pasó su fecha
            if (estatus.equals("VENCIDA") && !LocalDate.parse(p.getFechaVencimiento()).isBefore(LocalDate.now())) {
                return "La póliza todavía no llega a su fecha de vencimiento.";
            }
        }

        // RN-8: un vehículo no puede tener dos pólizas vigentes con fechas que se crucen
        if (estatus.equals("ACTIVA") || estatus.equals("EN_TRAMITE")) {
            if (pm.hayPolizaCruzada(p.getIdVehiculo(), p.getFechaInicio(), p.getFechaVencimiento(), p.getIdPoliza())) {
                return "Este vehículo ya tiene otra póliza activa o en trámite con fechas que se cruzan.";
            }
        }

        // RN-6 y RN-3: requisitos para pasar a ACTIVA
        if (estatus.equals("ACTIVA")) {
            if (p.getNumeroPoliza().isEmpty()) {
                return "Para activar la póliza escribe el número que dio la compañía.";
            }
            if (p.getFechaEmision().isEmpty()) {
                return "Para activar la póliza escribe la fecha de emisión.";
            }
            if (pm.contarCoberturas(p.getIdPoliza()) == 0) {
                return "Para activar la póliza debe tener al menos una cobertura (módulo Detalle Coberturas).";
            }
            if (pm.contarPagosRealizados(p.getIdPoliza()) == 0) {
                return "Para activar la póliza debe tener al menos un pago registrado como PAGADO.";
            }
            if (pm.contarDocumentosCliente(p.getIdVehiculo()) < 3) {
                return "Para activar la póliza el cliente debe tener sus 3 documentos (INE, comprobante de domicilio y tarjeta de circulación).";
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
            String actual = pm.estatusActual(id);
            if (!"EN_TRAMITE".equals(actual)) {
                mostrarAviso("Solo se pueden eliminar pólizas EN_TRAMITE.\nLas pólizas activas se dan de baja registrando una cancelación, para guardar el historial.");
                return;
            }

            int respuesta = JOptionPane.showConfirmDialog(pv,
                "¿Eliminar la póliza #" + id + "? Esta acción no se puede deshacer.",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (respuesta != JOptionPane.YES_OPTION) {
                return;
            }

            PolizaEntidad p = new PolizaEntidad(id, "", 0, 0, 0, 0, 0, null, "", "", "", 0.0, "");
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
                    pv.seleccionar(p.getIdVehiculo(), p.getIdCompania(), p.getIdUsuario(), p.getIdTipo());
                    pv.txtIdCotizacion.setText(p.getIdCotizacion() > 0 ? String.valueOf(p.getIdCotizacion()) : "");
                    pv.txtIdPolizaAnterior.setText(p.getIdPolizaAnterior() != null ? String.valueOf(p.getIdPolizaAnterior()) : "");
                    pv.txtFechaEmision.setText(p.getFechaEmision() == null ? "" : p.getFechaEmision());
                    pv.txtFechaInicio.setText(p.getFechaInicio());
                    pv.txtFechaVencimiento.setText(p.getFechaVencimiento());
                    pv.txtPrimaTotal.setText(String.valueOf(p.getPrimaTotal()));
                    pv.cmbEstatus.setSelectedItem(EstatusPoliza.valueOf(p.getEstatus()));
                }
            }
        });
    }
}
