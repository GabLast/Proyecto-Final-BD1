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

import logic.SQLConnection;
import net.proteanit.sql.DbUtils;

public class ListarVehiculosComprados extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private static JTable table;
	/**
	 * Create the dialog.
	 */
	public ListarVehiculosComprados(Connection dbConnection, long idCliente) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		setTitle("Veh\u00EDculos Comprados");
		setBounds(100, 100, 800, 567);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Listado de Vehículos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JScrollPane scrollPane = new JScrollPane();
				panel.add(scrollPane, BorderLayout.CENTER);
				{
					table = new JTable();				
					table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

					load(dbConnection, idCliente);
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
			
		}
	}
	
	public static void load(Connection dbConnection, long id)
	{
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getTableHeader().setReorderingAllowed(false);
		
		
		String query = String.format("select * from comprasCliente(%d)", id); //sp
		
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
				st.close();
				rs.close();
//			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
