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
    protected JMenu mA, mModulos, mCatalogos;
    protected JMenuItem home, mensaje;
    protected JMenuItem mClientes, mVehiculos, mPolizas;
    protected JMenuItem mMarcas, mModelos, mUsos, mAseguradoras, mFormasPago, mCoberturas;
    protected CardLayout cl;
    protected JPanel pi, ps, pd, pc;

    public Bienvenida() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.menu();

        // Panel izquierdo
        pi = new JPanel();
        pi.setBackground(new Color(30, 30, 30));
        JLabel lblIzq = new JLabel("Gestor de Pólizas");
        lblIzq.setForeground(Color.white);
        pi.add(lblIzq);
        pi.setPreferredSize(new Dimension(200, getHeight()));

        // Panel Superior
        ps = new JPanel();
        ps.setBackground(new Color(20, 40, 80));
        JLabel titulo = new JLabel("Sistema de Gestión de Pólizas");
        titulo.setForeground(Color.white);
        Font fuente = new Font("Arial", Font.BOLD, 18);
        titulo.setFont(fuente);
        ps.add(titulo);

        // Panel central con CardLayout
        cl = new CardLayout();
        pc = new JPanel(cl);
        pc.setBackground(new Color(40, 40, 40));

        JLabel lblWelcome = new JLabel("Bienvenido al Gestor de Pólizas. Abre los módulos desde el menú Módulos o Catálogos.", javax.swing.SwingConstants.CENTER);
        lblWelcome.setForeground(Color.white);
        pc.add(lblWelcome, "home");
        pc.add(new Mensaje(), "mensaje");
        cl.show(pc, "home");

        // Panel derecho
        pd = new JPanel();
        pd.setBackground(new Color(30, 30, 30));
        JLabel lblDer = new JLabel("Equipo 5");
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

        // Los tres módulos principales del sistema
        this.mModulos = new JMenu("Módulos");
        mClientes = new JMenuItem("Clientes");
        mVehiculos = new JMenuItem("Vehículos");
        mPolizas = new JMenuItem("Pólizas");
        mModulos.add(mClientes);
        mModulos.add(mVehiculos);
        mModulos.add(mPolizas);

        // Catálogos que usan los módulos principales
        this.mCatalogos = new JMenu("Catálogos");
        mMarcas = new JMenuItem("Marcas");
        mModelos = new JMenuItem("Modelos");
        mUsos = new JMenuItem("Usos de vehículo");
        mAseguradoras = new JMenuItem("Aseguradoras");
        mFormasPago = new JMenuItem("Formas de pago");
        mCoberturas = new JMenuItem("Coberturas");
        mCatalogos.add(mMarcas);
        mCatalogos.add(mModelos);
        mCatalogos.add(mUsos);
        mCatalogos.addSeparator();
        mCatalogos.add(mAseguradoras);
        mCatalogos.add(mFormasPago);
        mCatalogos.add(mCoberturas);

        this.mb.add(mA);
        this.mb.add(mModulos);
        this.mb.add(mCatalogos);
    }
}
