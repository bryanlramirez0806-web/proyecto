package polizas.Submarcas;

import java.awt.Color;
import java.util.List;
import javax.swing.*;

public class SubmarcaVista extends JPanel {
    protected JButton btnConsultar, btnAgregar, btnActualizar, btnEliminar;
    protected JTextField txtIdSubmarca, txtIdMarca, txtNombreSubmarca;
    protected JTable tabla;
    protected SubmarcaTM modelo;
    protected JComboBox<Submarca> cmb;

    public SubmarcaVista() {
        setLayout(null);
        setBackground(new Color(40, 40, 40));

        JLabel lbl = new JLabel("Administración de Submarcas");
        lbl.setForeground(Color.white);
        lbl.setBounds(380, 10, 250, 30);
        add(lbl);

        add(crearL("ID Submarca:", 30, 50));
        txtIdSubmarca = crearT("0", 140, 50);

        add(crearL("ID Marca:", 30, 90));
        txtIdMarca = crearT("0", 140, 90);

        add(crearL("Submarca:", 30, 130));
        txtNombreSubmarca = crearT("Nombre", 140, 130);

        cmb = new JComboBox<>();
        cmb.setBounds(450, 50, 300, 30);
        add(cmb);

        btnConsultar = new JButton("Consultar"); btnConsultar.setBounds(140, 180, 100, 30); add(btnConsultar);
        btnAgregar = new JButton("Agregar"); btnAgregar.setBounds(250, 180, 100, 30); add(btnAgregar);
        btnActualizar = new JButton("Actualizar"); btnActualizar.setBounds(360, 180, 100, 30); add(btnActualizar);
        btnEliminar = new JButton("Eliminar"); btnEliminar.setBounds(470, 180, 100, 30); add(btnEliminar);

        modelo = new SubmarcaTM();
        tabla = new JTable(modelo);
        JScrollPane sc = new JScrollPane(tabla);
        sc.setBounds(30, 230, 950, 350);
        add(sc);
    }

    private JLabel crearL(String t, int x, int y) {
        JLabel l = new JLabel(t); l.setForeground(Color.white); l.setBounds(x, y, 110, 30); return l;
    }
    private JTextField crearT(String t, int x, int y) {
        JTextField tf = new JTextField(t); tf.setBounds(x, y, 200, 30); add(tf); return tf;
    }
    public void actualizarVista(List<Submarca> l) {
        modelo.vaciarLista(); modelo.llenar(l); modelo.fireTableDataChanged();
        cmb.removeAllItems();
        for(Submarca s : l) cmb.addItem(s);
    }
}