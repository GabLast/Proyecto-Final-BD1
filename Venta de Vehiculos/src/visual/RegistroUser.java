package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.awt.event.ActionEvent;

public class RegistroUser extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsername;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 380, 213);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registro de usuario", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JComboBox cbxTipoUser = new JComboBox();
		cbxTipoUser.setBounds(105, 72, 233, 20);
		panel.add(cbxTipoUser);
		
		JLabel lblNewLabel = new JLabel("Nombre de usuario:");
		lblNewLabel.setBounds(10, 34, 102, 14);
		panel.add(lblNewLabel);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(105, 31, 233, 20);
		panel.add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblTipoDeCuenta = new JLabel("Tipo de cuenta:");
		lblTipoDeCuenta.setBounds(10, 75, 102, 14);
		panel.add(lblTipoDeCuenta);
		
		JButton btnNewButton = new JButton("Registrarse");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnNewButton.setBounds(249, 121, 89, 23);
		panel.add(btnNewButton);
	}
}
