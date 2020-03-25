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

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

public class MainLogIn extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField passwordField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
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
		setResizable(false);
		setTitle("Iniciando sesi\u00F3n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 502, 312);
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
		btnLogIn.setBounds(176, 202, 141, 31);
		panel.add(btnLogIn);
		
		txtUsuario = new JTextField();
		txtUsuario.setFont(new Font("Roboto", Font.PLAIN, 12));
		txtUsuario.setBounds(71, 90, 369, 29);
		panel.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Roboto", Font.PLAIN, 12));
//		passwordField.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				if(Empresa.getInstance().confirmLogin(txtUsuario.getText(),String.valueOf(passwordField.getPassword())))
//				{
//					Principal frame = new Principal();
//					dispose();
//					frame.setVisible(true);
//				}
//				else
//				{
//					JOptionPane.showMessageDialog(null, "Usuario y/o clave incorrecta.", "Error", JOptionPane.INFORMATION_MESSAGE);
//				}
//			}
//		});
		passwordField.setBounds(71, 147, 369, 29);
		panel.add(passwordField);
		
		JLabel lblNewLabel = new JLabel("Olvido la contrase\u00F1a ? Click");
		lblNewLabel.setFont(new Font("Roboto", Font.PLAIN, 12));
		
		lblNewLabel.setBounds(159, 237, 158, 14);
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
		lblAqui.setBounds(313, 237, 46, 14);
		panel.add(lblAqui);
		
		JLabel lblPlanificadoraDeEventos = new JLabel("Venta de vehículos");
		lblPlanificadoraDeEventos.setFont(new Font("Roboto", Font.PLAIN, 16));
		lblPlanificadoraDeEventos.setBounds(176, 35, 164, 14);
		panel.add(lblPlanificadoraDeEventos);
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
