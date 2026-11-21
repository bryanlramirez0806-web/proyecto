package polizas.DetalleCobertura;

import java.awt.Color;
import java.util.List;
import javax.swing.*;

public class DetalleCoberturaVista extends JPanel {
    protected JButton btnConsultar, btnAgregar, btnActualizar, btnEliminar;
    protected JTextField txtIdPoliza, txtIdCobertura, txtSuma, txtDeducible;
    protected JTable tabla;
    protected DetalleCoberturaTM modelo;

    public DetalleCoberturaVista() {
        setLayout(null); setBackground(new Color(40, 40, 40));
        JLabel lbl = new JLabel("Detalle de Coberturas por Póliza"); lbl.setForeground(Color.white); lbl.setBounds(380, 10, 250, 30); add(lbl);

        add(crearL("ID Póliza:", 30, 50)); txtIdPoliza = crearT("0", 130, 50);
        add(crearL("ID Cobertura:", 30, 90)); txtIdCobertura = crearT("0", 130, 90);
        add(crearL("Suma Aseg:", 30, 130)); txtSuma = crearT("0.0", 130, 130);
        add(crearL("Deducible:", 30, 170)); txtDeducible = crearT("0.0", 130, 170);

        btnConsultar = new JButton("Consultar"); btnConsultar.setBounds(130, 220, 100, 30); add(btnConsultar);
        btnAgregar = new JButton("Agregar"); btnAgregar.setBounds(240, 220, 100, 30); add(btnAgregar);
        btnActualizar = new JButton("Actualizar"); btnActualizar.setBounds(350, 220, 100, 30); add(btnActualizar);
        btnEliminar = new JButton("Eliminar"); btnEliminar.setBounds(460, 220, 100, 30); add(btnEliminar);

        modelo = new DetalleCoberturaTM(); tabla = new JTable(modelo);
        JScrollPane sc = new JScrollPane(tabla); sc.setBounds(30, 270, 950, 320); add(sc);
    }
    private JLabel crearL(String t, int x, int y) { JLabel l = new JLabel(t); l.setForeground(Color.white); l.setBounds(x,y,100,30); return l; }
    private JTextField crearT(String t, int x, int y) { JTextField tf = new JTextField(t); tf.setBounds(x,y,200,30); add(tf); return tf; }
    public void actualizarVista(List<DetalleCobertura> l) { modelo.vaciarLista(); modelo.llenar(l); modelo.fireTableDataChanged(); }
}