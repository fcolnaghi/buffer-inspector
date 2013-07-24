package br.com.autoprocess.inspector.view.components;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import br.com.autoprocess.inspector.view.renderers.InspectorJTableRenderer;

public class InspectorJTable extends JTable {

	private static final long serialVersionUID = 4111774189968564782L;
	
	public InspectorJTable(TableModel model) {
		super(model);
	}
	
	public TableCellRenderer getCellRenderer(int row, int column) {
		return new InspectorJTableRenderer();
	}
	
}
