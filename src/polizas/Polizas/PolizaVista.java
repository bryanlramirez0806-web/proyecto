package polizas.Polizas;

import java.awt.Color;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import polizas.Aseguradoras.Aseguradora;
import polizas.FormasPago.FormaPago;
import polizas.Vehiculos.Vehiculo;

public class PolizaVista extends JPanel {
    protected JButton btnConsultar, btnBuscar, btnAgregar, btnActualizar, btnEliminar, btnLimpiar;
    protected JTextField txtIdPoliza, txtNumeroPoliza, txtIdPolizaAnterior, txtFechaInicio, txtFechaFin, txtPrimaTotal, txtBuscar;
    protected JComboBox<Vehiculo> cmbVehiculo;
    protected JComboBox<Aseguradora> cmbAseguradora;
    protected JComboBox<FormaPago> cmbFormaPago;
    protected JComboBox<EstadoPoliza> cmbEstado;
    protected JTable tabla;
    protected PolizaTM polizaModelo;

    public PolizaVista() {
        setLayout(null);
        setBackground(new Color(40, 40, 40));

        JLabel lblTitulo = new JLabel("Gestión de Pólizas");
        lblTitulo.setForeground(Color.white);
        lblTitulo.setBounds(400, 10, 250, 30);
        add(lblTitulo);

        // Columna 1: datos principales
        crearLabel("ID Póliza:", 30, 50);
        txtIdPoliza = crearTextField(140, 50);
        txtIdPoliza.setEditable(false); // El ID lo pone la base de datos

        crearLabel("N° Póliza:", 30, 90);
        txtNumeroPoliza = crearTextField(140, 90);
        txtNumeroPoliza.setToolTipText("Lo da la aseguradora cuando emite la póliza");

        crearLabel("Vehículo:", 30, 130);
        cmbVehiculo = crearCombo(140, 130);

        crearLabel("Estado:", 30, 170);
        cmbEstado = new JComboBox<>(EstadoPoliza.values());
        cmbEstado.setBounds(140, 170, 180, 30);
        add(cmbEstado);

        // Columna 2: aseguradora y forma de pago
        crearLabel("Aseguradora:", 350, 50);
        cmbAseguradora = crearCombo(470, 50);

        crearLabel("Forma de pago:", 350, 90);
        cmbFormaPago = crearCombo(470, 90);

        crearLabel("ID Póliza ant.:", 350, 130);
        txtIdPolizaAnterior = crearTextField(470, 130);
        txtIdPolizaAnterior.setToolTipText("Opcional. Solo cuando es una renovación de otra póliza del mismo vehículo");

        // Columna 3: vigencia y prima
        crearLabel("Inicio:", 680, 50);
        txtFechaInicio = crearTextField(780, 50);
        txtFechaInicio.setToolTipText("AAAA-MM-DD");

        crearLabel("Fin:", 680, 90);
        txtFechaFin = crearTextField(780, 90);
        txtFechaFin.setToolTipText("AAAA-MM-DD");

        crearLabel("Prima total:", 680, 130);
        txtPrimaTotal = crearTextField(780, 130);

        // Botones CRUD
        btnConsultar = crearBoton("Consultar", 30, 220);
        btnAgregar = crearBoton("Agregar", 150, 220);
        btnActualizar = crearBoton("Actualizar", 270, 220);
        btnEliminar = crearBoton("Eliminar", 390, 220);
        btnLimpiar = crearBoton("Limpiar", 510, 220);

        // Búsqueda (RF-17)
        crearLabel("Buscar:", 30, 265);
        txtBuscar = crearTextField(140, 265);
        txtBuscar.setToolTipText("Número de póliza, cliente o placas");
        btnBuscar = crearBoton("Buscar", 330, 265);

        // Tabla
        polizaModelo = new PolizaTM();
        tabla = new JTable(polizaModelo);
        JScrollPane sc = new JScrollPane(tabla);
        sc.setBounds(30, 310, 950, 330);
        add(sc);

        limpiarCampos();
    }

    private void crearLabel(String texto, int x, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(Color.white);
        lbl.setBounds(x, y, 130, 30);
        add(lbl);
    }

    private JTextField crearTextField(int x, int y) {
        JTextField txt = new JTextField();
        txt.setBounds(x, y, 180, 30);
        add(txt);
        return txt;
    }

    private <T> JComboBox<T> crearCombo(int x, int y) {
        JComboBox<T> cmb = new JComboBox<>();
        cmb.setBounds(x, y, 180, 30);
        add(cmb);
        return cmb;
    }

    private JButton crearBoton(String texto, int x, int y) {
        JButton btn = new JButton(texto);
        btn.setBounds(x, y, 110, 30);
        add(btn);
        return btn;
    }

    // Llena las listas de vehículos, aseguradoras y formas de pago
    public void llenarCombos(List<Vehiculo> vehiculos, List<Aseguradora> aseguradoras, List<FormaPago> formas) {
        cmbVehiculo.removeAllItems();
        for (Vehiculo v : vehiculos) cmbVehiculo.addItem(v);
        cmbAseguradora.removeAllItems();
        for (Aseguradora a : aseguradoras) cmbAseguradora.addItem(a);
        cmbFormaPago.removeAllItems();
        for (FormaPago f : formas) cmbFormaPago.addItem(f);
    }

    // Selecciona en cada combo el registro con ese ID
    public void seleccionar(int idVehiculo, int idAseguradora, int idFormaPago) {
        cmbVehiculo.setSelectedIndex(-1); // Si el vehículo ya no está activo queda vacío
        for (int i = 0; i < cmbVehiculo.getItemCount(); i++) {
            if (cmbVehiculo.getItemAt(i).getIdVehiculo() == idVehiculo) cmbVehiculo.setSelectedIndex(i);
        }
        for (int i = 0; i < cmbAseguradora.getItemCount(); i++) {
            if (cmbAseguradora.getItemAt(i).getIdAseguradora() == idAseguradora) cmbAseguradora.setSelectedIndex(i);
        }
        for (int i = 0; i < cmbFormaPago.getItemCount(); i++) {
            if (cmbFormaPago.getItemAt(i).getIdFormaPago() == idFormaPago) cmbFormaPago.setSelectedIndex(i);
        }
    }

    public void limpiarCampos() {
        txtIdPoliza.setText("");
        txtNumeroPoliza.setText("");
        txtIdPolizaAnterior.setText("");
        txtFechaInicio.setText(java.time.LocalDate.now().toString());
        txtFechaFin.setText(java.time.LocalDate.now().plusYears(1).toString());
        txtPrimaTotal.setText("");
        cmbEstado.setSelectedItem(EstadoPoliza.EN_TRAMITE);
        if (cmbVehiculo.getItemCount() > 0) cmbVehiculo.setSelectedIndex(0);
        if (cmbAseguradora.getItemCount() > 0) cmbAseguradora.setSelectedIndex(0);
        if (cmbFormaPago.getItemCount() > 0) cmbFormaPago.setSelectedIndex(0);
        if (tabla != null) tabla.clearSelection();
    }

    public void actualizarVista(List<PolizaEntidad> lista) {
        polizaModelo.vaciarLista();
        polizaModelo.llenar(lista);
        polizaModelo.fireTableDataChanged();
    }
}
