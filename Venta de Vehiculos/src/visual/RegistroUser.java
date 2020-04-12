package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

import logic.Cliente;
import logic.SQLConnection;
import logic.User;
import logic.Vendedor;

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
import java.text.ParseException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFormattedTextField;

public class RegistroUser extends JDialog {

	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField passwordField;
	private JTextField txtNombre;
	private JTextField txtApellido;
	JFormattedTextField txtCedula;
	JLabel lblTipoDeVendedor;
	JComboBox cbxTipoVendedor;
	JComboBox cbxTipoCuenta;
	JComboBox cbxProvincia;
	private JLabel lblEmail;
	private JTextField txtMail;
	private JFormattedTextField txtNumber1;
	Connection dbConnection = null;
	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
	public RegistroUser(Connection z) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		dbConnection = SQLConnection.connect();
		setTitle("Registrando un nuevo usuario");
		setBounds(100, 100, 380, 623);
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

		JLabel lblNewLabel = new JLabel("Nombre de usuario:");
		lblNewLabel.setBounds(10, 38, 102, 14);
		panel.add(lblNewLabel);

		txtUsername = new JTextField();
		txtUsername.setBounds(105, 35, 233, 20);
		panel.add(txtUsername);
		txtUsername.setColumns(10);

		JLabel lblTipoDeCuenta = new JLabel("Tipo de cuenta:");
		lblTipoDeCuenta.setBounds(10, 90, 102, 14);
		panel.add(lblTipoDeCuenta);

		cbxTipoCuenta = new JComboBox();
		cbxTipoCuenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(cbxTipoCuenta.getSelectedItem().toString().equalsIgnoreCase("Vendedor"))
				{
					lblTipoDeVendedor.setVisible(true);
					cbxTipoVendedor.setVisible(true);

				}
				else
				{
					lblTipoDeVendedor.setVisible(false);
					cbxTipoVendedor.setVisible(false);
				}
			}
		});
		cbxTipoCuenta.setModel(new DefaultComboBoxModel(new String[] {"Cliente", "Vendedor"}));
		cbxTipoCuenta.setBounds(105, 87, 233, 20);
		panel.add(cbxTipoCuenta);


		JButton btnNewButton = new JButton("Registrarse");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(!verificarCampos())	//txtUsername.getText().isEmpty() || passwordField.getPassword().length <= 0 || cbxTipoCuenta.getSelectedItem().toString().length() < 0)
				{
					JOptionPane.showMessageDialog(null, "Llene todos los campos", "Error", JOptionPane.WARNING_MESSAGE, null);

				}
				else
				{
					User user = new User(cbxTipoCuenta.getSelectedItem().toString(), txtUsername.getText(), String.copyValueOf(passwordField.getPassword()));
					//System.out.println(user.getTipo());
					if(user.getTipo().equalsIgnoreCase("Vendedor"))
					{
						user.setTipo("2");
					}
					else if(user.getTipo().equalsIgnoreCase("Cliente"))
					{
						user.setTipo("3");
					}else
					{
						JOptionPane.showMessageDialog(null, "Tipo de usuario no válido", "Error", JOptionPane.WARNING_MESSAGE, null);
					}
					
					if(cbxTipoCuenta.getSelectedItem().toString().equalsIgnoreCase("Cliente"))
					{
						Cliente client = new Cliente(txtCedula.getText(), txtNombre.getText(), txtApellido.getText(), String.valueOf(cbxProvincia.getSelectedIndex()+1), txtMail.getText());
						String numero = txtNumber1.getText();
						String insert = String.format("exec registrarCliente @usuario = '%s', @tipoUser = '%s', @clave = '%s', @cedula = '%s', @nombre = '%s', "
								+ "@apellido = '%s', @provincia = '%s', @email = '%s', @numero = '%s'", user.getUserName(), user.getTipo(), user.getPass(),
								client.getCedula(), client.getNombre(), client.getApellido(), client.getProvincia(), client.getEmail(), numero);
						try
						{
							dbConnection.prepareCall(insert).execute();
							dbConnection.close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block

							e1.printStackTrace();

						}

						JOptionPane.showMessageDialog(null, "Se ha registrado correctamente", "Notificación", JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}
					else if(cbxTipoCuenta.getSelectedItem().toString().equalsIgnoreCase("Vendedor"))
					{
						Vendedor vendedor = new Vendedor(txtCedula.getText(), txtNombre.getText(), txtApellido.getText(), String.valueOf(cbxProvincia.getSelectedIndex()+1), 
								txtMail.getText(), cbxTipoVendedor.getSelectedItem().toString());
						String numero = txtNumber1.getText();
						String insert = String.format("exec registrarVendedor @usuario = '%s', @tipoUser = '%s', @clave = '%s', @cedula = '%s', @nombre = '%s', "
								+ "@apellido = '%s', @provincia = '%s', @email = '%s', @numero = '%s', @tipoVendedor = '%s'", user.getUserName(), user.getTipo(), user.getPass(),
								vendedor.getCedula(), vendedor.getNombre(), vendedor.getApellido(), vendedor.getProvincia(), vendedor.getEmail(), numero, vendedor.getTipoVendedor());
						try
						{
							dbConnection.prepareCall(insert).execute();
							dbConnection.close();

						} catch (SQLException e1) {
							// TODO Auto-generated catch block

							e1.printStackTrace();

						}

						JOptionPane.showMessageDialog(null, "Se ha registrado correctamente", "Notificación", JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}

				}

			}
		});
		btnNewButton.setBounds(265, 546, 89, 23);
		panel.add(btnNewButton);

		JLabel lblClave = new JLabel("Clave:");
		lblClave.setBounds(10, 142, 102, 14);
		panel.add(lblClave);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Dialog", Font.PLAIN, 12));
		passwordField.setBounds(105, 139, 233, 20);
		panel.add(passwordField);

		JLabel lblCedula = new JLabel("C\u00E9dula:");
		lblCedula.setBounds(10, 194, 102, 14);
		panel.add(lblCedula);

		JLabel lblNombre = new JLabel("Nombres:");
		lblNombre.setBounds(10, 246, 102, 14);
		panel.add(lblNombre);

		JLabel lblApellido = new JLabel("Apellidos:");
		lblApellido.setBounds(10, 298, 102, 14);
		panel.add(lblApellido);

		JLabel lblProvincia = new JLabel("Provincia:");
		lblProvincia.setBounds(10, 350, 102, 14);
		panel.add(lblProvincia);

		JLabel lblNumero1 = new JLabel("N\u00FAmero:");
		lblNumero1.setBounds(10, 402, 102, 14);
		panel.add(lblNumero1);

		lblTipoDeVendedor = new JLabel("Tipo de Vendedor:");
		lblTipoDeVendedor.setBounds(10, 503, 102, 14);
		lblTipoDeVendedor.setVisible(false);
		panel.add(lblTipoDeVendedor);

		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(105, 243, 233, 20);
		panel.add(txtNombre);

		txtApellido = new JTextField();
		txtApellido.setColumns(10);
		txtApellido.setBounds(105, 295, 233, 20);
		panel.add(txtApellido);

		cbxTipoVendedor = new JComboBox();
		cbxTipoVendedor.setModel(new DefaultComboBoxModel(new String[] {"Dealer", "Particular"}));
		cbxTipoVendedor.setSelectedIndex(0);
		cbxTipoVendedor.setVisible(false);
		cbxTipoVendedor.setBounds(105, 500, 233, 20);
		panel.add(cbxTipoVendedor);

		cbxProvincia = new JComboBox();
		cbxProvincia.setModel(new DefaultComboBoxModel(new String[] {"Azua", "Bahoruco", "Barahona", "Dajab\u00F3n", "Distrito Nacional", "Duarte", "El\u00EDas Pi\u00F1a", "El Seibo", "Espaillat", "Hato Mayor", "Hermanas Mirabal", "Independencia", "La Altagracia", "La Romana", "La Vega", "Mar\u00EDa Trinidad S\u00E1nchez", "Monse\u00F1or Nouel", "Monte Cristi", "Monte Plata", "Pedernales", "Peravia", "Puerto Plata", "Saman\u00E1", "S\u00E1nchez Ram\u00EDrez", "San Crist\u00F3bal", "San Jos\u00E9 de Ocoa", "San Juan", "San Pedro de Macor\u00EDs", "Santiago", "Santiago Rodr\u00EDguez", "Santo Domingo", "Valverde"}));
		cbxProvincia.setSelectedIndex(0);
		cbxProvincia.setBounds(105, 347, 233, 20);
		panel.add(cbxProvincia);

		lblEmail = new JLabel("Email:");
		lblEmail.setBounds(10, 455, 102, 14);
		panel.add(lblEmail);

		txtMail = new JTextField();
		txtMail.setColumns(10);
		txtMail.setBounds(105, 452, 233, 20);
		panel.add(txtMail);

		JButton btnNewButton_1 = new JButton("Salir");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnNewButton_1.setBounds(166, 546, 89, 23);
		panel.add(btnNewButton_1);

		try {
			txtCedula = new JFormattedTextField(new MaskFormatter("AAA-AAAAAAA-A"));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		txtCedula.setBounds(105, 191, 233, 20);
		panel.add(txtCedula);

		try {
			txtNumber1 = new JFormattedTextField(new MaskFormatter("(###)-###-####"));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		txtNumber1.setBounds(105, 399, 233, 20);
		panel.add(txtNumber1);

	}

	private boolean verificarCampos()
	{
		if(txtApellido.getText().isEmpty() || txtCedula.getText().isEmpty() || txtNombre.getText().isEmpty() ||
				txtNumber1.getText().isEmpty() || txtUsername.getText().isEmpty()
				|| passwordField.getPassword().length <= 0 || cbxTipoCuenta.getSelectedIndex() < 0 || cbxTipoVendedor.getSelectedIndex() < 0)
		{
			return false;
		}else
		{
			return true;
		}

	}
}