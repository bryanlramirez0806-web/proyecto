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
import polizas.Companias.Compania;
import polizas.TiposPoliza.TipoPoliza;
import polizas.Usuarios.Usuario;
import polizas.Vehiculos.Vehiculo;

public class PolizaVista extends JPanel {
    protected JButton btnConsultar, btnBuscar, btnAgregar, btnActualizar, btnEliminar, btnLimpiar;
    protected JTextField txtIdPoliza, txtNumeroPoliza, txtIdCotizacion, txtIdPolizaAnterior, txtFechaEmision, txtFechaInicio, txtFechaVencimiento, txtPrimaTotal, txtBuscar;
    protected JComboBox<Vehiculo> cmbVehiculo;
    protected JComboBox<Compania> cmbCompania;
    protected JComboBox<Usuario> cmbUsuario;
    protected JComboBox<TipoPoliza> cmbTipo;
    protected JComboBox<EstatusPoliza> cmbEstatus;
    protected JLabel lblTitulo;
    protected JTable tabla;
    protected PolizaTM polizaModelo;

    public PolizaVista() {
        setLayout(null);
        setBackground(new Color(40, 40, 40));

        lblTitulo = new JLabel("Gestión de Pólizas");
        lblTitulo.setForeground(Color.white);
        lblTitulo.setBounds(400, 10, 250, 30);
        add(lblTitulo);

        // Columna 1 (datos principales)
        crearLabel("ID Póliza:", 30, 50);
        txtIdPoliza = crearTextField(140, 50);
        txtIdPoliza.setEditable(false); // El ID lo pone la base de datos

        crearLabel("N° Póliza:", 30, 90);
        txtNumeroPoliza = crearTextField(140, 90);
        txtNumeroPoliza.setToolTipText("Lo da la compañía cuando emite la póliza");

        crearLabel("Vehículo:", 30, 130);
        cmbVehiculo = crearCombo(140, 130);

        crearLabel("Compañía:", 30, 170);
        cmbCompania = crearCombo(140, 170);

        // Columna 2 (relaciones y fechas)
        crearLabel("Vendedor:", 350, 50);
        cmbUsuario = crearCombo(470, 50);

        crearLabel("Tipo póliza:", 350, 90);
        cmbTipo = crearCombo(470, 90);

        crearLabel("ID Cotización:", 350, 130);
        txtIdCotizacion = crearTextField(470, 130);
        txtIdCotizacion.setToolTipText("Opcional. Debe ser una cotización ACEPTADA");

        crearLabel("ID Póliza ant.:", 350, 170);
        txtIdPolizaAnterior = crearTextField(470, 170);
        txtIdPolizaAnterior.setToolTipText("Opcional. Solo cuando es una renovación");

        // Columna 3 (fechas, prima y estatus)
        crearLabel("Emisión:", 680, 50);
        txtFechaEmision = crearTextField(780, 50);
        txtFechaEmision.setToolTipText("AAAA-MM-DD. Se llena cuando la compañía emite la póliza");

        crearLabel("Inicio:", 680, 90);
        txtFechaInicio = crearTextField(780, 90);
        txtFechaInicio.setToolTipText("AAAA-MM-DD");

        crearLabel("Vencimiento:", 680, 130);
        txtFechaVencimiento = crearTextField(780, 130);
        txtFechaVencimiento.setToolTipText("AAAA-MM-DD");

        crearLabel("Prima total:", 680, 170);
        txtPrimaTotal = crearTextField(780, 170);

        crearLabel("Estatus:", 680, 210);
        cmbEstatus = new JComboBox<>(EstatusPoliza.values());
        cmbEstatus.setBounds(780, 210, 180, 30);
        add(cmbEstatus);

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
        sc.setBounds(30, 310, 950, 320);
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

    // Llena las listas de vehículos, compañías, vendedores y tipos de póliza
    public void llenarCombos(List<Vehiculo> vehiculos, List<Compania> companias, List<Usuario> usuarios, List<TipoPoliza> tipos) {
        cmbVehiculo.removeAllItems();
        for (Vehiculo v : vehiculos) cmbVehiculo.addItem(v);
        cmbCompania.removeAllItems();
        for (Compania c : companias) cmbCompania.addItem(c);
        cmbUsuario.removeAllItems();
        for (Usuario u : usuarios) cmbUsuario.addItem(u);
        cmbTipo.removeAllItems();
        for (TipoPoliza t : tipos) cmbTipo.addItem(t);
    }

    // Selecciona en cada combo el elemento con ese ID
    public void seleccionar(int idVehiculo, int idCompania, int idUsuario, int idTipo) {
        cmbVehiculo.setSelectedIndex(-1);
        for (int i = 0; i < cmbVehiculo.getItemCount(); i++) {
            if (cmbVehiculo.getItemAt(i).getIdVehiculo() == idVehiculo) cmbVehiculo.setSelectedIndex(i);
        }
        cmbCompania.setSelectedIndex(-1);
        for (int i = 0; i < cmbCompania.getItemCount(); i++) {
            if (cmbCompania.getItemAt(i).getIdCompania() == idCompania) cmbCompania.setSelectedIndex(i);
        }
        cmbUsuario.setSelectedIndex(-1);
        for (int i = 0; i < cmbUsuario.getItemCount(); i++) {
            if (cmbUsuario.getItemAt(i).getIdUsuario() == idUsuario) cmbUsuario.setSelectedIndex(i);
        }
        cmbTipo.setSelectedIndex(-1);
        for (int i = 0; i < cmbTipo.getItemCount(); i++) {
            if (cmbTipo.getItemAt(i).getIdTipo() == idTipo) cmbTipo.setSelectedIndex(i);
        }
    }

    public void limpiarCampos() {
        txtIdPoliza.setText("");
        txtNumeroPoliza.setText("");
        txtIdCotizacion.setText("");
        txtIdPolizaAnterior.setText("");
        txtFechaEmision.setText("");
        txtFechaInicio.setText(java.time.LocalDate.now().toString());
        txtFechaVencimiento.setText(java.time.LocalDate.now().plusYears(1).toString());
        txtPrimaTotal.setText("");
        cmbEstatus.setSelectedItem(EstatusPoliza.EN_TRAMITE);
        if (cmbVehiculo.getItemCount() > 0) cmbVehiculo.setSelectedIndex(0);
        if (cmbCompania.getItemCount() > 0) cmbCompania.setSelectedIndex(0);
        if (cmbUsuario.getItemCount() > 0) cmbUsuario.setSelectedIndex(0);
        if (cmbTipo.getItemCount() > 0) cmbTipo.setSelectedIndex(0);
        if (tabla != null) tabla.clearSelection();
    }

    public void actualizarVista(List<PolizaEntidad> lista) {
        polizaModelo.vaciarLista();
        polizaModelo.llenar(lista);
        polizaModelo.fireTableDataChanged();
    }
}
