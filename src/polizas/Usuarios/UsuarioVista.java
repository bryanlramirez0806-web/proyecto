package polizas.Usuarios;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class UsuarioVista extends JPanel {
    protected JButton btnConsultar, btnAgregar, btnActualizar, btnEliminar;
    protected JTextField txtIdUsuario, txtNombre, txtTelefono, txtCorreo, txtRol;
    protected JLabel lblTitulo, lblIdUsuario, lblNombre, lblTelefono, lblCorreo, lblRol;
    protected JTable tabla;
    protected UsuarioTM usuarioModelo;
    protected JComboBox<Usuario> cmbUsuarios;

    public UsuarioVista() {
        setLayout(null);
        setBackground(new Color(40, 40, 40));

        lblTitulo = new JLabel("Administración de Usuarios / Agentes");
        lblTitulo.setForeground(Color.white);
        lblTitulo.setBounds(370, 10, 260, 30);
        add(lblTitulo);

        // Etiquetas y Campos
        lblIdUsuario = crearLabel("ID Usuario:", 30, 50);
        txtIdUsuario = crearTextField("0", 120, 50);

        lblNombre = crearLabel("Nombre:", 30, 90);
        txtNombre = crearTextField("Nombre", 120, 90);

        lblTelefono = crearLabel("Teléfono:", 30, 130);
        txtTelefono = crearTextField("Teléfono", 120, 130);

        lblCorreo = crearLabel("Correo:", 450, 50);
        txtCorreo = crearTextField("Correo", 530, 50);

        lblRol = crearLabel("Rol:", 450, 90);
        txtRol = crearTextField("VENDEDOR", 530, 90);

        cmbUsuarios = new JComboBox<>();
        cmbUsuarios.setBounds(450, 130, 350, 30);
        add(cmbUsuarios);

        // Botones CRUD
        btnConsultar = new JButton("Consultar");
        btnConsultar.setBounds(120, 180, 100, 30);
        add(btnConsultar);

        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(230, 180, 100, 30);
        add(btnAgregar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(340, 180, 100, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(450, 180, 100, 30);
        add(btnEliminar);

        // Tabla
        usuarioModelo = new UsuarioTM();
        tabla = new JTable(usuarioModelo);
        JScrollPane sc = new JScrollPane(tabla);
        sc.setBounds(30, 230, 950, 350);
        add(sc);
    }

    private JLabel crearLabel(String texto, int x, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(Color.white);
        lbl.setBounds(x, y, 80, 30);
        add(lbl);
        return lbl;
    }

    private JTextField crearTextField(String texto, int x, int y) {
        JTextField txt = new JTextField(texto);
        txt.setBounds(x, y, 250, 30);
        add(txt);
        return txt;
    }

    public void actualizarVista(List<Usuario> lista) {
        usuarioModelo.vaciarLista();
        usuarioModelo.llenar(lista);
        usuarioModelo.fireTableDataChanged();

        cmbUsuarios.removeAllItems();
        for (Usuario u : lista) {
            cmbUsuarios.addItem(u);
        }
    }
}