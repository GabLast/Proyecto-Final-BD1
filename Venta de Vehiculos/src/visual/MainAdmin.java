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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class MainAdmin extends JFrame {

	private JPanel contentPane;
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
					dbConnection.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		setTitle("Ventana de Administradores");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 808, 545);
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Gesti\u00F3n de Anuncios");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Listado de Anuncios");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AnunciosAdmin(dbConnection).setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
