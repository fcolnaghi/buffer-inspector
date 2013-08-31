package br.com.autoprocess.inspector.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.undo.UndoManager;

import br.com.autoprocess.inspector.main.Inspector;
import br.com.autoprocess.inspector.model.ItemCombo;
import br.com.autoprocess.inspector.model.ModInspector;

public class FormMain extends JFrame {

	private static final long serialVersionUID = -2529757428607548663L;
	
	private ModInspector modInspector;

	// ---------------------------
	// Carrega todas as janelas da aplicacao
	// ---------------------------
	FormAbout about = new FormAbout();

	// ---------------------------
	// Cria componentes da tela principal
	// ---------------------------
	private JComboBox<ItemCombo> comboDirectories;
	private JComboBox<ItemCombo> comboInterfaces;
	
	private InspectorTextAreaComponent buffer;
	private JTable table;
	
	/**
	 * Construtor da classe
	 */
	public FormMain() {
		
		// ---------------------------
		// Inicializa a model
		// ---------------------------
		modInspector = new ModInspector();
		
		// ---------------------------
		// Propriedades
		// ---------------------------
		setTitle(Inspector.APPLICATION_NAME);
		setSize(1024, 600); // tamanho da tela
		setMinimumSize(new Dimension(800, 600)); // tamanho minimo
		setLocationRelativeTo(null); // centralizado na tela
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setExtendedState(JFrame.MAXIMIZED_BOTH); // inicia o programa maximizado
		
		// ---------------------------
		// icone
		// ---------------------------
		ImageIcon icon = new ImageIcon(getClass().getResource("/simbolo-256x256-transparent.png"));
		setIconImage(icon.getImage());
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		// ---------------------------
		// Menu
		// ---------------------------
		loadMenu();
		
		// ---------------------------
		// Painel superior
		// ---------------------------
		createTopPanel();
		
		// ---------------------------
		// Painel central
		// ---------------------------
		createCentralPanel();
		
		// ---------------------------
		// Populates combobox
		// ---------------------------
		loadDirectories();

	}

	/**
	 * Metodo responsavel por criar o menu
	 */
	public void loadMenu() {
		
		JMenuBar menubar = new JMenuBar();

		// ---------------------------
		// File
		// ---------------------------
		JMenu menuFile = new JMenu("File");
		menuFile.setMnemonic(KeyEvent.VK_F);
		menubar.add(menuFile);
		
		// ---------------------------
		// File - Export
		// ---------------------------
		JMenuItem submenuExport = new JMenuItem("Export...");
		submenuExport.setMnemonic(KeyEvent.VK_O);
		
		submenuExport.setIcon(new ImageIcon(getClass().getResource("/export.gif")));
		
		submenuExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				exportData();
			}
		});
				
		menuFile.add(submenuExport);
				
		menuFile.addSeparator();
		
		// ---------------------------
		// File - Sair
		// ---------------------------
		JMenuItem submenuExit = new JMenuItem("Exit");
		submenuExit.setMnemonic(KeyEvent.VK_X);
		
		submenuExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		
		menuFile.add(submenuExit);
		
		// ---------------------------
		// Help
		// ---------------------------
		JMenu menuHelp = new JMenu("Help");
		menuHelp.setMnemonic(KeyEvent.VK_H);
		menubar.add(menuHelp);

		// ---------------------------
		// Help - About
		// ---------------------------
		JMenuItem submenuAbout = new JMenuItem("About "+ Inspector.APPLICATION_NAME);
		submenuAbout.setMnemonic(KeyEvent.VK_A);

		submenuAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				about.setVisible(true);
			}
		});
		
		menuHelp.add(submenuAbout);
		// ---------------------------
		// Seta o menu no form
		// ---------------------------
		setJMenuBar(menubar);
	
		
	}
	
	/**
	 * Metodo responsavel por criar o painel superior
	 */
	public void createTopPanel() {
		
		// ---------------------------
		// Cria o painel superior
		// ---------------------------
		JPanel painel = new JPanel();
		painel.setLayout(new BorderLayout());
		getContentPane().add(painel, BorderLayout.NORTH);
		
		// ---------------------------
		// Cria os paineis
		// ---------------------------
		JPanel painelEsquerdo = new JPanel();
		painel.add(painelEsquerdo, BorderLayout.WEST);
		
		JPanel painelDireito = new JPanel();
		painel.add(painelDireito, BorderLayout.EAST);
		
		// ---------------------------
		// Cria os combos com os layout de transacao
		// ---------------------------
		comboDirectories = new JComboBox<ItemCombo>();
		comboInterfaces = new JComboBox<ItemCombo>();
		
		// ---------------------------
		// Adiciona as combobox no painel superior (lado esquerdo)
		// ---------------------------
		painelEsquerdo.add(comboDirectories);
		painelEsquerdo.add(comboInterfaces);
		
		// ---------------------------
		// Define o formato das combobox
		// ---------------------------
		formataCombobox();
		
		// ---------------------------
		// Listener para a opcao 'Directories'
		// ---------------------------
		comboDirectories.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent event) {
				
				// ---------------------------
				// Se selecionou a primeira opcao (default)
				// ---------------------------
				if (comboDirectories.getSelectedIndex() == 0) {
					
					// ---------------------------
					// Esconde a combo 'Interfaces'
					// ---------------------------
					comboInterfaces.setVisible(false);
					
				} else {
					
					// ---------------------------
					// Apresenta a combo 'Interfaces'
					// ---------------------------
					comboInterfaces.setVisible(true);
					
					// ---------------------------
					// Carrega o conteudo da combo 'Interfaces'
					// ---------------------------
					loadInterfaces();

				}

			}
		});

		// ---------------------------
		// Listener para a combo 'Interfaces'
		// ---------------------------
		comboInterfaces.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if(event.getStateChange() == ItemEvent.SELECTED) {
					atualizaDadosTabela();
				}
			}
		});
	
	}
	
	/*
	 * Metodo responsavel por criar o painel central
	 */
	public void createCentralPanel() {

		// ---------------------------
		// Cria o painel central
		// ---------------------------
		JPanel painel = new JPanel();
		painel.setLayout(new BorderLayout(0, 0));
		getContentPane().add(painel, BorderLayout.CENTER);
		
		// ---------------------------
		// Cria a textarea do buffer
		// ---------------------------
		buffer = new InspectorTextAreaComponent();
		
		formataBuffer();
		
		// ---------------------------
		// Cria a tabela de resultados
		// ---------------------------
		table = new InspectorJTableComponent(new TableModel());
		formataTable();		
		
		// ---------------------------
		// Cria os scroll panels
		// ---------------------------
		JScrollPane leftScroll = new JScrollPane(buffer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		formataScroll(leftScroll);
		
		JScrollPane rightScroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		formataScroll(rightScroll);
		
		// ---------------------------
		// Cria o split
		// ---------------------------
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftScroll, rightScroll);
		split.setResizeWeight(.3d);
		split.setContinuousLayout(true);
		
		painel.add(split);
		
		
		// ---------------------------
		// Listener para a textarea
		// ---------------------------
		buffer.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent event) {
				// do nothing
			}
			
			public void keyReleased(KeyEvent event) {
				atualizaDadosTabela();
			}
			
			public void keyPressed(KeyEvent event) {
				// do nothing
			}
		});
		
		// ---------------------------
		// Adiciona as funcionalidades undo/redo (control-z / control-y) 
		// ---------------------------
		UndoManager undoManager = new UndoManager();
		
	    Action undoAction = new UndoAction(undoManager);
	    Action redoAction = new RedoAction(undoManager);
		
		buffer.getDocument().addUndoableEditListener(undoManager);
		
		buffer.registerKeyboardAction(undoAction, KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK), JComponent.WHEN_FOCUSED);
		buffer.registerKeyboardAction(redoAction, KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK), JComponent.WHEN_FOCUSED);
		
	}

	/**
	 * Loads directories
	 * 
	 */
	private void loadDirectories() {
		
		// ---------------------------
		// Directories...
		// ---------------------------
		comboDirectories.addItem(new ItemCombo("Directories"));
		
		for (String d : modInspector.getGroups()) {
			comboDirectories.addItem(new ItemCombo(d));
		}
		
	}
	
	/**
	 * Loads interfaces
	 * 
	 */
	private void loadInterfaces() {
		
		// ---------------------------
		// Limpa a combo 'Interfaces', caso tenha valor
		// ---------------------------
		comboInterfaces.removeAllItems();
		
		// ---------------------------
		// Recupera o Servico selecionado
		// ---------------------------
		ItemCombo servicoSelecionado = (ItemCombo) comboDirectories.getSelectedItem();
		
		// ---------------------------
		// Popula o combo de interfaces
		// ---------------------------
		comboInterfaces.addItem(new ItemCombo("Interfaces"));
		
		for (String file : modInspector.getInterfaces(servicoSelecionado.getValor())) {
			comboInterfaces.addItem(new ItemCombo(file));
		}
	}
	
	/**
	 * Define o formato das combobox
	 */
	private void formataCombobox() {
		
		// ---------------------------
		// Formata a borda
		// ---------------------------
		comboDirectories.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		comboInterfaces.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		
		// ---------------------------
		// Formata o espa�amento entre os itens da lista
		// ---------------------------
		comboDirectories.setRenderer(new ComboBoxRenderer());
		comboInterfaces.setRenderer(new ComboBoxRenderer());
		
	}
	
	/**
	 * Define o formato da textarea
	 */
	private void formataBuffer() {

		// ---------------------------
		// Seta a quebra automatica de linhas
		// ---------------------------
		buffer.setLineWrap(true);
		
		// ---------------------------
		// Seta uma fonte monospaced (que facilita a leitura)
		// ---------------------------
		buffer.setFont(new Font("Lucida Console", Font.PLAIN, 12));
		
		// ---------------------------
		// Seta a cor da fonte para cinza escuro 
		// ---------------------------
		buffer.setForeground(new Color(51, 51, 51));
		
	}
	
	/**
	 * Define o formato dos scrolls
	 */
	private void formataScroll(JScrollPane scroll) {
		scroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
	}
	
	/**
	 * Atualiza o formato da tabela (alinhamento e tamanhos de colunas)
	 * 
	 * @param table
	 * @param percentuais
	 */
	private void formataTable() {

		// ---------------------------
		// Centraliza o cabecalho das colunas
		// ---------------------------
	    DefaultTableCellRenderer headerRender = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
	    headerRender.setHorizontalAlignment(SwingConstants.CENTER);
		
		// ---------------------------
		// Por ultimo, atualiza a largura das colunas
		// ---------------------------
		double[] percentuais = { 0.02, 0.35, 0.40, 0.13, 0.10 };
		
		TableColumnModel model = table.getColumnModel();
		
		for (int i = 0; i < percentuais.length; i++) {
			TableColumn coluna = model.getColumn(i);
			coluna.setPreferredWidth((int) (percentuais[i] * 10000));
		}
		
		// ---------------------------
		// Altera a cor do grid da tabela para cinza claro
		// ---------------------------
		table.setGridColor(new Color(221, 221, 221));
		
	}

	/**
	 * Atualiza os dados da tabela 
	 */
	private void atualizaDadosTabela() {
		
		try {
			
			// ---------------------------
			// Altera cursor para 'wait'
			// ---------------------------
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			// ---------------------------
			// Inicia o processo caso o buffer nao estiver vazio
			// ---------------------------
			if (buffer.getText().equals("")) {
				table.setModel(new TableModel());
			} else {
				
				// ---------------------------
				// GSI
				// ---------------------------
				if (comboInterfaces.isVisible()) {
					
					// ---------------------------
					// Recupera a opcao selecionada na combo 'Interfaces'
					// ---------------------------
					int index = comboInterfaces.getSelectedIndex();
					
					// ---------------------------
					// Se nao for a primeira opcao (label da combobox)
					// ---------------------------
					if (index != 0) {
						
						// ---------------------------
						// Recupera a interface selecionada
						// ---------------------------
						ItemCombo operacao = (ItemCombo) comboInterfaces.getSelectedItem();
						
						// ---------------------------
						// Recupera o nome do arquivo de layout
						// ---------------------------
						ItemCombo servico = (ItemCombo) comboDirectories.getSelectedItem();
						String fileName = servico.getValor() + "\\" + operacao.getValor();
						
						// ---------------------------
						// Carrega os dados na tabela
						// ---------------------------
						table.setModel(modInspector.getItensTabela(fileName, buffer.getText()));
						
					}
					
				}
				
			}
			
			// ---------------------------
			// Formata a tabela
			// ---------------------------
			formataTable();
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), Inspector.APPLICATION_NAME, JOptionPane.ERROR_MESSAGE);
		} finally {
			// ---------------------------
			// Retorna cursor para o formato default
			// ---------------------------
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}

	}
	
	/**
	 * Exporta os dados da tabela para um arquivo texto
	 */
	private void exportData() {
		
		// ---------------------------
		// Cria a caixa de dialogo para salvar o arquivo
		// ---------------------------
		JFileChooser fileChooser = new JFileChooser();
		
		// ---------------------------
		// Seta as propriedades da caixa de dialogo
		// ---------------------------
		fileChooser.setFileFilter(new TxtFileFilter());
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setDialogTitle("Export");
		
		// ---------------------------
		// Seta a opcao padrao
		// ---------------------------
		int salvar = JOptionPane.YES_OPTION;
		
		// ---------------------------
		// Se o usuario nao clicou em cancelar
		// ---------------------------
		if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			
			// ---------------------------
			// Tenta salvar o arquivo
			// ---------------------------
			try {
				
				// ---------------------------
				// Recupera o arquivo que a caixa de dialogo esta tentando criar
				// ---------------------------
				File file = fileChooser.getSelectedFile();
				
				// ---------------------------
				// Se o arquivo ja existe solicita permissao para sobreescrever
				// ---------------------------
				if (file.exists()) {
					
					String message = "The file '"+ file.getAbsolutePath() +"' already exists.\nDo you want to overwrite it?";
					
					salvar = JOptionPane.showOptionDialog(this, message, Inspector.APPLICATION_NAME, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				}
				
				// ---------------------------
				// Se tiver permissao para continuar
				// ---------------------------
				if (salvar == JOptionPane.YES_OPTION) {
					
					// ---------------------------
					// Altera cursor para 'wait'
					// ---------------------------
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
					
					// ---------------------------
					// Exporta os dados para o arquivo
					// ---------------------------
					TableModel dadosTabela = (TableModel) table.getModel();
					modInspector.export(buffer.getText(), dadosTabela, file);

					// ---------------------------
					// Apresenta mensagem de sucesso
					// ---------------------------
					JOptionPane.showMessageDialog (null, "Success.", Inspector.APPLICATION_NAME, JOptionPane.INFORMATION_MESSAGE);
					
				} else {
					fileChooser.cancelSelection();
				}
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), Inspector.APPLICATION_NAME, JOptionPane.ERROR_MESSAGE);
			} finally {
				// ---------------------------
				// Retorna cursor para o formato default
				// ---------------------------
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));				
			}
		}
	}
	
}
