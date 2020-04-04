package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

public class ListarAnuncios extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
//select idAnuncio as 'Código', descripcion as 'Descripción', fechaInicio as 'Fecha de Inicio', fechaFin as 'Fecha de Baja', costo as 'Costo', idVehiculo as 'Código del Vehículo', preciovehiculo as 'Precio del Vehículo' from Anuncio where idVendedor = %d
	/**
	 * Launch the application.
	 */

	/**
	 * Create the dialog.
	 */
	public ListarAnuncios(Connection dbConnection, int vendedor) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
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
					scrollPane.setViewportView(table);
					
					String query = String.format("select idAnuncio as 'Código del Anuncio', descripcion as 'Descripción', fechaInicio as 'Fecha de Inicio', fechaFin as 'Fecha de Baja', costo as 'Costo', "
							+ "idVehiculo as 'Código del Vehículo', preciovehiculo as 'Precio del Vehículo' from Anuncio where idVendedor = %d", vendedor);
					
					try {
						PreparedStatement st = dbConnection.prepareStatement(query);
						ResultSet rs = null;
						try
						{
							rs = st.executeQuery();
							table.setModel(DbUtils.resultSetToTableModel(rs));
							
						}catch (SQLException e) {
							// TODO: handle exception
						}finally
						{
							st.close();
							rs.close();
						}
						
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
		}
	}

}
