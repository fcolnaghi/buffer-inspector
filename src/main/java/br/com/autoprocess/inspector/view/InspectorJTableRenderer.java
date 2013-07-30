package br.com.autoprocess.inspector.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import br.com.autoprocess.inspector.model.ItemTabela;

public class InspectorJTableRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = -8603990626392521531L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		
		// ---------------------------
		// Recupera o renderer
		// ---------------------------
		JLabel renderer = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		// ---------------------------
		// Seta uma fonte monospaced (que facilita a leitura)
		// ---------------------------
		renderer.setFont(new Font("Lucida Console", Font.PLAIN, 12));

		// ---------------------------
		// Seta a cor da fonte para cinza escuro 
		// ---------------------------
		renderer.setForeground(new Color(51, 51, 51));
		
		// ---------------------------
		// Corrige o alinhamento das colunas
		// ---------------------------
		switch (column) {
			case 0:
				renderer.setHorizontalAlignment(SwingConstants.CENTER);
				break;
			case 3:
				renderer.setHorizontalAlignment(SwingConstants.CENTER);
				break;
			case 4:
				renderer.setHorizontalAlignment(SwingConstants.CENTER);
				break;
		}
		
		// ---------------------------
		// Recupera o objeto que contem o conteudo da linha
		// ---------------------------
		ItemTabela item = ((TableModel) table.getModel()).getCampo(row);
		
		// ---------------------------
		// Tooltip com a descricao do item (caso exista)
		// ---------------------------
		if (item.getDescricao() != null && !item.getDescricao().equals("")) {
			renderer.setToolTipText(item.getDescricao());
		}
		
		// ---------------------------
		// Identifica as informacoes de header da pga
		// ---------------------------
		if (item.getNome() != null && item.getNome().toUpperCase().startsWith("HEADER-PGA")) {
			renderer.setBackground(new Color(240, 240, 240));
		}
		
		// ---------------------------
		// Sinaliza que ha um erro quando:
		// 	- Houve um erro no parser da linha
		// 	- O tipo e numerico mas existe caracteres nao numericos
		// 	- e a ultima linha, com o resto do buffer
		// ---------------------------
		if (item.isErro() || !isValid(item)) {
			renderer.setForeground(new Color(241, 63, 83));
			renderer.setBackground(new Color(255, 248, 248));
			
			if (column == 0) {
				renderer.setText("");
				renderer.setIcon(new ImageIcon(getClass().getResource("/icon-error.gif")));			
			}
		}

		// ---------------------------
		// Define as cores padrao ao selecionar
		// ---------------------------
		if (isSelected) {
			renderer.setBackground(table.getSelectionBackground());
			renderer.setForeground(table.getSelectionForeground());
		}
		
		// ---------------------------
		// Retorna o formato
		// ---------------------------
		return renderer;
	}

	/**
	 * Verifica se o item e valido
	 * 
	 * @param item
	 * @return
	 */
	private boolean isValid(ItemTabela item) {
		
		if (item.getTipo() != null) {
			if ((item.getTipo().equals("N") && !item.getValor().matches("\\d+")) || item.getNome().equals("Remaining content")) {
				return false;
			}
		}
		
		return true;
	}
}
