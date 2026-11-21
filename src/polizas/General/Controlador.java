package polizas.General;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;
import polizas.Clientes.ClienteControl;
import polizas.Clientes.ClienteVista;
import polizas.Companias.CompaniaControl;
import polizas.Companias.CompaniaVista;
import polizas.Cotizaciones.CotizacionControl;
import polizas.Cotizaciones.CotizacionVista;
import polizas.Marcas.MarcasControl;
import polizas.Marcas.MarcasVista;
import polizas.Polizas.PolizaControl;
import polizas.Polizas.PolizaVista;
import polizas.TiposPoliza.TipoPolizaControl;
import polizas.TiposPoliza.TipoPolizaVista;
import polizas.Usuarios.UsuarioControl;
import polizas.Usuarios.UsuarioVista;
import polizas.Vehiculos.VehiculoControl;
import polizas.Vehiculos.VehiculoVista;

public class Controlador {

    protected Bienvenida vista;
    protected MarcasVista marcasVista;
    private final Map<String, JPanel> panelesActivos = new HashMap<>();

    public Controlador(Bienvenida vista) {
        this.vista = vista;
        this.eventos();
    }

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
        vista.mensaje.addActionListener(
            e -> vista.cl.show(vista.pc, "mensaje")
        );

        vista.home.addActionListener(e -> {
            JPanel panelHome = new JPanel(null);
            panelHome.setBackground(new Color(40, 40, 40));
            JLabel lblHome = new JLabel("Bienvenido al Gestor de Pólizas. Abre los módulos desde el menú Base de Datos.");
            lblHome.setForeground(Color.WHITE);
            lblHome.setBounds(250, 20, 600, 30);
            panelHome.add(lblHome);
            mostrarPanel(panelHome, "home");
        });

        vista.marcas.addActionListener(e -> {
            marcasVista = new MarcasVista();
            new MarcasControl(marcasVista);
            mostrarPanel(marcasVista, "mk");
        });

        vista.mClientes.addActionListener(e -> {
            ClienteVista clienteVista = new ClienteVista();
            new ClienteControl(clienteVista);
            mostrarPanel(clienteVista, "clientesKey");
        });

        vista.mVehiculos.addActionListener(e -> {
            VehiculoVista vehiculoVista = new VehiculoVista();
            new VehiculoControl(vehiculoVista);
            mostrarPanel(vehiculoVista, "vehiculosKey");
        });

        vista.mCompanias.addActionListener(e -> {
            CompaniaVista companiasVista = new CompaniaVista();
            new CompaniaControl(companiasVista);
            mostrarPanel(companiasVista, "companiasKey");
        });

        vista.mTipoPoliza.addActionListener(e -> {
            TipoPolizaVista tpVista = new TipoPolizaVista();
            new TipoPolizaControl(tpVista);
            mostrarPanel(tpVista, "tipoPolizaKey");
        });

        vista.mUsuarios.addActionListener(e -> {
            UsuarioVista uVista = new UsuarioVista();
            new UsuarioControl(uVista);
            mostrarPanel(uVista, "usuariosKey");
        });

        vista.mCotizaciones.addActionListener(e -> {
            CotizacionVista cVista = new CotizacionVista();
            new CotizacionControl(cVista);
            mostrarPanel(cVista, "cotizacionesKey");
        });

        vista.mPolizasSistema.addActionListener(e -> {
            PolizaVista pVista = new PolizaVista();
            new PolizaControl(pVista);
            mostrarPanel(pVista, "polizasKey");
        });

        vista.mCoberturas.addActionListener(e -> {
            var v = new polizas.Coberturas.CoberturaVista();
            new polizas.Coberturas.CoberturaControl(v);
            mostrarPanel(v, "cobKey");
        });

        vista.mDetalleCob.addActionListener(e -> {
            var v = new polizas.DetalleCobertura.DetalleCoberturaVista();
            new polizas.DetalleCobertura.DetalleCoberturaControl(v);
            mostrarPanel(v, "detCobKey");
        });

        vista.mPagos.addActionListener(e -> {
            var v = new polizas.Pagos.PagoVista();
            new polizas.Pagos.PagoControl(v);
            mostrarPanel(v, "pagoKey");
        });

        vista.mCancelaciones.addActionListener(e -> {
            var v = new polizas.Cancelaciones.CancelacionVista();
            new polizas.Cancelaciones.CancelacionControl(v);
            mostrarPanel(v, "cancKey");
        });

        vista.mNotificaciones.addActionListener(e -> {
            var v = new polizas.Notificaciones.NotificacionVista();
            new polizas.Notificaciones.NotificacionControl(v);
            mostrarPanel(v, "notifKey");
        });

        vista.mDocumentos.addActionListener(e -> {
            var v = new polizas.Documentos.DocumentoVista();
            new polizas.Documentos.DocumentoControl(v);
            mostrarPanel(v, "docKey");
        });
    }
}