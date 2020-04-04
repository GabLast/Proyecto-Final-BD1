package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.UIManager;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.Calendar;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import com.toedter.calendar.JCalendar;

import logic.Anuncio;
import logic.SQLConnection;
import net.proteanit.sql.DbUtils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PublicarAnuncio extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtCostoAnuncio;
	private JTextField txtPrecioVehiculo;
	private JTextField txtRd;
	JButton okButton;
	JCalendar fechaInicio;
	JCalendar fechaFinal;
	private JTextField textField;
	JTextArea txtDescripcion;
	Connection dbConnection = null;
	/**
	 * Launch the application.


	/**
	 * Create the dialog.
	 */
	public PublicarAnuncio(Connection z, int idVendedor, long idVehiculo) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		dbConnection = SQLConnection.connect();
		setTitle("Publicaci\u00F3n de anuncios");
		setBounds(100, 100, 746, 476);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Datos del Anuncio", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			
			JLabel lblDes = new JLabel("Descripci\u00F3n:");
			lblDes.setBounds(10, 301, 73, 14);
			panel.add(lblDes);
			
			JLabel lblFechaDePublicacin = new JLabel("Fecha de Inicio:");
			lblFechaDePublicacin.setBounds(10, 33, 111, 14);
			panel.add(lblFechaDePublicacin);
			
			JLabel lblFechaDeCorto = new JLabel("Fecha de Corto:");
			lblFechaDeCorto.setBounds(380, 33, 111, 14);
			panel.add(lblFechaDeCorto);
			
			JLabel lblCosto = new JLabel("Costo del Anuncio:");
			lblCosto.setBounds(10, 207, 111, 14);
			panel.add(lblCosto);
			
			JLabel lblrdda = new JLabel("*RD$1200.00/d\u00EDa");
			lblrdda.setForeground(Color.RED);
			lblrdda.setBounds(353, 207, 111, 14);
			panel.add(lblrdda);
			
			JLabel lblCostoDelVehculo = new JLabel("Costo del Veh\u00EDculo");
			lblCostoDelVehculo.setBounds(10, 262, 111, 14);
			panel.add(lblCostoDelVehculo);
			
			txtCostoAnuncio = new JTextField();
			txtCostoAnuncio.setEditable(false);
			txtCostoAnuncio.setBounds(172, 204, 171, 20);
			panel.add(txtCostoAnuncio);
			txtCostoAnuncio.setColumns(10);
			
			txtPrecioVehiculo = new JTextField();
			txtPrecioVehiculo.setColumns(10);
			txtPrecioVehiculo.setBounds(172, 259, 171, 20);
			panel.add(txtPrecioVehiculo);
			
			txtRd = new JTextField();
			txtRd.setText("RD$");
			txtRd.setEditable(false);
			txtRd.setColumns(10);
			txtRd.setBounds(146, 259, 197, 20);
			panel.add(txtRd);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
			panel_1.setBounds(146, 301, 560, 74);
			panel.add(panel_1);
			panel_1.setLayout(new BorderLayout(0, 0));
			
			txtDescripcion = new JTextArea();
			txtDescripcion.setLineWrap(true);
			txtDescripcion.setWrapStyleWord(true);
			panel_1.add(txtDescripcion, BorderLayout.CENTER);
			
			fechaInicio = new JCalendar();
			fechaInicio.setBounds(141, 33, 205, 153);
			panel.add(fechaInicio);
			
			fechaFinal = new JCalendar();
			fechaFinal.setBounds(501, 33, 205, 153);
			panel.add(fechaFinal);
			
			textField = new JTextField();
			textField.setText("RD$");
			textField.setEditable(false);
			textField.setColumns(10);
			textField.setBounds(146, 204, 197, 20);
			panel.add(textField);
			
			JButton btnNewButton = new JButton("Calcular");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					calcularCosto(dbConnection);
				}
			});
			btnNewButton.setBounds(454, 203, 89, 23);
			panel.add(btnNewButton);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Salir");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			{
				okButton = new JButton("Publicar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(!verificarCampos())
						{
							JOptionPane.showMessageDialog(null, "Llenar los campos y calcular el costo del anuncio", null, JOptionPane.WARNING_MESSAGE, null);
						}
						else
						{
												
							SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yyyy"); 
							
							String fin = formato.format(fechaFinal.getDate());
							String inicio = formato.format(fechaInicio.getDate());
							
							Anuncio anuncio = new Anuncio(txtDescripcion.getText(), 0, 0, null, null);
							String sp = String.format("exec crearAnuncio @descripcion = '%s', @fechaInicio = '%s', @fechaFin = '%s', @idVendedor = '%d', @idVehiculo = '%d', @precioVehiculo = '%s'",
									anuncio.getDescripcion(), inicio, fin, idVendedor, idVehiculo, txtPrecioVehiculo.getText().toString());
							
							try
							{
								if(dbConnection.isClosed())
									dbConnection = SQLConnection.connect();
								dbConnection.prepareCall(sp).execute();
								
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								
								e1.printStackTrace();
								
							}
							
							JOptionPane.showMessageDialog(null, "Su anuncio ha sido creado. Este será aprobado en unos minutos.", "Notificación", JOptionPane.INFORMATION_MESSAGE);
							dispose();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	
	private void calcularCosto(Connection dbConnection)
	{
		
		Date fin = fechaFinal.getDate();
		Date inicio = fechaInicio.getDate();
		float valor = 0;
		
		java.sql.Date sqlDate1 = new java.sql.Date(inicio.getTime());
		java.sql.Date sqlDate2 = new java.sql.Date(fin.getTime());
		
		
		try {
			if(dbConnection.isClosed())
				dbConnection = SQLConnection.connect();
			CallableStatement cstmt = dbConnection.prepareCall("{? = CALL [dbo].[calcularCosto](?, ?)}");
			cstmt.registerOutParameter(1, Types.FLOAT);
			cstmt.setDate(2, sqlDate1);
			cstmt.setDate(3, sqlDate2);
			cstmt.execute();
			valor = cstmt.getFloat(1);
		}catch (SQLException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		//int days = (int) (fin.getTime() - inicio.getTime()) / (1000 * 60 * 60 * 24);
		txtCostoAnuncio.setText(String.valueOf(valor));
	}
	
	private boolean verificarCampos()
	{
		if(txtCostoAnuncio.getText().isEmpty() || txtPrecioVehiculo.getText().isEmpty()
				|| txtDescripcion.getText().isEmpty())
		{
			return false;
		}
		else
			return true;
	}
}
