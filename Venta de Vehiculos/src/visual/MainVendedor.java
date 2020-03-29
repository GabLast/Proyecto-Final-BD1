package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;

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
				new RegistrarVehiculo(dbConnection, user).setVisible(true);
			}
		});
		mnVehiculos.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Listado de Veh\u00EDculos");
		mnVehiculos.add(mntmNewMenuItem_2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
