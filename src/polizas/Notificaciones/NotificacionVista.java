package polizas.Notificaciones;

import java.awt.Color;
import java.util.List;
import javax.swing.*;

public class NotificacionVista extends JPanel {
    protected JButton btnConsultar, btnAgregar, btnActualizar, btnEliminar;
    protected JTextField txtId, txtIdPoliza, txtIdUsuario, txtTipo, txtFecha, txtMedio, txtCosto;
    protected JTable tabla;
    protected NotificacionTM modelo;

    public NotificacionVista() {
        setLayout(null); setBackground(new Color(40, 40, 40));
        JLabel lbl = new JLabel("Gestión de Notificaciones"); lbl.setForeground(Color.white); lbl.setBounds(400, 10, 200, 30); add(lbl);

        add(crearL("ID Notif:", 30, 50)); txtId = crearT("0", 130, 50);
        add(crearL("ID Póliza:", 30, 90)); txtIdPoliza = crearT("0", 130, 90);
        add(crearL("ID Usuario:", 30, 130)); txtIdUsuario = crearT("0", 130, 130);
        add(crearL("Tipo:", 30, 170)); txtTipo = crearT("VENCIMIENTO", 130, 170);

        add(crearL("Fecha:", 450, 50)); txtFecha = crearT("2026-11-17", 550, 50);
        add(crearL("Medio:", 450, 90)); txtMedio = crearT("WHATSAPP", 550, 90);
        add(crearL("Costo Ren:", 450, 130)); txtCosto = crearT("0.0", 550, 130);

        btnConsultar = new JButton("Consultar"); btnConsultar.setBounds(130, 220, 100, 30); add(btnConsultar);
        btnAgregar = new JButton("Agregar"); btnAgregar.setBounds(240, 220, 100, 30); add(btnAgregar);
        btnActualizar = new JButton("Actualizar"); btnActualizar.setBounds(350, 220, 100, 30); add(btnActualizar);
        btnEliminar = new JButton("Eliminar"); btnEliminar.setBounds(460, 220, 100, 30); add(btnEliminar);

        modelo = new NotificacionTM(); tabla = new JTable(modelo);
        JScrollPane sc = new JScrollPane(tabla); sc.setBounds(30, 275, 950, 310); add(sc);
    }
    private JLabel crearL(String t, int x, int y) { JLabel l = new JLabel(t); l.setForeground(Color.white); l.setBounds(x,y,100,30); return l; }
    private JTextField crearT(String t, int x, int y) { JTextField tf = new JTextField(t); tf.setBounds(x,y,200,30); add(tf); return tf; }
    public void actualizarVista(List<Notificacion> l) { modelo.vaciarLista(); modelo.llenar(l); modelo.fireTableDataChanged(); }
}