package polizas.Clientes;

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
import polizas.General.MedioContacto;

public class ClienteVista extends JPanel {
    protected JButton btnConsultar, btnBuscar, btnAgregar, btnEliminar, btnActualizar, btnLimpiar;
    protected JTextField txtId, txtNombre, txtTelefono, txtCorreo, txtRfc, txtDireccion, txtBuscar;
    protected JLabel lblTitulo, lblId, lblNombre, lblTelefono, lblCorreo, lblRfc, lblDireccion, lblMedioContacto, lblBuscar;
    protected JComboBox<MedioContacto> cmbMedioContacto;
    protected JCheckBox chkActivo;
    protected JTable tabla;
    protected ClienteTM clienteModelo;

    public ClienteVista() {
        setLayout(null);
        setBackground(new Color(40, 40, 40));

        lblTitulo = new JLabel("Gestión de Clientes");
        lblTitulo.setForeground(Color.white);
        lblTitulo.setBounds(400, 10, 250, 30);
        add(lblTitulo);

        // Etiquetas y campos de texto
        lblId = crearLabel("ID:", 30, 50);
        txtId = crearTextField(130, 50);
        txtId.setEditable(false); // El ID lo pone la base de datos

        lblNombre = crearLabel("Nombre:", 30, 90);
        txtNombre = crearTextField(130, 90);

        lblTelefono = crearLabel("Teléfono:", 30, 130);
        txtTelefono = crearTextField(130, 130);

        lblCorreo = crearLabel("Correo:", 30, 170);
        txtCorreo = crearTextField(130, 170);

        lblRfc = crearLabel("RFC:", 450, 50);
        txtRfc = crearTextField(560, 50);

        lblDireccion = crearLabel("Dirección:", 450, 90);
        txtDireccion = crearTextField(560, 90);

        lblMedioContacto = crearLabel("Medio contacto:", 450, 130);
        cmbMedioContacto = new JComboBox<>(MedioContacto.values());
        cmbMedioContacto.setBounds(560, 130, 250, 30);
        add(cmbMedioContacto);

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

        // Búsqueda (RF-02)
        lblBuscar = crearLabel("Buscar:", 30, 265);
        txtBuscar = crearTextField(130, 265);
        txtBuscar.setToolTipText("Nombre, RFC o teléfono");
        btnBuscar = crearBoton("Buscar", 390, 265);

        // Tabla
        clienteModelo = new ClienteTM();
        tabla = new JTable(clienteModelo);
        JScrollPane sc = new JScrollPane(tabla);
        sc.setBounds(30, 310, 950, 320);
        add(sc);
    }

    private JLabel crearLabel(String texto, int x, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(Color.white);
        lbl.setBounds(x, y, 110, 30);
        add(lbl);
        return lbl;
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

    public void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtRfc.setText("");
        txtDireccion.setText("");
        cmbMedioContacto.setSelectedIndex(0);
        chkActivo.setSelected(true);
        tabla.clearSelection();
    }

    public void actualizarVista(List<Cliente> lista) {
        clienteModelo.vaciarLista();
        clienteModelo.llenar(lista);
        clienteModelo.fireTableDataChanged();
    }
}
