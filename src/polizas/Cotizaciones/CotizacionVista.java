package polizas.Cotizaciones;

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

public class CotizacionVista extends JPanel {
    protected JButton btnConsultar, btnAgregar, btnActualizar, btnEliminar;
    protected JTextField txtIdCotizacion, txtIdVehiculo, txtIdUsuario, txtIdCompania, txtIdTipo, txtFecha, txtCosto, txtOpcionesPago, txtEstatus;
    protected JLabel lblTitulo, lblIdCotizacion, lblIdVehiculo, lblIdUsuario, lblIdCompania, lblIdTipo, lblFecha, lblCosto, lblOpcionesPago, lblEstatus;
    protected JTable tabla;
    protected CotizacionTM cotizacionModelo;
    protected JComboBox<Cotizacion> cmbCotizaciones;

    public CotizacionVista() {
        setLayout(null);
        setBackground(new Color(40, 40, 40));

        lblTitulo = new JLabel("Administración de Cotizaciones");
        lblTitulo.setForeground(Color.white);
        lblTitulo.setBounds(400, 10, 220, 30);
        add(lblTitulo);

        // Columna Izquierda
        lblIdCotizacion = crearLabel("ID Cotización:", 30, 50);
        txtIdCotizacion = crearTextField("0", 140, 50);

        lblIdVehiculo = crearLabel("ID Vehículo:", 30, 90);
        txtIdVehiculo = crearTextField("0", 140, 90);

        lblIdUsuario = crearLabel("ID Usuario:", 30, 130);
        txtIdUsuario = crearTextField("0", 140, 130);

        lblIdCompania = crearLabel("ID Compañía:", 30, 170);
        txtIdCompania = crearTextField("0", 140, 170);

        lblIdTipo = crearLabel("ID Tipo Póliza:", 30, 210);
        txtIdTipo = crearTextField("0", 140, 210);

        // Columna Derecha
        lblFecha = crearLabel("Fecha (AAAA-MM-DD):", 450, 50);
        txtFecha = crearTextField("2026-11-17", 590, 50);

        lblCosto = crearLabel("Costo Total:", 450, 90);
        txtCosto = crearTextField("0.0", 590, 90);

        lblOpcionesPago = crearLabel("Opciones Pago:", 450, 130);
        txtOpcionesPago = crearTextField("COMPLETO", 590, 130);

        lblEstatus = crearLabel("Estatus:", 450, 170);
        txtEstatus = crearTextField("PENDIENTE", 590, 170);

        cmbCotizaciones = new JComboBox<>();
        cmbCotizaciones.setBounds(450, 210, 340, 30);
        add(cmbCotizaciones);

        // Botones CRUD
        btnConsultar = new JButton("Consultar");
        btnConsultar.setBounds(140, 260, 100, 30);
        add(btnConsultar);

        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(250, 260, 100, 30);
        add(btnAgregar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(360, 260, 100, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(470, 260, 100, 30);
        add(btnEliminar);

        // Tabla
        cotizacionModelo = new CotizacionTM();
        tabla = new JTable(cotizacionModelo);
        JScrollPane sc = new JScrollPane(tabla);
        sc.setBounds(30, 310, 950, 300);
        add(sc);
    }

    private JLabel crearLabel(String texto, int x, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(Color.white);
        lbl.setBounds(x, y, 130, 30);
        add(lbl);
        return lbl;
    }

    private JTextField crearTextField(String texto, int x, int y) {
        JTextField txt = new JTextField(texto);
        txt.setBounds(x, y, 200, 30);
        add(txt);
        return txt;
    }

    public void actualizarVista(List<Cotizacion> lista) {
        cotizacionModelo.vaciarLista();
        cotizacionModelo.llenar(lista);
        cotizacionModelo.fireTableDataChanged();

        cmbCotizaciones.removeAllItems();
        for (Cotizacion c : lista) {
            cmbCotizaciones.addItem(c);
        }
    }
}