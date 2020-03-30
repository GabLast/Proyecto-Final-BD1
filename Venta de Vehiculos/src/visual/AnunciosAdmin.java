package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

public class AnunciosAdmin extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	JButton btnAutorizar;
	int idAnuncio = -1;
	int idVehiculo = -1;
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
			AnunciosAdmin dialog = new AnunciosAdmin(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AnunciosAdmin(Connection dbConnection) {
		setTitle("Mis Anuncios");
		setBounds(100, 100, 1100, 610);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new TitledBorder(null, "Lista de Veh\u00EDculos en Venta", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JScrollPane scrollPane = new JScrollPane();
				panel.add(scrollPane, BorderLayout.CENTER);
				{
					table = new JTable();				
					table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
					table.getTableHeader().setReorderingAllowed(false);
					table.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent arg0) {
							if(table.getSelectedRow()>=0) {
								idAnuncio = (table.getSelectedRow() + 1);
								btnAutorizar.setEnabled(true);
							}
						}
					});
					scrollPane.setViewportView(table);
					
					String query ="select idAnuncio as 'Código del Anuncio', descripcion as 'Descripción', fechaInicio as 'Fecha de Inicio', fechaFin as 'Fecha de Baja', costo as 'Costo', "
							+ "idVehiculo as 'Código del Vehículo', preciovehiculo as 'Precio del Vehículo', autorizado as 'Autorización' from Anuncio";
					
					try {
						PreparedStatement st = dbConnection.prepareStatement(query);
						ResultSet rs = st.executeQuery();
						table.setModel(DbUtils.resultSetToTableModel(rs));
						rs.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			{
				btnAutorizar = new JButton("Autorizar");
				btnAutorizar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						if(idAnuncio == -1)
						{
							JOptionPane.showMessageDialog(null, "Elija anuncio a autorizar", "Error", JOptionPane.WARNING_MESSAGE, null);
						}
						else
						{
							String query = String.format("select idvehiculo from anuncio where idAnuncio = '%d'", idAnuncio);
							
							try {
								Statement st;
								st = dbConnection.createStatement();
								ResultSet rs = st.executeQuery(query);
								
								while(rs.next() && idVehiculo == -1)
								{
									idVehiculo = Integer.valueOf(rs.getString(1));
								}
								
								rs.close();
							} catch (SQLException e5) {
								// TODO Auto-generated catch block
								e5.printStackTrace();
							}
							
							if(idVehiculo == -1)
							{
								JOptionPane.showMessageDialog(null, "El vehículo no existe", "Error", JOptionPane.WARNING_MESSAGE, null);
							}
							else
							{
								String sp = String.format("exec autorizarAnuncio @idAnuncio = '%d', @idVehiculo = '%d'", idAnuncio, idVehiculo);
								
								try
								{
									dbConnection.prepareCall(sp).execute();
									
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									
									e1.printStackTrace();
									
								}
								
								JOptionPane.showMessageDialog(null, "El anuncio ha sido autorizado correctamente", "Notificación", JOptionPane.INFORMATION_MESSAGE);
							}		
						}
					}
				});
				btnAutorizar.setActionCommand("OK");
				buttonPane.add(btnAutorizar);
			}
		}
	}

}
