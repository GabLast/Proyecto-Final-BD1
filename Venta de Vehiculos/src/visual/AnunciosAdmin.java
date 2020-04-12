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
import javax.swing.table.DefaultTableModel;

import logic.SQLConnection;
import net.proteanit.sql.DbUtils;

public class AnunciosAdmin extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static JTable table;
	JButton btnAutorizar;
	JButton btnBorrar;
	long idAnuncio = -1;
	Connection dbConnection = null;

	/**
	 * Launch the application.
	 */
	/**
	 * Create the dialog.
	 */
	public AnunciosAdmin() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		dbConnection = SQLConnection.connect();
		
		setTitle("Anuncios Disponibles");
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
								idAnuncio = (long) (table.getValueAt(table.getSelectedRow(), 0));
								btnAutorizar.setEnabled(true);
								btnBorrar.setEnabled(true);
							}
						}
					});
					scrollPane.setViewportView(table);
					
					load(dbConnection);
					
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Salir");
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

							String sp = String.format("exec autorizarAnuncio @idAnuncio = %d", idAnuncio);

							try
							{
								if(dbConnection.isClosed())
									dbConnection = SQLConnection.connect();
								
								dbConnection.prepareCall(sp).execute();

							} catch (SQLException e1) {
								// TODO Auto-generated catch block

								e1.printStackTrace();

							}
							dispose();
							new AnunciosAdmin().setVisible(true);
							JOptionPane.showMessageDialog(null, "El anuncio ha sido autorizado correctamente", "Notificación", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				});
				{
					btnBorrar = new JButton("Borrar");
					btnBorrar.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							
							if(idAnuncio == -1)
							{
								JOptionPane.showMessageDialog(null, "Elija anuncio a borrar", "Error", JOptionPane.WARNING_MESSAGE, null);
							}
							else
							{
								String sp = String.format("exec eliminarAnuncio @idAnuncio = %d", idAnuncio);

								try
								{
									if(dbConnection.isClosed())
										dbConnection = SQLConnection.connect();
									dbConnection.prepareCall(sp).execute();

								} catch (SQLException e1) {
									// TODO Auto-generated catch block

									e1.printStackTrace();

								}
								dispose();
								new AnunciosAdmin().setVisible(true);
								JOptionPane.showMessageDialog(null, "El anuncio ha sido eliminado correctamente", "Notificación", JOptionPane.INFORMATION_MESSAGE);
							}
						}
					});
					btnBorrar.setEnabled(false);
					btnBorrar.setActionCommand("OK");
					buttonPane.add(btnBorrar);
				}
				btnAutorizar.setActionCommand("OK");
				btnAutorizar.setEnabled(false);
				buttonPane.add(btnAutorizar);
			}
		}
	}
	
	public static void load(Connection dbConnection)
	{
		String query ="select idAnuncio as 'Código del Anuncio', descripcion as 'Descripción', fechaInicio as 'Fecha de Inicio', fechaFin as 'Fecha de Baja', costo as 'Costo', "
				+ "idVehiculo as 'Código del Vehículo', preciovehiculo as 'Precio del Vehículo', autorizado as 'Autorización' from Anuncio where comprado = 0";
		//table.setModel(new DefaultTableModel());
		
		try {
			if(dbConnection.isClosed())
				dbConnection = SQLConnection.connect();
			PreparedStatement st = dbConnection.prepareStatement(query);
			ResultSet rs = null;
			try
			{
				rs = st.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(rs));
			}catch (Exception e) {
				// TODO: handle exception
			}finally
			{
				dbConnection.close();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
