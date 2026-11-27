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
import polizas.Modelos.Modelo;
import polizas.UsosVehiculo.UsoVehiculo;

public class VehiculoVista extends JPanel {
    protected JButton btnConsultar, btnBuscar, btnAgregar, btnActualizar, btnEliminar, btnLimpiar;
    protected JTextField txtIdVehiculo, txtAnio, txtNumSerie, txtPlacas, txtColor, txtBuscar;
    protected JComboBox<Cliente> cmbCliente;
    protected JComboBox<Modelo> cmbModelo;
    protected JComboBox<UsoVehiculo> cmbUso;
    protected JCheckBox chkActivo;
    protected JTable tabla;
    protected VehiculoTM vehiculoModelo;

    public VehiculoVista() {
        setLayout(null);
        setBackground(new Color(40, 40, 40));

        JLabel lblTitulo = new JLabel("Gestión de Vehículos");
        lblTitulo.setForeground(Color.white);
        lblTitulo.setBounds(400, 10, 250, 30);
        add(lblTitulo);

        // Columna izquierda: dueño, modelo y uso (se escogen de una lista)
        crearLabel("ID Vehículo:", 30, 50);
        txtIdVehiculo = crearTextField(130, 50);
        txtIdVehiculo.setEditable(false); // El ID lo pone la base de datos

        crearLabel("Cliente:", 30, 90);
        cmbCliente = new JComboBox<>();
        cmbCliente.setBounds(130, 90, 250, 30);
        add(cmbCliente);

        crearLabel("Modelo:", 30, 130);
        cmbModelo = new JComboBox<>();
        cmbModelo.setBounds(130, 130, 250, 30);
        add(cmbModelo);

        crearLabel("Uso:", 30, 170);
        cmbUso = new JComboBox<>();
        cmbUso.setBounds(130, 170, 250, 30);
        add(cmbUso);

        // Columna derecha: datos del vehículo
        crearLabel("Año:", 450, 50);
        txtAnio = crearTextField(560, 50);

        crearLabel("N° Serie (VIN):", 450, 90);
        txtNumSerie = crearTextField(560, 90);

        crearLabel("Placas:", 450, 130);
        txtPlacas = crearTextField(560, 130);

        crearLabel("Color:", 450, 170);
        txtColor = crearTextField(560, 170);

        chkActivo = new JCheckBox("Activo", true);
        chkActivo.setForeground(Color.white);
        chkActivo.setOpaque(false);
        chkActivo.setBounds(830, 50, 100, 30);
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
        txtBuscar.setToolTipText("Placas, número de serie, cliente o modelo");
        btnBuscar = crearBoton("Buscar", 390, 265);

        // Tabla
        vehiculoModelo = new VehiculoTM();
        tabla = new JTable(vehiculoModelo);
        JScrollPane sc = new JScrollPane(tabla);
        sc.setBounds(30, 310, 950, 330);
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

    // Llena las listas de clientes, modelos y usos
    public void llenarCombos(List<Cliente> clientes, List<Modelo> modelos, List<UsoVehiculo> usos) {
        cmbCliente.removeAllItems();
        for (Cliente c : clientes) cmbCliente.addItem(c);
        cmbModelo.removeAllItems();
        for (Modelo m : modelos) cmbModelo.addItem(m);
        cmbUso.removeAllItems();
        for (UsoVehiculo u : usos) cmbUso.addItem(u);
    }

    // Selecciona en cada combo el registro con ese ID
    public void seleccionar(int idCliente, int idModelo, int idUso) {
        cmbCliente.setSelectedIndex(-1); // Si el cliente ya no está activo queda vacío
        for (int i = 0; i < cmbCliente.getItemCount(); i++) {
            if (cmbCliente.getItemAt(i).getIdCliente() == idCliente) cmbCliente.setSelectedIndex(i);
        }
        for (int i = 0; i < cmbModelo.getItemCount(); i++) {
            if (cmbModelo.getItemAt(i).getIdModelo() == idModelo) cmbModelo.setSelectedIndex(i);
        }
        for (int i = 0; i < cmbUso.getItemCount(); i++) {
            if (cmbUso.getItemAt(i).getIdUso() == idUso) cmbUso.setSelectedIndex(i);
        }
    }

    public void limpiarCampos() {
        txtIdVehiculo.setText("");
        txtAnio.setText("");
        txtNumSerie.setText("");
        txtPlacas.setText("");
        txtColor.setText("");
        if (cmbCliente.getItemCount() > 0) cmbCliente.setSelectedIndex(0);
        if (cmbModelo.getItemCount() > 0) cmbModelo.setSelectedIndex(0);
        if (cmbUso.getItemCount() > 0) cmbUso.setSelectedIndex(0);
        chkActivo.setSelected(true);
        tabla.clearSelection();
    }

    public void actualizarVista(List<Vehiculo> lista) {
        vehiculoModelo.vaciarLista();
        vehiculoModelo.llenar(lista);
        vehiculoModelo.fireTableDataChanged();
    }
}
