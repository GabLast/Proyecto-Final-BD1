package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import logic.SQLConnection;
import net.proteanit.sql.DbUtils;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListarAnunciosCliente extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static JTable table;
	JButton btnComprar;
	long idAnuncio = -1;
	long idVehiculo = -1;
	Connection dbConnection = null;
	//String precioVehi = null;

	/**
	 * Create the dialog.
	 */
	public ListarAnunciosCliente(Connection z, long idCliente) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		dbConnection = SQLConnection.connect();
		setTitle("Veh\u00EDculos Disponibles");
		setBounds(100, 100, 800, 567);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Listado de Anuncios", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
								idAnuncio = (long) (table.getValueAt(table.getSelectedRow(), 0));
								idVehiculo = (long) (table.getValueAt(table.getSelectedRow(), 1));
								//precioVehi = String.valueOf((table.getValueAt(table.getSelectedRow(), 8)));
								btnComprar.setEnabled(true);
							}
						}
					});
					
					load(dbConnection);
					scrollPane.setViewportView(table);
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
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			{
				btnComprar = new JButton("Comprar");
				btnComprar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						if(idAnuncio == -1 || idVehiculo == -1 /*|| precioVehi == null*/)
						{
							JOptionPane.showMessageDialog(null, "Elija el vehículo a comprar", "Error", JOptionPane.WARNING_MESSAGE, null);
						}
						else
						{
							
							String sp = String.format("exec comprarVehiculo @idCliente = %d, @idVehiculo = %d, @idAnuncio = %d",
									idCliente, idVehiculo, idAnuncio);
							try
							{
								if(dbConnection.isClosed())
									dbConnection = SQLConnection.connect();
								dbConnection.prepareCall(sp).execute();
								
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								
								e1.printStackTrace();
								
							}
							
							JOptionPane.showMessageDialog(null, "Su compra ha sido realizada exitosamente.", "Notificación", 
									JOptionPane.INFORMATION_MESSAGE);
							dispose();
							new ListarAnunciosCliente(dbConnection, idCliente).setVisible(true);
						}
					}
				});
				btnComprar.setActionCommand("OK");
				buttonPane.add(btnComprar);
				btnComprar.setEnabled(false);
				getRootPane().setDefaultButton(btnComprar);
			}
		}
	}
	
	public static void load(Connection dbConnection)
	{
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getTableHeader().setReorderingAllowed(false);
		
		
		String query = String.format("exec ListarVehiculosEnVenta"); //sp
		
		try {
			if(dbConnection.isClosed())
				dbConnection = SQLConnection.connect();
			PreparedStatement st = dbConnection.prepareStatement(query);
			ResultSet rs = null;
			try
			{
				rs = st.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(rs));
			}catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
//			finally {
//				st.close();
				rs.close();
//			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
