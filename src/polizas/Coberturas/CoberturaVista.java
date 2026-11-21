package polizas.Coberturas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;
import javax.swing.*;

public class CoberturaVista extends JPanel {
    protected JButton btnConsultar, btnAgregar, btnActualizar, btnEliminar;
    protected JTextField txtId, txtNombre, txtDescripcion;
    protected JTable tabla;
    protected CoberturaTM modelo;
    protected JComboBox<Cobertura> cmb;

    public CoberturaVista() {
        setLayout(null);
        setBackground(new Color(40, 40, 40));

        JLabel lbl = new JLabel("Administración de Coberturas");
        lbl.setForeground(Color.white);
        lbl.setBounds(380, 10, 250, 30);
        add(lbl);

        add(crearLabel("ID Cobertura:", 30, 50));
        txtId = crearTxt("0", 130, 50);
        add(crearLabel("Nombre:", 30, 90));
        txtNombre = crearTxt("Nombre", 130, 90);
        add(crearLabel("Descripción:", 30, 130));
        txtDescripcion = crearTxt("Descripción", 130, 130);

        cmb = new JComboBox<>();
        cmb.setBounds(450, 50, 300, 30);
        add(cmb);

        btnConsultar = new JButton("Consultar"); btnConsultar.setBounds(130, 180, 100, 30); add(btnConsultar);
        btnAgregar = new JButton("Agregar"); btnAgregar.setBounds(240, 180, 100, 30); add(btnAgregar);
        btnActualizar = new JButton("Actualizar"); btnActualizar.setBounds(350, 180, 100, 30); add(btnActualizar);
        btnEliminar = new JButton("Eliminar"); btnEliminar.setBounds(460, 180, 100, 30); add(btnEliminar);

        modelo = new CoberturaTM();
        tabla = new JTable(modelo);
        JScrollPane sc = new JScrollPane(tabla);
        sc.setBounds(30, 230, 950, 350);
        add(sc);
    }

    private JLabel crearLabel(String t, int x, int y) {
        JLabel l = new JLabel(t); l.setForeground(Color.white); l.setBounds(x, y, 90, 30); return l;
    }
    private JTextField crearTxt(String t, int x, int y) {
        JTextField tf = new JTextField(t); tf.setBounds(x, y, 250, 30); add(tf); return tf;
    }
    public void actualizarVista(List<Cobertura> l) {
        modelo.vaciarLista(); modelo.llenar(l); modelo.fireTableDataChanged();
        cmb.removeAllItems(); for(Cobertura c : l) cmb.addItem(c);
    }
}
