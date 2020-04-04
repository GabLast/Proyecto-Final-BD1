package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logic.SQLConnection;
import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

public class MostrarVehículosPorMarca extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	long idMarca = -1;
	JComboBox cbxMarca;
	Connection dbConnection = null;

	/**
	 * Create the dialog.
	 */
	public MostrarVehículosPorMarca(Connection z) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		dbConnection = SQLConnection.connect();
		setTitle("Veh\u00EDculos por Marca");
		setBounds(100, 100, 830, 463);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel bigpanel = new JPanel();
			bigpanel.setBorder(new TitledBorder(null, "Informaci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(bigpanel, BorderLayout.CENTER);
			bigpanel.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("Marca:");
			lblNewLabel.setBounds(10, 18, 46, 14);
			bigpanel.add(lblNewLabel);
			cbxMarca = new JComboBox();
			cbxMarca.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String query = String.format("select * from mostrarVehiculosPorMarca(%d)", cbxMarca.getSelectedIndex()); //sp

					try {
						if(dbConnection.isClosed())
							dbConnection = SQLConnection.connect();
						PreparedStatement st = dbConnection.prepareStatement(query);
						ResultSet rs = null;
						try
						{
							rs = st.executeQuery();
							table.setModel(DbUtils.resultSetToTableModel(rs));
						}catch (SQLException a) {
							a.printStackTrace();
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
			});
			cbxMarca.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>"}));
			cbxMarca.setBounds(66, 15, 161, 20);
			bigpanel.add(cbxMarca);
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
				}catch (SQLException e) {
					e.printStackTrace();
					// TODO: handle exception
				}
//				finally {
//					st.close();
//					rs.close();
//				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Listado de Veh\u00EDculos Registrados", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel.setBounds(10, 43, 784, 327);
			bigpanel.add(panel);
			panel.setLayout(new BorderLayout(0, 0));
			
			JScrollPane scrollPane = new JScrollPane();
			panel.add(scrollPane, BorderLayout.CENTER);
			
			
			table = new JTable();				
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			table.getTableHeader().setReorderingAllowed(false);
			scrollPane.setViewportView(table);

			String query = String.format("select * from mostrarVehiculosPorMarca(%d)", idMarca); //sp

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
				}finally {
					st.close();
					rs.close();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
		}
	}
}
