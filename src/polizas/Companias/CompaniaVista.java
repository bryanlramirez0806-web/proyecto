package polizas.Companias;

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

public class CompaniaVista extends JPanel {
    protected JButton btnConsultar, btnAgregar, btnActualizar, btnEliminar;
    protected JTextField txtIdCompania, txtNombre, txtTelefono, txtCorreo, txtSitioValidacion;
    protected JLabel lblTitulo, lblIdCompania, lblNombre, lblTelefono, lblCorreo, lblSitioValidacion;
    protected JTable tabla;
    protected CompaniaTM companiaModelo;
    protected JComboBox<Compania> cmbCompanias;

    public CompaniaVista() {
        setLayout(null);
        setBackground(new Color(40, 40, 40));

        lblTitulo = new JLabel("Administración de Compañías de Seguros");
        lblTitulo.setForeground(Color.white);
        lblTitulo.setBounds(350, 10, 300, 30);
        add(lblTitulo);

        // Etiquetas y Campos de Texto
        lblIdCompania = crearLabel("ID Compañía:", 30, 50);
        txtIdCompania = crearTextField("0", 140, 50);

        lblNombre = crearLabel("Nombre:", 30, 90);
        txtNombre = crearTextField("Nombre", 140, 90);

        lblTelefono = crearLabel("Teléfono:", 30, 130);
        txtTelefono = crearTextField("Teléfono", 140, 130);

        lblCorreo = crearLabel("Correo:", 450, 50);
        txtCorreo = crearTextField("Correo", 570, 50);

        lblSitioValidacion = crearLabel("Sitio Validación:", 450, 90);
        txtSitioValidacion = crearTextField("URL / Sitio", 570, 90);

        cmbCompanias = new JComboBox<>();
        cmbCompanias.setBounds(450, 130, 370, 30);
        add(cmbCompanias);

        // Botones CRUD
        btnConsultar = new JButton("Consultar");
        btnConsultar.setBounds(140, 180, 100, 30);
        add(btnConsultar);

        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(250, 180, 100, 30);
        add(btnAgregar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(360, 180, 100, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(470, 180, 100, 30);
        add(btnEliminar);

        // Tabla
        companiaModelo = new CompaniaTM();
        tabla = new JTable(companiaModelo);
        JScrollPane sc = new JScrollPane(tabla);
        sc.setBounds(30, 230, 950, 350);
        add(sc);
    }

    private JLabel crearLabel(String texto, int x, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(Color.white);
        lbl.setBounds(x, y, 110, 30);
        add(lbl);
        return lbl;
    }

    private JTextField crearTextField(String texto, int x, int y) {
        JTextField txt = new JTextField(texto);
        txt.setBounds(x, y, 250, 30);
        add(txt);
        return txt;
    }

    public void actualizarVista(List<Compania> lista) {
        companiaModelo.vaciarLista();
        companiaModelo.llenar(lista);
        companiaModelo.fireTableDataChanged();

        cmbCompanias.removeAllItems();
        for (Compania c : lista) {
            cmbCompanias.addItem(c);
        }
    }
}