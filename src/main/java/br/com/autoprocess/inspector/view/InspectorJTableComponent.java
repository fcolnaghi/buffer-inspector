package br.com.autoprocess.inspector.view;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class InspectorJTableComponent extends JTable {

	private static final long serialVersionUID = 4111774189968564782L;
	
	public InspectorJTableComponent(TableModel model) {
		super(model);
	}
	
	public TableCellRenderer getCellRenderer(int row, int column) {
		return new InspectorJTableRenderer();
	}
	
}
