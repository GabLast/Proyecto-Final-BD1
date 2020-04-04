package visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.awt.event.ActionEvent;

public class MainVendedor extends JFrame {

	private JPanel contentPane;
	//Connection dbConnection = null;
	int idVendedor = -1;
	private Dimension dim;
	String nombre = null;
	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
	public MainVendedor(Connection dbConnection, String user) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		//dbConnection = SQLConnection.connect();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					dbConnection.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		String buscarVendedor = String.format("select u.usuario, p.idPersona, v.idVendedor, p.nombre from Persona p join Users u on p.idPersona = "
				+ "u.idPersona join Vendedor v on v.idPersona = p.idPersona where u.usuario = '%s'", user);
		try {
			//Statement st2;
			//st2 = dbConnection.createStatement();
			//ResultSet rs3 = st2.executeQuery(buscarVendedor);
			
			PreparedStatement st2 = dbConnection.prepareStatement(buscarVendedor);
			ResultSet rs3 = null;
			try
			{
				rs3 = st2.executeQuery();
				while(rs3.next() && idVendedor == -1)
				{
					idVendedor = Integer.valueOf(rs3.getString(3));
					nombre = rs3.getString(4);
				}
				
			}catch (SQLException e) {
				// TODO: handle exception
			}finally
			{
				st2.close();
				rs3.close();
			}

		} catch (SQLException e5) {
			// TODO Auto-generated catch block
			e5.printStackTrace();
		}
		
		setTitle("Ventana del vendedor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dim = super.getToolkit().getScreenSize();
		dim.width *= .85;
		dim.height *= .85;
		super.setSize(dim.width, dim.height);
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnVehiculos = new JMenu("Gesti\u00F3n de Veh\u00EDculos");
		menuBar.add(mnVehiculos);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Registrar Veh\u00EDculo");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RegistrarVehiculo window = new RegistrarVehiculo(dbConnection, idVendedor);
				window.setVisible(true);
			}
		});
		mnVehiculos.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Listado de Veh\u00EDculos");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ListarVehiculos(dbConnection, idVendedor, nombre).setVisible(true);
			}
		});
		mnVehiculos.add(mntmNewMenuItem_2);
		
		JMenu mnNewMenu = new JMenu("Anuncios");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmListadoDeAnuncios = new JMenuItem("Mis Anuncios Publicados");
		mnNewMenu.add(mntmListadoDeAnuncios);
		mntmListadoDeAnuncios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ListarAnuncios(dbConnection, idVendedor).setVisible(true);
			}
		});
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
