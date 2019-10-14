package fr.superesi.superapp.monitoring;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import fr.superesi.superapp.Order;

@SuppressWarnings("serial")
public class OrdersTableModel extends AbstractTableModel {

	private String[] colNnames= {"Product","Unit price (€)","Quantity","Total Price (€)"};
	private ArrayList<Order> data;

	public OrdersTableModel(ArrayList<Order> data) {
		// storing data (i.e. list of Orders) in local variable
		this.data = data;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {

		case 0:
			return data.get(rowIndex).getProduct();
		case 1:
			return data.get(rowIndex).getUnitPrice();
		case 2:
			return data.get(rowIndex).getQuantity();
		case 3 : return (data.get(rowIndex).getUnitPrice()*
						 data.get(rowIndex).getQuantity());
		}
		return null;
	}
	@Override
	public String getColumnName(int col) {
        return colNnames[col];
    }

	public void addOrder(Order ami) {
		data.add(ami);

		fireTableRowsInserted(data.size() -1, data.size() -1);
	}

	/**
	 * @brief remove  element the rowIndex position
	 * @param rowIndex the position of the element
	 */
	public void removeOrder(int rowIndex){

		data.remove(rowIndex);

		fireTableRowsDeleted(rowIndex, rowIndex);
	}
}
