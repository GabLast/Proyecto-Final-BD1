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
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import logic.SQLConnection;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Font;

public class MainLogIn extends JFrame {

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
		EventQueue.invokeLater(new Runnable() {
			public void run() {
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
		});
	}

	/**
	 * Create the frame.
	 */
	public MainLogIn() {
		dbConnection = SQLConnection.connect();
		setResizable(false);
		setTitle("Iniciando sesi\u00F3n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		JButton btnLogIn = new JButton("Iniciar sesión");
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
				
				try
				{
					Statement statement = dbConnection.createStatement();
					ResultSet query = statement.executeQuery("select u.signId, t.descripcion, u.clave from Users as u join TipoUser as t on u.idTipoUser = t.idTipoUser");
					
					
					while(query.next() && !userExists)
					{
						if(user.equalsIgnoreCase(query.getString(1)) && password.equals(query.getString(3)))
						{
							userExists = true;
							tipoUser = query.getString(2);
							
							if(tipoUser.equalsIgnoreCase("Administrador"))
							{
								query.close();
								JOptionPane.showMessageDialog(null, user +" ha iniciado sección correctamente", "Notificación", JOptionPane.INFORMATION_MESSAGE);
								MainAdmin window = new MainAdmin(dbConnection);
								dispose();
								window.setVisible(true);
								
							}
							else if(tipoUser.equalsIgnoreCase("Vendedor"))
							{
								query.close();
								JOptionPane.showMessageDialog(null, user +" ha iniciado sección correctamente", "Notificación", JOptionPane.INFORMATION_MESSAGE);
								MainVendedor window = new MainVendedor(dbConnection);
								dispose();
								window.setVisible(true);
							}
							else if(tipoUser.equalsIgnoreCase("Cliente"))
							{
								query.close();
								JOptionPane.showMessageDialog(null, user +" ha iniciado sección correctamente", "Notificación", JOptionPane.INFORMATION_MESSAGE);
								MainCliente window = new MainCliente(dbConnection);
								dispose();
								window.setVisible(true);
							}
							else
								JOptionPane.showMessageDialog(null, "Tipo de usuario no válido", "Error", JOptionPane.WARNING_MESSAGE, null);
						}
					}
					
					query.close();
					
					if(!userExists)
					{
						JOptionPane.showMessageDialog(null, "Nombre de usuario o contraseña incorrecto", "Error", JOptionPane.WARNING_MESSAGE, null);
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
		
		JLabel lblAqui = new JLabel("aqui");
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
		
		JLabel lblPlanificadoraDeEventos = new JLabel("Venta de vehículos");
		lblPlanificadoraDeEventos.setFont(new Font("Dialog", Font.PLAIN, 29));
		lblPlanificadoraDeEventos.setBounds(115, 11, 252, 68);
		panel.add(lblPlanificadoraDeEventos);
		
		JLabel lblRegistrarse = new JLabel("Para registrarse, click aqu\u00ED");
		lblRegistrarse.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblRegistrarse.setBounds(59, 237, 204, 14);
		panel.add(lblRegistrarse);
		
		JButton btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnRegistrarse.setFont(new Font("Dialog", Font.PLAIN, 12));
		btnRegistrarse.setBounds(262, 229, 141, 31);
		panel.add(btnRegistrarse);
//		btnLogIn.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if(Empresa.getInstance().confirmLogin(txtUsuario.getText(),String.valueOf(passwordField.getPassword())))
//				{
//					Principal frame = new Principal();
//					dispose();
//					frame.setVisible(true);
//				}
//				else
//				{
//					JOptionPane.showMessageDialog(null, "Digite un usuario y/o contraseña.", "Error", JOptionPane.WARNING_MESSAGE);
//					
//					
//				}
//			}
//		});
	}
}
