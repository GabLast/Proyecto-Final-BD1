package visual;

import java.awt.BorderLayout;
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
import java.awt.event.ActionEvent;

public class MainVendedor extends JFrame {

	private JPanel contentPane;
	Connection dbConnection = null;
	int idVendedor = -1;
	String nombre = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				} catch (Throwable e) {
					e.printStackTrace();
				}
				try {
					MainVendedor frame = new MainVendedor(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainVendedor(String user) {
		dbConnection = SQLConnection.connect();
		
		String buscarVendedor = String.format("select u.usuario, p.idPersona, v.idVendedor, p.nombre from Persona p join Users u on p.idPersona = "
				+ "u.idPersona join Vendedor v on v.idPersona = p.idPersona where u.usuario = '%s'", user);
		try {
			Statement st2;
			st2 = dbConnection.createStatement();
			ResultSet rs3 = st2.executeQuery(buscarVendedor);
			
			while(rs3.next() && idVendedor == -1)
			{
				idVendedor = Integer.valueOf(rs3.getString(3));
				nombre = rs3.getString(4);
			}
			
			rs3.close();
		} catch (SQLException e5) {
			// TODO Auto-generated catch block
			e5.printStackTrace();
		}
		
		setTitle("Ventana del vendedor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 762, 494);
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnVehiculos = new JMenu("Gesti\u00F3n de Veh\u00EDculos");
		menuBar.add(mnVehiculos);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Registrar Veh\u00EDculo");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new RegistrarVehiculo(dbConnection, idVendedor).setVisible(true);
			}
		});
		mnVehiculos.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Listado de Veh\u00EDculos");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		mnVehiculos.add(mntmNewMenuItem_2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
