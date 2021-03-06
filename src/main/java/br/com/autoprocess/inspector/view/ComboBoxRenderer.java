package br.com.autoprocess.inspector.view;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

public class ComboBoxRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = 542799980497637385L;
	
	/**
	 * Corrige o espacamento entre itens (lista) dentro de uma combo 
	 */
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		
		label.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 2));
		
		return label;
	}
}