package polizas.General;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Bienvenida extends JFrame {
    protected JMenuBar mb;
    protected JMenu mA, mBD;
    protected JMenuItem mCancelaciones, mNotificaciones, mDocumentos;
    protected JMenuItem mCoberturas, mDetalleCob, mPagos;
    protected JMenuItem mClientes, mVehiculos, mCompanias, mPolizasSistema;
    protected JMenuItem home, mTipoPoliza, mUsuarios, mCotizaciones;
    protected JMenuItem mensaje, marcas, mSubmarcas;
    protected CardLayout cl;
    protected JPanel pi, ps, pd, pc;

    public Bienvenida() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.menu();

        // Panel izquierdo
        pi = new JPanel();
        pi.setBackground(new Color(30, 30, 30));
        JLabel lblIzq = new JLabel("Panel izquierdo");
        lblIzq.setForeground(Color.white);
        pi.add(lblIzq);
        pi.setPreferredSize(new Dimension(200, getHeight()));

        // Panel Superior
        ps = new JPanel();
        ps.setBackground(new Color(20, 40, 80));
        JLabel titulo = new JLabel("Sistema de Gestión...");
        titulo.setForeground(Color.white);
        Font fuente = new Font("Arial", Font.BOLD, 18);
        titulo.setFont(fuente);
        ps.add(titulo);

        // Panel central con CardLayout
        cl = new CardLayout();
        pc = new JPanel(cl);
        pc.setBackground(new Color(40, 40, 40));

        JLabel lblWelcome = new JLabel("Welcome to Java ...");
        lblWelcome.setForeground(Color.white);
        pc.add(lblWelcome, "home");
        pc.add(new Mensaje(), "mensaje");
        cl.show(pc, "home");

        // Panel derecho
        pd = new JPanel();
        pd.setBackground(new Color(30, 30, 30));
        JLabel lblDer = new JLabel("Panel derecho");
        lblDer.setForeground(Color.white);
        pd.add(lblDer);
        pd.setPreferredSize(new Dimension(200, getHeight()));

        this.setLayout(new BorderLayout());
        this.add(pi, BorderLayout.WEST);
        this.add(pd, BorderLayout.EAST);
        this.add(ps, BorderLayout.NORTH);
        this.add(pc, BorderLayout.CENTER);
        this.setVisible(true);
    }

    private void menu() {
        this.mb = new JMenuBar();
        this.setJMenuBar(mb);

        this.mA = new JMenu("Archivo");
        home = new JMenuItem("Home");
        this.mA.add(home);
        mensaje = new JMenuItem("Mensaje");
        this.mA.add(mensaje);

        this.mBD = new JMenu("Base de Datos");
        marcas = new JMenuItem("Marcas");
        mSubmarcas = new JMenuItem("Submarcas");
        mClientes = new JMenuItem("Clientes");
        mVehiculos = new JMenuItem("Vehículos");
        mCompanias = new JMenuItem("Compañías de Seguros");
        mTipoPoliza = new JMenuItem("Tipos de Póliza");
        mUsuarios = new JMenuItem("Usuarios");
        mCotizaciones = new JMenuItem("Cotizaciones");
        mPolizasSistema = new JMenuItem("Pólizas");
        mCoberturas = new JMenuItem("Coberturas");
        mDetalleCob = new JMenuItem("Detalle Coberturas");
        mPagos = new JMenuItem("Pagos");
        mCancelaciones = new JMenuItem("Cancelaciones");
        mNotificaciones = new JMenuItem("Notificaciones");
        mDocumentos = new JMenuItem("Documentos");

        mBD.add(mCancelaciones);
        mBD.add(mNotificaciones);
        mBD.add(mDocumentos);
        mBD.add(mCoberturas);
        mBD.add(mDetalleCob);
        mBD.add(mPagos);
        mBD.add(mPolizasSistema);
        mBD.add(mCotizaciones);
        mBD.add(mUsuarios);
        mBD.add(mTipoPoliza);
        mBD.add(mCompanias);
        mBD.add(marcas);
        mBD.add(mSubmarcas);
        mBD.add(mClientes);
        mBD.add(mVehiculos);

        this.mb.add(mA);
        this.mb.add(mBD);
    }
}