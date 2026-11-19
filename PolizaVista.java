package polizas.Polizas;

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

public class PolizaVista extends JPanel {
    protected JButton btnConsultar, btnAgregar, btnActualizar, btnEliminar;
    protected JTextField txtIdPoliza, txtNumeroPoliza, txtIdCotizacion, txtIdVehiculo, txtIdCompania, txtIdUsuario, txtIdTipo, txtIdPolizaAnterior, txtFechaEmision, txtFechaInicio, txtFechaVencimiento, txtPrimaTotal, txtEstatus;
    protected JLabel lblTitulo;
    protected JTable tabla;
    protected PolizaTM polizaModelo;
    protected JComboBox<PolizaEntidad> cmbPolizas;

    public PolizaVista() {
        setLayout(null);
        setBackground(new Color(40, 40, 40));

        lblTitulo = new JLabel("Administración General de Pólizas");
        lblTitulo.setForeground(Color.white);
        lblTitulo.setBounds(400, 10, 250, 30);
        add(lblTitulo);

        // Columna 1 (IDs y Datos Principales)
        crearLabel("ID Póliza:", 30, 50);
        txtIdPoliza = crearTextField("0", 140, 50);

        crearLabel("N° Póliza:", 30, 90);
        txtNumeroPoliza = crearTextField("POL-001", 140, 90);

        crearLabel("ID Cotización:", 30, 130);
        txtIdCotizacion = crearTextField("0", 140, 130);

        crearLabel("ID Vehículo:", 30, 170);
        txtIdVehiculo = crearTextField("0", 140, 170);

        crearLabel("ID Compañía:", 30, 210);
        txtIdCompania = crearTextField("0", 140, 210);

        // Columna 2 (Relaciones secundarias y Fechas)
        crearLabel("ID Usuario:", 350, 50);
        txtIdUsuario = crearTextField("0", 470, 50);

        crearLabel("ID Tipo Póliza:", 350, 90);
        txtIdTipo = crearTextField("0", 470, 90);

        crearLabel("ID Póliza Ant.:", 350, 130);
        txtIdPolizaAnterior = crearTextField("0", 470, 130);

        crearLabel("Emisión (AAAA-MM-DD):", 350, 170);
        txtFechaEmision = crearTextField("2026-11-17", 470, 170);

        crearLabel("Inicio (AAAA-MM-DD):", 350, 210);
        txtFechaInicio = crearTextField("2026-11-17", 470, 170); // Ajustado verticalmente abajo

        // Columna 3 (Vencimiento, Costo, Estatus)
        crearLabel("Vencimiento:", 680, 50);
        txtFechaVencimiento = crearTextField("2027-11-17", 780, 50);

        crearLabel("Prima Total:", 680, 90);
        txtPrimaTotal = crearTextField("0.0", 780, 90);

        crearLabel("Estatus:", 680, 130);
        txtEstatus = crearTextField("EN_TRAMITE", 780, 130);

        cmbPolizas = new JComboBox<>();
        cmbPolizas.setBounds(680, 170, 270, 30);
        add(cmbPolizas);

        // Corregir posiciones solapadas de etiquetas y campos en columna 2 y 3 si es necesario, adaptándolas limpiamente:
        // Ajustemos txtFechaInicio bien:
        txtFechaInicio.setBounds(470, 210, 180, 30);

        // Botones CRUD
        btnConsultar = new JButton("Consultar");
        btnConsultar.setBounds(200, 260, 100, 30);
        add(btnConsultar);

        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(310, 260, 100, 30);
        add(btnAgregar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(420, 260, 100, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(530, 260, 100, 30);
        add(btnEliminar);

        // Tabla
        polizaModelo = new PolizaTM();
        tabla = new JTable(polizaModelo);
        JScrollPane sc = new JScrollPane(tabla);
        sc.setBounds(30, 310, 950, 300);
        add(sc);
    }

    private void crearLabel(String texto, int x, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(Color.white);
        lbl.setBounds(x, y, 130, 30);
        add(lbl);
    }

    private JTextField crearTextField(String texto, int x, int y) {
        JTextField txt = new JTextField(texto);
        txt.setBounds(x, y, 180, 30);
        add(txt);
        return txt;
    }

    public void actualizarVista(List<PolizaEntidad> lista) {
        polizaModelo.vaciarLista();
        polizaModelo.llenar(lista);
        polizaModelo.fireTableDataChanged();

        cmbPolizas.removeAllItems();
        for (PolizaEntidad p : lista) {
            cmbPolizas.addItem(p);
        }
    }
}