package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

public class ConsultaPublicacion extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	
	public ConsultaPublicacion(Connection dbConnection) {
		 try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (Throwable e) {
				e.printStackTrace();
			}
			setTitle("Anuncios");
			setBounds(100, 100, 800, 567);
			setLocationRelativeTo(null);
			getContentPane().setLayout(new BorderLayout());
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			getContentPane().add(contentPanel, BorderLayout.CENTER);
			contentPanel.setLayout(new BorderLayout(0, 0));
			{
				JPanel panel = new JPanel();
				panel.setBorder(new TitledBorder(null, "Lista de Publicaciones", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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


						String query = String.format("select * from listarPublicaciones()");

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
					JButton okButton = new JButton("OK");
					okButton.setActionCommand("OK");
					buttonPane.add(okButton);
					getRootPane().setDefaultButton(okButton);
				}
				{
					JButton cancelButton = new JButton("Cancel");
					cancelButton.setActionCommand("Cancel");
					buttonPane.add(cancelButton);
				}
			}
		}

}
