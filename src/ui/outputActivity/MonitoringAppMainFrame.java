package ui.outputActivity;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import fr.superesi.superapp.Order;
import fr.superesi.superapp.monitoring.OrdersTableModel;


@SuppressWarnings("serial")
public class MonitoringAppMainFrame extends JFrame implements ActionListener {
	
	private JTable table;
	private OrdersTableModel tableModel;
	private JPanel mainPanel, buttonPanel;
	
	private JButton razButton = new JButton("reset table");

	ArrayList<Order> data = new ArrayList<>();
			
	public MonitoringAppMainFrame() {
		fillInitTableData(); // COMMENT THIS LINE TO REMOVE STUB DATA
		tableModel = new OrdersTableModel(data);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		
		mainPanel = new JPanel();
		mainPanel.add(scrollPane);
		add(mainPanel, BorderLayout.PAGE_START);
		
		buttonPanel = new JPanel();
		buttonPanel.add(razButton);
		add(buttonPanel, BorderLayout.PAGE_END);
		
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
			
	}
	
	/**
	 * Stub method filling the table with some data sample.
	 */
	private void fillInitTableData() {
		data.add(new Order("coffee",2,1.2f));
		data.add(new Order("tea",3,1.6f));
		data.add(new Order("tea",1,1.6f));
		data.add(new Order("sparkling water",4,1.1f));
	}
	
	/**
	 * Just the main of the monitoring app...
	 * 
	 * @param args
	 *            - unused main arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				new MonitoringAppMainFrame();
				
				
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO to be continued
		
	}

}
