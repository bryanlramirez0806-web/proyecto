package polizas.Vehiculos;

import java.awt.Color;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import polizas.Clientes.Cliente;
import polizas.Submarcas.Submarca;

public class VehiculoVista extends JPanel {
    protected JButton btnConsultar, btnBuscar, btnAgregar, btnActualizar, btnEliminar, btnLimpiar;
    protected JTextField txtIdVehiculo, txtModelo, txtNumeroSerie, txtPlacas, txtBuscar;
    protected JComboBox<Cliente> cmbCliente;
    protected JComboBox<Submarca> cmbSubmarca;
    protected JComboBox<String> cmbTipoVehiculo;
    protected JCheckBox chkActivo;
    protected JLabel lblTitulo;
    protected JTable tabla;
    protected VehiculoTM vehiculoModelo;

    public VehiculoVista() {
        setLayout(null);
        setBackground(new Color(40, 40, 40));

        lblTitulo = new JLabel("Gestión de Vehículos");
        lblTitulo.setForeground(Color.white);
        lblTitulo.setBounds(400, 10, 250, 30);
        add(lblTitulo);

        // Columna izquierda
        crearLabel("ID Vehículo:", 30, 50);
        txtIdVehiculo = crearTextField(130, 50);
        txtIdVehiculo.setEditable(false); // El ID lo pone la base de datos

        // El cliente y la submarca se escogen de una lista, así no se escriben IDs a mano
        crearLabel("Cliente:", 30, 90);
        cmbCliente = new JComboBox<>();
        cmbCliente.setBounds(130, 90, 250, 30);
        add(cmbCliente);

        crearLabel("Submarca:", 30, 130);
        cmbSubmarca = new JComboBox<>();
        cmbSubmarca.setBounds(130, 130, 250, 30);
        add(cmbSubmarca);

        crearLabel("Modelo (año):", 30, 170);
        txtModelo = crearTextField(130, 170);

        // Columna derecha
        crearLabel("Tipo vehículo:", 450, 50);
        cmbTipoVehiculo = new JComboBox<>(new String[]{"Sedán", "Hatchback", "SUV", "Pick-up", "Camioneta", "Motocicleta"});
        cmbTipoVehiculo.setEditable(true); // Se puede escribir otro tipo si no está en la lista
        cmbTipoVehiculo.setBounds(560, 50, 250, 30);
        add(cmbTipoVehiculo);

        crearLabel("N° Serie (VIN):", 450, 90);
        txtNumeroSerie = crearTextField(560, 90);

        crearLabel("Placas:", 450, 130);
        txtPlacas = crearTextField(560, 130);

        chkActivo = new JCheckBox("Activo", true);
        chkActivo.setForeground(Color.white);
        chkActivo.setOpaque(false);
        chkActivo.setBounds(560, 170, 100, 30);
        add(chkActivo);

        // Botones CRUD
        btnConsultar = crearBoton("Consultar", 30, 220);
        btnAgregar = crearBoton("Agregar", 150, 220);
        btnActualizar = crearBoton("Actualizar", 270, 220);
        btnEliminar = crearBoton("Dar de baja", 390, 220);
        btnLimpiar = crearBoton("Limpiar", 510, 220);

        // Búsqueda (RF-07)
        crearLabel("Buscar:", 30, 265);
        txtBuscar = crearTextField(130, 265);
        txtBuscar.setToolTipText("Placas, número de serie o cliente");
        btnBuscar = crearBoton("Buscar", 390, 265);

        // Tabla
        vehiculoModelo = new VehiculoTM();
        tabla = new JTable(vehiculoModelo);
        JScrollPane sc = new JScrollPane(tabla);
        sc.setBounds(30, 310, 950, 320);
        add(sc);
    }

    private void crearLabel(String texto, int x, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(Color.white);
        lbl.setBounds(x, y, 110, 30);
        add(lbl);
    }

    private JTextField crearTextField(int x, int y) {
        JTextField txt = new JTextField();
        txt.setBounds(x, y, 250, 30);
        add(txt);
        return txt;
    }

    private JButton crearBoton(String texto, int x, int y) {
        JButton btn = new JButton(texto);
        btn.setBounds(x, y, 110, 30);
        add(btn);
        return btn;
    }

    // Llena las listas de clientes y submarcas
    public void llenarCombos(List<Cliente> clientes, List<Submarca> submarcas) {
        cmbCliente.removeAllItems();
        for (Cliente c : clientes) {
            cmbCliente.addItem(c);
        }
        cmbSubmarca.removeAllItems();
        for (Submarca s : submarcas) {
            cmbSubmarca.addItem(s);
        }
    }

    // Selecciona en el combo el cliente con ese ID
    public void seleccionarCliente(int idCliente) {
        for (int i = 0; i < cmbCliente.getItemCount(); i++) {
            if (cmbCliente.getItemAt(i).getIdCliente() == idCliente) {
                cmbCliente.setSelectedIndex(i);
                return;
            }
        }
        cmbCliente.setSelectedIndex(-1); // El cliente ya no está activo
    }

    // Selecciona en el combo la submarca con ese ID
    public void seleccionarSubmarca(int idSubmarca) {
        for (int i = 0; i < cmbSubmarca.getItemCount(); i++) {
            if (cmbSubmarca.getItemAt(i).getIdSubmarca() == idSubmarca) {
                cmbSubmarca.setSelectedIndex(i);
                return;
            }
        }
    }

    public void limpiarCampos() {
        txtIdVehiculo.setText("");
        txtModelo.setText("");
        txtNumeroSerie.setText("");
        txtPlacas.setText("");
        cmbTipoVehiculo.setSelectedIndex(0);
        if (cmbCliente.getItemCount() > 0) cmbCliente.setSelectedIndex(0);
        if (cmbSubmarca.getItemCount() > 0) cmbSubmarca.setSelectedIndex(0);
        chkActivo.setSelected(true);
        tabla.clearSelection();
    }

    public void actualizarVista(List<Vehiculo> lista) {
        vehiculoModelo.vaciarLista();
        vehiculoModelo.llenar(lista);
        vehiculoModelo.fireTableDataChanged();
    }
}
