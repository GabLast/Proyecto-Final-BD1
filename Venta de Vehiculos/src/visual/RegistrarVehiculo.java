package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import logic.Vehiculo;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class RegistrarVehiculo extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtAnio;
	JComboBox cbxModelo;
	JComboBox cbxTipoVehiculo;
	JComboBox cbxMarca;
	JTextArea txtDescripcion;
	JComboBox cbxEstado;
	int idModelo = -1;

	/**
	 * Create the dialog.
	 */
	public RegistrarVehiculo(Connection dbConnection, int idVendedor) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		setTitle("Registrando un Veh\u00EDculo");
		setBounds(100, 100, 445, 446);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Datos del Veh\u00EDculo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			{
				JLabel lblNewLabel = new JLabel("Estado:");
				lblNewLabel.setBounds(10, 27, 54, 14);
				panel.add(lblNewLabel);
			}
			
			JLabel lblAo = new JLabel("A\u00F1o:");
			lblAo.setBounds(10, 68, 54, 14);
			panel.add(lblAo);
			
			JLabel lblMarca = new JLabel("Marca:");
			lblMarca.setBounds(10, 109, 54, 14);
			panel.add(lblMarca);
			
			JLabel lblTipoDeVehculo = new JLabel("Tipo de veh\u00EDculo:");
			lblTipoDeVehculo.setBounds(10, 191, 98, 14);
			panel.add(lblTipoDeVehculo);
			
			cbxTipoVehiculo = new JComboBox();
			cbxTipoVehiculo.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>"}));
			cbxTipoVehiculo.setBounds(118, 188, 291, 20);
			panel.add(cbxTipoVehiculo);
			int i = 1;
			String cons = "select descripcion from TipoVehiculo";
			
			try {
				//Statement st;
				//st = dbConnection.createStatement();
				//ResultSet rs = st.executeQuery(cons);
				PreparedStatement st = dbConnection.prepareStatement(cons);
				ResultSet rs = null;
				try
				{
					rs = st.executeQuery();
					while(rs.next())
					{
						cbxTipoVehiculo.insertItemAt(rs.getString(1), i);
						i++;
					}
				}catch (SQLException e) {
					// TODO: handle exception
				}finally {
					st.close();
					rs.close();
				}
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			cbxMarca = new JComboBox();
			cbxMarca.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int k = 1;
					String Query1 = String.format("select mo.idModelo, mo.nombre, ma.nombre from Modelo mo join Marca ma on mo.idMarca = ma.idMarca where ma.idMarca = %d", cbxMarca.getSelectedIndex());
					
					try {
						cbxModelo.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>"}));
						Statement st;
						st = dbConnection.createStatement();
						ResultSet rs = null;
						
						try
						{
							rs = st.executeQuery(Query1);
							while(rs.next())
							{
								cbxModelo.insertItemAt(rs.getString(2), k);
								k++;
							}
						}catch (SQLException a) {
							// TODO: handle exception
						}finally {
							st.close();
							rs.close();
						}					
						
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			});
			cbxMarca.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>"}));
			cbxMarca.setBounds(118, 106, 291, 20);
			panel.add(cbxMarca);
			int j = 1;
			String Query = "select nombre from Marca";
			
			try {
				Statement st;
				st = dbConnection.createStatement();
				ResultSet rs = null;
				try
				{
					rs = st.executeQuery(Query);
					while(rs.next())
					{
						cbxMarca.insertItemAt(rs.getString(1), j);
						j++;
					}
				}catch (Exception e) {
					// TODO: handle exception
				}finally {
					st.close();
					rs.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			txtAnio = new JTextField();
			txtAnio.setBounds(118, 65, 291, 20);
			panel.add(txtAnio);
			txtAnio.setColumns(10);
			
			cbxEstado = new JComboBox();
			cbxEstado.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>", "Nuevo", "Usado"}));
			cbxEstado.setBounds(118, 24, 291, 20);
			panel.add(cbxEstado);
			
			JLabel lblDescripcin = new JLabel("Descripci\u00F3n:");
			lblDescripcin.setBounds(10, 232, 98, 14);
			panel.add(lblDescripcin);
			
			JPanel panel_1 = new JPanel();
			panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
			panel_1.setBounds(118, 232, 291, 114);
			panel.add(panel_1);
			panel_1.setLayout(new BorderLayout(0, 0));
			
			txtDescripcion = new JTextArea();
			txtDescripcion.setWrapStyleWord(true);
			txtDescripcion.setLineWrap(true);
			panel_1.add(txtDescripcion, BorderLayout.CENTER);
			
			JLabel lblModelo = new JLabel("Modelo:");
			lblModelo.setBounds(10, 150, 54, 14);
			panel.add(lblModelo);
			
			cbxModelo = new JComboBox();
			cbxModelo.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>"}));
			cbxModelo.setBounds(118, 147, 291, 20);
			panel.add(cbxModelo);
			
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			{
				JButton okButton = new JButton("Registrar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(!verificarCampos())
						{
							JOptionPane.showMessageDialog(null, "Llene todos los campos", "Error", JOptionPane.WARNING_MESSAGE, null);
						}
						else
						{
							String buscandoIdMod = String.format("select mo.idModelo, mo.nombre, ma.nombre from Modelo mo join Marca ma on mo.idMarca = ma.idMarca where mo.nombre = '%s'",
									cbxModelo.getSelectedItem().toString());
							try {
								Statement st;
								st = dbConnection.createStatement();
								ResultSet rs = st.executeQuery(buscandoIdMod);
								
								while(rs.next())
								{
									idModelo = Integer.valueOf(rs.getString(1));
								}
								rs.close();
							} catch (SQLException e5) {
								// TODO Auto-generated catch block
								e5.printStackTrace();
							}
							
							
							
							if(idModelo != -1 && idVendedor != -1)
							{
								Vehiculo vehiculo = new Vehiculo(cbxEstado.getSelectedItem().toString(), Integer.valueOf(txtAnio.getText()), 
										cbxTipoVehiculo.getSelectedIndex(), cbxMarca.getSelectedIndex(), idModelo, txtDescripcion.getText());
								
								String insert = String.format("exec registrarVehiculo @estado = '%s', @anio = '%d', @idMarca = '%d', @idModelo = '%d', @idTipoVehiculo = '%d', "
										+ "@descripcion = '%s', @idVendedor = '%d'", vehiculo.getEstado(), vehiculo.getAnio(), vehiculo.getIdMarca(), vehiculo.getIdModelo(),
										vehiculo.getIdTipoVehiculo(), vehiculo.getDescripcion(), idVendedor);
								
								try {
									dbConnection.prepareCall(insert).execute();
									
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
								JOptionPane.showMessageDialog(null, "Se ha registrado correctamente", "Notificación", JOptionPane.INFORMATION_MESSAGE);
								dispose();
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Hubo un error obteniendo el modelo", "Error", JOptionPane.WARNING_MESSAGE, null);
								
								if(idVendedor == -1)
								{
									JOptionPane.showMessageDialog(null, "Hubo un error obteniendo al vendedor", "Error", JOptionPane.WARNING_MESSAGE, null);
								}
							}
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	
	private boolean verificarCampos()
	{
		if(cbxMarca.getSelectedIndex() < 1 || cbxModelo.getSelectedIndex() < 1 || cbxTipoVehiculo.getSelectedIndex() < 1
				|| txtAnio.getText().isEmpty() || txtDescripcion.getText().isEmpty() || cbxEstado.getSelectedIndex() < 1)
		{
			return false;
		}
		else
			return true;
	}
}
