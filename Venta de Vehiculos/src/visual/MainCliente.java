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
import java.awt.event.ActionEvent;

public class MainCliente extends JFrame {

	private JPanel contentPane;
	//Connection dbConnection = null;
	private Dimension dim;
	long idcliente = -1;


	/**
	 * Create the frame.
	 */
	public MainCliente(Connection dbConnection, String user) {
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
					if(!dbConnection.isClosed())
						dbConnection.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		String buscandoCliente = String.format("select u.usuario, p.idPersona, c.idCliente, p.nombre "
				+ "from Persona p join Users u on p.idPersona = u.idPersona join Cliente as c on c.idPersona = p.idPersona where u.usuario = '%s'", user);
		
		try {
			//Statement st2;
			//st2 = dbConnection.createStatement();
			//ResultSet rs3 = st2.executeQuery(buscandoCliente);
			
			PreparedStatement st2 = dbConnection.prepareStatement(buscandoCliente);
			ResultSet rs3 = null;
			
			try {
				rs3 = st2.executeQuery();
				while(rs3.next() && idcliente == -1)
				{
					idcliente = Long.valueOf(rs3.getString(3));
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
		setTitle("Compra de Veh\u00EDculos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dim = super.getToolkit().getScreenSize();
		dim.width *= .85;
		dim.height *= .85;
		super.setSize(dim.width, dim.height);
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu_1 = new JMenu("Veh\u00EDculos");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmListarVehculosComprados = new JMenuItem("Listar Veh\u00EDculos Comprados");
		mntmListarVehculosComprados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ListarVehiculosComprados(dbConnection, idcliente).setVisible(true);;
			}
		});
		mnNewMenu_1.add(mntmListarVehculosComprados);
		
		JMenu mnNewMenu = new JMenu("Anuncios");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Veh\u00EDculos en Venta");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new ListarAnunciosCliente(dbConnection, idcliente).setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
