package polizas.Pagos;

import java.awt.Color;
import java.util.List;
import javax.swing.*;

public class PagoVista extends JPanel {
    protected JButton btnConsultar, btnAgregar, btnActualizar, btnEliminar;
    protected JTextField txtIdPago, txtIdPoliza, txtParcialidad, txtMonto, txtFLimite, txtFPago, txtMetodo, txtComprobante, txtEstatus;
    protected JTable tabla;
    protected PagoTM modelo;

    public PagoVista() {
        setLayout(null); setBackground(new Color(40, 40, 40));
        JLabel lbl = new JLabel("Control de Pagos"); lbl.setForeground(Color.white); lbl.setBounds(420, 10, 200, 30); add(lbl);

        add(crearL("ID Pago:", 30, 50)); txtIdPago = crearT("0", 130, 50);
        add(crearL("ID Póliza:", 30, 90)); txtIdPoliza = crearT("0", 130, 90);
        add(crearL("Parcialidad:", 30, 130)); txtParcialidad = crearT("1", 130, 130);
        add(crearL("Monto:", 30, 170)); txtMonto = crearT("0.0", 130, 170);
        add(crearL("F. Límite:", 30, 210)); txtFLimite = crearT("2026-12-31", 130, 210);

        add(crearL("F. Pago:", 450, 50)); txtFPago = crearT("2026-11-17", 550, 50);
        add(crearL("Método:", 450, 90)); txtMetodo = crearT("EFECTIVO", 550, 90);
        add(crearL("Comprobante:", 450, 130)); txtComprobante = crearT("COMP-123", 550, 130);
        add(crearL("Estatus:", 450, 170)); txtEstatus = crearT("PAGADO", 550, 170);

        btnConsultar = new JButton("Consultar"); btnConsultar.setBounds(150, 260, 100, 30); add(btnConsultar);
        btnAgregar = new JButton("Agregar"); btnAgregar.setBounds(260, 260, 100, 30); add(btnAgregar);
        btnActualizar = new JButton("Actualizar"); btnActualizar.setBounds(370, 260, 100, 30); add(btnActualizar);
        btnEliminar = new JButton("Eliminar"); btnEliminar.setBounds(480, 260, 100, 30); add(btnEliminar);

        modelo = new PagoTM(); tabla = new JTable(modelo);
        JScrollPane sc = new JScrollPane(tabla); sc.setBounds(30, 310, 950, 280); add(sc);
    }
    private JLabel crearL(String t, int x, int y) { JLabel l = new JLabel(t); l.setForeground(Color.white); l.setBounds(x,y,100,30); return l; }
    private JTextField crearT(String t, int x, int y) { JTextField tf = new JTextField(t); tf.setBounds(x,y,200,30); add(tf); return tf; }
    public void actualizarVista(List<Pago> l) { modelo.vaciarLista(); modelo.llenar(l); modelo.fireTableDataChanged(); }
}