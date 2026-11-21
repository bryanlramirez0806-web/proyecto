package polizas.TiposPoliza;

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

public class TipoPolizaVista extends JPanel {
    protected JButton btnConsultar, btnAgregar, btnActualizar, btnEliminar;
    protected JTextField txtIdTipo, txtNombre, txtDescripcion;
    protected JLabel lblTitulo, lblIdTipo, lblNombre, lblDescripcion;
    protected JTable tabla;
    protected TipoPolizaTM tipoPolizaModelo;
    protected JComboBox<TipoPoliza> cmbTiposPoliza;

    public TipoPolizaVista() {
        setLayout(null);
        setBackground(new Color(40, 40, 40));

        lblTitulo = new JLabel("Administración de Tipos de Póliza");
        lblTitulo.setForeground(Color.white);
        lblTitulo.setBounds(380, 10, 250, 30);
        add(lblTitulo);

        // Etiquetas y Campos
        lblIdTipo = crearLabel("ID Tipo:", 30, 50);
        txtIdTipo = crearTextField("0", 120, 50);

        lblNombre = crearLabel("Nombre:", 30, 90);
        txtNombre = crearTextField("Nombre", 120, 90);

        lblDescripcion = crearLabel("Descripción:", 30, 130);
        txtDescripcion = crearTextField("Descripción", 120, 130);

        cmbTiposPoliza = new JComboBox<>();
        cmbTiposPoliza.setBounds(450, 50, 350, 30);
        add(cmbTiposPoliza);

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
        tipoPolizaModelo = new TipoPolizaTM();
        tabla = new JTable(tipoPolizaModelo);
        JScrollPane sc = new JScrollPane(tabla);
        sc.setBounds(30, 230, 950, 350);
        add(sc);
    }

    private JLabel crearLabel(String texto, int x, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(Color.white);
        lbl.setBounds(x, y, 90, 30);
        add(lbl);
        return lbl;
    }

    private JTextField crearTextField(String texto, int x, int y) {
        JTextField txt = new JTextField(texto);
        txt.setBounds(x, y, 280, 30);
        add(txt);
        return txt;
    }

    public void actualizarVista(List<TipoPoliza> lista) {
        tipoPolizaModelo.vaciarLista();
        tipoPolizaModelo.llenar(lista);
        tipoPolizaModelo.fireTableDataChanged();

        cmbTiposPoliza.removeAllItems();
        for (TipoPoliza tp : lista) {
            cmbTiposPoliza.addItem(tp);
        }
    }
}
