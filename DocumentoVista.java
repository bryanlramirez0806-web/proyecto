package polizas.Documentos;

import java.awt.Color;
import java.util.List;
import javax.swing.*;

public class DocumentoVista extends JPanel {
    protected JButton btnConsultar, btnAgregar, btnActualizar, btnEliminar;
    protected JTextField txtId, txtIdCliente, txtTipo, txtFecha, txtRuta;
    protected JTable tabla;
    protected DocumentoTM modelo;

    public DocumentoVista() {
        setLayout(null); setBackground(new Color(40, 40, 40));
        JLabel lbl = new JLabel("Gestión de Documentos"); lbl.setForeground(Color.white); lbl.setBounds(400, 10, 200, 30); add(lbl);

        add(crearL("ID Doc:", 30, 50)); txtId = crearT("0", 130, 50);
        add(crearL("ID Cliente:", 30, 90)); txtIdCliente = crearT("0", 130, 90);
        add(crearL("Tipo:", 30, 130)); txtTipo = crearT("INE", 130, 130);
        add(crearL("F. Recepción:", 30, 170)); txtFecha = crearT("2026-11-17", 130, 170);
        add(crearL("Ruta Archivo:", 30, 210)); txtRuta = crearT("C:/docs/file.pdf", 130, 210);

        btnConsultar = new JButton("Consultar"); btnConsultar.setBounds(130, 260, 100, 30); add(btnConsultar);
        btnAgregar = new JButton("Agregar"); btnAgregar.setBounds(240, 260, 100, 30); add(btnAgregar);
        btnActualizar = new JButton("Actualizar"); btnActualizar.setBounds(350, 260, 100, 30); add(btnActualizar);
        btnEliminar = new JButton("Eliminar"); btnEliminar.setBounds(460, 260, 100, 30); add(btnEliminar);

        modelo = new DocumentoTM(); tabla = new JTable(modelo);
        JScrollPane sc = new JScrollPane(tabla); sc.setBounds(30, 310, 950, 280); add(sc);
    }
    private JLabel crearL(String t, int x, int y) { JLabel l = new JLabel(t); l.setForeground(Color.white); l.setBounds(x,y,100,30); return l; }
    private JTextField crearT(String t, int x, int y) { JTextField tf = new JTextField(t); tf.setBounds(x,y,250,30); add(tf); return tf; }
    public void actualizarVista(List<Documento> l) { modelo.vaciarLista(); modelo.llenar(l); modelo.fireTableDataChanged(); }
}