package polizas.General;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;
import polizas.Aseguradoras.AseguradoraControl;
import polizas.Aseguradoras.AseguradoraVista;
import polizas.Clientes.ClienteControl;
import polizas.Clientes.ClienteVista;
import polizas.Coberturas.CoberturaControl;
import polizas.Coberturas.CoberturaVista;
import polizas.FormasPago.FormaPagoControl;
import polizas.FormasPago.FormaPagoVista;
import polizas.Marcas.MarcaControl;
import polizas.Marcas.MarcaVista;
import polizas.Modelos.ModeloControl;
import polizas.Modelos.ModeloVista;
import polizas.Polizas.PolizaControl;
import polizas.Polizas.PolizaVista;
import polizas.UsosVehiculo.UsoVehiculoControl;
import polizas.UsosVehiculo.UsoVehiculoVista;
import polizas.Vehiculos.VehiculoControl;
import polizas.Vehiculos.VehiculoVista;

public class Controlador {

    protected Bienvenida vista;
    private final Map<String, JPanel> panelesActivos = new HashMap<>();

    public Controlador(Bienvenida vista) {
        this.vista = vista;
        this.eventos();
    }

    // Cambia el panel del centro por el del módulo que se abrió
    private void mostrarPanel(JPanel nuevoPanel, String key) {
        JPanel anterior = panelesActivos.get(key);
        if (anterior != null) {
            vista.pc.remove(anterior);
        }
        vista.pc.add(nuevoPanel, key);
        panelesActivos.put(key, nuevoPanel);
        vista.pc.revalidate();
        vista.pc.repaint();
        vista.cl.show(vista.pc, key);
    }

    void eventos() {
        vista.mensaje.addActionListener(e -> vista.cl.show(vista.pc, "mensaje"));

        vista.home.addActionListener(e -> {
            JPanel panelHome = new JPanel(null);
            panelHome.setBackground(new Color(40, 40, 40));
            JLabel lblHome = new JLabel("Bienvenido al Gestor de Pólizas. Abre los módulos desde el menú Módulos o Catálogos.");
            lblHome.setForeground(Color.WHITE);
            lblHome.setBounds(250, 20, 650, 30);
            panelHome.add(lblHome);
            mostrarPanel(panelHome, "home");
        });

        // ---------- Módulos principales ----------
        vista.mClientes.addActionListener(e -> {
            ClienteVista v = new ClienteVista();
            new ClienteControl(v);
            mostrarPanel(v, "clientes");
        });

        vista.mVehiculos.addActionListener(e -> {
            VehiculoVista v = new VehiculoVista();
            new VehiculoControl(v);
            mostrarPanel(v, "vehiculos");
        });

        vista.mPolizas.addActionListener(e -> {
            PolizaVista v = new PolizaVista();
            new PolizaControl(v);
            mostrarPanel(v, "polizas");
        });

        // ---------- Catálogos ----------
        vista.mMarcas.addActionListener(e -> {
            MarcaVista v = new MarcaVista();
            new MarcaControl(v);
            mostrarPanel(v, "marcas");
        });

        vista.mModelos.addActionListener(e -> {
            ModeloVista v = new ModeloVista();
            new ModeloControl(v);
            mostrarPanel(v, "modelos");
        });

        vista.mUsos.addActionListener(e -> {
            UsoVehiculoVista v = new UsoVehiculoVista();
            new UsoVehiculoControl(v);
            mostrarPanel(v, "usos");
        });

        vista.mAseguradoras.addActionListener(e -> {
            AseguradoraVista v = new AseguradoraVista();
            new AseguradoraControl(v);
            mostrarPanel(v, "aseguradoras");
        });

        vista.mFormasPago.addActionListener(e -> {
            FormaPagoVista v = new FormaPagoVista();
            new FormaPagoControl(v);
            mostrarPanel(v, "formasPago");
        });

        vista.mCoberturas.addActionListener(e -> {
            CoberturaVista v = new CoberturaVista();
            new CoberturaControl(v);
            mostrarPanel(v, "coberturas");
        });
    }
}
