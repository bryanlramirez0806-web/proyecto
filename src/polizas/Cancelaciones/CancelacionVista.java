package polizas.Cancelaciones;

import java.awt.Color;
import java.util.List;
import javax.swing.*;

public class CancelacionVista extends JPanel {
    protected JButton btnConsultar, btnAgregar, btnActualizar, btnEliminar;
    protected JTextField txtId, txtIdPoliza, txtIdUsuario, txtFecha, txtMotivo, txtMonto, txtEstatus;
    protected JTable tabla;
    protected CancelacionTM modelo;

    public CancelacionVista() {
        setLayout(null); setBackground(new Color(40, 40, 40));
        JLabel lbl = new JLabel("Gestión de Cancelaciones"); lbl.setForeground(Color.white); lbl.setBounds(400, 10, 200, 30); add(lbl);

        add(crearL("ID Cancelación:", 30, 50)); txtId = crearT("0", 140, 50);
        add(crearL("ID Póliza:", 30, 90)); txtIdPoliza = crearT("0", 140, 90);
        add(crearL("ID Usuario:", 30, 130)); txtIdUsuario = crearT("0", 140, 130);
        add(crearL("F. Solicitud:", 30, 170)); txtFecha = crearT("2026-11-17", 140, 170);

        add(crearL("Motivo:", 450, 50)); txtMotivo = crearT("Motivo", 560, 50);
        add(crearL("Monto Dev:", 450, 90)); txtMonto = crearT("0.0", 560, 90);
        add(crearL("Estatus Dev:", 450, 130)); txtEstatus = crearT("PENDIENTE", 560, 130);

        btnConsultar = new JButton("Consultar"); btnConsultar.setBounds(140, 220, 100, 30); add(btnConsultar);
        btnAgregar = new JButton("Agregar"); btnAgregar.setBounds(250, 220, 100, 30); add(btnAgregar);
        btnActualizar = new JButton("Actualizar"); btnActualizar.setBounds(360, 220, 100, 30); add(btnActualizar);
        btnEliminar = new JButton("Eliminar"); btnEliminar.setBounds(470, 220, 100, 30); add(btnEliminar);

        modelo = new CancelacionTM(); tabla = new JTable(modelo);
        JScrollPane sc = new JScrollPane(tabla); sc.setBounds(30, 275, 950, 310); add(sc);
    }
    private JLabel crearL(String t, int x, int y) { JLabel l = new JLabel(t); l.setForeground(Color.white); l.setBounds(x,y,110,30); return l; }
    private JTextField crearT(String t, int x, int y) { JTextField tf = new JTextField(t); tf.setBounds(x,y,200,30); add(tf); return tf; }
    public void actualizarVista(List<Cancelacion> l) { modelo.vaciarLista(); modelo.llenar(l); modelo.fireTableDataChanged(); }
}