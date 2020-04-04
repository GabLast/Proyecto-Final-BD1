package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class ListarVehiculos extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	
	JButton btnAnuncio;
	long indiceVehiculo = -1;

	/**
	 * Create the dialog.
	 */
	public ListarVehiculos(Connection dbConnection, int vendedor, String nombre) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		setTitle("Listado de vehiculos de: " + nombre);
		setBounds(100, 100, 940, 551);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Lista de veh\u00EDculos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JScrollPane scrollPane = new JScrollPane();
				panel.add(scrollPane, BorderLayout.CENTER);
				{
					table = new JTable();				
					table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					table.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent arg0) {
							if(table.getSelectedRow()>=0) {
								indiceVehiculo = (long) (table.getValueAt(table.getSelectedRow(), 0));
								btnAnuncio.setEnabled(true);
							}
						}
					});
					
					table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
					table.getTableHeader().setReorderingAllowed(false);
					scrollPane.setViewportView(table);
					
					String query = String.format("select * from ListarVehiculosVendedor(%d)", vendedor); //esto es una funcion
					
					try {
						PreparedStatement st = dbConnection.prepareStatement(query);
						ResultSet rs = null;
						try
						{
							rs = st.executeQuery();
							table.setModel(DbUtils.resultSetToTableModel(rs));
						}catch (SQLException e) {
							// TODO: handle exception
						}finally {
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
				btnAnuncio = new JButton("Publicar Anuncio");
				btnAnuncio.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(indiceVehiculo != -1)
						{
							new PublicarAnuncio(dbConnection, vendedor, indiceVehiculo).setVisible(true);
						}
						else
							JOptionPane.showMessageDialog(null, "Elija el vehículo a publicar", "Error", JOptionPane.WARNING_MESSAGE, null);
						
					}
				});
				btnAnuncio.setActionCommand("OK");
				btnAnuncio.setEnabled(false);
				buttonPane.add(btnAnuncio);
				getRootPane().setDefaultButton(btnAnuncio);
			}
		}
	}

}
