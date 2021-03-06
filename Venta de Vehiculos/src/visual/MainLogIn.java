package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import logic.SQLConnection;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Font;

public class MainLogIn extends JDialog {

	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField passwordField;
	Connection dbConnection = null;
	boolean userExists = false;
	String tipoUser = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {
			MainLogIn frame = new MainLogIn();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public MainLogIn() {
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
		dbConnection = SQLConnection.connect();
		setResizable(false);
		setTitle("Iniciando sesi\u00F3n");
		setBounds(100, 100, 474, 320);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		//setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/imagen/titlelogin.png")));

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.add(panel);
		panel.setLayout(null);

		JButton btnLogIn = new JButton("Iniciar sesi�n");
		btnLogIn.setFont(new Font("Roboto", Font.PLAIN, 12));
		btnLogIn.setBounds(262, 187, 141, 31);
		panel.add(btnLogIn);

		txtUsuario = new JTextField();
		txtUsuario.setFont(new Font("Roboto", Font.PLAIN, 12));
		txtUsuario.setBounds(49, 90, 369, 29);
		panel.add(txtUsuario);
		txtUsuario.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Roboto", Font.PLAIN, 12));
		passwordField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String user = txtUsuario.getText();
				String password = String.valueOf(passwordField.getPassword());
				String consulta = "select u.usuario, t.descripcion, u.clave from Users as u join TipoUser as t on u.idTipoUser = t.idTipoUser";
				try
				{
					if(dbConnection.isClosed())
						dbConnection = SQLConnection.connect();
										
					PreparedStatement statement = dbConnection.prepareStatement(consulta);
					ResultSet query = null;
					try
					{
						//query = statement.executeQuery("select u.usuario, t.descripcion, u.clave from Users as u join TipoUser as t on u.idTipoUser = t.idTipoUser");

						query = statement.executeQuery();
						
						while(query.next() && !userExists)
						{
							if(user.equalsIgnoreCase(query.getString(1)) && password.equals(query.getString(3)))
							{
								userExists = true;
								tipoUser = query.getString(2);

								if(tipoUser.equalsIgnoreCase("Administrador"))
								{
									//query.close();
									JOptionPane.showMessageDialog(null, user +" ha iniciado secci�n correctamente", "Notificaci�n", JOptionPane.INFORMATION_MESSAGE);
									MainAdmin window = new MainAdmin(dbConnection, user);
									dispose();
									window.setVisible(true);

								}
								else if(tipoUser.equalsIgnoreCase("Vendedor"))
								{
									//query.close();
									JOptionPane.showMessageDialog(null, user +" ha iniciado secci�n correctamente", "Notificaci�n", JOptionPane.INFORMATION_MESSAGE);
									MainVendedor window = new MainVendedor(dbConnection, user);
									window.setVisible(true);
									dispose();
								}
								else if(tipoUser.equalsIgnoreCase("Cliente"))
								{
									//query.close();
									JOptionPane.showMessageDialog(null, user +" ha iniciado secci�n correctamente", "Notificaci�n", JOptionPane.INFORMATION_MESSAGE);
									MainCliente window = new MainCliente(dbConnection, user);
									dispose();
									window.setVisible(true);
								}
								else
									JOptionPane.showMessageDialog(null, "Tipo de usuario no v�lido", "Error", JOptionPane.WARNING_MESSAGE, null);
							}
						}
					}catch (SQLException a) 
					{
			            a.printStackTrace();

					
					}finally
					{
						if(!query.isClosed())
						{
							statement.close();
							query.close();
						}
						//dbConnection.close();
					}
					
					if(!userExists)
					{
						JOptionPane.showMessageDialog(null, "Nombre de usuario o contrase�a incorrecto", "Error", JOptionPane.WARNING_MESSAGE, null);
						
					}

				} catch (SQLException e1) {
					// TODO Auto-generated catch block				
					e1.printStackTrace();
				}
			}
		});
		passwordField.setBounds(49, 145, 369, 29);
		panel.add(passwordField);

		JLabel lblNewLabel = new JLabel("Olvido la contrase\u00F1a ? Click");
		lblNewLabel.setFont(new Font("Roboto", Font.PLAIN, 12));

		lblNewLabel.setBounds(59, 195, 158, 14);
		panel.add(lblNewLabel);

		JLabel lblAqui = new JLabel("aqu\u00ED");
		lblAqui.setFont(new Font("Roboto", Font.PLAIN, 12));
		lblAqui.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "id: admin | clave: 123", "Aviso", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		lblAqui.setForeground(Color.BLUE);
		lblAqui.setBounds(217, 195, 46, 14);
		panel.add(lblAqui);

		JLabel lblPlanificadoraDeEventos = new JLabel("Venta de veh�culos");
		lblPlanificadoraDeEventos.setFont(new Font("Dialog", Font.PLAIN, 29));
		lblPlanificadoraDeEventos.setBounds(115, 11, 252, 68);
		panel.add(lblPlanificadoraDeEventos);

		JLabel lblRegistrarse = new JLabel("Para registrarse:");
		lblRegistrarse.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblRegistrarse.setBounds(59, 237, 204, 14);
		panel.add(lblRegistrarse);

		JButton btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new RegistroUser(dbConnection).setVisible(true);
			}
		});
		btnRegistrarse.setFont(new Font("Dialog", Font.PLAIN, 12));
		btnRegistrarse.setBounds(262, 229, 141, 31);
		panel.add(btnRegistrarse);
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String user = txtUsuario.getText();
				String password = String.valueOf(passwordField.getPassword());
				String consulta = "select u.usuario, t.descripcion, u.clave from Users as u join TipoUser as t on u.idTipoUser = t.idTipoUser";
				try
				{
					if(dbConnection.isClosed())
						dbConnection = SQLConnection.connect();
										
					PreparedStatement statement = dbConnection.prepareStatement(consulta);
					ResultSet query = null;
					try
					{
						//query = statement.executeQuery("select u.usuario, t.descripcion, u.clave from Users as u join TipoUser as t on u.idTipoUser = t.idTipoUser");

						query = statement.executeQuery();
						
						while(query.next() && !userExists)
						{
							if(user.equalsIgnoreCase(query.getString(1)) && password.equals(query.getString(3)))
							{
								userExists = true;
								tipoUser = query.getString(2);

								if(tipoUser.equalsIgnoreCase("Administrador"))
								{
									//query.close();
									JOptionPane.showMessageDialog(null, user +" ha iniciado secci�n correctamente", "Notificaci�n", JOptionPane.INFORMATION_MESSAGE);
									MainAdmin window = new MainAdmin(dbConnection, user);
									dispose();
									window.setVisible(true);

								}
								else if(tipoUser.equalsIgnoreCase("Vendedor"))
								{
									//query.close();
									JOptionPane.showMessageDialog(null, user +" ha iniciado secci�n correctamente", "Notificaci�n", JOptionPane.INFORMATION_MESSAGE);
									MainVendedor window = new MainVendedor(dbConnection, user);
									window.setVisible(true);
									dispose();
								}
								else if(tipoUser.equalsIgnoreCase("Cliente"))
								{
									//query.close();
									JOptionPane.showMessageDialog(null, user +" ha iniciado secci�n correctamente", "Notificaci�n", JOptionPane.INFORMATION_MESSAGE);
									MainCliente window = new MainCliente(dbConnection, user);
									dispose();
									window.setVisible(true);
								}
								else
									JOptionPane.showMessageDialog(null, "Tipo de usuario no v�lido", "Error", JOptionPane.WARNING_MESSAGE, null);
							}
						}
					}catch (SQLException a) 
					{
			            a.printStackTrace();

					
					}finally
					{
						if(!query.isClosed())
						{
							statement.close();
							query.close();
						}
						//dbConnection.close();
					}
					
					if(!userExists)
					{
						JOptionPane.showMessageDialog(null, "Nombre de usuario o contrase�a incorrecto", "Error", JOptionPane.WARNING_MESSAGE, null);
						
					}

				} catch (SQLException e1) {
					// TODO Auto-generated catch block				
					e1.printStackTrace();
				}
			}
		});
	}
}
