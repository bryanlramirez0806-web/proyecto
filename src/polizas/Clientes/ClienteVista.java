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

public class ClienteVista extends JPanel {
    protected JButton btnConsultar, btnBuscar, btnAgregar, btnEliminar, btnActualizar, btnLimpiar;
    protected JTextField txtId, txtNombre, txtApellidoPaterno, txtApellidoMaterno, txtRfc, txtCurp,
            txtTelefono, txtCorreo, txtFechaRegistro, txtBuscar;
    protected JComboBox<TipoPersona> cmbTipoPersona;
    protected JCheckBox chkActivo;
    protected JTable tabla;
    protected ClienteTM clienteModelo;

    public ClienteVista() {
        setLayout(null);
        setBackground(new Color(40, 40, 40));

        JLabel lblTitulo = new JLabel("Gestión de Clientes");
        lblTitulo.setForeground(Color.white);
        lblTitulo.setBounds(400, 10, 250, 30);
        add(lblTitulo);

        // Columna izquierda: datos de la persona
        crearLabel("ID:", 30, 50);
        txtId = crearTextField(150, 50);
        txtId.setEditable(false); // El ID lo pone la base de datos

        crearLabel("Tipo persona:", 30, 90);
        cmbTipoPersona = new JComboBox<>(TipoPersona.values());
        cmbTipoPersona.setBounds(150, 90, 250, 30);
        add(cmbTipoPersona);

        crearLabel("Nombre / razón:", 30, 130);
        txtNombre = crearTextField(150, 130);

        crearLabel("Ap. paterno:", 30, 170);
        txtApellidoPaterno = crearTextField(150, 170);

        crearLabel("Ap. materno:", 30, 210);
        txtApellidoMaterno = crearTextField(150, 210);

        // Columna derecha: datos fiscales y de contacto
        crearLabel("RFC:", 450, 50);
        txtRfc = crearTextField(560, 50);

        crearLabel("CURP:", 450, 90);
        txtCurp = crearTextField(560, 90);

        crearLabel("Teléfono:", 450, 130);
        txtTelefono = crearTextField(560, 130);

        crearLabel("Correo:", 450, 170);
        txtCorreo = crearTextField(560, 170);

        crearLabel("Registro:", 450, 210);
        txtFechaRegistro = crearTextField(560, 210);
        txtFechaRegistro.setEditable(false); // La pone la base de datos el día del alta

        chkActivo = new JCheckBox("Activo", true);
        chkActivo.setForeground(Color.white);
        chkActivo.setOpaque(false);
        chkActivo.setBounds(830, 50, 100, 30);
        add(chkActivo);

        // Botones CRUD
        btnConsultar = crearBoton("Consultar", 30, 260);
        btnAgregar = crearBoton("Agregar", 150, 260);
        btnActualizar = crearBoton("Actualizar", 270, 260);
        btnEliminar = crearBoton("Dar de baja", 390, 260);
        btnLimpiar = crearBoton("Limpiar", 510, 260);

        // Búsqueda (RF-02)
        crearLabel("Buscar:", 30, 305);
        txtBuscar = crearTextField(150, 305);
        txtBuscar.setToolTipText("Nombre, RFC, CURP o teléfono");
        btnBuscar = crearBoton("Buscar", 410, 305);

        // Tabla
        clienteModelo = new ClienteTM();
        tabla = new JTable(clienteModelo);
        JScrollPane sc = new JScrollPane(tabla);
        sc.setBounds(30, 350, 950, 290);
        add(sc);
    }

    private void crearLabel(String texto, int x, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(Color.white);
        lbl.setBounds(x, y, 120, 30);
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

    // Una persona moral (empresa) no tiene apellidos ni CURP
    public void ajustarPorTipo() {
        boolean fisica = cmbTipoPersona.getSelectedItem() == TipoPersona.FISICA;
        txtApellidoPaterno.setEnabled(fisica);
        txtApellidoMaterno.setEnabled(fisica);
        txtCurp.setEnabled(fisica);
        if (!fisica) {
            txtApellidoPaterno.setText("");
            txtApellidoMaterno.setText("");
            txtCurp.setText("");
        }
    }

    public void limpiarCampos() {
        txtId.setText("");
        cmbTipoPersona.setSelectedItem(TipoPersona.FISICA);
        txtNombre.setText("");
        txtApellidoPaterno.setText("");
        txtApellidoMaterno.setText("");
        txtRfc.setText("");
        txtCurp.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtFechaRegistro.setText("");
        chkActivo.setSelected(true);
        tabla.clearSelection();
        ajustarPorTipo();
    }

    public void actualizarVista(List<Cliente> lista) {
        clienteModelo.vaciarLista();
        clienteModelo.llenar(lista);
        clienteModelo.fireTableDataChanged();
    }
}
