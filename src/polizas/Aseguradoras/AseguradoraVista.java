package polizas.Aseguradoras;

import java.awt.Color;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class AseguradoraVista extends JPanel {
    protected JButton btnConsultar, btnBuscar, btnAgregar, btnActualizar, btnEliminar, btnLimpiar;
    protected JTextField txtId, txtNombre, txtRfc, txtTelefono, txtCorreo, txtNombreContacto, txtBuscar;
    protected JTable tabla;
    protected AseguradoraTM tablaModelo;

    public AseguradoraVista() {
        setLayout(null);
        setBackground(new Color(40, 40, 40));

        JLabel lblTitulo = new JLabel("Gestión de Aseguradoras");
        lblTitulo.setForeground(Color.white);
        lblTitulo.setBounds(400, 10, 300, 30);
        add(lblTitulo);

        crearLabel("ID:", 30, 50);
        txtId = crearTextField(130, 50);
        txtId.setEditable(false); // El ID lo pone la base de datos

        crearLabel("Nombre:", 30, 90);
        txtNombre = crearTextField(130, 90);

        crearLabel("RFC:", 30, 130);
        txtRfc = crearTextField(130, 130);

        crearLabel("Teléfono:", 450, 50);
        txtTelefono = crearTextField(560, 50);

        crearLabel("Correo:", 450, 90);
        txtCorreo = crearTextField(560, 90);

        crearLabel("Contacto:", 450, 130);
        txtNombreContacto = crearTextField(560, 130);

        // Botones CRUD
        btnConsultar = crearBoton("Consultar", 30, 180);
        btnAgregar = crearBoton("Agregar", 150, 180);
        btnActualizar = crearBoton("Actualizar", 270, 180);
        btnEliminar = crearBoton("Eliminar", 390, 180);
        btnLimpiar = crearBoton("Limpiar", 510, 180);

        // Búsqueda
        crearLabel("Buscar:", 30, 225);
        txtBuscar = crearTextField(130, 225);
        btnBuscar = crearBoton("Buscar", 390, 225);

        tablaModelo = new AseguradoraTM();
        tabla = new JTable(tablaModelo);
        JScrollPane sc = new JScrollPane(tabla);
        sc.setBounds(30, 270, 950, 360);
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

    public void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtRfc.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtNombreContacto.setText("");
        tabla.clearSelection();
    }

    public void actualizarVista(List<Aseguradora> lista) {
        tablaModelo.vaciarLista();
        tablaModelo.llenar(lista);
        tablaModelo.fireTableDataChanged();
    }
}
