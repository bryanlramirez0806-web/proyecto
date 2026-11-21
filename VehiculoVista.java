package polizas.Vehiculos;

import java.awt.Color;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class VehiculoVista extends JPanel {
    protected JButton btnConsultar, btnAgregar, btnActualizar, btnEliminar;
    protected JTextField txtIdVehiculo, txtIdCliente, txtIdSubmarca, txtModelo, txtTipoVehiculo, txtNumeroSerie, txtPlacas;
    protected JLabel lblTitulo, lblIdVehiculo, lblIdCliente, lblIdSubmarca, lblModelo, lblTipoVehiculo, lblNumeroSerie, lblPlacas;
    protected JTable tabla;
    protected VehiculoTM vehiculoModelo;
    protected JComboBox<Vehiculo> cmbVehiculos;

    public VehiculoVista() {
        setLayout(null);
        setBackground(new Color(40, 40, 40));

        lblTitulo = new JLabel("Administración de Vehículos");
        lblTitulo.setForeground(Color.white);
        lblTitulo.setBounds(400, 10, 200, 30);
        add(lblTitulo);

        // Campos de Texto y Etiquetas
        lblIdVehiculo = crearLabel("ID Vehículo:", 30, 50);
        txtIdVehiculo = crearTextField("0", 130, 50);

        lblIdCliente = crearLabel("ID Cliente:", 30, 90);
        txtIdCliente = crearTextField("0", 130, 90);

        // Reemplazado Marca y Submarca de texto por ID Submarca numérico
        lblIdSubmarca = crearLabel("ID Submarca:", 30, 130);
        txtIdSubmarca = crearTextField("0", 130, 130);

        lblModelo = crearLabel("Modelo:", 450, 50);
        txtModelo = crearTextField("Modelo", 550, 50);

        lblTipoVehiculo = crearLabel("Tipo Vehículo:", 450, 90);
        txtTipoVehiculo = crearTextField("Tipo", 550, 90);

        lblNumeroSerie = crearLabel("N° Serie:", 450, 130);
        txtNumeroSerie = crearTextField("Serie", 550, 130);

        lblPlacas = crearLabel("Placas:", 450, 170);
        txtPlacas = crearTextField("Placas", 550, 170);

        cmbVehiculos = new JComboBox<>();
        cmbVehiculos.setBounds(450, 210, 350, 30);
        add(cmbVehiculos);

        // Botones CRUD
        btnConsultar = new JButton("Consultar");
        btnConsultar.setBounds(130, 260, 100, 30);
        add(btnConsultar);

        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(240, 260, 100, 30);
        add(btnAgregar);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(350, 260, 100, 30);
        add(btnActualizar);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(460, 260, 100, 30);
        add(btnEliminar);

        // Tabla
        vehiculoModelo = new VehiculoTM();
        tabla = new JTable(vehiculoModelo);
        JScrollPane sc = new JScrollPane(tabla);
        sc.setBounds(30, 310, 950, 300);
        add(sc);
    }

    private JLabel crearLabel(String texto, int x, int y) {
        JLabel lbl = new JLabel(texto);
        lbl.setForeground(Color.white);
        lbl.setBounds(x, y, 100, 30);
        add(lbl);
        return lbl;
    }

    private JTextField crearTextField(String texto, int x, int y) {
        JTextField txt = new JTextField(texto);
        txt.setBounds(x, y, 250, 30);
        add(txt);
        return txt;
    }

    public void actualizarVista(List<Vehiculo> lista) {
        vehiculoModelo.vaciarLista();
        vehiculoModelo.llenar(lista);
        vehiculoModelo.fireTableDataChanged();

        cmbVehiculos.removeAllItems();
        if (lista != null) {
            for (Vehiculo v : lista) {
                cmbVehiculos.addItem(v);
            }
        }
    }
}