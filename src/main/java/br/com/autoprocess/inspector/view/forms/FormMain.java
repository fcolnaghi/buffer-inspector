package br.com.autoprocess.inspector.view.forms;

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
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.undo.UndoManager;

import br.com.autoprocess.inspector.bean.Contexto;
import br.com.autoprocess.inspector.bean.ItemCombo;
import br.com.autoprocess.inspector.bean.TipoHeader;
import br.com.autoprocess.inspector.main.Inspector;
import br.com.autoprocess.inspector.model.ModInspector;
import br.com.autoprocess.inspector.view.actions.RedoAction;
import br.com.autoprocess.inspector.view.actions.UndoAction;
import br.com.autoprocess.inspector.view.components.InspectorJTable;
import br.com.autoprocess.inspector.view.components.InspectorTextArea;
import br.com.autoprocess.inspector.view.filters.TxtFileFilter;
import br.com.autoprocess.inspector.view.models.TableModel;
import br.com.autoprocess.inspector.view.renderers.ComboBoxRenderer;

public class FormMain extends JFrame {

	private static final long serialVersionUID = -2529757428607548663L;
	
	private ModInspector modInspector;

	// ---------------------------
	// Cria componentes da tela principal
	// ---------------------------
	private JRadioButton radioGSI;
	private JRadioButton radioMI;
	
	private JRadioButton radioEntrada;
	private JRadioButton radioSaida;
	
	private JComboBox<ItemCombo> comboServicoGSI;
	private JComboBox<ItemCombo> comboOperacaoGSI;
	private JComboBox<ItemCombo> comboTransacaoMI;
	
	private InspectorTextArea buffer;
	private JTable table;

	private JButton exportar;
	
	/**
	 * Construtor da classe
	 */
	public FormMain() {
		
		// ---------------------------
		// Ao inicializar apresenta a versao
		// ---------------------------
		System.out.println(Inspector.INTERNAL_NAME + " has started...");
		
		// ---------------------------
		// Inicializa a model
		// ---------------------------
		modInspector = new ModInspector();
		
		// ---------------------------
		// Propriedades
		// ---------------------------
		setFormProperties();
		
		// ---------------------------
		// Menu
		// ---------------------------
		criaMenu();
		
		// ---------------------------
		// Painel superior
		// ---------------------------
		criaPainelSuperior();
		
		// ---------------------------
		// Painel central
		// ---------------------------
		criaPainelCentral();
		
		// ---------------------------
		// Painel inferior
		// ---------------------------
		criaPainelInferior();

	}
	
	/**
	 * Seta as propriedades
	 */
	private void setFormProperties() {
		
		// ---------------------------
		// Propriedades
		// ---------------------------
		setTitle(Inspector.APPLICATION_NAME);
		setSize(1024, 600);
		setMinimumSize(new Dimension(800, 600));
		setLocationRelativeTo(null); // centralizado na tela
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		// ---------------------------
		// icone
		// ---------------------------
		ImageIcon icon = new ImageIcon(getClass().getResource("/simbolo-256x256-transparent.png"));
		setIconImage(icon.getImage());
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		// ---------------------------
		// Look and Feel
		// ---------------------------
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metodo responsavel por criar o menu
	 */
	public void criaMenu() {
		
		JMenuBar menubar = new JMenuBar();
		
		// ---------------------------
		// Icones
		// ---------------------------
		// ImageIcon icon = new ImageIcon(getClass().getResource("exit.png"));

		// ---------------------------
		// Arquivo
		// ---------------------------
		JMenu menuArquivo = new JMenu("Arquivo");
		menuArquivo.setMnemonic(KeyEvent.VK_A);
		menubar.add(menuArquivo);
		
		// ---------------------------
		// Arquivo - Exportar
		// ---------------------------
		JMenuItem submenuExportar = new JMenuItem("Exportar");
		submenuExportar.setMnemonic(KeyEvent.VK_E);
		menuArquivo.add(submenuExportar);
		
		menuArquivo.addSeparator();
		
		// ---------------------------
		// Arquivo - Sair
		// ---------------------------
		JMenuItem submenuSair = new JMenuItem("Sair");
		submenuSair.setMnemonic(KeyEvent.VK_S);
		
		submenuSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		
		menuArquivo.add(submenuSair);
		
		// ---------------------------
		// Ajuda
		// ---------------------------
		JMenu menuAjuda = new JMenu("Ajuda");
		menuAjuda.setMnemonic(KeyEvent.VK_J);
		menubar.add(menuAjuda);

		// ---------------------------
		// Ajuda - Sobre
		// ---------------------------
		JMenuItem submenuSobre = new JMenuItem("Sobre Buffer Inspector");
		submenuSobre.setMnemonic(KeyEvent.VK_S);

		menuAjuda.add(submenuSobre);

		// ---------------------------
		// Seta o menu no form
		// ---------------------------
		setJMenuBar(menubar);
	
		
	}
	
	/**
	 * Metodo responsavel por criar o painel superior
	 */
	public void criaPainelSuperior() {
		
		// ---------------------------
		// Cria o painel superior
		// ---------------------------
		JPanel painel = new JPanel();
		painel.setLayout(new BorderLayout());
		getContentPane().add(painel, BorderLayout.NORTH);

		// ---------------------------
		// Cria os radios de selecao com os tipos de transacao
		// ---------------------------
		radioGSI = new JRadioButton("GSI");
		radioMI = new JRadioButton("MI");

		// ---------------------------
		// Cria o agrupamento dos radios
		// ---------------------------
		ButtonGroup grupoTiposHeader = new ButtonGroup();
		grupoTiposHeader.add(radioGSI);
		grupoTiposHeader.add(radioMI);
		
		// ---------------------------
		// Adiciona os radios no painel superior (lado esquerdo)
		// ---------------------------
		JPanel painelEsquerdo = new JPanel();
		painelEsquerdo.add(radioGSI);
		painelEsquerdo.add(radioMI);
		
		painel.add(painelEsquerdo, BorderLayout.WEST);

		// ---------------------------
		// Cria o painel do lado direito
		// ---------------------------
		JPanel painelDireito = new JPanel();
		painel.add(painelDireito, BorderLayout.EAST);
		
		// ---------------------------
		// Cria os combos com os layout de transacao
		// ---------------------------
		comboServicoGSI = new JComboBox<ItemCombo>(modInspector.getServicesList());
		comboOperacaoGSI = new JComboBox<ItemCombo>();
		comboTransacaoMI = new JComboBox<ItemCombo>(modInspector.getTransactionsList());
		
		// ---------------------------
		// Define o formato das combobox
		// ---------------------------
		formataCombobox();
		
		// ---------------------------
		// Adiciona as combobox no painel superior (lado direito)
		// ---------------------------
		painelDireito.add(comboServicoGSI);
		painelDireito.add(comboOperacaoGSI);
		painelDireito.add(comboTransacaoMI);
		
		// ---------------------------
		// Cria os radios de selecao com os tipos de entrada/sa�da
		// ---------------------------
		radioEntrada = new JRadioButton("Input");
		radioSaida = new JRadioButton("Output");
		
		// ---------------------------
		// Adiciona os radios no painel superior (lado direito)
		// ---------------------------
		painelDireito.add(radioEntrada);
		painelDireito.add(radioSaida);
		
		// ---------------------------
		// Cria o agrupamento dos radios
		// ---------------------------
		ButtonGroup grupoContextos = new ButtonGroup();
		grupoContextos.add(radioEntrada);
		grupoContextos.add(radioSaida);
		
		// ---------------------------
		// Seta o foco no radio 'GSI' (default)
		// ---------------------------
		radioGSI.setSelected(true);
		
		// ---------------------------
		// Seta o foco na opcao 'Entrada' (default)
		// ---------------------------
		radioEntrada.setSelected(true);
		
		// ---------------------------
		// Inicialmente somente a combo 'Servico GSI' deve ser visivel, entao esconde as demais
		// ---------------------------
		comboOperacaoGSI.setVisible(false);
		comboTransacaoMI.setVisible(false);
		
		// ---------------------------
		// Listener para o radio 'GSI'
		// ---------------------------
		radioGSI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				// ---------------------------
				// Esconde a combo 'Transacao MI'
				// ---------------------------
				comboTransacaoMI.setVisible(false);
				
				// ---------------------------
				// Apresenta a combo 'Servico GSI'
				// ---------------------------
				comboServicoGSI.setVisible(true);
				
				// ---------------------------
				// Esconde a combo 'Operacao GSI'
				// ---------------------------
				comboOperacaoGSI.setVisible(false);
				
				// ---------------------------
				// Limpa a combo 'Servico GSI'
				// ---------------------------
				if (comboServicoGSI.getSelectedIndex() != 0) {
					comboServicoGSI.setSelectedIndex(0);
				}
				
			}
		});
		
		// ---------------------------
		// Listener para o radio 'MI'
		// ---------------------------
		radioMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				// ---------------------------
				// Esconde a combo 'Servico GSI'
				// ---------------------------
				comboServicoGSI.setVisible(false);
				
				// ---------------------------
				// Esconde a combo 'Operacao GSI'
				// ---------------------------
				comboOperacaoGSI.setVisible(false);
				
				// ---------------------------
				// Apresenta a combo 'Transacao MI'
				// ---------------------------
				comboTransacaoMI.setVisible(true);
				
				// ---------------------------
				// Limpa a combo 'Transacao MI'
				// ---------------------------
				if (comboTransacaoMI.getSelectedIndex() != 0) {
					comboTransacaoMI.setSelectedIndex(0);
				}

			}
		});
		
		// ---------------------------
		// Listener para a opcao 'Servico GSI'
		// ---------------------------
		comboServicoGSI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				// ---------------------------
				// Recupera o Servico selecionado
				// ---------------------------
				ItemCombo servico = (ItemCombo) comboServicoGSI.getSelectedItem();
				
				// ---------------------------
				// Se selecionou a primeira opcao (default)
				// ---------------------------
				if (comboServicoGSI.getSelectedIndex() == 0) {
					
					// ---------------------------
					// Esconde a combo 'Operacao GSI'
					// ---------------------------
					comboOperacaoGSI.setVisible(false);
					
				} else {
					
					// ---------------------------
					// Apresenta a combo 'Operacao GSI'
					// ---------------------------
					comboOperacaoGSI.setVisible(true);
					
					DefaultComboBoxModel<ItemCombo> modeloComboBox = new DefaultComboBoxModel<ItemCombo>(modInspector.getOperationsList(servico.getValor()));
					comboOperacaoGSI.setModel(modeloComboBox);
				}

			}
		});

		// ---------------------------
		// Listener para a combo 'Operacao GSI'
		// ---------------------------
		comboOperacaoGSI.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if(event.getStateChange() == ItemEvent.SELECTED) {
					atualizaDadosTabela();
				}
			}
		});
		
		// ---------------------------
		// Listener para a combo 'Transacao MI'
		// ---------------------------
		comboTransacaoMI.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if(event.getStateChange() == ItemEvent.SELECTED) {
					atualizaDadosTabela();
				}
			}
		});
	
		// ---------------------------
		// Listener para a opcao 'Entrada'
		// ---------------------------
		radioEntrada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				atualizaDadosTabela();
			}
		});

		// ---------------------------
		// Listener para a opcao 'Saida'
		// ---------------------------
		radioSaida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				atualizaDadosTabela();
			}
		});
	
	}
	
	/*
	 * Metodo responsavel por criar o painel central
	 */
	public void criaPainelCentral() {

		// ---------------------------
		// Cria o painel central
		// ---------------------------
		JPanel painel = new JPanel();
		painel.setLayout(new BorderLayout(0, 0));
		getContentPane().add(painel, BorderLayout.CENTER);
		
		// ---------------------------
		// Cria a textarea do buffer
		// ---------------------------
		buffer = new InspectorTextArea();
		
		formataBuffer();
		
		// ---------------------------
		// Cria a tabela de resultados
		// ---------------------------
		table = new InspectorJTable(new TableModel());
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
	 * Metodo responsavel por criar o painel inferior
	 */
	public void criaPainelInferior() {
		
		JPanel painel = new JPanel();
		
		getContentPane().add(painel, BorderLayout.SOUTH);
		painel.setLayout(new BorderLayout());
		
		// ---------------------------
		// Painel esquerdo
		// ---------------------------
		JPanel painelEsquerdo = new JPanel();
		painel.add(painelEsquerdo, BorderLayout.WEST);
		
		// ---------------------------
		// Logo
		// ---------------------------
//		JLabel logo = new JLabel();
//		logo.setToolTipText("Visit our website");
//		logo.setIcon(new ImageIcon(getClass().getResource("/logo-lateral-175x50.png")));
//		
//		logo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//		logo.addMouseListener(new MouseAdapter() {
//			public void mouseClicked(MouseEvent e) {
//				if (e.getClickCount() > 0) {
//					try {
//						Desktop.getDesktop().browse(new URI("http://www.autoprocess.com.br/?f="+ Inspector.INTERNAL_NAME));
//					} catch (Exception ex) {
//						// nothing to do
//					}
//				}
//			}
//		});
//		
//		painelEsquerdo.add(logo, BorderLayout.WEST);
		
		// ---------------------------
		// Exportar
		// ---------------------------
		JPanel painelDireito = new JPanel();
		painel.add(painelDireito, BorderLayout.EAST);
		
		exportar = new JButton("Export...");
		
		painelDireito.add(exportar);
		
		exportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				exportData();
			}
		});
		
	}

	/**
	 * Define o formato das combobox
	 */
	private void formataCombobox() {
		
		// ---------------------------
		// Formata a borda
		// ---------------------------
		comboServicoGSI.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		comboOperacaoGSI.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		comboTransacaoMI.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		
		// ---------------------------
		// Formata o espa�amento entre os itens da lista
		// ---------------------------
		comboServicoGSI.setRenderer(new ComboBoxRenderer());
		comboOperacaoGSI.setRenderer(new ComboBoxRenderer());
		comboTransacaoMI.setRenderer(new ComboBoxRenderer());
		
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
				// Verifica se o buffer e de entrada ou de sa�da
				// ---------------------------
				Contexto ctx = Contexto.ENTRADA;
				
				if (radioSaida.isSelected()) {
					ctx = Contexto.SAIDA;
				}
				
				// ---------------------------
				// GSI
				// ---------------------------
				if (comboOperacaoGSI.isVisible()) {
					
					// ---------------------------
					// Recupera a opcao selecionada na combo 'Operacao GSI'
					// ---------------------------
					int index = comboOperacaoGSI.getSelectedIndex();
					
					// ---------------------------
					// Se nao for a primeira opcao (label da combobox)
					// ---------------------------
					if (index != 0) {
						
						// ---------------------------
						// Recupera a Operacao GSI selecionada
						// ---------------------------
						ItemCombo operacao = (ItemCombo) comboOperacaoGSI.getSelectedItem();
						
						// ---------------------------
						// Recupera o nome do arquivo de layout
						// ---------------------------
						ItemCombo servico = (ItemCombo) comboServicoGSI.getSelectedItem();
						String fileName = servico.getValor() + "\\" + operacao.getValor();
						
						// ---------------------------
						// Carrega os dados na tabela
						// ---------------------------
						table.setModel(modInspector.getItensTabela(fileName, buffer.getText(), TipoHeader.GSI, ctx));
						
					}
					
				} else if (comboTransacaoMI.isVisible()) { // MI
					
					// ---------------------------
					// Recupera a opcao selecionada na combo 'Transacao MI'
					// ---------------------------
					int index = comboTransacaoMI.getSelectedIndex();
					
					// ---------------------------
					// Se nao for a primeira opcao (label da combobox)
					// ---------------------------
					if (index != 0) {
						
						// ---------------------------
						// Recupera a Transacao MI selecionada
						// ---------------------------
						ItemCombo transacao = (ItemCombo) comboTransacaoMI.getSelectedItem();
						
						// ---------------------------
						// Recupera o nome do arquivo de layout
						// ---------------------------
						String fileName = transacao.getValor();
						
						// ---------------------------
						// Carrega os dados na tabela
						// ---------------------------
						table.setModel(modInspector.getItensTabela(fileName, buffer.getText(), TipoHeader.MI, ctx));
						
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
		// Cria a caixa de di�logo para salvar o arquivo
		// ---------------------------
		JFileChooser fileChooser = new JFileChooser();
		
		// ---------------------------
		// Seta as propriedades da caixa de dialogo
		// ---------------------------
		fileChooser.setFileFilter(new TxtFileFilter());
		fileChooser.setAcceptAllFileFilterUsed(false);
		
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
					// Recupera o contexto
					// ---------------------------
					Contexto ctx = Contexto.ENTRADA;
					
					if (radioSaida.isSelected()) {
						ctx = Contexto.SAIDA;
					}
					
					// ---------------------------
					// Exporta os dados para o arquivo
					// ---------------------------
					TableModel dadosTabela = (TableModel) table.getModel();
					modInspector.export(buffer.getText(), dadosTabela, file, ctx);

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
