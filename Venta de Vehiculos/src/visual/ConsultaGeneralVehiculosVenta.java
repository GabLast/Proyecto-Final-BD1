package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import logic.SQLConnection;
import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ConsultaGeneralVehiculosVenta extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;

	public ConsultaGeneralVehiculosVenta(Connection dbConnection) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		setTitle("Veh\u00EDculos Disponibles");
		setBounds(100, 100, 800, 567);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "Veh\u00EDculos en Venta", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
					
					String query = "exec ListarVehiculosEnVenta";
					
					try {
						if(dbConnection.isClosed())
							dbConnection = SQLConnection.connect();
						CallableStatement st = dbConnection.prepareCall(query);
						ResultSet rs = null;
						try
						{
							rs = st.executeQuery();
							table.setModel(DbUtils.resultSetToTableModel(rs));
						}catch (SQLException e) {
							// TODO: handle exception
							e.printStackTrace();
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
		}
	}

}
