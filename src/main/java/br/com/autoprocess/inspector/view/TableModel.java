package br.com.autoprocess.inspector.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.autoprocess.inspector.main.Inspector;
import br.com.autoprocess.inspector.model.ItemTabela;

public class TableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private List<ItemTabela> linhas;
	private String[] colunas = new String[] { "", "Attribute", "Value", "Type", "Length" };

	public TableModel() {
		linhas = new ArrayList<ItemTabela>();
	}

	public TableModel(List<ItemTabela> listaDeCampos) {
		linhas = new ArrayList<ItemTabela>(listaDeCampos);
	}

	public int getColumnCount() {
		return colunas.length;
	}

	public int getRowCount() {
		return linhas.size();
	}

	public String getColumnName(int columnIndex) {
		return colunas[columnIndex];
	}

	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		ItemTabela campo = linhas.get(rowIndex);

		switch (columnIndex) {
			case 0:
				return campo.getSequencia();
			case 1:
				return formataIdentacaoNome(campo.getNome(), campo.getNivel());
			case 2:
				return campo.getValor();
			case 3:
				return campo.getTipo();
			case 4:
				
				if (campo.getTipo() == null || campo.getTipo().equals("")) {
					return "";
				} else {
					return campo.getTamanho();
				}
				
			default:
				throw new IndexOutOfBoundsException("columnIndex out of bounds");
		}
	}

	private String formataIdentacaoNome(String nome, int nivel) {

		String espacos = "";
		
		if (nivel > 0) {
			for (int i = 0; i < nivel * Inspector.QTD_IDENTACOES; i++) {
				espacos += " ";
			}
		}
		
		return espacos + nome;
	}

	public void setValueAt(Object valor, int rowIndex, int columnIndex) {
		
		ItemTabela campo = linhas.get(rowIndex); // Carrega o item da linha que deve ser modificado

		switch (columnIndex) { // Seta o valor do campo respectivo
			case 0:
				campo.setSequencia(valor.toString());
			case 1:
				campo.setNome(valor.toString());
			case 2:
				campo.setValor(valor.toString());
			case 3:
				campo.setTipo(valor.toString());
			case 4:
				campo.setTamanho(Integer.parseInt(valor.toString()));
		}
		
		fireTableCellUpdated(rowIndex, columnIndex);
	}

	public void setValueAt(ItemTabela valor, int rowIndex) {
		ItemTabela campo = linhas.get(rowIndex);
		
		campo.setSequencia(valor.getSequencia());
		campo.setNome(valor.getNome());
		campo.setValor(valor.getValor());
		campo.setTipo(valor.getTipo());
		campo.setTamanho(valor.getTamanho());

		fireTableCellUpdated(rowIndex, 0);
		fireTableCellUpdated(rowIndex, 1);
		fireTableCellUpdated(rowIndex, 2);
		fireTableCellUpdated(rowIndex, 3);
		fireTableCellUpdated(rowIndex, 4);
		
	};

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public ItemTabela getCampo(int indiceLinha) {
		return linhas.get(indiceLinha);
	}

	public void addCampo(ItemTabela m) {
		linhas.add(m);

		int ultimoIndice = getRowCount() - 1;

		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}

	public void removeCampo(int indiceLinha) {
		linhas.remove(indiceLinha);

		fireTableRowsDeleted(indiceLinha, indiceLinha);
	}

	public void addListaDeCampos(List<ItemTabela> campos) {
		// Pega o tamanho antigo da tabela.
		int tamanhoAntigo = getRowCount();

		// Adiciona os registros.
		linhas.addAll(campos);

		fireTableRowsInserted(tamanhoAntigo, getRowCount() - 1);
	}

	public void limpar() {
		linhas.clear();

		fireTableDataChanged();
	}

	public boolean isEmpty() {
		return linhas.isEmpty();
	}

}