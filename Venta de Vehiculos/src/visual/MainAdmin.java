package visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import logic.SQLConnection;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class MainAdmin extends JFrame {

	private JPanel contentPane;
	private Dimension dim;
	//Connection dbConnection = null;

	public MainAdmin(Connection dbConnection, String user) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					if(!dbConnection.isClosed())
						dbConnection.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		setTitle("Ventana de Administradores");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dim = super.getToolkit().getScreenSize();
		dim.width *= .85;
		dim.height *= .85;
		super.setSize(dim.width, dim.height);
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Gesti\u00F3n de Anuncios");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Listado de Anuncios");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AnunciosAdmin().setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenu mnConsultas = new JMenu("Consultas");
		menuBar.add(mnConsultas);
		
		JMenuItem mntmVehculosEnVenta = new JMenuItem("Veh\u00EDculos en venta");
		mntmVehculosEnVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new ConsultaGeneralVehiculosVenta(dbConnection).setVisible(true);
			}
		});
		mnConsultas.add(mntmVehculosEnVenta);
		
		JMenuItem mntmVendedores = new JMenuItem("Vendedores");
		mntmVendedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ConsultaVendedores(dbConnection).setVisible(true);;
			}
		});
		mnConsultas.add(mntmVendedores);
		
		JMenuItem mntmClientes = new JMenuItem("Clientes");
		mntmClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ConsultaGeneralUsuarios(dbConnection).setVisible(true);
			}
		});
		mnConsultas.add(mntmClientes);
		
		JMenuItem mntmAnuncios = new JMenuItem("Anuncios");
		mntmAnuncios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ConsultaPublicacion(dbConnection).setVisible(true);
			}
		});
		mnConsultas.add(mntmAnuncios);
		
		JMenuItem mntmVendedoresConMayores = new JMenuItem("Vendedores con Mayores Ventas");
		mntmVendedoresConMayores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new ConsultaVendedoresMayorVenntas(dbConnection).setVisible(true);
			}
		});
		mnConsultas.add(mntmVendedoresConMayores);
		
		JMenuItem mntmMostrarVehculosRegistrados = new JMenuItem("Mostrar Veh\u00EDculos Registrados");
		mntmMostrarVehculosRegistrados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new MostrarVehículosPorMarca(dbConnection).setVisible(true);
			}
		});
		mnConsultas.add(mntmMostrarVehculosRegistrados);
		
		JMenuItem mntmVentas = new JMenuItem("Ventas");
		mntmVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ConsultaVentas(dbConnection).setVisible(true);
			}
		});
		mnConsultas.add(mntmVentas);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
