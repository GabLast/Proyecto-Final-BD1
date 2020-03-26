package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import logic.SQLConnection;
import logic.User;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;

public class RegistroUser extends JDialog {

	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField passwordField;

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
					Connection dbConnection = null;
					RegistroUser frame = new RegistroUser(dbConnection);
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
	public RegistroUser(Connection dbConnection) {
		setTitle("Registrando un nuevo usuario");
		setBounds(100, 100, 380, 248);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registro de usuario", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JComboBox cbxTipoUser = new JComboBox();
		cbxTipoUser.setModel(new DefaultComboBoxModel(new String[] {"Administrador", "Vendedor", "Cliente"}));
		cbxTipoUser.setSelectedIndex(0);
		cbxTipoUser.setBounds(105, 84, 233, 20);
		panel.add(cbxTipoUser);
		
		JLabel lblNewLabel = new JLabel("Nombre de usuario:");
		lblNewLabel.setBounds(10, 36, 102, 14);
		panel.add(lblNewLabel);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(105, 32, 233, 20);
		panel.add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblTipoDeCuenta = new JLabel("Tipo de cuenta:");
		lblTipoDeCuenta.setBounds(10, 86, 102, 14);
		panel.add(lblTipoDeCuenta);
		
		JButton btnNewButton = new JButton("Registrarse");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(txtUsername.getText().isEmpty() || passwordField.getPassword().length <= 0 || cbxTipoUser.getSelectedItem().toString().length() < 0)
				{
					JOptionPane.showMessageDialog(null, "Llene todos los campos", "Error", JOptionPane.WARNING_MESSAGE, null);
					
				}
				else
				{
					User user = new User(cbxTipoUser.getSelectedItem().toString(), txtUsername.getText(), String.copyValueOf(passwordField.getPassword()));
					//System.out.println(user.getTipo());
					if(user.getTipo().equalsIgnoreCase("Administrador"))
					{
						user.setTipo("00");
					}
					else if(user.getTipo().equalsIgnoreCase("Vendedor"))
					{
						user.setTipo("01");
					}else if(user.getTipo().equalsIgnoreCase("Cliente"))
					{
						user.setTipo("02");
					}else
					{
						JOptionPane.showMessageDialog(null, "Tipo de usuario no válido", "Error", JOptionPane.WARNING_MESSAGE, null);
					}
					//System.out.println(user.getTipo());
					try
					{
						
						String insert = String.format("exec insertarUsuario @signid = '%s', @tipo = '%s', @clave = '%s';", user.getUserName(), user.getTipo(), user.getPass());
						dbConnection.prepareCall(insert).execute();
						JOptionPane.showMessageDialog(null, "Usuario Registrado Satisfactoriamente", null, JOptionPane.INFORMATION_MESSAGE, null);
						dispose();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						
						e1.printStackTrace();
						
					}
					
					
					
				}
				
			}
		});
		btnNewButton.setBounds(249, 165, 89, 23);
		panel.add(btnNewButton);
		
		JLabel lblClave = new JLabel("Clave:");
		lblClave.setBounds(10, 136, 102, 14);
		panel.add(lblClave);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Dialog", Font.PLAIN, 12));
		passwordField.setBounds(105, 136, 233, 20);
		panel.add(passwordField);
	}
}
