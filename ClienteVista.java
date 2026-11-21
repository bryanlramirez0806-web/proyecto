package polizas.Clientes;

import java.awt.Color;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ClienteVista extends JPanel {
    protected JButton btnConsultar, btnAgregar, btnEliminar, btnActualizar;
    protected JTextField txtId, txtNombre, txtTelefono, txtCorreo, txtRfc, txtDireccion, txtMedioContacto;
    protected JLabel lblTitulo, lblId, lblNombre, lblTelefono, lblCorreo, lblRfc, lblDireccion, lblMedioContacto;
    protected JTable tabla;
    protected ClienteTM clienteModelo;
    protected JComboBox<Cliente> cmbClientes;

    public ClienteVista() {
        setLayout(null);
        setBackground(new Color(40, 40, 40));

        lblTitulo = new JLabel("Administración de Clientes");
        lblTitulo.setForeground(Color.white);
        lblTitulo.setBounds(400, 10, 200, 30);
        add(lblTitulo);

        // Etiquetas y Campos de Texto
        lblId = crearLabel("ID:", 30, 50);
        txtId = crearTextField("0", 130, 50);

        lblNombre = crearLabel("Nombre:", 30, 90);
        txtNombre = crearTextField("Nombre completo", 130, 90);

        lblTelefono = crearLabel("Teléfono:", 30, 130);
        txtTelefono = crearTextField("Teléfono", 130, 130);

        lblCorreo = crearLabel("Correo:", 30, 170);
        txtCorreo = crearTextField("Correo", 130, 170);

        lblRfc = crearLabel("RFC:", 450, 50);
        txtRfc = crearTextField("RFC", 550, 50);

        lblDireccion = crearLabel("Dirección:", 450, 90);
        txtDireccion = crearTextField("Dirección", 550, 90);

        lblMedioContacto = crearLabel("Medio Contacto:", 450, 130);
        txtMedioContacto = crearTextField("WHATSAPP", 550, 130);

        cmbClientes = new JComboBox<>();
        cmbClientes.setBounds(450, 170, 350, 30);
        add(cmbClientes);

        // Botones CRUD
        btnConsultar = new JButton("Consultar");
        btnConsultar.setBounds(130, 220, 100, 30);
        add(btnConsultar);

        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(240, 220, 100, 30);
        add(btnAgregar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(350, 220, 100, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(460, 220, 100, 30);
        add(btnEliminar);

        // Tabla
        clienteModelo = new ClienteTM();
        tabla = new JTable(clienteModelo);
        JScrollPane sc = new JScrollPane(tabla);
        sc.setBounds(30, 275, 950, 350);
        add(sc);
    }

    private JLabel crearLabel(String texto, int x, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(Color.white);
        lbl.setBounds(x, y, 100, 30);
        add(lbl);
        return lbl;
    }

    private JTextField crearTextField(String texto, int x, int y) {
        JTextField txt = new JTextField(texto);
        txt.setBounds(x, y, 250, 30);
        add(txt);
        return txt;
    }

    public void actualizarVista(List<Cliente> lista) {
        clienteModelo.vaciarLista();
        clienteModelo.llenar(lista);
        clienteModelo.fireTableDataChanged();

        cmbClientes.removeAllItems();
        if (lista != null) {
            for (Cliente c : lista) {
                cmbClientes.addItem(c);
            }
        }
    }
}